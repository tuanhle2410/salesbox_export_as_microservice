package com.salesbox.entity;

import com.salesbox.entity.enums.DiscProfileType;
import com.salesbox.entity.enums.MediaType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/17/14
 */
@Entity
@Table(name = "shared_contact")
public class SharedContact extends BaseEntity
{
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name_lower_case")
    private String fullNameLowerCase;

    @Column(name = "disc_profile")
    @Enumerated(EnumType.ORDINAL)
    private DiscProfileType discProfile = DiscProfileType.NONE;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "street")
    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "country")
    private String country;

    @Column(name = "title")
    private String title;

    @Column(name = "media_type")
    private MediaType mediaType = MediaType.MANUAL;

    @Column(name = "external_key")
    private String externalKey;

    @Column(name = "external_url")
    private String externalUrl;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "creator_id")
    @Type(type = "pg-uuid")
    private UUID creatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private WorkDataOrganisation industry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_organisation_id")
    private SharedOrganisation sharedOrganisation;

    @Column(name = "hash_code")
    private String hashCode;
    
    @Column(name = "is_public")
    private Boolean isPublic = Boolean.TRUE;

    @Column(name = "avatar")
    private String avatar;

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

    public String getFullNameLowerCase() {
        return fullNameLowerCase;
    }

    public void setFullNameLowerCase(String fullNameLowerCase) {
        this.fullNameLowerCase = fullNameLowerCase;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public DiscProfileType getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(DiscProfileType discProfile)
    {
        this.discProfile = discProfile;
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

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public WorkDataOrganisation getIndustry()
    {
        return industry;
    }

    public void setIndustry(WorkDataOrganisation industry)
    {
        this.industry = industry;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public SharedOrganisation getSharedOrganisation()
    {
        return sharedOrganisation;
    }

    public void setSharedOrganisation(SharedOrganisation sharedOrganisation)
    {
        this.sharedOrganisation = sharedOrganisation;
    }

    public MediaType getMediaType()
    {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
    }

    public String getExternalKey()
    {
        return externalKey;
    }

    public void setExternalKey(String externalKey)
    {
        this.externalKey = externalKey;
    }

    public Integer getClientId()
    {
        return clientId;
    }

    public void setClientId(Integer clientId)
    {
        this.clientId = clientId;
    }

    public String getExternalUrl()
    {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl)
    {
        this.externalUrl = externalUrl;
    }

    public UUID getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getHashCode()
    {
        return hashCode;
    }

    public void setHashCode(String hashCode)
    {
        this.hashCode = hashCode;
    }

    public Boolean getPublic()
    {
        return isPublic;
    }

    public void setPublic(Boolean aPublic)
    {
        isPublic = aPublic;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    /*Convenience method to get full address combination from street, city, state, country*/
    public String getFullAddress()
    {
        String fullAddress = "";
        if (street != null)
        {
            fullAddress = street;
        }
        if (city != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + city;
        }
        if (region != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + region;
        }

        if (zipCode != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + zipCode;
        }

        if (country != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + country;
        }
        return fullAddress;
    }

    public String buildFullName() {

        return (this.firstName == null || this.firstName.isEmpty() ? "" : (this.firstName + " ")) +
                (this.lastName == null || this.lastName.isEmpty() ? "" : (this.lastName));
    }

}
