package com.salesbox.entity;

import com.salesbox.entity.enums.NoteSourceType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * User: luult
 * Date: 5/20/14
 */
@Entity
@Table(name = "note")
public class Note extends BaseEntity
{
    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "lead_id")
    private UUID leadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(name = "source_type")
    @Enumerated(EnumType.ORDINAL)
    private NoteSourceType sourceType = NoteSourceType.MANUALLY;

    @Column(name = "latest_communication_id")
    @Type(type = "pg-uuid")
    @Basic
    private UUID latestCommunicationId;

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public NoteSourceType getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(NoteSourceType sourceType)
    {
        this.sourceType = sourceType;
    }

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }

    public UUID getLeadId()
    {
        return leadId;
    }

    public void setLeadId(UUID leadId)
    {
        this.leadId = leadId;
    }

    public UUID getLatestCommunicationId()
    {
        return latestCommunicationId;
    }

    public void setLatestCommunicationId(UUID latestCommunicationId)
    {
        this.latestCommunicationId = latestCommunicationId;
    }
}
