package com.salesbox.dao;

import com.salesbox.entity.*;
import com.salesbox.entity.enums.StorageIntegrationType;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/15/14
 * Time: 2:57 PM
 */
public interface UserStorageIntegrationDAO extends BaseDAO<UserStorageIntegration>
{
    UserStorageIntegration findByUserAndStorageIntegrationType(User user, StorageIntegrationType type);

    UserStorageIntegration findByUserAndStorageIntegrationTypeIn(User user, List<StorageIntegrationType> typeList);

    List<UserStorageIntegration> findByUser(User user);

    UserStorageIntegration findOne(UUID uuid);

    UserStorageIntegration findByUserIdentifierAndType(String userIdentifier, StorageIntegrationType type);

    List<UserStorageIntegration> findByStorageTypeIn(List<StorageIntegrationType> types);

    UserStorageIntegration findByUserIdentifierAndTypeIn(String userIdentifier, List<StorageIntegrationType> typeList);

    UserStorageIntegration findByOrganisationAndType(Organisation organisation, StorageIntegrationType type);

    List<String> findUserByOrganisation(Organisation organisation);

    UserStorageIntegration findByEnterpriseAndStorageIntegrationType(Enterprise enterprise, StorageIntegrationType type);

    UserStorageIntegration findByEmailAndStorageIntegrationType(String email, StorageIntegrationType type);

    UserStorageIntegration findByContactAndStorageIntegrationType(Contact enterprise, StorageIntegrationType type);

    List<String> findUserByContact(Contact contact);

    List<String> findUserByProspect(UUID prospectId);

}
