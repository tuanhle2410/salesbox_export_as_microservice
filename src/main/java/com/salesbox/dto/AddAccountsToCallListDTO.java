package com.salesbox.dto;

import java.util.List;

/**
 * Created by admin on 5/20/2017.
 */
public class AddAccountsToCallListDTO
{

    private String callListAccountId;

    private List<String> accountIds;

    private List<String> removeAccountIds;

    public List<String> getRemoveAccountIds()
    {
        return removeAccountIds;
    }

    public void setRemoveAccountIds(List<String> removeAccountIds)
    {
        this.removeAccountIds = removeAccountIds;
    }

    public List<String> getAccountIds()
    {
        return accountIds;
    }

    public void setAccountIds(List<String> accountIds)
    {
        this.accountIds = accountIds;
    }

    public String getCallListAccountId()
    {
        return callListAccountId;
    }

    public void setCallListAccountId(String callListAccountId)
    {
        this.callListAccountId = callListAccountId;
    }
}
