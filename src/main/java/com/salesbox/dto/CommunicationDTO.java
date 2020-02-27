package com.salesbox.dto;

import com.salesbox.entity.Communication;
import com.salesbox.entity.CommunicationOrganisation;
import com.salesbox.entity.SharedCommunication;
import com.salesbox.entity.SharedCommunicationOrganisation;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 9/12/14
 * Time: 11:47 AM
 */
public class CommunicationDTO implements Serializable
{
    private UUID uuid;
    private String value;
    private String type;
    private Boolean main;

    private UUID pendingId;
    private Boolean isPrivate;
    private UUID creatorId;

    // for Appointment service
    private Boolean accepted;

    public CommunicationDTO()
    {

    }

    public CommunicationDTO(SharedCommunicationOrganisation sharedCommunicationOrganisation)
    {
        this.setUuid(sharedCommunicationOrganisation.getUuid());
        this.setValue(sharedCommunicationOrganisation.getValue().toLowerCase());
        this.setType(sharedCommunicationOrganisation.getType().toString());
        this.setMain(sharedCommunicationOrganisation.getMain());
    }

    public CommunicationDTO(SharedCommunication sharedCommunication)
    {
        this.setUuid(sharedCommunication.getUuid());
        this.setValue(sharedCommunication.getValue().toLowerCase());
        this.setType(sharedCommunication.getType().toString());
        this.setMain(sharedCommunication.getMain());
    }

    public CommunicationDTO(CommunicationOrganisation communicationOrganisation)
    {
        this.setUuid(communicationOrganisation.getUuid());
        this.setValue(communicationOrganisation.getValue());
        this.setType(communicationOrganisation.getType().toString());
        this.setMain(communicationOrganisation.getMain());
    }

    public CommunicationDTO(Communication communication)
    {
        this.setUuid(communication.getUuid());
        this.setValue(communication.getValue());
        this.setType(communication.getType().toString());
        this.setMain(communication.getMain());
    }

    public CommunicationDTO(String value, String type, boolean main, boolean isPrivate)
    {
        this.value = value;
        this.type = type;
        this.main = main;
        this.isPrivate = isPrivate;
    }

    public Boolean getAccepted()
    {
        return accepted;
    }

    public void setAccepted(Boolean accepted)
    {
        this.accepted = accepted;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Boolean getMain()
    {
        return main;
    }

    public void setMain(Boolean main)
    {
        this.main = main;
    }

    public UUID getPendingId()
    {
        return pendingId;
    }

    public void setPendingId(UUID pendingId)
    {
        this.pendingId = pendingId;
    }

    public Boolean getIsPrivate()
    {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate)
    {
        this.isPrivate = isPrivate;
    }

    public UUID getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId)
    {
        this.creatorId = creatorId;
    }
}

