package com.salesbox.dto;

import com.salesbox.advancesearch.dto.SearchFieldDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by huynhlbq on 07/01/2015.
 */
public class ContactFilterDTO
{
    private String roleFilterType;
    private String roleFilterValue;
    private String orderBy;
    private String customFilter;
    private String searchText;
    private List<SearchFieldDTO[]> searchFieldDTOList = new ArrayList<>();
    private List<String> contactIds;
    private List<String> userIds;
//    private List<CustomFieldSearchDTO> customFieldSearchDTOList = new ArrayList<>();


    public String getRoleFilterType() {
        return roleFilterType;
    }

    public void setRoleFilterType(String roleFilterType) {
        this.roleFilterType = roleFilterType;
    }

    public String getRoleFilterValue() {
        return roleFilterValue;
    }

    public void setRoleFilterValue(String roleFilterValue) {
        this.roleFilterValue = roleFilterValue;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getCustomFilter() {
        return customFilter;
    }

    public void setCustomFilter(String customFilter) {
        this.customFilter = customFilter;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<SearchFieldDTO[]> getSearchFieldDTOList() {
        return searchFieldDTOList;
    }

    public void setSearchFieldDTOList(List<SearchFieldDTO[]> searchFieldDTOList) {
        this.searchFieldDTOList = searchFieldDTOList;
    }

    public List<String> getContactIds() {
        return contactIds;
    }

    public void setContactIds(List<String> contactIds) {
        this.contactIds = contactIds;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
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
        StringBuilder stringBuilder = new StringBuilder(roleFilterType);
        stringBuilder.append(roleFilterValue);
        stringBuilder.append(orderBy);
        stringBuilder.append(customFilter);
        stringBuilder.append(searchText);
        if (contactIds != null && contactIds.size() > 0)
            stringBuilder.append(contactIds.stream().reduce("", String::concat));
        if (userIds != null && userIds.size() > 0)
            stringBuilder.append(userIds.stream().reduce("", String::concat));
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
