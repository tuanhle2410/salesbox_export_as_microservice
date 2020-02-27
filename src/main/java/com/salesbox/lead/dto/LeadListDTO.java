package com.salesbox.lead.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hunglv on 7/15/14.
 */
public class LeadListDTO
{
    List<LeadDTO> leadDTOList = new ArrayList<LeadDTO>();
    private Date currentTime;
    private Double companyAvgDistributionDays;
    private String sessionKey;

    public Date getCurrentTime()
    {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime)
    {
        this.currentTime = currentTime;
    }

    public List<LeadDTO> getLeadDTOList()
    {
        return leadDTOList;
    }

    public void setLeadDTOList(List<LeadDTO> leadDTOList)
    {
        this.leadDTOList = leadDTOList;
    }

    public Double getCompanyAvgDistributionDays()
    {
        return companyAvgDistributionDays;
    }

    public void setCompanyAvgDistributionDays(Double companyAvgDistributionDays)
    {
        this.companyAvgDistributionDays = companyAvgDistributionDays;
    }

    public String getSessionKey()
    {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
    }
}


