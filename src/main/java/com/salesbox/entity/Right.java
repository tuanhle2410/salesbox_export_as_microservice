package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/23/14
 * Time: 6:30 PM
 */
@Entity
@Table(name = "account_right")
public class Right extends BaseEntity
{
    // ------------------------------ FIELDS ------------------------------
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id")
    private Scope scope;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

// --------------------- GETTER / SETTER METHODS ---------------------

    public Permission getPermission()
    {
        return permission;
    }

    public void setPermission(Permission permission)
    {
        this.permission = permission;
    }

    public Scope getScope()
    {
        return scope;
    }

    public void setScope(Scope scope)
    {
        this.scope = scope;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Right()
    {
    }

    public Right(User user, Scope scope, Permission permission)
    {
        this.permission = permission;
        this.scope = scope;
        this.user = user;
    }
}
