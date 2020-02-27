package com.salesbox.entity;

import com.salesbox.entity.enums.LeadType;
import com.salesbox.entity.enums.SourceType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * User: luult
 * Date: 7/16/14
 */
@Entity
@Table(name = "lead")
public class Lead extends BaseEntity
{
    @Column(name = "priority")
    private Double priority = 20d;  // default = LOWEST

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "source")
    @Enumerated(EnumType.ORDINAL)
    private SourceType source = SourceType.NONE;

    @Column(name = "is_finished")
    private Boolean finished = Boolean.FALSE;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "finished_date")
    private Date finishedDate;

    @Column(name = "deadline_date")
    private Date deadlineDate;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private LeadType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "line_of_business_id")
    private LineOfBusiness lineOfBusiness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @Column(name = "facebook_id")
    private String facebookId;

    @Column(name = "linkedin_id")
    private String linkedInId;

    @Column(name = "mailchimp_id")
    private String mailChimpId;

    @Column(name = "temp_email")
    private String tempEmail;

    @Column(name = "temp_first_name")
    private String tempFirstName;

    @Column(name = "temp_last_name")
    private String tempLastName;

    @Column(name = "temp_company_name")
    private String tempCompanyName;

    @Column(name = "facebook_like")
    private Boolean facebookLike = Boolean.FALSE;

    @Column(name = "facebook_comment")
    private Boolean facebookComment = Boolean.FALSE;

    @Column(name = "facebook_share")
    private Boolean facebookShare = Boolean.FALSE;

    @Column(name = "linked_in_like")
    private Boolean linkedInLike = Boolean.FALSE;

    @Column(name = "linked_in_comment")
    private Boolean linkedInComment = Boolean.FALSE;

    @Column(name = "linked_in_share")
    private Boolean linkedInShare = Boolean.FALSE;

    @Column(name = "mail_chimp_click")
    private Boolean mailChimpClick = Boolean.FALSE;

    @Column(name = "mail_chimp_total_open")
    private Long mailChimpTotalOpen = 0l;

    @Column(name = "mail_chimp_total_click")
    private Long mailChimpTotalClick = 0l;

    @Column(name = "mail_chimp_open")
    private Boolean mailChimpOpen = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "distribution_date")
    private Date distributionDate;

    @Column(name = "maestrano_id")
    private String maestranoId;

    @Column(name = "maestrano_org_id")
    private String maestranoOrgId;

    @Column(name = "status")
    private String status;

    @Type(type = "pg-uuid")
    @Column(name = "old_owner_id")
    private UUID oldOwnerId;

    @Column(name = "temp_lead")
    private Boolean tempLead = Boolean.FALSE;

    @Column(name = "leadboxer_id")
    private String leadBoxerId;

    @Column(name = "visit_more")
    private Boolean visitMore = Boolean.FALSE;

    @Column(name = "last_sync_time")
    private Date lastSyncTime = Calendar.getInstance().getTime();

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }

    public Boolean getTempLead()
    {
        return tempLead;
    }

    public void setTempLead(Boolean tempLead)
    {
        this.tempLead = tempLead;
    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public LineOfBusiness getLineOfBusiness()
    {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(LineOfBusiness lineOfBusiness)
    {
        this.lineOfBusiness = lineOfBusiness;
    }

    public Double getPriority()
    {
        return priority;
    }

    public void setPriority(Double priority)
    {
        this.priority = priority;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public Boolean getAccepted()
    {
        return accepted;
    }

    public void setAccepted(Boolean accepted)
    {
        this.accepted = accepted;
    }

    public SourceType getSource()
    {
        return source;
    }

    public void setSource(SourceType source)
    {
        this.source = source;
    }

    public Boolean getFinished()
    {
        return finished;
    }

    public void setFinished(Boolean finished)
    {
        this.finished = finished;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public LeadType getType()
    {
        return type;
    }

    public void setType(LeadType type)
    {
        this.type = type;
    }

    public User getCreator()
    {
        return creator;
    }

    public void setCreator(User creator)
    {
        this.creator = creator;
    }

    public Date getFinishedDate()
    {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate)
    {
        this.finishedDate = finishedDate;
    }

    public Campaign getCampaign()
    {
        return campaign;
    }

    public void setCampaign(Campaign campaign)
    {
        this.campaign = campaign;
    }

    public String getFacebookId()
    {
        return facebookId;
    }

    public void setFacebookId(String facebookId)
    {
        this.facebookId = facebookId;
    }

    public String getLinkedInId()
    {
        return linkedInId;
    }

    public void setLinkedInId(String linkedInId)
    {
        this.linkedInId = linkedInId;
    }

    public String getMailChimpId()
    {
        return mailChimpId;
    }

    public void setMailChimpId(String mailChimpId)
    {
        this.mailChimpId = mailChimpId;
    }

    public String getTempEmail()
    {
        return tempEmail;
    }

    public void setTempEmail(String tempEmail)
    {
        this.tempEmail = tempEmail;
    }

    public String getTempFirstName()
    {
        return tempFirstName;
    }

    public void setTempFirstName(String tempFirstName)
    {
        this.tempFirstName = tempFirstName;
    }

    public String getTempLastName()
    {
        return tempLastName;
    }

    public void setTempLastName(String tempLastName)
    {
        this.tempLastName = tempLastName;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public Boolean getFacebookLike()
    {
        return facebookLike;
    }

    public void setFacebookLike(Boolean facebookLike)
    {
        this.facebookLike = facebookLike;
    }

    public Boolean getFacebookComment()
    {
        return facebookComment;
    }

    public void setFacebookComment(Boolean facebookComment)
    {
        this.facebookComment = facebookComment;
    }

    public Boolean getFacebookShare()
    {
        return facebookShare;
    }

    public void setFacebookShare(Boolean facebookShare)
    {
        this.facebookShare = facebookShare;
    }

    public Boolean getLinkedInLike()
    {
        return linkedInLike;
    }

    public void setLinkedInLike(Boolean linkedInLike)
    {
        this.linkedInLike = linkedInLike;
    }

    public Boolean getLinkedInComment()
    {
        return linkedInComment;
    }

    public void setLinkedInComment(Boolean linkedInComment)
    {
        this.linkedInComment = linkedInComment;
    }

    public Boolean getLinkedInShare()
    {
        return linkedInShare;
    }

    public void setLinkedInShare(Boolean linkedInShare)
    {
        this.linkedInShare = linkedInShare;
    }

    public Boolean getMailChimpClick()
    {
        return mailChimpClick;
    }

    public void setMailChimpClick(Boolean mailChimpClick)
    {
        this.mailChimpClick = mailChimpClick;
    }

    public Long getMailChimpTotalOpen()
    {
        return mailChimpTotalOpen;
    }

    public void setMailChimpTotalOpen(Long mailChimpTotalOpen)
    {
        this.mailChimpTotalOpen = mailChimpTotalOpen;
    }

    public Long getMailChimpTotalClick()
    {
        return mailChimpTotalClick;
    }

    public void setMailChimpTotalClick(Long mailChimpTotalClick)
    {
        this.mailChimpTotalClick = mailChimpTotalClick;
    }

    public Boolean getMailChimpOpen()
    {
        return mailChimpOpen;
    }

    public void setMailChimpOpen(Boolean mailChimpOpen)
    {
        this.mailChimpOpen = mailChimpOpen;
    }

    //Only use when Lead finished
    public Long getLeadTime()
    {
        return this.finishedDate.getTime() - this.getCreatedDate().getTime();
    }

    public String getTempCompanyName()
    {
        return tempCompanyName;
    }

    public void setTempCompanyName(String tempCompanyName)
    {
        this.tempCompanyName = tempCompanyName;
    }

    public Date getDistributionDate()
    {
        return distributionDate;
    }

    public void setDistributionDate(Date distributionDate)
    {
        this.distributionDate = distributionDate;
    }

    public String getMaestranoId()
    {
        return maestranoId;
    }

    public void setMaestranoId(String maestranoId)
    {
        this.maestranoId = maestranoId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public UUID getOldOwnerId()
    {
        return oldOwnerId;
    }

    public void setOldOwnerId(UUID oldOwnerId)
    {
        this.oldOwnerId = oldOwnerId;
    }

    public String getLeadBoxerId()
    {
        return leadBoxerId;
    }

    public void setLeadBoxerId(String leadBoxerId)
    {
        this.leadBoxerId = leadBoxerId;
    }

    public Boolean getVisitMore()
    {
        return visitMore;
    }

    public void setVisitMore(Boolean visitMore)
    {
        this.visitMore = visitMore;
    }

    public Date getLastSyncTime()
    {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime)
    {
        this.lastSyncTime = lastSyncTime;
    }

    public String getMaestranoOrgId()
    {
        return maestranoOrgId;
    }

    public void setMaestranoOrgId(String maestranoOrgId)
    {
        this.maestranoOrgId = maestranoOrgId;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }



}
