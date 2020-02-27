package com.salesbox.dao;

import com.salesbox.entity.Organisation;
import com.salesbox.entity.OrganisationUser;
import com.salesbox.entity.Unit;
import com.salesbox.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/24/14
 */
public interface OrganisationUserDAO extends BaseDAO<OrganisationUser>
{
    List<OrganisationUser> findByOrganisationIn(List<Organisation> organisationList);

    List<OrganisationUser> findByOrganisation(Organisation organisation);

    List<Object[]> findUserAndUserSharedContactByOrganisation(Organisation organisation);

    OrganisationUser findByOrganisationAndUser(Organisation organisation, User user);

    List<Organisation> findByUserAndUpdatedDateAfterOrderByUuidAsc(User user, Date updatedDate, Pageable pageable);

    public List<Organisation> findByUnitAndUpdatedDateAfterOrderByUuidAsc(Unit unit, Date updatedDate, Pageable pageable);

    List<OrganisationUser> findByUser(User user);

    List<Organisation> findOrganisationByUserIn(List<User> userList);

    List<User> findUserByOrganisation(Organisation organisation);
    
    List<User> findUserByOrganisationIn(List<Organisation> organisationList);

    OrganisationUser findOne(UUID uuid);

    void deleteInBatch(List<OrganisationUser> oldOrganisationUserList);

    Long countNumberOrganisationUserAddedFromPool();

    public List<Organisation> findByUserInAndFilterByTimeOrderByCondition(List<User> users, Pageable pageable, User user, String orderBy);

    public List<Organisation> findByUserInAndFilterByTimeAndCustomFilter(List<User> users, Pageable pageable, User user, String customFilter);

    void removeWhereOrganisationIn(List<Organisation> organisationList);

    OrganisationUser findByOrganisationIdAndUser(UUID organisationId, User user);

    void removeWhereOrganisationInAndUser(List<Organisation> organisationList, User user);

    List<Organisation> findOrganisationByUserAndOrganisationIn(User user, List<Organisation> organisationList);

    List<OrganisationUser> findByOrganisationInOrUser(List<Organisation> organisations, User user);
    
    List<OrganisationUser> findByOrganisationInAndUser(List<Organisation> organisations, User user);

    List<UUID> findOrganisationIdByOrganisationIdInAndUser(List<UUID> organisationIds, User user);

    void deleteByOrganisationIn(List<Organisation> deletedOrganisations);

    void moveUser(User oldUser, User newUser);

    List<UUID> findOrganisationUuidByUserIn(List<User> userList);
}
