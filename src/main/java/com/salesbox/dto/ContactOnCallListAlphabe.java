package com.salesbox.dto;

import com.salesbox.entity.callList.ContactOnCallList;

import java.util.UUID;

/**
 * Created by admin on 5/31/2017.
 */
public class ContactOnCallListAlphabe
{
    private ContactOnCallList contactOnCallList;

    private Integer index;

    private String firstLetter;

    private UUID contactId;

    public ContactOnCallList getContactOnCallList() {
        return contactOnCallList;
    }

    public void setContactOnCallList(ContactOnCallList contactOnCallList) {
        this.contactOnCallList = contactOnCallList;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }
}
