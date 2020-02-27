package com.salesbox.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunglv2 on 12/9/14.
 */
public enum ValuePerActivityCode
{
    VALUE_PER_DIAL,
    VALUE_PER_CALL,
    VALUE_PER_MEETING,
    VALUE_PER_QUOTE,
    VALUE_PER_HOUR;

    public static List<String> getValueListAsString()
    {
        List<String> valueList = new ArrayList<>();
        for (ValuePerActivityCode keyCode : ValuePerActivityCode.values())
        {
            valueList.add(keyCode.toString());
        }

        return valueList;
    }
}
