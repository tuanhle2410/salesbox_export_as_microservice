package com.salesbox.dao;

import com.salesbox.entity.Campaign;
import com.salesbox.entity.CampaignStep;
import com.salesbox.entity.enums.CampaignStatusType;
import com.salesbox.entity.enums.SourceType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 7/23/14
 * Time: 3:29 PM
 */
public interface CampaignStepDAO extends BaseDAO<CampaignStep>
{
    public CampaignStep findOne(UUID uuid);

    public List<CampaignStep> findByCampaignOrderByIndex(Campaign campaign);

    List<CampaignStep> findAllSharedCampaignStep();

    public List<CampaignStep> findByCampaignAndStatusOrderByIndex(Campaign campaign, CampaignStatusType status);

    List<CampaignStep> findByStatusAndTypeAndDateBetween(CampaignStatusType status, SourceType type, Date startDate, Date endDate);

    List<CampaignStep> findAllSharedCampaignStepCreatedDateBetween(Date startDate, Date endDate);

    List<CampaignStep> findByCampaignIn(List<Campaign> campaignList);

    void removeWhereCampaignStepIn(List<CampaignStep> campaignStepList);

    List<CampaignStep> findByStatusAndTypeAndDateBefore(CampaignStatusType campaignStatusType, SourceType sourceType, Date date);
}
