package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by GEM on 2017-06-10.
 */
@Entity
@Table(name = "lead_boxer")
public class LeadBoxer extends BaseEntity
{

    @Column(name = "site")
    String site;

    @Column(name = "domain_name")
    String domainName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enterprise_id")
    Enterprise enterprise;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
