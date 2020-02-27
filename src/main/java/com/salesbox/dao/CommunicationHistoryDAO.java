package com.salesbox.dao;

import com.salesbox.entity.*;
import com.salesbox.entity.enums.CommunicationHistoryType;
import org.springframework.data.domain.PageRequest;

import java.util.*;

/**
 * Created by hungnh on 25/07/2014.
 */
public interface CommunicationHistoryDAO extends BaseDAO<CommunicationHistory>
{
    CommunicationHistory findOne(UUID uuid);

    List<CommunicationHistory> findByEnterpriseAndSharedContactAndPageable(Enterprise enterprise, SharedContact sharedContact, PageRequest pageable);

    List<CommunicationHistory> findByEnterpriseAndSharedContactAndPageableTypeIsNotSenderAndNotEmptyContent(Enterprise enterprise, SharedContact sharedContact, PageRequest pageable);

    List<CommunicationHistory> findByEnterpriseAndSharedContactAndPageableTenPlus(Enterprise enterprise, SharedContact sharedContact, PageRequest pageable, Date startDate, Date endDate, CommunicationHistoryType cHType, String content);

    List<CommunicationHistory> findByUserAndSharedContactAndPageable(User user, SharedContact sharedContact, PageRequest pageable);

    List<CommunicationHistory> findByUnitAndSharedContactAndPageable(Unit unit, SharedContact sharedContact, PageRequest pageable);

    List<CommunicationHistory> findByUserAndSharedContactAndStartDateAfter(User user, SharedContact sharedContact, Date lastDate);

    List<CommunicationHistory> findByUnitAndSharedContact(Unit unit, SharedContact sharedContact, Date lastDate);

    List<CommunicationHistory> findByEnterpriseAndSharedContact(Enterprise enterprise, SharedContact sharedContact, Date lastDate);

    CommunicationHistory findLatestByUserAndTypeIn(User user, List<CommunicationHistoryType> communicationHistoryTypeList);

    List<CommunicationHistory> findByUserAndTypeInOrderByDateCreatedAsc(User user, List<CommunicationHistoryType> communicationHistoryTypeList);

    List<CommunicationHistory> findByUserAndTypeInOrderByDateAfterDate(User user, Date afterDate, List<CommunicationHistoryType> communicationHistoryTypeList);


    List<CommunicationHistory> findByUserAndSharedContactInOrOrganisation(User user, List<SharedContact> sharedContactList, Organisation organisation, Date lastDate);

    List<CommunicationHistory> findByUnitAndSharedContactInOrOrganisation(Unit unit, List<SharedContact> sharedContactList, Organisation organisation, Date lastDate);

    List<CommunicationHistory> findByEnterpriseAndSharedContactInOrOrganisation(Enterprise enterprise, List<SharedContact> sharedContactList, Organisation organisation, Date lastDate);

    List<CommunicationHistory> findByUserAndSharedContactInOrOrganisationAndPageable(User user, List<SharedContact> sharedContactList, Organisation organisation, PageRequest pageRequest);

    List<CommunicationHistory> findByUnitAndSharedContactInOrOrganisationAndPageable(Unit unit, List<SharedContact> sharedContactList, Organisation organisation, PageRequest pageRequest);

    List<CommunicationHistory> findByEnterpriseAndSharedContactInOrOrganisationAndPageable(Enterprise enterprise, List<SharedContact> sharedContactList, Organisation organisation, PageRequest pageRequest);

    List<CommunicationHistory> findByUserInAndTypeInAndStartDateBetween(List<User> userList, List<CommunicationHistoryType> dialOrCallType,
                                                                        Date startDate, Date endDate);

    List<CommunicationHistory> findByUnitInAndTypeInAndStartDateBetween(List<Unit> unitList, List<CommunicationHistoryType> dialOrCallType,
                                                                        Date startDate, Date endDate);

    Long countByUserAndTypeInWeek(User user, List<CommunicationHistoryType> type, Date startDate, Date endDate);

    CommunicationHistory findLatestByProspectAndTypeIn(ProspectBase prospect, List<CommunicationHistoryType> dialOrCallType);

    Long countNumberPerformedCommunicationByTypeIn(List<CommunicationHistoryType> communicationTypeList);

    List<Object[]> findByTypeInAndPage(List<CommunicationHistoryType> communicationHistoryTypeList, int pageIndex, int maxPageSize);

    Long countNumberPerformedCommunicationByTypeInAndCreatedDateBetween(List<CommunicationHistoryType> communicationHistoryTypeList, Date startDate, Date endDate);

    List<Object[]> findByTypeInAndPageAndCreatedDateBetween(List<CommunicationHistoryType> typeList, Date startDate, Date endDate, int pageIndex, int pageSize);

    List<UUID> findSharedContactIdByUserInAndStartDateAfter(List<User> userList, Date date);

    List<Object[]> findStartDateAndDurationAndTypeByUserInAndTypeInAndStartDateBetween(List<User> userList, List<CommunicationHistoryType> communicationHistoryTypeList, Date startDate, Date endDate);

    List<Object[]> findStartDateAndTypeByUserIdInAndTypeInAndStartDateBetween(List<UUID> userIdList, List<CommunicationHistoryType> communicationHistoryTypeList, Date startDate, Date endDate);

    Double getCompanyMinAverageDurationPersonalRecordByEnterpriseAndStartDateBetweenAndTypeIn(Enterprise enterprise, Date startDate, Date endDate, List<CommunicationHistoryType> typeList);

    Double getCompanyMinAverageDurationUnitRecordByEnterpriseAndStartDateBetweenAndTypeIn(Enterprise enterprise, Date startDate, Date endDate, List<CommunicationHistoryType> typeList);

    List<Object[]> findStartDateAndDurationAndTypeAndUserIdAndUnitIdByEnterpriseAndTypeInAndStartDateBetween(Enterprise enterprise, List<CommunicationHistoryType> communicationHistoryTypeList, Date startDate, Date endDate);

    Long countByUserInAndTypeInAndStartDateBetween(List<User> userList, List<CommunicationHistoryType> typeList, Date startDate, Date endDate);

    CommunicationHistory findLatestByUserAndTypeInAndBefore(User user, List<CommunicationHistoryType> dialOrCallType, Date createdDate);

    List<Object[]> findPropertiesByEnterpriseAndSharedContactIdInOrOrganisationAndPageable(Enterprise enterprise, List<UUID> sharedContactIdList, Organisation organisation, PageRequest pageRequest);

    List<Object[]> findPropertiesByEnterpriseAndSharedContactIdInOrOrganisationAndPageableTenPlus(Enterprise enterprise, List<UUID> sharedContactIdList, Organisation organisation, PageRequest pageRequest, Date startDate, Date endDate, CommunicationHistoryType cHType, String content);

    List<Object[]> findPropertiesByEnterpriseAndStartDateBetweenAndSharedContactIdInOrOrganisation(Enterprise enterprise, Date startDate, Date endDate, List<UUID> sharedContactIdList, Organisation organisation);

    List<Object[]> findPropertiesByUnitAndStartDateBetweenAndSharedContactIdInOrOrganisation(Unit unit, Date startDate, Date endDate, List<UUID> sharedContactIdList, Organisation organisation);

    List<Object[]> findPropertiesByUserAndStartDateBetweenAndSharedContactIdInOrOrganisation(User user, Date startDate, Date endDate, List<UUID> sharedContactIdList, Organisation organisation);

    List<CommunicationHistory> findByUserIn(List<User> userList);

    List<CommunicationHistory> findByUserInAndTypeIn(List<User> userList, List<CommunicationHistoryType> communicationHistoryTypes);

    void removeWhereCommunicationHistoryIn(List<CommunicationHistory> communicationHistoryList);

    List<CommunicationHistory> findByIdIn(Collection<UUID> uuids);

    List<Object[]> findUserIdAndNumberCommunicationsByUserInAndTypeInAndStartDateBetween(List<User> userList, List<CommunicationHistoryType> typeList, Date startDate, Date endDate);

    Map<UUID, Long> getByUserInAndTypeInAndStartDateBetween(List<User> userList, List<CommunicationHistoryType> communicationHistoryTypes, Date startDate, Date endDate);

    Long getByUserInAndTypeInAndStartDateBetween(User user, List<CommunicationHistoryType> communicationHistoryTypes, Date startDate, Date endDate);

    Long getByUnitInAndTypeInAndStartDateBetween(Unit unit, List<CommunicationHistoryType> communicationHistoryTypes, Date startDate, Date endDate);

    Long getByEnterpriseAndTypeInAndStartDateBetween(Enterprise enterprise, List<CommunicationHistoryType> communicationHistoryTypes, Date startDate, Date endDate);

    Long getByBestRecordOfUserCompanyInAndTypeInAndStartDateBetween(Enterprise enterprise, List<CommunicationHistoryType> communicationHistoryTypes, Date startDate, Date endDate);

    Long getByBestRecordOfUnitInAndTypeInAndStartDateBetween(Enterprise enterprise, List<CommunicationHistoryType> communicationHistoryTypes, Date startDate, Date endDate);

    Map<UUID, Long> getUnitAndCountDialByTypeInAndStartDateBetween(Enterprise enterprise, List<CommunicationHistoryType> communicationHistoryTypes, Date startDate, Date endDate);

    Map<UUID, Long> getUnitAndCountCallByTypeInAndStartDateBetween(Enterprise enterprise, List<CommunicationHistoryType> communicationHistoryTypes, Date startDate, Date endDate);

    List<CommunicationHistory> findByTrackingCodeAndType(String trackingCode, CommunicationHistoryType email);

    List<CommunicationHistory> findByTrackingUrlCodeAndType(String trackingCode, CommunicationHistoryType email);

    List<CommunicationHistory> findByTrackingAttachmentCodeAndType(String trackingCode, CommunicationHistoryType email);

    List<String> findOutLookIdByUser(User user);

    List<CommunicationHistory> findBySharedContactIdAndUserOrderByDateDescPageable(Enterprise enterprise, List<UUID> sharedContactIdList, Integer pageIndex, Integer pageSize);

    CommunicationHistory findLatestByOrganisation(Organisation prospect);

    CommunicationHistory findLatestByContact(Contact contact);

    List<CommunicationHistory>  findByTrackingCode(String code);

    void deleteByContactIn(List<Contact> deletedContactList);
}
