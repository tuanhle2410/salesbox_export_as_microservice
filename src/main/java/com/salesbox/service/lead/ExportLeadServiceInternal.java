package com.salesbox.service.lead;

import com.google.gson.Gson;
import com.salesbox.common.BaseService;
import com.salesbox.common.multitenant.TenantContext;
import com.salesbox.dao.*;
import com.salesbox.dto.CountDTO;
import com.salesbox.dto.ExportResultDTO;
import com.salesbox.entity.*;
import com.salesbox.entity.enums.CustomFieldType;
import com.salesbox.entity.enums.FileType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.exception.ServiceException;
import com.salesbox.file.AmazonS3FileAccessor;
import com.salesbox.lead.LeadFtsInternal;
import com.salesbox.lead.constant.LeadConstant;
import com.salesbox.lead.dto.LeadFilterDTO;
import com.salesbox.utils.CommonUtils;
import com.salesbox.utils.ImportExportUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.DateFormatConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by: tunh
 * Date: 10/04/18
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ExportLeadServiceInternal extends BaseService
{
    @Autowired
    NoteDAO noteDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    Gson gson;
    @Autowired
    CustomFieldDAO customFieldDAO;
    @Autowired
    CustomFieldValueDAO customFieldValueDAO;
    @Autowired
    UnitDAO unitDAO;
    @Autowired
    WorkDataWorkDataDAO workDataWorkDataDAO;
    @Autowired
    LeadFtsInternal leadFtsInternal;
    @Autowired
    LeadDAO leadDAO;
    @Autowired
    ProspectBaseDAO prospectBaseDAO;
    @Autowired
    LeadProductDAO leadProductDAO;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    ImportExportUtils importExportUtils;

    @Autowired
    AmazonS3FileAccessor amazonS3FileAccessor;

    private static final String EXPORT_LEAD_AS_TEMPLATE_FILE = "Salesbox_AS_Leads.xlsx";
    private static final String EXPORT_LEAD_DELEGATION_AS_TEMPLATE_FILE = "Salesbox_AS_Leads_Delegation.xlsx";


    public ExportResultDTO exportAdvancedSearch(String token, String filterDTO, String leadFrom, String timeZone) throws IOException, ServiceException, InvalidFormatException
    {
        LeadFilterDTO leadFilterDTO = gson.fromJson(filterDTO, LeadFilterDTO.class);
        CountDTO countDTO = leadFtsInternal.countRecords(token, UUID.randomUUID().toString(), leadFilterDTO);

        User user = getUserFromToken(token);
        Enterprise enterprise = getEnterpriseFromToken(token);

        ExportResultDTO exportResultDTO = new ExportResultDTO();
        if (countDTO.getCount() > 30)
        {
            exportResultDTO.setSendEmail(true);
            exportASAndSendEmail(enterprise, token, user, leadFilterDTO, leadFrom, timeZone);
            return exportResultDTO;
        }
        else
        {
            String fileUrl = exportNewAS(token, leadFilterDTO, leadFrom, timeZone);
            exportResultDTO.setFileUrl(fileUrl);
            return exportResultDTO;
        }
    }

    private void exportASAndSendEmail(Enterprise enterprise, String token, User user, LeadFilterDTO leadFilterDTO, String leadFrom, String timeZone)
    {
        taskExecutor.execute(() -> {
            TenantContext.setCurrentTenant(enterprise.getUuid().toString());
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = transactionManager.getTransaction(def);
            try
            {
                String fileUrl = exportNewAS(token, leadFilterDTO, leadFrom, timeZone);
                importExportUtils.sendMailWithExportResult(user, fileUrl);
            }
            catch (IOException | InvalidFormatException | ServiceException e)
            {
                e.printStackTrace();
            }

            transactionManager.commit(status);
        });

    }

    private String exportNewAS(String token, LeadFilterDTO leadFilterDTO, String leadFrom, String timeZone) throws IOException, ServiceException, InvalidFormatException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);

        File file;
        if (leadFrom.equals("lead"))
        {
            file = new ClassPathResource("/excel/" + EXPORT_LEAD_AS_TEMPLATE_FILE).getFile();
        }
        else
        {
            file = new ClassPathResource("/excel/" + EXPORT_LEAD_DELEGATION_AS_TEMPLATE_FILE).getFile();
        }
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet leadSheet = workbook.getSheetAt(0);

        List<Lead> leadList;

        List<Object[]> listObjectLead = leadFtsInternal.getLeads(token, "", leadFilterDTO, 0, 1000);

        leadList = convertObjectToLead(listObjectLead);

        List<UUID> leadIdList = getUniqueId(leadList);
        Map<UUID, String> leadNoteMap = getLeadNoteMap(leadIdList);

        List<CustomField> customFieldList = customFieldDAO.findByObjectTypeOrderByPositionAsc(enterprise, ObjectType.LEAD);
        Map<UUID, Map<UUID, List<CustomFieldValue>>> leadCustomFieldMap = getLeadCustomFieldMap(leadIdList);

        //gen title
        Map<Integer, UUID> indexCustomFieldMap = new HashMap<>();
        generateTitleRow(leadSheet, leadFilterDTO, enterprise, timeZone);
        populateHeaderRowForCustomField(customFieldList, leadSheet, indexCustomFieldMap);

        CellStyle evenStyle = createEvenStyle(workbook);
        CellStyle oddStyle = workbook.createCellStyle();

        oddStyle.cloneStyleFrom(evenStyle);
        oddStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        oddStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int i = 2;
        int numberOf = 1;

        List<Lead> leadDTOS = leadList;
        for (Lead leadDTO : leadDTOS)
        {
            Row taskSheetRow = leadSheet.createRow(i);

            UUID leadId = leadDTO.getUuid();

            Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap = leadCustomFieldMap.get(leadId);
            if (customFieldIdCustomFieldValueMap == null)
            {
                customFieldIdCustomFieldValueMap = new HashMap<>();
            }
            CellStyle cellStyle = leadDTOS.indexOf(leadDTO) % 2 == 0 ? evenStyle : oddStyle;

            generateValueForRow(leadDTO, taskSheetRow, workbook, leadNoteMap.get(leadId),
                    indexCustomFieldMap, customFieldIdCustomFieldValueMap, cellStyle, numberOf, timeZone);

            numberOf++;
            i++;
        }

        StringBuilder preFileName = importExportUtils.createExportFileName(FileType.XLSX, LeadConstant.PRE_EXPORT_FILE_NAME);

        String finalFileName = enterprise.getSharedOrganisation().getName() + "_AS_" + preFileName.toString();

        ByteArrayOutputStream exportOutputStream = new ByteArrayOutputStream();
        workbook.write(exportOutputStream);
        exportOutputStream.close();

        InputStream exportInputStream = new ByteArrayInputStream(exportOutputStream.toByteArray());

        String fileURL = amazonS3FileAccessor.saveExportFile(finalFileName.replaceAll("\\s+", ""), exportInputStream);
        System.out.println(fileURL);

        return fileURL;

    }


    public void exportAdvancedSearch(String token, String filterDTO,
                                     HttpServletRequest request, HttpServletResponse response, String leadFrom, String timeZone) throws IOException, ServiceException, InvalidFormatException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        LeadFilterDTO leadFilterDTO = gson.fromJson(filterDTO, LeadFilterDTO.class);
        File file;
        if (leadFrom.equals("lead"))
        {
            file = new ClassPathResource("/excel/" + EXPORT_LEAD_AS_TEMPLATE_FILE).getFile();
        }
        else
        {
            file = new ClassPathResource("/excel/" + EXPORT_LEAD_DELEGATION_AS_TEMPLATE_FILE).getFile();
        }
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet leadSheet = workbook.getSheetAt(0);

        List<Lead> leadList;

        List<Object[]> listObjectLead = leadFtsInternal.getLeads(token, "", leadFilterDTO, 0, 1000);

        leadList = convertObjectToLead(listObjectLead);

        List<UUID> leadIdList = getUniqueId(leadList);
        Map<UUID, String> leadNoteMap = getLeadNoteMap(leadIdList);

        List<CustomField> customFieldList = customFieldDAO.findByObjectTypeOrderByPositionAsc(enterprise, ObjectType.LEAD);
        Map<UUID, Map<UUID, List<CustomFieldValue>>> leadCustomFieldMap = getLeadCustomFieldMap(leadIdList);

        //gen title
        Map<Integer, UUID> indexCustomFieldMap = new HashMap<>();
        generateTitleRow(leadSheet, leadFilterDTO, enterprise, timeZone);
        populateHeaderRowForCustomField(customFieldList, leadSheet, indexCustomFieldMap);

        CellStyle evenStyle = createEvenStyle(workbook);
        CellStyle oddStyle = workbook.createCellStyle();

        oddStyle.cloneStyleFrom(evenStyle);
        oddStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        oddStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int i = 2;
        int numberOf = 1;

        List<Lead> leadDTOS = leadList;
        for (Lead leadDTO : leadDTOS)
        {
            Row taskSheetRow = leadSheet.createRow(i);

            UUID leadId = leadDTO.getUuid();

            Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap = leadCustomFieldMap.get(leadId);
            if (customFieldIdCustomFieldValueMap == null)
            {
                customFieldIdCustomFieldValueMap = new HashMap<>();
            }
            CellStyle cellStyle = leadDTOS.indexOf(leadDTO) % 2 == 0 ? evenStyle : oddStyle;

            generateValueForRow(leadDTO, taskSheetRow, workbook, leadNoteMap.get(leadId),
                    indexCustomFieldMap, customFieldIdCustomFieldValueMap, cellStyle, numberOf, timeZone);

            numberOf++;
            i++;
        }

        String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        response.setContentType(mimeType);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("filename=\"%s\"", "Leads.xlsx");
        response.setHeader(headerKey, headerValue);

        OutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        outputStream.close();
    }

    private List<Lead> convertObjectToLead(List<Object[]> listObjectLead)
    {
        List<Lead> listLeads;
        List<UUID> listUuid = new ArrayList<>();

        for (Object[] object : listObjectLead)
        {
            UUID uuid = UUID.fromString(object[0].toString());
            listUuid.add(uuid);
        }

        listLeads = leadDAO.findUUIDIn(listUuid);
        return listLeads;
    }

    private void generateTitleRow(Sheet taskSheet, LeadFilterDTO leadFilterDTO, Enterprise enterprise, String timeZone)
    {
        Row titleLeadRow = taskSheet.getRow(0);

        Cell ownerCell = titleLeadRow.getCell(3);
        Cell startCell = titleLeadRow.getCell(4);
        Cell endCell = titleLeadRow.getCell(5);

        String roleName = "";

        if (leadFilterDTO.getRoleFilterType().equals("Person"))
        {
            User user = userDAO.findOne(UUID.fromString(leadFilterDTO.getRoleFilterValue()));
            if (user != null)
            {
                roleName = user.getSharedContact().getFirstName() + " " + user.getSharedContact().getLastName();
            }
        }
        else if (leadFilterDTO.getRoleFilterType().equals("Unit"))
        {
            Unit unit = unitDAO.findOne(UUID.fromString(leadFilterDTO.getRoleFilterValue()));
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long diffTime = (serverTimezone + Integer.parseInt(timeZone)) * 60 * 60 * 1000;
        if (!leadFilterDTO.isFilterAll())
        {
            startCell.setCellValue(dateFormat.format(new Date(leadFilterDTO.getStartDate().getTime() + diffTime)));
            endCell.setCellValue(dateFormat.format(new Date(leadFilterDTO.getEndDate().getTime() - 1)));
        }
    }

    //gen header for custom field
    private void populateHeaderRowForCustomField(List<CustomField> customFieldList, Sheet leadSheet,
                                                 Map<Integer, UUID> indexCustomFieldMap)
    {
        Row headerRow = leadSheet.getRow(1);
        int index = 0;
        int nextHeaderIndex = ExportLeadASColumnIndex.BEGIN_CUSTOM_FIELD.index;
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

    private void generateValueForRow(Lead leadDTO, Row taskRow, Workbook workbook,
                                     String note, Map<Integer, UUID> indexCustomFieldMap,
                                     Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap,
                                     CellStyle cellStyle, int numberOf, String timeZone)
    {
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(cellStyle);
        DataFormat poiFormat = workbook.createDataFormat();
        String excelFormatPattern = DateFormatConverter.convert(Locale.ENGLISH, "mm/dd/yyyy");
        dateStyle.setDataFormat(poiFormat.getFormat(excelFormatPattern));

        int rowNumber = taskRow.getRowNum();

        generateLeadData(leadDTO, taskRow, note, cellStyle, dateStyle, numberOf, timeZone);

        int index = ExportLeadASColumnIndex.BEGIN_CUSTOM_FIELD.index;
        do
        {
            UUID customFieldId = indexCustomFieldMap.get(index - ExportLeadASColumnIndex.BEGIN_CUSTOM_FIELD.index);
            List<CustomFieldValue> customFieldValueList = customFieldIdCustomFieldValueMap.get(customFieldId);
            boolean hasValue = false;
            Cell customFieldCell = taskRow.createCell(index);
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
                        customFieldCell.setCellValue(Double.valueOf(customFieldValue.getValue()));
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
        } while (index < ExportLeadASColumnIndex.BEGIN_CUSTOM_FIELD.index + indexCustomFieldMap.size());
    }

    private void generateLeadData(Lead lead, Row leadSheetRow, String note, CellStyle cellStyle, CellStyle dateStyle, int numberOf, String timeZone)
    {
        //No
        Cell noCell = leadSheetRow.createCell(ExportLeadASColumnIndex.NO.getIndex());
        noCell.setCellValue(String.valueOf(numberOf));
        noCell.setCellStyle(cellStyle);

        //AccountName
        Cell accountNameCell = leadSheetRow.createCell(ExportLeadASColumnIndex.ACCOUNT_NAME.getIndex());
        if (lead.getOrganisation() != null)
        {
            accountNameCell.setCellValue(lead.getOrganisation().getName());
        }
        else
        {
            accountNameCell.setCellValue("");
        }
        accountNameCell.setCellStyle(cellStyle);

        //Contact_First_Name
        Cell contactFirstNameCell = leadSheetRow.createCell(ExportLeadASColumnIndex.CONTACT_FIRST_NAME.getIndex());
        if (lead.getContact() != null)
        {
            contactFirstNameCell.setCellValue(lead.getContact().getFirstName());
        }
        else
        {
            contactFirstNameCell.setCellValue("");
        }
        contactFirstNameCell.setCellStyle(cellStyle);

        //Contact_Last_Name
        Cell contactLastNameCell = leadSheetRow.createCell(ExportLeadASColumnIndex.CONTACT_LAST_NAME.getIndex());
        if (lead.getContact() != null)
        {
            contactLastNameCell.setCellValue(lead.getContact().getLastName());
        }
        else
        {
            contactLastNameCell.setCellValue("");
        }
        contactLastNameCell.setCellStyle(cellStyle);

        //Contact_Email_Name
        Cell contactEmailCell = leadSheetRow.createCell(ExportLeadASColumnIndex.CONTACT_EMAIL.getIndex());
        if (lead.getContact() != null)
        {
            contactEmailCell.setCellValue(lead.getContact().getEmail());
        }
        else
        {
            contactEmailCell.setCellValue("");
        }
        contactEmailCell.setCellStyle(cellStyle);

        //Contact_Phone
        Cell contactPhoneCell = leadSheetRow.createCell(ExportLeadASColumnIndex.CONTACT_PHONE.getIndex());
        if (lead.getContact() != null)
        {
            contactPhoneCell.setCellValue(lead.getContact().getPhone());
        }
        else
        {
            contactPhoneCell.setCellValue("");
        }
        contactPhoneCell.setCellStyle(cellStyle);

        //Product_Group
        Cell productGroupCell = leadSheetRow.createCell(ExportLeadASColumnIndex.PRODUCT_GROUP.getIndex());
        if (lead.getLineOfBusiness() != null)
        {
            productGroupCell.setCellValue(lead.getLineOfBusiness().getName());
        }
        else
        {
            productGroupCell.setCellValue("");
        }
        productGroupCell.setCellStyle(cellStyle);

        //Product
        Cell productCell = leadSheetRow.createCell(ExportLeadASColumnIndex.PRODUCTS.getIndex());
        if (lead.getLineOfBusiness() != null && leadProductDAO.findByLead(lead).size() > 0)
        {
            List<Product> products = new ArrayList<>();
            StringBuilder productString = new StringBuilder();
            products = leadProductDAO.findProductByLead(lead);
            for (int i = 0; i < products.size(); )
            {
                productString.append(products.get(i).getName());
                if (i != products.size() - 1)
                {
                    productString.append(", ");
                }
                i++;
            }
            productCell.setCellValue(productString.toString());
        }
        else
        {
            productCell.setCellValue("");
        }
        productCell.setCellStyle(cellStyle);

        //Priority
        Cell priorityCell = leadSheetRow.createCell(ExportLeadASColumnIndex.PRIORITY.getIndex());
        priorityCell.setCellValue(convetPriorityToString(lead.getPriority()));
        priorityCell.setCellStyle(cellStyle);

        //Deadline
        Cell deadLineCell = leadSheetRow.createCell(ExportLeadASColumnIndex.DEADLINE.getIndex());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long diffTime = (serverTimezone + Integer.parseInt(timeZone)) * 60 * 60 * 1000;
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        if (lead.getDeadlineDate() != null)
        {
            deadLineCell.setCellValue(dateFormat.format(new Date(lead.getDeadlineDate().getTime() + diffTime)));
        }
        else
        {
            deadLineCell.setCellValue("");
        }
        deadLineCell.setCellStyle(cellStyle);

        //Status
        Cell statusCell = leadSheetRow.createCell(ExportLeadASColumnIndex.STATUS.getIndex());
        if (lead.getStatus() != null)
        {
            statusCell.setCellValue(lead.getStatus());
        }
        else
        {
            statusCell.setCellValue("none");
        }
        statusCell.setCellStyle(cellStyle);

        //Source
        Cell sourceCell = leadSheetRow.createCell(ExportLeadASColumnIndex.SOURCE.getIndex());
        String sourceCellTxt;
        int ext = lead.getSource().getExtension();
        switch (ext)
        {
            case 0:
                String userCreator = "";
                if (userDAO.findOne(lead.getCreator().getUuid()) != null)
                {
                    userCreator = userDAO.findOne(lead.getCreator().getUuid()).getSharedContact().buildFullName();
                }
                sourceCellTxt = userCreator;
                break;
            case 1:
                sourceCellTxt = "Facebook";
                break;
            case 2:
                sourceCellTxt = "LinkedIn";
                break;
            case 3:
                sourceCellTxt = "MailChimp";
                break;
            case 4:
                sourceCellTxt = "LeadBoxer";
                break;
            default:
                sourceCellTxt = "";
        }
        sourceCell.setCellValue(sourceCellTxt);
        sourceCell.setCellStyle(cellStyle);

        //Owner
        Cell ownerCell = leadSheetRow.createCell(ExportLeadASColumnIndex.OWNER.getIndex());
        String owner = "";
        if (lead.getOwner() != null)
        {
            if (userDAO.findOne(lead.getOwner().getUuid()) != null)
            {
                owner = userDAO.findOne(lead.getOwner().getUuid()).getSharedContact().buildFullName();
            }
            ownerCell.setCellValue(owner);
        }
        else
        {
            ownerCell.setCellValue("");
        }
        ownerCell.setCellStyle(cellStyle);

        //Note
        Cell noteCell = leadSheetRow.createCell(ExportLeadASColumnIndex.NOTE.getIndex());
        noteCell.setCellValue(note);
        noteCell.setCellStyle(cellStyle);

        //Convert_To_Opp
        Cell converToOppCell = leadSheetRow.createCell(ExportLeadASColumnIndex.CONVERT_TO_OPP.getIndex());
        if (lead.getProspectId() == null)
        {
            converToOppCell.setCellValue("");
        }
        else
        {
            converToOppCell.setCellValue(prospectBaseDAO.findOne(lead.getProspectId()).getDescription());
        }
        converToOppCell.setCellStyle(cellStyle);
    }

    //convert priority
    private String convetPriorityToString(Double priority)
    {
        Double tempPriority = priority / 20 - 1;
        String stringPriority = "";
        if (tempPriority.equals(LeadConstant.LOWEST))
        {
            stringPriority = "Lowest";
        }
        else if (tempPriority.equals(LeadConstant.LOW))
        {
            stringPriority = "Low";
        }
        else if (tempPriority.equals(LeadConstant.MEDIUM))
        {
            stringPriority = "Medium";
        }
        else if (tempPriority.equals(LeadConstant.HIGH))
        {
            stringPriority = "High";
        }
        else
        {
            stringPriority = "Highest";
        }
        return stringPriority;
    }

    private Map<UUID, Map<UUID, List<CustomFieldValue>>> getLeadCustomFieldMap(List<UUID> leadIdList)
    {
        Map<UUID, Map<UUID, List<CustomFieldValue>>> leadCustomFieldMap = new HashMap<>();
        for (UUID uuid : leadIdList)
        {
            leadCustomFieldMap.put(uuid, new HashMap<UUID, List<CustomFieldValue>>());
        }

        List<CustomFieldValue> customFieldValueList = customFieldValueDAO.findByCustomFieldAndObjectIdIn(leadIdList);
        for (CustomFieldValue customFieldValue : customFieldValueList)
        {
            UUID leadId = customFieldValue.getObjectId();
            UUID customFieldId = customFieldValue.getCustomField().getUuid();
            if (leadCustomFieldMap.get(leadId).get(customFieldId) != null)
            {
                leadCustomFieldMap.get(leadId).get(customFieldId).add(customFieldValue);
            }
            else
            {
                List<CustomFieldValue> newCustomFieldValueList = new ArrayList<>();
                newCustomFieldValueList.add(customFieldValue);
                leadCustomFieldMap.get(leadId).put(customFieldId, newCustomFieldValueList);
            }
        }

        return leadCustomFieldMap;
    }


    private List<UUID> getUniqueId(List<Lead> leadList)
    {
        List<UUID> uuidList = new ArrayList<>();
        if (leadList.size() > 0)
        {
            for (Lead leadDTO : leadList)
            {
                uuidList.add(leadDTO.getUuid());
            }
        }
        return uuidList;
    }

    private Map<UUID, String> getLeadNoteMap(List<UUID> uuidList)
    {
        Map<UUID, String> leadNoteMap = new HashMap<>();
        if (uuidList.size() > 0)
        {
            List<Object[]> noteList = taskDAO.findNoteByLeadIdIn(new HashSet<UUID>(uuidList));
            if (noteList.size() > 0)
            {
                for (Object[] objects : noteList)
                {
                    UUID leadId = (UUID) objects[0];
                    if (leadNoteMap.get(leadId) == null)
                    {
                        if (objects[1] == null)
                        {
                            leadNoteMap.put(leadId, "");
                        }
                        else
                        {
                            leadNoteMap.put(leadId, objects[1].toString());
                        }
                    }
                }
            }
        }
        return leadNoteMap;
    }

    enum ExportLeadASColumnIndex
    {
        NO(0),
        ACCOUNT_NAME(1),
        CONTACT_FIRST_NAME(2),
        CONTACT_LAST_NAME(3),
        CONTACT_EMAIL(4),
        CONTACT_PHONE(5),
        PRODUCT_GROUP(6),
        PRODUCTS(7),
        PRIORITY(8),
        DEADLINE(9),
        STATUS(10),
        SOURCE(11),
        OWNER(12),
        NOTE(13),
        CONVERT_TO_OPP(14),
        BEGIN_CUSTOM_FIELD(15),
        ;
        private int index;

        ExportLeadASColumnIndex(int index)
        {
            this.index = index;
        }

        public int getIndex()
        {
            return this.index;
        }
    }
}
