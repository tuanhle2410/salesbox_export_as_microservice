package com.salesbox.dao;

import com.salesbox.entity.Activity;
import com.salesbox.entity.SalesMethod;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 4/26/14
 */
public interface ActivityDAO extends BaseDAO<Activity>
{
    Activity findOne(UUID uuid);

    List<Activity> findBySalesMethodOrderByIndexAsc(SalesMethod salesMethod);

    List<Activity> findBySalesMethod(SalesMethod salesMethod);

    List<Activity> findByUuidIn(Collection<UUID> idList);

    List<Activity> findBySalesMethodAndNameCaseInsensitive(SalesMethod salesMethod, String name);

    void removeBySalesMethodIn(List<SalesMethod> salesMethodList);

    List<Activity> findByActiveSalesMethodIn(List<SalesMethod> salesMethodList);
}
