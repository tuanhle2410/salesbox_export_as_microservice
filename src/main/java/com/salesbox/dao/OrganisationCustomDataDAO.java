package com.salesbox.dao;

import com.salesbox.entity.Organisation;
import com.salesbox.entity.OrganisationCustomData;
import com.salesbox.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/24/14
 */
public interface OrganisationCustomDataDAO extends BaseDAO<OrganisationCustomData>
{
    List<OrganisationCustomData> findByUserAndUpdatedDateAfterOrderByUuidAsc(
            User user,
            Date updatedDate,
            Pageable pageable);

    List<OrganisationCustomData> findByOrganisationInAndUser(List<Organisation> organisationList, User user);

    List<OrganisationCustomData> findByOrganisationIn(List<Organisation> organisationList);

    OrganisationCustomData findByOrganisationAndUser(Organisation organisation, User user);

    OrganisationCustomData findOne(UUID uuid);

    int getMaxByUserAndFavorite(User user, boolean favorite);

    void removeWhereUserIn(List<User> userList);

    void removeWhereOrganisationIn(List<Organisation> organisationList);

    void deleteByOrganisationIn(List<Organisation> deletedOrganisations);

    List<OrganisationCustomData> findByRecentActionTypeIsNotNull(Integer pageIndex, Integer pageSize);

    List<Organisation> findByUserAndOrderByUpdatedDate(User user, Pageable pageable);
}
