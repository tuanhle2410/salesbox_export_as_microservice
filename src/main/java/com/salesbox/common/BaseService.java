package com.salesbox.common;

import com.salesbox.dao.*;
import com.salesbox.entity.*;
import com.salesbox.entity.enums.*;
import com.salesbox.exception.ServiceException;
import com.salesbox.message.UserMessage;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: luult
 * Date: 7/2/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseService
{
    @Autowired
    protected UserDAO userDAO;
    @Autowired
    protected EnterpriseDAO enterpriseDAO;
    @Autowired
    protected UserTempDAO userTempDAO;
    @Autowired
    protected SessionDAO sessionDAO;
    @Autowired
    protected LocalizationDAO localizationDAO;
    @Autowired
    protected WorkDataWorkDataDAO workDataWorkDataDAO;
    @Autowired
    protected DeletedContactUserDAO deletedContactUserDAO;
    @Autowired
    protected ContactUserDAO contactUserDAO;
    @Autowired
    protected PermissionDAO permissionDAO;
    @Autowired
    protected RightDAO rightDAO;
    @Autowired
    protected LanguageDAO languageDAO;
    @Autowired
    protected UserConfigurationDAO userConfigurationDAO;
    @Autowired
    protected ServerConfigDAO serverConfigDAO;
    @Autowired
    protected SubscriptionInfoDAO subscriptionInfoDAO;
    @Autowired
    CommunicationHistoryDAO communicationHistoryDAO;

    public static int serverTimezone = TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 1000 / 60 / 60;

    public User getUserFromToken(String token)
    {
        return sessionDAO.findUserByToken(token);
    }

    public Session getOneTokenByUser(User user)
    {
        return sessionDAO.findOneByUser(user);
    }

    public Enterprise getEnterpriseFromToken(String token)
    {
        return sessionDAO.findEnterpriseByToken(token);
    }

    public Scope getScopeByUserAndPermission(User user, String permissionId)
    {
        return rightDAO.findScopeByUserAndPermissionId(user, UUID.fromString(permissionId));
    }


    public String getLocalizationValueByLanguageAndCode(Language language, String keyCode)
    {
        Localization localization = getLocalizationByLanguageAndCode(language, keyCode);
        return localization != null ? localization.getValue() : null;
    }

    public Localization getLocalizationByLanguageAndCode(Language language, String keyCode)
    {
        Localization localization = localizationDAO.findByLanguageAndKeyCode(language, keyCode);

        if (localization == null)
        {
            Language english = languageDAO.findByDescription("English");
            localization = localizationDAO.findByLanguageAndKeyCode(english, keyCode);
        }

        return localization;
    }

    public List<Localization> getLocalizationByLanguageAndListCode(Language language, List<String> keyCodes)
    {
        List<Localization> localizations = localizationDAO.findByLanguageAndKeyCodeIn(language, keyCodes);

        if (localizations == null)
        {
            Language english = languageDAO.findByDescription("English");
            localizations = localizationDAO.findByLanguageAndKeyCodeIn(english, keyCodes);
        }

        return localizations;
    }


    public String generateTrackingCode(String type)
    {
        String trackingCode = RandomStringUtils.randomNumeric(8);
        List<CommunicationHistory> communicationHistorys;
        if (type.equals(TrackingEmailType.EMAIL_CONTENT.toString()))
        {
            communicationHistorys = communicationHistoryDAO.findByTrackingCodeAndType(trackingCode, CommunicationHistoryType.EMAIL_SENDER);
        }
        else if (type.equals(TrackingEmailType.URL.toString()))
        {
            communicationHistorys = communicationHistoryDAO.findByTrackingUrlCodeAndType(trackingCode, CommunicationHistoryType.EMAIL_SENDER);

        }
        else
        {
            communicationHistorys = communicationHistoryDAO.findByTrackingAttachmentCodeAndType(trackingCode, CommunicationHistoryType.EMAIL_SENDER);
        }

        if (communicationHistorys != null && communicationHistorys.size() > 0)
        {
            return generateTrackingCode(type);
        }
        return trackingCode;
    }

    public String createStringFromList(List<String> stringList)
    {
        String result = "";
        for (String object : stringList)
        {
            if (result.length() > 0)
            {
                result += Constant.DELIMITER + object.trim();
            }
            else
            {
                result = object;
            }
        }

        return result;
    }

    public List<UUID> getUUIDListFromString(String stringTemplate)
    {
        List<UUID> uuidList = new ArrayList<>();

        if (null != stringTemplate && stringTemplate.length() != 0)
        {
            String[] idStringList = stringTemplate.split(Constant.DELIMITER);

            for (String idString : idStringList)
            {
                uuidList.add(UUID.fromString(idString.trim()));
            }
        }

        return uuidList;
    }

    public List<String> createListFromString(String stringTemplate)
    {
        List<String> stringList = new ArrayList<>();
        if (stringTemplate == null || stringTemplate.length() == 0)
        {
            return stringList;
        }
        Collections.addAll(stringList, stringTemplate.split(Constant.DELIMITER));

        return stringList;
    }

    public String convertListToString(List<? extends Object> dtoList) throws NoSuchFieldException, IllegalAccessException
    {
        return convertListToString(dtoList, "uuid");
    }

    public String getValueByField(Object ob, String fieldName) throws IllegalAccessException
    {
        Class<?> clazz = ob.getClass();
        Field field = org.springframework.util.ReflectionUtils.findField(clazz, fieldName);
        org.springframework.util.ReflectionUtils.makeAccessible(field);
        Object fieldValue = field.get(ob);
        if (fieldValue != null)
        {
            return fieldValue.toString();
        }
        return null;
    }

    public String convertListToString(List<? extends Object> dtoList, String fieldName) throws NoSuchFieldException, IllegalAccessException
    {
        String convertedString = "";
        for (Object o : dtoList)
        {
            Class<?> clazz = o.getClass();
            Field field = org.springframework.util.ReflectionUtils.findField(clazz, fieldName);
            org.springframework.util.ReflectionUtils.makeAccessible(field);
            Object fieldValue = field.get(o);

            if (convertedString.length() == 0)
            {
                convertedString = fieldValue.toString();
            }
            else
            {
                convertedString += Constant.DELIMITER + fieldValue.toString();
            }
        }

        return convertedString;
    }

    public Calendar getStartWorkingTimeInWeek()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, -12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return calendar;
    }

    public boolean equals(Object oldObject, Object newObject) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if (oldObject == null && newObject == null)
        {
            return true;
        }

        if (oldObject == null && newObject instanceof String && ((String) newObject).length() == 0)
        {
            return true;
        }

        if (newObject == null && oldObject instanceof String && ((String) oldObject).length() == 0)
        {
            return true;
        }

        if ((oldObject != null && newObject == null) ||
                (oldObject == null && newObject != null))
        {
            return false;
        }

        Class<?> clazz = oldObject.getClass();
        Field field = org.springframework.util.ReflectionUtils.findField(clazz, "uuid");
        if (field != null)
        {
            String oldUuid = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(oldObject, "uuid")).invoke(oldObject).toString();
            String newUuid = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(newObject, "uuid")).invoke(newObject).toString();
            return oldUuid.equals(newUuid);
        }
        return oldObject.toString().equals(newObject.toString());
    }

//    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
//    {
//        Revenue revenue = new Revenue();
//        PropertyUtils.getWriteMethod(PropertyUtils.getPropertyDescriptor(revenue, "month1")).invoke(revenue, 1d);
//        System.out.println(revenue.getMonth1());
//    }

    public int getFiscalMonth(Enterprise enterprise)
    {
        String fiscalYear = workDataWorkDataDAO.findValueByEnterpriseAndName(enterprise, Constant.FISCAL_YEAR);

        return new Integer(fiscalYear.split(Constant.delimiterDate)[1]);
    }

    public int getMonth(Date date, Long diffTimeZone)
    {
        Date clientDate = new Date(date.getTime() + diffTimeZone);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(clientDate);

        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getNumberDaysInMonth(Date date, Long diffTimeZone)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime() + diffTimeZone));

        return calendar.getActualMaximum(Calendar.DATE);
    }


    public Language getLanguageFromUser(User user)
    {
        UserConfiguration userConfiguration = userConfigurationDAO.findByUserAndKey(user, UserConfigurationKey.LANGUAGE.toString());

        if (userConfiguration != null)
        {
            return languageDAO.findByName(userConfiguration.getValue());
        }
        else
        {
            return languageDAO.findByName("en");
        }

    }

    public void checkValidateEnterprise(User user) throws ServiceException
    {
        Enterprise enterprise = user.getUnit().getEnterprise();
        Date now = new Date();

        if (enterprise.getType().equals(EnterpriseType.CANCELLATION))
        {
            throw new ServiceException(UserMessage.YOUR_ACCOUNT_HAS_BEEN_CANCELLED.toString());
        }

        if (enterprise.getIsSubscribedAmazon() != null && enterprise.getIsSubscribedAmazon())
        {
            if (user.getSubscriptionInfo() == null || user.getSubscriptionInfo().getAmazonSubscriptionId() == null
                    || user.getSubscriptionInfo().getNextBillingDate().compareTo(new Date()) < 0)
            {
                throw user.getSuperAdmin() ? new ServiceException(UserMessage.YOUR_CARD_CANNOT_PAYMENT_CONTINUE.toString(), user.getSuperAdmin())
                        : new ServiceException(UserMessage.YOUR_CARD_CANNOT_PAYMENT_CONTINUE.toString());
            }
        }
        else
        {

            if (user.getType().equals(UserAccountType.PAID))
            {
                int numberPaidLicense = enterprise.getNumberPaidLicense();
                int numberPaidLicenseIOS = enterprise.getNumberPaidLicenseIOS();
                Date expireDateStripe = enterprise.getExpireDate();
                Date expireDateiOS = enterprise.getExpireDate();

                //if number paid license iOS > 0, get expireDateiOS
                if (numberPaidLicenseIOS > 0)
                {
                    SubscriptionInfo subscriptionInfo = subscriptionInfoDAO.findByEnterpriseAndType(enterprise, SubscriptionSourceType.APPLE);
                    if (subscriptionInfo != null)
                    {
                        expireDateiOS = subscriptionInfo.getNextBillingDate();
                    }
                }

                if ((numberPaidLicense == 0 && numberPaidLicenseIOS == 0) // -> not yet paid or all payment is failed
                        || ((numberPaidLicense > 0 || numberPaidLicenseIOS > 0)
                        && (expireDateStripe.getTime() < now.getTime() && expireDateiOS.getTime() < now.getTime()))) // -> paid but all payment is expired
                {
                    throw user.getSuperAdmin() ? new ServiceException(UserMessage.YOUR_CARD_CANNOT_PAYMENT_CONTINUE.toString(), user.getSuperAdmin())
                            : new ServiceException(UserMessage.YOUR_CARD_CANNOT_PAYMENT_CONTINUE.toString());
                }
            }
            else if (user.getType().equals(UserAccountType.FREE_EXPIRABLE))
            {
                int numberFreeExpirableLicense = enterprise.getNumberFreeExpirableLicense();
                Date freeExpiredDate = enterprise.getFreeExpiredDate();

                if (freeExpiredDate.compareTo(now) < 0 || numberFreeExpirableLicense == 0)
                {
                    throw user.getSuperAdmin() ? new ServiceException(UserMessage.YOUR_CARD_CANNOT_PAYMENT_CONTINUE.toString(), user.getSuperAdmin())
                            : new ServiceException(UserMessage.YOUR_CARD_CANNOT_PAYMENT_CONTINUE.toString());
                }
            }
        }

    }

    public Long getDiffTimeZoneFromTimezone(String timezone)
    {
        String signString = timezone.substring(0, 1);
        int sign = 1;
        if (signString.equals("-"))
        {
            sign = -1;
        }

        Long hour = new Long(timezone.substring(1, 3));
        Long minute = new Long(timezone.substring(3, 5));

        return sign * (hour * 60 * 60 * 1000 + minute * 60 * 1000);
    }

    public Double getDoubleValue(Double aDouble)
    {
//        if (aDouble.compareTo(Constant.e) < 0)
//        {
//            return 0d;
//        }
//        else
//        {
        return aDouble;
//        }
    }

    public Date getFirstDateOfPreviousMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    public void writeToFile(String header, String content, String thePath) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        String dateToString = simpleDateFormat.format(new Date());
        stringBuilder.append("\n****************************************\n" + dateToString + "\n");
        stringBuilder.append(header + "\n");
        stringBuilder.append(content + "\n");
        java.io.File file = new java.io.File(thePath);
        if (!file.exists())
        {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(stringBuilder.toString());
        bw.close();
    }

    public void doLogGlobal(final String header, final String content, final String thePath)
    {
        taskExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    writeToFile(header, content, thePath);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }
}
