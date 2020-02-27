package com.salesbox.prospect.dto;

import com.salesbox.advancesearch.dto.SearchFieldDTO;
import com.salesbox.entity.enums.ProspectHistoricStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lent on 6/13/2015.
 */
public class ProspectFilterDTO {
    private Date startDate;
    private Date endDate;
    private String roleFilterType;
    private String roleFilterValue;
    private boolean isShowHistory;
    private boolean isDeleted;
    private String orderBy;
    private boolean isRequiredOwner;
    private String ftsTerms;
    private String customFilter;
    private Boolean isFilterAll = false;
    private ProspectHistoricStatus wonLossFilter;
    private List<SearchFieldDTO[]> searchFieldDTOList = new ArrayList<>();

    private List<UUID> salesProcessIds = new ArrayList<>();

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

    public boolean isShowHistory() {
        return isShowHistory;
    }

    public void setIsShowHistory(boolean isShowHistory) {
        this.isShowHistory = isShowHistory;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isRequiredOwner() {
        return isRequiredOwner;
    }

    public void setIsRequiredOwner(boolean isRequiredOwner) {
        this.isRequiredOwner = isRequiredOwner;
    }

    public String getFtsTerms() {
        return ftsTerms;
    }

    public void setFtsTerms(String ftsTerms) {
        this.ftsTerms = ftsTerms;
    }

    public String getCustomFilter() {
        return customFilter;
    }

    public void setCustomFilter(String customFilter) {
        this.customFilter = customFilter;
    }

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

    public List<SearchFieldDTO[]> getSearchFieldDTOList()
    {
        return searchFieldDTOList;
    }

    public void setSearchFieldDTOList(List<SearchFieldDTO[]> searchFieldDTOList)
    {
        this.searchFieldDTOList = searchFieldDTOList;
    }


    public Boolean getIsFilterAll()
    {
        return isFilterAll;
    }

    public void setIsFilterAll(Boolean isFilterAll)
    {
        this.isFilterAll = isFilterAll;
    }

    public Boolean getFilterAll()
    {
        return isFilterAll;
    }

    public void setFilterAll(Boolean filterAll)
    {
        isFilterAll = filterAll;
    }

    public ProspectHistoricStatus getWonLossFilter()
    {
        return wonLossFilter;
    }

    public void setWonLossFilter(ProspectHistoricStatus wonLossFilter)
    {
        this.wonLossFilter = wonLossFilter;
    }

    public List<UUID> getSalesProcessIds()
    {
        return salesProcessIds;
    }

    public void setSalesProcessIds(List<UUID> salesProcessIds)
    {
        this.salesProcessIds = salesProcessIds;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (startDate != null) {
            stringBuilder.append(startDate.toString());
        }
        if (endDate != null) {
            stringBuilder.append(endDate.toString());
        }
        stringBuilder.append(roleFilterType);
        stringBuilder.append(roleFilterValue);
        stringBuilder.append(isShowHistory);
        stringBuilder.append(isDeleted);
        stringBuilder.append(orderBy);
        stringBuilder.append(isRequiredOwner);
        stringBuilder.append(ftsTerms);
        stringBuilder.append(customFilter);
        stringBuilder.append(isFilterAll);
        stringBuilder.append(wonLossFilter);

        if (searchFieldDTOList != null && searchFieldDTOList.size() > 0) {
            String strSearchFields = searchFieldDTOList.stream().filter(field -> field != null)
                    .map(field -> Arrays.stream(field).map(item -> item.toString())
                            .collect(Collectors.joining()))
                    .collect(Collectors.joining());
            stringBuilder.append(strSearchFields);
        }
        if (salesProcessIds != null && salesProcessIds.size() > 0) {
            stringBuilder.append(salesProcessIds.stream().map(item -> item.toString()).reduce("", String::concat));
        }

        return stringBuilder.toString();
    }

}
