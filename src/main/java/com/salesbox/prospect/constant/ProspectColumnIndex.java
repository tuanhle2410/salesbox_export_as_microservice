package com.salesbox.prospect.constant;

/**
 * Created by hunglv on 10/2/14.
 */
public enum ProspectColumnIndex
{
    PROSPECT_ID(0),
    DESCRIPTION(1),
    SPONSOR(2),
    ACCOUNT_EMAIL(3),
    LINE_OF_BUSINESS(4),
    SALES_METHOD(5),
    CONTRACT_DATE(6),
    STATUS(7),
    OPPORTUNITY_TEAM(8),
    USER_SHARE_OF_ORDER_VALUE(9);

    private int index;

    private ProspectColumnIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return this.index;
    }
}
