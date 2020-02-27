package com.salesbox.prospect.dto;

import com.salesbox.entity.Contact;

import java.io.Serializable;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/21/14
 */
public class SponsorDTO implements Serializable
{
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String relationship;
    private String discProfile;
    private UUID sharedContactId;
    private String avatar;

    public SponsorDTO()
    {
    }

    public SponsorDTO(UUID uuid)
    {
        this.uuid = uuid;
    }
    public SponsorDTO(UUID uuid, String firstName, String lastName)
    {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public SponsorDTO(Contact contact)
    {
        uuid = contact.getUuid();
//        sharedContactId = contact.getSharedContact().getUuid();
        firstName = contact.getFirstName();
        lastName = contact.getLastName();
        phone = contact.getPhone();
        email = contact.getEmail();
        discProfile = contact.getDiscProfile().toString();
        avatar = contact.getAvatar();

        if (contact.getRelationship() != null)
        {
            relationship = contact.getRelationship().toString();
        }

    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getRelationship()
    {
        return relationship;
    }

    public void setRelationship(String relationship)
    {
        this.relationship = relationship;
    }

    public String getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(String discProfile)
    {
        this.discProfile = discProfile;
    }

    public UUID getSharedContactId()
    {
        return sharedContactId;
    }

    public void setSharedContactId(UUID sharedContactId)
    {
        this.sharedContactId = sharedContactId;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
}
