package com.salesbox.dao;

import com.salesbox.entity.CallList;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hunglv on 7/23/14.
 */
public interface CallListDAO extends BaseDAO<CallList>
{
    CallList findOne(UUID uuid);

    CallList findByUuidAndDeleted(UUID uuid, Boolean deleted);

    List<CallList> findByEnterpriseAndUpdatedDateAfterOrderByUuidAsc(Enterprise enterprise, int pageIndex, int pageSize, Date updatedDate);

    List<CallList> findAll();

    List<CallList> findByUuidIn(List<UUID> callListIdList);

    List<CallList> findByIdInWithOrder(Collection<UUID> uuids);

    void updateUpdatedDateByUuidIn(List<UUID> uuidList);

    List<CallList> findByEnterprise(Enterprise enterprise);

    List<CallList> findByOwnerIn(List<UUID> userList);

    void removeWhereCallListIn(List<CallList> callListList);

    List<CallList> findByOwnerIdAndUpdatedDateAfterOrderByUuidAsc(UUID ownerId, int pageIndex, int pageSize, Date updatedDate);

    List<CallList> findByOwnerUnitIdAndUpdatedDateAfterOrderByUuidAsc(UUID unitId, int pageIndex, int pageSize, Date updatedDate);

    List<Object[]> findUuidAndNameByOwnerInAndNotFinished(List<UUID> userIds);

    List<CallList> findByOwner(User user);

    List<UUID> findCallListIdByOwner(User user);

    void moveOwner(User oldUser, User newUser);
}
