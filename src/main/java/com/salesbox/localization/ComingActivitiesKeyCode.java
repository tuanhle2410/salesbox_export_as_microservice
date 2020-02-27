package com.salesbox.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnh on 11/17/14.
 */
public enum ComingActivitiesKeyCode
{
    COMING_ACTIVITIES,
    ACCOUNT,
    CONTACT,
    CATEGORY,
    FOCUS,
    PREFERED_DISC,
    OWNER,
    DISC,
    STARTS,
    ENDS,

    APPOINTMENT;

    public static List<String> getValueListAsString()
    {
        List<String> valueList = new ArrayList<>();

        for (ComingActivitiesKeyCode keyCode : ComingActivitiesKeyCode.values())
        {
            valueList.add(keyCode.toString());
        }

        return valueList;
    }
}
