package com.salesbox.entity.enums;

/**
 * Created by hungnh on 1/21/15.
 */
public enum ContactUserSourceType
{
    MANUALLY(0),
    POOL(1),
    IMPORT_DEVICE(2),
    IMPORT_TEMPLATE(3),
    CAMPAIGN(4),
    GOOGLE(5),
    OUTLOOK(6),
    OFFICE_365(7),
    MAESTRANO(8),
    VISMA(8),
    FORTNOX(10);

    private int extension;

    ContactUserSourceType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return extension;
    }

    public void setExtension(int extension)
    {
        this.extension = extension;
    }
}
