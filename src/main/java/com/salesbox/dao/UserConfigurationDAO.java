package com.salesbox.dao;

import com.salesbox.entity.User;
import com.salesbox.entity.UserConfiguration;
import com.salesbox.entity.enums.UserConfigurationKey;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 7/22/14
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserConfigurationDAO extends BaseDAO<UserConfiguration>
{
    UserConfiguration findByUserAndKey(User user, String key);

    List<UserConfiguration> findByUser(User user);

    List<UserConfiguration> findByUserInAndKey(Collection<User> users, UserConfigurationKey key);

    List<Object[]> findUserIdAndLanguageIdByUserIn(List<User> userList);

    void removeWhereUserIn(List<User> userList);
}
