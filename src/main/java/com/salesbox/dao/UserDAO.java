package com.salesbox.dao;

import com.salesbox.entity.*;
import com.salesbox.entity.enums.UserAccountType;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/10/14
 * Time: 5:37 PM
 */
public interface UserDAO extends BaseDAO<User>
{
    User findOne(UUID uuid);

    public List<User> findByUuidIn(List<UUID> uuidList);

    public List<User> findByUnitEnterpriseOrderByFirstName(Enterprise enterprise);

    public List<User> findByUnitOrderByFirstName(Unit unit);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public List<User> findByEmailIn(List<String> email);

    public long countActivateUserByEnterprise(Enterprise enterprise, boolean activate);

    List<User> findByUnitIn(List<Unit> unitList);

    List<UUID> findUuidByUnit(Unit unit);

    List<UUID> findUuidByUnitId(UUID unitId);

    List<User> findByUnitInAndActive(List<Unit> unitList);

    List<User> findByUnitEnterpriseAndActive(Enterprise enterprise);

    List<User> findByUnitEnterpriseAndInActive(Enterprise enterprise);

    List<User> findByUnitEnterpriseAndActiveOrderByFirstName(Enterprise enterprise);

    List<User> findByUnitEnterpriseAndActiveOrderByCreatedDateDesc(Enterprise enterprise);

    List<User> findByUnitEnterpriseAndActiveOrderByCreatedDateAsc(Enterprise enterprise);

    List<User> findByEnterpriseAndNotHasSubscriptionAmazonAndActiveOrderByCreatedDateAsc(Enterprise enterprise);

    List<User> findByEnterpriseAndSubscriptionAmazonAndActive(SubscriptionInfo subscriptionInfo);

    Long countByEnterpriseAndSubscriptionInfoAndActive(SubscriptionInfo subscriptionInfo);

    User findBySharedContact(SharedContact sharedContact);

    List<User> findAll();

    List<User> findAllAndEnterpriseActive();

    Long countLoggedInUser();

    List<User> findBySharedContactIn(List<SharedContact> sharedContactList);

    Long countByTypeAndEnterprise(UserAccountType type, Enterprise enterprise);

    List<User> findByUsernameInAndEnterprise(List<String> usernameList, Enterprise enterprise);

    User findByUnitEnterpriseAndUsernameIsNotAndType(Enterprise enterprise, String email, UserAccountType userAccountType);

    User findByUnitEnterpriseAndTypeOrderByUuidDesc(Enterprise enterprise, UserAccountType type);

    int countByUnitEnterpriseAndType(Enterprise enterprise, UserAccountType accountType);

    User findByUsernameAndActive(String username, Boolean active);

    List<Object[]> findActiveUserByPageOrderByUuidDesc(int pageIndex, int pageSize);

    List<Object[]> findActiveUserByPageOrderByUuidDescAfter16Nov(int pageIndex, int pageSize);

    List<User> findByUnitIdAndActive(UUID unitId);

    List<User> findAllByUnitId(UUID unitId);

    List<User> findByCreatedDateBetweenAndUuidNotIn(Date startDate, Date endDate, List<UUID> exceptedUuidList);

    List<UUID> findUUIDByUnitEnterpriseAndActiveOrderByUserCreatedDateAsc(Enterprise enterprise);

    List<Object[]> findEnterpriseIdAndUserByWizardFinishedDateSecondUserBetween(Date startDate, Date endDate);

    List<User> findByEnterpriseIdInAndActive(Set<UUID> enterpriseIdList);

    User findByIsFirstUserAndEnterpriseIdAndActive(UUID enterpriseId);

    List<User> findByUnitEnterprise(Enterprise enterprise);

    List<SharedContact> findSharedContactByUserIn(List<User> userList);

    void removeWhereEnterprise(Enterprise enterprise);

    List<Object[]> findUsernameAndLanguageCodeByPageOrderByUserNameDesc(int pageIndex, int pageSize);

    long countNumberFreeExperibleLicenseByEnterprise(Enterprise enterprise);

    List<User> findByEnterpriseInAndType(List<Enterprise> enterpriseList, UserAccountType type);

    List<User> findByEnterpriseAndType(Enterprise enterprise, UserAccountType type);

    long countByEnterprise(Enterprise enterprise);

    List<Object[]> findUserNameAndFirstNameAndLanguageCodeByPageOrderByUserNameDesc(int pageIndex, int pageSize);

    List<Object[]> findUserNameAndFirstNameAndLanguageCodeAndEnterpriseByPageAndCreatedDateBeforeOrderByUserNameAsc(Date date, int pageIndex, int pageSize);

    List<Object[]> findUserIdAndUserNameAndFirstNameAndLanguageCodeAndEnterpriseByCreatedDateAfterAndTwoWeekToEndOfOfferPeriodBetweenOrderByUserNameAsc(Date date, Date startDate, Date endDate, int numberOfferDays);

    List<Object[]> findUserNameAndFirstNameAndLanguageCodeAndEnterpriseByCreatedDateAfterAndOneWeekToEndOfOfferPeriodBetweenOrderByUserNameAsc(Date startDate, Date endDate);

    List<Object[]> findUserIdAndUserNameAndFirstNameAndLanguageCodeAndEnterpriseByCreatedDateAfterAndOneDayToEndOfOfferPeriodBetweenOrderByUserNameAsc(Date startDate, Date endDate);

    List<Object[]> findUuidAndUserNameAndFirstNameByEmailSubscribedAndPageOrderByUsernameAsc(int pageIndex, int pageSize);

    List<Object[]> findUuidAndUserNameAndFirstNameByEmailSubscribedAndNotExpirePageOrderByUsernameAsc(int pageIndex, int pageSize);

    List<Object[]> findUuidAndUserNameAndFirstNameByEmailSubscribedAndEnterpriseInAndPageOrderByUsernameAsc(List<Enterprise> enterpriseList, int pageIndex, int pageSize);

    long countUuidAndUserNameAndFirstNameByEmailSubscribedAndEnterpriseInAndPageOrderByUsernameAsc(List<Enterprise> enterpriseList);

    List<UUID> findUserSharedContactIdBySharedContactIdIn(List<UUID> sharedContactIdList);

    long countNumberActiveUser();

    long countNumberActiveUserByEnterprise(Enterprise enterprise);

    long countNumberUserByUnit(Unit unit);

    List<Object[]> findActiveUser(Long pageIndex, Long pageSize);

    Long countNumberActiveUserAfter16Nov();

    List<Object[]> findActiveUserAfter16Nov(Long pageIndex, Long pageSize);

    List<Object[]> findUserIdAndSharedContactPhoneByUserIn(List<User> userList);

    List<Object[]> findActiveUserCreatedBetween(Date startTime, Date endTime);

    List<User> findByEnterpriseIn(List<Enterprise> enterpriseList);
    
    User findByOpenIdAndAccountIndentifier(String openid, String accountIdentifier);

    User findByEmailAndActive(String email, boolean active);

    List<Object[]> findUnexpiredUuidAndUserNameAndFirstNameByEmailSubscribedAndPageOrderByUsernameAsc(int pageIndex, int maxPageSize);

    List<Object[]> findExpiredUuidAndUserNameAndFirstNameByEmailSubscribedAndPageOrderByUsernameAsc(int pageIndex, int maxPageSize);

    List<User> findExpireUserOderByUsernameAsc(int pageIndex, int maxPageSize);

    long countExpireUserOderByUsernameAsc();

    List<Object[]> findUnExpiredUserByEmailSubscribedAndPageOrderByUsernameAsc(int pageIndex, int pageSize);

    List<Object[]> findExpiredUserByEmailSubscribedAndPageOrderByUsernameAsc(int pageIndex, int pageSize);

    User findByUuidAndMaestranoUserId(UUID userId, String maestranoUserId);

    User findByMaestranoUserId(String userId);

    List<User> finByUnitId(UUID unitId);

    List<User> findByEnterprise(Enterprise enterprise);

    List<User> findActiveAndPageIndexAndPageSize(Integer pageIndex, Integer pageSize);

    List<User> findAllAndPageIndexAndPageSize(Integer pageIndex, Integer pageSize);

    List<User> findUserForMailCampaignMay31(int pageIndex, int maxPageSize);

    List<User> findUserForMailCampaignMay31();
    
    long countUserForMailCampaignMay31();

	List<User> find14DaysTrailUser();

    List<User> findAllByUnitIdAndDeactiveDate(UUID unitId, Date startDate, Date endDate);

    List<User> findAllByEnterpriseAndDeactiveDate(Enterprise enterprise, Date startDate, Date endDate);
}
