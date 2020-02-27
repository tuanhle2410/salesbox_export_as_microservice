package com.salesbox.lead.dto;

import com.salesbox.entity.view.taskRelation.LeadOnOrganisation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/24/2017.
 */
public class LeadOnOrganisationListDTO
{

    List<LeadOnOrganisation> leadOnOrganisationList = new ArrayList<LeadOnOrganisation>();

    public List<LeadOnOrganisation> getLeadOnOrganisationList()
    {
        return leadOnOrganisationList;
    }

    public void setLeadOnOrganisationList(List<LeadOnOrganisation> leadOnOrganisationList)
    {
        this.leadOnOrganisationList = leadOnOrganisationList;
    }
}
