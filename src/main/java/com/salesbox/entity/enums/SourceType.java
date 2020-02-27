package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 7/16/14
 */
public enum SourceType
{
    NONE(0),
    FACEBOOK(1),
    LINKED_IN(2),
    MAIL_CHIMP(3),
    LEADBOXER(4);
    int extension;

// -------------------------- STATIC METHODS --------------------------

    SourceType(int extension)
    {
        this.extension = extension;
    }

    public static SourceType getSourceType(int extension)
    {
        try
        {
            return SourceType.values()[extension];
        }
        catch (Exception ex)
        {
            return null;
        }
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
