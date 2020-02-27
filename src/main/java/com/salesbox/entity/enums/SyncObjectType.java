package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 7/29/14
 * Time: 3:49 PM
 */
public enum SyncObjectType
{
    LEAD_BOXER(0),
    EXPORT_ALL_ACCOUNT(1),
    EXPORT_ALL_CONTACT(2),
    DELETE_CONTACT(3),
    DELETE_ACCOUNT(4),
    SYNC_OUTLOOK_MAIL(5),
    SYNC_GMAIL(6),
    SYNC_OFFICE_365(7);

    private int extension;

    private SyncObjectType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
