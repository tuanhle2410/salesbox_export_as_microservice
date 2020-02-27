package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by GEM on 2017-06-10.
 */
@Entity
@Table(name = "lead_site")
public class LeadSite extends BaseEntity
{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lead_id")
    Lead lead;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "site_id")
    LeadBoxer leadBoxer;

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public LeadBoxer getLeadBoxer() {
        return leadBoxer;
    }

    public void setLeadBoxer(LeadBoxer leadBoxer) {
        this.leadBoxer = leadBoxer;
    }
}
