package com.salesbox.dao;

import com.salesbox.entity.*;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/5/14
 * Time: 4:16 PM
 */
public interface ContactUserDAO extends BaseDAO<ContactUser>
{
    ContactUser findOne(UUID uuid);

    public ContactUser findByContactAndUser(Contact contact, User user);

    public List<ContactUser> findByUser(User user);

    public List<UUID> findContactIdByUserAndUpdatedDateAfterOrderByUuidAsc(User user, Date updatedDate, Pageable pageable);

    public List<Contact> findByUserIn(List<User> userList);

    public List<UUID> findContactIdByUnitAndUpdatedDateAfterOrderByUuidAsc(Unit unit, Date updatedDate, Pageable pageable);

    public List<User> findUserByContact(Contact contact);
    
    List<User> findUserByContactIn(List<Contact> contactList);

    public List<ContactUser> findByContact(Contact contact);

    public List<ContactUser> findByContactIn(List<Contact> contactList);

    public List<ContactUser> findByContactList(List<Contact> contactList);

    public List<ContactUser> findByContactInAndUser(List<Contact> contactList, User user);


    Long countNumberContactUserAddedFromPool();

    List<UUID> findSharedContactIdByUser(User user);

    List<SharedContact> findSharedContactByUserInAndContactCreatedDateBeforeAndSharedContactUUIDNotInAndSharedContactUUIDIn(List<User> userList, Date date, List<UUID> excludedSharedContactUuidList, List<UUID> includedSharedContactUuidList);

    List<Object[]> findSharedContactIdAndContactIdAndOrganisationIdByUserAndSharedContactIdIn(User user, List<UUID> sharedContactUUIDList);

    Contact findContactBySharedContactAndUser(SharedContact sharedContact, User user);

    List<Contact> findContactBySharedContactListAndUser(Collection<SharedContact> sharedContacts, User user);

    List<Contact> findContactBySharedContactIdInAndUser(Collection<UUID> sharedContacts, User user);

    List<Contact> findContactByUserListAndDeletedAndFilterByTimeOrderByCondition(List<User> userList, Pageable pageable, String orderBy);

    List<Contact> findByUserInAndFilterByCustomFilter(List<User> users, User user, Pageable pageable, String customFilter);

    List<Object[]> findUserAndUserSharedContactByContact(Contact contact);

    void removeWhereContactIn(List<Contact> contactList);

    void removeListContactUser(List<ContactUser> contactUserList);

    void removeWhereContactInAndUser(List<Contact> contactList, User user);

    List<UUID> findUserIdByContact(Contact contact);

    List<Contact> findContactByUserIn(List<User> userList);

    List<SharedContact> findSharedContactByUserInAndContactCreatedDateBeforeAndSharedContactUUIDIn(List<User> userList, Date date, List<UUID> sharedContactIdList);

    List<Contact> findContactByUserAndContactIn(User user, List<Contact> contactList);

    List<Object[]> findUserIdByContactIn(List<Contact> contacts);

    List<Contact> findContactByUser(User user);

    List<ContactUser> findByContactInOrUser(List<Contact> contactList, User user);

    void moveUser(User oldUser, User newUser);

    List<UUID> findContactIdByContactIdInAndUser(List<UUID> contactIds, User user);

}
