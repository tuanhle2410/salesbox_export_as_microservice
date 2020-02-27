package com.salesbox.entity.view.taskRelation;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by admin on 3/26/2017.
 */

@Entity
@Table(name = "v_lead_on_organisation_or_contact")
public class LeadOnContactOrOrganisation
{


    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "contact_id")
    private UUID contactId;

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
    @Column(name = "contact_name")
    private String contactName;

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
    @Column(name = "is_finished")
    private Boolean finished;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "lead_owner_id")
    private UUID leadOwnerId;

    @Basic
    @Column(name = "lead_deleted")
    private Boolean deleted;

    @Basic
    @Column(name = "lead_temp_lead")
    private Boolean tempLead;


    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public UUID getLeadId() {
        return leadId;
    }

    public void setLeadId(UUID leadId) {
        this.leadId = leadId;
    }

    public String getLeadNote() {
        return leadNote;
    }

    public void setLeadNote(String leadNote) {
        this.leadNote = leadNote;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public UUID getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(UUID organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public Boolean getFinished()
    {
        return finished;
    }

    public void setFinished(Boolean finished)
    {
        this.finished = finished;
    }

    public UUID getLeadOwnerId()
    {
        return leadOwnerId;
    }

    public void setLeadOwnerId(UUID leadOwnerId)
    {
        this.leadOwnerId = leadOwnerId;
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
