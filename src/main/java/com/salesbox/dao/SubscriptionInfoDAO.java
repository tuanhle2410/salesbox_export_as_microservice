package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.SubscriptionInfo;
import com.salesbox.entity.enums.SubscriptionSourceType;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/10/14
 * Time: 5:40 PM
 */
public interface SubscriptionInfoDAO extends BaseDAO<SubscriptionInfo>
{
    SubscriptionInfo findOne(UUID uuid);

    List<SubscriptionInfo> findByEnterprise(Enterprise enterprise);

    SubscriptionInfo findByEnterpriseAndType(Enterprise enterprise, SubscriptionSourceType type);

    void removeByEnterprise(Enterprise enterprise);

    SubscriptionInfo findByEnterpriseAndAmazonSubscriptionId(Enterprise enterprise, String amazonSubscriptionId);

    SubscriptionInfo findBySubscriptionId(String amazonSubscriptionId);

    Long countSubscriptionByEnterpriseAndType(Enterprise enterprise, SubscriptionSourceType type);

    /**
     * Check if entityManager contains entity or not
     */
    void remove(SubscriptionInfo subscriptionInfo);
}
