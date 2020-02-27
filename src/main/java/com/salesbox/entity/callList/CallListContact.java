package com.salesbox.entity.callList;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by admin on 5/26/2017.
 */
@Entity
@Table(name = "call_list_shared_contact")
public class CallListContact extends BaseEntity
{
    @Column(name = "call_list_id")
    @Type(type = "pg-uuid")
    private UUID callListId;

    @Column(name = "contact_id")
    @Type(type = "pg-uuid")
    private UUID contactId;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "deleted")
    private Boolean deleted = false;

    public UUID getCallListId()
    {
        return callListId;
    }

    public void setCallListId(UUID callListContactId)
    {
        this.callListId = callListContactId;
    }

    public UUID getContactId()
    {
        return contactId;
    }

    public void setContactId(UUID contactId)
    {
        this.contactId = contactId;
    }

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
}
