package com.salesbox.dao;

import com.salesbox.entity.*;
import com.salesbox.entity.enums.CommunicationType;
import com.salesbox.entity.enums.MediaType;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * User: luult
 * Date: 5/21/14                         Organisation
 */
public interface OrganisationDAO extends BaseDAO<Organisation>
{
    public List<Organisation> findAll();

    public List<Organisation> findByEnterpriseAndDeletedOrderByName(Enterprise enterprise, Boolean deleted);

    public List<Organisation> findByEnterpriseAndNoteDeleted(Enterprise enterprise);

    public List<Object[]> findPropertiesByEnterpriseAndNotDeleted(Enterprise enterprise);

    public List<UUID> findSharedOrganisationIdByEnterpriseAndDeleted(Enterprise enterprise);

    public List<Organisation> findByEnterpriseAndTypeAndDeleted(Enterprise enterprise, WorkDataOrganisation type, Boolean deleted);

    public List<Organisation> findByUuidIn(List<UUID> uuidList);

    public List<Organisation> findByUuidInNotDeleted(List<UUID> uuidList);

    public List<Organisation> findByEnterpriseAndSharedOrganisation(Enterprise enterprise, SharedOrganisation sharedOrganisation);

    public List<Organisation> findByEnterpriseAndSharedOrganisationIn(Enterprise enterprise, List<SharedOrganisation> sharedOrganisation);

    public List<Organisation> findByEnterpriseAndUpdatedDateAfterOrderByUuidAsc(Enterprise enterprise, Date updatedDate, Pageable pageable);

    public Organisation findByEnterpriseAndSharedOrganisationAndDeleted(Enterprise enterprise, SharedOrganisation sharedOrganisation, Boolean deleted);

    public Organisation findOne(UUID uuid);

    public List<Organisation> findByOwnerAndNotDeletedOrderByUuid(User owner);

    public List<Organisation> findByOwnerAndNotDeletedOrderByCreatedDateDesc(User owner);

    public List<Organisation> findBySharedOrganisationAndNotDeleted(SharedOrganisation sharedOrganisation);

    public Organisation findByEmailAndPrivate(String email, Boolean isPrivate);

    public Organisation findByEmailAndEnterprise(String email, Enterprise enterprise);

    public Organisation findByEnterpriseAndLinkedinProfileIdAndNotDeleted(Enterprise enterprise, String linkedinProfileId);

    public List<Organisation> findByEmailInAndEnterprise(List<String> emailList, Enterprise enterprise);

    List<Organisation> findAllByPage(int pageIndex, int pageSize);

    List<Object[]> findUuidAndNameAndSharedOrganisationIdByUuidIn(List<UUID> uuidList);

    List<Organisation> findByEnterpriseAndNameAndNotDeletedOrderByName(Enterprise enterprise, String name, Pageable pageable);

    List<Organisation> findByEnterpriseAndNameAndNotDeletedOrderByNameAndNotMatchVisma(Enterprise enterprise, String name, Pageable pageable);

    List<Organisation> findByEnterpriseAndNameInAndNotDeletedOrderByName(Enterprise enterprise, List<String> name);

    List<Organisation> findByOwnerIn(List<User> userList);

    void removeWhereOrganisationIn(List<Organisation> organisationList);

    List<Organisation> findByIds(Pageable page, Collection<UUID> uuids);

    List<Organisation> findBySharedOrganisationInAndDeleted(List<SharedOrganisation> sharedOrganisationList, boolean deleted);

    void resetMeetingCache();

    List<Organisation> findByNameInAndEnterpriseAndDeleted(List<String> nameList, Enterprise enterprise, boolean deleted);

    List<Organisation> findByUuidInAndOwnerIsNotAndDeleted(List<UUID> uuidList, User owner, boolean deleted);

    List<Organisation> findByUuidInAndOwnerAndDeleted(List<UUID> uuidList, User owner, boolean deleted);

    List<Organisation> findByIdIn(Collection<UUID> uuids);

    List<Organisation> getByIdIn(Collection<UUID> uuids);

    public List<Organisation> search(String name, List<UUID> industryList, List<String> countryList, List<UUID> sizeTypeList, Integer pageIndex, Integer pageSize,
                                     Enterprise enterprise);

    List<Object[]> findOrganisationAndNumberActiveTaskByOrganisationIn(List<Organisation> organisationList);

    List<Organisation> findByEnterpriseAndCreatedAfter(Enterprise enterprise, Date now);


    List<Organisation> findBySharedOrganisationIn(List<SharedOrganisation> removedSharedOrganisationList);

    List<Organisation> findByEnterpriseAndPhone(Enterprise enterprise, String phone);

    List<Organisation> findByNameAndEnterprise(String accountName, Enterprise enterprise);

    List<Organisation> findByOwner(User user);

    Organisation findByEnterpriseAndMaestranoId(Enterprise enterprise, String maestranoId);

    void moveOwner(User oldUser, User newUser);

    List<Organisation> findByNameAndPhoneAndEnterpriseAndDeleted(String name, List<CommunicationType> communicationTypes, List<String> phoneList, Enterprise enterprise, boolean deleted);

    List<Organisation> findByNameAndCityAndStreet(String name, String city, String street, Enterprise enterprise, boolean deleted);

    List<Organisation> findByCityAndPhone(String city, String phone, Enterprise enterprise, boolean deleted);

    List<Organisation> findByNameAndWebAndCityAndStreet(String name, String website, String city, String street, Enterprise enterprise, boolean deleted);

    List<Organisation> findByNameAndWebsite(String name, String website, Enterprise enterprise, boolean deleted);

    List<Organisation> findByNameAndPhoneIn(String name, List<String> newPhoneList, Enterprise enterprise, boolean deleted);

    List<Organisation> findByEnterprise(Enterprise enterprise);

    long countNotDeletedByEnterprise(Enterprise enterprise);

    Double averageMedianDealSizeByEnterprise(Enterprise enterprise);

    Double averageMedianDealTimeByEnterprise(Enterprise enterprise);

    List<Organisation> findUuidAndNameAndEmailAndPhoneBySuggestedDomainAndName(Enterprise enterprise);

    Double averageClosedSalesByEnterprise(Enterprise enterprise);

    Double averageClosedProfitByEnterprise(Enterprise enterprise);

    Double averageClosedMarginByEnterprise(Enterprise enterprise);

    Map<UUID, Organisation> getByUuidIn(Set<UUID> uuids);

    List<Organisation> findByNameAndMediaTypeAndEnterprise(String name, MediaType linkedin, Enterprise enterprise);

    List<String> findLinkedinProfileIdByEnterpriseAndNotDelete(Enterprise enterprise);

    List<String> findLinkedinProfileSalesIdByEnterpriseAndNotDelete(Enterprise enterprise);

    List<Organisation> findByVatOrNameVisma(String vismaId, String vat, String name, Enterprise enterprise);

    List<Organisation> findByEnterpriseAndVismaIsNotNull(Enterprise enterprise);

    Organisation findByEnterpriseByVismaId(Enterprise enterprise, String vismaId);

    List<Organisation> findByEnterpriseByVismaIds(Enterprise enterprise, List<String> vismaId);

    List<Organisation> findByVatAndEnterprise(String vat, Enterprise enterprise);

    Organisation getOrganisationByCustomerIDAndEnterprise(String customerNumber, Enterprise enterprise);

    List<Organisation> findByEnterpriseAndNameAndNotDeletedOrderByNameAndNotMatchFortNox(Enterprise enterprise, String name, Pageable pageable);

    List<UUID> findByEnterpriseAndNoResponsible(Enterprise enterprise, List<User> users);

    List<Object[]> findIdAndEmailByUuidIn(List<UUID> uuidList);

}
