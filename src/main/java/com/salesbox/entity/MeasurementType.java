package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by quynhtq on 4/27/14.
 */

@Entity
@Table(name = "measurement_type")
public class MeasurementType extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "is_in_wizard")
    private Boolean isInWizard = Boolean.FALSE;

// --------------------------- CONSTRUCTORS ---------------------------

    public MeasurementType()
    {
    }

// --------------------- GETTER / SETTER METHODS ---------------------


    public Boolean getIsInWizard()
    {
        return isInWizard;
    }

    public void setIsInWizard(Boolean isInWizard)
    {
        this.isInWizard = isInWizard;
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

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
}
