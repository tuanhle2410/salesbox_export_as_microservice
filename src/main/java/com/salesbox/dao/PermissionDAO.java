package com.salesbox.dao;

import com.salesbox.entity.Permission;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/23/14
 * Time: 6:43 PM
 */
public interface PermissionDAO extends BaseDAO<Permission>
{
    Permission findOne(UUID uuid);
}
