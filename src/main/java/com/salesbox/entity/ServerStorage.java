package com.salesbox.entity;

import com.salesbox.entity.enums.ServerStorageType;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/3/14
 * Time: 9:34 AM
 */
@Entity
@Table(name = "server_storage")
public class ServerStorage extends BaseEntity
{
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    ServerStorageType type;

    @Column(name = "value")
    String value;

    public ServerStorageType getType()
    {
        return type;
    }

    public void setType(ServerStorageType type)
    {
        this.type = type;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
