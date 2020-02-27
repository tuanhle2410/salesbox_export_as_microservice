package com.salesbox.message;

/**
 * Created by hungnh on 8/14/15.
 */
public enum LeadMessage
{
    LEAD_DELETED,
    DUPLICATED_LEAD,
    LEAD_NOT_FOUND,
    CANNOT_DELETE_LEAD_BECAUSE_EXISTING_ACTIVE_TASK,
    CANNOT_DELETE_LEAD_BECAUSE_EXISTING_ACTIVE_MEETING,
    DOES_NOT_EXIST_USER,
    CANNOT_DELETE_LEAD_BECAUSE_EXISTING_ACTIVE_APPOINTMENT,
    LEAD_STATUS_IS_INVALID,

    DUPLICATED_LEAD_OF_MINE,
    DUPLICATED_LEAD_OF_OTHER,
    ;
}
