package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 9/22/2014
 */
public enum PeriodType
{
    DAY(0),
    WEEK(1),
    MONTH(2),
    QUARTER(3),
    YEAR(4);

    private PeriodType(int extension)
    {
        this.extension = extension;
    }

    private int extension;

    public int getExtension()
    {
        return extension;
    }

    public void setExtension(int extension)
    {
        this.extension = extension;
    }
}
