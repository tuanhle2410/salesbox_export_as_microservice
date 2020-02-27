package com.salesbox.task.dto;

import com.salesbox.advancesearch.dto.SearchFieldDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskFilterDTO
{
    private Date startDate;
    private Date endDate;
    private String roleFilterType;
    private String roleFilterValue;
    private boolean isShowHistory;
    private boolean isDeleted;
    private String orderBy;
    private String selectedMark;
    private boolean isRequiredOwner;
    private boolean isDelegateTask;
    private String ftsTerms;
    private String customFilter;
    private boolean isFilterAll  = false;
    private List<SearchFieldDTO[]> searchFieldDTOList = new ArrayList<>();
//    private List<CustomFieldSearchDTO> customFieldSearchDTOList = new ArrayList<>();

    public void setShowHistory(boolean showHistory)
    {
        isShowHistory = showHistory;
    }

    public void setDeleted(boolean deleted)
    {
        isDeleted = deleted;
    }

    public void setRequiredOwner(boolean requiredOwner)
    {
        isRequiredOwner = requiredOwner;
    }

    public void setDelegateTask(boolean delegateTask)
    {
        isDelegateTask = delegateTask;
    }

    public List<SearchFieldDTO[]> getSearchFieldDTOList()
    {
        return searchFieldDTOList;
    }

    public void setSearchFieldDTOList(List<SearchFieldDTO[]> searchFieldDTOList)
    {
        this.searchFieldDTOList = searchFieldDTOList;
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

    public String getRoleFilterType()
    {
        return roleFilterType;
    }

    public void setRoleFilterType(String roleFilterType)
    {
        this.roleFilterType = roleFilterType;
    }

    public String getRoleFilterValue()
    {
        return roleFilterValue;
    }

    public void setRoleFilterValue(String roleFilterValue)
    {
        this.roleFilterValue = roleFilterValue;
    }

    public boolean isShowHistory()
    {
        return isShowHistory;
    }

    public void setIsShowHistory(boolean isShowHistory)
    {
        this.isShowHistory = isShowHistory;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public String getSelectedMark()
    {
        return selectedMark;
    }

    public void setSelectedMark(String selectedMark)
    {
        this.selectedMark = selectedMark;
    }

    public boolean isRequiredOwner()
    {
        return isRequiredOwner;
    }

    public void setIsRequiredOwner(boolean isRequiredOwner)
    {
        this.isRequiredOwner = isRequiredOwner;
    }

    public boolean isDelegateTask()
    {
        return isDelegateTask;
    }

    public void setIsDelegateTask(boolean isDelegateTask)
    {
        this.isDelegateTask = isDelegateTask;
    }

    public String getFtsTerms()
    {
        return ftsTerms;
    }

    public void setFtsTerms(String ftsTerms)
    {
        this.ftsTerms = ftsTerms;
    }

    public boolean isDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public String getCustomFilter()
    {
        return customFilter;
    }

    public void setCustomFilter(String customFilter)
    {
        this.customFilter = customFilter;
    }
//    public List<CustomFieldSearchDTO> getCustomFieldSearchDTOList()
//    {
//        return customFieldSearchDTOList;
//    }
//
//    public void setCustomFieldSearchDTOList(List<CustomFieldSearchDTO> customFieldSearchDTOList)
//    {
//        this.customFieldSearchDTOList = customFieldSearchDTOList;
//    }


    public boolean isFilterAll()
    {
        return isFilterAll;
    }

    public void setIsFilterAll(boolean isFilterAll)
    {
        this.isFilterAll = isFilterAll;
    }
}
