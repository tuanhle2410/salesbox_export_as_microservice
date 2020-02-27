package com.salesbox.dto;

import com.salesbox.entity.callList.OrganisationOnCallList;

/**
 * Created by admin on 5/31/2017.
 */
public class OrganisationOnCallListAlphabetWrapper
{
    private OrganisationOnCallList organisationOnCallList;
    private Integer index;

    public OrganisationOnCallList getOrganisationOnCallList()
    {
        return organisationOnCallList;
    }

    public void setOrganisationOnCallList(OrganisationOnCallList organisationOnCallList)
    {
        this.organisationOnCallList = organisationOnCallList;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }
}
