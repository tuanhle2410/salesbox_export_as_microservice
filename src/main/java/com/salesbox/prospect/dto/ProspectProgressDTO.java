package com.salesbox.prospect.dto;

import com.salesbox.entity.ProspectProgress;
import com.salesbox.annotation.OrikaMapper;

import java.io.Serializable;
import java.util.UUID;

/**
 * [File description]
 *
 * @author lent
 * @version 1.0
 * @since 5/1/14
 */
@OrikaMapper(mapClass = ProspectProgress.class)
public class ProspectProgressDTO implements Serializable
{
    private UUID uuid;
    private String name;
    private Boolean finished;
    private String discProfile;
    private String description;
    private Integer numberActiveMeeting;
    private UUID userId;
    private String type;
    private UUID activityId;
    private Integer progress;
    private Double totalAllProgress;


    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Boolean getFinished()
    {
        return finished;
    }

    public void setFinished(Boolean finished)
    {
        this.finished = finished;
    }

    public String getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(String discProfile)
    {
        this.discProfile = discProfile;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getNumberActiveMeeting()
    {
        return numberActiveMeeting;
    }

    public void setNumberActiveMeeting(Integer numberActiveMeeting)
    {
        this.numberActiveMeeting = numberActiveMeeting;
    }

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public UUID getActivityId()
    {
        return activityId;
    }

    public void setActivityId(UUID activityId)
    {
        this.activityId = activityId;
    }

    public Integer getProgress()
    {
        return progress;
    }

    public void setProgress(Integer progress)
    {
        this.progress = progress;
    }

    public Double getTotalAllProgress()
    {
        return totalAllProgress;
    }

    public void setTotalAllProgress(Double totalAllProgress)
    {
        this.totalAllProgress = totalAllProgress;
    }
}
