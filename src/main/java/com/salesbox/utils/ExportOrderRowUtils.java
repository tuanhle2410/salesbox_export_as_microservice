package com.salesbox.utils;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.OrderRow;
import com.salesbox.localization.ImportExportColumnCode;
import com.salesbox.prospect.constant.OrderRowColumnIndex;
import com.salesbox.prospect.constant.OrderRowConstant;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Created by: hunglv
 * Date: 10/28/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExportOrderRowUtils
{
    @Autowired
    ImportExportUtils importExportUtils;

    public void createHeaderRowForOrderRow(HSSFSheet sheet, CellStyle headerStyle, Enterprise enterprise)
    {
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(OrderRowConstant.HEIGHT_ROW);

        HashMap<String, String> hashMap = importExportUtils.getStringHashMapNameColumnExcel(enterprise);

        Cell prospectIDTitleCell = titleRow.createCell(OrderRowColumnIndex.PROSPECT_ID.getIndex());
        prospectIDTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.PROSPECT_ID.toString()));
        prospectIDTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(OrderRowColumnIndex.PROSPECT_ID.getIndex(), OrderRowConstant.LARGE_WIDTH);

        Cell productIDTitleCell = titleRow.createCell(OrderRowColumnIndex.PRODUCT_NAME.getIndex());
        productIDTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.PRODUCT_NAME.toString()));
        productIDTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(OrderRowColumnIndex.PRODUCT_NAME.getIndex(), OrderRowConstant.LARGE_WIDTH);

        Cell typeTitleCell = titleRow.createCell(OrderRowColumnIndex.TYPE.getIndex());
        typeTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.PRODUCT_TYPE.toString()));
        typeTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(OrderRowColumnIndex.TYPE.getIndex(), OrderRowConstant.LARGE_WIDTH);

        Cell numberOfUnitTitleCell = titleRow.createCell(OrderRowColumnIndex.NUMBER_OF_UNITS.getIndex());
        numberOfUnitTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.NUMBER_OF_UNITS.toString()));
        numberOfUnitTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(OrderRowColumnIndex.NUMBER_OF_UNITS.getIndex(), OrderRowConstant.SMALL_WIDTH);


        Cell pricePerUnitTitleCell = titleRow.createCell(OrderRowColumnIndex.PRICE_PER_UNIT.getIndex());
        pricePerUnitTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.PRICE_PER_UNIT.toString()));
        pricePerUnitTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(OrderRowColumnIndex.PRICE_PER_UNIT.getIndex(), OrderRowConstant.SMALL_WIDTH);


        Cell deliveryStartDateTitleCell = titleRow.createCell(OrderRowColumnIndex.DELIVERY_START_DATE.getIndex());
        deliveryStartDateTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.DELIVERY_START_DATE.toString()));
        deliveryStartDateTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(OrderRowColumnIndex.DELIVERY_START_DATE.getIndex(), OrderRowConstant.MEDIUM_WIDTH);


        Cell deliveryEndDateTitleCell = titleRow.createCell(OrderRowColumnIndex.DELIVERY_END_DATE.getIndex());
        deliveryEndDateTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.DELIVERY_END_DATE.toString()));
        deliveryEndDateTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(OrderRowColumnIndex.DELIVERY_END_DATE.getIndex(), OrderRowConstant.MEDIUM_WIDTH);


        Cell marginTitleCell = titleRow.createCell(OrderRowColumnIndex.MARGIN.getIndex());
        marginTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.MARGIN.toString()));
        marginTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(OrderRowColumnIndex.MARGIN.getIndex(), OrderRowConstant.SMALL_WIDTH);

    }

    public void generateValueForRow(OrderRow orderRow, Row contentRow, Workbook workbook)
    {
        Cell prospectIDCell = contentRow.createCell(OrderRowColumnIndex.PROSPECT_ID.getIndex());
        prospectIDCell.setCellValue(orderRow.getProspectId().toString());

        Cell productIDCell = contentRow.createCell(OrderRowColumnIndex.PRODUCT_NAME.getIndex());
        productIDCell.setCellValue(orderRow.getProduct().getName());

        Cell typeCell = contentRow.createCell(OrderRowColumnIndex.TYPE.getIndex());
        typeCell.setCellValue(orderRow.getMeasurementType().getName());

        Cell numberOfUnitType = contentRow.createCell(OrderRowColumnIndex.NUMBER_OF_UNITS.getIndex());
        numberOfUnitType.setCellValue(orderRow.getNumberOfUnit());

        Cell pricePerUnitCell = contentRow.createCell(OrderRowColumnIndex.PRICE_PER_UNIT.getIndex());
        pricePerUnitCell.setCellValue(orderRow.getPrice());

        CellStyle cellStyle = importExportUtils.getCellStyleDate(workbook);

        Cell deliveryStartDateCell = contentRow.createCell(OrderRowColumnIndex.DELIVERY_START_DATE.getIndex());
        deliveryStartDateCell.setCellStyle(cellStyle);
        deliveryStartDateCell.setCellValue(orderRow.getDeliveryStartDate());

        Cell deliveryEndDateCell = contentRow.createCell(OrderRowColumnIndex.DELIVERY_END_DATE.getIndex());
        deliveryEndDateCell.setCellStyle(cellStyle);
        deliveryEndDateCell.setCellValue(orderRow.getDeliveryEndDate());

        Cell marginCell = contentRow.createCell(OrderRowColumnIndex.MARGIN.getIndex());
        marginCell.setCellValue(orderRow.getMargin());

    }


}
