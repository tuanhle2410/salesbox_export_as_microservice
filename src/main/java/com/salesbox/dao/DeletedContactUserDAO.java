package com.salesbox.dao;

import com.salesbox.entity.Contact;
import com.salesbox.entity.DeletedContactUser;
import com.salesbox.entity.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by luult on 10/6/2014.
 */
public interface DeletedContactUserDAO extends BaseDAO<DeletedContactUser>
{
    public List<UUID> findByUserAndUpdatedDate(User user, Date updatedDate);
   
    DeletedContactUser findByContactAndUser(Contact contact, User user);
    
    List<DeletedContactUser> findByContactInAndUserIn(List<Contact> contactList, List<User> users);
    
    DeletedContactUser findByContactInAndUser(List<Contact> contact, User user);

    void removeWhereContactIn(List<Contact> contactList);

    void deletByContactIn(List<Contact> contactList);
}
