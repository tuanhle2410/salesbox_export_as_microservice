package com.salesbox.dao;

import com.salesbox.entity.User;
import com.salesbox.entity.UserMetadata;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by tuantx on 12/4/2015.
 */
public interface UserMetadataDAO extends BaseDAO<UserMetadata>
{
    UserMetadata findByUserAndKey(User user, String key);

    List<UserMetadata> findByUserInAndKey(List<User> userList, String key);

    Map<String,UserMetadata> findByUserAndKeyIn(User user, List<String> keyList);

    Map<User, UserMetadata> getByUserInAndKey(List<User> userList, String key);

    Map<User, Map<String, UserMetadata>> findByUserInAndKeyIn(Collection<User> users, Collection<String> keys);
}
