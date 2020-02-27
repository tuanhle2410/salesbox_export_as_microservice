package com.salesbox.organisation.constant;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 9/29/14
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public enum  AccountColumnIndex
{
    NUMBER_OF(0),
    ACCOUNT_NAME(1),
    VAT(2),
    ADDRESS(3),
    ZIP_CODE(4),
    CITY(5),
    REGION(6),
    COUNTRY(7),
    PHONE(8),
    EMAIL(9),
    WEB(10),
    TYPE(11),
    INDUSTRY(12),
    SIZE(13),
    BUDGET(14),
    APPOINTMENT_GOAL(15),
    ACCOUNT_TEAM(16),
    LAST_NOTE(17),
    LAST_COMMUNICATION(18),
    CLOSED_SALES(19),
    PROFIT(20),
    MEDIAN_DEAL_SIZE(21),
    MEDIAN_DEAL_TIME(22),
    GROSS_PINE(23),
    WEIGHTED_PINE(24),

    BEGIN_CUSTOM_FIELD(25),;

    private int index;

    private AccountColumnIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return this.index;
    }
}
