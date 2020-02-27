package com.salesbox.contact.constant;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 9/29/14
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public enum  ContactColumnIndex
{
    NO(0),
    FIRST_NAME(1),
    LAST_NAME(2),
    ACCOUNT_EMAIL(3),
    ADDRESS(4),
    ZIP_CODE(5),
    CITY(6),
    REGION(7),
    COUNTRY(8),
    TITLE(9),
    PHONE(10),
    EMAIL(11),
    TYPE(12),
    INDUSTRY(13),
    RELATION(14),
    RELATIONSHIP(15),
    DISC_PROFILE(16),
    CONTACT_TEAM(17),
    LAST_NOTE(18),
    LASTEST_COMMUNICATION(19),
    CLOSED_SALES(20),
    PROFIT(21),
    MEDIAN_DEAL_SIZE(22),
    MEDIAN_DEAL_TIME(23),
    GROSS_PIPE(24),
    WEIGHTED_PIPE(25),

    BEGIN_CUSTOM_FIELD(26),
    ;

    private int index;

    private ContactColumnIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return this.index;
    }
}
