package com.salesbox.entity.callList;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Created by admin on 5/27/2017.
 */
@Entity
@Table(name = "v_call_list")
public class CallListView extends BaseEntity
{
    @Column(name = "name")
    private String name;

    @Column(name = "deadline_date")
    private Date deadlineDate;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "owner_id")
    @Type(type = "pg-uuid")
    private UUID ownerId;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "number_contact")
    private Long numberContact = 0L;

    @Column(name = "number_account")
    private Long numberAccount = 0L;

    @Column(name = "number_dial")
    private Long numberDial = 0L;

    @Column(name = "number_call")
    private Long numberCall = 0L;

    @Column(name = "number_meeting")
    private Long numberMeeting = 0L;

    @Column(name = "number_prospect")
    private Long numberProspect = 0L;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @Column(name = "start_communication_date")
    private Date startCommunicationDate;

    @Column(name = "number_called_contact")
    private Long numberCalledContact = 0L;

    @Column(name = "is_genius")
    private Boolean isGenius;

    @Column(name = "unit_id")
    @Type(type = "pg-uuid")
    private UUID unitId;

    public Long getNumberCalledContact() {
        return numberCalledContact;
    }

    public void setNumberCalledContact(Long numberCalledContact) {
        this.numberCalledContact = numberCalledContact;
    }

    public Date getStartCommunicationDate() {
        return startCommunicationDate;
    }

    public void setStartCommunicationDate(Date startCommunicationDate) {
        this.startCommunicationDate = startCommunicationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getNumberContact() {
        return numberContact;
    }

    public void setNumberContact(Long numberContact) {
        this.numberContact = numberContact;
    }

    public Long getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(Long numberAccount) {
        this.numberAccount = numberAccount;
    }

    public Long getNumberDial() {
        return numberDial;
    }

    public void setNumberDial(Long numberDial) {
        this.numberDial = numberDial;
    }

    public Long getNumberCall() {
        return numberCall;
    }

    public void setNumberCall(Long numberCall) {
        this.numberCall = numberCall;
    }

    public Long getNumberMeeting() {
        return numberMeeting;
    }

    public void setNumberMeeting(Long numberMeeting) {
        this.numberMeeting = numberMeeting;
    }

    public Long getNumberProspect() {
        return numberProspect;
    }

    public void setNumberProspect(Long numberProspect) {
        this.numberProspect = numberProspect;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Boolean getGenius() {
        return isGenius;
    }

    public void setGenius(Boolean genius) {
        isGenius = genius;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }
}
