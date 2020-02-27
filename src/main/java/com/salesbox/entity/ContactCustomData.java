package com.salesbox.entity;

import com.salesbox.entity.enums.RecentActionType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/5/14
 * Time: 2:03 PM
 */
@Entity
@Table(name = "contact_custom_data")
public class ContactCustomData extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(name = "is_favorite")
    private Boolean favorite = Boolean.FALSE;

    @Column(name = "index")
    private Integer index;

    @Column(name = "recent_action_type")
    @Enumerated(EnumType.ORDINAL)
    private RecentActionType recentActionType;

    @Column(name = "last_viewed")
    private Date lastViewed;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
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

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public RecentActionType getRecentActionType()
    {
        return recentActionType;
    }

    public void setRecentActionType(RecentActionType recentActionType)
    {
        this.recentActionType = recentActionType;
    }
}
