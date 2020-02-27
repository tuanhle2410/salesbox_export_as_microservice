package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 6/17/14
 */
public enum CommunicationType
{
    // Contact communication type
    EMAIL_HOME(0),
    EMAIL_WORK(1),
    EMAIL_ICLOUD(2),
    EMAIL_OTHER(3),

    PHONE_HOME(4),
    PHONE_WORK(5),
    PHONE_IPHONE(6),
    PHONE_MOBILE(7),
    PHONE_MAIN(8),
    PHONE_HOME_FAX(9),
    PHONE_WORK_FAX(10),
    PHONE_OTHER(11),

    // Organisation communication type
    EMAIL_HEAD_QUARTER(12),
    EMAIL_SUBSIDIARY(13),
    EMAIL_DEPARTMENT(14),
    EMAIL_UNIT(15),

    PHONE_HEAD_QUARTER(16),
    PHONE_SUBSIDIARY(17),
    PHONE_DEPARTMENT(18),
    PHONE_UNIT(19);

    int extension;

    CommunicationType(int extension)
    {
        this.extension = extension;
    }


// --------------------- GETTER / SETTER METHODS ---------------------

    public int getExtension()
    {
        return extension;
    }

    public void setExtension(int extension)
    {
        this.extension = extension;
    }
}
