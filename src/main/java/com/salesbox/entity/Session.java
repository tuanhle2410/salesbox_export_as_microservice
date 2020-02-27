package com.salesbox.entity;

import com.salesbox.entity.enums.PlatformType;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Quynh
 * Date: 01/05/2014
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "session")
public class Session extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "device_token")
    private String deviceToken = "WEB_TOKEN"; //default token act as web

    @Column(name = "client_version")
    private String clientVersion = "1.0.0";

    @Column(name = "platform_type")
    @Enumerated(EnumType.ORDINAL)
    private PlatformType platformType = PlatformType.WEB;

// --------------------------- CONSTRUCTORS ---------------------------

    public Session()
    {
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getDeviceToken()
    {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken)
    {
        this.deviceToken = deviceToken;
    }

    public String getClientVersion()
    {
        return clientVersion;
    }

    public void setClientVersion(String version)
    {
        this.clientVersion = version;
    }

    public PlatformType getPlatformType()
    {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType)
    {
        this.platformType = platformType;
    }
}
