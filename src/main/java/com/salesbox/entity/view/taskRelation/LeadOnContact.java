package com.salesbox.entity.view.taskRelation;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by admin on 3/22/2017.
 */

@Entity
@Table(name = "v_lead_on_contact")
public class LeadOnContact
{


    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "contact_id")
    private UUID contactId;

    @Id
    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "lead_id")
    private UUID leadId;

    @Basic
    @Column(name = "contact_name")
    private String contactName;

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
