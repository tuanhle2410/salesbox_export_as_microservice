package com.salesbox.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by hunglv on 7/26/14.
 */

@Entity
@Table(name = "language")
public class Language extends BaseEntity
{
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

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
}
