package com.salesbox.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnh on 11/5/14.
 */
public enum ForecastDetailKeyCode
{
    FORECAST_ORDER_INTAKE,
    FORECAST_PROFIT,
    FORECAST_REVENUE,
    FORECAST_MARGIN,

    SUMMARY,
    OPPORTUNITY_SERIAL,
    ACCOUNT,
    OPPORTUNITY,
    PROGRESS,
    OWNER;

    public static List<String> getValueListAsString()
    {
        List<String> valueList = new ArrayList<>();
        for (ForecastDetailKeyCode keyCode : ForecastDetailKeyCode.values())
        {
            valueList.add(keyCode.toString());
        }

        return valueList;
    }
}
