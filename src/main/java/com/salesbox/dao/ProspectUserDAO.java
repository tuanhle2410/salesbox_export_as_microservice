package com.salesbox.dao;

import com.salesbox.entity.*;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 5/22/14
 */
public interface ProspectUserDAO extends BaseDAO<ProspectUser>
{
    public List<ProspectActive> findActiveProspectByUserAndUpdatedDateAfterOrderByUuidAsc(User user, Date updatedDate, Pageable pageable);

    public List<ProspectHistoric> findHistoricProspectByUserAndUpdatedDateAfterOrderByUuidAsc(User user, Date updatedDate, Pageable pageable);

    public List<ProspectActive> findByActiveProspectUnitAndUpdatedDateAfterOrderByUuidAsc(Unit unit, Date updatedDate, Pageable pageable);

    public List<ProspectHistoric> findHistoricProspectByUnitAndUpdatedDateAfterOrderByUuidAsc(Unit unit, Date updatedDate, Pageable pageable);

    List<ProspectUser> findByProspectInOrderByIndexAsc(Collection<UUID> prospectCollection);

    List<Object[]> findPropertiesByProspectInOrderByIndexAsc(Collection prospectCollection);

    List<Object[]> findPropertiesByProspectIdInOrderByIndexAsc(Collection<UUID> prospectIds);

    List<ProspectUser> findByProspectIdOrderByIndexAsc(UUID prospectId);
    
    List<ProspectUser> findByProspectIdsIn(Collection<UUID> prospectIds);

    List<ProspectUser> findByUserAndWonProspectOrderByUpdatedWonDateAsc(User user);

    List<ProspectBase> findProspectByUserAndWonProspectOrderByUpdatedWonDateAsc(User user);
    
    List<ProspectBase> findProspectByUserInAndWonProspect(Collection<User> userList);

    ProspectUser findOne(UUID uuid);

    void deleteInBatch(List<ProspectUser> prospectUserList);

    List<UUID> findByUserInAndContractDateBetween(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findByUserInAndProspectContractDateBetweenAndProspectIsActiveAndNotDeleted(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findProspectByUserInAndProspectContractDateBetweenAndProspectIsActiveAndNotDeletedAndLineOfBusiness(List<User> userList, Date startDate, Date endDate, LineOfBusiness lineOfBusiness);

    List<ProspectActive> findProspectByUserInAndProspectContractDateBetweenAndProspectIsActiveAndNotDeleted(List<User> userList, Date startDate, Date endDate);

    Double sumMeetingLeftByUserInAndContractDateBetweenAndGrossValueGreaterThanZeroAndProgressIsGreaterThanZero(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findByUserInAndProspectWonLostDateBetweenAndProspectIsWonAndNotDeletedOrderByUpdatedWonDateAsc(List<User> userList, Date startDate, Date endDate);

    List<ProspectHistoric> findProspectByUserInAndProspectWonLostDateBetweenAndProspectIsWonAndNotDeleted(List<User> userList, Date startDate, Date endDate);

    List<User> findUserByProspect(UUID prospectId);
    
    List<User> findUserByProspectIn(List<UUID> prospectIdList);

    List<Object[]> findByUserInAndClosedAndWonLostDataBetWeenAndNotDeleted(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findByUnitInAndClosedAndWonLostDataBetWeenAndNotDeleted(List<Unit> unitList, Date startDate, Date endDate);

    int countProspectByUserInAndProspectWonLostDateBetweenAndProspectIsLostAndNotDeleted(List<User> userList, Date startDate, Date endDate);

    int countProspectByUserInAndProspectWonLostDateBetweenAndProspectIsWonAndNotDeleted(List<User> userList, Date startDate, Date endDate);

    long countProspectActiveByUserInAndCreatedDateBetween(List<User> userList, Date startDate, Date endDate);

    long countProspectActiveByUserInAndContractDateBetween(List<User> userList, Date startDate, Date endDate);

    long countProspectActiveByUserInAndCreatedDateBetweenCreatedDateAndEndDate(List<User> userList, Date endDate);

    long countProspectHistoricByUserInAndCreatedDateBetween(List<User> userList, Date startDate, Date endDate);

    long countDoneProspectByUserInAndCreatedDateLessThanEndDateAndWonLostDateGreaterThanCreatedDate(List<User> userList, Date startDate, Date endDate);

    long countDoneProspectByUserInAndWonLostDateBetween(List<User> userList, Date startDate, Date endDate);

    List<Object[]> countUserAndDoneProspectByUserInAndWonLostDateBetween(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findPropertiesByUserInAndProspectWonLostDateBetweenAndProspectIsWonAndNotDeletedOrderByUpdatedWonDateAsc(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findPropertiesByUserInAndProspectWonLostDateBetweenAndProspectIsLostAndNotDeletedOrderByUpdatedWonDateAsc(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findPropertiesByEnterpriseAndProspectWonLostDateBetweenAndProspectIsWonAndNotDeletedOrderByUpdatedWonDateAsc(Enterprise enterprise, Date startDate, Date endDate);

    int countProspectByEnterpriseAndProspectWonLostDateBetweenAndProspectIsLostAndNotDeleted(Enterprise enterprise, Date startDate, Date endDate);

    List<Object[]> findPropertiesByUserInAndProspectContractDateBetweenAndProspectIsActiveAndNotDeleted(List<User> userList, Date startDate, Date endDate);

    List<Object[]> findUserAndUserSharedContactAndPercentByProspectOrderByIndexAsc(UUID prospectId);

    void removeWhereProspectIn(List<UUID> prospectList);

    List<ProspectHistoric> findWonProspectByUserInAndOrganisationIsNotNullAndWonLostDateBetween(List<User> userList, Date startDate, Date endDate);

    List<ProspectHistoric> findProspectByUserInAndProspectWonLostDateBetweenAndProspectIsWonAndNotDeletedOrderByUpdatedWonDateAsc(List<User> userList, Date startDate, Date endDate);


    List<ProspectUser> findByProspectInOrUser(List<UUID> prospects, User user);

    void moveUser(User oldUser, User newUser);

    List<ProspectHistoric> findByLineOfBusinessInAndWonAndUserIn(List<LineOfBusiness> lineOfBusinessList, List<User> userList, Date startDate, Date endDate);

    List<ProspectActive> findByLineOfBusinessInAndActiveAndUserIn(List<LineOfBusiness> lineOfBusinessList, List<User> userList, Date startDate, Date endDate);

    List<Object[]> findPropertiesByUserInAndProspectIsWonAndNotDeletedOrderByUpdatedWonDateAsc(List<User> userList);

    List<Object[]> findPropertiesByEnterpriseAndProspectIsWonAndNotDeletedOrderByUpdatedWonDateAsc(Enterprise enterprise);

    List< Object[]> findPropertiesByUserInAndProspectIsActiveAndNotDeleted(List<User> userList);

    int countProspectByUserInAndProspectIsLostAndNotDeleted(List<User> userList);

    int countProspectByEnterpriseAndProspectIsLostAndNotDeleted(Enterprise enterprise);
}
