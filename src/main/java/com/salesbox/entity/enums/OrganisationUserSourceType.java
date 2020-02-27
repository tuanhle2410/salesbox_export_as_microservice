package com.salesbox.entity.enums;

/**
 * Created by hungnh on 1/21/15.
 */
public enum OrganisationUserSourceType
{
    MANUALLY(0),
    POOL(1),
    IMPORT_TEMPLATE(2),
    CAMPAIGN(3),
    MAESTRANO(4),
    VISMA(5)
    ;

    private int extension;

    OrganisationUserSourceType(int extension)
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
