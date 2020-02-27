package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by luult on 10/6/2014.
 */
@Entity
@Table(name = "deleted_organisation_user")
public class DeletedOrganisationUser extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public DeletedOrganisationUser() {
		super();
	}

	public DeletedOrganisationUser(Organisation organisation, User user) {
		super();
		this.organisation = organisation;
		this.user = user;
	}

	public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }
}
