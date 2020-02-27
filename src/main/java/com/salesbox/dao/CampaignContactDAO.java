package com.salesbox.dao;

import com.salesbox.entity.Campaign;
import com.salesbox.entity.CampaignContact;
import com.salesbox.entity.Contact;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 8/15/14
 * Time: 11:24 AM
 */
public interface CampaignContactDAO extends BaseDAO<CampaignContact>
{
    public CampaignContact findByContactAndCampaign(Contact contact, Campaign campaign);

    void removeWhereCampaignIn(List<Campaign> campaignList);
}
