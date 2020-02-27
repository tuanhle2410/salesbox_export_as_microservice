package com.salesbox.entity.callList;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by admin on 5/23/2017.
 */

@Entity
@Table(name = "call_list_account_account_communication_history")
public class CallListCommunicationHistory extends BaseEntity
{

    @Column(name = "call_list_account_account_id")
    @Type(type = "pg-uuid")
    private UUID callListAccountAccountId;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "communication_history_id")
    @Type(type = "pg-uuid")
    private UUID communicationHistoryId;

    @Column(name = "deleted")
    private Boolean deleted = false;

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public UUID getCallListAccountAccountId()
    {
        return callListAccountAccountId;
    }

    public void setCallListAccountAccountId(UUID callListAccountAccountId)
    {
        this.callListAccountAccountId = callListAccountAccountId;
    }

    public UUID getCommunicationHistoryId()
    {
        return communicationHistoryId;
    }

    public void setCommunicationHistoryId(UUID communicationHistoryId)
    {
        this.communicationHistoryId = communicationHistoryId;
    }
}
