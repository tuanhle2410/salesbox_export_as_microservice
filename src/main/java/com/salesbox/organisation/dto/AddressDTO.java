package com.salesbox.organisation.dto;

import com.salesbox.entity.Address;

import java.io.Serializable;

public class AddressDTO implements Serializable
{
    private String street;
    private String zipCode;
    private String city;
    private String state;
    private String country;

    public AddressDTO()
    {
    }

    public AddressDTO(Address address)
    {
        this.street = address.getStreet();
        this.zipCode = address.getZipCode();
        this.city = address.getCity();
        this.state = address.getState();
        this.country = address.getCountry();
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
}
