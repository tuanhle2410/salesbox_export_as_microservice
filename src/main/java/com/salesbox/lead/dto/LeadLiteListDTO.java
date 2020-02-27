package com.salesbox.lead.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 6/9/2017.
 */
public class LeadLiteListDTO
{
    List<UUID> leadIdList = new ArrayList<>();

    public List<UUID> getLeadIdList()
    {
        return leadIdList;
    }

    public void setLeadIdList(List<UUID> leadIdList)
    {
        this.leadIdList = leadIdList;
    }
}
