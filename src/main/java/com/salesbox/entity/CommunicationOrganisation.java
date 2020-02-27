package com.salesbox.entity;

import com.salesbox.entity.enums.CommunicationType;

import javax.persistence.*;

/**
 * User: hunglv
 * Date: 9/4/14
 */
@Entity
@Table(name = "communication_organisation")
public class CommunicationOrganisation extends BaseEntity
{
    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private CommunicationType type;

    @Column(name = "main")
    private Boolean main = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    public CommunicationOrganisation()
    {
    }

    public CommunicationOrganisation(String value, CommunicationType type, Boolean main, Organisation organisation)
    {
        this.value = value;
        this.type = type;
        this.main = main;
        this.organisation = organisation;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public CommunicationType getType()
    {
        return type;
    }

    public void setType(CommunicationType type)
    {
        this.type = type;
    }

    public Boolean getMain()
    {
        return main;
    }

    public void setMain(Boolean main)
    {
        this.main = main;
    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }
}
