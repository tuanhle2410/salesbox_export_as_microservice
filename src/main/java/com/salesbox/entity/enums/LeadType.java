package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 7/16/14
 */
public enum LeadType
{
    PRIORITISED(0),
    MANUAL(1),
    INVITED(2),
    DISTRIBUTE(3);

    private int extension;

    public static LeadType getLeadType(int extension)
    {
        try
        {
            return LeadType.values()[extension];
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    LeadType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return extension;
    }

    public void setExtension(int extension)
    {
        this.extension = extension;
    }
}
