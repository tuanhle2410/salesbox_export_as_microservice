package com.salesbox.lead.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunglv on 7/15/14.
 */
public class LeadWebListDTO
{
    List<LeadDTO> leadDTOList = new ArrayList<LeadDTO>();
    int numberLead;

    public int getNumberLead()
    {
        return numberLead;
    }

    public void setNumberLead(int numberLead)
    {
        this.numberLead = numberLead;
    }

    public List<LeadDTO> getLeadDTOList()
    {
        return leadDTOList;
    }

    public void setLeadDTOList(List<LeadDTO> leadDTOList)
    {
        this.leadDTOList = leadDTOList;
    }
}


