package com.salesbox.entity.callList;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Created by admin on 5/30/2017.
 */

@Entity
@Table(name = "call_list_account_account_task")
public class CallListAccountTask extends BaseEntity
{

    @Column(name = "call_list_account_account_id")
    @Type(type = "pg-uuid")
    private UUID callListAccountAccountId;

    @Column(name = "task_id")
    @Type(type = "pg-uuid")
    private UUID taskId;

    @Column(name = "call_back_time")
    private Date callBackTime;

    public UUID getCallListAccountAccountId()
    {
        return callListAccountAccountId;
    }

    public void setCallListAccountAccountId(UUID callListAccountId)
    {
        this.callListAccountAccountId = callListAccountId;
    }

    public UUID getTaskId()
    {
        return taskId;
    }

    public void setTaskId(UUID taskId)
    {
        this.taskId = taskId;
    }

    public Date getCallBackTime()
    {
        return callBackTime;
    }

    public void setCallBackTime(Date callBackTime)
    {
        this.callBackTime = callBackTime;
    }
}
