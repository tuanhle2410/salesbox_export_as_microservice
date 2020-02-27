package com.salesbox.dao;

import com.salesbox.dto.TaskPaginateBean;
import com.salesbox.entity.*;
import com.salesbox.entity.enums.TaskType;
import com.salesbox.entity.task.TaskView;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * User: luult
 * Date: 5/21/14
 */
public interface TaskDAO extends BaseDAO<Task>
{
    Task findOne(UUID uuid);

    List<Task> findByProspectIdAndDeletedAndFinishedOrderByDateAndTime(UUID prospectId, Boolean deleted, Boolean finished);

    List<Task> findByOwnerIn(List<User> userList);

    List<Task> findByOwnerInAndPageable(List<User> userList, Integer pageIndex, Integer pageSize);

    List<Task> findByEnterpriseAndUpdatedDateAfterOrderByUuidAsc(Enterprise enterprise, Date updatedDate, Pageable pageable);

    List<Task> findByProspectAndFocusActivityAndDeletedAndFinished(UUID prospectId, Activity activity, Boolean deleted, Boolean finished);

    List<Task> findByProspectAndFocusActivityInAndDeletedAndFinished(UUID prospectId, List<Activity> activityList, Boolean deleted);

    List<Task> findByProspectInAndFocusActivityAndDeletedAndFinished(Collection<ProspectHistoric> prospectList, Activity activity, Boolean deleted, Boolean finished);
    
    List<Task> findByProspectIdInAndDeletedAndFinishedAndOwner(List<UUID> prospectIds, Boolean deleted, Boolean finished, User owner);

    List<Task> findByProspectAndTypeAndDeletedAndFinished(ProspectBase prospect, TaskType type, Boolean deleted, Boolean finished);

    List<Task> findByProspectAndDeletedAndFinished(UUID prospectId, Boolean deleted, Boolean finished);

    List<Task> listByProspectAndYearAndFinishedAndConditions(ProspectBase prospect, Boolean finished, Integer year, String orderBy, Pageable pageable);

    List<Task> findByProspectAndOwnerAndDeletedAndFinishedAndTypeNot(UUID prospectId, User user, Boolean deleted, Boolean finished, TaskType taskType);

    List<Task> findByOrganisationAndDeletedAndFinishedOrderByDateAndTime(Organisation organisation, Boolean deleted, Boolean finished);

    List<Task> findByOrganisationAndDeletedAndFinished(Organisation organisation, Boolean deleted, Boolean finished);

    List<Task> findByOrganisationInAndDeletedAndFinishedAndDateAndTimeAfterNowOrderByDateAndTime(List<Organisation> organisationList, Boolean deleted, Boolean finished);

    List<Task> findByContactAndDeletedAndFinishedOrderByDateAndTime(Contact contact, Boolean deleted, Boolean finished);
    
    List<Task> findByContactInAndDeletedAndFinishedOrderByDateAndTime(List<Contact> contactList, Boolean deleted, Boolean finished);

    List<Task> findByOwnerAndTypeNotAndDeletedAndFinished(User user, TaskType type, Boolean deleted, Boolean finished);

    List<Task> findByProspectAndFocusWorkDataAndDeletedAndFinished(UUID prospectId, WorkDataActivity focusWorkData, Boolean deleted, Boolean finished);

    Long countActiveTasks();

    Long countPerformedTasks();

    Integer countActiveTaskByOwnerAndDateAndTimeBetween(User user, Date startDate, Date endDate);

    List<Object[]> countActiveTaskByOwnerInAndDateAndTimeBetween(List<User> userList, Date startDate, Date endDate);

    Integer countActiveTaskByUnitAndDateAndTimeBetween(Unit unit, Date startDate, Date endDate);

    List<Object[]> countActiveTaskByUnitInAndDateAndTimeBetween(List<Unit> unitList, Date startDate, Date endDate);

    Integer countActiveTaskByProspectAndDateAndTimeBetween(ProspectBase prospect, Integer year);

    List<Task> findByOwnerInAndFinishedAndNotDeletedAndDateTimeBetWeen(List<User> userList, Date startDate, Date endDate);

    List<Task> findByOwnerInAndFinishedAndNotDeletedAndDateTimeBetWeenAndPageable(List<User> userList, Date startDate, Date endDate, Integer pageIndex, Integer pageSize);

    List<Task> findActiveTaskByOwnerAndDateTimeBetweenAndNotDeleted(User user, Date startDate, Date endDate);

    Long countActiveTasksCreatedDateBetween(Date startDate, Date endDate);

    Long countPerformedTasksCreatedDateBetween(Date startDate, Date endDate);

    Integer countByProspectAndDateAndTimeBetween(ProspectBase prospect, Integer year);

    List<Object[]> findActiveTaskByOwnerInAndDateTimeBetweenAndNotDeleted(List<User> userList, Date startDate, Date endDate);

    List<Task> findCallBackTaskByCreatorAndContactAndFinishedAndDeleted(User user, Contact contact, boolean finished, boolean deleted);

    List<Task> findCallBackTaskByCreatorAndOrganisationAndFinishedAndDeleted(User user, Organisation organisation, boolean finished, boolean deleted);

    int countByOrganisationAndDateAndTimeBetween(Organisation organisation, Integer year);

    int countActiveTaskByOrganisationAndDateAndTimeBetween(Organisation organisation, Integer year);

    long countActiveTaskByOrganisation(Organisation organisation);

    List<Task> listByOrganisationAndYearAndFinishedAndOrderByDateAndTime(Organisation organisation, boolean finished, Integer year, String orderBy, Pageable pageable);

    List<Task> listByContactAndYearAndFinishedAndConditions(Contact contact, boolean finished, Integer year, String orderBy, Pageable pageable);

    int countActiveTaskByContactAndDateAndTimeBetween(Contact contact, Integer year);

    long countActiveTaskByContact(Contact contact);

    int countTotalTaskByContactAndDateAndTimeBetween(Contact contact, Integer year);

    void removeWhereOwnerInOrCreatorIn(List<User> userList);

    List<Task> findByIds(Collection<UUID> uuids);

    List<Object[]> findUuidAndContactSyncPropertiesByUuidIn(List<UUID> taskIdList);

    List<Object[]> findUuidAndProspectSyncPropertiesByUuidIn(List<UUID> taskIdList);

    List<Object[]> findUuidAndOtherSyncPropertiesByUuidIn(List<UUID> taskIdList);

    List<Object[]> findUuidAndOwnerSyncPropertiesByUuidIn(List<UUID> taskIdList);

    List<Object[]> findUuidAndCreatorSyncPropertiesByUuidIn(List<UUID> taskIdList);

    List<Task> findAllTaskHasNoneTag();

    List<Task> findActiveByCreatorAndNotNullOrganisation(User user);

    List<Task> findByActiveAndNotNullOrganisation();

    List<Task> findByActiveAndListContactAndOrganisation(List<Contact> contacts, Organisation organisation, List<UUID> prospectUuids);

    Long countActiveTaskByProspectId(UUID prospectId);

    List<Task> findByOwnerOrContactIn(User user, List<Contact> contacts);

    Task findByEnterpriseAndMaestranoId(Enterprise enterprise, String maestranoId);

    void moveOwner(User oldUser, User newUser);

    void moveCreator(User oldUser, User newUser);

    List<Task> findByContactInAndDeleted(List<Contact> contactList, boolean deleted);

    List<Task> findByContactInAndDeletedAndNoteIsNotNull(List<Contact> contactList, boolean deleted);

    List<Task> findByOrganisationInAndDeletedAndNoteIsNotNull(List<Organisation> organisationList, boolean deleted);

    List<Task> findByOrganisationInAndDeleted(List<Organisation> organisationList, boolean deleted);

    List<Date> findDoneByEnterprise(Enterprise enterprise);

    List<Date> findDoneByUser(User user);

    List<Date> findDoneByUserIn(List<User> userList);

    Long countDoneByUserAndDateAndTimeBetween(User user, Date startDate, Date endDate);

    Long countDoneByUnitAndDateAndTimeBetween(Unit unit, Date startDate, Date endDate);

    Long countDoneByEnterpriseAndDateAndTimeBetween(Enterprise enterprise, Date startDate, Date endDate);

    Long getByBestRecordOfUserCompanyAndStartDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    Long getByBestRecordOfUnitCompanyAndStartDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    Map<UUID, Long> getUUIDAndNumberDoneTask(Enterprise enterprise, Date startDate, Date endDate);

    List<Date> findDoneByUnitId(UUID currentUnitId);

    Map<UUID, Long> getUnitIdAndNumberDoneTaskByEnterprise(Enterprise enterprise, Date startDate, Date endDate);

    List<Task> findByEnterpriseAndGoogleTaskIdNotNull(Enterprise enterprise, Pageable pageable);

    List<Task> findByUserAndGoogleTaskIdNotNull(User user);

    List<Task> findByEnterpriseAndGoogleTaskIdIn(Enterprise enterprise, List<String> taskIds);

    List<String> findGoogleIdByEnterpriseAndGoogleIdIn(Enterprise enterprise, List<String> googleIdList);

    long countByUserHasGoogleId(User user);

    List<Task> findAllByUuidList(List<UUID> uuidList, Boolean isFinished);

    List<TaskView> getTasks(TaskPaginateBean taskPaginateBean);

    List<Task> findActiveTaskByOwnerAndDateTimeBetweenAndNotDeletedOrderDateTime(User user, Date startDate, Date endDate);

    List<Date> findDateTimeActiveTaskByOwnerAndTimeRangeAndNotDeletedOrderDateTime(User user, Date startDate, Date endDate);

    List<Task> findAllByUserAndDeleted(User user, Boolean deleted);

    List<Task> findNoteByOrganisation(Organisation organisation);

    Integer countNoteByOrganisation(Organisation organisation);

    List<Task> findTaskByOppUUIDList(List<UUID> prospectIdList);

    List<Task> findTaskByOppUUIDListAnDeletedAndFinished(List<UUID> prospectIdList);

    List<Object[]> findDescOppWithListUUID(List<UUID> listTaskIdByOpp);

    List<Object[]> findLeadUUIdWithUUIDList(List<UUID> listUUIDbyTask);

    List<Task> findTaskWithListUUIDAndNotLead(List<UUID> listUUIDbyTask);

    List<Task> findTaskWithLeadId(UUID leadId);

    Integer countTaskWithLeadId(UUID leadId);

    List<Task> findTaskWithContact(Contact contact);

    Integer countTaskWithContact(Contact contact);

    List<Task> findByProspectAndDeletedAndFinishedNoteNotNull(UUID prospectId, Boolean deleted, Boolean finished);

    List<Task> findByProspectAll(UUID prospectId);

    Integer countByProspectAndDeletedAndFinishedNoteNotNull(UUID prospectId, Boolean deleted, Boolean finished);

    List<Object[]> findTaskWithLeadsAll(List<UUID> leadIds);

    Integer countAllByProspect(UUID uuid);

    Integer countPriorityTaskByProspect(UUID uuid);

    Integer countTaskByLeadIds(List<UUID> leadIds);


    List<Task> findByIdsAndConditions(Collection<UUID> uuids, Boolean deleted, Boolean finished, Boolean userExisted);

    List<Object[]> findNoteByTaskIdIn(Set<UUID> taskIdList);

    List<Object[]> findNoteByLeadIdIn(Set<UUID> taskIdList);

    List<Task> findActiveTaskWithOwnerByCurrentWeek(User user, Date start, Date end);

    List<Task> findTaskInDelegationByCurrentWeek(Enterprise enterprise, Date start, Date end);
}
