package com.salesbox.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 5/26/2017.
 */
public class CallListPaginationBean extends PaginationBeanBase
{
    private String enterpriseId;
    private Date lastSyncDate;
    private Boolean deleted;

    private Boolean showHistory;
    private String roleFilterType;
    private String roleFilterValue;

    private List<UUID> ownerIds;

    private String searchText;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(Date lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Boolean getShowHistory() {
        return showHistory;
    }

    public void setShowHistory(Boolean showHistory) {
        this.showHistory = showHistory;
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

    public List<UUID> getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(List<UUID> ownerIds) {
        this.ownerIds = ownerIds;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
