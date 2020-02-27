package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "address")
public class Address extends BaseEntity
{
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

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "organisation_id")
    private UUID organisationId;

    public Address()
    {
    }

    public Address(String street, String zipCode, String city, String state, String country, UUID organisationId)
    {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.organisationId = organisationId;
    }

    public void setValues(String street, String zipCode, String city, String state, String country, UUID organisationId)
    {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.organisationId = organisationId;
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

    public UUID getOrganisationId()
    {
        return organisationId;
    }

    public void setOrganisationId(UUID organisationId)
    {
        this.organisationId = organisationId;
    }
}
