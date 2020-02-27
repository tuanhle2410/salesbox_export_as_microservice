package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by GEM on 3/9/2018.
 */
@Entity
@Table(name = "organisation_visma_customer")
public class OrganisationVismaCustomer extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @Column(name = "visma_id")
    private String vismaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public String getVismaId()
    {
        return vismaId;
    }

    public void setVismaId(String vismaId)
    {
        this.vismaId = vismaId;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public OrganisationVismaCustomer(Organisation organisation, String vismaId, Enterprise enterprise)
    {
        this.organisation = organisation;
        this.vismaId = vismaId;
        this.enterprise = enterprise;
    }

    public OrganisationVismaCustomer()
    {
    }
}
