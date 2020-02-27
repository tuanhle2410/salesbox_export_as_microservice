package com.salesbox.entity;

import com.salesbox.entity.enums.RecentActionType;

import javax.persistence.*;
import java.util.Date;

/**
 * User: luult
 * Date: 6/24/14
 */
@Entity
@Table(name = "organisation_custom_data")
public class OrganisationCustomData extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @Column(name = "is_favorite")
    private Boolean favorite = Boolean.FALSE;

    @Column(name = "index")
    private Integer index;

    @Column(name = "recent_action_type")
    @Enumerated(EnumType.ORDINAL)
    private RecentActionType recentActionType;

    @Column(name = "last_viewed")
    private Date lastViewed;

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Boolean getFavorite()
    {
        return favorite;
    }

    public void setFavorite(Boolean favorite)
    {
        this.favorite = favorite;
    }

    public RecentActionType getRecentActionType()
    {
        return recentActionType;
    }

    public void setRecentActionType(RecentActionType recentActionType)
    {
        this.recentActionType = recentActionType;
    }

    public Date getLastViewed()
    {
        return lastViewed;
    }

    public void setLastViewed(Date lastViewed)
    {
        this.lastViewed = lastViewed;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }
}
