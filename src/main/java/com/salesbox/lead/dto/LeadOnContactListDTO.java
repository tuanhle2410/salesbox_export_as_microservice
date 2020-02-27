package com.salesbox.lead.dto;

import com.salesbox.entity.view.taskRelation.LeadOnContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/22/2017.
 */
public class LeadOnContactListDTO
{

    List<LeadOnContact> leadOnContactList = new ArrayList<LeadOnContact>();

    public List<LeadOnContact> getLeadOnContactList() {
        return leadOnContactList;
    }

    public void setLeadOnContactList(List<LeadOnContact> leadOnContactList) {
        this.leadOnContactList = leadOnContactList;
    }
}
