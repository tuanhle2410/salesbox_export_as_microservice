package com.salesbox.lead.dto;

import com.salesbox.entity.view.taskRelation.LeadOnContactOrOrganisation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/26/2017.
 */
public class LeadOnContactOrOrganisationListDTO
{

    List<LeadOnContactOrOrganisation> leadOnContactOrOrganisationList = new ArrayList<LeadOnContactOrOrganisation>();

    public List<LeadOnContactOrOrganisation> getLeadOnContactOrOrganisationList() {
        return leadOnContactOrOrganisationList;
    }

    public void setLeadOnContactOrOrganisationList(List<LeadOnContactOrOrganisation> leadOnContactOrOrganisationList) {
        this.leadOnContactOrOrganisationList = leadOnContactOrOrganisationList;
    }
}
