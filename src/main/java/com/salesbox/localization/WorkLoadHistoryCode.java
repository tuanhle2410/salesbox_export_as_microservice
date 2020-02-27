package com.salesbox.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunglv2 on 12/9/14.
 */
public enum  WorkLoadHistoryCode
{
    ASK_FOR_HELP,
    GIVEN_HELP,
    WON,
    DIAL,
    CALL,
    TASKS,
    APPOINTMENTS,
    OPPORTUNITIES,
    RATIO,
    WORKED_HOURS,
    NO_SENT_QUOTES,
    CAPACITY;

    public static List<String> getValueListAsString()
    {
        List<String> valueList = new ArrayList<>();
        for (WorkLoadHistoryCode keyCode : WorkLoadHistoryCode.values())
        {
            valueList.add(keyCode.toString());
        }

        return valueList;
    }
}
