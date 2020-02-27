package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by hungnh on 4/22/15.
 */
@Entity
@Table(name = "call_list_shared_contact_communication")
public class CallListSharedContactCommunication extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_list_id")
    private CallList callList;

    @Column(name = "contact_id")
    @Type(type = "pg-uuid")
    private UUID contactId;

    @Column(name = "communication_history_id")
    @Type(type = "pg-uuid")
    private UUID communicationHistoryId;

    public UUID getContactId()
    {
        return contactId;
    }

    public void setContactId(UUID contactId)
    {
        this.contactId = contactId;
    }

    public CallList getCallList()
    {
        return callList;
    }

    public void setCallList(CallList callList)
    {
        this.callList = callList;
    }

    public UUID getCommunicationHistoryId()
    {
        return communicationHistoryId;
    }

    public void setCommunicationHistoryId(UUID communicationHistoryId)
    {
        this.communicationHistoryId = communicationHistoryId;
    }
}
