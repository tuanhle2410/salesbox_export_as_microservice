package com.salesbox.appointment.dto;

import com.salesbox.dto.ActivityDTO;
import com.salesbox.dto.WorkDataActivityDTO;
import com.salesbox.dto.DetailContactAccountRelationDTO;
import com.salesbox.dto.UserDTO;
import com.salesbox.entity.Appointment;
import com.salesbox.organisation.dto.OrganisationDTO;
import com.salesbox.prospect.dto.ProspectDTO;
import com.salesbox.annotation.OrikaMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hungnh on 15/07/2014.
 */
@OrikaMapper(mapClass = Appointment.class, excludes = {"contactList", "inviteeList", "organisation",
        "prospect", "externalKeyTempList", "focusActivity",
        "focusWorkData", "owner"})
public class AppointmentDTO
{
    private UUID uuid;
    private String title;
    private String location;
    private Double longitude;
    private Double latitude;
    private String note;
    private Double travellingTime;

    private UUID firstContactId;
    private String firstContactName;
    private String firstContactAddress;

    private UUID firstSharedContactId;

    private OrganisationDTO organisation;
    private UserDTO owner;
    private ActivityDTO focusActivity;
    private WorkDataActivityDTO focusWorkData;
    private ProspectDTO prospect;

    private List<ContactLiteDTO> contactList = new ArrayList<>();
    private InviteeListDTO inviteeList = new InviteeListDTO();

    private Boolean finished;
    private Boolean deleted;
    private Boolean ignored;

    private Date startDate;
    private Date endDate;
    private Date lastViewed;
    private Date popupDate;

    private String externalKey;
    private List<String> externalKeyTempList = new ArrayList<>();

    private Boolean isShowedPopup;
    private Date lastModifiedDate;

    private Integer laterCounter;
    private Integer meetingOrder;
    private Date laterCounterUpdatedDate;
    private UUID enterpriseId;
    private String googleEventId;
    private String outlookEventId;
    private String office365EventId;

    //Mapping to Lead
    private UUID leadId;

    public List<String> getExternalKeyTempList()
    {
        return externalKeyTempList;
    }

    private DetailContactAccountRelationDTO contactAccountRelation;

    public DetailContactAccountRelationDTO getContactAccountRelation()
    {
        return contactAccountRelation;
    }

    public void setContactAccountRelation(DetailContactAccountRelationDTO contactAccountRelation)
    {
        this.contactAccountRelation = contactAccountRelation;
    }

    public void setExternalKeyTempList(List<String> externalKeyTempList)
    {
        this.externalKeyTempList = externalKeyTempList;
    }

    public Date getLaterCounterUpdatedDate()
    {
        return laterCounterUpdatedDate;
    }

    public void setLaterCounterUpdatedDate(Date laterCounterUpdatedDate)
    {
        this.laterCounterUpdatedDate = laterCounterUpdatedDate;
    }

    public Integer getLaterCounter()
    {
        return laterCounter;
    }

    public void setLaterCounter(Integer laterCounter)
    {
        this.laterCounter = laterCounter;
    }

    public Boolean getIsShowedPopup()
    {
        return isShowedPopup;
    }

    public void setIsShowedPopup(Boolean isShowedPopup)
    {
        this.isShowedPopup = isShowedPopup;
    }

    public String getExternalKey()
    {
        return externalKey;
    }

    public void setExternalKey(String externalKey)
    {
        this.externalKey = externalKey;
    }

    public Date getLastViewed()
    {
        return lastViewed;
    }

    public void setLastViewed(Date lastViewed)
    {
        this.lastViewed = lastViewed;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public Double getTravellingTime()
    {
        return travellingTime;
    }

    public void setTravellingTime(Double travellingTime)
    {
        this.travellingTime = travellingTime;
    }

    public UUID getFirstContactId()
    {
        return firstContactId;
    }

    public void setFirstContactId(UUID firstContactId)
    {
        this.firstContactId = firstContactId;
    }

    public OrganisationDTO getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(OrganisationDTO organisation)
    {
        this.organisation = organisation;
    }

    public ActivityDTO getFocusActivity()
    {
        return focusActivity;
    }

    public void setFocusActivity(ActivityDTO focusActivity)
    {
        this.focusActivity = focusActivity;
    }

    public WorkDataActivityDTO getFocusWorkData()
    {
        return focusWorkData;
    }

    public void setFocusWorkData(WorkDataActivityDTO focusWorkData)
    {
        this.focusWorkData = focusWorkData;
    }

    public ProspectDTO getProspect()
    {
        return prospect;
    }

    public void setProspect(ProspectDTO prospect)
    {
        this.prospect = prospect;
    }

    public InviteeListDTO getInviteeList()
    {
        return inviteeList;
    }

    public void setInviteeList(InviteeListDTO inviteeList)
    {
        this.inviteeList = inviteeList;
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

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public UserDTO getOwner()
    {
        return owner;
    }

    public void setOwner(UserDTO owner)
    {
        this.owner = owner;
    }


    public List<ContactLiteDTO> getContactList()
    {
        return contactList;
    }

    public void setContactList(List<ContactLiteDTO> contactList)
    {
        this.contactList = contactList;
    }

    public Boolean getIgnored()
    {
        return ignored;
    }

    public void setIgnored(Boolean ignored)
    {
        this.ignored = ignored;
    }

    public Date getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public String getFirstContactName()
    {
        return firstContactName;
    }

    public void setFirstContactName(String firstContactName)
    {
        this.firstContactName = firstContactName;
    }

    public String getFirstContactAddress()
    {
        return firstContactAddress;
    }

    public void setFirstContactAddress(String firstContactAddress)
    {
        this.firstContactAddress = firstContactAddress;
    }

    public Integer getMeetingOrder() {
        return meetingOrder;
    }

    public void setMeetingOrder(Integer meetingOrder) {
        this.meetingOrder = meetingOrder;
    }

    public Date getPopupDate() {
        return popupDate;
    }

    public void setPopupDate(Date popupDate) {
        this.popupDate = popupDate;
    }

    public UUID getFirstSharedContactId()
    {
        return firstSharedContactId;
    }

    public void setFirstSharedContactId(UUID firstSharedContactId)
    {
        this.firstSharedContactId = firstSharedContactId;
    }

    public String getGoogleEventId()
    {
        return googleEventId;
    }

    public void setGoogleEventId(String googleEventId)
    {
        this.googleEventId = googleEventId;
    }

    public String getOutlookEventId()
    {
        return outlookEventId;
    }

    public void setOutlookEventId(String outlookEventId)
    {
        this.outlookEventId = outlookEventId;
    }

    public String getOffice365EventId()
    {
        return office365EventId;
    }

    public void setOffice365EventId(String office365EventId)
    {
        this.office365EventId = office365EventId;
    }

    public UUID getLeadId() {
        return leadId;
    }

    public void setLeadId(UUID leadId) {
        this.leadId = leadId;
    }
}
