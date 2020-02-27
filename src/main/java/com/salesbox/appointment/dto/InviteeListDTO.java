package com.salesbox.appointment.dto;

import com.salesbox.dto.CommunicationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnh on 15/07/2014.
 */
public class InviteeListDTO
{
    private List<CommunicationDTO> communicationInviteeDTOList = new ArrayList<CommunicationDTO>();
    private List<ContactLiteDTO> contactInviteeDTOList = new ArrayList<ContactLiteDTO>();

    public List<ContactLiteDTO> getContactInviteeDTOList()
    {
        return contactInviteeDTOList;
    }

    public void setContactInviteeDTOList(List<ContactLiteDTO> contactInviteeDTOList)
    {
        this.contactInviteeDTOList = contactInviteeDTOList;
    }

    public List<CommunicationDTO> getCommunicationInviteeDTOList()
    {
        return communicationInviteeDTOList;
    }

    public void setCommunicationInviteeDTOList(List<CommunicationDTO> communicationInviteeDTOList)
    {
        this.communicationInviteeDTOList = communicationInviteeDTOList;
    }
}
