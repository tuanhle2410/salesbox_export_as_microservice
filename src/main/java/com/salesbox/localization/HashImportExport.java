package com.salesbox.localization;

import java.util.HashMap;

/**
 * Created by: hunglv
 * Date: 10/24/14
 */
public class HashImportExport
{
    public static HashMap<String, String> getHashMapColumnName(String language)
    {
        if (language.equals("English"))
        {
            HashMap<String, String> english = new HashMap<>();
            english.put("ACCOUNT_ID", "Account ID");
            english.put("ACCOUNT_NAME", "Account name");
            english.put("ADDRESS", "Address");
            english.put("ZIP_CODE", "Zip Code");
            english.put("CITY", "City");
            english.put("REGION", "Region");
            english.put("COUNTRY", "Country");
            english.put("PHONE", "Phone");
            english.put("EMAIL", "Email");
            english.put("WEB", "Web");
            english.put("ORGANISATION_TYPE", "Type");
            english.put("INDUSTRY", "Industry");
            english.put("SIZE", "Size");
            english.put("BUDGET", "Buget");
            english.put("APPOINTMENT_GOAL", "Appointment goal per week");
            english.put("ACCOUNT_TEAM", "Account team");
            english.put("FIRST_NAME", "First name");
            english.put("LAST_NAME", "Last name");
            english.put("ACCOUNT_EMAIL", "Account Domain");
            english.put("TITLE", "Title");
            english.put("RELATION", "Relation");
            english.put("RELATIONSHIP", "Relationship");
            english.put("DISC_PROFILE", "Disc Profile");
            english.put("CONTACT_TEAM", "Contact Team");
            english.put("PROSPECT_ID", "Opportunity ID");
            english.put("DESCRIPTION", "Description");
            english.put("SPONSOR", "Power sponsor/sponsor");
            english.put("LINE_OF_BUSINESS", "Line of Business");
            english.put("SALES_METHOD", "Sales Method");
            english.put("CONTRACT_DATE", "Contract Date");
            english.put("STATUS_PROSPECT", "Status");
            english.put("OPPORTUNITY_TEAM", "Opportunity Team");
            english.put("USER_SHARE_OF_ORDER_VALUE", "User share of order value");
            english.put("PRODUCT_NAME", "Product Name");
            english.put("PRODUCT_TYPE", "Product Type");
            english.put("NUMBER_OF_UNITS", "No.of units");
            english.put("PRICE_PER_UNIT", "Price per unit");
            english.put("DELIVERY_START_DATE", "Delivery start date");
            english.put("DELIVERY_END_DATE", "Delivery end date");
            english.put("MARGIN", "Margin");
            english.put("DEFAULT_SALES_METHOD", "Default Sales Method");

            return english;
        }

        if (language.equals("Italian"))
        {
        }
        if (language.equals("Turkish"))
        {
        }
        if (language.equals("Chinese"))
        {
        }

        if (language.equals("Vietnamese"))
        {
        }
        if (language.equals(""))
        {
        }
        if (language.equals(""))
        {
        }
        if (language.equals(""))
        {
        }
        if (language.equals(""))
        {
        }


        return null;
    }

}
