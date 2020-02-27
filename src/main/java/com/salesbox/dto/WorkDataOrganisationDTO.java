package com.salesbox.dto;

import com.salesbox.entity.WorkDataOrganisation;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/26/14
 * Time: 12:00 AM
 */
public class WorkDataOrganisationDTO implements Serializable
{
    private UUID uuid;
    private String type;
    private String name;
    private String code;
    private String keyCode;

    public String getKeyCode()
    {
        return keyCode;
    }

    public void setKeyCode(String keyCode)
    {
        this.keyCode = keyCode;
    }

    public WorkDataOrganisationDTO()
    {
    }

    public WorkDataOrganisationDTO(WorkDataOrganisation workDataOrganisation)
    {
        this.uuid = workDataOrganisation.getUuid();
        this.type = workDataOrganisation.getType().toString();
        this.name = workDataOrganisation.getName();
        this.code = workDataOrganisation.getCode();
        this.keyCode = workDataOrganisation.getKeyCode();
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}
