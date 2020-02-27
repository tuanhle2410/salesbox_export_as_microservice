package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hungnh on 15/07/2014.
 */
@Entity
@Table(name = "appointment")
public class Appointment extends BaseEntity
{
    // ------------------------------ FIELDS ------------------------------
    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "focus_activity_id")
    private Activity focusActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "focus_work_data_id")
    private WorkDataActivity focusWorkData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "external_key")
    private String externalKey;

    @Column(name = "external_key_temp_list")
    private String externalKeyTempList;

    @Column(name = "location")
    private String location;

    @Column(name = "longitude ")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    //unit for calculation: millisecond
    @Column(name = "travelling_time")
    private Double travellingTime;

    @Column(name = "note")
    private String note;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "is_finished")
    private Boolean finished = Boolean.FALSE;

    @Column(name = "ignored")
    private Boolean ignored = Boolean.FALSE;

    @Column(name = "ignored_after_appointment")
    private Boolean ignoredAfterAppointment = Boolean.FALSE;

    @Column(name = "number_call")
    private Integer numberCall = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_contact_id")
    private Contact firstContact;

    @Column(name = "is_showed_popup")
    private Boolean isShowedPopup = Boolean.FALSE;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Column(name = "later_counter")
    private Integer laterCounter = 0;

    @Column(name = "later_counter_updated_date")
    private Date laterCounterUpdatedDate;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "meeting_order")
    private Integer meetingOrder = 1;

    @Column(name = "google_event_id")
    private String googleEventId;

    @Column(name = "outlook_event_id")
    private String outlookEventId;

    @Column(name = "office365_event_id")
    private String office365EventId;

    @Column(name = "maestrano_id")
    private String maestranoId;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;


    public String getMaestranoId()
    {
        return maestranoId;
    }

    public void setMaestranoId(String maestranoId)
    {
        this.maestranoId = maestranoId;
    }

    public Integer getMeetingOrder()
    {
        return meetingOrder;
    }

    public void setMeetingOrder(Integer meetingOrder)
    {
        this.meetingOrder = meetingOrder;
    }

    public String getTimezone()
    {
        return timezone;
    }

    public void setTimezone(String timezone)
    {
        this.timezone = timezone;
    }

    public String getExternalKeyTempList()
    {
        return externalKeyTempList;
    }

    public void setExternalKeyTempList(String externalKeyTempList)
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

    public Boolean getIsShowedPopup()
    {
        return isShowedPopup;
    }

    public void setIsShowedPopup(Boolean isShowedPopup)
    {
        this.isShowedPopup = isShowedPopup;
    }

    public Double getHours()
    {
        return (endDate.getTime() - startDate.getTime()) / 1000.0 / 60 / 60;
    }

    public Double getTravellingInHours()
    {
        if (travellingTime != null && travellingTime >= 0) {
            return travellingTime / 1000.0 / 60 / 60;
        }
        return -1D;
//            return owner.getTravellingTimePerMeeting();
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public Activity getFocusActivity()
    {
        return focusActivity;
    }

    public void setFocusActivity(Activity focusActivity)
    {
        this.focusActivity = focusActivity;
    }

    public WorkDataActivity getFocusWorkData()
    {
        return focusWorkData;
    }

    public void setFocusWorkData(WorkDataActivity focusWorkData)
    {
        this.focusWorkData = focusWorkData;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
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

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public Boolean getFinished()
    {
        return finished;
    }

    public void setFinished(Boolean finished)
    {
        this.finished = finished;
    }

    public Contact getFirstContact()
    {
        return firstContact;
    }

    public void setFirstContact(Contact firstContact)
    {
        this.firstContact = firstContact;
    }

    public String getExternalKey()
    {
        return externalKey;
    }

    public void setExternalKey(String externalKey)
    {
        this.externalKey = externalKey;
    }

    public User getCreator()
    {
        return creator;
    }

    public void setCreator(User creator)
    {
        this.creator = creator;
    }

    public Boolean getIgnored()
    {
        return ignored;
    }

    public void setIgnored(Boolean ignored)
    {
        this.ignored = ignored;
    }

    public Boolean getIgnoredAfterAppointment() {
        return ignoredAfterAppointment;
    }

    public void setIgnoredAfterAppointment(Boolean ignoredAfterAppointment) {
        this.ignoredAfterAppointment = ignoredAfterAppointment;
    }

    public Integer getNumberCall()
    {
        return numberCall;
    }

    public void setNumberCall(Integer numberCall)
    {
        this.numberCall = numberCall;
    }

    public Double getTravellingTime()
    {
        return travellingTime;
    }

    public void setTravellingTime(Double travellingTime)
    {
        this.travellingTime = travellingTime;
    }

    public Date getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getLaterCounter()
    {
        return laterCounter;
    }

    public void setLaterCounter(Integer laterCounter)
    {
        this.laterCounter = laterCounter;
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

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }
}

