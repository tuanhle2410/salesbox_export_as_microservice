package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hungnh on 22/07/2014.
 */
@Entity
@Table(name = "call_list_shared_contact")
public class CallListSharedContact extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_list_id")
    private CallList callList;

    @Column(name = "contact_id")
    @Type(type = "pg-uuid")
    private UUID contactId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "call_back_time")
    private Date callBackTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_call_id")
    private CommunicationHistory latestCall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_dial_id")
    private CommunicationHistory latestDial;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "deleted")
    private Boolean deleted = false;

    public UUID getContactId()
    {
        return contactId;
    }

    public void setContactId(UUID contactId)
    {
        this.contactId = contactId;
    }

    public CallList getCallList()
    {
        return callList;
    }

    public void setCallList(CallList callList)
    {
        this.callList = callList;
    }

    public Task getTask()
    {
        return task;
    }

    public void setTask(Task task)
    {
        this.task = task;
    }

    public Date getCallBackTime()
    {
        return callBackTime;
    }

    public void setCallBackTime(Date callBackTime)
    {
        this.callBackTime = callBackTime;
    }

    public CommunicationHistory getLatestCall()
    {
        return latestCall;
    }

    public void setLatestCall(CommunicationHistory latestCall)
    {
        this.latestCall = latestCall;
    }

    public CommunicationHistory getLatestDial()
    {
        return latestDial;
    }

    public void setLatestDial(CommunicationHistory latestDial)
    {
        this.latestDial = latestDial;
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
}
