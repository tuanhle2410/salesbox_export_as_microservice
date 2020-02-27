package com.salesbox.dto;

import com.salesbox.annotation.OrikaMapper;
import com.salesbox.entity.WorkDataActivity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/26/14
 * Time: 4:28 PM
 */
@OrikaMapper(mapClass = WorkDataActivity.class)
public class WorkDataActivityDTO implements Serializable
{
    private UUID uuid;
    private String type;
    private String name;
    private String description;
    private String discProfile;
    private String keyCode;

    public void populateInfoFromWorkDataActivity(WorkDataActivity workDataActivity)
    {
        this.uuid = workDataActivity.getUuid();
        this.type = workDataActivity.getType().toString();
        this.name = workDataActivity.getName();
        this.description = workDataActivity.getDescription();
        this.discProfile = workDataActivity.getDiscProfile() != null ? workDataActivity.getDiscProfile().toString() : null;
        this.keyCode = workDataActivity.getKeyCode();
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(String discProfile)
    {
        this.discProfile = discProfile;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }
}
