package com.salesbox.appointment.dto;

import com.salesbox.advancesearch.dto.SearchFieldDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huynhlbq on 06/04/2015.
 */
public class AppointmentFilterDTO
{
    private Date startDate;
    private Date endDate;
    private String roleFilterType;
    private String roleFilterValue;
    private String orderBy;
    private String customFilter;
    private String searchText;
    private Boolean isShowHistory;
    private Boolean isFilterAll  = false;
    private String leadId;
    private List<SearchFieldDTO[]> searchFieldDTOList = new ArrayList<>();
//    private List<CustomFieldSearchDTO> customFieldSearchDTOList = new ArrayList<>();

    public Boolean getShowHistory()
    {
        return isShowHistory;
    }

    public void setShowHistory(Boolean showHistory)
    {
        isShowHistory = showHistory;
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

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public String getCustomFilter()
    {
        return customFilter;
    }

    public void setCustomFilter(String customFilter)
    {
        this.customFilter = customFilter;
    }

    public String getSearchText()
    {
        return searchText;
    }

    public void setSearchText(String searchText)
    {
        this.searchText = searchText;
    }

    public Boolean getIsShowHistory()
    {
        return isShowHistory;
    }

    public void setIsShowHistory(Boolean isShowHistory)
    {
        this.isShowHistory = isShowHistory;
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


    public String getLeadId()
    {
        return leadId;
    }

    public void setLeadId(String leadId)
    {
        this.leadId = leadId;
    }

    public Boolean getIsFilterAll()
    {
        return isFilterAll;
    }

    public void setIsFilterAll(Boolean isFilterAll)
    {
        this.isFilterAll = isFilterAll;
    }
}
