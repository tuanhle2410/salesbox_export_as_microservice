package com.salesbox.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnh on 10/9/14.
 */
public enum ForecastOverviewKeyCode
{
    // Report type
    FORECAST_OVERVIEW,

    // Title
    MISCELLANEOUS,
    ACCRUAL_FORECAST,

    // Label of miscellaneous block
    REVENUE_TARGET,
    REVENUE_FORECAST,
    REVENUE_CLOSED,

    SALES_TARGET,
    SALES_FORECAST,
    SALES_CLOSED,

    PROFIT_TARGET,
    PROFIT_FORECAST,
    PROFIT_CLOSED,

    MARGIN_TARGET,
    MARGIN_FORECAST,
    MARGIN_CLOSED,

    GROSS_PIPELINE,
    NET_PIPELINE,
    SALES_GAP,
    MISSING_OPPORTUNITIES,
    PRIORITISED_OPPORTUNITIES,
    APPOINTMENTS_LEFT,

    PIPELINE_PROGRESS,
    RATIO,
    BOOK_TO_BILL,
    MEDIAN_DEAL_TIME,
    MEDIAN_DEAL_SIZE,
    MEDIAN_PROFIT,
    MEDIAN_MARGIN,

    // Label of accrual forecast block
    MARGIN,
    GROWTH,
    TOTAL,

    YEAR,
    QUARTER,
    MONTH,
    WEEK,
    DAY;

    public static List<String> getValueListAsString()
    {
        List<String> valueList = new ArrayList<>();
        for (ForecastOverviewKeyCode keyCode : ForecastOverviewKeyCode.values())
        {
            valueList.add(keyCode.toString());
        }

        return valueList;
    }
}
