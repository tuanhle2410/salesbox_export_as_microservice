package com.salesbox.dao;

import com.salesbox.entity.Campaign;
import com.salesbox.entity.CampaignProduct;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 7/23/14
 * Time: 4:32 PM
 */
public interface CampaignProductDAO extends BaseDAO<CampaignProduct>
{
    public List<CampaignProduct> findByCampaign(Campaign campaign);
    
    public List<CampaignProduct> findByCampaignIn(List<Campaign> campaignList);

    void removeWhereCampaignIn(List<Campaign> campaignList);
}
