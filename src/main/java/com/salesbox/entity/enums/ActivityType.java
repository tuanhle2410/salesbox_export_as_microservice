package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/27/14
 * Time: 11:30 PM
 */
public enum ActivityType
{
    NONE(0),
    CATEGORY(1),
    FOCUS(2),
    HELP_TYPE(3);

// ------------------------------ FIELDS ------------------------------

    private int extension;

// -------------------------- STATIC METHODS --------------------------

    private ActivityType(int extension)
    {
        this.extension = extension;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public static ActivityType getActivity(int extension)
    {
        try
        {
            return ActivityType.values()[extension];
        }
        catch (Exception ex)
        {
            return null;
        }
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getExtension()
    {
        return this.extension;
    }
}
