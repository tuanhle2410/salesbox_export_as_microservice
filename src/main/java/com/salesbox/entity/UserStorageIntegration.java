package com.salesbox.entity;

import com.salesbox.entity.BaseEntity;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.PlatformType;
import com.salesbox.entity.enums.StorageIntegrationType;
import com.salesbox.entity.enums.TokenStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hunglv2
 * Date: 2/2616
 * Time: 12:03 PM
 */

@Entity
@Table(name = "user_storage_integration")
public class UserStorageIntegration extends BaseEntity
{
    @Column(name = "type")
    @Enumerated
    private StorageIntegrationType type;

    @Column(name = "user_identifier")
    private String userIdentifier;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;
    
    @Column(name = "service_resource_id")
    private String serviceResourceId;
    
    @Column(name = "service_endpoint_uri")
    private String serviceEndpointUri;

    @Column(name = "expire_date")
    private Date expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "platform_type")
    @Enumerated
    private PlatformType platformType = PlatformType.WEB;

    @Column(name = "is_revoked")
    private TokenStatus isRevoked;

    public StorageIntegrationType getType()
    {
        return type;
    }

    public void setType(StorageIntegrationType type)
    {
        this.type = type;
    }

    public String getUserIdentifier()
    {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier)
    {
        this.userIdentifier = userIdentifier;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public Date getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(Date expireDate)
    {
        this.expireDate = expireDate;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public PlatformType getPlatformType()
    {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType)
    {
        this.platformType = platformType;
    }

	public String getServiceEndpointUri() {
		return serviceEndpointUri;
	}

	public void setServiceEndpointUri(String serviceEndpointUri) {
		this.serviceEndpointUri = serviceEndpointUri;
	}

	public String getServiceResourceId() {
		return serviceResourceId;
	}

	public void setServiceResourceId(String serviceResourceId) {
		this.serviceResourceId = serviceResourceId;
	}

    public TokenStatus getRevoked()
    {
        return isRevoked;
    }

    public void setRevoked(TokenStatus revoked)
    {
        isRevoked = revoked;
    }
}
