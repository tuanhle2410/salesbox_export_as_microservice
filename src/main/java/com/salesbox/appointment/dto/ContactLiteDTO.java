package com.salesbox.appointment.dto;

import com.salesbox.dto.WorkDataOrganisationDTO;
import com.salesbox.entity.Contact;
import com.salesbox.entity.SharedContact;
import com.salesbox.entity.enums.DiscProfileType;

import java.util.UUID;

/**
 * Created by hunglv on 7/19/14.
 */
public class ContactLiteDTO
{
    private UUID uuid;

    private String firstName;
    private String lastName;
    private String title;
    private String avatar;
    private String relationship;
    private String discProfile;
    private String phone;
    private String email;
    private Boolean accepted;

    private Long numberActiveProspect;
    private UUID sharedContactId;
    private UUID sharedOrganisationId;
    private UUID creatorId;
    private UUID organisationId;
    private String organisationName;


    private WorkDataOrganisationDTO type;
    private WorkDataOrganisationDTO industry;
    private WorkDataOrganisationDTO relation;

    public ContactLiteDTO()
    {

    }

    public ContactLiteDTO(UUID uuid)
    {
        this.uuid = uuid;
    }


    public ContactLiteDTO(UUID uuid, Boolean accepted)
    {
        this.uuid = uuid;
        this.accepted = accepted;
    }

    public ContactLiteDTO(Contact contact)
    {
        uuid = contact.getUuid();
        relationship = contact.getRelationship() == null ? null : contact.getRelationship().toString();
        numberActiveProspect = contact.getNumberActiveProspect();
        avatar = contact.getAvatar();

        if (contact.getOrganisation() != null)
        {
            organisationId = contact.getOrganisation().getUuid();
            organisationName = contact.getOrganisation().getName().trim();
            sharedOrganisationId = contact.getOrganisation().getSharedOrganisation().getUuid();
        }

        SharedContact sharedContact = contact.getSharedContact();
        sharedContactId = sharedContact.getUuid();
        creatorId = sharedContact.getCreatorId();
        firstName = contact.getFirstName();
        lastName = contact.getLastName();
        title = contact.getTitle();
        email = contact.getEmail();
        phone = contact.getPhone();
        DiscProfileType sharedContactDisProfile = sharedContact.getDiscProfile();
        DiscProfileType contactDisProfile = contact.getDiscProfile();
        if (sharedContactDisProfile == null || DiscProfileType.NONE.equals(sharedContactDisProfile))
        {
            discProfile = contactDisProfile.toString();
        }
        else
        {
            discProfile = sharedContactDisProfile.toString();
        }
    }

    public UUID getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId)
    {
        this.creatorId = creatorId;
    }

    public UUID getSharedContactId()
    {
        return sharedContactId;
    }

    public void setSharedContactId(UUID sharedContactId)
    {
        this.sharedContactId = sharedContactId;
    }

    public UUID getSharedOrganisationId()
    {
        return sharedOrganisationId;
    }

    public void setSharedOrganisationId(UUID sharedOrganisationId)
    {
        this.sharedOrganisationId = sharedOrganisationId;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public UUID getOrganisationId()
    {
        return organisationId;
    }

    public void setOrganisationId(UUID organisationId)
    {
        this.organisationId = organisationId;
    }

    public String getOrganisationName()
    {
        return organisationName;
    }

    public void setOrganisationName(String organisationName)
    {
        this.organisationName = organisationName;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Boolean getAccepted()
    {
        return accepted;
    }

    public void setAccepted(Boolean accepted)
    {
        this.accepted = accepted;
    }

    public String getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(String discProfile)
    {
        this.discProfile = discProfile;
    }

    public String getRelationship()
    {
        return relationship;
    }

    public void setRelationship(String relationship)
    {
        this.relationship = relationship;
    }

    public Long getNumberActiveProspect()
    {
        return numberActiveProspect;
    }

    public void setNumberActiveProspect(Long numberActiveProspect)
    {
        this.numberActiveProspect = numberActiveProspect;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public WorkDataOrganisationDTO getType()
    {
        return type;
    }

    public void setType(WorkDataOrganisationDTO type)
    {
        this.type = type;
    }

    public WorkDataOrganisationDTO getIndustry()
    {
        return industry;
    }

    public void setIndustry(WorkDataOrganisationDTO industry)
    {
        this.industry = industry;
    }

    public WorkDataOrganisationDTO getRelation()
    {
        return relation;
    }

    public void setRelation(WorkDataOrganisationDTO relation)
    {
        this.relation = relation;
    }
}
