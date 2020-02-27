package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/27/14
 * Time: 10:48 AM
 */
public enum WorkDataType
{
    NONE(0),
    TARGET(1),
    WORK_LOAD(2),
    FISCAL_YEAR(3),
    WARNINGS(4),
    ORDER_ROW_TYPE(5);  // FIXED/RECURRING, START/END

// ------------------------------ FIELDS ------------------------------

    int extension;

// -------------------------- STATIC METHODS --------------------------

    WorkDataType(int extension)
    {
        this.extension = extension;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public static WorkDataType getWorkData(int extension)
    {
        try
        {
            return WorkDataType.values()[extension];
        }
        catch (Exception ex)
        {
            return null;
        }
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getExtension()
    {
        return extension;
    }

    public void setExtension(int extension)
    {
        this.extension = extension;
    }
}
