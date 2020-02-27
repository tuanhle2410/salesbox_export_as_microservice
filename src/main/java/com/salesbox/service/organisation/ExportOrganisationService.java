package com.salesbox.service.organisation;

import com.google.gson.Gson;
import com.salesbox.common.BaseService;
import com.salesbox.common.multitenant.TenantContext;
import com.salesbox.dao.*;
import com.salesbox.dto.CountDTO;
import com.salesbox.dto.ExportDTO;
import com.salesbox.dto.ExportResultDTO;
import com.salesbox.entity.*;
import com.salesbox.entity.enums.CommunicationType;
import com.salesbox.entity.enums.CustomFieldType;
import com.salesbox.entity.enums.FileType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.exception.ServiceException;
import com.salesbox.file.AmazonS3FileAccessor;
import com.salesbox.file.FileAccessor;
import com.salesbox.localization.ImportExportColumnCode;
import com.salesbox.message.ImportExportMessage;
import com.salesbox.organisation.constant.AccountColumnIndex;
import com.salesbox.organisation.constant.OrganisationConstant;
import com.salesbox.organisation.dto.OrganisationFilterDTO;
import com.salesbox.utils.CommonUtils;
import com.salesbox.utils.ImportExportUtils;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.DateFormatConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExportOrganisationService extends BaseService
{
    @Autowired
    OrganisationDAO organisationDAO;
    @Autowired
    ImportExportUtils importExportUtils;
    @Autowired
    ImportExportHistoryDAO importExportHistoryDAO;
    @Autowired
    OrganisationUserDAO organisationUserDAO;
    @Autowired
    CommunicationOrganisationDAO communicationOrganisationDAO;
    @Autowired
    FileAccessor fileAccessor;
    @Autowired
    Gson gson;
    @Autowired
    OrganisationSearchService organisationSearchService;
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    AmazonS3FileAccessor amazonS3FileAccessor;
    @Autowired
    UnitDAO unitDAO;
    @Autowired
    NoteDAO noteDAO;
    @Autowired
    CustomFieldValueDAO customFieldValueDAO;
    @Autowired
    CustomFieldDAO customFieldDAO;
    @Autowired
    CommunicationHistoryDAO communicationHistoryDAO;

    private static final String EXPORT_OPP_ACC_TEMPLATE_FILE = "Salesbox_AS_Acc.xlsx";


    public ExportResultDTO exportNew(String token) throws IOException
    {
        ExportResultDTO exportResultDTO = new ExportResultDTO();
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);
        long numberAccountList = organisationDAO.countNotDeletedByEnterprise(enterprise);
        if (numberAccountList > 30)
        {
            exportResultDTO.setSendEmail(true);
            exportDataAndSendEmail(user, enterprise);
            return exportResultDTO;
        }
        else
        {
            String fileURL = exportFile(enterprise);
            exportResultDTO.setFileUrl(fileURL);
            return exportResultDTO;

        }
    }

    private String exportFile(Enterprise enterprise) throws IOException
    {

        List<Organisation> organisationList = organisationDAO.findByEnterpriseAndDeletedOrderByName(enterprise, false);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        String sheetName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.ACCOUNT_SHEET_NAME.toString());
        if (sheetName == null)
        {
            sheetName = "Accounts";
        }
        workbook.setSheetName(0, sheetName);

        CellStyle headStyle = importExportUtils.createHeadStyle(workbook);
        createHeaderRow(sheet, headStyle, enterprise);

        List<CommunicationOrganisation> communicationList = communicationOrganisationDAO.findByOrganisation(organisationList);
        List<OrganisationUser> organisationUserList = organisationUserDAO.findByOrganisationIn(organisationList);

        int i = 0;
        int numberOf = 1;
        for (Organisation organisation : organisationList)
        {
            i++;
            Row contentRow = sheet.createRow(i);
            generateValueForRow(organisation, contentRow, communicationList, organisationUserList, numberOf, null, "", false);
            numberOf++;
        }

        StringBuilder preFileName = importExportUtils.createExportFileName(FileType.XLS, OrganisationConstant.PRE_EXPORT_FILE_NAME);

        String finalFileName = enterprise.getSharedOrganisation().getName() + "_" + preFileName.toString();

        ByteArrayOutputStream exportOutputStream = new ByteArrayOutputStream();
        workbook.write(exportOutputStream);
        exportOutputStream.close();

        InputStream exportInputStream = new ByteArrayInputStream(exportOutputStream.toByteArray());

        String fileURL = amazonS3FileAccessor.saveExportFile(finalFileName.replaceAll("\\s+", ""), exportInputStream);
        System.out.println(fileURL);

        return fileURL;

    }

    private void exportDataAndSendEmail(User user, Enterprise enterprise)
    {
        taskExecutor.execute(() -> {
            TenantContext.setCurrentTenant(enterprise.getUuid().toString());
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = transactionManager.getTransaction(def);

            try
            {
                String fileURL = exportFile(enterprise);
                importExportUtils.sendMailWithExportResult(user, fileURL);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


            transactionManager.commit(status);
        });
    }


    public ExportDTO exportOrganisation(String token) throws IOException, ServiceException, EncoderException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        String sheetName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.ACCOUNT_SHEET_NAME.toString());
        if (sheetName == null)
        {
            sheetName = "Accounts";
        }
        workbook.setSheetName(0, sheetName);

        CellStyle headStyle = importExportUtils.createHeadStyle(workbook);
        createHeaderRow(sheet, headStyle, enterprise);


        List<Organisation> organisationList = organisationDAO.findByEnterpriseAndDeletedOrderByName(enterprise, false);
        importExportUtils.checkSizeExport(organisationList.size());

        List<CommunicationOrganisation> communicationList = communicationOrganisationDAO.findByOrganisation(organisationList);
        List<OrganisationUser> organisationUserList = organisationUserDAO.findByOrganisationIn(organisationList);

        int i = 0;
        int numberOf = 1;
        for (Organisation organisation : organisationList)
        {
            i++;
            Row contentRow = sheet.createRow(i);
            generateValueForRow(organisation, contentRow, communicationList, organisationUserList, numberOf, null, "", false);
            numberOf++;
        }

        StringBuilder fileName = importExportUtils.createExportFileName(FileType.XLS, OrganisationConstant.PRE_EXPORT_FILE_NAME);

        ImportExportHistory exportHistory = importExportUtils.generateExportHistory(user, enterprise, fileName, ObjectType.ACCOUNT);
        importExportHistoryDAO.save(exportHistory);

        putResultFileToAmazon(workbook, exportHistory.getUuid().toString());

        ExportDTO exportDTO = new ExportDTO();
        exportDTO.setFileId(exportHistory.getUuid().toString());
        exportDTO.setFileName(fileName.toString());
        exportDTO.setToEmail(user.getUsername());
        StringBuilder subject = importExportUtils.createSubjectExportName(OrganisationConstant.PRE_SUBJECT);
        exportDTO.setSubject(subject.toString());

        return exportDTO;
    }

    @Async
    public void putResultFileToAmazon(Workbook workbook, String filedId) throws IOException, EncoderException
    {
        File exportFile = fileAccessor.selectFileFromFileName(filedId);
        OutputStream exportStream = new FileOutputStream(exportFile);
        workbook.write(exportStream);
        exportStream.close();

        amazonS3FileAccessor.saveDocumentFile(filedId, fileAccessor.selectFileFromFileName(filedId));
    }

    private void generateValueForRow(Organisation organisation, Row contentRow,
                                     List<CommunicationOrganisation> communicationList,
                                     List<OrganisationUser> organisationUserList,
                                     int numberOf, CellStyle cellStyle, String note, boolean isAdvanceSearch)
    {
        Map<UUID, String> uuidValueOfCO = new HashMap<>();
        Map<UUID, Date> uuidDate = new HashMap<>();
        for (CommunicationOrganisation temp : communicationList)
        {
            if (!uuidValueOfCO.containsKey(temp.getOrganisation().getUuid()))
            {
                uuidValueOfCO.put(temp.getOrganisation().getUuid(), temp.getValue());
                uuidDate.put(temp.getOrganisation().getUuid(), temp.getUpdatedDate());
            }
            else
            {
                if (temp.getUpdatedDate().after(uuidDate.get(temp.getOrganisation().getUuid())))
                {
                    uuidValueOfCO.put(temp.getOrganisation().getUuid(), temp.getValue());
                    uuidDate.put(temp.getOrganisation().getUuid(), temp.getUpdatedDate());
                }
            }
        }

        Cell noContentCell = contentRow.createCell(AccountColumnIndex.NUMBER_OF.getIndex());
        noContentCell.setCellValue(String.valueOf(numberOf));
        noContentCell.setCellStyle(cellStyle);

        Cell accountNameContentCell = contentRow.createCell(AccountColumnIndex.ACCOUNT_NAME.getIndex());
        accountNameContentCell.setCellValue(organisation.getName());
        accountNameContentCell.setCellStyle(cellStyle);

        Cell vatContentCell = contentRow.createCell(AccountColumnIndex.VAT.getIndex());
        vatContentCell.setCellValue(organisation.getVatNumber());
        vatContentCell.setCellStyle(cellStyle);

        Cell streetContentCell = contentRow.createCell(AccountColumnIndex.ADDRESS.getIndex());
        streetContentCell.setCellValue(organisation.getStreet());
        streetContentCell.setCellStyle(cellStyle);

        Cell zipCodeContentCell = contentRow.createCell(AccountColumnIndex.ZIP_CODE.getIndex());
        zipCodeContentCell.setCellValue(organisation.getZipCode());
        zipCodeContentCell.setCellStyle(cellStyle);

        Cell cityContentCell = contentRow.createCell(AccountColumnIndex.CITY.getIndex());
        cityContentCell.setCellValue(organisation.getCity());
        cityContentCell.setCellStyle(cellStyle);

        Cell countryContentCell = contentRow.createCell(AccountColumnIndex.COUNTRY.getIndex());
        countryContentCell.setCellValue(organisation.getCountry());
        countryContentCell.setCellStyle(cellStyle);

        Cell regionContentCell = contentRow.createCell(AccountColumnIndex.REGION.getIndex());
        regionContentCell.setCellValue(organisation.getState());
        regionContentCell.setCellStyle(cellStyle);

        Cell phoneContentCell = contentRow.createCell(AccountColumnIndex.PHONE.getIndex());
        phoneContentCell.setCellValue(String.valueOf(getPhoneList(organisation, communicationList)));
        phoneContentCell.setCellStyle(cellStyle);

        Cell emailContentCell = contentRow.createCell(AccountColumnIndex.EMAIL.getIndex());
        emailContentCell.setCellValue(String.valueOf(getEmailList(organisation, communicationList)));
        emailContentCell.setCellStyle(cellStyle);

        Cell webContentCell = contentRow.createCell(AccountColumnIndex.WEB.getIndex());
        webContentCell.setCellValue(organisation.getWeb());
        webContentCell.setCellStyle(cellStyle);

        Cell typeContentCell = contentRow.createCell(AccountColumnIndex.TYPE.getIndex());
        typeContentCell.setCellStyle(cellStyle);
        if (organisation.getType() != null)
        {
            typeContentCell.setCellValue(organisation.getType().getName());
        }

        Cell industryContentCell = contentRow.createCell(AccountColumnIndex.INDUSTRY.getIndex());
        industryContentCell.setCellStyle(cellStyle);
        if (organisation.getIndustry() != null)
        {
            industryContentCell.setCellValue(organisation.getIndustry().getName());
        }

        Cell sizeContentCell = contentRow.createCell(AccountColumnIndex.SIZE.getIndex());
        sizeContentCell.setCellStyle(cellStyle);
        if (organisation.getSize() != null)
        {
            sizeContentCell.setCellValue(organisation.getSize().getName());
        }

        Cell budgetContentCell = contentRow.createCell(AccountColumnIndex.BUDGET.getIndex());
        budgetContentCell.setCellStyle(cellStyle);
        if (organisation.getBudget() != null)
        {
            budgetContentCell.setCellValue(organisation.getBudget());
        }

        Cell appointmentContentCell = contentRow.createCell(AccountColumnIndex.APPOINTMENT_GOAL.getIndex());
        appointmentContentCell.setCellStyle(cellStyle);
        appointmentContentCell.setCellValue(organisation.getNumberActiveMeeting());

        Cell accountTeamCell = contentRow.createCell(AccountColumnIndex.ACCOUNT_TEAM.getIndex());
        accountTeamCell.setCellStyle(cellStyle);
        accountTeamCell.setCellValue(String.valueOf(getAccountTeam(organisation, organisationUserList)));

        if (isAdvanceSearch)
        {
            //last note
            Cell lastNoteCell = contentRow.createCell(AccountColumnIndex.LAST_NOTE.getIndex());
            lastNoteCell.setCellStyle(cellStyle);
            lastNoteCell.setCellValue(note);

            //lastest communication
            Cell communicationCell = contentRow.createCell(AccountColumnIndex.LAST_COMMUNICATION.getIndex());
            communicationCell.setCellStyle(cellStyle);
            String accountLastCommunication = "";
            CommunicationHistory communicationHistory = communicationHistoryDAO.findLatestByOrganisation(organisation);
            if (communicationHistory != null)
            {
                accountLastCommunication = communicationHistory.getType() + " " + communicationHistory.getUser().getSharedContact().buildFullName() + " " + communicationHistory.getStartDate();
            }
            communicationCell.setCellValue(accountLastCommunication);

            //closed sales
            Cell closedSalesCell = contentRow.createCell(AccountColumnIndex.CLOSED_SALES.getIndex());
            closedSalesCell.setCellStyle(cellStyle);
            closedSalesCell.setCellValue(organisation.getOrderIntake());

            //Median deal size
            Cell mdealSizeCell = contentRow.createCell(AccountColumnIndex.MEDIAN_DEAL_SIZE.getIndex());
            mdealSizeCell.setCellStyle(cellStyle);
            mdealSizeCell.setCellValue(organisation.getMedianDealSize());

            //Median deal time
            Cell mDealTimeCell = contentRow.createCell(AccountColumnIndex.MEDIAN_DEAL_TIME.getIndex());
            mDealTimeCell.setCellStyle(cellStyle);
            mDealTimeCell.setCellValue(Math.round(organisation.getMedianDealTime() / 86400000));

            //Gross pipe
            Cell grosspipeCell = contentRow.createCell(AccountColumnIndex.GROSS_PINE.getIndex());
            grosspipeCell.setCellStyle(cellStyle);
            grosspipeCell.setCellValue(organisation.getGrossPipeline());

            //Weighted pipe
            Cell weightedCell = contentRow.createCell(AccountColumnIndex.WEIGHTED_PINE.getIndex());
            weightedCell.setCellStyle(cellStyle);
            weightedCell.setCellValue(organisation.getNetPipeline());

            //Profit
            Cell profitCell = contentRow.createCell(AccountColumnIndex.PROFIT.getIndex());
            profitCell.setCellStyle(cellStyle);
            profitCell.setCellValue(organisation.getWonProfit());
        }
    }

    private void generateDataForRow(Organisation organisation,
                                    Row contentRow, Workbook workbook, String note,
                                    Map<Integer, UUID> indexCustomFieldMap,
                                    Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap,
                                    CellStyle cellStyle, int numberOf,
                                    List<CommunicationOrganisation> communicationList,
                                    List<OrganisationUser> organisationUserList)
    {
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(cellStyle);
        DataFormat poiFormat = workbook.createDataFormat();
        String excelFormatPattern = DateFormatConverter.convert(Locale.ENGLISH, "mm/dd/yyyy");
        dateStyle.setDataFormat(poiFormat.getFormat(excelFormatPattern));

        int rowNumber = contentRow.getRowNum();

        generateValueForRow(organisation, contentRow, communicationList, organisationUserList, numberOf, cellStyle, note, true);


        int index = AccountColumnIndex.BEGIN_CUSTOM_FIELD.getIndex();
        do
        {
            UUID customFieldId = indexCustomFieldMap.get(index - AccountColumnIndex.BEGIN_CUSTOM_FIELD.getIndex());
            List<CustomFieldValue> customFieldValueList = customFieldIdCustomFieldValueMap.get(customFieldId);
            boolean hasValue = false;
            Cell customFieldCell = contentRow.createCell(index);
            if (indexCustomFieldMap.size() > 0)
            {
                customFieldCell.setCellStyle(cellStyle);
            }
            String dropDownValue = "";

            if (customFieldValueList != null)
            {
                for (CustomFieldValue customFieldValue : customFieldValueList)
                {
                    if (customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.NUMBER.toString())
                            && !customFieldValue.getValue().isEmpty())
                    {
                        if (NumberUtils.isNumber(customFieldValue.getValue()))
                        {
                            customFieldCell.setCellType(CellType.NUMERIC);
                            customFieldCell.setCellValue(customFieldValue.getValue());
                            hasValue = true;
                        }
                        else
                        {
                            customFieldCell.setCellType(CellType.STRING);
                            customFieldCell.setCellStyle(cellStyle);
                            customFieldCell.setCellValue(customFieldValue.getValue());
                            hasValue = true;
                        }
                    }

                    if (customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.TEXT.toString())
                            || customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.TEXT_BOX.toString())
                            || customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.URL.toString())
                            || customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.DATE.toString())
                    )
                    {
                        customFieldCell.setCellType(CellType.STRING);
                        customFieldCell.setCellStyle(cellStyle);
                        customFieldCell.setCellValue(customFieldValue.getValue());
                        hasValue = true;
                    }

                    if (customFieldValue.getIsChecked() &&
                            (customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.CHECK_BOXES.toString())
                                    || customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.DROPDOWN.toString())
                            )
                    )
                    {

                        if (dropDownValue.length() > 0)
                        {
                            dropDownValue += "," + customFieldValue.getValue();
                        }
                        else
                        {
                            dropDownValue += customFieldValue.getValue();
                        }
                        customFieldCell.setCellType(CellType.STRING);
                        customFieldCell.setCellStyle(cellStyle);
                        hasValue = true;
                    }

                    if (customFieldValue.getCustomField().getFieldType() == CustomFieldType.PRODUCT_TAG) {
                        customFieldCell.setCellType(CellType.STRING);
                        customFieldCell.setCellStyle(cellStyle);
                        String productTag = CommonUtils.toTagFormat(customFieldValue.getProduct().getName());
                        dropDownValue += StringUtils.isEmpty(dropDownValue)
                                ? productTag
                                : ", " + productTag;
                        hasValue = true;
                    }
                }
            }
            if (dropDownValue.length() > 0)
            {
                customFieldCell.setCellValue(dropDownValue);
            }
            if (!hasValue)
            {
                customFieldCell.setCellType(CellType.STRING);
                customFieldCell.setCellValue("");
            }

            index++;
        } while (index < AccountColumnIndex.BEGIN_CUSTOM_FIELD.getIndex() + indexCustomFieldMap.size());
    }


    private StringBuilder getPhoneList(Organisation organisation, List<CommunicationOrganisation> communicationList)
    {
        StringBuilder communicationStr = new StringBuilder();
        for (CommunicationOrganisation communication : communicationList)
        {
            if (communication.getOrganisation().getUuid().compareTo(organisation.getUuid()) == 0)
            {
                if (communication.getType().compareTo(CommunicationType.PHONE_HEAD_QUARTER) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_SUBSIDIARY) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_DEPARTMENT) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_UNIT) == 0)
                {
                    if (communication.getMain())
                    {
                        if (communicationStr.toString().length() > 0)
                        {
                            communicationStr.insert(0, ", ");
                        }
                        communicationStr.insert(0, communication.getValue());

                    }
                    else
                    {
                        importExportUtils.appendMessage(communicationStr, communication.getValue());
                    }

                }

            }
        }
        return communicationStr;
    }

    private StringBuilder getEmailList(Organisation organisation, List<CommunicationOrganisation> communicationList)
    {
        StringBuilder communicationStr = new StringBuilder();
        for (CommunicationOrganisation communication : communicationList)
        {
            if (communication.getOrganisation().getUuid().compareTo(organisation.getUuid()) == 0)
            {
                if (communication.getType().compareTo(CommunicationType.EMAIL_HEAD_QUARTER) == 0
                        || communication.getType().compareTo(CommunicationType.EMAIL_SUBSIDIARY) == 0
                        || communication.getType().compareTo(CommunicationType.EMAIL_DEPARTMENT) == 0
                        || communication.getType().compareTo(CommunicationType.EMAIL_UNIT) == 0)
                {
                    if (communication.getMain())
                    {
                        if (communicationStr.toString().length() > 0)
                        {
                            communicationStr.insert(0, ", ");
                        }
                        communicationStr.insert(0, communication.getValue());

                    }
                    else
                    {
                        importExportUtils.appendMessage(communicationStr, communication.getValue());
                    }

                }

            }
        }
        return communicationStr;
    }


    private StringBuilder getAccountTeam(Organisation organisation, List<OrganisationUser> organisationUserList)
    {
        StringBuilder accountTeam = new StringBuilder("");
        for (OrganisationUser organisationUser : organisationUserList)
        {
            if (organisationUser.getOrganisation().getUuid().compareTo(organisation.getUuid()) == 0)
            {
                importExportUtils.appendMessage(accountTeam, organisationUser.getUser().getSharedContact().buildFullName());
            }
        }
        return accountTeam;
    }


    private void createHeaderRow(HSSFSheet sheet, CellStyle headerStyle, Enterprise enterprise)
    {
        HashMap<String, String> hashMap = importExportUtils.getStringHashMapNameColumnExcel(enterprise);
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(OrganisationConstant.HEIGHT_ROW);

        Cell accountIDTitleCell = titleRow.createCell(AccountColumnIndex.NUMBER_OF.getIndex());
        accountIDTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.ACCOUNT_ID.toString()));
        accountIDTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.NUMBER_OF.getIndex(), OrganisationConstant.LARGE_WIDTH);

        Cell accountNameTitleCell = titleRow.createCell(AccountColumnIndex.ACCOUNT_NAME.getIndex());
        accountNameTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.ACCOUNT_NAME.toString()));
        accountNameTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.ACCOUNT_NAME.getIndex(), OrganisationConstant.LARGE_WIDTH);

        Cell vatTitleCell = titleRow.createCell(AccountColumnIndex.VAT.getIndex());
        vatTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.VAT.toString()));
        vatTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.VAT.getIndex(), OrganisationConstant.LARGE_WIDTH);

        Cell streetTitleCell = titleRow.createCell(AccountColumnIndex.ADDRESS.getIndex());
        streetTitleCell.setCellStyle(headerStyle);
        streetTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.ADDRESS.toString()));
        sheet.setColumnWidth(AccountColumnIndex.ADDRESS.getIndex(), OrganisationConstant.LARGE_WIDTH);

        Cell zipCodeTitleCell = titleRow.createCell(AccountColumnIndex.ZIP_CODE.getIndex());
        zipCodeTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.ZIP_CODE.toString()));
        zipCodeTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.ZIP_CODE.getIndex(), OrganisationConstant.MEDIUM_WIDTH);

        Cell cityTitleCell = titleRow.createCell(AccountColumnIndex.CITY.getIndex());
        cityTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.CITY.toString()));
        cityTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.CITY.getIndex(), OrganisationConstant.SMALL_WIDTH);

        Cell regionTitleCell = titleRow.createCell(AccountColumnIndex.REGION.getIndex());
        regionTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.REGION.toString()));
        regionTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.REGION.getIndex(), OrganisationConstant.SMALL_WIDTH);

        Cell countryTitleCell = titleRow.createCell(AccountColumnIndex.COUNTRY.getIndex());
        countryTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.COUNTRY.toString()));
        countryTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.COUNTRY.getIndex(), OrganisationConstant.SMALL_WIDTH);

        Cell phoneTitleCell = titleRow.createCell(AccountColumnIndex.PHONE.getIndex());
        phoneTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.PHONE.toString()));
        phoneTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.PHONE.getIndex(), OrganisationConstant.SMALL_WIDTH);

        Cell emailTitleCell = titleRow.createCell(AccountColumnIndex.EMAIL.getIndex());
        emailTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.EMAIL.toString()));
        emailTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.EMAIL.getIndex(), OrganisationConstant.LARGE_WIDTH);

        Cell webTitleCell = titleRow.createCell(AccountColumnIndex.WEB.getIndex());
        webTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.WEB.toString()));
        webTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.WEB.getIndex(), OrganisationConstant.MEDIUM_WIDTH);

        Cell typeTitleCell = titleRow.createCell(AccountColumnIndex.TYPE.getIndex());
        typeTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.ORGANISATION_TYPE.toString()));
        typeTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.TYPE.getIndex(), OrganisationConstant.MEDIUM_WIDTH);

        Cell industryTitleCell = titleRow.createCell(AccountColumnIndex.INDUSTRY.getIndex());
        industryTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.INDUSTRY.toString()));
        industryTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.INDUSTRY.getIndex(), OrganisationConstant.LARGE_WIDTH);


        Cell sizeTitleCell = titleRow.createCell(AccountColumnIndex.SIZE.getIndex());
        sizeTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.SIZE.toString()));
        sizeTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.SIZE.getIndex(), OrganisationConstant.SMALL_WIDTH);

        Cell budgetTitleCell = titleRow.createCell(AccountColumnIndex.BUDGET.getIndex());
        budgetTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.BUDGET.toString()));
        budgetTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.BUDGET.getIndex(), OrganisationConstant.SMALL_WIDTH);

        Cell appointmentTitleCell = titleRow.createCell(AccountColumnIndex.APPOINTMENT_GOAL.getIndex());
        appointmentTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.APPOINTMENT_GOAL.toString()));
        appointmentTitleCell.setCellStyle(headerStyle);
        sheet.autoSizeColumn(AccountColumnIndex.APPOINTMENT_GOAL.getIndex());

        Cell accountTeamTitleCell = titleRow.createCell(AccountColumnIndex.ACCOUNT_TEAM.getIndex());
        accountTeamTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.ACCOUNT_TEAM.toString()));
        accountTeamTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(AccountColumnIndex.ACCOUNT_TEAM.getIndex(), OrganisationConstant.LARGE_WIDTH);
    }

    public void exportDirectFile(String token, HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        String sheetName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.ACCOUNT_SHEET_NAME.toString());
        if (sheetName == null)
        {
            sheetName = "Accounts";
        }
        workbook.setSheetName(0, sheetName);

        CellStyle headStyle = importExportUtils.createHeadStyle(workbook);
        createHeaderRow(sheet, headStyle, enterprise);


        List<Organisation> organisationList = organisationDAO.findByEnterpriseAndDeletedOrderByName(enterprise, false);
        importExportUtils.checkSizeExport(organisationList.size());

        List<CommunicationOrganisation> communicationList = communicationOrganisationDAO.findByOrganisation(organisationList);
        List<OrganisationUser> organisationUserList = organisationUserDAO.findByOrganisationIn(organisationList);

        int i = 0;
        int numberOf = 1;
        for (Organisation organisation : organisationList)
        {
            i++;
            Row contentRow = sheet.createRow(i);
            generateValueForRow(organisation, contentRow, communicationList, organisationUserList, numberOf, null, "", false);
            numberOf++;
        }

        StringBuilder fileName = importExportUtils.createExportFileName(FileType.XLS, OrganisationConstant.PRE_EXPORT_FILE_NAME);

        ImportExportHistory exportHistory = importExportUtils.generateExportHistory(user, enterprise, fileName, ObjectType.ACCOUNT);
        importExportHistoryDAO.save(exportHistory);

        String mimeType = "application/excel";
        response.setContentType(mimeType);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);

        OutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        outputStream.close();
    }

    public ExportResultDTO exportAdvancedSearch(String token, String filterDTO,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws ServiceException, IOException, InvalidFormatException
    {

        OrganisationFilterDTO organisationFilterDTO = gson.fromJson(filterDTO, OrganisationFilterDTO.class);

        CountDTO countDTO = organisationSearchService.countOrganisationRecords(token, UUID.randomUUID().toString(), organisationFilterDTO);

        User user = getUserFromToken(token);
        Enterprise enterprise = getEnterpriseFromToken(token);

        ExportResultDTO exportResultDTO = new ExportResultDTO();
        if (countDTO.getCount() > 30)
        {
            exportResultDTO.setSendEmail(true);
            exportASAndSendEmail(enterprise, token, user, organisationFilterDTO);
            return exportResultDTO;
        }
        else
        {
            String fileUrl = exportNewAS(token, user, organisationFilterDTO);
            exportResultDTO.setFileUrl(fileUrl);
            return exportResultDTO;
        }
    }

    private String exportNewAS(String token, User user, OrganisationFilterDTO organisationFilterDTO) throws IOException, InvalidFormatException
    {

        Enterprise enterprise = getEnterpriseFromToken(token);
        List<UUID> uuidList = new ArrayList<>();
        try
        {
            uuidList = organisationSearchService.getUUIDList(token, 0, 1000000, organisationFilterDTO);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Workbook workbook = new HSSFWorkbook();
        List<Organisation> organisationListTemp = new ArrayList<>();
        List<Organisation> organisationList = new ArrayList<>();

        if (uuidList.size() > 0)
        {
            organisationListTemp = organisationDAO.findByUuidIn(uuidList);
        }

        for (UUID uuid : uuidList)
        {
            Organisation organisation = findOrganisationDTO(organisationListTemp, uuid);
            organisationList.add(organisation);
        }

        List<CommunicationOrganisation> communicationList = communicationOrganisationDAO.findByOrganisation(organisationList);
        List<OrganisationUser> organisationUserList = organisationUserDAO.findByOrganisationIn(organisationList);

        List<UUID> organisationListUuid = new ArrayList<>();
        for (Organisation temp : organisationList)
        {
            organisationListUuid.add(temp.getUuid());
        }

        Map<UUID, String> AccountNoteMap = getAccountNoteMap(organisationListUuid);

        Map<UUID, Map<UUID, List<CustomFieldValue>>> leadCustomFieldMap = getAccountCustomFieldMap(organisationListUuid);
        List<CustomField> customFieldList = customFieldDAO.findByObjectTypeOrderByPositionAsc(enterprise, ObjectType.ACCOUNT);

        File file = new ClassPathResource("/excel/" + EXPORT_OPP_ACC_TEMPLATE_FILE).getFile();
        FileInputStream inputStream = new FileInputStream(file);

        workbook = WorkbookFactory.create(inputStream);
        Sheet accSheet = workbook.getSheetAt(0);

        //gen title
        Map<Integer, UUID> indexCustomFieldMap = new HashMap<>();
        generateTitleRow(accSheet, organisationFilterDTO, enterprise);
        populateHeaderRowForCustomField(customFieldList, accSheet, indexCustomFieldMap);

        CellStyle evenStyle = createEvenStyle(workbook);
        CellStyle oddStyle = workbook.createCellStyle();

        oddStyle.cloneStyleFrom(evenStyle);
        oddStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        oddStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int i = 2;
        int numberOf = 1;
        for (Organisation organisation : organisationList)
        {
            UUID uuid = organisation.getUuid();
            Row contentRow = accSheet.createRow(i);

            Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap = leadCustomFieldMap.get(uuid);
            if (customFieldIdCustomFieldValueMap == null)
            {
                customFieldIdCustomFieldValueMap = new HashMap<>();
            }

            CellStyle cellStyle = organisationList.indexOf(organisation) % 2 == 0 ? evenStyle : oddStyle;
            generateDataForRow(organisation, contentRow, workbook, AccountNoteMap.get(uuid),
                    indexCustomFieldMap, customFieldIdCustomFieldValueMap, cellStyle, numberOf, communicationList, organisationUserList);
            i++;
            numberOf++;
        }

        StringBuilder preFileName = importExportUtils.createExportFileName(FileType.XLSX, OrganisationConstant.PRE_EXPORT_FILE_NAME);
        String finalFileName = enterprise.getSharedOrganisation().getName() + "_AS_" + preFileName.toString();

        ByteArrayOutputStream exportOutputStream = new ByteArrayOutputStream();
        workbook.write(exportOutputStream);
        exportOutputStream.close();

        InputStream exportInputStream = new ByteArrayInputStream(exportOutputStream.toByteArray());

        String fileURL = amazonS3FileAccessor.saveExportFile(finalFileName.replaceAll("\\s+", ""), exportInputStream);
        System.out.println(fileURL);

        return fileURL;

    }

    private void exportASAndSendEmail(Enterprise enterprise, String token, User user, OrganisationFilterDTO organisationFilterDTO)
    {
        taskExecutor.execute(() -> {
            TenantContext.setCurrentTenant(enterprise.getUuid().toString());
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = transactionManager.getTransaction(def);


            String fileURL = null;
            try
            {
                fileURL = exportNewAS(token, user, organisationFilterDTO);
                importExportUtils.sendMailWithExportResult(user, fileURL);

            }
            catch (IOException | InvalidFormatException e)
            {
                e.printStackTrace();
            }

            transactionManager.commit(status);
        });
    }

    private Organisation findOrganisationDTO(List<Organisation> organisationDTOList, UUID uuid)
    {
        for (Organisation organisationDTO : organisationDTOList)
        {
            if (organisationDTO.getUuid().equals(uuid))
            {
                return organisationDTO;
            }
        }
        return null;
    }

    //gen header for custom field
    private void populateHeaderRowForCustomField(List<CustomField> customFieldList, Sheet leadSheet,
                                                 Map<Integer, UUID> indexCustomFieldMap)
    {
        Row headerRow = leadSheet.getRow(1);
        int index = 0;
        int nextHeaderIndex = AccountColumnIndex.BEGIN_CUSTOM_FIELD.getIndex();
        for (CustomField customField : customFieldList)
        {
            Cell cell = headerRow.createCell(nextHeaderIndex);
            cell.setCellStyle(headerRow.getCell(0).getCellStyle());
            cell.setCellValue(customField.getTitle());
            indexCustomFieldMap.put(index, customField.getUuid());
            index++;
            nextHeaderIndex++;
        }
    }


    private CellStyle createEvenStyle(Workbook workbook)
    {
        CellStyle evenStyle = workbook.createCellStyle();

        evenStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        evenStyle.setBorderBottom(BorderStyle.THIN);
        evenStyle.setBorderLeft(BorderStyle.THIN);
        evenStyle.setBorderRight(BorderStyle.THIN);
        evenStyle.setBorderTop(BorderStyle.THIN);
        evenStyle.setWrapText(true);
        return evenStyle;
    }

    private void generateTitleRow(Sheet taskSheet, OrganisationFilterDTO organisationFilterDTO, Enterprise enterprise)
    {
        Row titleLeadRow = taskSheet.getRow(0);

        Cell ownerCell = titleLeadRow.getCell(3);
        Cell startCell = titleLeadRow.getCell(4);
        Cell endCell = titleLeadRow.getCell(5);

        String roleName = "";

        if (organisationFilterDTO.getRoleFilterType().equals("Person"))
        {
            User user = userDAO.findOne(UUID.fromString(organisationFilterDTO.getRoleFilterValue()));
            if (user != null)
            {
                roleName = user.getSharedContact().getFirstName() + " " + user.getSharedContact().getLastName();
            }
        }
        else if (organisationFilterDTO.getRoleFilterType().equals("Unit"))
        {
            Unit unit = unitDAO.findOne(UUID.fromString(organisationFilterDTO.getRoleFilterValue()));
            if (unit != null)
            {
                roleName = unit.getName();
            }
        }
        else
        {
            roleName = enterprise.getSharedOrganisation().getName();
        }
        ownerCell.setCellValue(roleName);
    }

    private Map<UUID, String> getAccountNoteMap(List<UUID> uuidList)
    {
        Map<UUID, String> accountNoteMap = new HashMap<>();
        if (uuidList.size() > 0)
        {
            List<Object[]> noteList = noteDAO.findLatestByAccountIdIn(new HashSet<UUID>(uuidList));
            if (noteList.size() > 0)
            {
                for (Object[] objects : noteList)
                {
                    Organisation organisation = (Organisation) objects[0];
                    UUID prospectId = organisation.getUuid();
                    if (accountNoteMap.get(prospectId) == null)
                    {
                        accountNoteMap.put(prospectId, objects[1].toString() + ": " + objects[2].toString());
                    }
                }
            }
        }
        return accountNoteMap;
    }

    private Map<UUID, Map<UUID, List<CustomFieldValue>>> getAccountCustomFieldMap(List<UUID> accountIdList)
    {
        Map<UUID, Map<UUID, List<CustomFieldValue>>> accountCustomFieldMap = new HashMap<>();
        for (UUID uuid : accountIdList)
        {
            accountCustomFieldMap.put(uuid, new HashMap<UUID, List<CustomFieldValue>>());
        }

        List<CustomFieldValue> customFieldValueList = customFieldValueDAO.findByCustomFieldAndObjectIdIn(accountIdList);
        for (CustomFieldValue customFieldValue : customFieldValueList)
        {
            UUID accountId = customFieldValue.getObjectId();
            UUID customFieldId = customFieldValue.getCustomField().getUuid();
            if (accountCustomFieldMap.get(accountId).get(customFieldId) != null)
            {
                accountCustomFieldMap.get(accountId).get(customFieldId).add(customFieldValue);
            }
            else
            {
                List<CustomFieldValue> newCustomFieldValueList = new ArrayList<>();
                newCustomFieldValueList.add(customFieldValue);
                accountCustomFieldMap.get(accountId).put(customFieldId, newCustomFieldValueList);
            }
        }

        return accountCustomFieldMap;
    }

}
