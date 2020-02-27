package com.salesbox.dto;

import com.salesbox.entity.Notification;

import java.util.Date;
import java.util.UUID;

/**
 * Created by hunglv on 7/29/14.
 */
public class WebNotiDTO
{
    private UUID uuid;
    private UUID userId;
    private String content;
    private Boolean read;
    private String objectType;
    private String objectId;
    private String subObjectType;
    private String subObjectId;
    private Date notificationDate;
    private String keyCode;

    public WebNotiDTO()
    {
    }

    public WebNotiDTO(Notification notification)
    {
        this.uuid = notification.getUuid();
        this.userId = notification.getUser().getUuid();
        this.content = notification.getContent();
        this.read = notification.getRead();
        this.objectId = notification.getObjectId();
        this.subObjectId = notification.getSubObjectId();
        this.notificationDate = notification.getCreatedDate();
        if (notification.getObjectType() != null)
        {
            this.objectType = notification.getObjectType().toString();
        }
        if(notification.getSubObjectType() != null)
        {
            this.subObjectType = notification.getSubObjectType().toString();
        }
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Boolean getRead()
    {
        return read;
    }

    public void setRead(Boolean read)
    {
        this.read = read;
    }

    public String getObjectType()
    {
        return objectType;
    }

    public void setObjectType(String objectType)
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

    public Date getNotificationDate()
    {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate)
    {
        this.notificationDate = notificationDate;
    }

    public String getSubObjectType()
    {
        return subObjectType;
    }

    public void setSubObjectType(String subObjectType)
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

    public String getKeyCode()
    {
        return keyCode;
    }

    public void setKeyCode(String keyCode)
    {
        this.keyCode = keyCode;
    }
}
