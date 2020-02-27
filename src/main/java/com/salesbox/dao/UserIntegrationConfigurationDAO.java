package com.salesbox.dao;

import com.salesbox.entity.User;
import com.salesbox.entity.UserIntegrationConfiguration;
import com.salesbox.entity.enums.IntegrationType;
import com.salesbox.entity.enums.ObjectType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 7/24/14
 * Time: 8:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UserIntegrationConfigurationDAO extends BaseDAO<UserIntegrationConfiguration>
{
    UserIntegrationConfiguration findByResourceId(String resourceId);


    long countNumberChanelSyncByObjectTypeAndUser(User user, ObjectType objectType);

    List<UserIntegrationConfiguration> findByUser(User user);

    List<UserIntegrationConfiguration> findAll();

    UserIntegrationConfiguration findByUserAndObjectTypeAndIntegrationType(User user, ObjectType objectType, IntegrationType integrationType);

    List<UserIntegrationConfiguration> findByUserAndObjectTypeInAndIntegrationType(User user, List<ObjectType> objectTypeList, IntegrationType integrationType);

    List<UserIntegrationConfiguration> findByObjectTypeInAndIntegrationType(List<ObjectType> objectTypeList, IntegrationType integrationType);

    UserIntegrationConfiguration findByUserAndObjectType(User user, ObjectType objectType);

    //Using for Outlook chanel
    UserIntegrationConfiguration findByChanelId(String chanelId);

    //Using for Gmail chanel
    UserIntegrationConfiguration findByChanelIdForGmail(String email, IntegrationType integrationType);

}
