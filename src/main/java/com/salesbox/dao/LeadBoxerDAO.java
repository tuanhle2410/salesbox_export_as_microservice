package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.LeadBoxer;

import java.util.List;
import java.util.UUID;

/**
 * Created by GEM on 2017-06-10.
 */
public interface LeadBoxerDAO extends BaseDAO<LeadBoxer>
{
    LeadBoxer findOne(UUID uuid);

    List<LeadBoxer> findBySite(String site);

    List<LeadBoxer> findByEnterpriseId(Enterprise enterprise);

    List<LeadBoxer> findBySiteAndEnterprise(Enterprise enterprise, String site);
}
