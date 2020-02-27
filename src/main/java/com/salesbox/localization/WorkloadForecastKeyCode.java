package com.salesbox.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnh on 12/10/14.
 */
public enum WorkloadForecastKeyCode
{
    WORKLOAD_FORECAST_PERSON,
    WORKLOAD_FORECAST_UNIT,
    UNIT,
    PERSON,
    HOURS_PER_WEEK,
    DIALS,
    CALLS,
    APPOINTMENTS,
    TASKS,
    PRIORITISED_OPPORTUNITIES,
    MISSING_OPPORTUNITIES,
    CAPACITY;

    public static List<String> getValueListAsString()
    {
        List<String> valueList = new ArrayList<>();
        for (WorkloadForecastKeyCode keyCode : WorkloadForecastKeyCode.values())
        {
            valueList.add(keyCode.toString());
        }

        return valueList;
    }
}
