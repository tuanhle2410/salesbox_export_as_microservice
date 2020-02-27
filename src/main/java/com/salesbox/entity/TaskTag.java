package com.salesbox.entity;

import com.salesbox.entity.enums.ColorType;
import com.salesbox.entity.enums.TagType;

import javax.persistence.*;

/**
 * Created by hungnh on 8/5/15.
 */
@Entity
@Table(name = "task_tag")
public class TaskTag extends BaseEntity
{
    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private ColorType color;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private TagType type = TagType.MANUAL;

    @Column(name = "color_code")
    private String colorCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    public ColorType getColor()
    {
        return color;
    }

    public void setColor(ColorType color)
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

    public TagType getType()
    {
        return type;
    }

    public void setType(TagType type)
    {
        this.type = type;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public User getCreator()
    {
        return creator;
    }

    public void setCreator(User creator)
    {
        this.creator = creator;
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
