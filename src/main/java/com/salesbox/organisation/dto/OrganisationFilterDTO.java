package com.salesbox.organisation.dto;

import com.salesbox.advancesearch.dto.SearchFieldDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hungnh on 6/29/15.
 */
public class OrganisationFilterDTO
{
    private String roleFilterType;
    private String roleFilterValue;
    private String orderBy;
    private String ftsTerms;
    private String customFilter;
    private List<SearchFieldDTO[]> searchFieldDTOList = new ArrayList<>();
//    private List<CustomFieldSearchDTO> customFieldSearchDTOList = new ArrayList<>();

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

    public List<SearchFieldDTO[]> getSearchFieldDTOList()
    {
        return searchFieldDTOList;
    }

    public void setSearchFieldDTOList(List<SearchFieldDTO[]> searchFieldDTOList)
    {
        this.searchFieldDTOList = searchFieldDTOList;
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


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(roleFilterType);
        stringBuilder.append(roleFilterValue);
        stringBuilder.append(orderBy);
        stringBuilder.append(ftsTerms);
        stringBuilder.append(customFilter);
        if (searchFieldDTOList != null && searchFieldDTOList.size() > 0) {
            String strSearchFields = searchFieldDTOList.stream().filter(field -> field != null)
                    .map(field -> Arrays.stream(field).map(item -> item.toString())
                            .collect(Collectors.joining()))
                    .collect(Collectors.joining());
            stringBuilder.append(strSearchFields);
        }
        return stringBuilder.toString();
    }
}
