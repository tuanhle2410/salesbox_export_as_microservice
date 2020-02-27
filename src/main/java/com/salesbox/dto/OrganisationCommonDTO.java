package com.salesbox.dto;

import java.io.Serializable;

/**
 * Created by admin on 3/30/2017.
 */
public class OrganisationCommonDTO implements Serializable
{
    private Double numberGoalsMeeting;
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getNumberGoalsMeeting()
    {
        return numberGoalsMeeting;
    }

    public void setNumberGoalsMeeting(Double numberGoalsMeeting)
    {
        this.numberGoalsMeeting = numberGoalsMeeting;
    }
}
