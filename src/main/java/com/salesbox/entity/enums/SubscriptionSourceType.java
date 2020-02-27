package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/9/14
 * Time: 2:55 PM
 */
public enum SubscriptionSourceType
{
    STRIPE(0),
    APPLE(1),
    MAESTRANO(2),
    AMAZON(3);

    private int extension;

    private SubscriptionSourceType(int extension)
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
