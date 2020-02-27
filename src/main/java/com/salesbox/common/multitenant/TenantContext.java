package com.salesbox.common.multitenant;

import static com.salesbox.common.multitenant.MultiTenantConstants.DEFAULT_TENANT;

public class TenantContext
{
    private static ThreadLocal<String> currentTenant = new ThreadLocal<String>()
    {
        @Override
        protected String initialValue()
        {
            return DEFAULT_TENANT;
        }
    };

    public static void setCurrentTenant(String tenant)
    {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant()
    {
        return currentTenant.get();
    }

    public static void clear()
    {
        currentTenant.remove();
    }

}