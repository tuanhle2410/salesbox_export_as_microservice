package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.SharedOrganisation;
import com.salesbox.entity.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/10/14
 * Time: 5:38 PM
 */
public interface EnterpriseDAO extends BaseDAO<Enterprise>
{
	String getActiveToken(UUID uuid) throws Exception;   //leadboxer-----------------------
	
    Enterprise findOne(UUID uuid);

    List<Enterprise> findAll();

    Enterprise findBySharedOrganisation(SharedOrganisation sharedOrganisation);

    List<Enterprise> findAllOrderByCreatedDate();

    List<Enterprise> findByCreateDateBetweenAndNotActive(Date startDate, Date endDate);

    List<Enterprise> findByCreatedDateBeforeAndDurationExpireDateIsNull(Date date);

    Long countNumberEnterprises();

    List<Enterprise> findByActive();

    Enterprise findByStripeCustomerId(String customerId);

    List<Enterprise> findByExpireDateBetweenAndActive(Date startDate, Date endDate);

    List<Enterprise> findOldRegisteredEnterpriseToAddFreeUser();

    List<Enterprise> findByUuidIn(List<UUID> uuidList);

    List<Enterprise> findByUuidNotIn(List<UUID> uuidList);

    Enterprise findByStripeCustomerIdAndSubscriptionId(String stripeCustomerId, String subscriptionId);

    List<Object[]> findMainContactWithCompanySizeByMainContactIn(List<User> userList);

    Long countNumberEnterprisesCreatedDateBetween(Date startDate, Date endDate);

    Enterprise findByMainContactId(UUID mainContactId);

    List<Enterprise> findByPage(int pageIndex, int pageSize);

    List<SharedOrganisation> findSharedOrganisationByPage(int pageIndex, int pageSize);

    List<Enterprise> findByFreeExpiredDateBeforeOrderByUuidDesc(Date now, int pageIndex, int pageSize);

    List<Enterprise> findAllEnterpriseHasNumberPaidIsZero();

    List<Enterprise> findAllEnterpriseHasNumberPaidIsZeroAndRegisteredWithinFourteenDaysAgo(Date fourteenDaysAgo);

    List<Enterprise> findByCreatedDateBetween(Date startDate, Date endDate);

    Enterprise findByMaestranoGroupId(String groupId);

    User findMainContactByMaestranoGroupId(String uid);

    List<Enterprise> findByActiveAndNotIn(List<Enterprise> tempEnterpriseList);

    List<Enterprise> findByActiveAndPageIndexAndPageSizeAndExpireDate(Integer pageIndex, Integer pageSize);

    List<Enterprise> findPayingOrHaveTrackingKeyEnterprise();
}
