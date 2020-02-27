package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 6/18/14
 */
public enum MediaType
{
    MANUAL(0),
    FACEBOOK(1),
    LINKEDIN(2);

    int extension;

// -------------------------- STATIC METHODS --------------------------

    MediaType(int extension)
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
