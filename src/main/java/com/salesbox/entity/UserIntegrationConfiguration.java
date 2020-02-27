package com.salesbox.entity;

import com.salesbox.entity.enums.IntegrationType;
import com.salesbox.entity.enums.ObjectType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 7/22/14
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "user_integration_configuration")
public class UserIntegrationConfiguration extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "object_type")
    @Enumerated(EnumType.ORDINAL)
    private ObjectType objectType;

    @Column(name = "integration_type")
    private IntegrationType integrationType;

    @Column(name = "chanel_id")
    private String chanelId;

    @Column(name = "chanel_resource_id")
    private String chanelResourceId;

    @Column(name = "watched_resource_id")
    private String watchedResourceId;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "last_updated_date")
    private Date lastUpdatedDate = Calendar.getInstance().getTime();

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public ObjectType getObjectType()
    {
        return objectType;
    }

    public void setObjectType(ObjectType objectType)
    {
        this.objectType = objectType;
    }

    public IntegrationType getIntegrationType()
    {
        return integrationType;
    }

    public void setIntegrationType(IntegrationType integrationType)
    {
        this.integrationType = integrationType;
    }

    public String getChanelResourceId()
    {
        return chanelResourceId;
    }

    public void setChanelResourceId(String chanelResourceId)
    {
        this.chanelResourceId = chanelResourceId;
    }

    public String getChanelId()
    {
        return chanelId;
    }

    public void setChanelId(String chanelId)
    {
        this.chanelId = chanelId;
    }

    public String getWatchedResourceId()
    {
        return watchedResourceId;
    }

    public void setWatchedResourceId(String watchedResourceId)
    {
        this.watchedResourceId = watchedResourceId;
    }

    public Date getLastUpdatedDate()
    {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate)
    {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Date getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(Date expireDate)
    {
        this.expireDate = expireDate;
    }
}
