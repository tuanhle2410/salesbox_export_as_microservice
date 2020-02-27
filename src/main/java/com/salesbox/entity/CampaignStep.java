package com.salesbox.entity;

import com.salesbox.entity.enums.CampaignStatusType;
import com.salesbox.entity.enums.SourceType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hunglv on 7/22/14.
 */

@Entity
@Table(name = "campaign_step")
public class CampaignStep extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private SourceType type;

    @Column(name = "index")
    private Integer index;

    @Column(name = "subject")
    private  String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Date date;

    @Column(name = "mailchimp_campaign_id")
    private String mailChimpCampaignId;

    @Column(name = "mailchimp_list_id")
    private String mailChimpListId;

    @Column(name = "mailchimp_list_name")
    private String mailChimpListName;

    @Column(name = "facebook_campaign_id")
    private String facebookCampaignId;

    @Column(name = "linkedin_campaign_id")
    private String linkedInCampaignId;

    @Column(name = "post_url")
    private String postUrl;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private CampaignStatusType status = CampaignStatusType.NOT_SHARED;

    @Column(name = "ingress")
    private String ingress;

    @Column(name = "linked_mailchimp")
    private Boolean linkedMailChimp = Boolean.TRUE;

    @Column(name = "website")
    private String website;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "google_plus")
    private String googlePlus;

    @Column(name = "linked_in")
    private String linkedIn;

    @Column(name = "mail_chimp_token")
    private String mailChimpToken;

    @Column(name = "header")
    private  String header;

    public Campaign getCampaign()
    {
        return campaign;
    }

    public void setCampaign(Campaign campaign)
    {
        this.campaign = campaign;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public SourceType getType()
    {
        return type;
    }

    public void setType(SourceType type)
    {
        this.type = type;
    }

    public String getMailChimpCampaignId()
    {
        return mailChimpCampaignId;
    }

    public void setMailChimpCampaignId(String mailChimpCampaignId)
    {
        this.mailChimpCampaignId = mailChimpCampaignId;
    }

    public String getMailChimpListId()
    {
        return mailChimpListId;
    }

    public void setMailChimpListId(String mailChimpListId)
    {
        this.mailChimpListId = mailChimpListId;
    }

    public String getFacebookCampaignId()
    {
        return facebookCampaignId;
    }

    public void setFacebookCampaignId(String facebookCampaignId)
    {
        this.facebookCampaignId = facebookCampaignId;
    }

    public String getLinkedInCampaignId()
    {
        return linkedInCampaignId;
    }

    public void setLinkedInCampaignId(String linkedInCampaignId)
    {
        this.linkedInCampaignId = linkedInCampaignId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getPostUrl()
    {
        return postUrl;
    }

    public void setPostUrl(String postUrl)
    {
        this.postUrl = postUrl;
    }

    public CampaignStatusType getStatus()
    {
        return status;
    }

    public void setStatus(CampaignStatusType status)
    {
        this.status = status;
    }

    public String getMailChimpListName()
    {
        return mailChimpListName;
    }

    public void setMailChimpListName(String mailChimpListName)
    {
        this.mailChimpListName = mailChimpListName;
    }

    public String getIngress()
    {
        return ingress;
    }

    public void setIngress(String ingress)
    {
        this.ingress = ingress;
    }

    public Boolean getLinkedMailChimp()
    {
        return linkedMailChimp;
    }

    public void setLinkedMailChimp(Boolean linkedMailChimp)
    {
        this.linkedMailChimp = linkedMailChimp;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getFacebook()
    {
        return facebook;
    }

    public void setFacebook(String facebook)
    {
        this.facebook = facebook;
    }

    public String getGooglePlus()
    {
        return googlePlus;
    }

    public void setGooglePlus(String googlePlus)
    {
        this.googlePlus = googlePlus;
    }

    public String getLinkedIn()
    {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn)
    {
        this.linkedIn = linkedIn;
    }

    public String getMailChimpToken()
    {
        return mailChimpToken;
    }

    public void setMailChimpToken(String mailChimpToken)
    {
        this.mailChimpToken = mailChimpToken;
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }
}
