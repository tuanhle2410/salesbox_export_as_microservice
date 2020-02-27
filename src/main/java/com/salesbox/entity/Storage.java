package com.salesbox.entity;

import com.salesbox.entity.enums.StorageType;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/15/14
 * Time: 12:03 PM
 */

@Entity
@Table(name = "storage")
public class Storage extends BaseEntity
{
    @Column(name = "type")
    @Enumerated
    private StorageType type;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "salesbox_folder_id")
    private String salesBoxFolderId;

    @Column(name = "salesbox_folder_url")
    private String salesBoxFolderUrl;

    public StorageType getType()
    {
        return type;
    }

    public void setType(StorageType type)
    {
        this.type = type;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getSalesBoxFolderId()
    {
        return salesBoxFolderId;
    }

    public void setSalesBoxFolderId(String salesBoxFolderId)
    {
        this.salesBoxFolderId = salesBoxFolderId;
    }

    public String getSalesBoxFolderUrl()
    {
        return salesBoxFolderUrl;
    }

    public void setSalesBoxFolderUrl(String salesBoxFolderUrl)
    {
        this.salesBoxFolderUrl = salesBoxFolderUrl;
    }
}
