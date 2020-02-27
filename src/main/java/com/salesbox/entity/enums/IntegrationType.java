package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/15/14
 * Time: 2:00 PM
 */
public enum IntegrationType
{
    GOOGLE(0),
    OUTLOOK(1),
    OFFICE365(2);


    private int extension;

    private IntegrationType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
