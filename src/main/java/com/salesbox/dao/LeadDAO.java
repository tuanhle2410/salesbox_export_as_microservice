package com.salesbox.dao;

import com.salesbox.entity.*;
import com.salesbox.entity.enums.LeadType;
import com.salesbox.entity.view.taskRelation.LeadOnContact;
import com.salesbox.entity.view.taskRelation.LeadOnContactOrOrganisation;
import com.salesbox.entity.view.taskRelation.LeadOnOrganisation;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 7/16/14
 */
public interface LeadDAO extends BaseDAO<Lead>
{
    Lead findOne(UUID uuid);

    List<Lead> findUUIDIn(List<UUID> uuidList);

    List<Lead> findByEnterpriseAndUpdatedDateAfterOrderByUuidAsc(Enterprise enterprise, Date updatedDate, Integer pageIndex, Integer pageSize);

    List<Lead> findByContactAndFinishedAndDeleted(Contact contact, boolean finished, boolean deleted);
    
    List<Lead> findByContactInAndFinishedAndDeleted(List<Contact> contactList, boolean finished, boolean deleted);

    Integer countByContactAndDeleted(Contact contact, boolean deleted);

    Integer countByContactAndFinished(Contact contact, boolean finished);

    Lead findByProspectAndFinishedAndDeleted(ProspectBase prospect, boolean finished, boolean deleted);

    Lead findByProspectIdAndFinishedAndDeleted(UUID prospectId, boolean finished, boolean deleted);

    List<Lead> findByProspectIdInAndFinishedAndDeleted(List<UUID> prospectIds, boolean finished, boolean deleted);

    Lead findByProspectIdAndDeleted(UUID prospectId, boolean deleted);

    Lead findByCampaignAndFacebookId(Campaign campaign, String facebookId);

    Lead findByCampaignAndLinkedInId(Campaign campaign, String linkedInId);

    Lead findByCampaignAndMailChimpId(Campaign campaign, String mailChimpId);

    List<Lead> findByOwnerAndDeletedAndFinished(User user, boolean deleted, boolean finished);

    List<Lead> findByCampaignAndType(Campaign campaign, LeadType type);

    List<LeadOnContact> getLeadOnContact(UUID contactId);

    List<Lead> getLeadOnListContactAndActiveAndOrganisationAndOwner(List<Contact> contacts, Organisation organisation, User user);

    List<LeadOnOrganisation> getLeadOnOrganisation(UUID contactId);

    List<LeadOnContactOrOrganisation> getLeadOnContactOrOrganisation(List<UUID> contactIdList, List<UUID> organisationIdList, Boolean isFinished);

    Long countNumberAddedLeadByCampaign(Enterprise enterprise, Date startDate, Date endDate);

    Long countNumberAddedLeadByCampaignHasProspectId(Enterprise enterprise, Date startDate, Date endDate);

    Long countNumberAddedLead();

    Long countNumberFinishedLead();

    Long countNumberConvertedLead();

    Long countNumberAddedLeadCreatedDateBetween(Date startDate, Date endDate);

    Long countNumberFinishedLeadCreatedDateBetween(Date startDate, Date endDate);

    Long countNumberConvertedLeadCreatedDateBetween(Date startDate, Date endDate);

    Long countByOrganisationAndFinishedAndDeleted(Organisation organisation, boolean finished, boolean deleted);

    List<Lead> findByContactAndCreatedDateBetweenAndDeleted(Contact contact, Date startDate, Date endDate, boolean deleted);

    List<Lead> findByContactAndDeleted(Contact contact, boolean deleted);

    List<Lead> findByContactAndDeletedAndNoteNotNull(Contact contact, boolean deleted);

    List<Lead> findByOrganisationAndDeletedAndNoteNotNull(Organisation organisation, boolean deleted);

    Integer countByOrganisationAndDeleted(Organisation organisation, boolean deleted);

    List<Lead> findByOrganisationAndCreatedDateBetweenAndDeleted(Organisation organisation, Date startDate, Date endDate, boolean deleted);

    List<Lead> findByOrganisationAndDeleted(Organisation organisation, boolean deleted);

    List<Lead> findByOrganisationAndDeletedAndFinished(Organisation organisation, boolean deleted, boolean finished);

    List<Lead> findByOrganisationInAndDeleted(List<Organisation> organisations, boolean deleted);

    List<Lead> findByOrganisationAndContact(Organisation organisation, Contact contact, Enterprise enterprise);

    List<Lead> findByUserAndOrderByAndIsShowHistoryAndPageableAndCreatedDateBetween(List<User> userList, String orderBy, boolean showHistory, Pageable pageable, Date startDate, Date endDate, Enterprise enterprise);

    List<Lead> findByUserListAndOrderByAndActiveAndNotOwnerAndPageableAndCreatedDateBetween(List<User> userList, String orderBy, Pageable pageable, Date startDate, Date endDate, Enterprise enterprise);

    Long countListByUserListAndActiveAndNotOwnerAndCreatedDateBetween(List<User> userList, Date startDate, Date endDate, Enterprise enterprise);

    List<Lead> findByIds(List<UUID> uuidList);

    Long countByContactAndFinishedAndDeleted(Contact contact, boolean finished, boolean deleted);

    void removeWhereLeadIn(List<Lead> leadList);

    List<Lead> findByOwnerInOrCreatorIn(List<User> userList);

    long countByContactOrganisationAndFinishedAndDeleted(Organisation organisation, boolean finished, boolean deleted);

    List<Lead> findByOwnerOrContactIn(User user, List<Contact> contactList);

    void moveOwner(User oldUser, User newUser);

    void moveCreator(User oldUser, User newUser);

    List<Lead> findByContactInAndDeleted(List<Contact> contactList);

    List<Object[]> findByUserOrCreatorInAndDeletedAndCreatedBetween(List<User> userList, boolean deleted, Date startDate, Date endDate);

    Long countByOrganisationAndDeletedAndFinished(Organisation organisation, boolean deleted, boolean finished);

    List<Object[]> findIdAndProspectIdByCampaignInAndDeleted(List<Campaign> campaignList, Boolean deleted);

    Lead findByContactIdAndLineOfBusinessId(UUID contactId, UUID lineOfBusinessId);

    Lead findByLeadBoxerIdAndEnterprise(String leadBoxerId, Enterprise enterprise);

    Lead findByAccountNameAndEmailAndNameAndEnterprise(String accountName, String email, String firstName, String lastName, Enterprise enterprise);

    Lead findByLeadBoxerIdAndEnterprise(String leadBoxerId, Enterprise enterprise, boolean isDeleted);

    Lead findByAccountIdAndContactIdAndLineOfBusinessId(UUID accountId, UUID contactId, UUID lineOfBusinessId, Enterprise enterprise);

    UUID findMostCommonProductGroupIdInPeriod(Enterprise enterprise, Date startDate, Date endDate);

    Lead findByAccountAndContactIsNull(String accountName, Enterprise enterprise);

    Lead findByContactAndAccountIsNull(String contactFirstName, String contactLastName, Enterprise enterprise);

    List<Lead> findLeadNeedToUpdateVisitMore(Enterprise enterprise);

    void removeProspectIdWhereProspectIn(List<UUID> listProspectId);

    Long countActiveStartDateAndEndDateBetween(Date startDate, Date endDate, List<User> userList);

    Long countNewStartDateAndEndDateBetween(Date startDate, Date endDate, List<User> userList);

    Long countDoneStartDateAndEndDateBetween(Date startDate, Date endDate, List<User> userList);

    Long countDoneAndProspectIdStartDateAndEndDateBetween(Date startDate, Date endDate, List<User> userList);

    List<Lead> findbyOrganisationAndNullContactAndEnterPrise(UUID accountId, Enterprise enterprise);

    List<Lead> findNoteByOrganisation(Organisation organisation);

    List<UUID> findUUIDByOrganisation(Organisation organisation);

    List<UUID> findLeadWithContact(Contact contact);


    List<UUID> findUUIDByProspect(ProspectBase prospect);

    List<Lead> findLeadWithContactAndNote(Contact contact);

    List<Lead> findLeadByProspectAndNote(ProspectBase prospect);

    List<Lead> findLeadByOrganisationAndNote(Organisation organisation);

    Integer countLeadHaveNote(Contact contact);

    Integer countLeadByPropsectAndNote(ProspectBase prospect);

    int countLeadByOrganisationAndNote(Organisation organisation);

    List<Lead> findByIdsAndNotDeletedAndNotFinished(List<UUID> uuids, Boolean deleted, Boolean finished);

    int countbyLeadIdsAndNote(List<UUID> leadIds);

    List<Lead> findByUserListAndOrderByAndActiveAndNotOwnerAndPageable(List<User> users, String orderBy, Pageable pageable, Enterprise enterprise);

    long countListByUserListAndActiveAndNotOwner(List<User> users, Enterprise enterprise);

    List<Lead> findByContactAndDeletedAndFinished(Contact contact, boolean deleted, boolean finished);

    List<Lead> findActiveLeadWithOwnerByAfter60Day(User user, Date start, Pageable pageable);

    List<Lead> findLeadInDelegationByAfter60Day(Enterprise enterprise, Date start, Pageable pageable);


}