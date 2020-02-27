package com.salesbox.dao;

import com.salesbox.entity.Lead;
import com.salesbox.entity.LeadBoxer;
import com.salesbox.entity.LeadSite;

import java.util.List;
import java.util.UUID;

/**
 * Created by GEM on 2017-06-10.
 */
public interface LeadSiteDAO extends BaseDAO<LeadSite>
{
    LeadSite findOne(UUID uuid);

    LeadSite findOneByLeadAndSite(Lead lead, LeadBoxer leadBoxer);

    List<LeadBoxer> findLeadBoxerByLead(Lead lead);
}
