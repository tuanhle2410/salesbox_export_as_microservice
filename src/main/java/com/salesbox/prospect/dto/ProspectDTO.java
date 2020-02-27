package com.salesbox.prospect.dto;

import com.salesbox.dto.LineOfBusinessDTO;
import com.salesbox.dto.SalesMethodDTO;
import com.salesbox.dto.UserDTO;
import com.salesbox.entity.ProspectBase;
import com.salesbox.organisation.dto.OrganisationDTO;
import com.salesbox.annotation.OrikaMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@OrikaMapper(mapClass = ProspectBase.class, excludes = {"participantList", "sponsorList", "lineOfBusiness", "owner", "salesMethod", "organisation"})
public class ProspectDTO implements Serializable
{
    private UUID uuid;
    private Date updatedDate;
    private Date createdDate;
    private Date wonLostDate;
    private Long numberContact;
    private Long numberParticipant;
    private Long numberTask;
    private Long numberActiveTask;
    private Long totalTask;
    private Long numberMeeting;
    private Long numberActiveMeeting;
    private Double numberMeetingLeft;
    private Integer numberDoneMeeting;
    private Double doneWorkEffort;
    private Long totalMeeting;
    private Double grossValue;
    private Double netValue;
    private Double profit;
    private Boolean won;
    private String description;
    private Date contractDate;
    private Double neededWorkEffort;
    private OrganisationDTO organisation;
    private SalesMethodDTO salesMethod;
    private LineOfBusinessDTO lineOfBusiness;
    private List<LineOfBusinessDTO> lineOfBusinessList = new ArrayList<>();
    private String unitName;
    private Boolean favorite;
    private Integer prospectProgress;
    private Double realProspectProgress;
    private Integer numberActivityLeft;
    private UUID currentStepId;
    private String firstNextStep;
    private String secondNextStep;
    private String descriptionFirstNextStep;
    private String descriptionSecondNextStep;
    private String discProfileFirstNextStep;
    private String discProfileSecondNextStep;

    private List<SponsorDTO> sponsorList = new ArrayList<SponsorDTO>();
    private List<UserDTO> participantList = new ArrayList<UserDTO>();
    private List<ProspectProgressDTO> prospectProgressDTOList = new ArrayList<>();

    private Integer numberPhoto;
    private String descriptionLatestUpload;
    private Date updatedDateLatestUpload;

    private Integer numberDocument = 0;
    private String authorNameLatestDocument;
    private String nameLatestDocument;

    private Integer numberNote;
    private String authorNameLatestNote;
    private String subjectLatestNote;

    private String nextTaskFocus;
    private Date nextTaskDateAndTime;
    private boolean havePrioritiesTask;

    private Integer numberOrderRow;

    private Boolean deleted = false;
    private UUID leadId;

    private UUID nextMeetingId;
    private String locationNextMeeting;
    private Date startDateNextMeeting;
    private String prioritiseColor;

    private Long daysInPipeline;
    private Double margin;
    //for calculate hours per quote and hours per contract
    private UUID userId;
    private UUID ownerId;
    private String ownerName;
    private String ownerAvatar;
    private String ownerDiscProfile;

    private String leadBoxerId;
    private Date lastSyncTime;
    private Boolean visitMore;
    private String serialNumber;
    private Integer manualProgress;

    private List<OrderRowDTO> orderRowDTOList = new ArrayList<>();
    private List<OrderRowCustomFieldDTO> orderRowCustomFieldDTOList = new ArrayList<>();

    public List<ProspectProgressDTO> getProspectProgressDTOList()
    {
        return prospectProgressDTOList;
    }

    public Boolean errorWithVisma = false;
    public void setProspectProgressDTOList(List<ProspectProgressDTO> prospectProgressDTOList)
    {
        this.prospectProgressDTOList = prospectProgressDTOList;
    }

    public List<OrderRowDTO> getOrderRowDTOList()
    {
        return orderRowDTOList;
    }

    public void setOrderRowDTOList(List<OrderRowDTO> orderRowDTOList)
    {
        this.orderRowDTOList = orderRowDTOList;
    }

    public List<OrderRowCustomFieldDTO> getOrderRowCustomFieldDTOList()
    {
        return orderRowCustomFieldDTOList;
    }

    public void setOrderRowCustomFieldDTOList(List<OrderRowCustomFieldDTO> orderRowCustomFieldDTOList)
    {
        this.orderRowCustomFieldDTOList = orderRowCustomFieldDTOList;
    }

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
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

    public Date getWonLostDate()
    {
        return wonLostDate;
    }

    public void setWonLostDate(Date wonLostDate)
    {
        this.wonLostDate = wonLostDate;
    }

    public Long getNumberContact()
    {
        return numberContact;
    }

    public void setNumberContact(Long numberContact)
    {
        this.numberContact = numberContact;
    }

    public Long getNumberParticipant()
    {
        return numberParticipant;
    }

    public void setNumberParticipant(Long numberParticipant)
    {
        this.numberParticipant = numberParticipant;
    }

    public Long getNumberTask()
    {
        return numberTask;
    }

    public void setNumberTask(Long numberTask)
    {
        this.numberTask = numberTask;
    }

    public Long getNumberMeeting()
    {
        return numberMeeting;
    }

    public void setNumberMeeting(Long numberMeeting)
    {
        this.numberMeeting = numberMeeting;
    }

    public Double getGrossValue()
    {
        return grossValue;
    }

    public Boolean getFavorite()
    {
        return favorite;
    }

    public void setFavorite(Boolean favorite)
    {
        this.favorite = favorite;
    }

    public void setGrossValue(Double grossValue)
    {
        this.grossValue = grossValue;
    }

    public Double getNetValue()
    {
        return netValue;
    }

    public void setNetValue(Double netValue)
    {
        this.netValue = netValue;
    }

    public Double getProfit()
    {
        return profit;
    }

    public void setProfit(Double profit)
    {
        this.profit = profit;
    }

    public Boolean getWon()
    {
        return won;
    }

    public void setWon(Boolean won)
    {
        this.won = won;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getContractDate()
    {
        return contractDate;
    }

    public void setContractDate(Date contractDate)
    {
        this.contractDate = contractDate;
    }

    public Double getNeededWorkEffort()
    {
        return neededWorkEffort;
    }

    public void setNeededWorkEffort(Double neededWorkEffort)
    {
        this.neededWorkEffort = neededWorkEffort;
    }

    public OrganisationDTO getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(OrganisationDTO organisation)
    {
        this.organisation = organisation;
    }

    public SalesMethodDTO getSalesMethod()
    {
        return salesMethod;
    }

    public void setSalesMethod(SalesMethodDTO salesMethod)
    {
        this.salesMethod = salesMethod;
    }

    public LineOfBusinessDTO getLineOfBusiness()
    {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(LineOfBusinessDTO lineOfBusiness)
    {
        this.lineOfBusiness = lineOfBusiness;
    }

    public List<LineOfBusinessDTO> getLineOfBusinessList()
    {
        return lineOfBusinessList;
    }

    public void setLineOfBusinessList(List<LineOfBusinessDTO> lineOfBusinessList)
    {
        this.lineOfBusinessList = lineOfBusinessList;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public Integer getProspectProgress()
    {
        return prospectProgress;
    }

    public void setProspectProgress(Integer prospectProgress)
    {
        this.prospectProgress = prospectProgress;
    }

    public Integer getNumberActivityLeft()
    {
        return numberActivityLeft;
    }

    public void setNumberActivityLeft(Integer numberActivityLeft)
    {
        this.numberActivityLeft = numberActivityLeft;
    }

    public UUID getCurrentStepId()
    {
        return currentStepId;
    }

    public void setCurrentStepId(UUID currentStepId)
    {
        this.currentStepId = currentStepId;
    }

    public String getFirstNextStep()
    {
        return firstNextStep;
    }

    public void setFirstNextStep(String firstNextStep)
    {
        this.firstNextStep = firstNextStep;
    }

    public String getSecondNextStep()
    {
        return secondNextStep;
    }

    public void setSecondNextStep(String secondNextStep)
    {
        this.secondNextStep = secondNextStep;
    }

    public String getDescriptionFirstNextStep()
    {
        return descriptionFirstNextStep;
    }

    public void setDescriptionFirstNextStep(String descriptionFirstNextStep)
    {
        this.descriptionFirstNextStep = descriptionFirstNextStep;
    }

    public String getDescriptionSecondNextStep()
    {
        return descriptionSecondNextStep;
    }

    public void setDescriptionSecondNextStep(String descriptionSecondNextStep)
    {
        this.descriptionSecondNextStep = descriptionSecondNextStep;
    }

    public List<SponsorDTO> getSponsorList()
    {
        return sponsorList;
    }

    public void setSponsorList(List<SponsorDTO> sponsorList)
    {
        this.sponsorList = sponsorList;
    }

    public List<UserDTO> getParticipantList()
    {
        return participantList;
    }

    public void setParticipantList(List<UserDTO> participantList)
    {
        this.participantList = participantList;
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

    public String getNameLatestDocument()
    {
        return nameLatestDocument;
    }

    public void setNameLatestDocument(String nameLatestDocument)
    {
        this.nameLatestDocument = nameLatestDocument;
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

    public Integer getNumberOrderRow()
    {
        return numberOrderRow;
    }

    public void setNumberOrderRow(Integer numberOrderRow)
    {
        this.numberOrderRow = numberOrderRow;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public String getDiscProfileFirstNextStep()
    {
        return discProfileFirstNextStep;
    }

    public void setDiscProfileFirstNextStep(String discProfileFirstNextStep)
    {
        this.discProfileFirstNextStep = discProfileFirstNextStep;
    }

    public String getDiscProfileSecondNextStep()
    {
        return discProfileSecondNextStep;
    }

    public void setDiscProfileSecondNextStep(String discProfileSecondNextStep)
    {
        this.discProfileSecondNextStep = discProfileSecondNextStep;
    }

    public Long getTotalTask()
    {
        return totalTask;
    }

    public void setTotalTask(Long totalTask)
    {
        this.totalTask = totalTask;
    }

    public Long getTotalMeeting()
    {
        return totalMeeting;
    }

    public void setTotalMeeting(Long totalMeeting)
    {
        this.totalMeeting = totalMeeting;
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

    public Double getNumberMeetingLeft()
    {
        return numberMeetingLeft;
    }

    public void setNumberMeetingLeft(Double numberMeetingLeft)
    {
        this.numberMeetingLeft = numberMeetingLeft;
    }

    public Integer getNumberDoneMeeting()
    {
        return numberDoneMeeting;
    }

    public Double getDoneWorkEffort()
    {
        return doneWorkEffort;
    }

    public void setDoneWorkEffort(Double doneWorkEffort)
    {
        this.doneWorkEffort = doneWorkEffort;
    }

    public void setNumberDoneMeeting(Integer numberDoneMeeting)
    {
        this.numberDoneMeeting = numberDoneMeeting;
    }

    public UUID getLeadId()
    {
        return leadId;
    }

    public void setLeadId(UUID leadId)
    {
        this.leadId = leadId;
    }

    public Double getRealProspectProgress()
    {
        return realProspectProgress;
    }

    public void setRealProspectProgress(Double realProspectProgress)
    {
        this.realProspectProgress = realProspectProgress;
    }

    public UUID getNextMeetingId()
    {
        return nextMeetingId;
    }

    public void setNextMeetingId(UUID nextMeetingId)
    {
        this.nextMeetingId = nextMeetingId;
    }

    public String getLocationNextMeeting()
    {
        return locationNextMeeting;
    }

    public void setLocationNextMeeting(String locationNextMeeting)
    {
        this.locationNextMeeting = locationNextMeeting;
    }

    public Date getStartDateNextMeeting()
    {
        return startDateNextMeeting;
    }

    public void setStartDateNextMeeting(Date startDateNextMeeting)
    {
        this.startDateNextMeeting = startDateNextMeeting;
    }

    public String getPrioritiseColor()
    {
        return prioritiseColor;
    }

    public void setPrioritiseColor(String prioritiseColor)
    {
        this.prioritiseColor = prioritiseColor;
    }

    public Long getDaysInPipeline()
    {
        return daysInPipeline;
    }

    public void setDaysInPipeline(Long daysInPipeline)
    {
        this.daysInPipeline = daysInPipeline;
    }

    public Double getMargin()
    {
        return margin;
    }

    public void setMargin(Double margin)
    {
        this.margin = margin;
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

    public String getLeadBoxerId()
    {
        return leadBoxerId;
    }

    public void setLeadBoxerId(String leadBoxerId)
    {
        this.leadBoxerId = leadBoxerId;
    }

    public Date getLastSyncTime()
    {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime)
    {
        this.lastSyncTime = lastSyncTime;
    }

    public Boolean getVisitMore()
    {
        return visitMore;
    }

    public void setVisitMore(Boolean visitMore)
    {
        this.visitMore = visitMore;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public boolean getHavePrioritiesTask()
    {
        return havePrioritiesTask;
    }

    public void setHavePrioritiesTask(boolean havePrioritiesTask)
    {
        this.havePrioritiesTask = havePrioritiesTask;
    }

    public Boolean getErrorWithVisma()
    {
        return errorWithVisma;
    }

    public void setErrorWithVisma(Boolean errorWithVisma)
    {
        this.errorWithVisma = errorWithVisma;
    }

    public Integer getManualProgress()
    {
        return manualProgress;
    }

    public void setManualProgress(Integer manualProgress)
    {
        this.manualProgress = manualProgress;
    }
}
