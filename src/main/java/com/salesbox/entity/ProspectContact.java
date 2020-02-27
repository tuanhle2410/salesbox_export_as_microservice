package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * User: luult
 * Date: 5/22/14
 */
@Entity
@Table(name = "prospect_contact")
public class ProspectContact extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;

    @Column(name = "index")
    private Integer index;

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }
}
