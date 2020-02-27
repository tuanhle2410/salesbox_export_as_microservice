package com.salesbox.entity.callList;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Created by admin on 5/22/2017.
 */

@Entity
@Table(name = "v_call_list_account")
public class CallListAccountView extends BaseEntity
{

    @Column(name = "name")
    private String name;

    @Column(name = "deadline_date")
    private Date deadlineDate;

    @Column(name = "number_dial")
    private Long numberDial = 0L;

    @Column(name = "number_call")
    private Long numberCall = 0L;

    @Column(name = "number_meeting")
    private Long numberMeeting = 0L;

    @Column(name = "number_prospect")
    private Long numberProspect = 0L;

    @Column(name = "number_accounts")
    private Long numberAccounts = 0L;

    @Column(name = "number_called_account")
    private Long numberCalledAccount = 0L;

    @Column(name = "is_finished")
    private Boolean isFinished = Boolean.FALSE;

    @Column(name = "is_genius")
    private Boolean isGenius = Boolean.FALSE;

    @Column(name = "owner_id")
    @Type(type = "pg-uuid")
    private UUID ownerId;

    @Column(name = "unit_id")
    @Type(type = "pg-uuid")
    private UUID unitId;

    @Column(name = "shared_contact_id")
    @Type(type = "pg-uuid")
    private UUID sharedContactId;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "disc_profile")
    private String discProfile;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "start_communication_date")
    private Date startCommunicationDate;

    public Boolean getGenius()
    {
        return isGenius;
    }

    public void setGenius(Boolean genius)
    {
        isGenius = genius;
    }

    public Boolean getFinished()
    {
        return isFinished;
    }

    public void setFinished(Boolean finished)
    {
        isFinished = finished;
    }

    public UUID getUnitId()
    {
        return unitId;
    }

    public void setUnitId(UUID unitId)
    {
        this.unitId = unitId;
    }

    public Long getNumberCalledAccount()
    {
        return numberCalledAccount;
    }

    public void setNumberCalledAccount(Long numberCalledAccount)
    {
        this.numberCalledAccount = numberCalledAccount;
    }

    public Date getStartCommunicationDate()
    {
        return startCommunicationDate;
    }

    public void setStartCommunicationDate(Date startCommunicationDate)
    {
        this.startCommunicationDate = startCommunicationDate;
    }

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDeadlineDate()
    {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate)
    {
        this.deadlineDate = deadlineDate;
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

    public Long getNumberAccounts()
    {
        return numberAccounts;
    }

    public void setNumberAccounts(Long numberAccounts)
    {
        this.numberAccounts = numberAccounts;
    }

    public UUID getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId)
    {
        this.ownerId = ownerId;
    }

    public UUID getSharedContactId()
    {
        return sharedContactId;
    }

    public void setSharedContactId(UUID sharedContactId)
    {
        this.sharedContactId = sharedContactId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(String discProfile)
    {
        this.discProfile = discProfile;
    }
}
