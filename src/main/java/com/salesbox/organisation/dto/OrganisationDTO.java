package com.salesbox.organisation.dto;

import com.salesbox.dto.WorkDataOrganisationDTO;
import com.salesbox.dto.CommunicationDTO;
import com.salesbox.dto.OrganisationCommonDTO;
import com.salesbox.dto.ParticipantDTO;
import com.salesbox.entity.Organisation;
import com.salesbox.annotation.OrikaMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * [File description]
 *
 * @author lent
 * @version 1.0
 * @since 5/1/14
 */
@OrikaMapper(mapClass = Organisation.class, excludes = {"type", "industry", "size", "participantList", "shippingAddress", "billingAddress"})
public class OrganisationDTO extends OrganisationCommonDTO
{
    private UUID uuid;
    private String formalName;
    private String vatNumber;
    private Date createdDate;
    private Date updatedDate;
    private Long numberMeeting;
    private Long numberActiveTask;
    private Long numberActiveMeeting;
    private Long numberAccountTeam;
    private UUID firstMeetingId;
    private String locationFirstMeeting;
    private Date startDateFirstMeeting;
    private Long numberProspect;
    private Long numberActiveProspect;
    private Long numberPick;
    private Long numberCall;
    private boolean favorite;
    private String accountGrowth;
    private Double medianDealSize;
    private Long medianDealTime;
    private Double numberActualMeeting;
    private Double orderIntake;
    private Double grossPipeline;
    private Double netPipeline;
    private Double budget;
    private Double pipeMargin;
    private Double closedMargin; // margin in detail screen
    private Double relationship;
    private Double wonProfit;
    private Double pipeProfit;

    private String phone;
    private String mainPhoneType;
    private String email;
    private String mainEmailType;
    private String street;
    private String zipCode;
    private String city;
    private String state;
    private String country;
    private String fullAddress;
    private WorkDataOrganisationDTO type;
    private WorkDataOrganisationDTO industry;
    private WorkDataOrganisationDTO size;

    private List<ParticipantDTO> participantList = new ArrayList<ParticipantDTO>();
    private String web;
    private Boolean active;

    private UUID ownerId;
    private String ownerName;
    private String ownerAvatar;
    private String ownerDiscProfile;

    private Integer numberPhoto;
    private String descriptionLatestUpload;
    private Date updatedDateLatestUpload;

    private Integer numberDocument = 0;
    private String authorNameLatestDocument;
    private String subjectLatestDocument;

    private Integer numberNote;
    private String authorNameLatestNote;
    private String subjectLatestNote;

    private String nextTaskFocus;
    private Date nextTaskDateAndTime;

    private Integer numberContact;

    private Integer numberClosedProspect;
    private Integer numberWonProspect;

    private String mediaType;
    private String pipeMarginWarning;
    private String closedMarginWarning;
    private String netPipelineWarning;
    private UUID sharedOrganisationId;
    private Date lastViewed;
    private String recentActionType;


    private String externalKey;
    private Integer numberActiveLead;

    private Boolean isChanged;
    private Boolean isPrivate;

    private List<CommunicationDTO> additionalEmailList = new ArrayList<>();
    private List<CommunicationDTO> additionalPhoneList = new ArrayList<>();

    private UUID creatorId;
    private Boolean deleted;
    private String avatar;

    private Double totalClosedSalesCurrentYear;
    private Long numberFinishedMeetingCurrentWeek;

    private Date latestCallDate;
    private Date latestDialDate;

    private String linkedinProfileId;
    private String linkedinProfileSalesId;
    private AddressDTO shippingAddress;
    private AddressDTO billingAddress;
    private String phoneticName;

    public String getFormalName()
    {
        return formalName;
    }

    public void setFormalName(String formalName)
    {
        this.formalName = formalName;
    }

    public String getVatNumber()
    {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber)
    {
        this.vatNumber = vatNumber;
    }

    public AddressDTO getShippingAddress()
    {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDTO shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public AddressDTO getBillingAddress()
    {
        return billingAddress;
    }

    public void setBillingAddress(AddressDTO billingAddress)
    {
        this.billingAddress = billingAddress;
    }

    private Boolean addFromLead = Boolean.FALSE;

    public Boolean getChanged()
    {
        return isChanged;
    }

    public void setChanged(Boolean changed)
    {
        isChanged = changed;
    }

    public Boolean getPrivate()
    {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate)
    {
        isPrivate = aPrivate;
    }

    public String getLinkedinProfileId()
    {
        return linkedinProfileId;
    }

    public void setLinkedinProfileId(String linkedinProfileId)
    {
        this.linkedinProfileId = linkedinProfileId;
    }

    public String getLinkedinProfileSalesId()
    {
        return linkedinProfileSalesId;
    }

    public void setLinkedinProfileSalesId(String linkedinProfileSalesId)
    {
        this.linkedinProfileSalesId = linkedinProfileSalesId;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public Integer getNumberContact()
    {
        return numberContact;
    }

    public void setNumberContact(Integer numberContact)
    {
        this.numberContact = numberContact;
    }

    public Long getNumberMeeting()
    {
        return numberMeeting;
    }

    public void setNumberMeeting(Long numberMeeting)
    {
        this.numberMeeting = numberMeeting;
    }


    public UUID getFirstMeetingId()
    {
        return firstMeetingId;
    }

    public void setFirstMeetingId(UUID firstMeetingId)
    {
        this.firstMeetingId = firstMeetingId;
    }

    public Long getNumberActiveTask()
    {
        return numberActiveTask;
    }

    public void setNumberActiveTask(Long numberActiveTask)
    {
        this.numberActiveTask = numberActiveTask;
    }

    public Long getNumberActiveMeeting()
    {
        return numberActiveMeeting;
    }

    public void setNumberActiveMeeting(Long numberActiveMeeting)
    {
        this.numberActiveMeeting = numberActiveMeeting;
    }

    public Long getNumberAccountTeam()
    {
        return numberAccountTeam;
    }

    public void setNumberAccountTeam(Long numberAccountTeam)
    {
        this.numberAccountTeam = numberAccountTeam;
    }

    public Long getNumberProspect()
    {
        return numberProspect;
    }

    public void setNumberProspect(Long numberProspect)
    {
        this.numberProspect = numberProspect;
    }

    public Long getNumberPick()
    {
        return numberPick;
    }

    public void setNumberPick(Long numberPick)
    {
        this.numberPick = numberPick;
    }

    public Long getNumberCall()
    {
        return numberCall;
    }

    public void setNumberCall(Long numberCall)
    {
        this.numberCall = numberCall;
    }

    public boolean isFavorite()
    {
        return favorite;
    }

    public void setFavorite(boolean favorite)
    {
        this.favorite = favorite;
    }

    public String getAccountGrowth()
    {
        return accountGrowth;
    }

    public void setAccountGrowth(String accountGrowth)
    {
        this.accountGrowth = accountGrowth;
    }

    public Double getMedianDealSize()
    {
        return medianDealSize;
    }

    public void setMedianDealSize(Double medianDealSize)
    {
        this.medianDealSize = medianDealSize;
    }

    public WorkDataOrganisationDTO getSize()
    {
        return size;
    }

    public void setSize(WorkDataOrganisationDTO size)
    {
        this.size = size;
    }

    public Double getNumberActualMeeting()
    {
        return numberActualMeeting;
    }

    public void setNumberActualMeeting(Double numberActualMeeting)
    {
        this.numberActualMeeting = numberActualMeeting;
    }

    public Double getOrderIntake()
    {
        return orderIntake;
    }

    public void setOrderIntake(Double orderIntake)
    {
        this.orderIntake = orderIntake;
    }

    public Double getGrossPipeline()
    {
        return grossPipeline;
    }

    public void setGrossPipeline(Double grossPipeline)
    {
        this.grossPipeline = grossPipeline;
    }

    public Double getNetPipeline()
    {
        return netPipeline;
    }

    public void setNetPipeline(Double netPipeline)
    {
        this.netPipeline = netPipeline;
    }

    public Double getBudget()
    {
        return budget;
    }

    public void setBudget(Double budget)
    {
        this.budget = budget;
    }

    public Double getPipeMargin()
    {
        return pipeMargin;
    }

    public void setPipeMargin(Double pipeMargin)
    {
        this.pipeMargin = pipeMargin;
    }

    public Double getClosedMargin()
    {
        return closedMargin;
    }

    public void setClosedMargin(Double closedMargin)
    {
        this.closedMargin = closedMargin;
    }

    public Double getRelationship()
    {
        return relationship;
    }

    public void setRelationship(Double relationship)
    {
        this.relationship = relationship;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getFullAddress()
    {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress)
    {
        this.fullAddress = fullAddress;
    }

    public WorkDataOrganisationDTO getType()
    {
        return type;
    }

    public void setType(WorkDataOrganisationDTO type)
    {
        this.type = type;
    }

    public WorkDataOrganisationDTO getIndustry()
    {
        return industry;
    }

    public void setIndustry(WorkDataOrganisationDTO industry)
    {
        this.industry = industry;
    }

    public List<ParticipantDTO> getParticipantList()
    {
        return participantList;
    }

    public void setParticipantList(List<ParticipantDTO> participantList)
    {
        this.participantList = participantList;
    }

    public String getWeb()
    {
        return web;
    }

    public void setWeb(String web)
    {
        this.web = web;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Integer getNumberPhoto()
    {
        return numberPhoto;
    }

    public void setNumberPhoto(Integer numberPhoto)
    {
        this.numberPhoto = numberPhoto;
    }

    public String getDescriptionLatestUpload()
    {
        return descriptionLatestUpload;
    }

    public void setDescriptionLatestUpload(String descriptionLatestUpload)
    {
        this.descriptionLatestUpload = descriptionLatestUpload;
    }

    public Date getUpdatedDateLatestUpload()
    {
        return updatedDateLatestUpload;
    }

    public void setUpdatedDateLatestUpload(Date updatedDateLatestUpload)
    {
        this.updatedDateLatestUpload = updatedDateLatestUpload;
    }

    public Integer getNumberDocument()
    {
        return numberDocument;
    }

    public void setNumberDocument(Integer numberDocument)
    {
        this.numberDocument = numberDocument;
    }

    public String getAuthorNameLatestDocument()
    {
        return authorNameLatestDocument;
    }

    public void setAuthorNameLatestDocument(String authorNameLatestDocument)
    {
        this.authorNameLatestDocument = authorNameLatestDocument;
    }

    public String getSubjectLatestDocument()
    {
        return subjectLatestDocument;
    }

    public void setSubjectLatestDocument(String subjectLatestDocument)
    {
        this.subjectLatestDocument = subjectLatestDocument;
    }

    public Integer getNumberNote()
    {
        return numberNote;
    }

    public void setNumberNote(Integer numberNote)
    {
        this.numberNote = numberNote;
    }

    public String getAuthorNameLatestNote()
    {
        return authorNameLatestNote;
    }

    public void setAuthorNameLatestNote(String authorNameLatestNote)
    {
        this.authorNameLatestNote = authorNameLatestNote;
    }

    public String getSubjectLatestNote()
    {
        return subjectLatestNote;
    }

    public void setSubjectLatestNote(String subjectLatestNote)
    {
        this.subjectLatestNote = subjectLatestNote;
    }

    public String getNextTaskFocus()
    {
        return nextTaskFocus;
    }

    public void setNextTaskFocus(String nextTaskFocus)
    {
        this.nextTaskFocus = nextTaskFocus;
    }

    public Date getNextTaskDateAndTime()
    {
        return nextTaskDateAndTime;
    }

    public void setNextTaskDateAndTime(Date nextTaskDateAndTime)
    {
        this.nextTaskDateAndTime = nextTaskDateAndTime;
    }

    public Integer getNumberClosedProspect()
    {
        return numberClosedProspect;
    }

    public void setNumberClosedProspect(Integer numberClosedProspect)
    {
        this.numberClosedProspect = numberClosedProspect;
    }

    public Integer getNumberWonProspect()
    {
        return numberWonProspect;
    }

    public void setNumberWonProspect(Integer numberWonProspect)
    {
        this.numberWonProspect = numberWonProspect;
    }

    public UUID getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId)
    {
        this.ownerId = ownerId;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
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

    public Long getMedianDealTime()
    {
        return medianDealTime;
    }

    public void setMedianDealTime(Long medianDealTime)
    {
        this.medianDealTime = medianDealTime;
    }

    public String getMediaType()
    {
        return mediaType;
    }

    public void setMediaType(String mediaType)
    {
        this.mediaType = mediaType;
    }

    public String getPipeMarginWarning()
    {
        return pipeMarginWarning;
    }

    public void setPipeMarginWarning(String pipeMarginWarning)
    {
        this.pipeMarginWarning = pipeMarginWarning;
    }

    public String getClosedMarginWarning()
    {
        return closedMarginWarning;
    }

    public void setClosedMarginWarning(String closedMarginWarning)
    {
        this.closedMarginWarning = closedMarginWarning;
    }

    public String getNetPipelineWarning()
    {
        return netPipelineWarning;
    }

    public void setNetPipelineWarning(String netPipelineWarning)
    {
        this.netPipelineWarning = netPipelineWarning;
    }

    public Long getNumberActiveProspect()
    {
        return numberActiveProspect;
    }

    public void setNumberActiveProspect(Long numberActiveProspect)
    {
        this.numberActiveProspect = numberActiveProspect;
    }

    public UUID getSharedOrganisationId()
    {
        return sharedOrganisationId;
    }

    public void setSharedOrganisationId(UUID sharedOrganisationId)
    {
        this.sharedOrganisationId = sharedOrganisationId;
    }

    public Date getLastViewed()
    {
        return lastViewed;
    }

    public void setLastViewed(Date lastViewed)
    {
        this.lastViewed = lastViewed;
    }

    public String getRecentActionType()
    {
        return recentActionType;
    }

    public void setRecentActionType(String recentActionType)
    {
        this.recentActionType = recentActionType;
    }

    public String getExternalKey()
    {
        return externalKey;
    }

    public void setExternalKey(String externalKey)
    {
        this.externalKey = externalKey;
    }

    public Double getWonProfit()
    {
        return wonProfit;
    }

    public void setWonProfit(Double wonProfit)
    {
        this.wonProfit = wonProfit;
    }

    public Double getPipeProfit()
    {
        return pipeProfit;
    }

    public void setPipeProfit(Double pipeProfit)
    {
        this.pipeProfit = pipeProfit;
    }

    public Integer getNumberActiveLead()
    {
        return numberActiveLead;
    }

    public void setNumberActiveLead(Integer numberActiveLead)
    {
        this.numberActiveLead = numberActiveLead;
    }

    public String getLocationFirstMeeting()
    {
        return locationFirstMeeting;
    }

    public void setLocationFirstMeeting(String locationFirstMeeting)
    {
        this.locationFirstMeeting = locationFirstMeeting;
    }

    public Date getStartDateFirstMeeting()
    {
        return startDateFirstMeeting;
    }

    public void setStartDateFirstMeeting(Date startDateFirstMeeting)
    {
        this.startDateFirstMeeting = startDateFirstMeeting;
    }

    public Boolean getIsChanged()
    {
        return isChanged;
    }

    public void setIsChanged(Boolean isChanged)
    {
        this.isChanged = isChanged;
    }

    public String getMainPhoneType()
    {
        return mainPhoneType;
    }

    public void setMainPhoneType(String mainPhoneType)
    {
        this.mainPhoneType = mainPhoneType;
    }

    public String getMainEmailType()
    {
        return mainEmailType;
    }

    public void setMainEmailType(String mainEmailType)
    {
        this.mainEmailType = mainEmailType;
    }

    public List<CommunicationDTO> getAdditionalEmailList()
    {
        return additionalEmailList;
    }

    public void setAdditionalEmailList(List<CommunicationDTO> additionalEmailList)
    {
        this.additionalEmailList = additionalEmailList;
    }

    public List<CommunicationDTO> getAdditionalPhoneList()
    {
        return additionalPhoneList;
    }

    public void setAdditionalPhoneList(List<CommunicationDTO> additionalPhoneList)
    {
        this.additionalPhoneList = additionalPhoneList;
    }

    public UUID getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId)
    {
        this.creatorId = creatorId;
    }

    public Boolean getIsPrivate()
    {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate)
    {
        this.isPrivate = isPrivate;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public Double getTotalClosedSalesCurrentYear()
    {
        return totalClosedSalesCurrentYear;
    }

    public void setTotalClosedSalesCurrentYear(Double totalClosedSalesCurrentYear)
    {
        this.totalClosedSalesCurrentYear = totalClosedSalesCurrentYear;
    }

    public Long getNumberFinishedMeetingCurrentWeek()
    {
        return numberFinishedMeetingCurrentWeek;
    }

    public void setNumberFinishedMeetingCurrentWeek(Long numberFinishedMeetingCurrentWeek)
    {
        this.numberFinishedMeetingCurrentWeek = numberFinishedMeetingCurrentWeek;
    }

    public Date getLatestCallDate()
    {
        return latestCallDate;
    }

    public void setLatestCallDate(Date latestCallDate)
    {
        this.latestCallDate = latestCallDate;
    }

    public Date getLatestDialDate()
    {
        return latestDialDate;
    }

    public void setLatestDialDate(Date latestDialDate)
    {
        this.latestDialDate = latestDialDate;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public Boolean getAddFromLead() {
        return addFromLead;
    }

    public void setAddFromLead(Boolean addFromLead) {
        this.addFromLead = addFromLead;
    }


    public String getPhoneticName()
    {
        return phoneticName;
    }

    public void setPhoneticName(String phoneticName)
    {
        this.phoneticName = phoneticName;
    }

    public List<String> getAllEmail()
    {
        List<String> additionalEmailList = new ArrayList<>();

        for (CommunicationDTO communicationDTO : this.additionalEmailList)
        {
            additionalEmailList.add(communicationDTO.getValue().toLowerCase());
        }
        return additionalEmailList;
    }

    public List<String> getAllPhone()
    {
        List<String> additionalEmailList = new ArrayList<>();
        for (CommunicationDTO communicationDTO : this.additionalPhoneList)
        {
            additionalEmailList.add(communicationDTO.getValue().toLowerCase());
        }
        return additionalEmailList;
    }
}
