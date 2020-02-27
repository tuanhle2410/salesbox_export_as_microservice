package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/15/14
 * Time: 2:00 PM
 */
public enum StorageType
{
    DROP_BOX(0),
    FACEBOOK(1),
    LINKED_IN(2),
    MAIL_CHIMP(3),
    MAIL_CHIMP_TOKEN(4),
    LINKED_IN_TOKEN(5),
    FACEBOOK_TOKEN(6),
    FACEBOOK_PAGE(7),
    DEFAULT_FACEBOOK_APP_ID(8),
    DEFAULT_FACEBOOK_SECRET(9),
    LINKED_IN_PAGE(10),
    DEFAULT_LINKED_CLIENT_ID(11),
    DEFAULT_LINKED_ID_SECRET(12),
    MAIL_CHIMP_EMAIL(13),
    MAIL_CHIMP_WEB_TOKEN(14),
    MAIL_CHIMP_WEB(15),
    MAIL_CHIMP_WEB_EMAIL(16),
    ONE_DRIVE(17),
    GOOGLE_DRIVE(18),
    ONE_DRIVE_FOR_BUSINESS(19),
    LEAD_BOXER_API_KEY(20),
    LEAD_BOXER_SITE_ID(21),
    LEAD_BOXER_EMAIL(22),
    LEAD_BOXER_LAST_SYNC(23),
    VISMA_USER_TOKEN(24),
    VISMA_COMPANY(25),
    VISMA_HAMMER_GLASS(28),
    FORTNOX_API_KEY(26),
    FORTNOX_COMPANY_NAME(27);



    private int extension;

    private StorageType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
