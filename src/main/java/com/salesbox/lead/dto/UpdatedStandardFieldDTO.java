package com.salesbox.lead.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UpdatedStandardFieldDTO
{
    private UUID lineOfBusinessId;
    private List<UUID> productIdList = new ArrayList<>();
    private Date deadlineDate;
    private Double priority;
    private String status;
    private String note;

    public UUID getLineOfBusinessId()
    {
        return lineOfBusinessId;
    }

    public void setLineOfBusinessId(UUID lineOfBusinessId)
    {
        this.lineOfBusinessId = lineOfBusinessId;
    }

    public List<UUID> getProductIdList()
    {
        return productIdList;
    }

    public void setProductIdList(List<UUID> productIdList)
    {
        this.productIdList = productIdList;
    }

    public Date getDeadlineDate()
    {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate)
    {
        this.deadlineDate = deadlineDate;
    }

    public Double getPriority()
    {
        return priority;
    }

    public void setPriority(Double priority)
    {
        this.priority = priority;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }
}
