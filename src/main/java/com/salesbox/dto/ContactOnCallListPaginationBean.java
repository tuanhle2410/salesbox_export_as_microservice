package com.salesbox.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 5/26/2017.
 */
public class ContactOnCallListPaginationBean extends PaginationBeanBase
{
    private Date lastSyncDate;
    private Boolean deleted;
    private String callListId;
    private String enterpriseId;
    private Boolean showPrioritized;
    private List<UUID> existedContactIds = new ArrayList<>();

    public List<UUID> getExistedContactIds()
    {
        return existedContactIds;
    }

    public void setExistedContactIds(List<UUID> existedContactIds)
    {
        this.existedContactIds = existedContactIds;
    }

    public Boolean getShowPrioritized()
    {
        return showPrioritized;
    }

    public void setShowPrioritized(Boolean showPrioritized)
    {
        this.showPrioritized = showPrioritized;
    }

    public Date getLastSyncDate()
    {
        return lastSyncDate;
    }

    public void setLastSyncDate(Date lastSyncDate)
    {
        this.lastSyncDate = lastSyncDate;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public String getCallListId()
    {
        return callListId;
    }

    public void setCallListId(String callListId)
    {
        this.callListId = callListId;
    }

    public String getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }
}
