package com.salesbox.lead.dto;

import com.salesbox.advancesearch.dto.SearchFieldDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by quynhtq on 6/11/15.
 */
public class LeadFilterDTO
{
    private Date startDate;
    private Date endDate;
    private Date deadlineDate;
    private String roleFilterType;
    private String roleFilterValue;
    private boolean isRequiredOwner;
    private boolean isShowHistory;
    private String orderBy;
    private boolean isDelegateLead;
    private String ftsTerms;
    private String customFilter;
    private String statusSearchValue;
    private boolean isFilterAll  = false;
    private List<SearchFieldDTO[]> searchFieldDTOList = new ArrayList<>();
//    private List<CustomFieldSearchDTO> customFieldSearchDTOList = new ArrayList<>();

    public void setRequiredOwner(boolean requiredOwner)
    {
        isRequiredOwner = requiredOwner;
    }

    public void setShowHistory(boolean showHistory)
    {
        isShowHistory = showHistory;
    }

    public void setDelegateLead(boolean delegateLead)
    {
        isDelegateLead = delegateLead;
    }

    public List<SearchFieldDTO[]> getSearchFieldDTOList()
    {
        return searchFieldDTOList;
    }

    public void setSearchFieldDTOList(List<SearchFieldDTO[]> searchFieldDTOList)
    {
        this.searchFieldDTOList = searchFieldDTOList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public boolean isRequiredOwner() {
        return isRequiredOwner;
    }

    public void setIsRequiredOwner(boolean isRequiredOwner) {
        this.isRequiredOwner = isRequiredOwner;
    }

    public boolean isShowHistory() {
        return isShowHistory;
    }

    public void setIsShowHistory(boolean isShowHistory) {
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

    public boolean isDelegateLead()
    {
        return isDelegateLead;
    }

    public void setIsDelegateLead(boolean isDelegateLead)
    {
        this.isDelegateLead = isDelegateLead;
    }

    public String getFtsTerms()
    {
        return ftsTerms;
    }

    public void setFtsTerms(String ftsTerms)
    {
        this.ftsTerms = ftsTerms;
    }

    public String getCustomFilter()
    {
        return customFilter;
    }

    public void setCustomFilter(String customFilter)
    {
        this.customFilter = customFilter;
    }

    public String getStatusSearchValue() {
        return statusSearchValue;
    }

    public void setStatusSearchValue(String statusSearchValue) {
        this.statusSearchValue = statusSearchValue;
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

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public boolean isFilterAll()
    {
        return isFilterAll;
    }

    public void setIsFilterAll(boolean isFilterAll)
    {
        this.isFilterAll = isFilterAll;
    }
}
