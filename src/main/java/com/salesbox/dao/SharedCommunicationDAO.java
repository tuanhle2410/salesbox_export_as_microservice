package com.salesbox.dao;

import com.salesbox.entity.SharedCommunication;
import com.salesbox.entity.SharedContact;
import com.salesbox.entity.enums.CommunicationType;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


public interface SharedCommunicationDAO extends BaseDAO<SharedCommunication>
{
    SharedCommunication findOne(UUID uuid);

    SharedCommunication findBySharedContactAndTypeAndIsMain(SharedContact sharedContact, CommunicationType type, String value);

    List<SharedCommunication> findByTypeInAndSharedContact(List<CommunicationType> communicationTypeList, SharedContact sharedContact);

    List<SharedCommunication> findByTypeInAndSharedContactIn(List<CommunicationType> communicationTypeList, List<SharedContact> sharedContactList);

    List<SharedCommunication> findByTypeInAndSharedContactNotIsMain(List<CommunicationType> communicationTypeList, SharedContact sharedContact);

    SharedCommunication findByValueAndTypeInAndSharedContactIsNotNull(String value, List<CommunicationType> communicationTypeList);

    List<SharedCommunication> findByValueInAndTypeInAndSharedContactIsNotNull(List<String> valueList, List<CommunicationType> communicationTypeList);

    List<SharedContact> findSharedContactByValueInAndTypeInAndSharedContactIsNotNull(List<String> valueList, List<CommunicationType> communicationTypeList);

    List<SharedCommunication> findByValueInAndTypeInAndSharedContactIsNot(List<String> valueList, List<CommunicationType> communicationTypeList, SharedContact sharedContact);

    List<SharedCommunication> findByValueInAndTypeInAndSharedContactIsNotNull(String email, List<CommunicationType> communicationTypeList);

    long countByValueInAndTypeInAndSharedContactNotNull(List<String> valueList, List<CommunicationType> typeList);

    public void removeWhereSharedContact(SharedContact sharedContact);

    public void removeWhereSharedContactAndIsMain(SharedContact sharedContact);

    public void removeWhereSharedContactAndTypeIn(SharedContact sharedContact, List<CommunicationType> communicationTypeList);

    public void removeWhereSharedContactIn(List<SharedContact> sharedContactList);

    List<SharedCommunication> findBySharedContact(SharedContact sharedContact);

    List<SharedCommunication> findBySharedContactAndNotPrivate(SharedContact sharedContact);

    List<SharedCommunication> findBySharedContactAndTypeInAndNotPrivate(SharedContact sharedContact, List<CommunicationType> communicationTypeList);

    SharedContact findSharedContactByValueAndTypeInAndSharedContactIsNotNull(String value, List<CommunicationType> communicationTypeList);

    List<SharedCommunication> findBySharedContactAndTypeIn(SharedContact sharedContact, List<CommunicationType> communicationTypeList);

    List<SharedCommunication> findAll();

    List<SharedCommunication> findByTypeInAndValueInAndNotContact(List<String> valueList, List<CommunicationType> communicationTypeList, SharedContact sharedContact);

    List<Object[]> findValueAndSharedContactIdByValueInAndTypeInAndSharedContactIsNotNull(List<String> valueList, List<CommunicationType> typeList);

    List<Object[]> findSharedContactIdWithSharedCommunicationBySharedContactIn(List<SharedContact> sharedContactList);

    List<Object[]> findContactInviteeAppointmentPropertyByValueIn(Collection<String> values);

    List<Object[]> findValueAndSharedContactByValueInAndTypeIn(List<String> valueList, List<CommunicationType> communicationTypeList);

    long countByValueInAndTypeInAndSharedContactIsNot(List<String> valueList, List<CommunicationType> communicationTypeList, SharedContact sharedContact);

    List<SharedContact> findSharedContactByValueInAndTypeInAndSharedContactIsNotNullAndSharedContactIdIsNot(List<String> valueList, List<CommunicationType> typeList, UUID sharedContactId);

    List<Object[]> findValueAndSharedContactByValueInAndTypeInAndSharedContactIsNotNull(List<String> valueList, List<CommunicationType> communicationTypeList);

    List<String> findValueBySharedContactIn(List<SharedContact> sharedContactList);

    long countByValueInAndTypeIn(List<String> emailContactList, List<CommunicationType> contactAdditionalEmailTypes);

    List<SharedCommunication> findByContactInAndTypeInAndMain(List<SharedContact> sharedContactList, List<CommunicationType> communicationTypeList, boolean main);
}
