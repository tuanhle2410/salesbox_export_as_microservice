package com.salesbox.dao;

import com.salesbox.entity.*;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/21/14
 * Time: 4:49 PM
 */
public interface ProspectHistoricDAO extends BaseDAO<ProspectHistoric>
{
    ProspectHistoric findOne(UUID uuid);

    List<ProspectHistoric> findByEnterpriseAndUpdatedDateAfterOrderByUuidAsc(Enterprise enterprise, Date updatedDate, Pageable pageable);

    List<ProspectHistoric> findByOrganisation(Organisation organisation);

    List<ProspectHistoric> findByUuidIn(List<UUID> prospectIdList);

    List<ProspectHistoric> findByOwnerIn(UUID ownerId);

    Set<ProspectHistoric> findHistoricByPowerSponsor(Contact contact);

    List<ProspectHistoric> findByEnterprise(Enterprise enterprise);

    List<ProspectHistoric> findByWon(Boolean won);

    List<Object[]> findWonAndWonLostDateBetween(Date startDate, Date endDate);

    List<Object[]> findWonByUserInAndWonLostDateBetween(List<User> userList, Date startDate, Date endDate);

    Long countWonByUserAndWonLostDateBetween(User user, Date startDate, Date endDate);

    List<Object> findGrossValueAndWonAndOwnerLostDateBetween(User owner, Date startDate, Date endDate);

    List<Object[]> findWonLostDateAndCreatedDateAndWonAndOwnerLostDateBetween(User owner, Date startDate, Date endDate);

    List<ProspectHistoric> findByUUIDHistoricIn(Collection<UUID> uuidList);

    List<ProspectHistoric> findProspectHistoricByUserInAndContractDateBetween(List<User> userList, Date startDate, Date endDate);

    List<ProspectHistoric> findByUserIn(List<User> userList);

    List<ProspectHistoric> findWonByUserIn(List<User> userList);

    List<UUID> findIdByUserIn(List<User> userList);

    Long countNumberHistoricProspectByOrganisation(Organisation organisation);

    Long countNumberHistoricProspectByContact(Contact contact);

    int countNumberHistoricProspectBySalesMethod(SalesMethod salesMethod);

    Long countNumberWonProspectByOwnerInAndWonLostDateBetween(List<User> userList, Date startDate, Date endDate);

    List<Object[]> countUserNumberClosedProspectByOwnerInAndWonLostDateBetween(List<User> userList, Date startDate, Date endDate, Boolean won);

    List<Double> findBiggestGrossValueListByEnterpriseAndWonAndWonLostDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    List<Object[]> findBiggestProspectByOwnerInAndWonAndWonLostDateBetween(List<User> userList, Date startDate, Date endDate);

    List<Long> findFastestListByEnterpriseAndWonAndWonLostDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    List<Object[]> findFastestProspectByOwnerInAndWonAndWonLostDateBetween(List<User> userList, Date startDate, Date endDate);

    List<Double> findMostProfitableListByEnterpriseAndWonAndWonLostDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    List<Object[]> findMostProfitableByOwnerInAndWonAndWonLostDateBetween(List<User> userList, Date startDate, Date endDate);

    List<ProspectHistoric> findByPowerSponsorInAndWonAndWonLostDateBetweenAndNotDeleted(List<Contact> contactList, Date startDate, Date endDate);

    List<ProspectHistoric> findByOrganisationIdInAndWonAndWonLostDateBetweenAndNotDeleted(List<UUID> organisationIdList, Date startDate, Date endDate);

    List<UUID> findIdByEnterpriseAndWonAndWonLostDateBetween(Enterprise enterprise, Date startDate, Date endDate);

    List<ProspectHistoric> findByOrganisationInAndWonAndWonLostDateBetweenAndNotDeleted(List<Organisation> organisationList, Date startDate, Date endDate);

    List<ProspectHistoric> findByLineOfBusinessInAndWon(List<LineOfBusiness> lineOfBusinessList);

    ProspectHistoric findByEnterpriseAndWonAndMaestranoInvoiceId(Enterprise enterprise, String maestranoInvoiceId);

    Double sumGrossValueByOrganisationAndWonLostDateBetweenAndWon(Organisation organisation, Date startDate, Date endDate);

    Double sumGrossValueByPowerSponsorAndWonLostDateBetweenAndWon(Contact contact, Date startDate, Date endDate);

    List<ProspectHistoric> findByWonAndEnterpriseAndWonLostDateBetween(Enterprise enterprise, Date start, Date end);

    List<ProspectHistoric> findAllWonAndEnterpriseAndNotDeleted(Enterprise enterprise);

    ProspectHistoric findByVismaId(Enterprise enterprise, String vismaId);

    List<ProspectHistoric> findBySalesMethod(SalesMethod salesMethod);

    List<ProspectHistoric> findBySalesMethodAndWonOrderByUpdatedWonDateAsc(SalesMethod salesMethod);

    List<ProspectHistoric> findByOrganisationAndWon(Organisation organisation, Boolean won);

    List<ProspectHistoric> findByWonAndPowerSponsor(Boolean won, Contact contact);

    Long countByOrganisationAndWonAndWonLostDateLessThan(Organisation organisation, Boolean won, Date date);

    Long countByEnterprise(Enterprise enterprise);

    List<ProspectHistoric> findByWonAndOrganisationAndWonLostDateBetween(Boolean won, Organisation organisation, Date start, Date end);

    List<ProspectHistoric> findByWonAndEnterprise(Boolean won, Enterprise enterprise);

    List<ProspectHistoric> findByOwnerAndWonOrderByUpdatedWonDateAsc(User user, Boolean won);

    List<Object[]> findByWonAndPage(Boolean won, int pageIndex, int pageSize);

    List<Object[]> findByWonAndPageAndCreatedDateBetween(Boolean won, Date startDate, Date endDate, int pageIndex, int maxPageSize);

    List<ProspectHistoric> findByIds(Collection<UUID> uuids);

    List<ProspectHistoric> findByOwnerIn(List<User> userList);

    void removeWhereProspectIn(List<UUID> prospectList);

    List<ProspectHistoric> findByOwnerOrContactIn(User user, List<Contact> contactList);

    void moveProspect(User oldUser, User newUser);

    List<ProspectHistoric> findByContactIn(List<Contact> deletedContactList);

    List<ProspectHistoric> findByContactInAndNotVisitMoreAndLastSyncTimeNullOrBeforeSyncTime(List<Contact> contactList, Date lastSync);

    List<ProspectHistoric> findByOrganisationIn(List<Organisation> organisationList);

    List<ProspectHistoric> findByOrganisationInAndNotVisitMoreAndLastSyncTimeNullOrBeforeSyncTime(List<Organisation> organisationList, Date lastSync);

    List<ProspectHistoric> findAllByPage(int pageIndex, int pageSize);

    List<ProspectHistoric> findAll();
    
    List<ProspectHistoric> findByIdsAndActiveTaskAndActiveMetting(Collection<UUID> uuids);

    List<UUID> findUUIDByOrganisation(Organisation organisation);

    List<ProspectHistoric> getHistoricProspectByContactId(UUID contactId);

    List<UUID> getHistoricProspectIdsByContactId(UUID contactId);

    List<ProspectHistoric> findWonOpp();

    List<ProspectHistoric> findByContactListAndOrganisationAndOwner(List<Contact> contacts, Organisation organisation, User user);

    ProspectHistoric findByFortNoxId(Enterprise enterprise, String s);
}
