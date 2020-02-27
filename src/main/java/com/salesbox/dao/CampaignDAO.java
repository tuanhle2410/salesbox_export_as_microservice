package com.salesbox.dao;

import com.salesbox.entity.Campaign;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 7/23/14
 */
public interface CampaignDAO extends BaseDAO<Campaign>
{
    Campaign findOne(UUID uuid);

    List<Campaign> findByEnterpriseAndUpdatedDateAfterOrderByUuidAsc(Enterprise enterprise, Date updatedDate, Integer pageIndex, Integer pageSize);

    List<Campaign> findByEnterpriseAndUpdatedDateAfterAndNotDeletedOrderByUuidAsc(Enterprise enterprise, Date updatedDate, Integer pageIndex, Integer pageSize);

    List<Campaign> findByEnterprise(Enterprise enterprise);

    List<Campaign> findByEnterpriseIdAndDeletedAndEndDateAfter(UUID enterpriseId, Boolean deleted, Date endDate);

    List<Campaign> findAll();

    List<Campaign> findByOwnerIn(List<User> userList);

    void removeWhereCampaignIn(List<Campaign> campaignList);

    List<Campaign> findByIdIn(Collection<UUID> uuids);

    List<Campaign> findByOwner(User user);

    List<Campaign> findByEnterpriseAndStartDateAndEndDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    void moveOwner(User oldUser, User newUser);

    Long countNewStartDateAndEndDateBetween(Date startDate, Date endDate, List<User> userList);

    Long countActiveStartDateAndEndDateBetween(Date startDate, Date endDate, List<User> userList);

    Long countDoneStartDateAndEndDateBetween(Date startDate, Date endDate, List<User> userList);

}
