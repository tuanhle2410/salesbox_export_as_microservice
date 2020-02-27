package com.salesbox.dao;

import com.salesbox.entity.*;
import com.salesbox.entity.enums.NoteSourceType;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/23/14
 * Time: 10:07 AM
 */
public interface NoteDAO extends BaseDAO<Note>
{
    public Note findOne(UUID uuid);

    public List<Note> findByProspectOrderByUpdatedDateDesc(ProspectBase prospect);

    public Note findLatestByProspectOrderByUpdatedDateDesc(UUID prospectId);

    public List<Note> findByProspectAndCreatedDateBetween(ProspectBase prospect, Date startDate, Date endDate);

    public List<Note> findByProspectAndCreatedDateBetween(ProspectBase prospect, Date startDate, Date endDate, Integer pageIndex, Integer pageSize);

    public List<Note> findByProspectAndCreatedDateBetween(ProspectBase prospect, Date startDate, Date endDate, Pageable pageable);

    public List<Note> findByContactAndCreatedDateBetween(Contact contact, Date startDate, Date endDate);

    public List<Note> findByContactAndCreatedDateBetween(Contact contact, Date startDate, Date endDate, Integer pageIndex, Integer pageSize);

    public List<Note> findByOrganisationOrderByUpdatedDateDesc(Organisation organisation);

    public Integer countByProspect(ProspectBase prospect);

    public Integer countByProspectId(UUID prospectId);

    public Integer countByContact(Contact contact);

//    public Integer countByTask(Task task);

    public Integer countByLead(UUID leadId);

    public List<Note> findByContactOrderByUpdatedDateDesc(Contact contact);

    public Integer countByContactOrderByUpdatedDateDesc(Contact contact);

    public List<Note> findByOrganisationAndCreatedDateBetween(Organisation organisation, Date start, Date end);

    public List<Note> findByOrganisationAndCreatedDateBetween(Organisation organisation, Date start, Date end, Integer pageIndex, Integer pageSize);

    public Integer countByOrganisation(Organisation organisation);

    void removeWhereAuthorIn(List<User> userList);

    void removeWhereContactInAndSourceType(Set<Contact> contactSet, NoteSourceType sourceType);

    List<Note> findByUser(User user);

    List<Note> findByContactIn(List<Contact> contactList);

    List<Note> findByLatestCommunicationIn(List<UUID> latestCommunicationIds);

    List<Note> findByOrganisationIn(List<Organisation> organisationList);

    List<Object[]> findLatestByProspectIdIn(Set<UUID> prospectIdList);

    List<Note> findByListContactUUID(List<UUID> contactUUIDList);

    Integer countByListContactUUID(List<UUID> contactUUIDList);

    List<Note> findNoteByProspectIdIn(List<UUID> prospectIdList);

    Integer countNoteByProspectIdIn(List<UUID> prospectIdList);

    List<Note> findByLeadOrderByUpdatedDateDesc(Lead lead);

    List<Note> findByTaskOrderByUpdatedDateDesc(Task task);

    List<Note> findByAppointmentOrderByUpdatedDateDesc(Appointment appointment);

    List<Note> findNoteByLeadIdIn(List<UUID> leadIds);

    Integer countNoteByLeadIdIn(List<UUID> leadIds);

    void deleteByListProspectOrListContactOrListOrganisation(List<UUID> prospectIds, List<Contact> contacts, List<Organisation> organisations, List<UUID> leadIds);

    List<Object[]> findLatestByAccountIdIn(Set<UUID> accountIdList);

    List<Object[]> findLatestByContactIdIn(Set<UUID> accountIdList);
}
