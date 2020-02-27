package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.SyncEnterpriseSession;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.SyncObjectType;

import java.util.UUID;

/**
 * User: luult123
 * Date: 5/2/2014
 */
public interface SyncEnterpriseSessionDAO extends BaseDAO<SyncEnterpriseSession>
{
    SyncEnterpriseSession findOne(UUID uuid);

    SyncEnterpriseSession findByEnterpriseAndObjectType(Enterprise enterprise, SyncObjectType objectType);

    Boolean deleteByEnterPriseAndType(Enterprise enterprise, SyncObjectType objectType);

    SyncEnterpriseSession findByUserAndObjectType(User user, SyncObjectType objectType);

    Boolean updateAllInProgressTaskToDone();
}
