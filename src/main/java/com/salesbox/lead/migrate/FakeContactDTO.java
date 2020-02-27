package com.salesbox.lead.migrate;

/**
 * Created by HUNGLV2 on 12/18/2015.
 */
public class FakeContactDTO
{
    String firstName;
    String lastName;
    String email;
    String phone;

    public FakeContactDTO()
    {
    }

    public FakeContactDTO(String firstName, String lastName, String phone, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
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
}


