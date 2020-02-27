package com.salesbox.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hunglv on 7/22/14.
 */

@Entity
@Table(name = "campaign")
public class Campaign extends BaseEntity
{
    @Column(name = "name")
    private String name;

    @Column(name = "units")
    private String units;

    @Column(name = "users")
    private String users;

    @Column(name = "number_lead")
    private Integer numberLead = 0;

    @Column(name = "number_prospect")
    private Integer numberProspect = 0;

    @Column(name = "number_won_prospect")
    private Integer numberWonProspect = 0;

    @Column(name = "won_value")
    private Double wonValue = 0d;

    @Column(name = "number_step")
    private Integer numberStep = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_of_business_id")
    private LineOfBusiness lineOfBusiness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "chanel")
    private String chanel;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getNumberLead()
    {
        return numberLead;
    }

    public void setNumberLead(Integer numberLead)
    {
        this.numberLead = numberLead;
    }

    public Integer getNumberProspect()
    {
        return numberProspect;
    }

    public void setNumberProspect(Integer numberProspect)
    {
        this.numberProspect = numberProspect;
    }

    public Double getWonValue()
    {
        return wonValue;
    }

    public void setWonValue(Double wonValue)
    {
        this.wonValue = wonValue;
    }

    public Integer getNumberStep()
    {
        return numberStep;
    }

    public void setNumberStep(Integer numberStep)
    {
        this.numberStep = numberStep;
    }

    public LineOfBusiness getLineOfBusiness()
    {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(LineOfBusiness lineOfBusiness)
    {
        this.lineOfBusiness = lineOfBusiness;
    }

    public Double getBudget()
    {
        return budget;
    }

    public void setBudget(Double budget)
    {
        this.budget = budget;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public String getChanel()
    {
        return chanel;
    }

    public void setChanel(String chanel)
    {
        this.chanel = chanel;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
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

    public Integer getNumberWonProspect()
    {
        return numberWonProspect;
    }

    public void setNumberWonProspect(Integer numberWonProspect)
    {
        this.numberWonProspect = numberWonProspect;
    }
}
