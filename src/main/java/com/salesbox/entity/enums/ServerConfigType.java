package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/3/14
 * Time: 9:41 AM
 */
public enum ServerConfigType
{
    SUPPORT_VERSIONS(0),
    FREE_DATE(1),
    PROMOTION_TIME(2),
    SALE_TIME_END_TIME(3),
    INVITEE_FREE_DATE(4),
    NUMBER_FREE_LICENSE(5),
    IS_SENT_LOG_TO_EMAIL(6),
    NUMBER_FREE_DAYS_OF_EXPIRABLE_LICENSE(7),
    NUMBER_OFFER_DAYS(8),
    START_CAMPAIGN_DATE(9),
    IS_MAINTENANCE(10)
    ;

    private int extension;

    private ServerConfigType()
    {

    }

    private ServerConfigType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return extension;
    }
}
