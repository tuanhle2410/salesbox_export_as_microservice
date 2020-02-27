package com.salesbox.entity;

import com.salesbox.entity.enums.ObjectType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * User: luult
 * Date: 5/21/14
 */
@Entity
@Table(name = "contact_user_deleted")
public class ContactUserDeleted extends BaseEntity
{
    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "object_id")
    private UUID objectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "source_type")
    @Enumerated(EnumType.ORDINAL)
    private ObjectType sourceType = ObjectType.CONTACT;

    public ObjectType getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(ObjectType sourceType)
    {
        this.sourceType = sourceType;
    }

    public UUID getObjectId()
    {
        return objectId;
    }

    public void setObjectId(UUID objectId)
    {
        this.objectId = objectId;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public ContactUserDeleted(UUID objectId, User user, ObjectType sourceType)
    {
        this.objectId = objectId;
        this.user = user;
        this.sourceType = sourceType;
    }

    public ContactUserDeleted()
    {
    }
}
