package com.salesbox.dto;

import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 3/31/2017.
 */
public class TaskPaginateBean extends PaginationBeanBase
{

    private UUID taskTagId;
    private UUID leadId;
    private List<UUID> taskIds;
    private Boolean isFinished;

    public Boolean getFinished()
    {
        return isFinished;
    }

    public void setFinished(Boolean finished)
    {
        isFinished = finished;
    }

    public List<UUID> getTaskIds()
    {
        return taskIds;
    }

    public void setTaskIds(List<UUID> taskIds)
    {
        this.taskIds = taskIds;
    }

    public UUID getTaskTagId()
    {
        return taskTagId;
    }

    public void setTaskTagId(UUID taskTagId)
    {
        this.taskTagId = taskTagId;
    }

    public UUID getLeadId()
    {
        return leadId;
    }

    public void setLeadId(UUID leadId)
    {
        this.leadId = leadId;
    }
}
