package com.salesbox.entity.callList;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Created by admin on 5/21/2017.
 */

@Entity
@Table(name = "v_organitaion_on_call_list")
public class OrganisationOnCallList extends BaseEntity
{

    @Column(name = "organisation_id")
    @Type(type = "pg-uuid")
    private UUID organisationId;

    @Column(name = "call_list_account_id")
    @Type(type = "pg-uuid")
    private UUID callListAccountId;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "first_letter")
    private String firstLetter;

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

    @Column(name = "owner_id")
    @Type(type = "pg-uuid")
    private UUID ownerId;

    @Column(name = "unit_id")
    @Type(type = "pg-uuid")
    private UUID unitId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;


    public String getFirstLetter()
    {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter)
    {
        this.firstLetter = firstLetter;
    }

    public UUID getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId)
    {
        this.ownerId = ownerId;
    }

    public UUID getUnitId()
    {
        return unitId;
    }

    public void setUnitId(UUID unitId)
    {
        this.unitId = unitId;
    }

    public UUID getCallBackTaskId()
    {
        return callBackTaskId;
    }

    public void setCallBackTaskId(UUID callBackTaskId)
    {
        this.callBackTaskId = callBackTaskId;
    }

    public Date getCallBackTime()
    {
        return callBackTime;
    }

    public void setCallBackTime(Date callBackTime)
    {
        this.callBackTime = callBackTime;
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

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public UUID getCallListAccountId()
    {
        return callListAccountId;
    }

    public void setCallListAccountId(UUID callListAccountId)
    {
        this.callListAccountId = callListAccountId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public Long getNumberDial()
    {
        return numberDial;
    }

    public void setNumberDial(Long numberDial)
    {
        this.numberDial = numberDial;
    }

    public Long getNumberCall()
    {
        return numberCall;
    }

    public void setNumberCall(Long numberCall)
    {
        this.numberCall = numberCall;
    }

    public Long getNumberMeeting()
    {
        return numberMeeting;
    }

    public void setNumberMeeting(Long numberMeeting)
    {
        this.numberMeeting = numberMeeting;
    }

    public Long getNumberProspect()
    {
        return numberProspect;
    }

    public void setNumberProspect(Long numberProspect)
    {
        this.numberProspect = numberProspect;
    }

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public UUID getOrganisationId()
    {
        return organisationId;
    }

    public void setOrganisationId(UUID organisationId)
    {
        this.organisationId = organisationId;
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
