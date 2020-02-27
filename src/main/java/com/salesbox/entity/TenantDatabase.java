package com.salesbox.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/3/14
 * Time: 9:34 AM
 */
@Entity
@Table(name = "tenant_database")
public class TenantDatabase extends BaseEntity
{
    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "server_url")
    private String serverUrl;

    @Column(name = "database")
    private String database;

    public String getTenantId()
    {
        return tenantId;
    }

    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }


    public String getServerUrl()
    {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }

    public String getDatabase()
    {
        return database;
    }

    public void setDatabase(String database)
    {
        this.database = database;
    }
}
