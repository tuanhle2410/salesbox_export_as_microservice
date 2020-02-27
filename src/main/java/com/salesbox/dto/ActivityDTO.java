package com.salesbox.dto;

import com.salesbox.entity.Activity;
import com.salesbox.annotation.OrikaMapper;

import java.util.UUID;

/**
 * User: luult
 * Date: 4/26/14
 */
@OrikaMapper(mapClass = Activity.class)
public class ActivityDTO
{
    private UUID uuid;
    private String name;
    private Integer progress;
    private Double meeting;
    private String discProfile;
    private String description;
    private String saleMethodId;
    private String type;
    private Boolean active;
    private Integer index;
    private Boolean deletable;

    public void populateInfoFromActivity(Activity activity)
    {
        this.uuid = activity.getUuid();
        this.name = activity.getName();
        this.progress = activity.getProgress();
        this.meeting = Double.valueOf(activity.getMeeting());
        this.discProfile = activity.getDiscProfile().toString();
        this.description = activity.getDescription();
        this.type = activity.getType().toString();
        this.active = activity.getActive();
        this.index = activity.getIndex();
        this.deletable = activity.getDeletable();
    }

    public Boolean getDeletable()
    {
        return deletable;
    }

    public void setDeletable(Boolean deletable)
    {
        this.deletable = deletable;
    }

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

    public Integer getProgress()
    {
        return progress;
    }

    public void setProgress(Integer progress)
    {
        this.progress = progress;
    }

    public String getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(String discProfile)
    {
        this.discProfile = discProfile;
    }

    public String getSaleMethodId()
    {
        return saleMethodId;
    }

    public void setSaleMethodId(String saleMethodId)
    {
        this.saleMethodId = saleMethodId;
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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Double getMeeting()
    {
        return meeting;
    }

    public void setMeeting(Double meeting)
    {
        this.meeting = meeting;
    }
}
