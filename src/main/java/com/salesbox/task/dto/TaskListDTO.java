package com.salesbox.task.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by quynhtq on 5/3/14.
 */
public class TaskListDTO
{
// ------------------------------ FIELDS ------------------------------

    private List<TaskDTO> taskDTOList = new ArrayList<TaskDTO>();
    private Date currentTime;
    private long numberTask;
    private long numberActiveTask;
    private Double companyAvgDistributionDays;
    private String sessionKey;

    public long getNumberTask()
    {
        return numberTask;
    }

    public void setNumberTask(long numberTask)
    {
        this.numberTask = numberTask;
    }

    public long getNumberActiveTask()
    {
        return numberActiveTask;
    }

    public void setNumberActiveTask(long numberActiveTask)
    {
        this.numberActiveTask = numberActiveTask;
    }

    public Date getCurrentTime()
    {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime)
    {
        this.currentTime = currentTime;
    }

    public List<TaskDTO> getTaskDTOList()
    {
        return taskDTOList;
    }

    public void setTaskDTOList(List<TaskDTO> taskDTOList)
    {
        this.taskDTOList = taskDTOList;
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
