package com.salesbox.entity;

import com.salesbox.entity.enums.RecentActionType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/5/14
 * Time: 1:58 PM
 */
@Entity
@Table(name = "prospect_custom_data")
public class ProspectCustomData extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_favorite")
    private Boolean favorite = Boolean.FALSE;

    @Column(name = "recent_action_type")
    @Enumerated(EnumType.ORDINAL)
    private RecentActionType recentActionType = RecentActionType.VIEW_DETAILS;

    @Column(name = "last_viewed")
    private Date lastViewed;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;

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

    public Date getLastViewed()
    {
        return lastViewed;
    }

    public void setLastViewed(Date lastViewed)
    {
        this.lastViewed = lastViewed;
    }

    public RecentActionType getRecentActionType()
    {
        return recentActionType;
    }

    public void setRecentActionType(RecentActionType recentActionType)
    {
        this.recentActionType = recentActionType;
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
