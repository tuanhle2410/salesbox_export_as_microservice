package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * User: Quynh
 * Date: 01/05/2014
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable
{
// ------------------------------ FIELDS ------------------------------
    @Version
    @Column(name = "version", nullable = false)
    protected Long version;

    @Column(name = "updated_date", nullable = false, columnDefinition = "default now()")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedDate = Calendar.getInstance().getTime();

    @Column(name = "created_date", nullable = false, columnDefinition = "default now()")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate = Calendar.getInstance().getTime();

    @Id
    @Column(name = "uuid", unique = true, nullable = false, columnDefinition = "uuid")
    @Type(type = "pg-uuid")
    private UUID uuid = UUID.randomUUID();

//    @PrePersist
//    @PreUpdate
//    public void onUpdate()
//    {
//        this.setUpdatedDate(new Date());
//    }


// --------------------- GETTER / SETTER METHODS ---------------------


    public Long getVersion()
    {
        return version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }
}
