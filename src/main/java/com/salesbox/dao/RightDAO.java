package com.salesbox.dao;

import com.salesbox.entity.Permission;
import com.salesbox.entity.Right;
import com.salesbox.entity.Scope;
import com.salesbox.entity.User;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/23/14
 * Time: 6:44 PM
 */
public interface RightDAO extends BaseDAO<Right>
{
// -------------------------- OTHER METHODS --------------------------

    public List<Right> findByUser(User user);

    Right findByUserAndPermission(User user, Permission permission);

    public Right findOne(UUID uuid);

    Scope findScopeByUserAndPermissionId(User user, UUID permissionId);

    void removeWhereUserIn(List<User> userList);

    void removeWhereUser(User user);

    public List<Right> findByUserInAndPermissionAndScope(List<User> userList, Permission permission, Scope scope);
}
