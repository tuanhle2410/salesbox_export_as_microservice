package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * User: luult
 * Date: 5/21/14
 */
@Entity
@Table(name = "prospect_user")
public class ProspectUser extends BaseEntity
{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "percent")
    private Double percent;

    @Column(name = "index")
    private Integer index;

    @Column(name = "number_done_meeting")
    private Integer numberDoneMeeting = 0;


    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;

    public ProspectUser()
    {
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Double getPercent()
    {
        return percent;
    }

    public void setPercent(Double percent)
    {
        this.percent = percent;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public Integer getNumberDoneMeeting()
    {
        return numberDoneMeeting;
    }

    public void setNumberDoneMeeting(Integer numberDoneMeeting)
    {
        this.numberDoneMeeting = numberDoneMeeting;
    }

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }
}
