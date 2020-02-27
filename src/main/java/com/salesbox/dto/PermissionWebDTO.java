package com.salesbox.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tulh on 28/03/2016.
 */
public class PermissionWebDTO
{
    private Map<String, Boolean> own_unit = new HashMap<>();
    private Map<String, Boolean> own_objects = new HashMap<>();
    private Map<String, Boolean> all_company = new HashMap<>();
    private Boolean admin = Boolean.FALSE;

    public PermissionWebDTO()
    {
        this.own_unit.put("read", false);
        this.own_unit.put("write", false);
        this.own_unit.put("delete", false);

        this.own_objects.put("read", false);
        this.own_objects.put("write", false);
        this.own_objects.put("delete", false);

        this.all_company.put("read", false);
        this.all_company.put("write", false);
        this.all_company.put("delete", false);
    }

    public Map<String, Boolean> getOwn_unit()
    {
        return own_unit;
    }

    public void setOwn_unit(Map<String, Boolean> own_unit)
    {
        this.own_unit = own_unit;
    }

    public Map<String, Boolean> getOwn_objects()
    {
        return own_objects;
    }

    public void setOwn_objects(Map<String, Boolean> own_objects)
    {
        this.own_objects = own_objects;
    }

    public Map<String, Boolean> getAll_company()
    {
        return all_company;
    }

    public void setAll_company(Map<String, Boolean> all_company)
    {
        this.all_company = all_company;
    }

    public Boolean getAdmin()
    {
        return admin;
    }

    public void setAdmin(Boolean admin)
    {
        this.admin = admin;
    }
}
