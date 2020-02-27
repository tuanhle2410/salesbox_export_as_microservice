package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 11/13/14
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PromotionType
{
    NORMAL(0),
    INVITEE(1),
    SALE(2);

    private int extension;

    PromotionType(int extension)
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
