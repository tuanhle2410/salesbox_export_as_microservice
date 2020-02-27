package com.salesbox.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnh on 8/11/15.
 */
public enum ReceiptKeyCode
{
    RECEIPT_TITLE,
    ISSUED_DATE,
    PERIOD,
    BILLING_TO,
    PAYMENT_METHOD,
    VAT,
    ITEM_NAME,
    UNIT_PRICE,
    QUANTITY,
    AMOUNT,
    SUBTOTAL,
    TOTAL,
    DISCOUNT,
    FOOTER_LINE_01,
    FOOTER_LINE_02,
    INVOICE_ITEM_NAME_LINE_01,
    INVOICE_ITEM_NAME_LINE_02,
    ;

    public static List<String> getValueListAsString()
    {
        List<String> valueList = new ArrayList<>();

        for (ReceiptKeyCode keyCode : ReceiptKeyCode.values())
        {
            valueList.add(keyCode.toString());
        }

        return valueList;
    }
}
