package com.salesbox.dto;

import com.salesbox.entity.MultiRelation;

/**
 * Created by admin on 3/28/2017.
 */
public class DetailContactAccountRelationDTO
{
    private String uuid;
    private String name;

    public DetailContactAccountRelationDTO(MultiRelation multiRelation)
    {
        uuid = multiRelation.getUuid().toString();
        name = multiRelation.getName();
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
