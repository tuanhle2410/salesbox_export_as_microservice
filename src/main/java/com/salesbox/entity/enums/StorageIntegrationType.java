package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/15/14
 * Time: 2:00 PM
 */
public enum StorageIntegrationType
{
    GOOGLE_WEB(0),
    GOOGLE_IOS(1),
    OUTLOOK(2),
    OFFICE365_WEB(3),
    OFFICE365_IOS(4),
    ONE_DRIVE(5),
    DROP_BOX(6),
    GMAIL(7),
    ONE_DRIVE_FOR_BUSINESS(8);


    private int extension;

    private StorageIntegrationType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
