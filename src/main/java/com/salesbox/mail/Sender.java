package com.salesbox.mail;

/**
 * Created by hungnh on 9/2/15.
 */
public class Sender
{
    private String email;
    private String name;

    public Sender()
    {
    }

    public Sender(String email, String name)
    {
        this.email = email;
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
