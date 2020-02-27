package com.salesbox.entity;

import com.salesbox.entity.enums.ObjectType;
import com.salesbox.entity.enums.SubObjectType;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 7/28/14
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "read")
    private Boolean read = Boolean.FALSE;

    @Column(name = "object_type")
    @Enumerated(EnumType.ORDINAL)
    private ObjectType objectType;

    @Column(name = "object_id")
    private String objectId;

    @Column(name = "sub_object_type")
    @Enumerated(EnumType.ORDINAL)
    private SubObjectType subObjectType;

    @Column(name = "sub_object_id")
    private String subObjectId;

    @Column(name = "content")
    private String content;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Boolean getRead()
    {
        return read;
    }

    public void setRead(Boolean read)
    {
        this.read = read;
    }

    public ObjectType getObjectType()
    {
        return objectType;
    }

    public void setObjectType(ObjectType objectType)
    {
        this.objectType = objectType;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public void setObjectId(String objectId)
    {
        this.objectId = objectId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public SubObjectType getSubObjectType()
    {
        return subObjectType;
    }

    public void setSubObjectType(SubObjectType subObjectType)
    {
        this.subObjectType = subObjectType;
    }

    public String getSubObjectId()
    {
        return subObjectId;
    }

    public void setSubObjectId(String subObjectId)
    {
        this.subObjectId = subObjectId;
    }
}
