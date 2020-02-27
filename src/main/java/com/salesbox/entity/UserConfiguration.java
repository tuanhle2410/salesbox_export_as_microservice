package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 7/22/14
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "user_configuration")
public class UserConfiguration extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
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
