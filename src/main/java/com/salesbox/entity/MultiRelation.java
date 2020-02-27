package com.salesbox.entity;

import com.salesbox.entity.enums.ObjectType;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/26/14
 * Time: 10:20 AM
 */
@Entity
@Table(name = "multi_relation")
public class MultiRelation extends BaseEntity
{
    // ------------------------------ FIELDS ------------------------------
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "object_type")
    @Enumerated(EnumType.ORDINAL)
    private ObjectType objectType;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;
// --------------------- GETTER / SETTER METHODS ---------------------


    public MultiRelation()
    {
    }


    public MultiRelation(Enterprise enterprise, ObjectType objectType, String name)
    {
        this.enterprise = enterprise;
        this.objectType = objectType;
        this.name = name;
    }

    public ObjectType getObjectType()
    {
        return objectType;
    }

    public void setObjectType(ObjectType objectType)
    {
        this.objectType = objectType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
}
