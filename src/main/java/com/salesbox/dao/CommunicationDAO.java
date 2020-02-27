package com.salesbox.dao;

import com.salesbox.entity.Communication;
import com.salesbox.entity.Contact;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.enums.CommunicationType;

import java.util.List;
import java.util.UUID;


public interface CommunicationDAO extends BaseDAO<Communication>
{
    List<Communication> findByTypeInAndContact(List<CommunicationType> communicationTypeList, Contact contact);

    List<Communication> findByTypeInAndContactIn(List<CommunicationType> communicationTypeList, List<Contact> contactList);

    List<Communication> findByTypeInAndContactOrderByValue(List<CommunicationType> communicationTypeList, Contact contact);

    List<Communication> findByTypeInAndContactNotIsMain(List<CommunicationType> communicationTypeList, Contact contact);

    List<Communication> findByContactIsNullAndValueIn(List<String> valueList);

    List<Communication> findByContact(Contact contact);

    List<Communication> findByContactIn(List<Contact> contactList);

    public void removeWhereContact(Contact contact);

    public void removeWhereContactAndTypeIn(Contact contact, List<CommunicationType> communicationTypeList);

    public void removeWhereContactIn(List<Contact> contactList);

    List<Communication> findByValueInAndTypeInAndContactEnterprise(List<String> valueList, List<CommunicationType> communicationTypeList, Enterprise enterprise);

    Communication findByContactAndTypeInAndIsMain(Contact contact, List<CommunicationType> communicationTypeList);

    List<Communication> findAll();

    List<Communication> findByEnterpriseAndTypeInAndValueInAndNotContact(Enterprise enterprise, List<String> valueList, List<CommunicationType> communicationTypeList, Contact contact);


    List<Object[]> findValueAndContactIdByValueInAndTypeInAndContactEnterprise(List<String> valueList, List<CommunicationType> typeList, Enterprise enterprise);

    List<Object[]> findByContactInAndTypeInAndIsMain(List<Contact> contactList, List<CommunicationType> communicationTypeList, Boolean main);

    List<Contact> findContactByValueInAndTypeInAndContactEnterpriseAndDeleted(List<String> valueList, List<CommunicationType> typeList, Enterprise enterprise, boolean deleted);

    List<Contact> findContactByValueInAndTypeInAndContactEnterpriseAndDeletedAndNoContact(List<String> valueList, List<CommunicationType> typeList, Enterprise enterprise, boolean deleted);

    List<Contact> findContactByValueInAndTypeInAndContactEnterpriseAndDeletedAndFirstnameAndLastname(List<String> valueList, List<CommunicationType> typeList, Enterprise enterprise, boolean deleted, String firstName, String lastName);

    Contact findContactByValueAndTypeInAndContactEnterpriseAndDeleted(String value, List<CommunicationType> typeList, Enterprise enterprise, boolean deleted);

    List<String> findValueByContactIn(List<Contact> contactList);

    List<Communication> findByContactInAndTypeInAndMain(List<Contact> contactList, List<CommunicationType> communicationTypeList, boolean main);

    List<Communication> findByCreatorId(UUID uuid);

    void moveOwnerId(UUID oldUserId, UUID newUserId);

    void deleteByContactIn(List<Contact> deletedContactList);
}
