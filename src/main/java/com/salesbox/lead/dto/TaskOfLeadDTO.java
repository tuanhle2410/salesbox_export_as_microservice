package com.salesbox.lead.dto;

/**
 * Created by admin on 5/8/2017.
 */
public class TaskOfLeadDTO
{
    private boolean hasIdentifyLeadContact;
    private boolean hasQualifyLead;
    private boolean hasFollowUpLead;

    public boolean isHasIdentifyLeadContact()
    {
        return hasIdentifyLeadContact;
    }

    public void setHasIdentifyLeadContact(boolean hasIdentifyLeadContact)
    {
        this.hasIdentifyLeadContact = hasIdentifyLeadContact;
    }

    public boolean isHasQualifyLead()
    {
        return hasQualifyLead;
    }

    public void setHasQualifyLead(boolean hasQualifyLead)
    {
        this.hasQualifyLead = hasQualifyLead;
    }

    public boolean isHasFollowUpLead()
    {
        return hasFollowUpLead;
    }

    public void setHasFollowUpLead(boolean hasFollowUpLead)
    {
        this.hasFollowUpLead = hasFollowUpLead;
    }
}
