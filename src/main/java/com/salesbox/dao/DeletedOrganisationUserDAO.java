package com.salesbox.dao;

import com.salesbox.entity.DeletedOrganisationUser;
import com.salesbox.entity.Organisation;
import com.salesbox.entity.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by luult on 10/6/2014.
 */
public interface DeletedOrganisationUserDAO extends BaseDAO<DeletedOrganisationUser>
{
    List<UUID> findByUserAndUpdatedDate(User user, Date updatedDate);

    DeletedOrganisationUser findByOrganisationAndUser(Organisation organisation, User user);
    
    List<DeletedOrganisationUser> findByOrganisationInAndUserIn(List<Organisation> organisationList, List<User> users);
    
    DeletedOrganisationUser findByOrganisationInAndUser(List<Organisation> organisation, User user);

    void removeWhereOrganisationIn(List<Organisation> organisationList);

    void deleteByOrganisationIn(List<Organisation> organisationList);
}
