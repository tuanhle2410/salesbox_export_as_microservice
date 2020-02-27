package com.salesbox.dao;

/**
 * User: luult
 * Date: 5/22/14
 */

import com.salesbox.entity.Contact;
import com.salesbox.entity.ProspectContact;
import com.salesbox.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProspectContactDAO extends BaseDAO<ProspectContact>
{
    List<ProspectContact> findByProspectOrderByIndexAsc(UUID prospectId);

    List<ProspectContact> findByProspectInOrderByIndexAsc(Collection<UUID> prospectIdList);

    List<Object[]> findPropertiesByProspectIdInOrderByIndexAsc(Set<UUID> prospectList);

    ProspectContact findOne(UUID uuid);

    void deleteInBatch(List<ProspectContact> prospectContactList);

    List<UUID> findProspectIdByContacts(List<UUID> contactIds);

    //todo: need to fix, prospect table alredy be removed
    List<ProspectContact> findByContactAndUserAndWonIsNull(UUID contactId);

    long countByContactAndUserAndWonIsNull(UUID contactId);

    List<UUID> findByContactIdAndWonIsNull(UUID contactId);

    List<Contact> findContactByProspectIdOrderByIndexAsc(UUID prospectId);
    
    List<Contact> findContactByProspectInOrderByIndexAsc(List<UUID> prospectIds);

    void removeWhereProspectIn(List<UUID> prospectIds);

    void removeWhereProspectInAndContact(List<UUID> prospectIds, Contact contact);

    List<UUID> findSharedContactIdByProspectOwnerIn(List<User> userList);

    List<ProspectContact> findByProspectIDInOrContactIn(List<UUID> prospectIds, List<Contact> contactList);

    List<ProspectContact> findByContactIn(List<Contact> contactList);

    List<UUID> findProspectByListContact(List<Contact> contacts);

    List<UUID> findProspectActiveByListContact(List<Contact> contacts);

    void deleteByContactIn(List<Contact> deletedContactList);
}
