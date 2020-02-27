package com.salesbox.dao;

import com.salesbox.entity.Lead;
import com.salesbox.entity.LeadProduct;
import com.salesbox.entity.Product;

import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 7/16/14
 */
public interface LeadProductDAO extends BaseDAO<LeadProduct>
{
    LeadProduct findOne(UUID uuid);

    List<LeadProduct> findByLead(Lead lead);

    List<LeadProduct> findByLeadIn(List<Lead> leadList);

    List<Object[]> findByLeadIdIn(List<UUID> leadIdList);

    void deleteInBatch(List<LeadProduct> leadProductList);

    void removeWhereLeadIn(List<Lead> leadList);

    void removeWhereLead(Lead lead);

    List<Product> findProductByLead(Lead lead);

}
