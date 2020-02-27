package com.salesbox.entity;

import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Date: 09/08/2017
 */
@Entity
@Table(name = "prospect_historic")
public class ProspectHistoric extends ProspectBase
{

    @Column(name = "contact_first_name")
    private String contactFirstName;

    @Column(name = "contact_last_name")
    private String contactLastName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "organisation_name")
    private String organisationName;

    @Column(name = "won")
    private Boolean won;

    @Column(name = "won_lost_date")
    private Date wonLostDate;

    @Column(name = "updated_won_date")
    private Date updatedWonDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_set_won_id")
    private User userSetWon;

    @Column(name = "fortnox_id")
    private String fortNoxId;

    public ProspectHistoric()
    {
    }

    public ProspectHistoric(ProspectActive prospectActive)
    {
        BeanUtils.copyProperties(prospectActive, this);
    }

    public User getUserSetWon()
    {
        return userSetWon;
    }

    public void setUserSetWon(User userSetWon)
    {
        this.userSetWon = userSetWon;
    }

    public Boolean getWon()
    {
        return won;
    }

    public void setWon(Boolean won)
    {
        this.won = won;
    }

    public Date getWonLostDate()
    {
        return wonLostDate;
    }

    public void setWonLostDate(Date wonLostDate)
    {
        this.wonLostDate = wonLostDate;
    }

    public Date getUpdatedWonDate()
    {
        return updatedWonDate;
    }

    public void setUpdatedWonDate(Date updatedWonDate)
    {
        this.updatedWonDate = updatedWonDate;
    }

    public String getContactFirstName()
    {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName)
    {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName()
    {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName)
    {
        this.contactLastName = contactLastName;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public String getOrganisationName()
    {
        return organisationName;
    }

    public void setOrganisationName(String organisationName)
    {
        this.organisationName = organisationName;
    }

    public String getFortNoxId()
    {
        return fortNoxId;
    }

    public void setFortNoxId(String fortNoxId)
    {
        this.fortNoxId = fortNoxId;
    }
}
