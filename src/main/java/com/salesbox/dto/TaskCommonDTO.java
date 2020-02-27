package com.salesbox.dto;

import java.util.Date;
import java.util.UUID;

/**
 * Created by admin on 3/31/2017.
 */
public class TaskCommonDTO
{

    protected UUID organisationId;
    private Date dateAndTime;
    private UUID contactId;
    private UUID leadId;

    public UUID getLeadId()
    {
        return leadId;
    }

    public void setLeadId(UUID leadId)
    {
        this.leadId = leadId;
    }

    public UUID getOrganisationId()
    {
        return organisationId;
    }

    public void setOrganisationId(UUID organisationId)
    {
        this.organisationId = organisationId;
    }

    public UUID getContactId()
    {
        return contactId;
    }

    public void setContactId(UUID contactId)
    {
        this.contactId = contactId;
    }

    public Date getDateAndTime()
    {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime)
    {
        this.dateAndTime = dateAndTime;
    }
}
