package com.salesbox.entity;

import com.salesbox.entity.enums.CommunicationHistoryType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 7/25/14
 * Time: 10:39 AM
 */
@Entity
@Table(name = "communication_history")
public class CommunicationHistory extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_contact_id")
    private SharedContact sharedContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "duration")
    private Long duration = 0l;

    @Column(name= "type")
    @Enumerated(EnumType.ORDINAL)
    private CommunicationHistoryType type;

    @Column(name = "detail")
    private String detail;

    @Column(name = "receive_date")
    private Date receiveDate;

    @Column(name = "receive_url_date")
    private Date receiveUrlDate;

    @Column(name = "receive_attachment_date")
    private Date receiveAttachmentDate;

    @Column(name = "tracking_code")
    private String trackingCode;

    @Column(name = "tracking_url_code")
    private String trackingUrlCode;

    @Column(name = "tracking_attachment_code")
    private String trackingAttachmentCode;

    @Column(name = "outlook_mail_id")
    private String outLookMailId;

    @Column(name = "number_open_mail")
    private Integer numberOpenMail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;

    public CommunicationHistory()
    {
    }

    public CommunicationHistory(SharedContact sharedContact, User user, Date startDate, CommunicationHistoryType type, String detail)
    {
        this.sharedContact = sharedContact;
        this.user = user;
        this.startDate = startDate;
        this.type = type;
        this.detail = detail;
    }

    public CommunicationHistory(SharedContact sharedContact, User user, Date startDate, CommunicationHistoryType type, String detail, String outLookMailId)
    {
        this.sharedContact = sharedContact;
        this.user = user;
        this.startDate = startDate;
        this.type = type;
        this.detail = detail;
        this.outLookMailId = outLookMailId;
    }
    public CommunicationHistory(Organisation organisation, User user, Date startDate, CommunicationHistoryType type, String detail, String outLookMailId)
    {
        this.organisation = organisation;
        this.user = user;
        this.startDate = startDate;
        this.type = type;
        this.detail = detail;
        this.outLookMailId = outLookMailId;
    }

    public Integer getNumberOpenMail()
    {
        return numberOpenMail;
    }

    public void setNumberOpenMail(Integer numberOpenMail)
    {
        this.numberOpenMail = numberOpenMail;
    }

    public Double getMinutes()
    {
        return  duration/1000.0/60;
    }

    public Double getHours()
    {
        return duration/1000.0/60/60;
    }

    public SharedContact getSharedContact()
    {
        return sharedContact;
    }

    public void setSharedContact(SharedContact sharedContact)
    {
        this.sharedContact = sharedContact;
    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Long getDuration()
    {
        return duration;
    }

    public void setDuration(Long duration)
    {
        this.duration = duration;
    }

    public CommunicationHistoryType getType()
    {
        return type;
    }

    public void setType(CommunicationHistoryType type)
    {
        this.type = type;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public Date getReceiveDate()
    {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate)
    {
        this.receiveDate = receiveDate;
    }

    public String getTrackingCode()
    {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode)
    {
        this.trackingCode = trackingCode;
    }

    public Date getReceiveUrlDate()
    {
        return receiveUrlDate;
    }

    public void setReceiveUrlDate(Date receiveUrlDate)
    {
        this.receiveUrlDate = receiveUrlDate;
    }

    public Date getReceiveAttachmentDate()
    {
        return receiveAttachmentDate;
    }

    public void setReceiveAttachmentDate(Date receiveAttachmentDate)
    {
        this.receiveAttachmentDate = receiveAttachmentDate;
    }

    public String getTrackingUrlCode()
    {
        return trackingUrlCode;
    }

    public void setTrackingUrlCode(String trackingUrlCode)
    {
        this.trackingUrlCode = trackingUrlCode;
    }

    public String getTrackingAttachmentCode()
    {
        return trackingAttachmentCode;
    }

    public void setTrackingAttachmentCode(String trackingAttachmentCode)
    {
        this.trackingAttachmentCode = trackingAttachmentCode;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public String getOutLookMailId()
    {
        return outLookMailId;
    }

    public void setOutLookMailId(String outLookMailId)
    {
        this.outLookMailId = outLookMailId;
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
