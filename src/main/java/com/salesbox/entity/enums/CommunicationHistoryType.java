package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 6/17/14
 */
public enum CommunicationHistoryType
{
    CALL(0),
    DIAL(1),
//    FACE_TIME(2),
    EMAIL(2),
    I_MESSAGE(3),
    FACE_TIME_DIAL(4),
    FACE_TIME_CALL(5),
    EMAIL_SENDER(6),
    EMAIL_RECEIVER(7);

    int extension;

    CommunicationHistoryType(int extension)
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
