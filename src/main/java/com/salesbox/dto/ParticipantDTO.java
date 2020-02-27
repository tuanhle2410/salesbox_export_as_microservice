package com.salesbox.dto;

import com.salesbox.entity.SharedContact;
import com.salesbox.entity.User;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 6/19/14
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class ParticipantDTO implements Serializable
{
    private UUID uuid;
    private String avatar;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String discProfile;
    private Double sharedPercent;

    public ParticipantDTO()
    {

    }

    public ParticipantDTO(User user)
    {
        SharedContact sharedContact = user.getSharedContact();

        this.setUuid(user.getUuid());
        this.setAvatar(user.getAvatar());
        this.setFirstName(sharedContact.getFirstName());
        this.setLastName(sharedContact.getLastName());
        this.setEmail(sharedContact.getEmail());
        this.setPhone(sharedContact.getPhone());
        this.setDiscProfile(sharedContact.getDiscProfile().toString());
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

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
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

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(String discProfile)
    {
        this.discProfile = discProfile;
    }

    public Double getSharedPercent()
    {
        return sharedPercent;
    }

    public void setSharedPercent(Double sharedPercent)
    {
        this.sharedPercent = sharedPercent;
    }
}
