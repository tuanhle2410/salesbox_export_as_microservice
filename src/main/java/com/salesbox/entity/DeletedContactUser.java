package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by luult on 10/6/2014.
 */
@Entity
@Table(name = "deleted_contact_user")
public class DeletedContactUser extends BaseEntity
{

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    public DeletedContactUser() {
		super();
	}

	public DeletedContactUser(Contact contact, User user) {
    	super();
    	this.contact = contact;
    	this.user = user;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
