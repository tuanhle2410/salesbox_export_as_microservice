package com.salesbox.dao;

import com.salesbox.entity.SharedContact;
import com.salesbox.entity.SharedOrganisation;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/19/14
 */
public interface SharedContactDAO extends BaseDAO<SharedContact>
{
    SharedContact findOne(UUID uuid);

    List<SharedContact> findAll();

    SharedContact findByEmail(String email);

    int countByEmailIn(List<String> emailList);

    List<SharedContact> findByEmailIn(List<String> emailList);

    List<SharedContact> findByUuidIn(List<UUID> uuidList);

    Long countOrganisationBySharedContactIn(List<SharedContact> sharedContactSet);

    SharedContact findBySharedOrganisationAndNotDeletedOrderByUuidAsc(SharedOrganisation sharedOrganisation);

    Long countSharedContact();

    List<Object[]> findSharedOrganisationAndSharedContactByUuidIn(Set<UUID> uuidSet);

    Long countSharedContactCreatedDateBetween(Date startDate, Date endDate);

    List<Object[]> findPropertiesForCallListByUuidIn(List<UUID> uuidList);

    List<Object[]> findUuidAndCreatorIdByUuidIn(List<UUID> uuidList);

    void removeWhereSharedContactIn(List<SharedContact> sharedContactList);

    List<SharedContact> findBySharedOrganisationIn(List<SharedOrganisation> sharedOrganisationList);
    List<SharedContact> findByCreatorIdNotNullAndFirstnameLastNameContainsSpace();

}
