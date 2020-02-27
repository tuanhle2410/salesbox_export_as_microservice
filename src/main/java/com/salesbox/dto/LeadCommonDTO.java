package com.salesbox.dto;

import java.util.UUID;

/**
 * Created by admin on 3/30/2017.
 */
public class LeadCommonDTO
{
    UUID uuid;
    UUID contactId;
    UUID organisationId;
    UUID ownerId;

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public UUID getContactId()
    {
        return contactId;
    }

    public void setContactId(UUID contactId)
    {
        this.contactId = contactId;
    }

    public UUID getOrganisationId()
    {
        return organisationId;
    }

    public void setOrganisationId(UUID organisationId)
    {
        this.organisationId = organisationId;
    }

    public UUID getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId)
    {
        this.ownerId = ownerId;
    }
}
