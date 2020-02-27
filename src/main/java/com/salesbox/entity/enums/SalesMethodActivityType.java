package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 6/3/14
 */
public enum SalesMethodActivityType
{
    NORMAL(0),
    QUOTE_SENT(1),
    CONTRACT_SENT(2),
    CONTRACT_DATE(3);

    private int extension;

    private SalesMethodActivityType(int extension)
    {
        this.extension = extension;
    }
    public int getExtension()
    {
        return this.extension;
    }
}
