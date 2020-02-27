package com.salesbox.entity.enums;

/**
 * Created by hungnh on 8/12/15.
 */
public enum RecentActionType
{
    VIEW_DETAILS(0),
    EDIT(1),
    ADD_TO_OBJECT_USER_LIST(2),

    CALL(3),
    DIAL(4),
    EMAIL(5),
    I_MESSAGE(6),
    FACE_TIME(7),

    TASK(8),
    APPOINTMENT(9),
    NOTE(10),
    LEAD(11),
    PHOTO(12),
    DOCUMENT(13),

    OPPORTUNITY(14),
    PROGRESS(15),
    WON_OPPORTUNITY(16),
    LOST_OPPORTUNITY(17),
    ADD_OPPORTUNITY(18);

    private int extension;

    private RecentActionType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return extension;
    }

    public static RecentActionType getRecentActionType(int extension)
    {
        try
        {
            return RecentActionType.values()[extension];
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
