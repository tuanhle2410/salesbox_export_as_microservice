package com.salesbox.entity;

import com.salesbox.entity.enums.ObjectType;
import com.salesbox.entity.enums.RecentActionType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Quynhtq
 * Date: 20/03/16
 * Time: 2:03 PM
 */
@Entity
@Table(name = "recent_activity")
public class RecentActivity extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "object_id",nullable = false)
    @Type(type = "pg-uuid")
    private UUID objectId;

    @Column(name = "object_type")
    @Enumerated(EnumType.ORDINAL)
    private ObjectType objectType;

    @Column(name = "recent_action_type")
    @Enumerated(EnumType.ORDINAL)
    private RecentActionType recentActionType;

    public RecentActivity() {

    }

    public RecentActivity(User user, UUID objectId, ObjectType objectType, RecentActionType recentActionType) {
        this.user = user;
        this.objectId = objectId;
        this.objectType = objectType;
        this.recentActionType = recentActionType;
    }

    public User getUser() {
        return user;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public void setUser(User user)
    {
        this.user = user;
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
