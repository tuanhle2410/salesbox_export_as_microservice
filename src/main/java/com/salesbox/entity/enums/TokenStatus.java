package com.salesbox.entity.enums;

/**
 * Created by GEM on 9/12/2018.
 */
public enum TokenStatus
{
    VALID(0),
    INVALID(1),
    REVOKED(2),
    REVOKED_AND_MAILED(3);
    private int index;

    TokenStatus(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }
}
