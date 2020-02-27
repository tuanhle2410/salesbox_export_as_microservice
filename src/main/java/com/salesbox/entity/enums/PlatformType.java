package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 7/16/14
 */
public enum PlatformType
{
    WEB(0),
    IOS(1),
    ANDROID(2);

    int extension;

// -------------------------- STATIC METHODS --------------------------

    PlatformType(int extension)
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
