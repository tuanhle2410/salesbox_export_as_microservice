package com.salesbox.entity;

import com.salesbox.entity.enums.ContactUserSourceType;

import javax.persistence.*;

/**
 * User: luult
 * Date: 5/21/14
 */
@Entity
@Table(name = "contact_user")
public class ContactUser extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "source_type")
    @Enumerated(EnumType.ORDINAL)
    private ContactUserSourceType sourceType = ContactUserSourceType.MANUALLY;

    public ContactUserSourceType getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(ContactUserSourceType sourceType)
    {
        this.sourceType = sourceType;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (null == obj)
        {
            return false;
        }
        else if (!(obj instanceof ContactUser))
        {
            return false;
        }
        else if (obj == this)
        {
            return true;
        }
        else
        {
            return isEquals((ContactUser) obj);
        }
    }

    private boolean isEquals(ContactUser contactUser)
    {
        if (contactUser.getContact().getUuid().compareTo(contact.getUuid()) == 0
                && contactUser.getUser().getUuid().compareTo(user.getUuid()) == 0)
        {
            return true;
        }
        return false;
    }
}
