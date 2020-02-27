package com.salesbox.entity;

import com.salesbox.entity.enums.CommunicationType;

import javax.persistence.*;

/**
 * User: hunglv
 * Date: 9/4/14
 */
@Entity
@Table(name = "shared_communication_organisation")
public class SharedCommunicationOrganisation extends BaseEntity
{
    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private CommunicationType type;

    @Column(name = "main")
    private Boolean main = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_organisation_id")
    private SharedOrganisation sharedOrganisation;

    public SharedCommunicationOrganisation()
    {
    }

    public SharedCommunicationOrganisation(String value, CommunicationType type, Boolean main, SharedOrganisation sharedOrganisation)
    {
        this.value = value;
        this.type = type;
        this.main = main;
        this.sharedOrganisation = sharedOrganisation;
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

    public SharedOrganisation getSharedOrganisation()
    {
        return sharedOrganisation;
    }

    public void setSharedOrganisation(SharedOrganisation sharedOrganisation)
    {
        this.sharedOrganisation = sharedOrganisation;
    }
}
