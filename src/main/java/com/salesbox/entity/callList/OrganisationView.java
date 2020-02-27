package com.salesbox.entity.callList;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by admin on 5/19/2017.
 */

@Entity
@Table(name = "v_organisation")
public class OrganisationView
{

    @Id
    @Column(name = "uuid")
    @Type(type = "pg-uuid")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "size_id")
    @Type(type = "pg-uuid")
    private UUID sizeId;

    @Column(name = "type_id")
    @Type(type = "pg-uuid")
    private UUID typeId;

    @Column(name = "industry_id")
    @Type(type = "pg-uuid")
    private UUID industryId;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "size_name")
    private String sizeName;

    @Column(name = "industries_name")
    private String industriesName;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "street")
    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "custom_field_value_aggregation")
    private String customValues;

    public String getCustomValues()
    {
        return customValues;
    }

    public void setCustomValues(String customValues)
    {
        this.customValues = customValues;
    }

    public UUID getTypeId()
    {
        return typeId;
    }

    public void setTypeId(UUID typeId)
    {
        this.typeId = typeId;
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

    public void setZipCode(String zip_code)
    {
        this.zipCode = zip_code;
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

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public UUID getSizeId()
    {
        return sizeId;
    }

    public void setSizeId(UUID sizeId)
    {
        this.sizeId = sizeId;
    }

    public UUID getIndustryId()
    {
        return industryId;
    }

    public void setIndustryId(UUID industryId)
    {
        this.industryId = industryId;
    }

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public String getSizeName()
    {
        return sizeName;
    }

    public void setSizeName(String sizeName)
    {
        this.sizeName = sizeName;
    }

    public String getIndustriesName()
    {
        return industriesName;
    }

    public void setIndustriesName(String industriesName)
    {
        this.industriesName = industriesName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
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
