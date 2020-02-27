package com.salesbox.service.prospect;

import com.google.gson.Gson;
import com.salesbox.common.BaseService;
import com.salesbox.common.Constant;
import com.salesbox.common.multitenant.TenantContext;
import com.salesbox.dao.*;
import com.salesbox.dto.ExportDTO;
import com.salesbox.dto.ExportResultDTO;
import com.salesbox.entity.*;
import com.salesbox.entity.enums.CustomFieldType;
import com.salesbox.entity.enums.FileType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.entity.enums.WorkDataType;
import com.salesbox.exception.ServiceException;
import com.salesbox.file.AmazonS3FileAccessor;
import com.salesbox.file.FileAccessor;
import com.salesbox.message.ImportExportMessage;
import com.salesbox.prospect.dto.CountProspectDTO;
import com.salesbox.prospect.dto.ProspectDTO;
import com.salesbox.prospect.dto.ProspectFilterDTO;
import com.salesbox.prospect.dto.ProspectListDTO;
import com.salesbox.prospect.constant.ProspectConstant;
import com.salesbox.utils.ExportOrderRowUtils;
import com.salesbox.utils.ExportProspectUtils;
import com.salesbox.service.prospect.web.*;
import com.salesbox.utils.CommonUtils;
import com.salesbox.utils.CustomFieldValueUpdatedDateComparator;
import com.salesbox.utils.ImportExportUtils;
import com.salesbox.utils.ProspectUtils;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by: hunglv
 * Date: 10/2/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExportProspectServiceInternal extends BaseService
{
    @Autowired
    FileAccessor fileAccessor;

    /*@Autowired
    IBMObjectStorageFileAccessor ibmObjectStorageFileAccessor;*/

    @Autowired
    AmazonS3FileAccessor amazonS3FileAccessor;

    @Autowired
    ImportExportUtils importExportUtils;
    @Autowired ProspectUtils prospectUtils;
    @Autowired
    ImportExportHistoryDAO importExportHistoryDAO;
    @Autowired
    ProspectUserDAO prospectUserDAO;
    @Autowired
    ProspectContactDAO prospectContactDAO;
    @Autowired
    OrderRowDAO orderRowDAO;
    @Autowired
    NoteDAO noteDAO;
    @Autowired
    ExportProspectUtils exportProspectUtils;
    @Autowired
    ExportOrderRowUtils exportOrderRowUtils;
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
    ProspectHistoricDAO prospectHistoricDAO;
    @Autowired
    ProspectActiveDAO prospectActiveDAO;
    @Autowired
    ProspectActiveFtsService prospectActiveFtsService;
    @Autowired
    ProspectHistoricFtsService prospectHistoricFtsService;
    @Autowired
    ActivityDAO activityDAO;
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    PlatformTransactionManager transactionManager;

    private static final String EXPORT_OPP_AS_TEMPLATE_FILE = "Salesbox_AS_Opp.xlsx";

    private Integer BEGIN_CUSTOM_FIELD_PRODUCT;
    private Integer CURRENT_STEP;
    private Integer NEXT_STEP;
    private Integer LAST_NOTE;
    private Integer BEGIN_CUSTOM_FIELD;

    public ExportResultDTO exportNew(String token) throws IOException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);


        ExportResultDTO exportResultDTO = new ExportResultDTO();

        long numberProspect = prospectActiveDAO.countByEnterprise(enterprise) + prospectHistoricDAO.countByEnterprise(enterprise);
        if (numberProspect > 30)
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
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFSheet sheetOrderRow = workbook.createSheet();

        String sheetProspectName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.PROSPECT_SHEET_NAME.toString());
        String sheetOrderRowName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.ORDER_ROW_SHEET_NAME.toString());
        if (sheetProspectName == null)
        {
            sheetProspectName = "Qualified deals";
        }
        if (sheetOrderRowName == null)
        {
            sheetOrderRowName = "OrderRows";
        }
        workbook.setSheetName(0, sheetProspectName);
        workbook.setSheetName(1, sheetOrderRowName);

        CellStyle headStyle = importExportUtils.createHeadStyle(workbook);
        exportProspectUtils.createHeaderRowForExportProspect(sheet, headStyle, enterprise);
        exportOrderRowUtils.createHeaderRowForOrderRow(sheetOrderRow, headStyle, enterprise);

        List<ProspectBase> prospectList = new ArrayList<>();
        List<ProspectActive> prospectActiveList = prospectActiveDAO.findByEnterprise(enterprise);
        List<ProspectHistoric> prospectHistoricList = prospectHistoricDAO.findByEnterprise(enterprise);
        prospectList.addAll(prospectActiveList);
        prospectList.addAll(prospectHistoricList);

        List<UUID> prospectIds = new ArrayList<>();
        for (ProspectBase prospect : prospectList)
        {
            prospectIds.add(prospect.getUuid());
        }

        List<OrderRow> orderRowList = new ArrayList<>();
        if (prospectList.size() > 0)
        {
            orderRowList = orderRowDAO.findByProspectIdIn(prospectIds);
        }

        Set<UUID> prospectIdSet = new HashSet<>();
        for (ProspectBase prospect : prospectList)
        {
            prospectIdSet.add(prospect.getUuid());
        }

        List<ProspectContact> prospectContactList = prospectContactDAO.findByProspectInOrderByIndexAsc(prospectIdSet);

        List<UUID> listProspectId = new ArrayList<>();
        for (ProspectBase prospectBase : prospectList)
        {
            listProspectId.add(prospectBase.getUuid());
        }
        List<ProspectUser> prospectUserList = prospectUserDAO.findByProspectInOrderByIndexAsc(listProspectId);

        int i = 0;
        for (ProspectBase prospect : prospectList)
        {
            i++;
            Row contentRow = sheet.createRow(i);
            exportProspectUtils.generateValueForRow(prospect, contentRow, workbook, prospectContactList, prospectUserList);
        }

        int j = 0;
        for (OrderRow orderRow : orderRowList)
        {
            j++;
            Row contentRow = sheetOrderRow.createRow(j);
            exportOrderRowUtils.generateValueForRow(orderRow, contentRow, workbook);
        }

        StringBuilder preFileName = importExportUtils.createExportFileName(FileType.XLS, ProspectConstant.PRE_EXPORT_FILE_NAME);
        String finalFileName = enterprise.getSharedOrganisation().getName() + "_" + preFileName.toString();

        ByteArrayOutputStream exportOutputStream = new ByteArrayOutputStream();
        workbook.write(exportOutputStream);
        exportOutputStream.close();

        InputStream exportInputStream = new ByteArrayInputStream(exportOutputStream.toByteArray());

        String fileURL = amazonS3FileAccessor.saveExportFile(finalFileName.replaceAll("\\s+", ""), exportInputStream);
        System.out.println(fileURL);

        return fileURL;

    }

    public ExportDTO exportProspect(String token) throws IOException, ServiceException, EncoderException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFSheet sheetOrderRow = workbook.createSheet();

        String sheetProspectName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.PROSPECT_SHEET_NAME.toString());
        String sheetOrderRowName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.ORDER_ROW_SHEET_NAME.toString());
        if (sheetProspectName == null)
        {
            sheetProspectName = "Qualified deals";
        }
        if (sheetOrderRowName == null)
        {
            sheetOrderRowName = "OrderRows";
        }
        workbook.setSheetName(0, sheetProspectName);
        workbook.setSheetName(1, sheetOrderRowName);

        CellStyle headStyle = importExportUtils.createHeadStyle(workbook);
        exportProspectUtils.createHeaderRowForExportProspect(sheet, headStyle, enterprise);
        exportOrderRowUtils.createHeaderRowForOrderRow(sheetOrderRow, headStyle, enterprise);

        List<ProspectBase> prospectList = new ArrayList<>();
        List<ProspectActive> prospectActiveList = prospectActiveDAO.findByEnterprise(enterprise);
        List<ProspectActive> prospectHistoricList = prospectActiveDAO.findByEnterprise(enterprise);
        prospectList.addAll(prospectActiveList);
        prospectList.addAll(prospectHistoricList);

        importExportUtils.checkSizeExport(prospectList.size());

        List<UUID> prospectIds = new ArrayList<>();
        for (ProspectBase prospect : prospectList)
        {
            prospectIds.add(prospect.getUuid());
        }

        List<OrderRow> orderRowList = new ArrayList<>();
        if (prospectList.size() > 0)
        {
            orderRowList = orderRowDAO.findByProspectIdIn(prospectIds);
        }

        Set<UUID> prospectIdSet = new HashSet<>();
        for (ProspectBase prospect : prospectList)
        {
            prospectIdSet.add(prospect.getUuid());
        }

        List<ProspectContact> prospectContactList = prospectContactDAO.findByProspectInOrderByIndexAsc(prospectIdSet);

        List<UUID> listProspectId = new ArrayList<>();
        for (ProspectBase prospectBase : prospectList)
        {
            listProspectId.add(prospectBase.getUuid());
        }
        List<ProspectUser> prospectUserList = prospectUserDAO.findByProspectInOrderByIndexAsc(listProspectId);

        int i = 0;
        for (ProspectBase prospect : prospectList)
        {
            i++;
            Row contentRow = sheet.createRow(i);
            exportProspectUtils.generateValueForRow(prospect, contentRow, workbook, prospectContactList, prospectUserList);
        }

        int j = 0;
        for (OrderRow orderRow : orderRowList)
        {
            j++;
            Row contentRow = sheetOrderRow.createRow(j);
            exportOrderRowUtils.generateValueForRow(orderRow, contentRow, workbook);
        }

        StringBuilder fileName = importExportUtils.createExportFileName(FileType.XLS, ProspectConstant.PRE_EXPORT_FILE_NAME);
        ImportExportHistory exportHistory = importExportUtils.generateExportHistory(user, enterprise, fileName, ObjectType.OPPORTUNITY);
        importExportHistoryDAO.save(exportHistory);

        putResultFileToAmazon(workbook, exportHistory.getUuid().toString());

        ExportDTO exportDTO = new ExportDTO();
        exportDTO.setFileId(exportHistory.getUuid().toString());
        exportDTO.setFileName(fileName.toString());
        exportDTO.setToEmail(user.getUsername());
        StringBuilder subject = importExportUtils.createSubjectExportName(ProspectConstant.PRE_SUBJECT_EXPORT);
        exportDTO.setSubject(subject.toString());

        return exportDTO;
    }

    @Async
    public void putResultFileToAmazon(Workbook workbook, String fileId) throws IOException, EncoderException
    {
        java.io.File exportFile = fileAccessor.selectFileFromFileName(fileId);
        OutputStream exportStream = new FileOutputStream(exportFile);
        workbook.write(exportStream);
        exportStream.close();

        amazonS3FileAccessor.saveDocumentFile(fileId, fileAccessor.selectFileFromFileName(fileId));
    }

    public ExportResultDTO exportDirectFile(String token, HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFSheet sheetOrderRow = workbook.createSheet();

        String sheetProspectName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.PROSPECT_SHEET_NAME.toString());
        String sheetOrderRowName = getLocalizationValueByLanguageAndCode(enterprise.getLanguage(), ImportExportMessage.ORDER_ROW_SHEET_NAME.toString());
        if (sheetProspectName == null)
        {
            sheetProspectName = "Qualified deals";
        }
        if (sheetOrderRowName == null)
        {
            sheetOrderRowName = "OrderRows";
        }
        workbook.setSheetName(0, sheetProspectName);
        workbook.setSheetName(1, sheetOrderRowName);

        CellStyle headStyle = importExportUtils.createHeadStyle(workbook);
        exportProspectUtils.createHeaderRowForExportProspect(sheet, headStyle, enterprise);
        exportOrderRowUtils.createHeaderRowForOrderRow(sheetOrderRow, headStyle, enterprise);

        List<ProspectBase> prospectList = new ArrayList<>();
        List<ProspectActive> prospectActives = prospectActiveDAO.findByEnterprise(enterprise);
        List<ProspectHistoric> prospectHistorics = prospectHistoricDAO.findByEnterprise(enterprise);
        prospectList.addAll(prospectActives);
        prospectList.addAll(prospectHistorics);

        importExportUtils.checkSizeExport(prospectList.size());

        List<UUID> prospectIds = new ArrayList<>();
        for (ProspectBase prospect : prospectList)
        {
            prospectIds.add(prospect.getUuid());
        }

        List<OrderRow> orderRowList = new ArrayList<>();
        if (prospectList.size() > 0)
        {
            orderRowList = orderRowDAO.findByProspectIdIn(prospectIds);
        }
        Set<UUID> prospectIdSet = new HashSet<>();
        for (ProspectBase prospect : prospectList)
        {
            prospectIdSet.add(prospect.getUuid());
        }

        List<ProspectContact> prospectContactList = prospectContactDAO.findByProspectInOrderByIndexAsc(prospectIdSet);
        List<ProspectUser> prospectUserList = prospectUserDAO.findByProspectInOrderByIndexAsc(prospectIdSet);

        int i = 0;
        for (ProspectBase prospect : prospectList)
        {
            i++;
            Row contentRow = sheet.createRow(i);
            exportProspectUtils.generateValueForRow(prospect, contentRow, workbook, prospectContactList, prospectUserList);
        }

        int j = 0;
        for (OrderRow orderRow : orderRowList)
        {
            j++;
            Row contentRow = sheetOrderRow.createRow(j);
            exportOrderRowUtils.generateValueForRow(orderRow, contentRow, workbook);
        }

        StringBuilder fileName = importExportUtils.createExportFileName(FileType.XLS, ProspectConstant.PRE_EXPORT_FILE_NAME);
        ImportExportHistory exportHistory = importExportUtils.generateExportHistory(user, enterprise, fileName, ObjectType.OPPORTUNITY);
        importExportHistoryDAO.save(exportHistory);

        String mimeType = "application/excel";
        response.setContentType(mimeType);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);

        OutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        outputStream.close();

        return null;

    }


    public ExportResultDTO exportAdvancedSearch(String token, String filterDTO,
                                                HttpServletRequest request, HttpServletResponse response, String timeZone) throws IOException, ServiceException, InvalidFormatException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        ProspectFilterDTO prospectFilterDTO = gson.fromJson(filterDTO, ProspectFilterDTO.class);

        CountProspectDTO countDTO = prospectFilterDTO.getCustomFilter().equals("history")
                ? prospectHistoricFtsService.countHistoricProspect(token, prospectFilterDTO, user, enterprise)
                : prospectActiveFtsService.countActiveProspect(prospectFilterDTO, user, enterprise);
        ExportResultDTO exportResultDTO = new ExportResultDTO();

        if (countDTO.getCount() > 30)
        {
            exportResultDTO.setSendEmail(true);
            exportASAndSendEmail(enterprise, token, user, prospectFilterDTO, timeZone);
            return exportResultDTO;

        }
        else
        {
            String fileUrl = exportNewAS(token, prospectFilterDTO, timeZone);
            exportResultDTO.setFileUrl(fileUrl);
            return exportResultDTO;
        }
    }

    private void exportASAndSendEmail(Enterprise enterprise, String token, User user, ProspectFilterDTO prospectFilterDTO, String timeZone)
    {
        taskExecutor.execute(() -> {
            TenantContext.setCurrentTenant(enterprise.getUuid().toString());
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = transactionManager.getTransaction(def);

            try
            {
                String fileUrl = exportNewAS(token, prospectFilterDTO, timeZone);
                importExportUtils.sendMailWithExportResult(user, fileUrl);
            }
            catch (IOException | InvalidFormatException e)
            {
                e.printStackTrace();
            }

            transactionManager.commit(status);
        });

    }

    private String exportNewAS(String token, ProspectFilterDTO prospectFilterDTO, String timeZone) throws IOException, InvalidFormatException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        java.io.File file = new ClassPathResource("/excel/" + EXPORT_OPP_AS_TEMPLATE_FILE).getFile();
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet oppSheet = workbook.getSheetAt(0);

        ProspectListDTO prospectListDTO = new ProspectListDTO();
        boolean isHistoric = prospectFilterDTO.getCustomFilter().equals("history");
        String currency = workDataWorkDataDAO.findByEnterpriseAndTypeAndName(enterprise, WorkDataType.WORK_LOAD, Constant.CURRENCY).getValue();


        prospectListDTO = prospectFilterDTO.getCustomFilter().equals("history")
                ? prospectHistoricFtsService.getProspectListDTO(prospectFilterDTO, 0, 1000, user, enterprise)
                : prospectActiveFtsService.getProspectListDTO(prospectFilterDTO, 0, 1000, user, enterprise);


        List<UUID> prospectIdList = getUniqueId(prospectListDTO);
        Map<UUID, String> prospectNoteMap = getProspectNoteMap(prospectIdList);
        Map<UUID, List<OrderRow>> orderRowProspectMap = getProspectOrderRowMap(prospectIdList);

        List<UUID> currentStepIds = new ArrayList<>();
        List<Activity> allCurrentStepList = new ArrayList<>();

        if(!prospectFilterDTO.getCustomFilter().equals("history"))
        {
            currentStepIds = prospectListDTO.getProspectDTOList().stream()
                    .filter(x -> Objects.nonNull(x.getCurrentStepId()))
                    .map(ProspectDTO::getCurrentStepId)
                    .collect(Collectors.toList());

            if (!currentStepIds.isEmpty())
            {
                allCurrentStepList = activityDAO.findByUuidIn(currentStepIds);
            }
        }


        //List custom field of Opp
        List<CustomField> customFieldList = customFieldDAO.findByObjectTypeOrderByPositionAsc(enterprise, ObjectType.OPPORTUNITY);

        //List custom field of Product
        List<CustomField> customFieldProduct = customFieldDAO.findByObjectTypeOrderByPositionAsc(enterprise, ObjectType.PRODUCT_REGISTER);

        if (customFieldProduct != null && customFieldProduct.size() > 0)
        {
            CURRENT_STEP = ExportProspectASColumnIndex.DELIVERY_END.getIndex() + customFieldProduct.size() + 1;
        }
        else
        {
            CURRENT_STEP = ExportProspectASColumnIndex.DELIVERY_END.getIndex() + 1;
        }

        //index of lastnote and customfield of Opp
        NEXT_STEP = CURRENT_STEP + 1;
        LAST_NOTE = NEXT_STEP + 1;

        if (customFieldList.size() > 0)
        {
            BEGIN_CUSTOM_FIELD = LAST_NOTE + 1;
        }

        if (customFieldProduct.size() > 0)
        {
            BEGIN_CUSTOM_FIELD_PRODUCT = ExportProspectASColumnIndex.DELIVERY_END.getIndex() + 1;
        }

        Map<UUID, Map<UUID, List<CustomFieldValue>>> prospectCustomFieldMap = getProspectCustomFieldMap(prospectIdList);


        Map<UUID, Map<UUID, List<CustomFieldValue>>> customFieldOrderRowMap = getOrderRowCustomFieldMap(prospectIdList);


        generateTitleRow(oppSheet, prospectFilterDTO, enterprise, timeZone);

        Map<Integer, UUID> indexCustomFieldMap = new HashMap<>();

        Map<Integer, UUID> indexCustomFieldProductMap = new HashMap<>();

        populateHeaderRow(customFieldList, customFieldProduct, oppSheet, indexCustomFieldMap, indexCustomFieldProductMap, currency);

        CellStyle evenStyle = createEvenStyle(workbook);
        CellStyle oddStyle = workbook.createCellStyle();

        oddStyle.cloneStyleFrom(evenStyle);
        oddStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        oddStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int i = 2;
        List<ProspectDTO> prospectDTOList = prospectListDTO.getProspectDTOList();
        for (ProspectDTO prospectDTO : prospectDTOList)
        {
            Row oppSheetRow = oppSheet.createRow(i);

            UUID prospectId = prospectDTO.getUuid();
            List<OrderRow> orderRowList = orderRowProspectMap.get(prospectId) == null ? new ArrayList<>() : orderRowProspectMap.get(prospectId);

            Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap = prospectCustomFieldMap.get(prospectId);
            if (customFieldIdCustomFieldValueMap == null)
            {
                customFieldIdCustomFieldValueMap = new HashMap<>();
            }

            CellStyle cellStyle = prospectDTOList.indexOf(prospectDTO) % 2 == 0 ? evenStyle : oddStyle;

            generateValueForRow(prospectDTO, oppSheetRow, workbook, prospectNoteMap.get(prospectId), orderRowList,
                    indexCustomFieldMap, customFieldIdCustomFieldValueMap, indexCustomFieldProductMap, customFieldOrderRowMap,
                    allCurrentStepList,
                    cellStyle, isHistoric, timeZone);

            if (orderRowList.size() > 1)
            {
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 0, 0));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 1, 1));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 2, 2));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 3, 3));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 4, 4));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 5, 5));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 6, 6));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 7, 7));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 8, 8));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 9, 9));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 10, 10));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 11, 11));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), 12, 12));

                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), LAST_NOTE, LAST_NOTE));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), CURRENT_STEP, CURRENT_STEP));
                oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), NEXT_STEP, NEXT_STEP));

                if (BEGIN_CUSTOM_FIELD != null)
                {
                    for (int startCustomField = BEGIN_CUSTOM_FIELD; startCustomField < (BEGIN_CUSTOM_FIELD + customFieldList.size()); startCustomField++)
                    {
                        oppSheet.addMergedRegion(new CellRangeAddress(i, (i + orderRowList.size() - 1), startCustomField, startCustomField));
                    }
                }
            }

            if (orderRowList.size() > 0)
            {
                i = i + orderRowList.size();
            }
        }

        StringBuilder preFileName = importExportUtils.createExportFileName(FileType.XLSX, ProspectConstant.PRE_EXPORT_FILE_NAME);

        String finalFileName = enterprise.getSharedOrganisation().getName() + "_AS_" + preFileName.toString();

        ByteArrayOutputStream exportOutputStream = new ByteArrayOutputStream();
        workbook.write(exportOutputStream);
        exportOutputStream.close();

        InputStream exportInputStream = new ByteArrayInputStream(exportOutputStream.toByteArray());

        String fileURL = amazonS3FileAccessor.saveExportFile(finalFileName.replaceAll("\\s+", ""), exportInputStream);
        System.out.println(fileURL);

        return fileURL;

    }


    private void populateHeaderRow(List<CustomField> customFieldList,
                                   List<CustomField> customFieldProduct,
                                   Sheet oppSheet,
                                   Map<Integer, UUID> indexCustomFieldMap,
                                   Map<Integer, UUID> indexCustomFieldProductMap,
                                   String currency)
    {
        Row headerRow = oppSheet.getRow(1);

        int index = 0;

        if (customFieldList.size() > 0)
        {
            int nextHeaderIndex = BEGIN_CUSTOM_FIELD;
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

        //bind header of custom field of product
        int indexProduct = 0;
        int nextHeaderCustomfieldProduct = ExportProspectASColumnIndex.DELIVERY_END.getIndex() + 1;
        for (CustomField customFieldTemp : customFieldProduct)
        {
            Cell cell = headerRow.createCell(nextHeaderCustomfieldProduct);
            cell.setCellStyle(headerRow.getCell(0).getCellStyle());
            cell.setCellValue(customFieldTemp.getTitle());
            indexCustomFieldProductMap.put(indexProduct, customFieldTemp.getUuid());
            indexProduct++;
            nextHeaderCustomfieldProduct++;
        }


        String currencyInfo = " (" + currency + ")";
        Cell cellValue = headerRow.getCell(ExportProspectASColumnIndex.VALUE.getIndex());
        cellValue.setCellValue(cellValue.getStringCellValue() + currencyInfo);

        Cell cellWeight = headerRow.getCell(ExportProspectASColumnIndex.WEIGHT.getIndex());
        cellWeight.setCellValue(cellWeight.getStringCellValue() + currencyInfo);

        Cell cellProfit = headerRow.getCell(ExportProspectASColumnIndex.PROFIT.getIndex());
        cellProfit.setCellValue(cellProfit.getStringCellValue() + currencyInfo);

        Cell cellPriceUnit = headerRow.getCell(ExportProspectASColumnIndex.PRICE_UNIT.getIndex());
        cellPriceUnit.setCellValue(cellPriceUnit.getStringCellValue() + currencyInfo);

        Cell cellDisPercent = headerRow.getCell(ExportProspectASColumnIndex.DISCOUNT_PERCENT.getIndex());
        cellDisPercent.setCellValue(cellDisPercent.getStringCellValue());

        Cell cellDisPrice = headerRow.getCell(ExportProspectASColumnIndex.DISCOUNT_PRICE.getIndex());
        cellDisPrice.setCellValue(cellDisPrice.getStringCellValue() + currencyInfo);

        Cell cellDisAmount = headerRow.getCell(ExportProspectASColumnIndex.DISCOUNT_AMOUNT.getIndex());
        cellDisAmount.setCellValue(cellDisAmount.getStringCellValue() + currencyInfo);

        Cell cellOrderRow = headerRow.getCell(ExportProspectASColumnIndex.ORDER_ROWS_VALUE.getIndex());
        cellOrderRow.setCellValue(cellOrderRow.getStringCellValue() + currencyInfo);

        Cell cellOrderRowProfit = headerRow.getCell(ExportProspectASColumnIndex.ORDER_ROWS_PROFIT.getIndex());
        cellOrderRowProfit.setCellValue(cellOrderRowProfit.getStringCellValue() + currencyInfo);

        Cell currentStepCell = headerRow.getCell(CURRENT_STEP);
        if (currentStepCell == null)
        {
            headerRow.createCell(CURRENT_STEP);
            currentStepCell = headerRow.getCell(CURRENT_STEP);
        }
        currentStepCell.setCellStyle(headerRow.getCell(0).getCellStyle());
        currentStepCell.setCellValue("Current step");

        Cell nextStepRow = headerRow.getCell(NEXT_STEP);
        if (nextStepRow == null)
        {
            headerRow.createCell(NEXT_STEP);
            nextStepRow = headerRow.getCell(NEXT_STEP);
        }
        nextStepRow.setCellStyle(headerRow.getCell(0).getCellStyle());
        nextStepRow.setCellValue("Next step");

        Cell lastNoteRow = headerRow.getCell(LAST_NOTE);
        if (lastNoteRow == null)
        {
            headerRow.createCell(LAST_NOTE);
            lastNoteRow = headerRow.getCell(LAST_NOTE);
        }
        lastNoteRow.setCellStyle(headerRow.getCell(0).getCellStyle());
        lastNoteRow.setCellValue("Lastest note");
    }

    private void generateTitleRow(Sheet oppSheet, ProspectFilterDTO prospectFilterDTO, Enterprise enterprise, String timeZone)
    {
        Row titleOppRow = oppSheet.getRow(0);
        Cell titleCell = titleOppRow.getCell(3);
        Cell startCell = titleOppRow.getCell(4);
        Cell endCell = titleOppRow.getCell(5);

        String roleName = "";

        if (prospectFilterDTO.getRoleFilterType().equals("Person"))
        {
            User user = userDAO.findOne(UUID.fromString(prospectFilterDTO.getRoleFilterValue()));
            if (user != null)
            {
                roleName = user.getSharedContact().getFirstName() + " " + user.getSharedContact().getLastName();
            }
        }
        else if (prospectFilterDTO.getRoleFilterType().equals("Unit"))
        {
            Unit unit = unitDAO.findOne(UUID.fromString(prospectFilterDTO.getRoleFilterValue()));
            if (unit != null)
            {
                roleName = unit.getName();
            }
        }
        else
        {
            roleName = enterprise.getSharedOrganisation().getName();
        }
        titleCell.setCellValue(roleName);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long diffTime = (serverTimezone + Integer.parseInt(timeZone)) * 60 * 60 * 1000;
        if (!prospectFilterDTO.getIsFilterAll())
        {
            startCell.setCellValue(dateFormat.format(new Date(prospectFilterDTO.getStartDate().getTime() + diffTime)));
            endCell.setCellValue(dateFormat.format(new Date(prospectFilterDTO.getEndDate().getTime() - 1)));
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

    private void generateValueForRow(ProspectDTO prospectDTO, Row oppRow, Workbook workbook,
                                     String note, List<OrderRow> orderRows,
                                     Map<Integer, UUID> indexCustomFieldMap,
                                     Map<UUID, List<CustomFieldValue>> customFieldIdCustomFieldValueMap,
                                     Map<Integer, UUID> indexCustomFieldProductMap,
                                     Map<UUID, Map<UUID, List<CustomFieldValue>>> customFieldOrderRowMap,
                                     List<Activity> allCurrentStepList,
                                     CellStyle cellStyle, Boolean isHistoric, String timeZone)
    {
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(cellStyle);
        DataFormat poiFormat = workbook.createDataFormat();
        String excelFormatPattern = DateFormatConverter.convert(Locale.ENGLISH, "dd/MM/yyyy");
        dateStyle.setDataFormat(poiFormat.getFormat(excelFormatPattern));

        if (isHistoric)
        {
            generateProspectHistoricDataFirstSheet(prospectDTO, oppRow, note, cellStyle, dateStyle, timeZone);
        }
        else
        {
            generateProspectActiveDataFirstSheet(prospectDTO, oppRow, allCurrentStepList, note, cellStyle, dateStyle, timeZone);
        }

        int rowNumber = oppRow.getRowNum();
        if (orderRows.size() == 0)
        {
            orderRows.add(new OrderRow());
        }
        for (OrderRow orderRow : orderRows)
        {
            Row nextOppRow;
            nextOppRow = rowNumber > oppRow.getRowNum() ? oppRow.getSheet().createRow(rowNumber) : oppRow;
            generateValueForOrderRowsInOppSheet(orderRow, nextOppRow, cellStyle, dateStyle, rowNumber > oppRow.getRowNum(), indexCustomFieldMap.size(), timeZone);
            generateForCustomFieldOrderRow(orderRow, nextOppRow, cellStyle, indexCustomFieldProductMap, customFieldOrderRowMap);

            if (BEGIN_CUSTOM_FIELD != null)
            {
                int index = BEGIN_CUSTOM_FIELD;
                do
                {
                    UUID customFieldId = indexCustomFieldMap.get(index - BEGIN_CUSTOM_FIELD);
                    List<CustomFieldValue> customFieldValueList = customFieldIdCustomFieldValueMap.get(customFieldId);
                    boolean hasValue = false;
                    Cell customFieldCell = nextOppRow.createCell(index);
                    customFieldCell.setCellStyle(cellStyle);
                    String dropDownValue = "";

                    if (customFieldValueList != null)
                    {
                        Set<UUID> addedCustomFiledOptionValueId = new HashSet<>();
                        Collections.sort(customFieldValueList, new CustomFieldValueUpdatedDateComparator());

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

                            if ((customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.CHECK_BOXES.toString())
                                    || customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.DROPDOWN.toString())
                            )
                            )
                            {

                                if (!addedCustomFiledOptionValueId.contains(customFieldValue.getCustomFieldOptionValue().getUuid()))
                                {
                                    addedCustomFiledOptionValueId.add(customFieldValue.getCustomFieldOptionValue().getUuid());

                                    if (customFieldValue.getIsChecked())
                                    {
                                        if (dropDownValue.length() > 0)
                                        {
                                            if (customFieldValue.getCustomFieldOptionValue().getCustomFieldOption().getMultiChoice())
                                            {
                                                dropDownValue += "," + customFieldValue.getValue();
                                            }
                                        }
                                        else
                                        {
                                            if (customFieldValue.getValue() != null)
                                            {
                                                dropDownValue += customFieldValue.getValue();
                                            }
                                        }
                                    }
                                    customFieldCell.setCellType(CellType.STRING);
                                    customFieldCell.setCellStyle(cellStyle);
                                    hasValue = true;
                                }
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
                        customFieldCell.setCellStyle(cellStyle);
                        customFieldCell.setCellValue("");
                    }

                    index++;
                } while (index < BEGIN_CUSTOM_FIELD + indexCustomFieldMap.size());
            }

            rowNumber++;
        }
    }

    private void generateForCustomFieldOrderRow(OrderRow orderRow, Row nextOppRow, CellStyle cellStyle, Map<Integer, UUID> indexCustomFieldProductMap,
                                                Map<UUID, Map<UUID, List<CustomFieldValue>>> customFieldOrderRowMap)
    {
        Map<UUID, List<CustomFieldValue>> customFieldProductMap = customFieldOrderRowMap.get(orderRow.getUuid());
        if (customFieldProductMap == null)
        {
            customFieldProductMap = new HashMap<>();
        }

        //generate data for custom field product
        if (BEGIN_CUSTOM_FIELD_PRODUCT != null)
        {
            int indexCustomFieldProduct = BEGIN_CUSTOM_FIELD_PRODUCT;
            do
            {
                UUID customFieldId = indexCustomFieldProductMap.get(indexCustomFieldProduct - BEGIN_CUSTOM_FIELD_PRODUCT);
                List<CustomFieldValue> customFieldValueList = customFieldProductMap.get(customFieldId);
                boolean hasValue = false;
                Cell customFieldCell = nextOppRow.createCell(indexCustomFieldProduct);
                customFieldCell.setCellStyle(cellStyle);
                String dropDownValue = "";

                if (customFieldValueList != null)
                {
                    Set<UUID> addedCustomFiledOptionValueId = new HashSet<>();
                    Collections.sort(customFieldValueList, new CustomFieldValueUpdatedDateComparator());
                    for (CustomFieldValue customFieldValue : customFieldValueList)
                    {
                        if(customFieldValue == null) {
                            continue;
                        }
                        if (customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.DATE.toString())
                                && !customFieldValue.getValue().isEmpty())
                        {
                            customFieldCell.setCellValue(customFieldValue.getValue());
                            hasValue = true;
                            break;
                        }

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

                        if ((customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.CHECK_BOXES.toString())
                                || customFieldValue.getCustomField().getFieldType().toString().equals(CustomFieldType.DROPDOWN.toString())
                        )
                        )
                        {

                            if (customFieldValue.getCustomFieldOptionValue() != null && !addedCustomFiledOptionValueId.contains(customFieldValue.getCustomFieldOptionValue().getUuid()))
                            {
                                addedCustomFiledOptionValueId.add(customFieldValue.getCustomFieldOptionValue().getUuid());

                                if (customFieldValue.getIsChecked())
                                {
                                    if (dropDownValue.length() > 0)
                                    {
                                        if (customFieldValue.getCustomFieldOptionValue().getCustomFieldOption().getMultiChoice())
                                        {
                                            dropDownValue += "," + customFieldValue.getValue();
                                        }
                                    }
                                    else
                                    {
                                        if (customFieldValue.getValue() != null)
                                        {
                                            dropDownValue += customFieldValue.getValue();
                                        }
                                    }
                                }
                                customFieldCell.setCellType(CellType.STRING);
                                customFieldCell.setCellStyle(cellStyle);
                                hasValue = true;
                            }
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
                    customFieldCell.setCellStyle(cellStyle);
                    customFieldCell.setCellValue("");
                }

                indexCustomFieldProduct++;
            } while (indexCustomFieldProduct < BEGIN_CUSTOM_FIELD_PRODUCT + indexCustomFieldProductMap.size());
        }
    }

    private void generateProspectActiveDataFirstSheet(ProspectDTO prospectDTO, Row oppSheetRow,
                                                      List<Activity> allCurrentStepList,
                                                      String note, CellStyle cellStyle,
                                                      CellStyle dateStyle, String timeZone)
    {
        Cell serialCell = oppSheetRow.createCell(ExportProspectASColumnIndex.SERIAL.getIndex());
        serialCell.setCellValue(prospectDTO.getSerialNumber());
        serialCell.setCellStyle(cellStyle);

        Cell desCell = oppSheetRow.createCell(ExportProspectASColumnIndex.DESCRIPTION.getIndex());
        desCell.setCellValue(prospectDTO.getDescription());
        desCell.setCellStyle(cellStyle);


        Cell statusCell = oppSheetRow.createCell(ExportProspectASColumnIndex.STATUS.getIndex());
        statusCell.setCellType(CellType.STRING);
        statusCell.setCellStyle(cellStyle);
        statusCell.setCellValue("Active");


        Cell daysInPipeCell = oppSheetRow.createCell(ExportProspectASColumnIndex.DAY_IN_PIPE.getIndex());
        daysInPipeCell.setCellStyle(cellStyle);
        Date createdDate = prospectDTO.getCreatedDate();
        daysInPipeCell.setCellValue((new Date().getTime() - createdDate.getTime()) / 1000 / 24 / 60 / 60);


        Cell closureDateCell = oppSheetRow.createCell(ExportProspectASColumnIndex.CLOSURE_DATE.getIndex());
        long diffTime = (serverTimezone + Integer.parseInt(timeZone)) * 60 * 60 * 1000;
        closureDateCell.setCellStyle(dateStyle);
        closureDateCell.setCellValue(new Date(prospectDTO.getContractDate().getTime() + diffTime));

        Cell ownerCell = oppSheetRow.createCell(ExportProspectASColumnIndex.OWNER.getIndex());
        ownerCell.setCellStyle(cellStyle);
        ownerCell.setCellValue(prospectDTO.getOwnerName());

        Cell accountCell = oppSheetRow.createCell(ExportProspectASColumnIndex.ACCOUNT.getIndex());
        accountCell.setCellStyle(cellStyle);
        accountCell.setCellValue(prospectDTO.getOrganisation() != null ? prospectDTO.getOrganisation().getName() : null);

        Cell contactFirstNameCell = oppSheetRow.createCell(ExportProspectASColumnIndex.CONTACT_FIRST.getIndex());
        contactFirstNameCell.setCellStyle(cellStyle);
        contactFirstNameCell.setCellValue(prospectDTO.getSponsorList().get(0).getFirstName());

        Cell contactLastNameCell = oppSheetRow.createCell(ExportProspectASColumnIndex.CONTACT_LAST.getIndex());
        contactLastNameCell.setCellStyle(cellStyle);
        contactLastNameCell.setCellValue(prospectDTO.getSponsorList().get(0).getLastName());

        Double value = prospectDTO.getGrossValue();
        Double profit = prospectDTO.getProfit();
        Double netValue = prospectDTO.getNetValue();
        Double margin = 0d;
        if (value.compareTo(0d) > 0 && profit != null)
        {
            margin = profit / value * 100;
        }

        Cell valueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.VALUE.getIndex());
        valueCell.setCellType(CellType.NUMERIC);
        valueCell.setCellStyle(cellStyle);
        valueCell.setCellValue(value);

        Cell netValueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.WEIGHT.getIndex());
        netValueCell.setCellType(CellType.NUMERIC);
        netValueCell.setCellStyle(cellStyle);
        netValueCell.setCellValue(netValue);

        Cell marginValueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.MARGIN.getIndex());
        marginValueCell.setCellType(CellType.NUMERIC);
        marginValueCell.setCellStyle(cellStyle);
        marginValueCell.setCellValue(Math.round(margin * 100.0) / 100.0);

        Cell profitValueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.PROFIT.getIndex());
        profitValueCell.setCellType(CellType.NUMERIC);
        profitValueCell.setCellStyle(cellStyle);
        profitValueCell.setCellValue(profit == null ? 0 : profit);

        Cell currentStep = oppSheetRow.createCell(CURRENT_STEP);
        currentStep.setCellStyle(cellStyle);
        currentStep.setCellValue(getCurrentStepName(prospectDTO.getCurrentStepId(), allCurrentStepList));


        Cell nextStepCell = oppSheetRow.createCell(NEXT_STEP);
        nextStepCell.setCellStyle(cellStyle);
        String firstNextStep = prospectDTO.getFirstNextStep();
        String secondNextStep = prospectDTO.getSecondNextStep();
        if (null != firstNextStep && null != secondNextStep)
        {
            nextStepCell.setCellValue(firstNextStep + "\n" + secondNextStep);
        }
        else if (null != firstNextStep)
        {
            nextStepCell.setCellValue(firstNextStep);
        }
        else if (null != secondNextStep)
        {
            nextStepCell.setCellValue(secondNextStep);
        }

        Cell lastNoteCell = oppSheetRow.createCell(LAST_NOTE);
        lastNoteCell.setCellStyle(cellStyle);
        lastNoteCell.setCellValue(note);
    }

    private String getCurrentStepName(UUID currentStepId, List<Activity> allCurrentStepList)
    {
        if(currentStepId == null) return  "";
        Activity activity = allCurrentStepList.stream()
                .filter(x -> currentStepId.equals(x.getUuid()))
                .findAny()
                .orElse(null);

        return activity == null ? "" : activity.getName();
    }

    private void generateProspectHistoricDataFirstSheet(ProspectDTO prospectDTO, Row oppSheetRow,
                                                        String note, CellStyle cellStyle,
                                                        CellStyle dateStyle, String timeZone)
    {
        Cell serialCell = oppSheetRow.createCell(ExportProspectASColumnIndex.SERIAL.getIndex());
        serialCell.setCellValue(prospectDTO.getSerialNumber());
        serialCell.setCellStyle(cellStyle);

        Cell desCell = oppSheetRow.createCell(ExportProspectASColumnIndex.DESCRIPTION.getIndex());
        desCell.setCellValue(prospectDTO.getDescription());
        desCell.setCellStyle(cellStyle);


        Cell statusCell = oppSheetRow.createCell(ExportProspectASColumnIndex.STATUS.getIndex());
        statusCell.setCellType(CellType.STRING);
        statusCell.setCellStyle(cellStyle);

        if (prospectDTO.getWon() != null && prospectDTO.getWon())
        {
            statusCell.setCellValue("WON");
        }
        else if (prospectDTO.getWon() != null && !prospectDTO.getWon())
        {
            statusCell.setCellValue("LOST");
        }


        Cell daysInPipeCell = oppSheetRow.createCell(ExportProspectASColumnIndex.DAY_IN_PIPE.getIndex());
        daysInPipeCell.setCellStyle(cellStyle);
        daysInPipeCell.setCellValue(prospectDTO.getDaysInPipeline() / 1000 / 24 / 60 / 60);


        Cell closureDateCell = oppSheetRow.createCell(ExportProspectASColumnIndex.CLOSURE_DATE.getIndex());
        long diffTime = (serverTimezone + Integer.parseInt(timeZone)) * 60 * 60 * 1000;
        closureDateCell.setCellStyle(dateStyle);
        closureDateCell.setCellValue(new Date(prospectDTO.getWonLostDate().getTime() + diffTime));

        Cell ownerCell = oppSheetRow.createCell(ExportProspectASColumnIndex.OWNER.getIndex());
        ownerCell.setCellStyle(cellStyle);
        ownerCell.setCellValue(prospectDTO.getOwnerName());

        Cell accountCell = oppSheetRow.createCell(ExportProspectASColumnIndex.ACCOUNT.getIndex());
        accountCell.setCellStyle(cellStyle);
        accountCell.setCellValue(prospectDTO.getOrganisation() != null ? prospectDTO.getOrganisation().getName() : null);

        Cell contactFirstNameCell = oppSheetRow.createCell(ExportProspectASColumnIndex.CONTACT_FIRST.getIndex());
        contactFirstNameCell.setCellStyle(cellStyle);
        contactFirstNameCell.setCellValue(prospectDTO.getSponsorList().get(0).getFirstName());

        Cell contactLastNameCell = oppSheetRow.createCell(ExportProspectASColumnIndex.CONTACT_LAST.getIndex());
        contactLastNameCell.setCellStyle(cellStyle);
        contactLastNameCell.setCellValue(prospectDTO.getSponsorList().get(0).getLastName());

        Double value = prospectDTO.getGrossValue();
        Double profit = prospectDTO.getProfit();
        Double netValue = prospectDTO.getNetValue();
        Double margin = 0d;
        if (value.compareTo(0d) > 0)
        {
            margin = profit / value * 100;
        }

        Cell valueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.VALUE.getIndex());
        valueCell.setCellType(CellType.NUMERIC);
        valueCell.setCellStyle(cellStyle);
        valueCell.setCellValue(value);

        Cell netValueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.WEIGHT.getIndex());
        netValueCell.setCellType(CellType.NUMERIC);
        netValueCell.setCellStyle(cellStyle);
        netValueCell.setCellValue(netValue);

        Cell marginValueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.MARGIN.getIndex());
        marginValueCell.setCellType(CellType.NUMERIC);
        marginValueCell.setCellStyle(cellStyle);
        marginValueCell.setCellValue(Math.round(margin * 100.0) / 100.0);

        Cell profitValueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.PROFIT.getIndex());
        profitValueCell.setCellType(CellType.NUMERIC);
        profitValueCell.setCellStyle(cellStyle);
        profitValueCell.setCellValue(profit);

        Cell nextStepCell = oppSheetRow.createCell(NEXT_STEP);
        nextStepCell.setCellStyle(cellStyle);
        String firstNextStep = prospectDTO.getFirstNextStep();
        String secondNextStep = prospectDTO.getSecondNextStep();
        if (null != firstNextStep && null != secondNextStep)
        {
            nextStepCell.setCellValue(firstNextStep + "\n" + secondNextStep);
        }
        else if (null != firstNextStep)
        {
            nextStepCell.setCellValue(firstNextStep);
        }
        else if (null != secondNextStep)
        {
            nextStepCell.setCellValue(secondNextStep);
        }

        Cell lastNoteCell = oppSheetRow.createCell(LAST_NOTE);
        lastNoteCell.setCellStyle(cellStyle);
        lastNoteCell.setCellValue(note);
    }

    private void generateValueForOrderRowsInOppSheet(OrderRow orderRow, Row nextRow, CellStyle cellStyle,
                                                     CellStyle dateStyle, boolean additionalRow, int customFieldSize, String timeZone)
    {
        Double pricePerUnit = orderRow.getPrice();
        Double discountedPriceUnit = orderRow.getDiscountedPrice();
        Double numberOfUnit = orderRow.getNumberOfUnit();
        Double orderRowMargin = orderRow.getMargin();
        Double discountPercent = orderRow.getDiscountPercent();
        Double discountPrice = orderRow.getDiscountedPrice();
        Double discountAmount;
        String orderRowDescription = orderRow.getDescription();
        long diffTime = (serverTimezone + Integer.parseInt(timeZone)) * 60 * 60 * 1000;


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (pricePerUnit != null && discountedPriceUnit != null && numberOfUnit != null)
        {
            discountAmount = (pricePerUnit - discountedPriceUnit) * numberOfUnit;
        }
        else
        {
            discountAmount = null;
        }

        Cell productGroupValueCell = nextRow.createCell(ExportProspectASColumnIndex.PRODUCT_GROUP.getIndex());
        productGroupValueCell.setCellStyle(cellStyle);
        if (orderRow.getProduct() != null)
        {
            productGroupValueCell.setCellValue(orderRow.getProduct().getLineOfBusiness().getName());
        }

        Cell productTypeValueCell = nextRow.createCell(ExportProspectASColumnIndex.PRODUCT_TYPE.getIndex());
        productTypeValueCell.setCellStyle(cellStyle);
        if (orderRow.getProduct() != null)
        {
            productTypeValueCell.setCellValue(orderRow.getProduct().getMeasurementType().getName());
        }

        Cell productValueCell = nextRow.createCell(ExportProspectASColumnIndex.PRODUCT.getIndex());
        productValueCell.setCellStyle(cellStyle);
        if (orderRow.getProduct() != null)
        {
            productValueCell.setCellValue(orderRow.getProduct().getName());
        }

        Cell noUnitValueCell = nextRow.createCell(ExportProspectASColumnIndex.NO_UNIT.getIndex());
        noUnitValueCell.setCellStyle(cellStyle);
        if (numberOfUnit != null)
        {
            noUnitValueCell.setCellType(CellType.NUMERIC);
            noUnitValueCell.setCellValue(numberOfUnit);
        }

        Cell priceUnitValueCell = nextRow.createCell(ExportProspectASColumnIndex.PRICE_UNIT.getIndex());
        priceUnitValueCell.setCellStyle(cellStyle);
        if (pricePerUnit != null)
        {
            priceUnitValueCell.setCellType(CellType.NUMERIC);
            priceUnitValueCell.setCellValue(pricePerUnit);
        }

        Cell disPercentValueCell = nextRow.createCell(ExportProspectASColumnIndex.DISCOUNT_PERCENT.getIndex());
        disPercentValueCell.setCellStyle(cellStyle);
        if (discountPercent != null)
        {
            disPercentValueCell.setCellType(CellType.NUMERIC);
            disPercentValueCell.setCellValue(discountPercent);
        }

        Cell disPriceValueCell = nextRow.createCell(ExportProspectASColumnIndex.DISCOUNT_PRICE.getIndex());
        disPriceValueCell.setCellStyle(cellStyle);
        if (discountPrice != null)
        {
            disPriceValueCell.setCellType(CellType.NUMERIC);
            disPriceValueCell.setCellValue(discountPrice);
        }

        Cell disAmountValueCell = nextRow.createCell(ExportProspectASColumnIndex.DISCOUNT_AMOUNT.getIndex());
        disAmountValueCell.setCellStyle(cellStyle);
        if (discountAmount != null)
        {
            disAmountValueCell.setCellType(CellType.NUMERIC);
            disAmountValueCell.setCellValue(discountAmount);
        }
        else
        {
            disAmountValueCell.setCellValue("");
        }

        Cell descriptionCell = nextRow.createCell(ExportProspectASColumnIndex.ORDER_ROWS_DESCRIPTION.getIndex());
        descriptionCell.setCellStyle(cellStyle);
        if (orderRowDescription != null)
        {
            descriptionCell.setCellType(CellType.STRING);
            descriptionCell.setCellValue(orderRowDescription);
        }
        else
        {
            descriptionCell.setCellValue("");
        }

        Cell deliveryStartValueCell = nextRow.createCell(ExportProspectASColumnIndex.DELIVERY_START.getIndex());
        deliveryStartValueCell.setCellStyle(dateStyle);
        if (orderRow.getDeliveryStartDate() != null)
        {
            deliveryStartValueCell.setCellValue(new Date(orderRow.getDeliveryStartDate().getTime() + diffTime));
        }

        Cell deliveryEndValueCell = nextRow.createCell(ExportProspectASColumnIndex.DELIVERY_END.getIndex());
        deliveryEndValueCell.setCellStyle(dateStyle);
        if (orderRow.getDeliveryEndDate() != null)
        {
            deliveryEndValueCell.setCellValue(new Date(orderRow.getDeliveryEndDate().getTime() + diffTime));
        }

        Cell orderRowValueCell = nextRow.createCell(ExportProspectASColumnIndex.ORDER_ROWS_VALUE.getIndex());
        orderRowValueCell.setCellStyle(cellStyle);
        if (numberOfUnit != null && discountedPriceUnit != null)
        {
            orderRowValueCell.setCellType(CellType.NUMERIC);
            orderRowValueCell.setCellValue(numberOfUnit * discountedPriceUnit);
        }

        Cell orderRowMarginValueCell = nextRow.createCell(ExportProspectASColumnIndex.ORDER_ROWS_MARGIN.getIndex());
        orderRowMarginValueCell.setCellStyle(cellStyle);
        orderRowMarginValueCell.setCellType(CellType.NUMERIC);
        orderRowMarginValueCell.setCellValue(orderRowMargin == null ? 0 : orderRowMargin);


        Cell orderRowProfitValueCell = nextRow.createCell(ExportProspectASColumnIndex.ORDER_ROWS_PROFIT.getIndex());
        orderRowProfitValueCell.setCellStyle(cellStyle);
        if (numberOfUnit != null && discountedPriceUnit != null && orderRowMargin != null)
        {
            orderRowProfitValueCell.setCellType(CellType.NUMERIC);
            orderRowProfitValueCell.setCellValue(Math.round(numberOfUnit * discountedPriceUnit * (orderRowMargin / 100) * 100.0) / 100.0);
        }

        if (additionalRow)
        {
            createBlankDataForAdditionalRow(nextRow, cellStyle, dateStyle, customFieldSize);
        }
    }

    private void createBlankDataForAdditionalRow(Row oppSheetRow, CellStyle cellStyle, CellStyle dateStyle, int customFieldSize)
    {
        Cell desCell = oppSheetRow.createCell(ExportProspectASColumnIndex.DESCRIPTION.getIndex());
        desCell.setCellStyle(cellStyle);

        Cell statusCell = oppSheetRow.createCell(ExportProspectASColumnIndex.STATUS.getIndex());
        statusCell.setCellType(CellType.STRING);
        statusCell.setCellStyle(cellStyle);

        Cell daysInPipeCell = oppSheetRow.createCell(ExportProspectASColumnIndex.DAY_IN_PIPE.getIndex());
        daysInPipeCell.setCellStyle(cellStyle);

        Cell closureDateCell = oppSheetRow.createCell(ExportProspectASColumnIndex.CLOSURE_DATE.getIndex());
        closureDateCell.setCellStyle(dateStyle);


        Cell ownerCell = oppSheetRow.createCell(ExportProspectASColumnIndex.OWNER.getIndex());
        ownerCell.setCellStyle(cellStyle);

        Cell accountCell = oppSheetRow.createCell(ExportProspectASColumnIndex.ACCOUNT.getIndex());
        accountCell.setCellStyle(cellStyle);

        Cell contactFirstNameCell = oppSheetRow.createCell(ExportProspectASColumnIndex.CONTACT_FIRST.getIndex());
        contactFirstNameCell.setCellStyle(cellStyle);

        Cell contactLastNameCell = oppSheetRow.createCell(ExportProspectASColumnIndex.CONTACT_LAST.getIndex());
        contactLastNameCell.setCellStyle(cellStyle);


        Cell valueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.VALUE.getIndex());
        valueCell.setCellType(CellType.NUMERIC);
        valueCell.setCellStyle(cellStyle);

        Cell netValueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.WEIGHT.getIndex());
        netValueCell.setCellType(CellType.NUMERIC);
        netValueCell.setCellStyle(cellStyle);

        Cell marginValueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.MARGIN.getIndex());
        marginValueCell.setCellType(CellType.NUMERIC);
        marginValueCell.setCellStyle(cellStyle);

        Cell profitValueCell = oppSheetRow.createCell(ExportProspectASColumnIndex.PROFIT.getIndex());
        profitValueCell.setCellType(CellType.NUMERIC);
        profitValueCell.setCellStyle(cellStyle);

        Cell nextStepCell = oppSheetRow.createCell(NEXT_STEP);
        nextStepCell.setCellStyle(cellStyle);

        Cell lastNoteCell = oppSheetRow.createCell(LAST_NOTE);
        lastNoteCell.setCellStyle(cellStyle);

        if (BEGIN_CUSTOM_FIELD != null)
        {
            for (int i = BEGIN_CUSTOM_FIELD; i < (BEGIN_CUSTOM_FIELD + customFieldSize); i++)
            {
                Cell newCustomFieldCell = oppSheetRow.createCell(i);
                newCustomFieldCell.setCellStyle(cellStyle);
            }
        }

    }

    private Map<UUID, Map<UUID, List<CustomFieldValue>>> getProspectCustomFieldMap(List<UUID> prospectIdList)
    {
        Map<UUID, Map<UUID, List<CustomFieldValue>>> prospectCustomFieldMap = new HashMap<>();
        for (UUID uuid : prospectIdList)
        {
            prospectCustomFieldMap.put(uuid, new HashMap<UUID, List<CustomFieldValue>>());
        }

        if (!prospectIdList.isEmpty())
        {
            List<CustomFieldValue> customFieldValueList = customFieldValueDAO.findByCustomFieldAndObjectIdIn(prospectIdList);
            for (CustomFieldValue customFieldValue : customFieldValueList)
            {
                UUID prospectId = customFieldValue.getObjectId();
                UUID customFieldId = customFieldValue.getCustomField().getUuid();
                if (prospectCustomFieldMap.get(prospectId).get(customFieldId) != null)
                {
                    prospectCustomFieldMap.get(prospectId).get(customFieldId).add(customFieldValue);
                }
                else
                {
                    List<CustomFieldValue> newCustomFieldValueList = new ArrayList<>();
                    newCustomFieldValueList.add(customFieldValue);
                    prospectCustomFieldMap.get(prospectId).put(customFieldId, newCustomFieldValueList);
                }
            }
        }


        return prospectCustomFieldMap;
    }

    private Map<UUID, Map<UUID, List<CustomFieldValue>>> getOrderRowCustomFieldMap(List<UUID> prospectIdList)
    {
        //find orderrow by list prospect
        List<OrderRow> orderRows = orderRowDAO.findByProspectIdIn(prospectIdList);
        List<UUID> uuidOrderRows = new ArrayList<>();

        for (OrderRow orderRow : orderRows)
        {
            uuidOrderRows.add(orderRow.getUuid());
        }

        //find custom fiel value by list id order row
        List<CustomFieldValue> customFieldValues = new ArrayList<>();
        if (!uuidOrderRows.isEmpty())
        {
            customFieldValues = customFieldValueDAO.findByCustomFieldAndObjectIdIn(uuidOrderRows);
        }

        Map<UUID, Map<UUID, List<CustomFieldValue>>> prospectCustomFieldMap = new HashMap<>();

        for (UUID uuid : uuidOrderRows)
        {
            prospectCustomFieldMap.put(uuid, new HashMap<UUID, List<CustomFieldValue>>());
        }


        for (CustomFieldValue customFieldValue : customFieldValues)
        {
            UUID customFieldId = customFieldValue.getCustomField().getUuid();

            if (prospectCustomFieldMap.get(customFieldValue.getObjectId()).get(customFieldId) != null)
            {
                prospectCustomFieldMap.get(customFieldValue.getObjectId()).get(customFieldId).add(customFieldValue);
            }
            else
            {
                prospectCustomFieldMap.get(customFieldValue.getObjectId()).put(customFieldId, new ArrayList<CustomFieldValue>()
                {{
                    add(customFieldValue);
                }});
            }
        }


        return prospectCustomFieldMap;
    }

    private Map<UUID, List<OrderRow>> getProspectOrderRowMap(List<UUID> uuidList)
    {
        Map<UUID, List<OrderRow>> prospectOrderRowMap = new HashMap<>();
        if (uuidList.size() > 0)
        {
            List<OrderRow> orderRowList = orderRowDAO.findByProspectIdIn(uuidList);
            for (OrderRow orderRow : orderRowList)
            {
                UUID uuid = orderRow.getProspectId();
                if (prospectOrderRowMap.get(uuid) == null)
                {
                    List<OrderRow> newOrderRowList = new ArrayList<>();
                    newOrderRowList.add(orderRow);
                    prospectOrderRowMap.put(uuid, newOrderRowList);
                }
                else
                {
                    prospectOrderRowMap.get(orderRow.getProspectId()).add(orderRow);
                }
            }
        }

        for (UUID uuid : uuidList)
        {
            if (prospectOrderRowMap.get(uuid) == null)
            {
                prospectOrderRowMap.put(uuid, new ArrayList<OrderRow>());
            }
        }

        return prospectOrderRowMap;
    }

    private List<UUID> getUniqueId(ProspectListDTO prospectListDTO)
    {
        List<UUID> uuidList = new ArrayList<>();
        if (prospectListDTO.getProspectDTOList().size() > 0)
        {
            for (ProspectDTO prospectDTO : prospectListDTO.getProspectDTOList())
            {
                uuidList.add(prospectDTO.getUuid());
            }
        }
        return uuidList;
    }

    private Map<UUID, String> getProspectNoteMap(List<UUID> uuidList)
    {
        Map<UUID, String> prospectNoteMap = new HashMap<>();
        if (uuidList.size() > 0)
        {
            List<Object[]> noteList = noteDAO.findLatestByProspectIdIn(new HashSet<UUID>(uuidList));
            if (noteList.size() > 0)
            {
                for (Object[] objects : noteList)
                {
                    UUID prospectId = (UUID) objects[0];
                    if (prospectNoteMap.get(prospectId) == null)
                    {
                        prospectNoteMap.put(prospectId, objects[1].toString() + ": " + objects[2].toString());
                    }
                }
            }
        }
        return prospectNoteMap;
    }

    enum ExportProspectASColumnIndex
    {
        SERIAL(0),
        DESCRIPTION(1),
        STATUS(2),
        DAY_IN_PIPE(3),
        CLOSURE_DATE(4),
        OWNER(5),
        ACCOUNT(6),
        CONTACT_FIRST(7),
        CONTACT_LAST(8),

        VALUE(9),
        WEIGHT(10),
        MARGIN(11),
        PROFIT(12),

        PRODUCT_GROUP(13),
        PRODUCT_TYPE(14),
        PRODUCT(15),
        NO_UNIT(16),
        PRICE_UNIT(17),

        DISCOUNT_PERCENT(18),
        DISCOUNT_PRICE(19),
        DISCOUNT_AMOUNT(20),

        ORDER_ROWS_DESCRIPTION(21),
        ORDER_ROWS_VALUE(22),
        ORDER_ROWS_MARGIN(23),
        ORDER_ROWS_PROFIT(24),
        DELIVERY_START(25),
        DELIVERY_END(26),
        ;

        private int index;

        ExportProspectASColumnIndex(int index)
        {
            this.index = index;
        }

        public int getIndex()
        {
            return this.index;
        }
    }


    enum ExportOrderRowASColumnIndex
    {
        DESCRIPTION(0),
        STATUS(1),
        ACCOUNT(2),
        CONTACT_FIRST(3),
        CONTACT_LAST(4),

        PRODUCT(5),
        NO_UNIT(6),
        PRICE_UNIT(7),
        ORDER_ROWS_VALUE(8),
        ORDER_ROWS_MARGIN(9),
        ORDER_ROWS_PROFIT(10),
        DELIVERY_START(11),
        DELIVERY_END(12);

        private int index;

        ExportOrderRowASColumnIndex(int index)
        {
            this.index = index;
        }

        public int getIndex()
        {
            return this.index;
        }
    }
}
