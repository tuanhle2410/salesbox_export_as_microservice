package com.salesbox.dao;

import com.salesbox.entity.*;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 9/4/14
 * Time: 2:26 PM
 */
public interface UserTempDAO extends BaseDAO<UserTemp>
{
    List<UserTemp> findByUnitOrderByFirstName(Unit unit);

    UserTemp findByConfirmToken(String confirmToken);

    List<UserTemp> findByEmail(String email);

    List<UserTemp> findByUnitEnterpriseOrderByFirstName(Enterprise enterprise);

    List<UserTemp> findByEmailAndUnitEnterprise(String email, Enterprise enterprise);

    UserTemp findByEnterpriseTemp(EnterpriseTemp enterpriseTemp);

    UserTemp findOne(UUID uuid);

    List<UserTemp> findAll();

    List<UserTemp> findAll(int pageIndex, int pageSize);

    UserTemp findByUnitEnterpriseAndEmailIsNotOrderByCreatedDateAsc(Enterprise enterprise, String email);

    List<UserTemp> findAllByPageOrderByDateCreatedDescAfter16Nov(int pageIndex, int pageSize);

    List<UserTemp> findByEnterpriseTempIsNotNullOrderByUuidDesc(int pageIndex, int pageSize);

    void removeWhereCreatorIn(List<User> userList);

    Long countNumberTempUser();

    List<UserTemp> findBySubscription(SubscriptionInfo subscriptionInfo);
}
