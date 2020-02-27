package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 8/15/14
 * Time: 10:39 AM
 */
@Entity
@Table(name = "campaign_contact")
public class CampaignContact extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    public Campaign getCampaign()
    {
        return campaign;
    }

    public void setCampaign(Campaign campaign)
    {
        this.campaign = campaign;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }
}
