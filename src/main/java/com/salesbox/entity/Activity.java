package com.salesbox.entity;


import com.salesbox.entity.enums.DiscProfileType;
import com.salesbox.entity.enums.SalesMethodActivityType;

import javax.persistence.*;

/**
 * User: luult
 * Date: 4/26/14
 */
@Entity
@Table(name = "activity")
public class Activity extends BaseEntity
{
    @Column(name = "name")
    private String name;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "meeting")
    private Integer meeting;

    @Column(name = "disc_profile")
    private DiscProfileType discProfile;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active = Boolean.TRUE;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private SalesMethodActivityType type = SalesMethodActivityType.NORMAL;

    @Column(name = "actual_meeting")
    private Double actualMeeting;

    @Column(name = "actual_disc_profile")
    private DiscProfileType actualDiscProfile;

    @Column(name = "index")
    private Integer index;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sales_method_id")
    private SalesMethod salesMethod;

    @Column(name = "deletable")
    private Boolean deletable = Boolean.TRUE;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;


    public Activity()
    {
    }

    public Activity(String name, Integer progress, Integer meeting, DiscProfileType discProfile, Integer index, SalesMethodActivityType type, String description)
    {
        this.name = name;
        this.progress = progress;
        this.meeting = meeting;
        this.discProfile = discProfile;
        this.index = index;
        this.type = type;
        this.description = description;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public SalesMethodActivityType getType()
    {
        return type;
    }

    public void setType(SalesMethodActivityType type)
    {
        this.type = type;
    }

    public Double getActualMeeting()
    {
        return actualMeeting;
    }

    public void setActualMeeting(Double actualMeeting)
    {
        this.actualMeeting = actualMeeting;
    }

    public DiscProfileType getActualDiscProfile()
    {
        return actualDiscProfile;
    }

    public void setActualDiscProfile(DiscProfileType actualDiscProfile)
    {
        this.actualDiscProfile = actualDiscProfile;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getProgress()
    {
        return progress;
    }

    public void setProgress(Integer progress)
    {
        this.progress = progress;
    }

    public Integer getMeeting()
    {
        return meeting;
    }

    public void setMeeting(Integer meeting)
    {
        this.meeting = meeting;
    }

    public DiscProfileType getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(DiscProfileType discProfile)
    {
        this.discProfile = discProfile;
    }

    public SalesMethod getSalesMethod()
    {
        return salesMethod;
    }

    public void setSalesMethod(SalesMethod salesMethod)
    {
        this.salesMethod = salesMethod;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public Boolean getDeletable()
    {
        return deletable;
    }

    public void setDeletable(Boolean deletable)
    {
        this.deletable = deletable;
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
