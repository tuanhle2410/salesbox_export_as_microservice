package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hungnh on 22/07/2014.
 */
@Entity
@Table(name = "call_list")
public class CallList extends BaseEntity
{
    // ------------------------------ FIELDS ------------------------------
    @Column(name = "name")
    private String name;

    @Column(name = "units")
    private String units;

    @Column(name = "users")
    private String users;

    @Column(name = "industries")
    private String industries;

    @Column(name = "organisations")
    private String organisations;

    @Column(name = "cities")
    private String cities;

    @Column(name = "countries")
    private String countries;

    @Column(name = "titles")
    private String titles;

    @Column(name = "deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "is_genius")
    private Boolean isGenius = Boolean.FALSE;

    @Column(name = "deadline_date")
    private Date deadlineDate;

    @Column(name = "period_in_month")
    private Long periodInMonth;

    @Column(name = "is_finished")
    private Boolean isFinished = Boolean.FALSE;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "owner_id")
    @Type(type = "pg-uuid")
    private UUID ownerId;

    @Column(name = "creator_id")
    @Type(type = "pg-uuid")
    private UUID creatorId;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUnits()
    {
        return units;
    }

    public void setUnits(String units)
    {
        this.units = units;
    }

    public String getUsers()
    {
        return users;
    }

    public void setUsers(String users)
    {
        this.users = users;
    }

    public String getIndustries()
    {
        return industries;
    }

    public void setIndustries(String industries)
    {
        this.industries = industries;
    }

    public String getOrganisations()
    {
        return organisations;
    }

    public void setOrganisations(String organisations)
    {
        this.organisations = organisations;
    }

    public String getCities()
    {
        return cities;
    }

    public void setCities(String city)
    {
        this.cities = city;
    }

    public Boolean getIsGenius()
    {
        return isGenius;
    }

    public void setIsGenius(Boolean isGenius)
    {
        this.isGenius = isGenius;
    }

    public String getCountries()
    {
        return countries;
    }

    public void setCountries(String countries)
    {
        this.countries = countries;
    }

    public String getTitles()
    {
        return titles;
    }

    public void setTitles(String titles)
    {
        this.titles = titles;
    }

    public Boolean getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Date getDeadlineDate()
    {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate)
    {
        this.deadlineDate = deadlineDate;
    }

    public Long getPeriodInMonth()
    {
        return periodInMonth;
    }

    public void setPeriodInMonth(Long periodInMonth)
    {
        this.periodInMonth = periodInMonth;
    }

    public Boolean getIsFinished()
    {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished)
    {
        this.isFinished = isFinished;
    }

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public UUID getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId)
    {
        this.ownerId = ownerId;
    }

    public UUID getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId)
    {
        this.creatorId = creatorId;
    }
}
