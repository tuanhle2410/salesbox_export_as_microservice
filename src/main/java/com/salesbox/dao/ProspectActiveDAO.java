package com.salesbox.dao;

import com.salesbox.dto.ProspectResultSet;
import com.salesbox.entity.*;
import com.salesbox.entity.enums.SalesMethodActivityType;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/21/14
 * Time: 4:49 PM
 */
public interface ProspectActiveDAO extends BaseDAO<ProspectActive>
{
    ProspectActive findOne(UUID uuid);

    List<ProspectActive> findByEnterpriseAndUpdatedDateAfterOrderByUuidAsc(Enterprise enterprise, Date updatedDate, Pageable pageable);

    List<ProspectActive> findByOrganisation(Organisation organisation);

    List<ProspectActive> findByUuidIn(List<UUID> prospectIdList);

    List<ProspectActive> findByUuidInAndDecr(List<UUID> prospectIdList);

    List<ProspectActive> findByOwnerIn(UUID ownerId);

    List<ProspectActive> findAll();

    List<ProspectActive> findByEnterprise(Enterprise enterprise);

    Set<ProspectActive> findActiveByPowerSponsor(Contact contact);

    List<ProspectActive> findProspectActiveByUserInAndContractDateBetween(List<User> userList, Date startDate, Date endDate);

    List<ProspectActive> findProspectActiveByUserInAndContractDateAfterNow(List<User> userList, Date startDate, Pageable pageable);

    List<ProspectActive> findByUserIn(List<User> userList);

    List<UUID> findIdByUserIn(List<User> userList);

    Long countNumberActiveProspectByOrganisation(Organisation organisation);

    Long countNumberActiveProspectByContact(Contact contact);

    Long countActiveByOwnerInAndContractDateBetween(List<User> userList, Date startDate, Date endDate);

    Map<UUID, Long> getUserAndCountActiveProspectByNotMarkedAndContractDateBetween(Enterprise enterprise, SalesMethodActivityType type, Date startDate, Date endDate);

    Long countActiveByUnitIdAndContractDateBetween(UUID currentUnitId, Date startDate, Date endDate);

    Long countNumberActiveProspectByOwnerInAndContractDateBetween(List<User> userList, Date startDate, Date endDate);

    Long countNumberActiveProspectByLineOfBusiness(LineOfBusiness lineOfBusiness);

    Set<ProspectActive> findByPowerSponsor(Contact contact);

    Set<ProspectActive> findByPowerSponsorIn(Collection<Contact> contacts);

    List<ProspectActive> findBySalesMethod(SalesMethod salesMethod);

    Long countBySalesMethod(SalesMethod salesMethod);

    Long countByEnterprise(Enterprise enterprise);

    List<ProspectActive> findByNowAfterEndDateAndOrderByUuid(Date now, Integer pageIndex, Integer pageSize);

    List<Object[]> findByWonAndPage(int pageIndex, int pageSize);

    List<Object[]> findByPageAndCreatedDateBetween(Date startDate, Date endDate, int pageIndex, int maxPageSize);

    List<ProspectActive> findByIds(Collection<UUID> uuids);

    List<ProspectActive> findByOwnerIn(List<User> userList);

    void removeWhereProspectIn(List<UUID> prospectList);

    List<ProspectActive> findByOwnerOrContactIn(User user, List<Contact> contactList);

    void moveProspect(User oldUser, User newUser);

    List<ProspectActive> findByContactIn(List<Contact> deletedContactList);

    List<ProspectActive> findByNextStepNull();

    List<ProspectActive> findByContactListAndOrganisationAndOwner(List<Contact> contacts, Organisation organisation, User user);

    List<ProspectActive> findByContactInAndNotVisitMoreAndLastSyncTimeNullOrBeforeSyncTime(List<Contact> contactList, Date lastSyncDate);

    List<ProspectActive> findByOrganisationIn(List<Organisation> organisationList);

    List<ProspectActive> findByOrganisationInAndNotVisitMoreAndLastSyncTimeNullOrBeforeSyncTime(List<Organisation> organisationList, Date lastSyncDate);

    List<ProspectActive> findAllByPage(int pageIndex, int pageSize);

    List<ProspectResultSet> findProspectActive(String stString);

    List<UUID> findUUIDByOrganisation(Organisation organisation);

    List<ProspectActive> getActiveProspectByContactId(UUID contactid);
    
    List<ProspectActive> findByIdsAndActiveTaskAndActiveMetting(Collection<UUID> uuids);

    List<UUID> getActiveProspectIdsByContactId(UUID contactId);

    List<Object[]> findActiveByCurrentAndFutureFiscalYear(List<User> userList, Date startDateCurrentYear);

    Long countNumberPrioritizedProspect(User user, Double priorityLevel, Date startDateCurrentYear, Date endDateCurrentYear);

    Long countNumberPrioritizedProspectAndUserIn(List<User> userList, Double priorityLevel, Date startDateCurrentYear, Date endDateCurrentYear);

    List<UUID> findProspectHavingPrioritizedTask(List<UUID> uuids);
}
