package com.salesbox.entity.view.taskRelation;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by admin on 3/24/2017.
 */

@Entity
@Table(name = "v_lead_on_organisation")
public class LeadOnOrganisation
{

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "organisation_id")
    private UUID organisationId;

    @Id
    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "lead_id")
    private UUID leadId;

    @Basic
    @Column(name = "organisation_name")
    private String organisationName;

    @Basic
    @Column(name = "product_group_name")
    private String productGroupName;

    @Basic
    @Column(name = "lead_note")
    private String leadNote;

    @Basic
    @Column(name = "lead_deleted")
    private Boolean deleted;

    @Basic
    @Column(name = "lead_temp_lead")
    private Boolean tempLead;

    public UUID getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(UUID organisationId) {
        this.organisationId = organisationId;
    }

    public UUID getLeadId() {
        return leadId;
    }

    public void setLeadId(UUID leadId) {
        this.leadId = leadId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public String getLeadNote() {
        return leadNote;
    }

    public void setLeadNote(String leadNote) {
        this.leadNote = leadNote;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public Boolean getTempLead()
    {
        return tempLead;
    }

    public void setTempLead(Boolean tempLead)
    {
        this.tempLead = tempLead;
    }
}
