package com.salesbox.utils;

import com.amazonaws.services.s3.model.S3Object;
import com.salesbox.common.BaseService;
import com.salesbox.dao.LanguageDAO;
import com.salesbox.dao.LocalizationDAO;
import com.salesbox.dto.ColumnExcel;
import com.salesbox.dto.CommunicationDTO;
import com.salesbox.dto.ResponseImportDTO;
import com.salesbox.entity.*;
import com.salesbox.entity.enums.ActionType;
import com.salesbox.entity.enums.FileType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.exception.ServiceException;
import com.salesbox.localization.ImportExportColumnCode;
import com.salesbox.mail.Content;
import com.salesbox.mail.EmailNotificationParams;
import com.salesbox.mail.MailSender;
import com.salesbox.mail.Recipient;
import com.salesbox.message.ImportExportException;
import com.salesbox.message.ImportExportMessage;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by: hunglv
 * Date: 9/26/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImportExportUtils extends BaseService
{
    @Autowired
    LanguageDAO languageDAO;
    @Autowired
    LocalizationDAO localizationDAO;
    @Autowired
    MailSender mailSender;


    public HashMap<String, String> getStringHashMapImportMessage(Enterprise enterprise)
    {
        HashMap<String, String> hashString = new HashMap<>();
        ImportExportMessage[] importExportMessages = ImportExportMessage.values();
        List<String> keyList = new ArrayList<>();

        for (ImportExportMessage importExportMessage : importExportMessages)
        {
            String key = importExportMessage.name();
            keyList.add(key);
        }

        List<Localization> localizationList = getLocalizationByLanguageAndListCode(enterprise.getLanguage(), keyList);
        for (Localization localization : localizationList)
        {
            hashString.put(localization.getKeyCode(), localization.getValue());
        }

        Language english = languageDAO.findByDescription("English");
        if (enterprise.getLanguage().getUuid().compareTo(english.getUuid()) != 0)
        {
            validateMessage(hashString, english, keyList);
        }

        return hashString;
    }

    public HashMap<String, String> getStringHashMapNameColumnExcel(Enterprise enterprise)
    {
        HashMap<String, String> hashString = new HashMap<>();
        ImportExportColumnCode[] importExportMessages = ImportExportColumnCode.values();
        List<String> keyList = new ArrayList<>();

        for (ImportExportColumnCode importExportMessage : importExportMessages)
        {
            String key = importExportMessage.name();
            keyList.add(key);
        }

        List<Localization> localizationList = getLocalizationByLanguageAndListCode(enterprise.getLanguage(), keyList);
        for (Localization localization : localizationList)
        {
            hashString.put(localization.getKeyCode(), localization.getValue());
        }

        Language english = languageDAO.findByDescription("English");
        if (enterprise.getLanguage().getUuid().compareTo(english.getUuid()) != 0)
        {
            validateMessage(hashString, english, keyList);
        }
        return hashString;
    }

    public List<ColumnExcel> generateColumnExcelList(Enterprise enterprise, ObjectType objectType)
    {
        //Todo set enum for CellType
        HashMap<String, String> hashMap = getStringHashMapNameColumnExcel(enterprise);
        if (objectType.equals(ObjectType.ACCOUNT))
        {
            return Arrays.asList(
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ACCOUNT_NAME.toString()), 1, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ADDRESS.toString()), 2, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ZIP_CODE.toString()), 3, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.CITY.toString()), 4, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.REGION.toString()), 5, false, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.COUNTRY.toString()), 6, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PHONE.toString()), 7, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.EMAIL.toString()), 8, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.WEB.toString()), 9, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ORGANISATION_TYPE.toString()), 10, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.INDUSTRY.toString()), 11, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.SIZE.toString()), 12, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.BUDGET.toString()), 13, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.APPOINTMENT_GOAL.toString()), 14, false, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ACCOUNT_TEAM.toString()), 15, true, 2)
            );
        }
        else if (objectType.equals(ObjectType.CONTACT))
        {
            return Arrays.asList(
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.FIRST_NAME.toString()), 0, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.LAST_NAME.toString()), 1, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ACCOUNT_EMAIL.toString()), 2, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ADDRESS.toString()), 3, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ZIP_CODE.toString()), 4, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.CITY.toString()), 5, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.REGION.toString()), 6, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.COUNTRY.toString()), 7, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.TITLE.toString()), 8, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PHONE.toString()), 9, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.EMAIL.toString()), 10, true, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ORGANISATION_TYPE.toString()), 11, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.INDUSTRY.toString()), 12, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.RELATION.toString()), 13, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.RELATIONSHIP.toString()), 14, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.DISC_PROFILE.toString()), 15, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.CONTACT_TEAM.toString()), 16, true, 1)
            );
        }
        else if (objectType.equals(ObjectType.PRODUCT_REGISTER))
        {
            return Arrays.asList(
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.LINE_OF_BUSINESS.toString()), 1, true, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.DEFAULT_SALES_METHOD.toString()), 2, false, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PRODUCT_TYPE.toString()), 3, true, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PRODUCT_NAME.toString()), 4, true, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PRICE_PER_UNIT.toString()), 5, true, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.NUMBER_OF_UNITS.toString()), 6, true, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.MARGIN.toString()), 7, true, 0)
            );
        }
        else if (objectType.equals(ObjectType.OPPORTUNITY))
        {
            return Arrays.asList(
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PROSPECT_ID.toString()), 0, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.DESCRIPTION.toString()), 1, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.SPONSOR.toString()), 2, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ACCOUNT_EMAIL.toString()), 3, false, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.LINE_OF_BUSINESS.toString()), 4, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.SALES_METHOD.toString()), 5, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.CONTRACT_DATE.toString()), 6, true, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.STATUS_PROSPECT.toString()), 7, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.OPPORTUNITY_TEAM.toString()), 8, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.USER_SHARE_OF_ORDER_VALUE.toString()), 9, true, 2)
            );
        }
        else if (objectType.equals(ObjectType.ORDER_ROW))
        {
            return Arrays.asList(
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PROSPECT_ID.toString()), 0, true, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PRODUCT_NAME.toString()), 1, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PRODUCT_TYPE.toString()), 3, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.NUMBER_OF_UNITS.toString()), 3, true, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PRICE_PER_UNIT.toString()), 4, true, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.DELIVERY_START_DATE.toString()), 5, true, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.DELIVERY_END_DATE.toString()), 6, true, 0),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.MARGIN.toString()), 7, true, 0)
            );
        }
        else if (objectType.equals(ObjectType.CALL_LIST))
        {
            return Arrays.asList(
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.FIRST_NAME.toString()), 0, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.LAST_NAME.toString()), 1, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.COUNTRY.toString()), 2, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PHONE.toString()), 3, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.EMAIL.toString()), 4, false, 1)
            );
        }
        else
        {
            return Arrays.asList(
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.FIRST_NAME.toString()), 0, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.LAST_NAME.toString()), 1, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.PHONE.toString()), 2, true, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.EMAIL.toString()), 3, true, 1),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ACCOUNT_NAME.toString()), 4, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ADDRESS.toString()), 5, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.ZIP_CODE.toString()), 6, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.CITY.toString()), 7, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.REGION.toString()), 8, 2),
                    new ColumnExcel(hashMap.get(ImportExportColumnCode.COUNTRY.toString()), 9, 2));
        }
    }

    private void validateMessage(HashMap<String, String> hashString, Language english, List<String> keyList)
    {
        List<Localization> localizationList = getLocalizationByLanguageAndListCode(english, keyList);
        for (String key : keyList)
        {
            if (hashString.get(key) == null)
            {
                hashString.put(key, getValueFromKey(key, localizationList));
            }
        }
    }

    private String getValueFromKey(String key, List<Localization> localizationList)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Localization localization : localizationList)
        {
            if (localization.getKeyCode().equals(key))
            {
                stringBuilder.append(localization.getValue());
            }
        }
        return stringBuilder.toString();
    }

    public void removeHeaderRow(Iterator<Row> rowIterator)
    {
        if (rowIterator.hasNext())
        {
            rowIterator.next();
        }
    }

    public Workbook getWorkbookFromFile(MultipartFile file) throws IOException, ServiceException, InvalidFormatException
    {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);
        if (null == workbook)
        {
            throw new ServiceException(ImportExportException.FAIL_READ_FILE.toString());
        }
        return workbook;
    }

    public void createFilteringData(Sheet sheet, String[] arrayString, int columnIndex)
    {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)
                dvHelper.createExplicitListConstraint(arrayString);
        CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, columnIndex, columnIndex);
        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(
                dvConstraint, addressList);
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);
    }

    public CellStyle getCellStyleDate(Workbook workbook)
    {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yyyy"));
        return cellStyle;
    }

    public String getStringValueFromCell(Row row, ColumnExcel columnExcel)
    {
        if (null == row.getCell(columnExcel.getIndex()))
        {
            return null;
        }
        if (row.getCell(columnExcel.getIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC)
        {
            return String.valueOf((long) row.getCell(columnExcel.getIndex()).getNumericCellValue());
        }
        else
        {
            try
            {
                String cellValueString = row.getCell(columnExcel.getIndex()).getStringCellValue().trim();
                return cellValueString.length() > 0 ? cellValueString : null;

            }
            catch (IllegalStateException ignored)
            {
                return String.valueOf(row.getCell(columnExcel.getIndex()).getNumericCellValue());
            }
        }
    }


    public Double getNumberValueFromCell(Row row, ColumnExcel columnExcel)
    {
        if (null == row.getCell(columnExcel.getIndex()))
        {
            return null;
        }
        return row.getCell(columnExcel.getIndex()).getNumericCellValue();
    }

    public Double validateDoubleNumberCell(Row row, ColumnExcel columnExcel)
    {
        if (null == row.getCell(columnExcel.getIndex()))
        {
            return null;
        }
        return row.getCell(columnExcel.getIndex()).getNumericCellValue();
    }

    public Double validatePositiveDoubleNumberCell(Row row, ColumnExcel columnExcel)
    {
        Double value = validateDoubleNumberCell(row, columnExcel);
        if (value == null || value < 0)
        {
            return null;
        }
        return row.getCell(columnExcel.getIndex()).getNumericCellValue();
    }

    public Date getDateValueFromCell(Row row, ColumnExcel columnExcel) throws ParseException
    {
        if (null == row.getCell(columnExcel.getIndex()))
        {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            return simpleDateFormat.parse(row.getCell(columnExcel.getIndex()).getStringCellValue());
        }
        catch (Exception e)
        {
            System.out.println(columnExcel.getName());
            System.out.println(columnExcel.getIndex());
            throw e;
        }
    }

    public void checkMissingInput(Row row, StringBuilder errorRow, List<ColumnExcel> columnExcelList)
    {
        for (ColumnExcel columnExcel : columnExcelList)
        {
            Cell cell = row.getCell(columnExcel.getIndex());
            if (columnExcel.isRequired()
                    && (cell == null
                    || cell.getCellType() == 3
                    || (cell.getCellType() == 1 && cell.getStringCellValue().length() == 0)
                    || (cell.getCellType() == 0 && cell.getNumericCellValue() == 0.0d)))
            {
                appendMessage(errorRow, columnExcel.getName());
            }
        }
    }

    public void checkInput(Row row, StringBuilder errorMissingRow, List<ColumnExcel> columnExcelList, StringBuilder errorInvalidData)
    {
        for (ColumnExcel columnExcel : columnExcelList)
        {
            Cell cell = row.getCell(columnExcel.getIndex());
            if (columnExcel.isRequired() &&
                    (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK
                            || (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().length() == 0)))
            {
                columnExcel.setCheck(true);
                appendMessage(errorMissingRow, columnExcel.getName());
            }

            if (cell != null && !columnExcel.isCheck())
            {
                try
                {
                    if (columnExcel.getCellType() == Cell.CELL_TYPE_STRING)
                    {
                        cell.getStringCellValue();
                    }
                    else if (columnExcel.getCellType() == Cell.CELL_TYPE_NUMERIC)
                    {
                        if (!"Contract date".equals(columnExcel.getName())
                                && !"Delivery start date".equals(columnExcel.getName())
                                && !"Delivery end date".equals(columnExcel.getName()))
                        {
                            cell.getNumericCellValue();
                        }
                        else
                        {
                            cell.getStringCellValue();
                        }
                    }
                }
                catch (IllegalStateException ignored)
                {
                    appendMessage(errorInvalidData, columnExcel.getName());
                }
            }

            columnExcel.setCheck(false);
        }
    }

    public StringBuilder appendMessage(StringBuilder appendedText, String appendingText)
    {
        if (appendedText.toString().length() > 0)
        {
            appendedText.append(", ").append(appendingText);
        }
        else
        {
            appendedText.append(appendingText);
        }

        return appendedText;
    }

    public StringBuilder appendNewValueOther(StringBuilder errorRow, String value)
    {
        if (errorRow.toString().length() > 0)
        {
            errorRow.append("; ").append(value);
        }
        else
        {
            errorRow.append(value);
        }
        return errorRow;
    }


    public CellStyle createHeadStyle(Workbook workbook)
    {
        Font titleFont = workbook.createFont();
        titleFont.setColor(IndexedColors.WHITE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerCellStyle.setFont(titleFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setWrapText(true);

        return headerCellStyle;
    }

    public CellStyle createHeadStyle(HSSFWorkbook workbook)
    {
        Font titleFont = workbook.createFont();
        titleFont.setColor(IndexedColors.WHITE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerCellStyle.setFont(titleFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setWrapText(true);

        return headerCellStyle;
    }

    public CellStyle createCenterStyle(HSSFWorkbook workbook)
    {
        CellStyle centerCellStyle = workbook.createCellStyle();
        centerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        centerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return centerCellStyle;
    }

    public CellStyle createCenterGreyForegroundStyle(HSSFWorkbook workbook)
    {
        CellStyle centerCellStyle = workbook.createCellStyle();
        centerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        centerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        centerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        centerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return centerCellStyle;
    }

    public CellStyle createGreyForegroundStyle(HSSFWorkbook workbook)
    {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    public CellStyle createPercentStyle(HSSFWorkbook workbook)
    {
        CellStyle percentCellStyle = workbook.createCellStyle();
        percentCellStyle.setAlignment(HorizontalAlignment.CENTER);
        percentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        percentCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0%"));
        return percentCellStyle;
    }

    public CellStyle createPercentGreyForegroundStyle(HSSFWorkbook workbook)
    {
        CellStyle percentCellStyle = workbook.createCellStyle();
        percentCellStyle.setAlignment(HorizontalAlignment.CENTER);
        percentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        percentCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        percentCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        percentCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0%"));
        return percentCellStyle;
    }

    public ImportExportHistory generateImportHistory(User user, Enterprise enterprise, ObjectType objectType, String preNameFile, FileType fileType)
    {
        ImportExportHistory importExportHistory = new ImportExportHistory();

        importExportHistory.setObjectType(objectType);
        importExportHistory.setActionType(ActionType.IMPORT);
        importExportHistory.setEnterprise(enterprise);
        importExportHistory.setUsername(user.getSharedContact().getFirstName() + " " + user.getSharedContact().getLastName());

        StringBuilder fileName = createImportHistoryFileName(user, preNameFile, fileType);

        importExportHistory.setFileName(String.valueOf(fileName));

        return importExportHistory;
    }

    public StringBuilder createImportHistoryFileName(User user, String preNameFile, FileType fileType)
    {
        StringBuilder fileName = new StringBuilder(preNameFile);
        Calendar cal = Calendar.getInstance();
        fileName.append(String.valueOf(cal.get(Calendar.YEAR))).append(String.valueOf(cal.get(Calendar.MONTH) + 1)).append(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        fileName.append("_").append(user.getSharedContact().getFirstName()).append("_").append(user.getSharedContact().getLastName());
        return fileName.append(".").append(fileType.toString().toLowerCase());
    }

    public StringBuilder createFailImportFileName(String preNameFile, FileType fileType)
    {
        StringBuilder fileName = new StringBuilder(preNameFile);
        Calendar cal = Calendar.getInstance();
        fileName.append(String.valueOf(cal.get(Calendar.YEAR))).append(String.valueOf(cal.get(Calendar.MONTH) + 1)).append(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        return fileName.append(".").append(fileType.toString().toLowerCase());
    }

    public ImportExportHistory generateExportHistory(User user, Enterprise enterprise, StringBuilder fileName, ObjectType objectType)
    {
        ImportExportHistory importExportHistory = new ImportExportHistory();
        importExportHistory.setFileName(String.valueOf(fileName));
        importExportHistory.setUsername(user.getSharedContact().getFirstName() + " " + user.getSharedContact().getLastName());
        importExportHistory.setEnterprise(enterprise);
        importExportHistory.setObjectType(objectType);
        importExportHistory.setActionType(ActionType.EXPORT);

        return importExportHistory;
    }

    public StringBuilder createExportFileName(FileType fileType, String preFileName)
    {
        StringBuilder fileName = new StringBuilder(preFileName);
        Calendar exportTime = Calendar.getInstance();
        fileName.append(String.valueOf(exportTime.get(Calendar.YEAR)))
                .append(String.valueOf(exportTime.get(Calendar.MONTH) + 1))
                .append(String.valueOf(exportTime.get(Calendar.DAY_OF_MONTH)))
                .append(String.valueOf(exportTime.get(Calendar.HOUR_OF_DAY)))
                .append(String.valueOf(exportTime.get(Calendar.MINUTE)))
                .append(String.valueOf(exportTime.get(Calendar.SECOND)))
                .append(String.valueOf(exportTime.get(Calendar.MILLISECOND)));
        fileName.append(".").append(fileType.toString().toLowerCase());
        return fileName;
    }

    public StringBuilder createSubjectExportName(String preFileName)
    {
        StringBuilder fileName = new StringBuilder(preFileName);
        Calendar exportTime = Calendar.getInstance();
        fileName.append(String.valueOf(exportTime.get(Calendar.MONTH) + 1)).append("/").append(String.valueOf(exportTime.get(Calendar.DAY_OF_MONTH))).append("/").append(String.valueOf(exportTime.get(Calendar.YEAR)));
        return fileName;
    }

    public StringBuilder createExportOrderRowFileName(User user, FileType fileType, String preFileName)
    {
        StringBuilder fileName = new StringBuilder(preFileName);

        Calendar exportTime = Calendar.getInstance();
        fileName.append(String.valueOf(exportTime.get(Calendar.YEAR))).append(String.valueOf(exportTime.get(Calendar.MONTH) + 1)).append(String.valueOf(exportTime.get(Calendar.DAY_OF_MONTH)));

        fileName.append("_").append(user.getSharedContact().getFirstName()).append("_").append(user.getSharedContact().getLastName());
        fileName.append(".").append(fileType.toString().toLowerCase());
        return fileName;
    }

    public List<String> convertCommunicationDTOToListValue(List<CommunicationDTO> communicationDTOList)
    {
        List<String> resultList = new ArrayList<>();
        for (CommunicationDTO communicationDTO : communicationDTOList)
        {
            resultList.add(communicationDTO.getValue());
        }
        return resultList;
    }

    public StringBuilder checkDuplicateEmailInput(List<String> emailList, List<String> allEmailList)
    {
        StringBuilder output = new StringBuilder();
        for (String email : emailList)
        {
            if (allEmailList.contains(email))
            {
                if (output.length() == 0)
                {
                    output.append(email);
                }
                else
                {
                    output.append(", ").append(email);
                }
            }
        }
        return output;
    }

    public StringBuilder convertListEmailToString(List<String> duplicateInputList)
    {
        StringBuilder output = new StringBuilder();
        for (String string : duplicateInputList)
        {
            if (output.length() == 0)
            {
                output.append(string);
            }
            else
            {
                output.append(", ").append(string);
            }
        }
        return output;
    }


    public List<String> getEmailListFromOneRow(Row row, List<ColumnExcel> accountRow, int indexEmailColumn)
    {
        List<String> emailList = new ArrayList<>();
        String email = getStringValueFromCell(row, accountRow.get(indexEmailColumn));

        if (email != null && email.length() > 0)
        {
            for (String value : email.trim().split(","))
            {
                value = value.trim().toLowerCase();
                emailList.add(value);
            }
        }

        return emailList;
    }

    public void checkSizeExport(int size) throws ServiceException
    {
        if (size > 10000)
        {
            throw new ServiceException(ImportExportException.CAN_NOT_EXPORT_DATA_BIGGER_THAN_10000.toString());
        }

//        if (size == 0)
//        {
//            throw new ServiceException(ImportExportException.NO_DATA_TO_EXPORT.toString());
//        }
    }

    public StringBuilder validEmailList(List<String> emailList)
    {
        StringBuilder invalidEmails = new StringBuilder();
        EmailValidator emailValidator = EmailValidator.getInstance();
        for (String email : emailList)
        {
            if (!emailValidator.isValid(email))
            {
                appendMessage(invalidEmails, email);
            }
        }
        return invalidEmails;
    }

    public StringBuilder getAllErrorInputMessage(StringBuilder errorMissingData, StringBuilder errorInvalidData,
                                                 StringBuilder invalidEmails, HashMap<String, String> errorMessageTemplateMap)
    {
        StringBuilder errorInputMessage = new StringBuilder();

        if (errorMissingData.length() > 0)
        {
            errorInputMessage.append(errorMessageTemplateMap.get(ImportExportMessage.DATA_MISSING.toString()).replaceFirst("\\[\\w*\\]", String.valueOf(errorMissingData))).append(". ");
        }
        if (invalidEmails.length() > 0)
        {
            errorInputMessage.append(errorMessageTemplateMap.get(ImportExportMessage.EMAIL_INVALID.toString())).append(": ").append(invalidEmails).append(". ");
        }

        if (errorInvalidData.length() > 0)
        {
            errorInputMessage.append(errorMessageTemplateMap.get(ImportExportMessage.INVALID.toString()).replaceFirst("\\[\\w*\\]", String.valueOf(errorInvalidData))).append(". ");
        }
        return errorInputMessage;
    }

    public void putErrorMessageOrderByIndex(ResponseImportDTO responseDTO, HashMap<Integer, String> errorIndexHashMap)
    {
        SortedSet<Integer> keys = new TreeSet<>(errorIndexHashMap.keySet());
        for (Integer key : keys)
        {
            responseDTO.getErrorList().add(errorIndexHashMap.get(key));
        }
    }

    /*public Workbook generateFailImportFile(HashMap<Integer, Row> rowFailHashMap, ObjectFile objectFile,
                                           int size) throws ServiceException, IOException, EncoderException
    {
        byte[] attachFile = objectFile.getBytes();
        InputStream inputStream = new ByteArrayInputStream(attachFile);
        Workbook workbookFail = new HSSFWorkbook(inputStream);
        Sheet sheetFail = workbookFail.getSheetAt(0);

        SortedSet<Integer> keys = new TreeSet<>(rowFailHashMap.keySet());
        int i = 1;
        for (Integer key : keys)
        {
            Row newRow = sheetFail.createRow(i++);
            putValueFromRowFailToNewRow(rowFailHashMap.get(key), newRow, size);
        }
        return workbookFail;
    }*/

    public Workbook generateFailImportFile(HashMap<Integer, Row> rowFailHashMap, S3Object s3Object,
                                           int size) throws ServiceException, IOException, EncoderException
    {
        byte[] attachFile = IOUtils.toByteArray(s3Object.getObjectContent());
        InputStream inputStream = new ByteArrayInputStream(attachFile);
        Workbook workbookFail = new HSSFWorkbook(inputStream);
        Sheet sheetFail = workbookFail.getSheetAt(0);

        SortedSet<Integer> keys = new TreeSet<>(rowFailHashMap.keySet());
        int i = 1;
        for (Integer key : keys)
        {
            Row newRow = sheetFail.createRow(i++);
            putValueFromRowFailToNewRow(rowFailHashMap.get(key), newRow, size);
        }
        return workbookFail;
    }


    public void putValueFromRowFailToNewRow(Row rowFail, Row newRow, int size)
    {
        for (int i = 0; i < size; i++)
        {
            Cell newCell = newRow.createCell(i);
            Cell cell = rowFail.getCell(i);
            if (cell != null && cell.toString().length() > 0)
            {
                if (cell.getCellType() != 0)
                {
                    newCell.setCellValue(cell.toString());
                }
                else
                {
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                        CellStyle cellStyle = getCellStyleDate(newRow.getSheet().getWorkbook());
                        newCell.setCellStyle(cellStyle);
                        newCell.setCellValue(cell.getDateCellValue());
                    }
                    else
                    {
                        Double doubleValue = Double.parseDouble(cell.toString());
                        if (Math.abs(doubleValue - Math.round(doubleValue)) > 0)
                        {
                            newCell.setCellValue(Double.parseDouble(cell.toString()));
                        }
                        else
                        {
                            newCell.setCellValue(Double.parseDouble(cell.toString()));
                        }
                    }
                }
            }
        }
    }


    public void sendMailWithExportResult(User user, String fileURL)
    {
        String[] fileNameSplit = fileURL.split("/");
        String fileName = fileNameSplit[fileNameSplit.length - 1];

        Language language = getLanguageFromUser(user);

        EmailNotificationParams emailNotificationParams = new EmailNotificationParams();

        String subject = getSubjectExport(language);
        emailNotificationParams.setSubject(subject);


        Recipient toRecipient = new Recipient(Message.RecipientType.TO, user.getSharedContact().getEmail());
        emailNotificationParams.getRecipientList().add(toRecipient);


        String exportFileLinkString = getFileLinkString(language);
        String getFileString = getFileString(language);
        String downloadWarningString = getDownloadWarningString(language);

        String contentValue = "Hi " + user.getSharedContact().getFirstName() + ",<br><br>" +
                exportFileLinkString.replace("[FileName]", fileName) + "<br><br>" +
                "<a href=\"" + fileURL + "\">" + getFileString + "</a>" +
                "<br><br>" +
                downloadWarningString +
                "<br><br><br>" +
                "Kind regards,<br>" +
                "The Salesbox team";

        Content stringContent = new Content();
        stringContent.setType(Content.Type.HTML);
        stringContent.setValue(contentValue);
        emailNotificationParams.getContentList().add(stringContent);

        mailSender.sendMail(emailNotificationParams, null);
    }

    private String getSubjectExport(Language language)
    {
        switch (language.getName())
        {
            case "se":
                return "Exporten är klar";
            case "es":
                return "La exportación está hecha";
            case "pt":
                return "A exportação é feita";
            default:
                return "The export done";
        }

    }

    private String getFileString(Language language)
    {
        switch (language.getName())
        {
            case "se":
                return "Hämta filen";
            case "es":
                return "Descargar archivo";
            case "pt":
                return "Obter arquivo";
            default:
                return "Get the file";
        }

    }

    private String getDownloadWarningString(Language language)
    {
        switch (language.getName())
        {
            case "se":
                return "Ladda ned filen inom 24 timmar, efter det tar vi bort filen.";
            case "es":
                return "Descargue el archivo dentro de las próximas 24 horas. Después de eso borramos su archivo.";
            case "pt":
                return "Baixe o arquivo nas próximas 24 horas. Depois disso, apagamos o seu arquivo.";
            default:
                return "Download the file within the next 24 hours. After that we delete your file.";
        }

    }

    private String getFileLinkString(Language language)
    {
        switch (language.getName())
        {
            case "se":
                return "Din exportfil, [FileName], är redo att laddas ned.";
            case "es":
                return "Su archivo de exportación, [FileName], está listo para descargar.";
            case "pt":
                return "Seu arquivo de exportação, [FileName], está pronto para download.";
            default:
                return "Your export file, [FileName], is ready for download.";
        }

    }
}
