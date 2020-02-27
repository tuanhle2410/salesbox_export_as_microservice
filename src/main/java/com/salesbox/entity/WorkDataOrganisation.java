package com.salesbox.entity;

import com.salesbox.entity.enums.OrganisationType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/26/14
 * Time: 12:32 AM
 */
@Entity
@Table(name = "workdata_organisation")
public class WorkDataOrganisation extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private OrganisationType type;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE ;

    @Column(name = "key_code")
    private String keyCode;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "language_id")
    private UUID languageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

// --------------------- GETTER / SETTER METHODS ---------------------

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

    public OrganisationType getType()
    {
        return type;
    }

    public void setType(OrganisationType organisationType)
    {
        this.type = organisationType;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

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

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
}
