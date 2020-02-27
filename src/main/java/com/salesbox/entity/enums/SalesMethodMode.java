package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 6/3/14
 */
public enum SalesMethodMode
{
    SEQUENTIAL(0),
    DYNAMIC(1);

    private int extension;

    private SalesMethodMode(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }

    public static SalesMethodMode getMode(int extension)
    {
        try
        {
            return SalesMethodMode.values()[extension];
        }
        catch (Exception ex)
        {
            return null;
        }
    }

}
