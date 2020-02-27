package com.salesbox.dao;

import com.salesbox.entity.SharedOrganisation;
import com.salesbox.entity.enums.CommunicationType;
import com.salesbox.entity.enums.MediaType;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/18/14
 */
public interface SharedOrganisationDAO extends BaseDAO<SharedOrganisation>
{
    List<SharedOrganisation> findAll();

    SharedOrganisation findOne(UUID uuid);

    SharedOrganisation findByEmail(String email);

    List<SharedOrganisation> findByUuidIn(List<UUID> uuidList);

    long countByEmail(String email);

    Long countSharedOrganisation();
    List<SharedOrganisation> findAllByPage(int pageIndex, int pageSize);

    Long countSharedOrganisationCreatedDateBetween(Date startDate, Date endDate);

    List<SharedOrganisation> findByNameOrderByNameDesc(String name, Integer pageIndex, Integer pageSize);

    List<SharedOrganisation> findByNameIn(Collection<String> names);

    void removeWhereShareOrganisationIn(List<SharedOrganisation> sharedOrganisations);

    List<String> findDuplicationEmail();

    List<Object[]> findSharedOrganisationByEmailIn(List<String> emailList);

    List<SharedOrganisation> findByNameAndPhone(String name, List<String> phoneList, List<CommunicationType> organisationAdditionalPhoneTypes);

    List<SharedOrganisation> findByNameAndCityAndStreet(String name, String city, String street);

    List<SharedOrganisation> findByNameAndWebsiteAndCityAndStreet(String name, String website, String city, String street);

    List<SharedOrganisation> findByNameAndWebsite(String name, String website);

    List<SharedOrganisation> findByNameAndMediaType(String name, MediaType mediaType);
}
