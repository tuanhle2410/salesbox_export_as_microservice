package com.salesbox.dao;

import com.salesbox.entity.ServerConfig;
import com.salesbox.entity.enums.ServerConfigType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/3/14
 * Time: 9:42 AM
 */
public interface ServerConfigDAO extends BaseDAO<ServerConfig>
{
    public ServerConfig findByType(ServerConfigType serverConfigType);

    public List<ServerConfig> findAll();

    String findValueByType(ServerConfigType serverConfigType);
}
