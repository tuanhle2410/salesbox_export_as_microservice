package com.salesbox.dao;

import com.salesbox.entity.RecentActivity;
import com.salesbox.entity.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by tuantx on 3/20/2016.
 */
public interface RecentActivityDAO extends BaseDAO<RecentActivity>
{
    public RecentActivity findByObjectIdAndUser(UUID objectId, User user);

    List<RecentActivity> findByObjectIdInAndUser(List<UUID> objectIdList, User user);

    void removeWhereUserIn(List<User> userList);

    List<RecentActivity> findByUserInAndUpdatedDateBetweenOrderByUpdatedDateDesc(List<User> userList, Date startDate, Date endDate, Integer pageIndex, Integer pageSize);
}
