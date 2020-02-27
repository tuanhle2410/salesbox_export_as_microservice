package com.salesbox.entity.task;

import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by admin on 4/1/2017.
 */

@Entity
@Table(name = "v_task")
public class TaskView extends TaskBase
{

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "focus_name")
    private String focusName;


    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "organisation_name")
    private String organisationName;

    @Column(name = "contact_name")
    private String contactName;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "task_tag_id_2")
    private UUID taskTagId2;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "lead_id")
    private UUID leadId;

    public UUID getLeadId()
    {
        return leadId;
    }

    public void setLeadId(UUID leadId)
    {
        this.leadId = leadId;
    }

    public UUID getTaskTagId2()
    {
        return taskTagId2;
    }

    public void setTaskTagId2(UUID taskTagId2)
    {
        this.taskTagId2 = taskTagId2;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getFocusName()
    {
        return focusName;
    }

    public void setFocusName(String focusName)
    {
        this.focusName = focusName;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getOrganisationName()
    {
        return organisationName;
    }

    public void setOrganisationName(String organisationName)
    {
        this.organisationName = organisationName;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
}
