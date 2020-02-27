package com.salesbox.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnh on 12/4/14.
 */
public enum ComingDealsKeyCode
{
    COMING_DEALS,
    COMING_DEALS_PRODUCT_GROUP,

    ACCOUNT,
    CONTACT,
    OPPORTUNITY,
    PROGRESS,
    GROSS,
    NET,
    MARGIN,
    DAYS_IN_PIPE,
    APPOINTMENTS_LEFT,
    OWNER,
    CONTRACT_DATE,
    LINE_OF_BUSINESS;

    public static List<String> getValueListAsString()
    {
        List<String> valueList = new ArrayList<>();

        for (ComingDealsKeyCode keyCode : ComingDealsKeyCode.values())
        {
            valueList.add(keyCode.toString());
        }

        return valueList;
    }
}
