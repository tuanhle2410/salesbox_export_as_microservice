package com.salesbox.entity;

import com.salesbox.entity.enums.ActivityType;
import com.salesbox.entity.enums.DiscProfileType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/26/14
 * Time: 10:20 AM
 */
@Entity
@Table(name = "workdata_activity")
public class WorkDataActivity extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private ActivityType type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "disc_profile")
    @Enumerated(EnumType.ORDINAL)
    private DiscProfileType discProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "key_code")
    private String keyCode;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE ;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "language_id")
    private UUID languageId;


// --------------------- GETTER / SETTER METHODS ---------------------

    public String getKeyCode()
    {
        return keyCode;
    }

    public void setKeyCode(String keyCode)
    {
        this.keyCode = keyCode;
    }

    public UUID getLanguageId()
    {
        return languageId;
    }

    public void setLanguageId(UUID languageId)
    {
        this.languageId = languageId;
    }

    public ActivityType getType()
    {
        return type;
    }

    public void setType(ActivityType type)
    {
        this.type = type;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public DiscProfileType getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(DiscProfileType discProfile)
    {
        this.discProfile = discProfile;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
}
