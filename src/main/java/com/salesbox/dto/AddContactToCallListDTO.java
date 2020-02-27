package com.salesbox.dto;

import java.util.List;

/**
 * Created by admin on 5/26/2017.
 */
public class AddContactToCallListDTO
{
    private String callListContactId;

    private List<String> contactIds;

    private List<String> removeContactIds;

    public String getCallListContactId()
    {
        return callListContactId;
    }

    public void setCallListContactId(String callListContactId)
    {
        this.callListContactId = callListContactId;
    }

    public List<String> getContactIds()
    {
        return contactIds;
    }

    public void setContactIds(List<String> contactIds)
    {
        this.contactIds = contactIds;
    }

    public List<String> getRemoveContactIds()
    {
        return removeContactIds;
    }

    public void setRemoveContactIds(List<String> removeContactIds)
    {
        this.removeContactIds = removeContactIds;
    }
}
