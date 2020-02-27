package com.salesbox.lead.dto;

import com.salesbox.dto.LeadCommonDTO;
import com.salesbox.dto.LineOfBusinessDTO;
import com.salesbox.dto.ProductDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hunglv on 7/15/14.
 */
public class LeadDTO extends LeadCommonDTO
{
    private String note;
    private UUID sharedContactId;
    private String contactFirstName;
    private String contactLastName;
    private String contactEmail;
    private String contactPhone;
    private String contactAvatar;
    private String contactDiscProfile;

    private String organisationName;
    private String organisationEmail;
    private String organisationPhone;
    private List<ProductDTO> productList = new ArrayList<>();
    private String leadBoxerId;
    private Boolean visitMore;
    private LineOfBusinessDTO lineOfBusiness;

    private String ownerFirstName;
    private String ownerLastName;
    private String ownerAvatar;
    private String ownerDiscProfile;
    private Long ownerMedianLeadTime;

    private UUID creatorId;
    private String creatorFirstName;
    private String creatorLastName;
    private String creatorAvatar;
    private String creatorDiscProfile;

    private Double priority;
    private Boolean finished;
    private Boolean deleted;
    private Boolean accepted;
    private String type;
    private UUID prospectId;
    private String source;
    private Date updatedDate;
    private Date createdDate;
    private Date finishedDate;
    private Date deadlineDate;

    private UUID campaignId;
    private String facebookId;
    private String linkedInId;
    private String mailChimpId;
    private Long mailChimpTotalClick;
    private Long mailChimpTotalOpen;

    private String tempEmail;
    private String tempFirstName;
    private String tempLastName;
    private String tempCompanyName;
    private String status;
    private Integer gmt;

    private Date distributionDate;
    private Long countOfActiveTask;

    private Long countOfActiveAppointment;

    private Date lastSyncTime;

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public UUID getSharedContactId()
    {
        return sharedContactId;
    }

    public void setSharedContactId(UUID sharedContactId)
    {
        this.sharedContactId = sharedContactId;
    }

    public String getContactFirstName()
    {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName)
    {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName()
    {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName)
    {
        this.contactLastName = contactLastName;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public String getContactAvatar()
    {
        return contactAvatar;
    }

    public void setContactAvatar(String contactAvatar)
    {
        this.contactAvatar = contactAvatar;
    }

    public String getContactDiscProfile()
    {
        return contactDiscProfile;
    }

    public void setContactDiscProfile(String contactDiscProfile)
    {
        this.contactDiscProfile = contactDiscProfile;
    }

    public String getOrganisationName()
    {
        return organisationName;
    }

    public void setOrganisationName(String organisationName)
    {
        this.organisationName = organisationName;
    }

    public String getOrganisationEmail()
    {
        return organisationEmail;
    }

    public void setOrganisationEmail(String organisationEmail)
    {
        this.organisationEmail = organisationEmail;
    }

    public String getOrganisationPhone()
    {
        return organisationPhone;
    }

    public void setOrganisationPhone(String organisationPhone)
    {
        this.organisationPhone = organisationPhone;
    }

    public List<ProductDTO> getProductList()
    {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList)
    {
        this.productList = productList;
    }

    public LineOfBusinessDTO getLineOfBusiness()
    {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(LineOfBusinessDTO lineOfBusiness)
    {
        this.lineOfBusiness = lineOfBusiness;
    }

    public String getOwnerFirstName()
    {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName)
    {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName()
    {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName)
    {
        this.ownerLastName = ownerLastName;
    }

    public Double getPriority()
    {
        return priority;
    }

    public void setPriority(Double priority)
    {
        this.priority = priority;
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

    public Boolean getAccepted()
    {
        return accepted;
    }

    public void setAccepted(Boolean accepted)
    {
        this.accepted = accepted;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public UUID getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getCreatorFirstName()
    {
        return creatorFirstName;
    }

    public void setCreatorFirstName(String creatorFirstName)
    {
        this.creatorFirstName = creatorFirstName;
    }

    public String getCreatorLastName()
    {
        return creatorLastName;
    }

    public void setCreatorLastName(String creatorLastName)
    {
        this.creatorLastName = creatorLastName;
    }

    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Date getFinishedDate()
    {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate)
    {
        this.finishedDate = finishedDate;
    }

    public UUID getCampaignId()
    {
        return campaignId;
    }

    public void setCampaignId(UUID campaignId)
    {
        this.campaignId = campaignId;
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

    public String getTempCompanyName()
    {
        return tempCompanyName;
    }

    public void setTempCompanyName(String tempCompanyName)
    {
        this.tempCompanyName = tempCompanyName;
    }

    public String getOwnerAvatar()
    {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar)
    {
        this.ownerAvatar = ownerAvatar;
    }

    public String getOwnerDiscProfile()
    {
        return ownerDiscProfile;
    }

    public void setOwnerDiscProfile(String ownerDiscProfile)
    {
        this.ownerDiscProfile = ownerDiscProfile;
    }

    public String getCreatorAvatar()
    {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar)
    {
        this.creatorAvatar = creatorAvatar;
    }

    public String getCreatorDiscProfile()
    {
        return creatorDiscProfile;
    }

    public void setCreatorDiscProfile(String creatorDiscProfile)
    {
        this.creatorDiscProfile = creatorDiscProfile;
    }

    public Date getDistributionDate()
    {
        return distributionDate;
    }

    public void setDistributionDate(Date distributionDate)
    {
        this.distributionDate = distributionDate;
    }

    public Long getOwnerMedianLeadTime()
    {
        return ownerMedianLeadTime;
    }

    public void setOwnerMedianLeadTime(Long ownerMedianLeadTime)
    {
        this.ownerMedianLeadTime = ownerMedianLeadTime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Long getCountOfActiveTask()
    {
        return countOfActiveTask;
    }

    public void setCountOfActiveTask(Long countOfActiveTask)
    {
        this.countOfActiveTask = countOfActiveTask;
    }

    public Long getCountOfActiveAppointment()
    {
        return countOfActiveAppointment;
    }

    public String getLeadBoxerId() {
        return leadBoxerId;
    }

    public void setLeadBoxerId(String leadBoxerId) {
        this.leadBoxerId = leadBoxerId;
    }

    public Boolean getVisitMore() {
        return visitMore;
    }

    public void setVisitMore(Boolean visitMore) {
        this.visitMore = visitMore;
    }

    public void setCountOfActiveAppointment(Long countOfActiveAppointment)
    {
        this.countOfActiveAppointment = countOfActiveAppointment;
    }

    public Integer getGmt()
    {
        return gmt;
    }

    public void setGmt(Integer gmt)
    {
        this.gmt = gmt;
    }

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}
