package com.salesbox.service.contact;

import com.google.gson.Gson;
import com.salesbox.common.BaseService;
import com.salesbox.common.multitenant.TenantContext;
import com.salesbox.contact.constant.ContactColumnIndex;
import com.salesbox.contact.constant.ContactConstant;
import com.salesbox.dao.*;
import com.salesbox.dto.ContactFilterDTO;
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
import com.salesbox.utils.CommonUtils;
import com.salesbox.utils.ImportExportUtils;
import org.apache.commons.codec.EncoderException;
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

/**
 * Created by: hunglv
 * Date: 9/29/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExportFileContactService extends BaseService
{
    @Autowired
    ImportExportUtils importExportUtils;
    @Autowired
    ContactDAO contactDAO;
    @Autowired
    ImportExportHistoryDAO importExportHistoryDAO;
    @Autowired
    ContactUserDAO contactUserDAO;
    @Autowired
    CommunicationDAO communicationDAO;
    @Autowired
    Gson gson;
    @Autowired
    ContactSearchService contactSearchService;
    @Autowired
    SyncEnterpriseSessionDAO syncEnterpriseSessionDAO;
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    UnitDAO unitDAO;
    @Autowired
    CustomFieldDAO customFieldDAO;
    @Autowired
    CustomFieldValueDAO customFieldValueDAO;
    @Autowired
    NoteDAO noteDAO;
    @Autowired
    CommunicationHistoryDAO communicationHistoryDAO;
    /*@Autowired
    IBMObjectStorageFileAccessor ibmObjectStorageFileAccessor;*/

    @Autowired
    AmazonS3FileAccessor amazonS3FileAccessor;

    @Autowired
    FileAccessor fileAccessor;

    private static final String EXPORT_CONTACT_TEMPLATE_FILE = "Salesbox_AS_Contacts.xlsx";

    public ExportResultDTO exportNew(String token) throws IOException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);


        ExportResultDTO exportResultDTO = new ExportResultDTO();

        long numberContactList = contactDAO.countByEnterprise(enterprise);
        if (numberContactList > 30)
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

    private String exportFile(Enterprise enterprise) throws IOException
    {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        String sheetName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.CONTACT_SHEET_NAME.toString());
        if (sheetName == null)
        {
            sheetName = "Contacts";
        }
        workbook.setSheetName(0, sheetName);

        CellStyle headStyle = importExportUtils.createHeadStyle(workbook);
        createHeaderRow(sheet, headStyle, enterprise);

        List<Contact> contactList = contactDAO.findByEnterpriseAndDeleted(enterprise, false);

        List<Communication> communicationList = communicationDAO.findByContactIn(contactList);
        List<ContactUser> contactUserList = contactUserDAO.findByContactIn(contactList);

        int i = 0;
        int numberOf = 1;

        for (Contact contact : contactList)
        {
            i++;
            Row contentRow = sheet.createRow(i);
            generateValueForRow(contact, contentRow, communicationList, contactUserList, numberOf, null, null, false);
            numberOf++;
        }

        StringBuilder preFileName = importExportUtils.createExportFileName(FileType.XLS, ContactConstant.PRE_EXPORT_FILE_NAME);
        String finalFileName = enterprise.getSharedOrganisation().getName() + "_" + preFileName.toString();

        ByteArrayOutputStream exportOutputStream = new ByteArrayOutputStream();
        workbook.write(exportOutputStream);
        exportOutputStream.close();

        InputStream exportInputStream = new ByteArrayInputStream(exportOutputStream.toByteArray());

        String fileURL = amazonS3FileAccessor.saveExportFile(finalFileName.replaceAll("\\s+", ""), exportInputStream);
        System.out.println(fileURL);

        return fileURL;

    }


    public ExportDTO exportContact(String token) throws IOException, ServiceException, EncoderException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        String sheetName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.CONTACT_SHEET_NAME.toString());
        if (sheetName == null)
        {
            sheetName = "Contacts";
        }
        workbook.setSheetName(0, sheetName);

        CellStyle headStyle = importExportUtils.createHeadStyle(workbook);
        createHeaderRow(sheet, headStyle, enterprise);

        List<Contact> contactList = contactDAO.findByEnterpriseAndDeleted(enterprise, false);

        importExportUtils.checkSizeExport(contactList.size());

        List<Communication> communicationList = communicationDAO.findByContactIn(contactList);
        List<ContactUser> contactUserList = contactUserDAO.findByContactIn(contactList);

        int i = 0;
        int numberOf = 1;

        for (Contact contact : contactList)
        {
            i++;
            Row contentRow = sheet.createRow(i);
            generateValueForRow(contact, contentRow, communicationList, contactUserList, numberOf, null, null, false);
            numberOf++;
        }

        StringBuilder fileName = importExportUtils.createExportFileName(FileType.XLS, ContactConstant.PRE_EXPORT_FILE_NAME);

        User user = getUserFromToken(token);
        ImportExportHistory exportHistory = importExportUtils.generateExportHistory(user, enterprise, fileName, ObjectType.CONTACT);
        importExportHistoryDAO.save(exportHistory);

        putResultFileToAmazon(workbook, exportHistory.getUuid().toString());

        ExportDTO exportDTO = new ExportDTO();
        exportDTO.setFileName(fileName.toString());
        exportDTO.setFileId(exportHistory.getUuid().toString());

        exportDTO.setToEmail(user.getUsername());
        StringBuilder subject = importExportUtils.createSubjectExportName(ContactConstant.PRE_SUBJECT);
        exportDTO.setSubject(subject.toString());

        return exportDTO;
    }

    @Async
    public void putResultFileToAmazon(Workbook workbook, String fileId) throws IOException, EncoderException
    {
        File exportFile = fileAccessor.selectFileFromFileName(fileId);
        OutputStream exportStream = new FileOutputStream(exportFile);
        workbook.write(exportStream);
        exportStream.close();

        //ibmObjectStorageFileAccessor.saveDocumentFile(fileId, fileAccessor.selectFileFromFileName(fileId));

        amazonS3FileAccessor.saveDocumentFile(fileId, fileAccessor.selectFileFromFileName(fileId));
    }

    private void generateValueForRow(Contact contact, Row row,
                                     List<Communication> communicationList, List<ContactUser> contactUserList,
                                     int numberOf, CellStyle cellStyle, String note, boolean isAdvanceSearch)
    {
        Cell numberOfNameCell = row.createCell(ContactColumnIndex.NO.getIndex());
        numberOfNameCell.setCellValue(String.valueOf(numberOf));
        numberOfNameCell.setCellStyle(cellStyle);

        Cell firstNameCell = row.createCell(ContactColumnIndex.FIRST_NAME.getIndex());
        firstNameCell.setCellValue(contact.getFirstName());
        firstNameCell.setCellStyle(cellStyle);

        Cell lastNameCell = row.createCell(ContactColumnIndex.LAST_NAME.getIndex());
        lastNameCell.setCellValue(contact.getLastName());
        lastNameCell.setCellStyle(cellStyle);

        Cell accountEmailCell = row.createCell(ContactColumnIndex.ACCOUNT_EMAIL.getIndex());
        accountEmailCell.setCellStyle(cellStyle);
        if (contact.getOrganisation() != null)
        {
            accountEmailCell.setCellValue(contact.getOrganisation().getName());
        }

        Cell streetCell = row.createCell(ContactColumnIndex.ADDRESS.getIndex());
        streetCell.setCellValue(contact.getStreet());
        streetCell.setCellStyle(cellStyle);

        Cell zipCodeCell = row.createCell(ContactColumnIndex.ZIP_CODE.getIndex());
        zipCodeCell.setCellValue(contact.getZipCode());
        zipCodeCell.setCellStyle(cellStyle);

        Cell cityCell = row.createCell(ContactColumnIndex.CITY.getIndex());
        cityCell.setCellValue(contact.getCity());
        cityCell.setCellStyle(cellStyle);

        Cell regionCell = row.createCell(ContactColumnIndex.REGION.getIndex());
        regionCell.setCellValue(contact.getRegion());
        regionCell.setCellStyle(cellStyle);

        Cell countryCell = row.createCell(ContactColumnIndex.COUNTRY.getIndex());
        countryCell.setCellValue(contact.getCountry());
        countryCell.setCellStyle(cellStyle);

        Cell titleCell = row.createCell(ContactColumnIndex.TITLE.getIndex());
        titleCell.setCellValue(contact.getTitle());
        titleCell.setCellStyle(cellStyle);

        Cell phoneCell = row.createCell(ContactColumnIndex.PHONE.getIndex());
        phoneCell.setCellValue(String.valueOf(getPhoneList(contact, communicationList)));
        phoneCell.setCellStyle(cellStyle);

        Cell emailCell = row.createCell(ContactColumnIndex.EMAIL.getIndex());
        emailCell.setCellValue(String.valueOf(getEmailList(contact, communicationList)));
        emailCell.setCellStyle(cellStyle);

        Cell typeCell = row.createCell(ContactColumnIndex.TYPE.getIndex());
        typeCell.setCellStyle(cellStyle);
        if (contact.getType() != null)
        {
            typeCell.setCellValue(contact.getType().getName());
        }

        Cell industryCell = row.createCell(ContactColumnIndex.INDUSTRY.getIndex());
        industryCell.setCellStyle(cellStyle);
        if (contact.getIndustry() != null)
        {
            industryCell.setCellValue(contact.getIndustry().getName());
        }

        Cell relationCell = row.createCell(ContactColumnIndex.RELATION.getIndex());
        relationCell.setCellStyle(cellStyle);
        if (contact.getRelation() != null)
        {
            relationCell.setCellValue(contact.getRelation().getName());
        }

        Cell relationshipCell = row.createCell(ContactColumnIndex.RELATIONSHIP.getIndex());
        relationshipCell.setCellStyle(cellStyle);
        if (contact.getRelationship() != null)
        {
            if (contact.getRelationship().getExtension() == 0)
            {
                relationshipCell.setCellValue("Neutral");
            }
            else if (contact.getRelationship().getExtension() == 1)
            {
                relationshipCell.setCellValue("Good");
            }
            else
            {
                relationshipCell.setCellValue("Bad");
            }

        }

        Cell discProfileCell = row.createCell(ContactColumnIndex.DISC_PROFILE.getIndex());
        discProfileCell.setCellStyle(cellStyle);
        if (contact.getDiscProfile() != null)
        {
            discProfileCell.setCellValue(contact.getDiscProfile().toString());
        }

        Cell contactTeamCell = row.createCell(ContactColumnIndex.CONTACT_TEAM.getIndex());
        contactTeamCell.setCellStyle(cellStyle);
        contactTeamCell.setCellValue(String.valueOf(getContactTeam(contact, contactUserList)));

        if (isAdvanceSearch)
        {
            //Last note
            Cell lastNoteCell = row.createCell(ContactColumnIndex.LAST_NOTE.getIndex());
            lastNoteCell.setCellStyle(cellStyle);
            if (note != null)
            {
                lastNoteCell.setCellValue(note);
            }
            else
            {
                lastNoteCell.setCellValue("");
            }

            //latest communication
            Cell latestCommunicationCell = row.createCell(ContactColumnIndex.LASTEST_COMMUNICATION.getIndex());
            latestCommunicationCell.setCellStyle(cellStyle);
            String latestCommunication = "";
            CommunicationHistory communicationHistory = communicationHistoryDAO.findLatestByContact(contact);
            if (communicationHistory != null)
            {
                latestCommunication = communicationHistory.getType() + " " + communicationHistory.getUser().getSharedContact().buildFullName() + " " + communicationHistory.getStartDate();
            }
            latestCommunicationCell.setCellValue(latestCommunication);

            //Closed_sales
            Cell closedSaleTeamCell = row.createCell(ContactColumnIndex.CLOSED_SALES.getIndex());
            closedSaleTeamCell.setCellStyle(cellStyle);
            closedSaleTeamCell.setCellValue(contact.getOrderIntake());

            //Median deal size
            Cell mdealSizeCell = row.createCell(ContactColumnIndex.MEDIAN_DEAL_SIZE.getIndex());
            mdealSizeCell.setCellStyle(cellStyle);
            mdealSizeCell.setCellValue(contact.getMedianDealSize());

            //Median deal time
            Cell mDealTimeCell = row.createCell(ContactColumnIndex.MEDIAN_DEAL_TIME.getIndex());
            mDealTimeCell.setCellStyle(cellStyle);
            mDealTimeCell.setCellValue(Math.round(contact.getMedianDealTime() / 86400000));

            //Gross pipe
            Cell grosspipeCell = row.createCell(ContactColumnIndex.GROSS_PIPE.getIndex());
            grosspipeCell.setCellStyle(cellStyle);
            grosspipeCell.setCellValue(contact.getGrossPipeline());

            //Weighted pipe
            Cell weightedCell = row.createCell(ContactColumnIndex.WEIGHTED_PIPE.getIndex());
            weightedCell.setCellStyle(cellStyle);
            weightedCell.setCellValue(contact.getNetPipeline());

            //Profit
            Cell profitCell = row.createCell(ContactColumnIndex.PROFIT.getIndex());
            profitCell.setCellStyle(cellStyle);
            profitCell.setCellValue(contact.getWonProfit());

//        Cell uuidContactCell = row.createCell(17);
//        .setCellStyle(cellStyle);
//        uuidContactCell.setCellValue(contact.getUuid().toString());
        }

    }

    private StringBuilder getEmailList(Contact contact, List<Communication> communicationList)
    {
        StringBuilder communicationStr = new StringBuilder();
        for (Communication communication : communicationList)
        {
            if (communication.getContact().getUuid().compareTo(contact.getUuid()) == 0)
            {
                if (communication.getType().compareTo(CommunicationType.EMAIL_WORK) == 0
                        || communication.getType().compareTo(CommunicationType.EMAIL_OTHER) == 0
                        || communication.getType().compareTo(CommunicationType.EMAIL_ICLOUD) == 0
                        || communication.getType().compareTo(CommunicationType.EMAIL_HOME) == 0)
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

    private StringBuilder getPhoneList(Contact contact, List<Communication> communicationList)
    {
        StringBuilder communicationStr = new StringBuilder();
        for (Communication communication : communicationList)
        {
            if (communication.getContact().getUuid().compareTo(contact.getUuid()) == 0)
            {
                if (communication.getType().compareTo(CommunicationType.PHONE_HOME) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_WORK) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_IPHONE) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_MOBILE) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_MAIN) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_HOME_FAX) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_WORK_FAX) == 0
                        || communication.getType().compareTo(CommunicationType.PHONE_OTHER) == 0)
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

    private StringBuilder getContactTeam(Contact contact, List<ContactUser> contactUserList)
    {
        StringBuilder contactTeam = new StringBuilder("");
        for (ContactUser contactUser : contactUserList)
        {
            if (contactUser.getContact().getUuid().compareTo(contact.getUuid()) == 0)
            {
                importExportUtils.appendMessage(contactTeam, contactUser.getUser().getSharedContact().buildFullName());
            }
        }

        return contactTeam;
    }

    public void createHeaderRow(Sheet sheet, CellStyle headerStyle, Enterprise enterprise)
    {
        HashMap<String, String> hashMap = importExportUtils.getStringHashMapNameColumnExcel(enterprise);
        Row row = sheet.createRow(0);
        row.setHeightInPoints(ContactConstant.HEIGHT_ROW);

        Cell firstNameCell = row.createCell(ContactColumnIndex.FIRST_NAME.getIndex());
        firstNameCell.setCellValue(hashMap.get(ImportExportColumnCode.FIRST_NAME.toString()));
        firstNameCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.FIRST_NAME.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell lastNameCell = row.createCell(ContactColumnIndex.LAST_NAME.getIndex());
        lastNameCell.setCellValue(hashMap.get(ImportExportColumnCode.LAST_NAME.toString()));
        lastNameCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.LAST_NAME.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell accountEmailCell = row.createCell(ContactColumnIndex.ACCOUNT_EMAIL.getIndex());
        accountEmailCell.setCellValue(hashMap.get(ImportExportColumnCode.ACCOUNT_EMAIL.toString()));
        accountEmailCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.ACCOUNT_EMAIL.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell streetCell = row.createCell(ContactColumnIndex.ADDRESS.getIndex());
        streetCell.setCellValue(hashMap.get(ImportExportColumnCode.ADDRESS.toString()));
        streetCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.ADDRESS.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell zipCodeCell = row.createCell(ContactColumnIndex.ZIP_CODE.getIndex());
        zipCodeCell.setCellValue(hashMap.get(ImportExportColumnCode.ZIP_CODE.toString()));
        zipCodeCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.ZIP_CODE.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell cityCell = row.createCell(ContactColumnIndex.CITY.getIndex());
        cityCell.setCellValue(hashMap.get(ImportExportColumnCode.CITY.toString()));
        cityCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.CITY.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell regionCell = row.createCell(ContactColumnIndex.REGION.getIndex());
        regionCell.setCellValue(hashMap.get(ImportExportColumnCode.REGION.toString()));
        regionCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.REGION.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell countryCell = row.createCell(ContactColumnIndex.COUNTRY.getIndex());
        countryCell.setCellValue(hashMap.get(ImportExportColumnCode.COUNTRY.toString()));
        countryCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.COUNTRY.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell titleCell = row.createCell(ContactColumnIndex.TITLE.getIndex());
        titleCell.setCellValue(hashMap.get(ImportExportColumnCode.TITLE.toString()));
        titleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.TITLE.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell phoneCell = row.createCell(ContactColumnIndex.PHONE.getIndex());
        phoneCell.setCellValue(hashMap.get(ImportExportColumnCode.PHONE.toString()));
        phoneCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.PHONE.getIndex(), ContactConstant.MEDIUM_WIDTH);

        Cell emailCell = row.createCell(ContactColumnIndex.EMAIL.getIndex());
        emailCell.setCellValue(hashMap.get(ImportExportColumnCode.EMAIL.toString()));
        emailCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.EMAIL.getIndex(), ContactConstant.LARGE_WIDTH);

        Cell typeCell = row.createCell(ContactColumnIndex.TYPE.getIndex());
        typeCell.setCellValue(hashMap.get(ImportExportColumnCode.ORGANISATION_TYPE.toString()));
        typeCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.TYPE.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell industryCell = row.createCell(ContactColumnIndex.INDUSTRY.getIndex());
        industryCell.setCellValue(hashMap.get(ImportExportColumnCode.INDUSTRY.toString()));
        industryCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.INDUSTRY.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell relationCell = row.createCell(ContactColumnIndex.RELATION.getIndex());
        relationCell.setCellValue(hashMap.get(ImportExportColumnCode.RELATION.toString()));
        relationCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.RELATION.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell relationshipCell = row.createCell(ContactColumnIndex.RELATIONSHIP.getIndex());
        relationshipCell.setCellValue(hashMap.get(ImportExportColumnCode.RELATIONSHIP.toString()));
        relationshipCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.RELATIONSHIP.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell discProfileCell = row.createCell(ContactColumnIndex.DISC_PROFILE.getIndex());
        discProfileCell.setCellValue(hashMap.get(ImportExportColumnCode.DISC_PROFILE.toString()));
        discProfileCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.DISC_PROFILE.getIndex(), ContactConstant.SMALL_WIDTH);

        Cell contactTeamCell = row.createCell(ContactColumnIndex.CONTACT_TEAM.getIndex());
        contactTeamCell.setCellValue(hashMap.get(ImportExportColumnCode.CONTACT_TEAM.toString()));
        contactTeamCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ContactColumnIndex.CONTACT_TEAM.getIndex(), ContactConstant.LARGE_WIDTH);


        Cell uuidCell = row.createCell(17);
        uuidCell.setCellValue("Contact ID");
        uuidCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(17, ContactConstant.LARGE_WIDTH);

    }

    public void exportDirectFile(String token, HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        String sheetName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.CONTACT_SHEET_NAME.toString());
        if (sheetName == null)
        {
            sheetName = "Contacts";
        }
        workbook.setSheetName(0, sheetName);

        CellStyle headStyle = importExportUtils.createHeadStyle(workbook);
        createHeaderRow(sheet, headStyle, enterprise);

        List<Contact> contactList = contactDAO.findByEnterpriseAndDeleted(enterprise, false);

        importExportUtils.checkSizeExport(contactList.size());

        List<Communication> communicationList = communicationDAO.findByContactIn(contactList);
        List<ContactUser> contactUserList = contactUserDAO.findByContactIn(contactList);

        int i = 0;
        int numberOf = 1;

        for (Contact contact : contactList)
        {
            i++;
            Row contentRow = sheet.createRow(i);
            generateValueForRow(contact, contentRow, communicationList, contactUserList, numberOf, null, null, false);
            numberOf++;
        }

        StringBuilder fileName = importExportUtils.createExportFileName(FileType.XLS, ContactConstant.PRE_EXPORT_FILE_NAME);

        User user = getUserFromToken(token);
        ImportExportHistory exportHistory = importExportUtils.generateExportHistory(user, enterprise, fileName, ObjectType.CONTACT);
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
                                                HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, InvalidFormatException, ServiceException
    {
        ContactFilterDTO contactFilterDTO = gson.fromJson(filterDTO, ContactFilterDTO.class);
        CountDTO countDTO = contactSearchService.countRecords(token, contactFilterDTO, UUID.randomUUID().toString());

        User user = getUserFromToken(token);

        ExportResultDTO exportResultDTO = new ExportResultDTO();
        if (countDTO.getCount() > 30)
        {
            exportResultDTO.setSendEmail(true);
            exportASAndSendEmail(getEnterpriseFromToken(token), token, user, contactFilterDTO);
            return exportResultDTO;
        }
        else
        {
            String fileUrl = exportNewAS(token, contactFilterDTO);
            exportResultDTO.setFileUrl(fileUrl);
            return exportResultDTO;
        }
    }

    private void exportASAndSendEmail(Enterprise enterprise, String token, User user, ContactFilterDTO contactFilterDTO)
    {
        taskExecutor.execute(() -> {
            TenantContext.setCurrentTenant(enterprise.getUuid().toString());
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = transactionManager.getTransaction(def);
            try
            {
                String fileUrl = exportNewAS(token, contactFilterDTO);
                importExportUtils.sendMailWithExportResult(user, fileUrl);
            }
            catch (IOException | IllegalAccessException | InvalidFormatException | ServiceException e)
            {
                e.printStackTrace();
            }

            transactionManager.commit(status);
        });

    }

    private String exportNewAS(String token, ContactFilterDTO contactFilterDTO) throws IOException, IllegalAccessException, ServiceException, InvalidFormatException
    {

        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);
        Workbook workbook = new HSSFWorkbook();

        File file = new ClassPathResource("/excel/" + EXPORT_CONTACT_TEMPLATE_FILE).getFile();
        FileInputStream inputStream = new FileInputStream(file);

        List<UUID> uuidList = contactSearchService.getUUIDList(token, contactFilterDTO);

        List<Contact> contactList = contactDAO.getByIdIn(uuidList);

        List<CustomField> customFieldList = customFieldDAO.findByObjectTypeOrderByPositionAsc(enterprise, ObjectType.CONTACT);

        List<UUID> contactListUuid = new ArrayList<>();
        for (Contact temp : contactList)
        {
            contactListUuid.add(temp.getUuid());
        }

        Map<UUID, Map<UUID, List<CustomFieldValue>>> contactCustomFieldMap = getContactCustomFieldMap(contactListUuid);

        Map<UUID, String> ContactNoteMap = getContactNoteMap(contactListUuid);

        workbook = WorkbookFactory.create(inputStream);
        Sheet contactSheet = workbook.getSheetAt(0);

        //gen title
        Map<Integer, UUID> indexCustomFieldMap = new HashMap<>();
        generateTitleRow(contactSheet, contactFilterDTO, enterprise);
        populateHeaderRowForCustomField(customFieldList, contactSheet, indexCustomFieldMap);

        CellStyle evenStyle = createEvenStyle(workbook);
        CellStyle oddStyle = workbook.createCellStyle();

        oddStyle.cloneStyleFrom(evenStyle);
        oddStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        oddStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        List<Communication> communicationList = communicationDAO.findByContactIn(contactList);
        List<ContactUser> contactUserList = contactUserDAO.findByContactIn(contactList);

        int i = 2;
        int numberOf = 1;

        for (Contact contact : contactList)
        {
            UUID uuid = contact.getUuid();
            Row contentRow = contactSheet.createRow(i);

            Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap = contactCustomFieldMap.get(uuid);
            if (customFieldIdCustomFieldValueMap == null)
            {
                customFieldIdCustomFieldValueMap = new HashMap<>();
            }
            CellStyle cellStyle = contactList.indexOf(contact) % 2 == 0 ? evenStyle : oddStyle;

            generateDataForRow(contact, contentRow, workbook, ContactNoteMap.get(uuid),
                    indexCustomFieldMap, customFieldIdCustomFieldValueMap, cellStyle, numberOf, communicationList, contactUserList);
            i++;
            numberOf++;
        }

        StringBuilder preFileName = importExportUtils.createExportFileName(FileType.XLSX, ContactConstant.PRE_EXPORT_FILE_NAME);

        String finalFileName = enterprise.getSharedOrganisation().getName() + "_AS_" + preFileName.toString();

        ByteArrayOutputStream exportOutputStream = new ByteArrayOutputStream();
        workbook.write(exportOutputStream);
        exportOutputStream.close();

        InputStream exportInputStream = new ByteArrayInputStream(exportOutputStream.toByteArray());

        String fileURL = amazonS3FileAccessor.saveExportFile(finalFileName.replaceAll("\\s+", ""), exportInputStream);
        System.out.println(fileURL);

        return fileURL;
    }

    private Map<UUID, Map<UUID, List<CustomFieldValue>>> getContactCustomFieldMap(List<UUID> accountIdList)
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

    //gen header for custom field
    private void populateHeaderRowForCustomField(List<CustomField> customFieldList, Sheet leadSheet,
                                                 Map<Integer, UUID> indexCustomFieldMap)
    {
        Row headerRow = leadSheet.getRow(1);
        int index = 0;
        int nextHeaderIndex = ContactColumnIndex.BEGIN_CUSTOM_FIELD.getIndex();
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

    private void generateTitleRow(Sheet contactSheet, ContactFilterDTO contactFilterDTO, Enterprise enterprise)
    {
        Row titleLeadRow = contactSheet.getRow(0);

        Cell ownerCell = titleLeadRow.getCell(3);
        Cell startCell = titleLeadRow.getCell(4);
        Cell endCell = titleLeadRow.getCell(5);

        String roleName = "";

        if (contactFilterDTO.getRoleFilterType().equals("Person"))
        {
            User user = userDAO.findOne(UUID.fromString(contactFilterDTO.getRoleFilterValue()));
            if (user != null)
            {
                roleName = user.getSharedContact().getFirstName() + " " + user.getSharedContact().getLastName();
            }
        }
        else if (contactFilterDTO.getRoleFilterType().equals("Unit"))
        {
            Unit unit = unitDAO.findOne(UUID.fromString(contactFilterDTO.getRoleFilterValue()));
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

    private Map<UUID, String> getContactNoteMap(List<UUID> uuidList)
    {
        Map<UUID, String> accountNoteMap = new HashMap<>();
        if (uuidList.size() > 0)
        {
            List<Object[]> noteList = noteDAO.findLatestByContactIdIn(new HashSet<UUID>(uuidList));
            if (noteList.size() > 0)
            {
                for (Object[] objects : noteList)
                {
                    Contact contact = (Contact) objects[0];
                    UUID prospectId = contact.getUuid();
                    if (accountNoteMap.get(prospectId) == null)
                    {
                        accountNoteMap.put(prospectId, objects[1].toString() + ": " + objects[2].toString());
                    }
                }
            }
        }
        return accountNoteMap;
    }

    private void generateDataForRow(Contact contact,
                                    Row contentRow, Workbook workbook, String note,
                                    Map<Integer, UUID> indexCustomFieldMap,
                                    Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap,
                                    CellStyle cellStyle, int numberOf,
                                    List<Communication> communicationList,
                                    List<ContactUser> contactUserList)
    {
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(cellStyle);
        DataFormat poiFormat = workbook.createDataFormat();
        String excelFormatPattern = DateFormatConverter.convert(Locale.ENGLISH, "mm/dd/yyyy");
        dateStyle.setDataFormat(poiFormat.getFormat(excelFormatPattern));

        int rowNumber = contentRow.getRowNum();

        generateValueForRow(contact, contentRow, communicationList, contactUserList, numberOf, cellStyle, note, true);


        int index = ContactColumnIndex.BEGIN_CUSTOM_FIELD.getIndex();
        do
        {
            UUID customFieldId = indexCustomFieldMap.get(index - ContactColumnIndex.BEGIN_CUSTOM_FIELD.getIndex());
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
                        customFieldCell.setCellType(CellType.NUMERIC);
                        customFieldCell.setCellValue(customFieldValue.getValue());
                        hasValue = true;
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

                    if (customFieldValue.getCustomField().getFieldType() == CustomFieldType.PRODUCT_TAG)
                    {
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
        } while (index < ContactColumnIndex.BEGIN_CUSTOM_FIELD.getIndex() + indexCustomFieldMap.size());
    }

}
