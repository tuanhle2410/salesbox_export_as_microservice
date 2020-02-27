package com.salesbox.dto;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by tulh on 28/03/2016.
 */
public class RightWebDTO implements Serializable
{
    private UUID uuid;
    private PermissionWebDTO permission;
    private String firstName;
    private String lastName;
    private Boolean active;
    private Boolean manager;
    private String unitName;
    private UUID unitId;

    public RightWebDTO()
    {
        this.permission = new PermissionWebDTO();
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public PermissionWebDTO getPermission()
    {
        return permission;
    }

    public void setPermission(PermissionWebDTO permissionWebDTO)
    {
        this.permission = permissionWebDTO;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Boolean getManager()
    {
        return manager;
    }

    public void setManager(Boolean manager)
    {
        this.manager = manager;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public UUID getUnitId()
    {
        return unitId;
    }

    public void setUnitId(UUID unitId)
    {
        this.unitId = unitId;
    }

    public static void main(String args[])
    {
        RightWebDTO rightWebDTO = new RightWebDTO();
        rightWebDTO.setUuid(UUID.randomUUID());
        System.out.println(new Gson().toJson(rightWebDTO));
    }
}
