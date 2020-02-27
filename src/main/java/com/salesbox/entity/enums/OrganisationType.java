package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/27/14
 * Time: 11:37 PM
 */
public enum OrganisationType
{
    NONE(0),
    INDUSTRY(1),
    TYPE(2),
    ORGANISATION_SIZE(3),
    CONTACT_RELATIONSHIP(4),
    CONTACT_ROLE(5);

// ------------------------------ FIELDS ------------------------------

    private int extension;

// -------------------------- STATIC METHODS --------------------------

    private OrganisationType(int extension)
    {
        this.extension = extension;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public static OrganisationType getOrganisation(int extension)
    {
        try
        {
            return OrganisationType.values()[extension];
        }
        catch (Exception ex)
        {
            return null;
        }
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getExtension()
    {
        return this.extension;
    }
}
