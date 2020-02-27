package com.salesbox.utils;

import com.salesbox.entity.CustomFieldValue;

import java.util.Comparator;


public class CustomFieldValueUpdatedDateComparator implements Comparator<CustomFieldValue>
{
    @Override
    public int compare(CustomFieldValue o2, CustomFieldValue o1)
    {
        return o1.getUpdatedDate().compareTo(o2.getUpdatedDate());
    }

}
