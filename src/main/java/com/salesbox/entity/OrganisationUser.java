package com.salesbox.entity;

import com.salesbox.entity.enums.OrganisationUserSourceType;

import javax.persistence.*;

/**
 * User: luult
 * Date: 5/20/14
 */
@Entity
@Table(name = "organisation_user")
public class OrganisationUser extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "source_type")
    @Enumerated(EnumType.ORDINAL)
    private OrganisationUserSourceType sourceType = OrganisationUserSourceType.MANUALLY;

    public OrganisationUser()
    {
    }

    public OrganisationUser(Organisation organisation, User user)
    {
        this.organisation = organisation;
        this.user = user;
    }


    public OrganisationUserSourceType getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(OrganisationUserSourceType sourceType)
    {
        this.sourceType = sourceType;
    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (null == obj)
        {
            return false;
        }
        else if (!(obj instanceof OrganisationUser))
        {
            return false;
        }
        else if (obj == this)
        {
            return true;
        }
        else
        {
            return isEquals((OrganisationUser) obj);
        }
    }

    private boolean isEquals(OrganisationUser organisationUser)
    {
        if (organisationUser.getOrganisation().getUuid().compareTo(organisation.getUuid()) == 0
                && organisationUser.getUser().getUuid().compareTo(user.getUuid()) == 0)
        {
            return true;
        }
        return false;
    }
}
