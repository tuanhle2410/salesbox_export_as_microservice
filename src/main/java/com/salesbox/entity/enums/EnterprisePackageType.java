package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/13/14
 * Time: 6:07 PM
 */
public enum EnterprisePackageType
{
    FREE(0),
    SUPER_MONTHLY(1),
    SUPER_YEARLY(2),
    ULTRA_MONTHLY(3),
    ULTRA_YEARLY(4),
    ULTIMATE_MONTHLY(5),
    ULTIMATE_YEARLY(6),
    BASIC_MONTHLY (7),
    BASIC_YEARLY (8)
    ;

    private int extension;

    private EnterprisePackageType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
