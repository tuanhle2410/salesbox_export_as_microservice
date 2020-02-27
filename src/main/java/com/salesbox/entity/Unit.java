package com.salesbox.entity;

import com.salesbox.entity.enums.UnitType;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/15/14
 * Time: 6:24 PM
 */
@Entity
@Table(name = "unit")
public class Unit extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    // default name for DEFAULT UNIT is blank
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    // type can be "UNIT" or "DEFAULT"
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private UnitType type;

    @Column(name = "is_active")
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "avatar")
    private String avatar;

// --------------------------- CONSTRUCTORS ---------------------------

    public Unit()
    {
        this.name = "No Unit";
        this.description = "";
        this.type = UnitType.DEFAULT;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public UnitType getType()
    {
        return type;
    }

    public void setType(UnitType type)
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

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
}
