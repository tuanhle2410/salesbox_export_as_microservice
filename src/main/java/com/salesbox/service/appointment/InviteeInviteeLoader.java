package com.salesbox.service.appointment;

import com.salesbox.appointment.dto.AppointmentListDTO;
import com.salesbox.dao.AppointmentInviteeInviteeDAO;
import com.salesbox.dto.CommunicationDTO;
import com.salesbox.entity.Appointment;
import com.salesbox.entity.AppointmentInviteeInvitee;
import com.salesbox.entity.Communication;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hunglv on 7/16/14.
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class InviteeInviteeLoader
{
    @Autowired
    AppointmentInviteeInviteeDAO appointmentInviteeInviteeDAO;

    @Autowired
    MapperFacade mapper;

    public void load(List<Appointment> appointmentList, AppointmentListDTO appointmentListDTO)
    {
        Map<Appointment, ArrayList<CommunicationDTO>> inviteeInviteeMap = new HashMap<Appointment, ArrayList<CommunicationDTO>>();

        loadDataToMap(appointmentList, inviteeInviteeMap);
        putDataToDTOList(appointmentList, appointmentListDTO, inviteeInviteeMap);
    }

    private void putDataToDTOList(List<Appointment> appointmentList, AppointmentListDTO appointmentListDTO, Map<Appointment,
            ArrayList<CommunicationDTO>> communicationMap)
    {
        int index = 0;
        for (Appointment appointment : appointmentList)
        {
            appointmentListDTO.getAppointmentDTOList().get(index).getInviteeList()
                    .setCommunicationInviteeDTOList(communicationMap.get(appointment));
            index++;
        }
    }

    private void loadDataToMap(List<Appointment> appointmentList, Map<Appointment, ArrayList<CommunicationDTO>> communicationMap)
    {
        List<AppointmentInviteeInvitee> appointmentInviteeInviteeList = new ArrayList<AppointmentInviteeInvitee>();

        if (appointmentList.size() > 0)
        {
            appointmentInviteeInviteeList = appointmentInviteeInviteeDAO.findByAppointmentIn(appointmentList);
        }

        Map<Appointment, ArrayList<Communication>> communicationArrayMap = new HashMap<Appointment, ArrayList<Communication>>();

        for (AppointmentInviteeInvitee appointmentInviteeInvitee : appointmentInviteeInviteeList)
        {
            if (communicationArrayMap.get(appointmentInviteeInvitee.getAppointment()) == null)
            {
                ArrayList<Communication> communicationArrayList = new ArrayList<Communication>();

                communicationArrayList.add(appointmentInviteeInvitee.getCommunication());
                communicationArrayMap.put(appointmentInviteeInvitee.getAppointment(), communicationArrayList);
            }
            else
            {
                communicationArrayMap.get(appointmentInviteeInvitee.getAppointment()).add(appointmentInviteeInvitee.getCommunication());
            }
        }

        for (Appointment appointment : appointmentList)
        {
            List<Communication> communicationList = communicationArrayMap.get(appointment);

            ArrayList<CommunicationDTO> communicationDTOList = new ArrayList<CommunicationDTO>();
            if (communicationList != null)
            {
                for (Communication communication : communicationList)
                {
                    CommunicationDTO communicationDTO = new CommunicationDTO(communication);
                    communicationDTO.setValue(communication.getValue());
                    communicationDTO.setAccepted(findByCommunication(appointmentInviteeInviteeList, communicationDTO).getAccepted());
                    communicationDTOList.add(communicationDTO);

                }
            }
            communicationMap.put(appointment, communicationDTOList);
        }
    }

    private AppointmentInviteeInvitee findByCommunication(List<AppointmentInviteeInvitee> appointmentInviteeInviteeList,
                                                          CommunicationDTO communicationDTO)
    {
        for(AppointmentInviteeInvitee appointmentInviteeInvitee : appointmentInviteeInviteeList)
        {
            if(appointmentInviteeInvitee.getCommunication().getUuid().equals(communicationDTO.getUuid()))
            {
                return appointmentInviteeInvitee;
            }
        }
        return null;
    }
}
