package com.salesbox.entity;

import com.salesbox.entity.enums.SyncObjectType;
import com.salesbox.entity.enums.SyncStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/3/14
 * Time: 9:34 AM
 */
@Entity
@Table(name = "sync_enterprise_session")
public class SyncEnterpriseSession extends BaseEntity
{
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private SyncObjectType type;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private SyncStatus status = SyncStatus.IN_PROGRESS;

    @Column(name = "last_sync_date")
    private Date lastSyncDate = new Date(0);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "user_id")
    private String userId;

    public SyncObjectType getType()
    {
        return type;
    }

    public void setType(SyncObjectType type)
    {
        this.type = type;
    }

    public SyncStatus getStatus()
    {
        return status;
    }

    public void setStatus(SyncStatus status)
    {
        this.status = status;
    }

    public Date getLastSyncDate()
    {
        return lastSyncDate;
    }

    public void setLastSyncDate(Date lastSyncDate)
    {
        this.lastSyncDate = lastSyncDate;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }
}
