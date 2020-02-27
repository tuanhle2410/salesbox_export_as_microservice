package com.salesbox.service.task;

import com.google.gson.Gson;
import com.salesbox.common.BaseService;
import com.salesbox.common.multitenant.TenantContext;
import com.salesbox.dao.*;
import com.salesbox.dao.task.TaskRelationDAO;
import com.salesbox.dto.CountDTO;
import com.salesbox.dto.ExportResultDTO;
import com.salesbox.entity.*;
import com.salesbox.entity.enums.CustomFieldType;
import com.salesbox.entity.enums.FileType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.exception.ServiceException;
import com.salesbox.file.AmazonS3FileAccessor;
import com.salesbox.task.TaskConstant;
import com.salesbox.task.dto.TaskFilterDTO;
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

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by: tunh
 * Date: 10/04/18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExportTaskServiceInternal extends BaseService
{
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
    TaskFtsInternal taskFtsInternal;
    @Autowired
    LeadDAO leadDAO;
    @Autowired
    ProspectBaseDAO prospectBaseDAO;
    @Autowired
    TaskRelationDAO taskRelationDAO;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    ImportExportUtils importExportUtils;

    @Autowired
    AmazonS3FileAccessor amazonS3FileAccessor;

    private static final String EXPORT_TASK_AS_TEMPLATE_FILE = "Salesbox_AS_Tasks.xlsx";
    private static final String EXPORT_TASK_DELEGATION_AS_TEMPLATE_FILE = "Salesbox_AS_Tasks_Delegation.xlsx";

    public ExportResultDTO exportAdvancedSearch(String token, String filterDTO, String taskFrom, String timeZone) throws IOException, IllegalAccessException, InvalidFormatException, ServiceException
    {
        TaskFilterDTO taskFilterDTO = gson.fromJson(filterDTO, TaskFilterDTO.class);
        CountDTO countDTO = taskFtsInternal.countRecords(token, UUID.randomUUID().toString(), taskFilterDTO);

        User user = getUserFromToken(token);
        Enterprise enterprise = getEnterpriseFromToken(token);

        ExportResultDTO exportResultDTO = new ExportResultDTO();
        if (countDTO.getCount() > 30)
        {
            exportResultDTO.setSendEmail(true);
            exportASAndSendEmail(enterprise, token, user, taskFilterDTO, taskFrom, timeZone);
            return exportResultDTO;
        }
        else
        {
            String fileUrl = exportNewAS(token, taskFilterDTO, taskFrom, timeZone);
            exportResultDTO.setFileUrl(fileUrl);
            return exportResultDTO;
        }
    }

    private void exportASAndSendEmail(Enterprise enterprise, String token, User user, TaskFilterDTO taskFilterDTO, String taskFrom, String timeZone)
    {
        taskExecutor.execute(() -> {
            TenantContext.setCurrentTenant(enterprise.getUuid().toString());
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = transactionManager.getTransaction(def);
            try
            {
                String fileUrl = exportNewAS(token, taskFilterDTO, taskFrom, timeZone);
                importExportUtils.sendMailWithExportResult(user, fileUrl);
            }
            catch (IOException | InvalidFormatException | ServiceException e)
            {
                e.printStackTrace();
            }

            transactionManager.commit(status);
        });

    }

    private String exportNewAS(String token, TaskFilterDTO taskFilterDTO, String taskFrom, String timeZone) throws IOException, ServiceException, InvalidFormatException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);

        File file;
        if (taskFrom.equals("task"))
        {
            file = new ClassPathResource("/excel/" + EXPORT_TASK_AS_TEMPLATE_FILE).getFile();
        }
        else
        {
            file = new ClassPathResource("/excel/" + EXPORT_TASK_DELEGATION_AS_TEMPLATE_FILE).getFile();
        }
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet taskSheet = workbook.getSheetAt(0);

        List<Task> taskList = new ArrayList<>();

        taskList = taskFtsInternal.listFromDB(token, "", taskFilterDTO, 0, 100000);

        List<UUID> taskIdList = getUniqueId(taskList);
        Map<UUID, String> taskNoteMap = getTaskNoteMap(taskIdList);

        //get custom field
        List<CustomField> customFieldList = customFieldDAO.findByObjectTypeOrderByPositionAsc(enterprise, ObjectType.TASK);
        Map<UUID, Map<UUID, List<CustomFieldValue>>> taskCustomFieldMap = getTaskCustomFieldMap(taskIdList);

        //gen fist and second title
        Map<Integer, UUID> indexCustomFieldMap = new HashMap<>();
        generateTitleRow(taskSheet, taskFilterDTO, enterprise, timeZone);
        populateHeaderRowForCustomField(customFieldList, taskSheet, indexCustomFieldMap);

        CellStyle evenStyle = createEvenStyle(workbook);
        CellStyle oddStyle = workbook.createCellStyle();

        oddStyle.cloneStyleFrom(evenStyle);
        oddStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        oddStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int i = 2;
        int numberOf = 1;

        List<Task> taskS = taskList;
        for (Task task : taskS)
        {
            Row taskSheetRow = taskSheet.createRow(i);

            UUID taskId = task.getUuid();

            Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap = taskCustomFieldMap.get(taskId);
            if (customFieldIdCustomFieldValueMap == null)
            {
                customFieldIdCustomFieldValueMap = new HashMap<>();
            }
            CellStyle cellStyle = taskS.indexOf(task) % 2 == 0 ? evenStyle : oddStyle;

            //gen value for row
            generateValueForRow(task, taskSheetRow, workbook, taskNoteMap.get(taskId),
                    indexCustomFieldMap, customFieldIdCustomFieldValueMap, cellStyle, numberOf, timeZone);

            numberOf++;
            i++;
        }

        StringBuilder preFileName = importExportUtils.createExportFileName(FileType.XLSX, TaskConstant.PRE_EXPORT_FILE_NAME);

        String finalFileName = enterprise.getSharedOrganisation().getName() + "_AS_" + preFileName.toString();

        ByteArrayOutputStream exportOutputStream = new ByteArrayOutputStream();
        workbook.write(exportOutputStream);
        exportOutputStream.close();

        InputStream exportInputStream = new ByteArrayInputStream(exportOutputStream.toByteArray());

        String fileURL = amazonS3FileAccessor.saveExportFile(finalFileName.replaceAll("\\s+", ""), exportInputStream);
        System.out.println(fileURL);

        return fileURL;
    }


    //gen first row with logo sale box
    private void generateTitleRow(Sheet taskSheet, TaskFilterDTO taskFilterDTO, Enterprise enterprise, String timeZone)
    {
        Row titleTaskRow = taskSheet.getRow(0);
        Cell ownerCell = titleTaskRow.getCell(3);
        Cell startCell = titleTaskRow.getCell(4);
        Cell endCell = titleTaskRow.getCell(5);

        String roleName = "";

        if (taskFilterDTO.getRoleFilterType().equals("Person"))
        {
            User user = userDAO.findOne(UUID.fromString(taskFilterDTO.getRoleFilterValue()));
            if (user != null)
            {
                roleName = user.getSharedContact().getFirstName() + " " + user.getSharedContact().getLastName();
            }
        }
        else if (taskFilterDTO.getRoleFilterType().equals("Unit"))
        {
            Unit unit = unitDAO.findOne(UUID.fromString(taskFilterDTO.getRoleFilterValue()));
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
        if (!taskFilterDTO.isFilterAll())
        {
            startCell.setCellValue(dateFormat.format(new Date(taskFilterDTO.getStartDate().getTime() + diffTime)));
            endCell.setCellValue(dateFormat.format(new Date(taskFilterDTO.getEndDate().getTime() - 1)));
        }
    }

    //gen header for custom field
    private void populateHeaderRowForCustomField(List<CustomField> customFieldList, Sheet taskSheet,
                                                 Map<Integer, UUID> indexCustomFieldMap)
    {
        Row headerRow = taskSheet.getRow(1);
        int index = 0;
        int nextHeaderIndex = ExportTaskASColumnIndex.BEGIN_CUSTOM_FIELD.index;
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

    private void generateValueForRow(Task task, Row taskRow, Workbook workbook,
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

        generateTaskData(task, taskRow, note, cellStyle, dateStyle, numberOf, timeZone);

        int index = ExportTaskASColumnIndex.BEGIN_CUSTOM_FIELD.index;
        do
        {
            UUID customFieldId = indexCustomFieldMap.get(index - ExportTaskASColumnIndex.BEGIN_CUSTOM_FIELD.index);
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
        } while (index < ExportTaskASColumnIndex.BEGIN_CUSTOM_FIELD.index + indexCustomFieldMap.size());
    }

    private void generateTaskData(Task task, Row taskSheetRow, String note, CellStyle cellStyle, CellStyle dateStyle, int numberOf, String timeZone)
    {
        //No
        Cell noCell = taskSheetRow.createCell(ExportTaskASColumnIndex.NO.getIndex());
        noCell.setCellValue(String.valueOf(numberOf));
        noCell.setCellStyle(cellStyle);

        //Account Name
        Cell accountNameCell = taskSheetRow.createCell(ExportTaskASColumnIndex.ACCOUNT_NAME.getIndex());
        if (task.getOrganisation() != null)
        {
            accountNameCell.setCellValue(task.getOrganisation().getName());
        }
        else
        {
            accountNameCell.setCellValue("");
        }
        accountNameCell.setCellStyle(cellStyle);

        //Contact First Name
        Cell contactFirstNameCell = taskSheetRow.createCell(ExportTaskASColumnIndex.CONTACT_FIRST_NAME.getIndex());
        if (task.getContact() != null)
        {
            contactFirstNameCell.setCellValue(task.getContact().getFirstName());
        }
        else
        {
            contactFirstNameCell.setCellValue("");
        }
        contactFirstNameCell.setCellStyle(cellStyle);

        //Contact Last Name
        Cell contactLastNameCell = taskSheetRow.createCell(ExportTaskASColumnIndex.CONTACT_LAST_NAME.getIndex());
        if (task.getContact() != null)
        {
            contactLastNameCell.setCellValue(task.getContact().getLastName());
        }
        else
        {
            contactLastNameCell.setCellValue("");
        }
        contactLastNameCell.setCellStyle(cellStyle);

        //Category
        Cell categoryCell = taskSheetRow.createCell(ExportTaskASColumnIndex.CATEGORY.getIndex());
        if (task.getCategory() != null)
        {
            categoryCell.setCellValue(task.getCategory().getName());
        }
        else
        {
            categoryCell.setCellValue("");
        }
        categoryCell.setCellStyle(cellStyle);

        //Focus
        Cell focusCell = taskSheetRow.createCell(ExportTaskASColumnIndex.FOCUS.getIndex());
        if (task.getFocusActivity() == null)
        {
            //check FocusWorkData
            if (task.getFocusWorkData() == null)
            {
                focusCell.setCellValue("");
            }
            else
            {
                focusCell.setCellValue(task.getFocusWorkData().getName());
            }
        }
        else
        {
            focusCell.setCellValue(task.getFocusActivity().getName());
        }
        focusCell.setCellStyle(cellStyle);

        //Date and time
        Cell dateAndTimeCell = taskSheetRow.createCell(ExportTaskASColumnIndex.DATE_AND_TIME.getIndex());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        long diffTime = (serverTimezone + Integer.parseInt(timeZone)) * 60 * 60 * 1000;
        if (task.getDateAndTime() != null)
        {
            dateAndTimeCell.setCellValue(dateFormat.format(new Date(task.getDateAndTime().getTime() + diffTime)));
        }
        else
        {
            dateAndTimeCell.setCellValue("");
        }
        dateAndTimeCell.setCellStyle(cellStyle);

        //Owner
        Cell ownerCell = taskSheetRow.createCell(ExportTaskASColumnIndex.OWNER.getIndex());
        String owner = "";
        if (task.getOwner() != null)
        {
            if (userDAO.findOne(task.getOwner().getUuid()) != null)
            {
                owner = userDAO.findOne(task.getOwner().getUuid()).getSharedContact().buildFullName();
            }
            ownerCell.setCellValue(owner);
        }
        else
        {
            ownerCell.setCellValue("");
        }
        ownerCell.setCellStyle(cellStyle);

        //Tag
        Cell tagCell = taskSheetRow.createCell(ExportTaskASColumnIndex.TAG.getIndex());
        tagCell.setCellValue(String.valueOf(task.getTaskTag().getName()));
        tagCell.setCellStyle(cellStyle);

        //Note
        Cell noteCell = taskSheetRow.createCell(ExportTaskASColumnIndex.NOTE.getIndex());
        noteCell.setCellValue(note);
        noteCell.setCellStyle(cellStyle);

        //Lead
        UUID leadId = taskRelationDAO.findLeadIdByTaskId(task.getUuid());
        if (leadId != null)
        {
            Cell leadCell = taskSheetRow.createCell(ExportTaskASColumnIndex.LEAD.getIndex());
            Lead lead = leadDAO.findOne(leadId);
            String leadInCell = "";
            if (lead != null)
            {
                if (lead.getContact() == null)
                {
                    if (lead.getOrganisation() != null)
                    {
                        if (lead.getLineOfBusiness() != null)
                        {
                            leadInCell = lead.getOrganisation().getName() + "-" + lead.getLineOfBusiness().getName();
                        }
                        else
                        {
                            leadInCell = lead.getOrganisation().getName();
                        }
                    }
                }
                else
                {
                    if (lead.getLineOfBusiness() != null)
                    {
                        leadInCell = lead.getContact().getFirstName() + " " + lead.getContact().getLastName() + "-" + lead.getLineOfBusiness().getName();
                    }
                    else
                    {
                        leadInCell = lead.getContact().getFirstName() + " " + lead.getContact().getLastName();
                    }
                }
            }
            leadCell.setCellValue(leadInCell);
            leadCell.setCellStyle(cellStyle);

            Cell oppCell = taskSheetRow.createCell(ExportTaskASColumnIndex.OPPORTUNITY.getIndex());
            oppCell.setCellValue("");
            oppCell.setCellStyle(cellStyle);
        }
        else
        {
            Cell leadCell = taskSheetRow.createCell(ExportTaskASColumnIndex.LEAD.getIndex());
            leadCell.setCellValue("");
            leadCell.setCellStyle(cellStyle);

            Cell oppCell = taskSheetRow.createCell(ExportTaskASColumnIndex.OPPORTUNITY.getIndex());
            if (task.getProspectId() == null)
            {
                oppCell.setCellValue("");
            }
            else
            {
                if (prospectBaseDAO.findOne(task.getProspectId()) != null)
                {
                    String oppCellText = prospectBaseDAO.findOne(task.getProspectId()).getDescription();
                    if (oppCellText != null)
                    {
                        oppCell.setCellValue(oppCellText);
                    }
                }
                else
                {
                    oppCell.setCellValue("");
                }
            }
            oppCell.setCellStyle(cellStyle);
        }
    }

    //get custom field
    private Map<UUID, Map<UUID, List<CustomFieldValue>>> getTaskCustomFieldMap(List<UUID> taskIdList)
    {
        Map<UUID, Map<UUID, List<CustomFieldValue>>> taskCustomFieldMap = new HashMap<>();
        for (UUID uuid : taskIdList)
        {
            taskCustomFieldMap.put(uuid, new HashMap<UUID, List<CustomFieldValue>>());
        }

        List<CustomFieldValue> customFieldValueList = customFieldValueDAO.findByCustomFieldAndObjectIdIn(taskIdList);
        for (CustomFieldValue customFieldValue : customFieldValueList)
        {
            UUID taskId = customFieldValue.getObjectId();
            UUID customFieldId = customFieldValue.getCustomField().getUuid();
            if (taskCustomFieldMap.get(taskId).get(customFieldId) != null)
            {
                taskCustomFieldMap.get(taskId).get(customFieldId).add(customFieldValue);
            }
            else
            {
                List<CustomFieldValue> newCustomFieldValueList = new ArrayList<>();
                newCustomFieldValueList.add(customFieldValue);
                taskCustomFieldMap.get(taskId).put(customFieldId, newCustomFieldValueList);
            }
        }

        return taskCustomFieldMap;
    }

    //get list uuid task
    private List<UUID> getUniqueId(List<Task> taskList)
    {
        List<UUID> uuidList = new ArrayList<>();
        if (taskList.size() > 0)
        {
            for (Task task : taskList)
            {
                uuidList.add(task.getUuid());
            }
        }
        return uuidList;
    }

    //get note of tasks
    private Map<UUID, String> getTaskNoteMap(List<UUID> uuidList)
    {
        Map<UUID, String> taskNoteMap = new HashMap<>();
        if (uuidList.size() > 0)
        {
            List<Object[]> noteList = taskDAO.findNoteByTaskIdIn(new HashSet<UUID>(uuidList));
            if (noteList.size() > 0)
            {
                for (Object[] objects : noteList)
                {
                    UUID tasktId = (UUID) objects[0];
                    if (taskNoteMap.get(tasktId) == null)
                    {
                        if (objects[1] == null)
                        {
                            taskNoteMap.put(tasktId, "");
                        }
                        else
                        {
                            taskNoteMap.put(tasktId, objects[1].toString());
                        }
                    }
                }
            }
        }
        return taskNoteMap;
    }

    //enum of task column
    enum ExportTaskASColumnIndex
    {
        NO(0),
        ACCOUNT_NAME(1),
        CONTACT_FIRST_NAME(2),
        CONTACT_LAST_NAME(3),
        CATEGORY(4),
        FOCUS(5),
        DATE_AND_TIME(6),
        OWNER(7),
        TAG(8),
        NOTE(9),
        LEAD(10),
        OPPORTUNITY(11),
        BEGIN_CUSTOM_FIELD(12),
        ;
        private int index;

        ExportTaskASColumnIndex(int index)
        {
            this.index = index;
        }

        public int getIndex()
        {
            return this.index;
        }
    }

}
