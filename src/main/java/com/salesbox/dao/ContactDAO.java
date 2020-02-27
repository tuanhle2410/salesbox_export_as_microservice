package com.salesbox.dao;

import com.salesbox.entity.*;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 5/21/14
 */
public interface ContactDAO extends BaseDAO<Contact>
{
    Contact findOne(UUID uuid);

    List<Contact> findAll();

    List<Contact> findByUuidIn(Collection<UUID> uuidList);

    List<Contact> findByUuidInAndNotDeleted(Collection<UUID> uuidList);

    List<Object[]> findPropertiesForSyncByEnterpriseAndUpdatedDateAfterOrderByUuidAsc(Enterprise enterprise, Date updatedDate, Pageable pageable);

    List<Contact> findByOrganisationAndActiveOrderByOrderIntakeDesc(Organisation organisation);

    List<Contact> findByOrganisationOrderByOrderUuid(Organisation organisation);

    List<Contact> findByOrganisation(Organisation organisation);

    List<Contact> findByOrganisationAndUuidNotAndNotDeletedOrderByOrderIntakeDesc(Organisation organisation, UUID uuid);

    List<Contact> findByOrganisationAndDeleted(Organisation organisation, Boolean deleted);
    
    List<Contact> findByOrganisationInAndDeleted(List<Organisation> organisations, Boolean deleted);

    List<Contact> findByOrganisationOrderByAlphabet(Organisation organisation, Integer pageIndex, Integer pageSize);

    List<Contact> findByOrganisationExceptOrderByAlphabet(Organisation organisation, Integer pageIndex, Integer pageSize, String exceptId);

    List<Contact> findByOrganisationFavorite(Organisation organisation, UUID userId);

    List<Contact> findByOrganisationRecent(Organisation organisation, UUID userId);

    List<Contact> findByOrganisationAndDeletedAndUpdatedDateAfterOrderByUuidAsc(Organisation organisation, Boolean deleted, Date updatedDate, Pageable pageable, Enterprise enterprise, String searchField);

    List<Contact> findByEnterpriseAndDeleted(Enterprise enterprise, Boolean deleted);

    List<Object[]> findPropertiesByEnterpriseAndNotDeleted(Enterprise enterprise);

    List<Object[]> findFirstLastNameEmailPhoneByEnterpriseAndNotDeleted(Enterprise enterprise);

    List<Contact> findByEnterpriseAndDeletedAndNoOrganisation(Enterprise enterprise, Boolean deleted);

    List<Contact> findByEnterpriseAndPhone(Enterprise enterprise, String phone);

    List<Contact> findActiveByEnterprise(Enterprise enterprise);

    List<UUID> findSharedContactIdByEnterpriseAndDeleted(Enterprise enterprise);

    Contact findByEnterpriseAndEmailAndDeleted(Enterprise enterprise, String email, Boolean deleted);

    List<Contact> findByEnterpriseAndListEmailAndDeleted(Enterprise enterprise, List<String> email, Boolean deleted);

    Contact findByEnterpriseAndPhoneAndDeleted(Enterprise enterprise, String phone, Boolean deleted);

    List<Contact> findByEnterpriseAndSharedContactInAndDeleted(Enterprise enterprise, List<SharedContact> sharedContactList, Boolean deleted);

    Contact findByEnterpriseAndSharedContactAndDeleted(Enterprise enterprise, SharedContact sharedContact, Boolean deleted);

    Contact findByEnterpriseAndSharedContactAndNotDeleted(Enterprise enterprise, SharedContact sharedContact);

    Contact findByEnterpriseAndLinkedinProfileIdAndNotDeleted(Enterprise enterprise, String linkedinProfileId);

    Contact findByEnterpriseAndLinkedinProfileIdAndNotDeletedAndNoOldContact(Enterprise enterprise, String linkedinProfileId);

    Contact findByEnterpriseAndAccount(Enterprise enterprise, Organisation account, String firstName, String lastName);

    Contact findByEnterpriseAndFirstnameAndLastname(Enterprise enterprise, String firstName, String lastName);

    List<String> findLinkedinProfileIdByEnterpriseAndNotDelete(Enterprise enterprise);

    List<String> findLinkedinProfileSalesIdByEnterpriseAndNotDelete(Enterprise enterprise);

    Contact findByEnterpriseAndGoogleContactIdAndNotDeleted(Enterprise enterprise, String googleContactId);

    List<Contact> findByEnterpriseAndAndNotDeletedAndHasGoogleId(Enterprise enterprise);

    List<Contact> findByOwnerAndAndNotDeletedAndHasGoogleId(User owner);

    List<Contact> findByOwnerAndAndNotDeletedAndHasOutlookId(User owner);

    List<Contact> findByOwnerAndAndNotDeletedAndHasOffice365Id(User owner);

    Contact findByEnterpriseAndOffice365ContactIdAndNotDeleted(Enterprise enterprise, String office365ContactId);

    Contact findByEnterpriseAndOutlookContactIdAndNotDeleted(Enterprise enterprise, String outlookContactId);

    List<Contact> findByEnterpriseAndSharedContactInAndNotDeleted(Enterprise enterprise, Collection<SharedContact> sharedContacts);

    List<Contact> findBySharedContactAndNotDelete(SharedContact sharedContact);

    List<SharedContact> findSharedContactNotGetByEnterpriseAndSharedContactIn(Enterprise enterprise, List<SharedContact> sharedContactList);

    List<Contact> findAllByPage(int pageIndex, int maxPageSize);

    List<Object[]> findPropertiesForSyncByUuidIn(List<UUID> uuidList);

    List<Object[]> findUuidAndSharedContactIdAndTypeNameByEnterpriseAndSharedContactIdIn(Enterprise enterprise, List<UUID> sharedContactIdList);

    Contact findByEnterpriseAndSharedContactId(Enterprise enterprise, UUID sharedContactId);

    List<UUID> findSharedContactIdByOrganisation(Organisation organisation);

    List<UUID> findIdByOrganisation(Organisation organisation);

    List<Contact> findByIds(Pageable pageable, Collection<UUID> uuids);

    List<Contact> findByIdIn(Collection<UUID> uuids);

    List<Contact> getByIdIn(Collection<UUID> uuids);

    Long countNumberColleagueByDeleted(boolean deleted, Contact contact);

    List<Contact> findByOwnerIn(List<User> userList);

    void removeWhereContactIn(List<Contact> contactList);

    Long countByOrganisationAndDeleted(Organisation organisation, Boolean deleted);

    void resetMeetingCache();

    List<Contact> findByUuidInAndOwnerAndDeleted(List<UUID> uuidList, User owner, boolean deleted);

    List<Contact> findByUuidInAndOwnerIsNotAndDeleted(List<UUID> uuidList, User owner, boolean deleted);

    List<UUID> findSharedContactIdByUuidInAndDeleted(List<UUID> uuidList, boolean deleted);

    List<Object[]> findSharedContactAndContactByEnterpriseAndSharedContactIdInAndDeleted(Enterprise enterprise, List<UUID> sharedContactIdList, boolean deleted);

    List<Contact> findByEnterpriseAndNameLikeOrderByName(Enterprise enterprise, String name, Pageable pageable);

    List<Contact> findByEnterpriseOrderByName(Enterprise enterprise, Pageable pageable);

    List<String> findEmailByUuidIn(List<UUID> uuidList);

    List<Contact> findIdAndEmailByUuidIn(List<UUID> uuidList);

    List<Contact> searchContact(String name,
                                List<UUID> industryList,
                                List<UUID> clientList,
                                List<String> countryList,
                                List<UUID> sizeTypeList, String title,
                                Integer pageIndex, Integer pageSize, Enterprise enterprise);

    List<Object[]> searchContactForCallList(String searchConditions,
                                            List<UUID> industryList,
                                            List<UUID> clientList,
                                            List<String> titleList,
                                            List<String> countryList,
                                            List<String> cityList,
                                            Integer pageIndex, Integer pageSize,
                                            Enterprise enterprise);

    long getTotalContactWhenSearchForCallList(String searchConditions,
                                              List<UUID> industryList,
                                              List<UUID> clientList,
                                              List<String> titleList,
                                              List<String> countryList,
                                              List<String> cityList,
                                              Enterprise enterprise);

    List<Object[]> findContactAndNumberActiveTaskByContactIn(List<Contact> contactList);

    List<Contact> findBySharedContactIn(List<SharedContact> sharedContactList);

    List<Contact> findByEmailInAndEnterpriseAndDeleted(List<String> emailList, Enterprise enterprise, Boolean deleted);

    List<Contact> findByOwner(User user);

    Contact findByEnterpriseAndMaestranoId(Enterprise enterprise, String maestranoId);

    void moveOwner(User oldUser, User newUser);

    List<Contact> findByEnterprise(Enterprise enterprise);

    List<Contact> findByEnterpriseAndVismaIsNotNull(Enterprise enterprise);

    List<Contact> findByOrganisationIn(List<Organisation> organisationList);

    long countNotDeletedByEnterprise(Enterprise enterprise);

    long countByEnterprise(Enterprise enterprise);

    Double averageClosedMargin(Enterprise enterprise);

    Double averageMedianDealSizeByEnterprise(Enterprise enterprise);

    Double averageClosedSalesByEnterprise(Enterprise enterprise);

    Double averageClosedProfitByEnterprise(Enterprise enterprise);

    Double averageMedianDealTimeByEnterprise(Enterprise enterprise);

    List<String> findGoogleIdByEnterpriseAndGoogleIdIn(Enterprise enterprise, List<String> googleExternalKeyList);

    List<Contact> findByEnterpriseAndGoogleIdIn(Enterprise enterprise, List<String> googleExternalKeyList);

    List<String> findOutlookIdByEnterpriseAndOutlookIdIn(Enterprise enterprise, List<String> outlookIdList);

    List<String> findOffice365IdByEnterpriseAndOffice365IdIn(Enterprise enterprise, List<String> office365IdList);

    Long countByOrganisationAndActiveOrderByOrderIntakeDesc(Organisation organisation);

    long countByUserHasGoogleId(User user);

    long countByUserHasExternalKey(User user);

    long countByUserHasOutlookId(User user);

    long countByUserHasOffice365Id(User user);

    Long countByOrganisationAndUuidNotAndNotDeleted(Organisation organisation, UUID uuid);

    List<String> getUserResponsible(Enterprise enterprise);

    List<Organisation> findOrganisationByContactIdIn(List<UUID> uuidList);

    Contact findByOrganisationAndFirstNameAndLastName(Organisation organisation, String firstName, String lastName);

    List<UUID> findContactIdByOrganisationNameAndFirstNameAndLastNameAndEnterprise(Enterprise enterprise, String accountName,
                                                                                   String firstName, String lastName);

    List<Contact> findContactByOrganisationNameAndFirstNameAndLastNameAndEnterprise(Enterprise enterprise, String accountName,
                                                                                    String firstName, String lastName);

    List<UUID> findContactIdByFirstNameAndLastNameAndPhoneAndCityAndEnterprise(Enterprise enterprise, String firstName, String lastName,
                                                                               String phone, String city);

    List<UUID> findContactIdFirstNameAndLastNameAndCityAndAddressAndEnterprise(Enterprise enterprise, String firstName,
                                                                               String lastName, String city, String street);

    List<Contact> findContactFirstNameAndLastNameAndCityAndAddressAndEnterprise(Enterprise enterprise, String firstName,
                                                                                String lastName, String city, String street);
    List<Contact> findByOrganisationAndVimaIdIsNotNull(Organisation organisation);

    Contact findByVismaId(String vismaId);

    List<Contact> findByVismaIds(List<String> vismaIds, Enterprise enterprise);


    List<UUID> findUUIDByOrganisation(Organisation organisation);

    Contact findByFirstNameAndLastNameAndNotDeleteAndNoOldContact(String firstName, String lastName, Enterprise enterprise);


    List<Object[]> findListContactByListEmailAndEnterpriseAndDeleted(List<String> emailList, Enterprise enterprise, Boolean deleted);

    long countContactByListEmailAndEnterpriseAndDeleted(List<String> emailList, Enterprise enterprise, Boolean deleted);

    List<Contact> findListContactByEnterpriseAndDeleted(Enterprise enterprise, Boolean deleted);

    Contact getContactByCustomerIDAndEnterprise(String customerNumber, Enterprise enterprise);
}
