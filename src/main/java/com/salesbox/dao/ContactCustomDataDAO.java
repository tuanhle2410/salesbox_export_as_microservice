package com.salesbox.dao;

import com.salesbox.entity.Contact;
import com.salesbox.entity.ContactCustomData;
import com.salesbox.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/5/14
 * Time: 4:55 PM
 */
public interface ContactCustomDataDAO extends BaseDAO<ContactCustomData>
{
    ContactCustomData findOne(UUID uuid);

    public ContactCustomData findByContactAndUser(Contact contact, User user);

    public List<ContactCustomData> findByContactInAndUser(List<Contact> contactList, User user);

    public List<ContactCustomData> findByContactIn(List<Contact> contactList);

    public List<ContactCustomData> findByUserAndUpdatedDateAfterOrderByUuidAsc(User user, Date updatedDate, Pageable pageable);

    int getMaxByUserAndFavorite(User user, boolean favorite);

    public List<ContactCustomData> findByUserAndContactUuidInOrderByUuidAsc(User user, List<UUID> uuids);

    void removeWhereUserIn(List<User> userList);

    void removeWhereContactIn(List<Contact> contactList);

    List<ContactCustomData> findByContactInOrUser(List<Contact> contactList, User user);

    void moveOwner(User oldUser, User newUser);

    void removeByUser(User oldUser);

    List<ContactCustomData> findByRecentActionTypeIsNotNull(Integer pageIndex, Integer pageSize);

    List<UUID> findContactIdByUserAndUpdatedDESC(User user, Pageable pageable);
}
