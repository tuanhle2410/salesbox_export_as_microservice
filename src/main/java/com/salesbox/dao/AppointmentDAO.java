package com.salesbox.dao;

import com.salesbox.entity.*;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * Created by hungnh on 15/07/2014.
 */
public interface AppointmentDAO extends BaseDAO<Appointment>
{
    List<Appointment> findAll();

    Appointment findOne(UUID uuid);

    List<Appointment> findByEnterpriseAndUpdatedDateAfterOrderByUuidAsc(Enterprise enterprise, Date updatedDate, Pageable pageable);

    List<Appointment> findByOrganisationAndNotFinishAndOrderByStartDateAsc(Organisation organisation);

    List<Appointment> findNoteByOrganisation(Organisation organisation);

    Integer countNoteByOrganisation(Organisation organisation);

    List<Appointment> findByOrganisationInAndNotFinishAndOrderByStartDateAsc(List<Organisation> organisationList);

    List<Appointment> findByProspectAndFinishedAndDeletedOrderByStartDate(UUID prospectId, Boolean finished, Boolean deleted);

    List<Appointment> findByProspectAndDeletedOrderByStartDate(ProspectBase prospect, Boolean deleted);
    
    List<Appointment> findByProspectIdInAndDeletedOrderByStartDate(List<UUID> prospectIds, Boolean deleted);
    
    List<Appointment> findByProspectIdInAndDeletedAndOwnerNotHistoric(List<UUID> prospectIds, Boolean deleted, User owner);

    List<Appointment> findByProspectAndFinishedOrderByStartDate(ProspectBase prospect, Boolean finished, Pageable pageable);

    List<Appointment> findByOrganisationAndFinishedOrderByStartDate(Organisation organisation, Boolean finished, Pageable pageable);

    List<Appointment> findByContactAndFinishedOrderByStartDate(Contact contact, Boolean finished);

    int countByProspectAndFocusActivityAndDeletedAndNotFinished(UUID prospectId, Activity activity, Boolean deleted);

    List<Appointment> findByNotFinishedAndNowAfterEndDateAndNotDeletedOrderByUuid(Date now, Integer pageIndex, Integer pageSize);

    List<Appointment> findByOwnerAndDeletedOrderByDateCreated(User user, Boolean deleted);
    
    Map<User, List<Appointment>> findByOwnerInAndDeletedOrderByDateCreated(List<User> userList, Boolean deleted);

    List<Appointment> findByOwnerAndHasGoogleEventId(User user);

    List<Appointment> findByOwnerAndHasNoGoogleEventIdAndActive(User user);

    List<Appointment> findByOwnerAndHasOutlookEventId(User user);

    List<Appointment> findByOwnerAndHasNoOutlookEventIdAndActive(User user);

    List<Appointment> findByOwnerAndHasNoOffice365EventIdAndActive(User user);

    List<Appointment> findByOwnerAndHasOffice365EventId(User user);

    List<Appointment>  findByActiveAndListContactAndOrganisationAndProspectList(List<Contact> contacts, Organisation organisation, List<UUID> prospectUuids);

    List<Appointment> findByOwnerAndDeletedAndFinished(User user, Boolean deleted, Boolean finished);

    public List<Appointment> findByProspectAndEndDateAfterNowAndNotDeleted(UUID prospectId);

    public List<Appointment> findByProspectAndActivityInAndNotDeleted(UUID prospectId, List<Activity> activityList);

    List<Appointment> findByCreatorAndNotDeletedAndNotFinished(User creator, Integer pageIndex, Integer pageSize);

    List<Appointment> findByOwnerInAndEndDateBetweenAndFinished(List<User> userList, Date startDate, Date endDate);

    List<Appointment> findByOwnerAndBeforeNowAndNotDeletedOrderByStartDate(User user, Date now);

    Long countByOwnerInAndEndDateBetween(List<User> userList, Date startDate, Date endDate);

    Long countByCreatorAndCreatedDateBetween(User creator, Date startDate, Date endDate);

    Long countDoneByOwnerAndCreatedDateBetween(User owner, Date startDate, Date endDate);

    Long countActiveAppointmentByProspectIdIn(List<UUID> prospectIds);

    List<Appointment> findByOwnerInAndNotFinishedAndNowAfterEndDateAndNotDeleted(List<User> ownerList);

    List<Appointment> findMissingByUser(User user, Date updatedDate, Integer pageIndex, Integer pageSize);

    List<Appointment> findMissingContactOrProspectByUser(User user);

    List<Appointment> findAppointmentEndDateBetween(User user, Date fromDate, Date toDate);

    List<Appointment> findLaterAppointmentBetween(User user, Date fromDate, Date toDate);

    List<Appointment> findAppointmentNotHandledToday(User user, Date startOfDay);

    Long countActiveAppointments();

    Long countPerformedAppointments();

    List<Appointment> findByUuidIn(List<UUID> appointmentIdList);

    List<Appointment> findActiveAppointmentByOwnerAndStartDateBetweenAndNotDeleted(User user, Date startDate, Date endDate);

    Long countPerformedAppointmentsCreatedDateBetween(Date startDate, Date endDate);

    List<Object[]> findActiveAppointmentByOwnerInAndEndDateBetweenAndNotDeleted(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findEndDateAndDurationByOwnerInAndEndDateBetween(List<User> userList, Date startDate, Date endDate);

    Double getCompanyMinAverageDurationPersonalRecordByEnterpriseAndEndDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    Double getCompanyMinAverageDurationUnitRecordByEnterpriseAndEndDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    List<Object[]> findTitleAndStartDateAndOwnerIdAndNoteByEnterpriseAndEndDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    List<Object[]> findTitleAndStartDateAndOwnerIdAndNoteByOwnerInAndEndDateBetween(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findEndDateAndOwnerIdAndUnitIdByEnterpriseAndEndDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    long countActiveAppointmentByProspectIdAndFocusActivityIdAndUuidIsNot(UUID prospectId, UUID focusActivityId, UUID exceptedUuid);

    long countActiveAppointmentByProspectIdAndFocusWorkDataIdAndUuidIsNot(UUID prospectId, UUID focusWorkDataId, UUID exceptedUuid);

    Long countByOrganisationAndEndDateBetweenAndDeleted(Organisation organisation, Date startDate, Date endDate, Boolean deleted);

    List<Appointment> findByCreatorInOrOwnerIn(List<User> userList);

    void removeWhereAppointmentIn(List<Appointment> appointmentList);

    List<Appointment> findByIds(Collection<UUID> uuids, Pageable pageable);

    List<Appointment> findByIdIn(Collection<UUID> uuids);


    List<Object[]> findOrganisationAndNumberMeeting();

    List<Object[]> findOrganisationAndNumberActiveMeeting();

    List<Object[]> findProspectAndNumberMeeting();

    List<Object[]> findProspectAndNumberActiveMeeting();

    List<Appointment> findByFirstContactIn(List<Contact> contactList);

    long countActiveAppointmentByProspectIdInAndOwnerIn(Collection<UUID> prospectIds, List<User> userList);

    List<Object[]> findUserIdAndAppointmentNumberByOwnerInAndDeletedAndEndDateBetween(List<User> userList, boolean deleted, Date startDate, Date endDate);

    List<Object[]> findUuidAndContactSyncPropertiesByUuidIn(List<UUID> uuidList);

    List<Object[]> findUuidAndOrganisationByUuidIn(List<UUID> uuidList);

    List<Object[]> findUuidAndOwnerByUuidIn(List<UUID> uuidList);

    List<Object[]> findUuidAndProspectByUuidIn(List<UUID> uuidList);

    List<Object[]> findUuidAndFocusActivityByUuidIn(List<UUID> uuidList);

    List<Object[]> findUuidAndWorkDataActivityByUuidIn(List<UUID> uuidList);

    List<Appointment> findByOwnerIn(List<User> userList);

    Long countActiveAppointmentByOrganisation(Organisation organisation);

    Long countNumberAppointmentByOrganisation(Organisation organisation);

    List<Appointment> findByTimezoneLike(String queryLike);

    List<Appointment> findByOwner(User user);

    void moveOwner(User oldUser, User newUser);

    List<Appointment> findByOrganisationIn(List<Organisation> organisationList);

    List<Appointment> findByGoogleEventIdIn(List<String> allEventId, User user);

    Appointment findByOutlookEventIdAndOwner(String outlookEventId, User user);

    Appointment findByOffice365IdAndOwner(String office365EventId, User user);

    Map<UUID, Long> getUnitAndCountAppointmentByStartDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    Map<UUID, Long> getUserAndCountAppointmentByStartDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    Long countAppointmentByOwnerAndEndDateBetween(User user, Date startDate, Date endDate);

    Long countAppointmentByUnitAndEndDateBetween(Unit unit, Date startDate, Date endDate);

    Long countAppointmentByEnterpriseAndEndDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    Long getByBestRecordOfUserCompanyAndStartDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    Long getByBestRecordOfUnitCompanyAndStartDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    Long countActiveAppointmentsHasGoogleIdByUser(User user);

    Long countActiveAppointmentsHasOutlookIdByUser(User user);

    Long countActiveAppointmentsHasOffice365IdByUser(User user);

    //Find by leadId
    List<Appointment> findAllByIdList(List<UUID> uuidList);

    List<Appointment> findAllByIdListAndFinished(List<UUID> uuidList, Boolean finished);

    Long countActiveAppointmentByOrganisationList(List<Organisation> organisationList);

    Appointment findByEnterpriseAndMaestranoId(Enterprise enterprise, String maestranoId);

    List<Appointment> findTaskByOppUUIDList(List<UUID> appointmentIdList);

    List<Object[]> findDescOppWithListUUID(List<UUID> listAppointmentIdByOpp);

    List<Object[]> findLeadUUIdWithUUIDList(List<UUID> appointmentNoteList);

    List<Appointment> findAppointmentWithListUUIDAndNotLead(List<UUID> listUUIDbyAppointment);

    List<Appointment> findByContactAndUnfinished(UUID contactid);

    List<Appointment> findByContactAndDeleted(UUID contactid, Boolean deleted);

    List<Appointment> findByContactAndDeletedAndNoteIsNotNull(UUID contactid, Boolean deleted);

    List<Appointment> findByOrganisationAndDeletedAndNoteIsNotNull(Organisation organisation, Boolean deleted);

    List<Appointment> findAppointmentWithLeadId(UUID leadId);

     Integer countAppointmentWithLeadId(UUID leadId);

    List<Appointment> findAppointmentWithContact(Contact contact);


    Integer countAppointmentWithContact(Contact contact);

    List<Appointment> findNoteByProspective(ProspectBase prospectActive);

    Integer countNoteByProspective(ProspectActive prospectActive);

    List<Object[]> findAppointmentWithLeadsAll(List<UUID> leadIds);

    Integer countAppointmentByLeadIds(List<UUID> leadIds);


    List<Object[]> findTitleAndStartDateAndOwnerIdAndNoteByEnterprise(Enterprise enterprise);

    List<Object[]> findTitleAndStartDateAndOwnerIdAndNoteByOwnerIn(List<User> byUnitIdAndActive);

    List<Object[]> findFinishedByUserInAndEndDateBetween(List<User> userList, Date time, Date date);

    Long countFinishedByUserAndEndDateBetween(User user, Date time, Date date);

    Long countAppointmentByOwnerAndStartDateBetween(User user, Date startDate, Date endDate);

    Long countAppointmentByUnitAndStartDateBetween(Unit unit, Date startDate, Date endDate);

    Long countAppointmentByEnterpriseAndStartDateBetween(Enterprise enterprise, Date startDate, Date endDate);
}
