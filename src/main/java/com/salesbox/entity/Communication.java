package com.salesbox.entity;

import com.salesbox.entity.enums.CommunicationType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * User: luult
 * Date: 8/22/14
 */
@Entity
@Table(name = "communication")
public class Communication extends BaseEntity
{
    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private CommunicationType type;

    @Column(name = "main")
    private Boolean main = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(name = "is_private")
    private Boolean isPrivate = Boolean.FALSE;

    @Column(name = "creator_id")
    @Type(type = "pg-uuid")
    private UUID creatorId;

    public Communication()
    {
    }

    public Communication(String value, CommunicationType type, Boolean main, Contact contact)
    {
        this.value = value;
        this.type = type;
        this.main = main;
        this.contact = contact;
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

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public Boolean getIsPrivate()
    {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate)
    {
        this.isPrivate = isPrivate;
    }

    public UUID getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId)
    {
        this.creatorId = creatorId;
    }
}
