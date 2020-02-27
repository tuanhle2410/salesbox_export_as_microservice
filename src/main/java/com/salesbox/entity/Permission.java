package com.salesbox.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/23/14
 * Time: 6:25 PM
 */
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
