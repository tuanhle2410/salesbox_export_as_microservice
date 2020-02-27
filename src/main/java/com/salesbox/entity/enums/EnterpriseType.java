package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/13/14
 * Time: 6:07 PM
 */
public enum EnterpriseType
{
    FREE(0),
    PREMIUM(1),
    CANCELLATION(2),
    SUSPENDED(3);

    private int extension;

    private EnterpriseType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
