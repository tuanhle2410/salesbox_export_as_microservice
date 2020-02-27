package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.Session;
import com.salesbox.entity.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: luult123
 * Date: 5/2/2014
 */
public interface SessionDAO extends BaseDAO<Session>
{
// -------------------------- OTHER METHODS --------------------------
    Session findOne(UUID uuid);

    public Session findByToken(String token);

    public Session findOneByUser(User user);

    public List<Session> findByUser(User user);

    public List<Session> findByUserNotWeb(User user);

    public List<Session> findByDeviceToken(String deviceToken);

    public Session findByUserAndDeviceToken(User user, String deviceToken);

    Enterprise findEnterpriseByToken(String token);

    User findUserByToken(String token);

    void removeWhereUserIn(List<User> userList);

    void removeWhereUser(User user);

    long countUniqueLoggedInUserBetween(Date startDate, Date endDate);
}
