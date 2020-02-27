package com.salesbox.entity.enums;

/**
 * Created by GEM on 12/11/2017.
 */
public enum SubObjectType
{
    PHOTO(0),
    DOCUMENT(1),
    NOTE(2),
    ORDER_ROWS(3),
    ACTION_PLAN(4),
    TASK_SUB(5),
    LEAD_SUB(6),
    OPPORTUNITY_SUB(7),
    OPPORTUNITY_CLOSED_SUB(8),
    APPOINTMENT_SUB(9),
    COLLEAGUES_SUB(10),
    CONTACTS_SUB(11),
    CREATE_SMS_EMAIL(12);
    private int extension;

    private SubObjectType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
