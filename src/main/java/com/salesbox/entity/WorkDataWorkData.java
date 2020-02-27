package com.salesbox.entity;

import com.salesbox.entity.enums.WorkDataType;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/27/14
 * Time: 9:44 AM
 */
@Entity
@Table(name = "workdata_workdata")
public class WorkDataWorkData extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private WorkDataType type;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "changeable")
    private boolean changeable = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    public WorkDataWorkData() {
    }

    public WorkDataWorkData(String name, String value, Enterprise enterprise) {
        this.name = name;
        this.value = value;
        this.enterprise = enterprise;
        this.type  = WorkDataType.TARGET;
        this.changeable = true;
    }
// --------------------- GETTER / SETTER METHODS ---------------------

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

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public WorkDataType getType()
    {
        return type;
    }

    public void setType(WorkDataType workDataType)
    {
        this.type = workDataType;
    }

    public boolean isChangeable()
    {
        return changeable;
    }

    public void setChangeable(boolean changeable)
    {
        this.changeable = changeable;
    }
}
