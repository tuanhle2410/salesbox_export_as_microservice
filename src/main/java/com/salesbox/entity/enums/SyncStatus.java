package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/17/14
 * Time: 3:22 PM
 */
public enum SyncStatus
{
    IN_PROGRESS(0),
    SUCCESS(1),
    FAILED(2),
    TURN_OFF(3);
    private int extension;

    private SyncStatus(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
