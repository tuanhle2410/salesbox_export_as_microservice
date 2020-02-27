package com.salesbox.service.appointment;

import com.google.gson.Gson;
import com.salesbox.appointment.constant.AppointmentConstant;
import com.salesbox.appointment.dto.AppointmentFilterDTO;
import com.salesbox.appointment.service.AppointmentFtsInternal;
import com.salesbox.common.BaseService;
import com.salesbox.common.multitenant.TenantContext;
import com.salesbox.constant.RelationConstant;
import com.salesbox.dao.*;
import com.salesbox.dto.CountDTO;
import com.salesbox.dto.ExportResultDTO;
import com.salesbox.entity.*;
import com.salesbox.entity.enums.CustomFieldType;
import com.salesbox.entity.enums.FileType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.exception.ServiceException;
import com.salesbox.file.AmazonS3FileAccessor;
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


@Service
@Transactional(rollbackFor = Exception.class)
public class ExportAppointmentServiceInternal extends BaseService
{
    @Autowired
    Gson gson;
    @Autowired
    CustomFieldDAO customFieldDAO;
    @Autowired
    CustomFieldValueDAO customFieldValueDAO;
    @Autowired
    UnitDAO unitDAO;
    @Autowired
    AppointmentFtsInternal appointmentFtsInternal;
    @Autowired
    LeadDAO leadDAO;
    @Autowired
    ProspectBaseDAO prospectBaseDAO;
    @Autowired
    AppointmentRelationDAO appointmentRelationDAO;
    @Autowired
    AppointmentInviteeContactDAO appointmentInviteeContactDAO;
    @Autowired
    AppointmentInviteeInviteeDAO appointmentInviteeInviteeDAO;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    ImportExportUtils importExportUtils;

    @Autowired
    AmazonS3FileAccessor amazonS3FileAccessor;

    private static final String EXPORT_APPOINTMENT_AS_TEMPLATE_FILE = "Salesbox_AS_Appointments.xlsx";


    public ExportResultDTO exportAdvancedSearch(String token, String filterDTO, String timeZone) throws IOException, IllegalAccessException, InvalidFormatException, ServiceException
    {
        AppointmentFilterDTO appointmentFilterDTO = gson.fromJson(filterDTO, AppointmentFilterDTO.class);
        CountDTO countDTO = appointmentFtsInternal.countRecords(token, UUID.randomUUID().toString(), appointmentFilterDTO);

        User user = getUserFromToken(token);
        Enterprise enterprise = getEnterpriseFromToken(token);

        ExportResultDTO exportResultDTO = new ExportResultDTO();
        if (countDTO.getCount() > 30)
        {
            exportResultDTO.setSendEmail(true);
            exportASAndSendEmail(enterprise, token, user, appointmentFilterDTO, timeZone);
            return exportResultDTO;
        }
        else
        {
            String fileUrl = exportNewAS(token, appointmentFilterDTO, timeZone);
            exportResultDTO.setFileUrl(fileUrl);
            return exportResultDTO;
        }
    }

    private void exportASAndSendEmail(Enterprise enterprise, String token, User user, AppointmentFilterDTO appointmentFilterDTO, String timeZone)
    {
        taskExecutor.execute(() -> {
            TenantContext.setCurrentTenant(enterprise.getUuid().toString());
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = transactionManager.getTransaction(def);
            try
            {
                String fileUrl = exportNewAS(token, appointmentFilterDTO, timeZone);
                importExportUtils.sendMailWithExportResult(user, fileUrl);
            }
            catch (IOException | InvalidFormatException | ServiceException | IllegalAccessException e)
            {
                e.printStackTrace();
            }

            transactionManager.commit(status);
        });

    }

    private String exportNewAS(String token, AppointmentFilterDTO appointmentFilterDTO,  String timeZone) throws IOException, ServiceException, InvalidFormatException, IllegalAccessException
    {

        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        File file = new ClassPathResource("/excel/" + EXPORT_APPOINTMENT_AS_TEMPLATE_FILE).getFile();
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet appointmentSheet = workbook.getSheetAt(0);

        List<Appointment> appointmentList = appointmentFtsInternal.getAppointments(token, 0, 1000, "", appointmentFilterDTO);

        List<UUID> appointmentIdList = getUniqueId(appointmentList);

        //get custom field
        List<CustomField> customFieldList = customFieldDAO.findByObjectTypeOrderByPositionAsc(enterprise, ObjectType.APPOINTMENT);
        Map<UUID, Map<UUID, List<CustomFieldValue>>> taskCustomFieldMap = getAppointmentCustomFieldMap(appointmentIdList);

        //gen fist and second title
        Map<Integer, UUID> indexCustomFieldMap = new HashMap<>();
        generateTitleRow(appointmentSheet, appointmentFilterDTO, enterprise, timeZone);
        populateHeaderRowForCustomField(customFieldList, appointmentSheet, indexCustomFieldMap);

        CellStyle evenStyle = createEvenStyle(workbook);
        CellStyle oddStyle = workbook.createCellStyle();

        oddStyle.cloneStyleFrom(evenStyle);
        oddStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        oddStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int i = 2;
        int numberOf = 1;

        List<Appointment> appointmentS = appointmentList;
        for (Appointment appointment : appointmentS)
        {
            Row appointmentSheetRow = appointmentSheet.createRow(i);

            UUID taskId = appointment.getUuid();

            Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap = taskCustomFieldMap.get(taskId);
            if (customFieldIdCustomFieldValueMap == null)
            {
                customFieldIdCustomFieldValueMap = new HashMap<>();
            }
            CellStyle cellStyle = appointmentS.indexOf(appointment) % 2 == 0 ? evenStyle : oddStyle;

            //gen value for row
            generateValueForRow(appointment, appointmentSheetRow, workbook, indexCustomFieldMap, customFieldIdCustomFieldValueMap, cellStyle, numberOf, timeZone);

            numberOf++;
            i++;
        }

        StringBuilder preFileName = importExportUtils.createExportFileName(FileType.XLSX, AppointmentConstant.PRE_EXPORT_FILE_NAME);

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
                                     HttpServletRequest request, HttpServletResponse response, String timeZone) throws
            IOException, ServiceException, InvalidFormatException, IllegalAccessException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        AppointmentFilterDTO appointmentFilterDTO = gson.fromJson(filterDTO, AppointmentFilterDTO.class);

        File file = new ClassPathResource("/excel/" + EXPORT_APPOINTMENT_AS_TEMPLATE_FILE).getFile();
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet appointmentSheet = workbook.getSheetAt(0);

        List<Appointment> appointmentList = appointmentFtsInternal.getAppointments(token, 0, 1000, "", appointmentFilterDTO);

        List<UUID> appointmentIdList = getUniqueId(appointmentList);

        //get custom field
        List<CustomField> customFieldList = customFieldDAO.findByObjectTypeOrderByPositionAsc(enterprise, ObjectType.APPOINTMENT);
        Map<UUID, Map<UUID, List<CustomFieldValue>>> taskCustomFieldMap = getAppointmentCustomFieldMap(appointmentIdList);

        //gen fist and second title
        Map<Integer, UUID> indexCustomFieldMap = new HashMap<>();
        generateTitleRow(appointmentSheet, appointmentFilterDTO, enterprise, timeZone);
        populateHeaderRowForCustomField(customFieldList, appointmentSheet, indexCustomFieldMap);

        CellStyle evenStyle = createEvenStyle(workbook);
        CellStyle oddStyle = workbook.createCellStyle();

        oddStyle.cloneStyleFrom(evenStyle);
        oddStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        oddStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int i = 2;
        int numberOf = 1;

        List<Appointment> appointmentS = appointmentList;
        for (Appointment appointment : appointmentS)
        {
            Row appointmentSheetRow = appointmentSheet.createRow(i);

            UUID taskId = appointment.getUuid();

            Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap = taskCustomFieldMap.get(taskId);
            if (customFieldIdCustomFieldValueMap == null)
            {
                customFieldIdCustomFieldValueMap = new HashMap<>();
            }
            CellStyle cellStyle = appointmentS.indexOf(appointment) % 2 == 0 ? evenStyle : oddStyle;

            //gen value for row
            generateValueForRow(appointment, appointmentSheetRow, workbook, indexCustomFieldMap, customFieldIdCustomFieldValueMap, cellStyle, numberOf, timeZone);

            numberOf++;
            i++;
        }

        String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        response.setContentType(mimeType);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("filename=\"%s\"", "Appointments.xlsx");
        response.setHeader(headerKey, headerValue);

        OutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        outputStream.close();
    }

    //gen first row with logo sale box
    private void generateTitleRow(Sheet appointmentSheet, AppointmentFilterDTO appointmentFilterDTO, Enterprise
            enterprise, String timeZone)
    {
        Row titleTaskRow = appointmentSheet.getRow(0);
        Cell ownerCell = titleTaskRow.getCell(3);
        Cell startCell = titleTaskRow.getCell(4);
        Cell endCell = titleTaskRow.getCell(5);

        String roleName = "";

        if (appointmentFilterDTO.getRoleFilterType().equals("Person"))
        {
            User user = userDAO.findOne(UUID.fromString(appointmentFilterDTO.getRoleFilterValue()));
            if (user != null)
            {
                roleName = user.getSharedContact().getFirstName() + " " + user.getSharedContact().getLastName();
            }
        }
        else if (appointmentFilterDTO.getRoleFilterType().equals("Unit"))
        {
            Unit unit = unitDAO.findOne(UUID.fromString(appointmentFilterDTO.getRoleFilterValue()));
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
        if (!appointmentFilterDTO.getIsFilterAll())
        {
            startCell.setCellValue(dateFormat.format(new Date(appointmentFilterDTO.getStartDate().getTime() + diffTime)));
            endCell.setCellValue(dateFormat.format(new Date(appointmentFilterDTO.getEndDate().getTime() - 1)));
        }
    }

    //gen header for custom field
    private void populateHeaderRowForCustomField(List<CustomField> customFieldList, Sheet taskSheet,
                                                 Map<Integer, UUID> indexCustomFieldMap)
    {
        Row headerRow = taskSheet.getRow(1);
        int index = 0;
        int nextHeaderIndex = ExportAppointmentASColumnIndex.BEGIN_CUSTOM_FIELD.index;
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

    private void generateValueForRow(Appointment appointment, Row appointmentRow, Workbook
            workbook, Map<Integer, UUID> indexCustomFieldMap,
                                     Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap,
                                     CellStyle cellStyle, int numberOf, String timeZone)
    {
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(cellStyle);
        DataFormat poiFormat = workbook.createDataFormat();
        String excelFormatPattern = DateFormatConverter.convert(Locale.ENGLISH, "mm/dd/yyyy");
        dateStyle.setDataFormat(poiFormat.getFormat(excelFormatPattern));

        int rowNumber = appointmentRow.getRowNum();

        generateAppointmentData(appointment, appointmentRow, cellStyle, dateStyle, numberOf, timeZone);

        int index = ExportAppointmentASColumnIndex.BEGIN_CUSTOM_FIELD.index;
        do
        {
            UUID customFieldId = indexCustomFieldMap.get(index - ExportAppointmentASColumnIndex.BEGIN_CUSTOM_FIELD.index);
            List<CustomFieldValue> customFieldValueList = customFieldIdCustomFieldValueMap.get(customFieldId);
            boolean hasValue = false;
            Cell customFieldCell = appointmentRow.createCell(index);
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
        } while (index < ExportAppointmentASColumnIndex.BEGIN_CUSTOM_FIELD.index + indexCustomFieldMap.size());
    }

    private void generateAppointmentData(Appointment appointment, Row appointmentSheetRow, CellStyle
            cellStyle, CellStyle dateStyle, int numberOf, String timeZone)
    {
        //No
        Cell noCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.NO.getIndex());
        noCell.setCellValue(String.valueOf(numberOf));
        noCell.setCellStyle(cellStyle);

        //Title
        Cell titleCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.TITLE.getIndex());
        if (appointment.getTitle() != null)
        {
            titleCell.setCellValue(appointment.getTitle());
        }
        else
        {
            titleCell.setCellValue("");
        }
        titleCell.setCellStyle(cellStyle);

        //Account Name
        Cell accountNameCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.ACCOUNT_NAME.getIndex());
        if (appointment.getOrganisation() != null)
        {
            accountNameCell.setCellValue(appointment.getOrganisation().getName());
        }
        else
        {
            accountNameCell.setCellValue("");
        }
        accountNameCell.setCellStyle(cellStyle);

        //Contact First Name
        Cell contactFirstNameCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.CONTACT_FIRST_NAME.getIndex());
        if (appointment.getFirstContact() != null)
        {
            String contactFirstNameTxt = appointment.getFirstContact().getFirstName();
            if (contactFirstNameTxt != null)
            {
                contactFirstNameCell.setCellValue(contactFirstNameTxt);
            }
            else
            {
                contactFirstNameCell.setCellValue("");
            }
        }
        else
        {
            contactFirstNameCell.setCellValue("");
        }
        contactFirstNameCell.setCellStyle(cellStyle);

        //Contact Last Name
        Cell contactLastNameCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.CONTACT_LAST_NAME.getIndex());
        if (appointment.getFirstContact() != null)
        {
            String contactLastNameTxt = appointment.getFirstContact().getLastName();
            if (contactLastNameTxt != null)
            {
                contactLastNameCell.setCellValue(contactLastNameTxt);
            }
            else
            {
                contactLastNameCell.setCellValue("");
            }
        }
        else
        {
            contactLastNameCell.setCellValue("");
        }
        contactLastNameCell.setCellStyle(cellStyle);

        //Focus
        Cell focusCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.FOCUS.getIndex());
        if (appointment.getFocusActivity() == null)
        {
            //check FocusWorkData
            if (appointment.getFocusWorkData() == null)
            {
                focusCell.setCellValue("");
            }
            else
            {
                focusCell.setCellValue(appointment.getFocusWorkData().getName());
            }
        }
        else
        {
            focusCell.setCellValue(appointment.getFocusActivity().getName());
        }
        focusCell.setCellStyle(cellStyle);

        //Locations
        Cell locationCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.LOCATIONS.getIndex());
        if (appointment.getLocation() != null)
        {
            locationCell.setCellValue(appointment.getLocation());
        }
        else
        {
            locationCell.setCellValue("");
        }
        locationCell.setCellStyle(cellStyle);

        //Start
        Cell startCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.START.getIndex());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        long diffTime = (serverTimezone + Integer.parseInt(timeZone)) * 60 * 60 * 1000;
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        if (appointment.getStartDate() != null)
        {
            startCell.setCellValue(dateFormat.format(new Date(appointment.getStartDate().getTime() + diffTime)));
        }
        else
        {
            startCell.setCellValue("");
        }
        startCell.setCellStyle(cellStyle);

        //End
        Cell endCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.END.getIndex());
        if (appointment.getEndDate() != null)
        {
            endCell.setCellValue(dateFormat.format(new Date(appointment.getEndDate().getTime() + diffTime)));
        }
        else
        {
            endCell.setCellValue("");
        }
        endCell.setCellStyle(cellStyle);

        //Invitess
        List<Contact> contactList = appointmentInviteeContactDAO.findContactByAppointment(appointment);
        List<Communication> communicationList = appointmentInviteeInviteeDAO.findCommunicationByAppointment(appointment);
        StringBuilder invitessTxt = new StringBuilder();
        if (contactList.size() > 0)
        {
            for (int i = 0; i < contactList.size(); )
            {
                Contact contact = contactList.get(i);
                if (contact.getFirstName() != null)
                {
                    invitessTxt.append(contact.getFirstName() + " ");
                }
                if (contact.getLastName() != null)
                {
                    invitessTxt.append(contact.getLastName());
                }
                if (i != contactList.size() - 1)
                {
                    invitessTxt.append(", ");
                }
                i++;
            }
        }
        if (communicationList.size() > 0)
        {
            if (contactList.size() > 0)
            {
                invitessTxt.append(", ");
            }
            for (int i = 0; i < communicationList.size(); )
            {
                Communication communication = communicationList.get(i);
                if (communication.getValue() != null)
                {
                    invitessTxt.append(communication.getValue());
                }
                if (i != communicationList.size() - 1)
                {
                    invitessTxt.append(", ");
                }
                i++;
            }
        }
        Cell invitessCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.INVITEES.getIndex());
        if (invitessTxt != null)
        {
            invitessCell.setCellValue(invitessTxt.toString());
        }
        else
        {
            invitessCell.setCellValue("");
        }
        invitessCell.setCellStyle(cellStyle);

        //Note
        Cell noteCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.NOTE.getIndex());
        if (appointment.getNote() != null)
        {
            noteCell.setCellValue(appointment.getNote());
        }
        else
        {
            noteCell.setCellValue("");
        }
        noteCell.setCellStyle(cellStyle);

        //Lead  & Opp
        AppointmentRelation appointmentRelation = appointmentRelationDAO.findByAppointmentIdAndRelationType(appointment.getUuid(), RelationConstant.APPOINTMENT_LEAD);

        if (appointment.getProspectId() != null)
        {
            Cell leadCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.LEAD.getIndex());
            leadCell.setCellValue("");
            leadCell.setCellStyle(cellStyle);

            Cell oppCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.OPPORTUNITY.getIndex());
            if (prospectBaseDAO.findOne(appointment.getProspectId()) != null)
            {
                String oppCellText = prospectBaseDAO.findOne(appointment.getProspectId()).getDescription();
                if (oppCellText != null)
                {
                    oppCell.setCellValue(oppCellText);
                }
                else
                {
                    oppCell.setCellValue("");
                }
            }
            else
            {
                oppCell.setCellValue("");
            }
            oppCell.setCellStyle(cellStyle);
        }
        else
        {
            Cell leadCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.LEAD.getIndex());
            String leadInCell = "";
            if (appointmentRelation != null)
            {
                Lead lead = leadDAO.findOne(appointmentRelation.getRelationObjectUuid());
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
            else
            {
                leadInCell = "";
            }
            leadCell.setCellValue(leadInCell);
            leadCell.setCellStyle(cellStyle);

            Cell oppCell = appointmentSheetRow.createCell(ExportAppointmentASColumnIndex.OPPORTUNITY.getIndex());
            oppCell.setCellValue("");
            oppCell.setCellStyle(cellStyle);
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

    //get custom field
    private Map<UUID, Map<UUID, List<CustomFieldValue>>> getAppointmentCustomFieldMap
    (List<UUID> appointmentIdList)
    {
        Map<UUID, Map<UUID, List<CustomFieldValue>>> taskCustomFieldMap = new HashMap<>();
        for (UUID uuid : appointmentIdList)
        {
            taskCustomFieldMap.put(uuid, new HashMap<UUID, List<CustomFieldValue>>());
        }

        List<CustomFieldValue> customFieldValueList = customFieldValueDAO.findByCustomFieldAndObjectIdIn(appointmentIdList);
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
    private List<UUID> getUniqueId(List<Appointment> appointmentList)
    {
        List<UUID> uuidList = new ArrayList<>();
        if (appointmentList.size() > 0)
        {
            for (Appointment appointment : appointmentList)
            {
                uuidList.add(appointment.getUuid());
            }
        }
        return uuidList;
    }


    //enum of task column
    enum ExportAppointmentASColumnIndex
    {
        NO(0),
        TITLE(1),
        ACCOUNT_NAME(2),
        CONTACT_FIRST_NAME(3),
        CONTACT_LAST_NAME(4),
        FOCUS(5),
        LOCATIONS(6),
        START(7),
        END(8),
        INVITEES(9),
        NOTE(10),
        LEAD(11),
        OPPORTUNITY(12),
        BEGIN_CUSTOM_FIELD(13),
        ;
        private int index;

        ExportAppointmentASColumnIndex(int index)
        {
            this.index = index;
        }

        public int getIndex()
        {
            return this.index;
        }
    }
}
