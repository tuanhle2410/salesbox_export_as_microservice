package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 5/21/14
 */
public enum OrderRowsType
{
    NORMAL(0),
    FIXED(1),
    RECURRING(2);

    private int extension;

    private OrderRowsType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
