package com.salesbox.entity;

import com.salesbox.entity.enums.EnterprisePackageType;
import com.salesbox.entity.enums.EnterpriseType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/10/14
 * Time: 2:33 PM
 */
@Entity
@Table(name = "enterprise")
public class Enterprise extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_id")
    private Billing billing;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_contact_id")
    private User mainContact;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private EnterpriseType type = EnterpriseType.FREE;

    @Column(name = "package_type")
    @Enumerated(EnumType.ORDINAL)
    private EnterprisePackageType packageType = EnterprisePackageType.ULTIMATE_MONTHLY;

    @Column(name = "special_coupon")
    private String specialCoupon;

    @Column(name = "first_login")
    private Boolean firstLogin = Boolean.TRUE; // True only for first time setup enterprise admin

    @Column(name = "wizard_finished")
    private Boolean wizardFinished = Boolean.FALSE;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_organisation_id", nullable = false)
    private SharedOrganisation sharedOrganisation;

    @Column(name = "active")
    private Boolean active = Boolean.FALSE;

    @Column(name ="number_license")
    private Integer numberLicense = 0;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "duration_expire_date")
    private Date durationExpireDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "subscription_id")
    private String subscriptionId;

    @Column(name = "reason_cancel")
    private String reasonCancel;

    @Column(name = "subscription_canceled")
    private Boolean subscriptionCanceled = Boolean.FALSE;

    @Column(name = "subscription_failed")
    private Boolean subscriptionFailed = Boolean.FALSE;

    @Column(name = "decrease_number_license_date")
    private Date decreaseNumberLicenseDate;

    @Column(name = "number_remove_license")
    private Integer numberRemoveLicense;

    @Column(name = "vat")
    private String vat;

    @Column(name = "vat_percent")
    private Double vatPercent;

    @Column(name = "lead_lock")
    private Boolean leadLock = Boolean.FALSE;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "stripe_customer_id")
    private String stripeCustomerId;

    //store last chargeId that paid from Stripe
    @Column(name = "stripe_charge_id")
    private String stripeChargeId;

    @Column(name = "recruit_user")
    @Type(type = "pg-uuid")
    private UUID recruit_user;

    @Column(name = "number_free_license")
    private Integer numberFreeLicense = 0;

    @Column(name = "number_paid_license")
    private Integer numberPaidLicense = 0;

    @Column(name = "number_free_expirable_license")
    private Integer numberFreeExpirableLicense = 0;

    @Column(name = "free_expired_date")
    private Date freeExpiredDate;

    @Column(name = "task_distribution_time_history_list")
    private String taskDistributionTimeHistoryList = ""; // list historic task distribution time in millis second

    @Column(name = "lead_distribution_time_history_list")
    private String leadDistributionTimeHistoryList = ""; // list historic lead distribution time in millis second

    @Column(name ="registered_time_zone")
    private Integer registeredTimeZone;

    @Column(name = "company_app_direct_id")
    private String companyAppDirectId;

    @Column(name ="platform")
    private String platform;

    @Column(name = "validate_receipt_payload")
    private String validateReceiptPayload;

    @Column(name = "number_paid_license_ios")
    private Integer numberPaidLicenseIOS = 0;

    @Column(name = "maestrano_group_id")
    private String maestranoGroupId;

    @Column(name = "maestrano_market_place")
    private String maestranoMarketPlace;

    @Column(name = "maestrano_bill_id")
    private String maestranoBillId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Column(name = "is_subscribed_amazon")
    private Boolean isSubscribedAmazon = Boolean.FALSE;

    @Transient
    public static final String DISTRIBUTION_TIME_DELIMITER = "#!!";


    @Column(name = "tracking_key")
    private String trackingKey;

// --------------------- GETTER / SETTER METHODS ---------------------


    public String getSpecialCoupon()
    {
        return specialCoupon;
    }

    public void setSpecialCoupon(String specialCoupon)
    {
        this.specialCoupon = specialCoupon;
    }

    public EnterprisePackageType getPackageType()
    {
        return packageType;
    }

    public void setPackageType(EnterprisePackageType packageType)
    {
        this.packageType = packageType;
    }

    public Boolean getSubscribedAmazon()
    {
        return isSubscribedAmazon;
    }

    public void setSubscribedAmazon(Boolean subscribedAmazon)
    {
        isSubscribedAmazon = subscribedAmazon;
    }

    public static String getDistributionTimeDelimiter()
    {
        return DISTRIBUTION_TIME_DELIMITER;
    }

    public String getMaestranoBillId() {
        return maestranoBillId;
    }

    public void setMaestranoBillId(String maestranoBillId) {
        this.maestranoBillId = maestranoBillId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getValidateReceiptPayload()
    {
        return validateReceiptPayload;
    }

    public void setValidateReceiptPayload(String validateReceiptPayload)
    {
        this.validateReceiptPayload = validateReceiptPayload;
    }

    public Integer getNumberPaidLicense()
    {
        return numberPaidLicense;
    }

    public void setNumberPaidLicense(Integer numberPaidLicense)
    {
        this.numberPaidLicense = numberPaidLicense;
    }

    public Boolean getWizardFinished()
    {
        return wizardFinished;
    }

    public void setWizardFinished(Boolean wizardFinished)
    {
        this.wizardFinished = wizardFinished;
    }

    public Integer getNumberFreeLicense()
    {
        return numberFreeLicense;
    }

    public void setNumberFreeLicense(Integer numberFreeLicense)
    {
        this.numberFreeLicense = numberFreeLicense;
    }
    public String getStripeCustomerId()
    {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId)
    {
        this.stripeCustomerId = stripeCustomerId;
    }

    public String getStripeChargeId()
    {
        return stripeChargeId;
    }

    public void setStripeChargeId(String stripeChargeId)
    {
        this.stripeChargeId = stripeChargeId;
    }

    public Boolean getSubscriptionCanceled()
    {
        return subscriptionCanceled;
    }

    public void setSubscriptionCanceled(Boolean subscriptionCanceled)
    {
        this.subscriptionCanceled = subscriptionCanceled;
    }

    public Boolean getSubscriptionFailed()
    {
        return subscriptionFailed;
    }

    public void setSubscriptionFailed(Boolean subscriptionFailed)
    {
        this.subscriptionFailed = subscriptionFailed;
    }

    public Date getDecreaseNumberLicenseDate()
    {
        return decreaseNumberLicenseDate;
    }

    public void setDecreaseNumberLicenseDate(Date decreaseNumberLicenseDate)
    {
        this.decreaseNumberLicenseDate = decreaseNumberLicenseDate;
    }

    public Billing getBilling()
    {
        return billing;
    }

    public void setBilling(Billing billing)
    {
        this.billing = billing;
    }

    public User getMainContact()
    {
        return mainContact;
    }

    public void setMainContact(User mainContact)
    {
        this.mainContact = mainContact;
    }

    public EnterpriseType getType()
    {
        return type;
    }

    public void setType(EnterpriseType type)
    {
        this.type = type;
    }

    public Boolean getFirstLogin()
    {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin)
    {
        this.firstLogin = firstLogin;
    }

    public SharedOrganisation getSharedOrganisation()
    {
        return sharedOrganisation;
    }

    public void setSharedOrganisation(SharedOrganisation sharedOrganisation)
    {
        this.sharedOrganisation = sharedOrganisation;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Integer getNumberLicense()
    {
        return numberLicense;
    }

    public void setNumberLicense(Integer numberLicense)
    {
        this.numberLicense = numberLicense;
    }

    public Date getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(Date expireDate)
    {
        this.expireDate = expireDate;
    }

    public Date getDurationExpireDate()
    {
        return durationExpireDate;
    }

    public void setDurationExpireDate(Date durationExpireDate)
    {
        this.durationExpireDate = durationExpireDate;
    }

    public Language getLanguage()
    {
        return language;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }

    public String getSubscriptionId()
    {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId)
    {
        this.subscriptionId = subscriptionId;
    }

    public String getReasonCancel()
    {
        return reasonCancel;
    }

    public void setReasonCancel(String reasonCancel)
    {
        this.reasonCancel = reasonCancel;
    }

    public Integer getNumberRemoveLicense()
    {
        return numberRemoveLicense;
    }

    public void setNumberRemoveLicense(Integer numberRemoveLicense)
    {
        this.numberRemoveLicense = numberRemoveLicense;
    }

    public String getVat()
    {
        return vat;
    }

    public void setVat(String vat)
    {
        this.vat = vat;
    }

    public Double getVatPercent()
    {
        return vatPercent;
    }

    public void setVatPercent(Double vatPercent)
    {
        this.vatPercent = vatPercent;
    }

    public Boolean getLeadLock()
    {
        return leadLock;
    }

    public void setLeadLock(Boolean leadLock)
    {
        this.leadLock = leadLock;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public UUID getRecruit_user()
    {
        return recruit_user;
    }

    public void setRecruit_user(UUID recruit_user)
    {
        this.recruit_user = recruit_user;
    }

    public Integer getNumberFreeExpirableLicense()
    {
        return numberFreeExpirableLicense;
    }

    public void setNumberFreeExpirableLicense(Integer numberFreeExpirableLicense)
    {
        this.numberFreeExpirableLicense = numberFreeExpirableLicense;
    }

    public Date getFreeExpiredDate()
    {
        return freeExpiredDate;
    }

    public void setFreeExpiredDate(Date freeExpiredDate)
    {
        this.freeExpiredDate = freeExpiredDate;
    }

    public Integer getRegisteredTimeZone()
    {
        return registeredTimeZone;
    }

    public void setRegisteredTimeZone(Integer registeredTimeZone)
    {
        this.registeredTimeZone = registeredTimeZone;
    }

    public String getTaskDistributionTimeHistoryList()
    {
        return taskDistributionTimeHistoryList;
    }

    public void setTaskDistributionTimeHistoryList(String taskDistributionTimeHistoryList)
    {
        this.taskDistributionTimeHistoryList = taskDistributionTimeHistoryList;
    }

    public String getCompanyAppDirectId()
    {
        return companyAppDirectId;
    }

    public void setCompanyAppDirectId(String companyAppDirectId)
    {
        this.companyAppDirectId = companyAppDirectId;
    }


    public void appendTaskDistributionTimeHistoryList(String distributionTimeString)
    {
        if (this.taskDistributionTimeHistoryList.length() == 0)
        {
            this.setTaskDistributionTimeHistoryList(distributionTimeString);
        }
        else
        {
            this.setTaskDistributionTimeHistoryList(taskDistributionTimeHistoryList + DISTRIBUTION_TIME_DELIMITER + distributionTimeString);
        }
    }

    public String getLeadDistributionTimeHistoryList()
    {
        return leadDistributionTimeHistoryList;
    }

    public void setLeadDistributionTimeHistoryList(String leadDistributionTimeHistoryList)
    {
        this.leadDistributionTimeHistoryList = leadDistributionTimeHistoryList;
    }

    public void appendLeadDistributionTimeHistoryList(String distributionTimeString)
    {
        if (this.leadDistributionTimeHistoryList.length() == 0)
        {
            this.setLeadDistributionTimeHistoryList(distributionTimeString);
        }
        else
        {
            this.setLeadDistributionTimeHistoryList(leadDistributionTimeHistoryList + DISTRIBUTION_TIME_DELIMITER + distributionTimeString);
        }
    }

    public double getTaskAvgDistributionTime()
    {
        List<String> distributionTimeStringList = new ArrayList<>();
        if (taskDistributionTimeHistoryList.length() > 0)
        {
            Collections.addAll(distributionTimeStringList, taskDistributionTimeHistoryList.split(DISTRIBUTION_TIME_DELIMITER));
        }

        return getAvgDistributionTimeAsDouble(distributionTimeStringList);
    }

    public double getLeadAvgDistributionTime()
    {
        List<String> distributionTimeStringList = new ArrayList<>();
        if (leadDistributionTimeHistoryList.length() > 0)
        {
            Collections.addAll(distributionTimeStringList, leadDistributionTimeHistoryList.split(DISTRIBUTION_TIME_DELIMITER));
        }

        return getAvgDistributionTimeAsDouble(distributionTimeStringList);
    }

    private double getAvgDistributionTimeAsDouble(List<String> distributionTimeStringList)
    {
        long totalDistributionTime = 0;
        long numberDistribution = 0;
        for (String distributionTime : distributionTimeStringList)
        {
            totalDistributionTime += Long.valueOf(distributionTime);
            numberDistribution++;
        }

        return numberDistribution > 0 ? ((double) totalDistributionTime) / numberDistribution : 0;
    }

    public Integer getNumberPaidLicenseIOS()
    {
        return numberPaidLicenseIOS;
    }

    public void setNumberPaidLicenseIOS(Integer numberPaidLicenseIOS)
    {
        this.numberPaidLicenseIOS = numberPaidLicenseIOS;
    }

    public String getMaestranoGroupId() {
        return maestranoGroupId;
    }

    public void setMaestranoGroupId(String maestranoGroupId) {
        this.maestranoGroupId = maestranoGroupId;
    }

    public String getMaestranoMarketPlace()
    {
        return maestranoMarketPlace;
    }

    public void setMaestranoMarketPlace(String maestranoMarketPlace)
    {
        this.maestranoMarketPlace = maestranoMarketPlace;
    }

    public Storage getStorage()
    {
        return storage;
    }

    public void setStorage(Storage storage)
    {
        this.storage = storage;
    }

    public Boolean getIsSubscribedAmazon()
    {
        return isSubscribedAmazon;
    }

    public void setIsSubscribedAmazon(Boolean isSubscribedAmazon)
    {
        this.isSubscribedAmazon = isSubscribedAmazon;
    }

    public String getTrackingKey()
    {
        return trackingKey;
    }

    public void setTrackingKey(String trackingKey)
    {
        this.trackingKey = trackingKey;
    }
}
