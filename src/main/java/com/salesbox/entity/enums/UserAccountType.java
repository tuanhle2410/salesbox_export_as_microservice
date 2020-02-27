package com.salesbox.entity.enums;

/**
 * Created by hungnh on 2/9/15.
 */
public enum UserAccountType
{
    FREE_FOREVER(0),
    PAID(1),
    FREE_EXPIRABLE(2)
    ;

    private int extension;

    private UserAccountType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return extension;
    }
}
