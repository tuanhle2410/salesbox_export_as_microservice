package com.salesbox.common.multitenant;

import com.salesbox.entity.TenantDatabase;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MultiTenantConstants
{
    String DEFAULT_TENANT = "common";

    String TENANT_KEY = "enterpriseID";
    String USER_NAME = "username";
    String EMAIL = "email";

    Cache tenantCache = new ConcurrentMapCache("tenantCache");
    String TENANT_CONFIG_LIST_KEY = "tenantConfigList";

    List<TenantDatabase> tenantDatabaseList = new ArrayList<>();


    Map<String, String> emailEnterpriseMap = new HashMap<>();
}
