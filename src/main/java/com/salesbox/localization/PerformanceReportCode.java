package com.salesbox.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunglv2 on 11/7/14.
 */
public enum  PerformanceReportCode
{
    ORDER_INTAKE,
    TARGET_FULFILLMENT,
    TARGET,
    PROFIT,
    MARGIN,
    MEDIAN_DEAL_SIZE,
    MEDIAN_DEAL_TIME,
    DIAL,
    CALL,
    APPOINTMENTS,
    OPPORTUNITIES,
    RATIO,
    GROWTH;

    public static List<String> getValueListAsString()
    {
        List<String> valueList = new ArrayList<>();
        for (PerformanceReportCode keyCode : PerformanceReportCode.values())
        {
            valueList.add(keyCode.toString());
        }

        return valueList;
    }
}
