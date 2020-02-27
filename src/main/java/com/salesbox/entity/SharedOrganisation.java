package com.salesbox.entity;

import com.salesbox.entity.enums.MediaType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/17/14
 */
@Entity
@Table(name = "shared_organisation")
public class SharedOrganisation extends BaseEntity
{
    @Column(name = "name")
    private String name;

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

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "media_type")
    private MediaType mediaType = MediaType.MANUAL;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "industry_id")
    private WorkDataOrganisation industry;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id")
    private WorkDataOrganisation size;

    @Column(name = "web")
    private String web;

    @Column(name = "external_key")
    private String externalKey;

    @Column(name = "creator_id")
    @Type(type = "pg-uuid")
    private UUID creatorId;

    @Column(name = "is_public")
    private Boolean isPublic = Boolean.TRUE;

    @Column(name = "avatar")
    private String avatar;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
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

    public WorkDataOrganisation getSize()
    {
        return size;
    }

    public void setSize(WorkDataOrganisation size)
    {
        this.size = size;
    }

    public String getWeb()
    {
        return web;
    }

    public void setWeb(String web)
    {
        this.web = web;
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

    public UUID getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId)
    {
        this.creatorId = creatorId;
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
        if (state != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + state;
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
}
