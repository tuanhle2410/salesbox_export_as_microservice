package com.salesbox.entity.callList;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by admin on 5/18/2017.
 */
@Entity
@Table(name = "call_list_account_account")
public class CallListAccountAccount extends BaseEntity
{

    @Column(name = "call_list_account_id")
    @Type(type = "pg-uuid")
    private UUID callListAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_list_account_id", insertable = false, updatable = false)
    private CallListAccount callListAccount;

    @Column(name = "account_id")
    @Type(type = "pg-uuid")
    private UUID accountId;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "number_dial")
    private Long numberDial = 0L;

    @Column(name = "number_call")
    private Long numberCall = 0L;

    @Column(name = "number_meeting")
    private Long numberMeeting = 0L;

    @Column(name = "number_prospect")
    private Long numberProspect = 0L;

    @Column(name = "call_back_task_id")
    @Type(type = "pg-uuid")
    private UUID callBackTaskId;

    public UUID getCallBackTaskId()
    {
        return callBackTaskId;
    }

    public void setCallBackTaskId(UUID callBackTaskId)
    {
        this.callBackTaskId = callBackTaskId;
    }

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
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

    public UUID getCallListAccountId()
    {
        return callListAccountId;
    }

    public void setCallListAccountId(UUID callListAccountId)
    {
        this.callListAccountId = callListAccountId;
    }

    public UUID getAccountId()
    {
        return accountId;
    }

    public void setAccountId(UUID accountId)
    {
        this.accountId = accountId;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public CallListAccount getCallListAccount() {
        return callListAccount;
    }

    public void setCallListAccount(CallListAccount callListAccount) {
        this.callListAccount = callListAccount;
    }
}
