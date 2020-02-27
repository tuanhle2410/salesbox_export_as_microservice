package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/3/14
 * Time: 9:41 AM
 */
public enum ServerStorageType
{
    GOOGLE_WEB_CLIENT_ID(0),
    GOOGLE_WEB_SECRET_ID(1),
    GOOGLE_IOS_CLIENT_ID(2),
    GOOGLE_IOS_SECRET_ID(3),
    OUTLOOK_CLIENT_ID(4),
    OUTLOOK_SECRET_ID(5),
    OFFICE365_WEB_CLIENT_ID(6),
    OFFICE365_WEB_SECRET_ID(7),
    OFFICE365_IOS_CLIENT_ID(8),
    OFFICE365_IOS_SECRET_ID(9),
    DROPBOX_CLIENT_ID(10),
    DROPBOX_SECRET_ID(11),
    VISMA_CLIENT_ID(12),
    VISMA_SECRET_ID(13),
    FORTNOX_SRECRET_ID(14),
    FORTNOX_CLIENT_ID(15);
    ;

    private int extension;

    private ServerStorageType()
    {

    }

    private ServerStorageType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return extension;
    }
}
