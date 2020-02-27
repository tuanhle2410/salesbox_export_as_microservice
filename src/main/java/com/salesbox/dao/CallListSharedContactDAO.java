package com.salesbox.dao;

import com.salesbox.entity.CallList;
import com.salesbox.entity.CallListSharedContact;
import com.salesbox.entity.SharedContact;
import com.salesbox.entity.Task;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hunglv on 7/23/14.
 */
public interface CallListSharedContactDAO extends BaseDAO<CallListSharedContact>
{
    CallListSharedContact findByCallListAndSharedContact(CallList callList, UUID contactId);

    List<CallListSharedContact> findBContactIn(List<UUID> contactIds);

    List<UUID> findSharedContactIdByCallListId(UUID callListId);

    List<Object[]> findSharedContactIdAndCallBackTimeAndLatestCallAndLatestDialByCallListAndUpdatedDateAfterOrderBySharedContactIdAsc(CallList callList, int pageIndex, int pageSize, Date updatedDate);

    void removeWhereCallList(CallList callList);

    List<CallListSharedContact> findByCallListInAndSharedContact(List<CallList> callListList, UUID contactId);

    CallListSharedContact findByCallListIdAndSharedContactId(UUID callListId, UUID contactId);

    void removeWhereCallListAndSharedContactIn(CallList callList, List<SharedContact> sharedContactList);

    void removeWhereCallListIdAndContactId(UUID callListId, UUID contactID);

    List<SharedContact> findSharedContactByCallListIdAndSharedContactIdIn(UUID callListId, List<UUID> sharedContactIdList);

    void updateUpdatedDateByCallListIdInAndSharedContactIdIn(List<UUID> callListIdList, List<UUID> contactIdList);

    List<Task> findTaskByCallListIdAndSharedContactIdIn(UUID callListId, List<UUID> sharedContactIdList);

    void removePrioritizedInfoByCallListIdAndSharedContactIdIn(UUID callListId, List<UUID> sharedContactIdList);

    void removePrioritizedInfoByTask(Task task);
    
    void removePrioritizedInfoByTaskIn(List<Task> taskList);

    void updateUpdatedDateByCallListIdInAndSharedContactId(List<UUID> callListIdList, UUID sharedContactId);

    void removeWhereCallListIn(List<CallList> callListList);

    Object[] findCallListIdAndSharedContactIdByTask(Task task);

    long countByCallList(CallList callList);

    //count by a,b,c (unaccent & lower case character)
    long countByCallListAndStartWith(CallList callList, String startWith);


    List<CallListSharedContact> findByCallListOrderBySharedContactNameAsc(CallList callList, int pageIndex, int pageSize);

    List<CallListSharedContact> findPrioritizedByCallListOrderByCallBackAsc(CallList callList, int pageIndex, int pageSize);

    List<CallListSharedContact> findByCallList(CallList callList, Integer pageIndex, Integer pageSize, String orderQuery);

    List<CallListSharedContact> findAll();

    List<CallListSharedContact> findByCallListAndSharedContactIn(CallList callList, List<SharedContact> sharedContactList);

    List<CallListSharedContact> findByContactIdAndIsFinished(UUID contactId, Boolean isFinished);
}
