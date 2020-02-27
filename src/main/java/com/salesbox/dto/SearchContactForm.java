package com.salesbox.dto;

import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 5/27/2017.
 */
public class SearchContactForm
{
    private List<String> keywords;
    private List<UUID> enterpriseIds;
    private UUID callListAccountId;

    public List<String> getKeywords()
    {
        return keywords;
    }

    public void setKeywords(List<String> keywords)
    {
        this.keywords = keywords;
    }

    public List<UUID> getEnterpriseIds() {
        return enterpriseIds;
    }

    public void setEnterpriseIds(List<UUID> enterpriseIds) {
        this.enterpriseIds = enterpriseIds;
    }

    public UUID getCallListAccountId()
    {
        return callListAccountId;
    }

    public void setCallListAccountId(UUID callListAccountId)
    {
        this.callListAccountId = callListAccountId;
    }
}
