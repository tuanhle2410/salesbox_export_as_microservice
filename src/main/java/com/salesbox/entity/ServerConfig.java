package com.salesbox.entity;

import com.salesbox.entity.enums.ServerConfigType;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/3/14
 * Time: 9:34 AM
 */
@Entity
@Table(name = "server_config")
public class ServerConfig extends BaseEntity
{
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    ServerConfigType type;

    @Column(name = "value")
    String value;

    public ServerConfigType getType()
    {
        return type;
    }

    public void setType(ServerConfigType type)
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
