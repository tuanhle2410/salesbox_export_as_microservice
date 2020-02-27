package com.salesbox.dao;

import com.salesbox.entity.CallList;
import com.salesbox.entity.CallListSharedContactCommunication;
import com.salesbox.entity.CommunicationHistory;
import com.salesbox.entity.SharedContact;
import com.salesbox.entity.enums.CommunicationHistoryType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hungnh on 4/22/15.
 */
public interface CallListSharedContactCommunicationDAO extends BaseDAO<CallListSharedContactCommunication>
{
    List<Object[]> findUuidAndCallListIdAndCommunicationTypeAndSharedContactIdByCallListIn(List<CallList> callListList);

    Long countCommunicationHistoryByCallListIdAndSharedContactIdAndCommunicationHistoryTypeIn(UUID callListId, UUID sharedContactId, List<CommunicationHistoryType> communicationHistoryTypeList);

    List<Object[]> findUuidAndSharedContactIdAndCommunicationTypeByCallListAndSharedContactIdIn(CallList callList, List<UUID> sharedContactIdList);

    void removeWhereCallList(CallList callList);

    void removeWhereCallListAndSharedContactIn(CallList callList, List<SharedContact> sharedContactList);

    List<CommunicationHistory> findCommunicationHistoryByCallListAndAndSharedContactAndStartDateAfter(CallList callList, SharedContact sharedContact, Date callListCreatedDate);

    void removeWhereCallListIn(List<CallList> callListList);

    void removeWhereCommunicationHistoryIn(List<CommunicationHistory> communicationHistoryList);

    List<CallListSharedContactCommunication> findAllOrderByCommunicationHistoryStartDateAsc();

    long countSharedContactIdByCallListAndCommunicationTypeIn(CallList callList, List<CommunicationHistoryType> communicationHistoryTypeList);

    List<CallListSharedContactCommunication> findAllNumberCall(List<CommunicationHistoryType> historyTypeList);

    Integer deleteByCallListContactIdAndContatId(UUID callListContactId, UUID contactId);
}
