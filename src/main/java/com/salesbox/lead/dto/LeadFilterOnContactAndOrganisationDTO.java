package com.salesbox.lead.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/27/2017.
 */
public class LeadFilterOnContactAndOrganisationDTO
{

    List<String> contactIdList = new ArrayList<String>();
    List<String> organisationIdList = new ArrayList<String>();
    Boolean finished;

    public List<String> getContactIdList() {
        return contactIdList;
    }

    public Boolean getFinished()
    {
        return finished;
    }

    public void setFinished(Boolean finished)
    {
        this.finished = finished;
    }

    public void setContactIdList(List<String> contactIdList) {
        this.contactIdList = contactIdList;
    }

    public List<String> getOrganisationIdList() {
        return organisationIdList;
    }

    public void setOrganisationIdList(List<String> organisationIdList) {
        this.organisationIdList = organisationIdList;
    }

}


