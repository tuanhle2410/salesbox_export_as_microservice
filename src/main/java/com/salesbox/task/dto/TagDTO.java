package com.salesbox.task.dto;

import com.salesbox.entity.TaskTag;

import java.util.UUID;

/**
 * Created by hungnh on 8/5/15.
 */
public class TagDTO
{
    private UUID uuid;
    private String color;
    private String name;
    private String type;
    private String colorCode;

    public TagDTO()
    {
    }

    public TagDTO(TaskTag taskTag)
    {
        this.uuid = taskTag.getUuid();
        this.color = taskTag.getColor().toString();
        this.name = taskTag.getName();
        this.type = taskTag.getType().toString();
        this.colorCode = taskTag.getColorCode();
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getColorCode()
    {
        return colorCode;
    }

    public void setColorCode(String colorCode)
    {
        this.colorCode = colorCode;
    }
}
