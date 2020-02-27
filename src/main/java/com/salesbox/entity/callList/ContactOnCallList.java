package com.salesbox.entity.callList;

import com.salesbox.entity.BaseEntity;
import com.salesbox.entity.enums.RelationshipType;
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
@Table(name = "v_contact_on_call_list")
public class ContactOnCallList extends BaseEntity
{
    @Column(name = "call_list_id")
    @Type(type = "pg-uuid")
    private UUID callListId;

    @Column(name = "contact_id")
    @Type(type = "pg-uuid")
    private UUID contactId;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "first_letter")
    private String firstLetter;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "relationship")
    private RelationshipType relationship;

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

    @Column(name = "latest_call_date")
    private Date latestCallDate;

    @Column(name = "latest_dial_date")
    private Date latestDialDate;

    @Column(name = "call_back_task_id")
    @Type(type = "pg-uuid")
    private UUID callBackTaskId;

    @Column(name = "call_back_time")
    private Date callBackTime;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public RelationshipType getRelationship()
    {
        return relationship;
    }

    public void setRelationship(RelationshipType relationship)
    {
        this.relationship = relationship;
    }

    public UUID getCallBackTaskId() {
        return callBackTaskId;
    }

    public void setCallBackTaskId(UUID callBackTaskId) {
        this.callBackTaskId = callBackTaskId;
    }

    public Date getCallBackTime() {
        return callBackTime;
    }

    public void setCallBackTime(Date callBackTime) {
        this.callBackTime = callBackTime;
    }

    public Date getLatestCallDate() {
        return latestCallDate;
    }

    public void setLatestCallDate(Date latestCallDate) {
        this.latestCallDate = latestCallDate;
    }

    public Date getLatestDialDate() {
        return latestDialDate;
    }

    public void setLatestDialDate(Date latestDialDate) {
        this.latestDialDate = latestDialDate;
    }

    public UUID getCallListId() {
        return callListId;
    }

    public void setCallListId(UUID callListId) {
        this.callListId = callListId;
    }

    public UUID getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
