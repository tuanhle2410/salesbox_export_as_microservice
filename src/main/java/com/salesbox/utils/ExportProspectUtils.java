package com.salesbox.utils;

import com.salesbox.entity.*;
import com.salesbox.localization.ImportExportColumnCode;
import com.salesbox.prospect.constant.ProspectColumnIndex;
import com.salesbox.prospect.constant.ProspectConstant;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by: hunglv
 * Date: 10/28/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExportProspectUtils
{
    @Autowired
    ImportExportUtils importExportUtils;

    public void createHeaderRowForExportProspect(HSSFSheet sheet, CellStyle headerStyle, Enterprise enterprise)
    {
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(ProspectConstant.HEIGHT_ROW);
        HashMap<String, String> hashMap = importExportUtils.getStringHashMapNameColumnExcel(enterprise);

        Cell prospectIDTitleCell = titleRow.createCell(ProspectColumnIndex.PROSPECT_ID.getIndex());
        prospectIDTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.PROSPECT_ID.toString()));
        prospectIDTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.PROSPECT_ID.getIndex(), ProspectConstant.LARGE_WIDTH);

        Cell descriptionTitleCell = titleRow.createCell(ProspectColumnIndex.DESCRIPTION.getIndex());
        descriptionTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.DESCRIPTION.toString()));
        descriptionTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.DESCRIPTION.getIndex(), ProspectConstant.LARGE_WIDTH);


        Cell sponsorTitleCell = titleRow.createCell(ProspectColumnIndex.SPONSOR.getIndex());
        sponsorTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.SPONSOR.toString()));
        sponsorTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.SPONSOR.getIndex(), ProspectConstant.LARGE_WIDTH);

        Cell accountEmailTitleCell = titleRow.createCell(ProspectColumnIndex.ACCOUNT_EMAIL.getIndex());
        accountEmailTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.ACCOUNT_EMAIL.toString()));
        accountEmailTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.ACCOUNT_EMAIL.getIndex(), ProspectConstant.LARGE_WIDTH);

        Cell lineOfBusinessTitleCell = titleRow.createCell(ProspectColumnIndex.LINE_OF_BUSINESS.getIndex());
        lineOfBusinessTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.LINE_OF_BUSINESS.toString()));
        lineOfBusinessTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.LINE_OF_BUSINESS.getIndex(), ProspectConstant.LARGE_WIDTH);


        Cell salesMethodTitleCell = titleRow.createCell(ProspectColumnIndex.SALES_METHOD.getIndex());
        salesMethodTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.SALES_METHOD.toString()));
        salesMethodTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.SALES_METHOD.getIndex(), ProspectConstant.LARGE_WIDTH);

        Cell contractDateTitleCell = titleRow.createCell(ProspectColumnIndex.CONTRACT_DATE.getIndex());
        contractDateTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.CONTRACT_DATE.toString()));
        contractDateTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.CONTRACT_DATE.getIndex(), ProspectConstant.SMALL_WIDTH);

        Cell statusTitleCell = titleRow.createCell(ProspectColumnIndex.STATUS.getIndex());
        statusTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.STATUS_PROSPECT.toString()));
        statusTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.STATUS.getIndex(), ProspectConstant.SMALL_WIDTH);

        Cell userEmailTitleCell = titleRow.createCell(ProspectColumnIndex.OPPORTUNITY_TEAM.getIndex());
        userEmailTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.OPPORTUNITY_TEAM.toString()));
        userEmailTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.OPPORTUNITY_TEAM.getIndex(), ProspectConstant.LARGE_WIDTH);

        Cell userSharedOfOrderValueTitleCell = titleRow.createCell(ProspectColumnIndex.USER_SHARE_OF_ORDER_VALUE.getIndex());
        userSharedOfOrderValueTitleCell.setCellValue(hashMap.get(ImportExportColumnCode.USER_SHARE_OF_ORDER_VALUE.toString()));
        userSharedOfOrderValueTitleCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(ProspectColumnIndex.USER_SHARE_OF_ORDER_VALUE.getIndex(), ProspectConstant.LARGE_WIDTH);
    }

    public void generateValueForRow(ProspectBase prospect, Row contentRow, Workbook workbook,
                                    List<ProspectContact> prospectContactList, List<ProspectUser> prospectUserList)
    {
        Cell prospectIDContentCell = contentRow.createCell(ProspectColumnIndex.PROSPECT_ID.getIndex());
        prospectIDContentCell.setCellValue(prospect.getUuid().toString());

        Cell descriptionContentCell = contentRow.createCell(ProspectColumnIndex.DESCRIPTION.getIndex());
        descriptionContentCell.setCellValue(prospect.getDescription());

        Cell sponsorCell = contentRow.createCell(ProspectColumnIndex.SPONSOR.getIndex());
        sponsorCell.setCellValue(String.valueOf(getContactTeam(prospect, prospectContactList)));

        Cell accountEmailCell = contentRow.createCell(ProspectColumnIndex.ACCOUNT_EMAIL.getIndex());
        if (prospect.getOrganisation() != null)
        {
            Organisation organisation = prospect.getOrganisation();
            String organisationEmail = organisation.getEmail();
            String organisationName = organisation.getName();

            accountEmailCell.setCellValue(organisationEmail != null ? organisationEmail : organisationName);
        }

        Cell lineOfBusinessCell = contentRow.createCell(ProspectColumnIndex.LINE_OF_BUSINESS.getIndex());
        lineOfBusinessCell.setCellValue(prospect.getLineOfBusiness() != null ? prospect.getLineOfBusiness().getName() : "");

        Cell salesMethodCell = contentRow.createCell(ProspectColumnIndex.SALES_METHOD.getIndex());
        salesMethodCell.setCellValue(prospect.getSalesMethod().getName());

        Cell contractDateCell = contentRow.createCell(ProspectColumnIndex.CONTRACT_DATE.getIndex());
        contractDateCell.setCellStyle(importExportUtils.getCellStyleDate(workbook));
        contractDateCell.setCellValue(prospect.getContractDate());

        Cell statusCell = contentRow.createCell(ProspectColumnIndex.STATUS.getIndex());
        setValueForStatusProspect(prospect, statusCell);

        Cell opportunityTeam = contentRow.createCell(ProspectColumnIndex.OPPORTUNITY_TEAM.getIndex());
        opportunityTeam.setCellValue(String.valueOf(getOpportunityTeam(prospect, prospectUserList)));

        Cell userSharedOfOrderValueCell = contentRow.createCell(ProspectColumnIndex.USER_SHARE_OF_ORDER_VALUE.getIndex());
        userSharedOfOrderValueCell.setCellValue(String.valueOf(getUserSharedOfOrderValueInOpportunityTeam(prospect, prospectUserList)));
    }

    private StringBuilder getContactTeam(ProspectBase prospect, List<ProspectContact> prospectContactList)
    {
        StringBuilder opportunityTeam = new StringBuilder();
        for (ProspectContact prospectContact : prospectContactList)
        {
            if (prospectContact.getProspectId().compareTo(prospect.getUuid()) == 0)
            {
                Contact contact = prospectContact.getContact();
                String contactName = contact.getFirstName() + " " + contact.getLastName();
                String contactEmail = contact.getEmail();

                importExportUtils.appendMessage(opportunityTeam, contactEmail != null ? contactEmail : contactName);
            }
        }

        return opportunityTeam.length() > 0 ? opportunityTeam : null;
    }



    private StringBuilder getOpportunityTeam(ProspectBase prospect, List<ProspectUser> prospectUserList)
    {
        StringBuilder opportunityTeam = new StringBuilder();
        for (ProspectUser prospectUser : prospectUserList)
        {
            if (prospectUser.getProspectId().compareTo(prospect.getUuid()) == 0)
            {
                importExportUtils.appendMessage(opportunityTeam, prospectUser.getUser().getUsername());
            }
        }

        return opportunityTeam.length() > 0 ? opportunityTeam : null;
    }

    private StringBuilder getUserSharedOfOrderValueInOpportunityTeam(ProspectBase prospect, List<ProspectUser> prospectUserList)
    {
        StringBuilder userSharedOfOrderValueCell = new StringBuilder();
        for (ProspectUser prospectUser : prospectUserList)
        {
            if (prospectUser.getProspectId().compareTo(prospect.getUuid()) == 0)
            {
                importExportUtils.appendMessage(userSharedOfOrderValueCell, String.valueOf(prospectUser.getPercent().intValue()));
            }
        }

        return userSharedOfOrderValueCell.length() > 0 ? userSharedOfOrderValueCell : null;
    }

    private void setValueForStatusProspect(ProspectBase prospect, Cell statusTitleCell)
    {
//        if (prospect.getWon() != null)
        if (prospect instanceof ProspectHistoric)
        {
            ProspectHistoric prospectHistoric = (ProspectHistoric) prospect;
            if (prospectHistoric.getWon())
            {
                statusTitleCell.setCellValue("Won");
            }
            else
            {
                statusTitleCell.setCellValue("Lost");
            }
        }
        else
        {
            statusTitleCell.setCellValue("Active");
        }
    }

}
