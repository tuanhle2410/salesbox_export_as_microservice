package com.salesbox.entity;

import com.salesbox.constant.Constant;
import com.salesbox.entity.enums.CommunicationType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/17/14
 */
@Entity
@Table(name = "shared_communication")
public class SharedCommunication extends BaseEntity
{
    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private CommunicationType type;

    @Column(name = "main")
    private Boolean main = Boolean.FALSE;

    @Column(name = "is_private")
    private Boolean isPrivate = Boolean.FALSE;

    @Column(name = "creator_id")
    @Type(type = "pg-uuid")
    private UUID creatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_contact_id")
    private SharedContact sharedContact;

    public SharedCommunication(String value, CommunicationType type, Boolean main, SharedContact sharedContact)
    {
        this.value = value;
        this.type = type;
        this.main = main;
        this.sharedContact = sharedContact;
    }

    public SharedCommunication()
    {
    }

    @PrePersist
    @PreUpdate
    public void onUpdate() {
        if ((Constant.contactAdditionalEmailTypes.contains(this.type)
                || Constant.organisationAdditionalEmailTypes.contains(this.type))
                && !this.value.equals(this.value.toLowerCase())) {
            this.value = this.value.toLowerCase();
        }
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

    public SharedContact getSharedContact()
    {
        return sharedContact;
    }

    public void setSharedContact(SharedContact sharedContact)
    {
        this.sharedContact = sharedContact;
    }

    public Boolean getMain()
    {
        return main;
    }

    public void setMain(Boolean main)
    {
        this.main = main;
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
