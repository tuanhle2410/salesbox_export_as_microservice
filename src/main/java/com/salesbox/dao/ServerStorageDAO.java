package com.salesbox.dao;

import com.salesbox.entity.ServerStorage;
import com.salesbox.entity.enums.ServerStorageType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/3/14
 * Time: 9:42 AM
 */
public interface ServerStorageDAO extends BaseDAO<ServerStorage>
{
    public ServerStorage findByType(ServerStorageType type);

    public List<ServerStorage> findByTypeIn(List<ServerStorageType> typeList);

    public List<ServerStorageType> findAll();

    String findValueByType(ServerStorageType type);
}
