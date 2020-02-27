package com.salesbox.service.appointment;

import com.salesbox.appointment.dto.AppointmentListDTO;
import com.salesbox.appointment.dto.ContactLiteDTO;
import com.salesbox.dao.AppointmentInviteeContactDAO;
import com.salesbox.entity.Appointment;
import com.salesbox.entity.AppointmentInviteeContact;
import com.salesbox.entity.Contact;
import com.salesbox.entity.Enterprise;
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
public class ContactInviteeLoader
{
    @Autowired
    AppointmentInviteeContactDAO appointmentInviteeContactDAO;

    @Autowired
    MapperFacade mapper;

    public void load(List<Appointment> appointmentList, AppointmentListDTO appointmentListDTO, Enterprise enterprise)
    {
        Map<Appointment, ArrayList<ContactLiteDTO>> participantMap = new HashMap<Appointment, ArrayList<ContactLiteDTO>>();

        loadDataToMap(appointmentList, participantMap, enterprise);
        putDataToDTOList(appointmentList, appointmentListDTO, participantMap);
    }

    private void putDataToDTOList(List<Appointment> appointmentList, AppointmentListDTO appointmentListDTO, Map<Appointment, ArrayList<ContactLiteDTO>> contactMap)
    {
        int index = 0;
        for (Appointment appointment : appointmentList)
        {
            appointmentListDTO.getAppointmentDTOList().get(index).getInviteeList().setContactInviteeDTOList(contactMap.get(appointment));
            index++;
        }
    }

    private void loadDataToMap(List<Appointment> appointmentList, Map<Appointment, ArrayList<ContactLiteDTO>> contactMap, Enterprise enterprise)
    {
        List<AppointmentInviteeContact> appointmentInviteeContactList = new ArrayList<AppointmentInviteeContact>();

        if (appointmentList.size() > 0)
        {
            appointmentInviteeContactList = appointmentInviteeContactDAO.findByAppointmentInAndContactEnterprise(appointmentList, enterprise);
        }

        Map<Appointment, ArrayList<Contact>> contactAppointmentMap = new HashMap<Appointment, ArrayList<Contact>>();

        for (AppointmentInviteeContact appointmentInviteeContact : appointmentInviteeContactList)
        {
            if (contactAppointmentMap.get(appointmentInviteeContact.getAppointment()) == null)
            {
                ArrayList<Contact> contactArrayList = new ArrayList<Contact>();

                contactArrayList.add(appointmentInviteeContact.getContact());
                contactAppointmentMap.put(appointmentInviteeContact.getAppointment(), contactArrayList);
            }
            else
            {
                contactAppointmentMap.get(appointmentInviteeContact.getAppointment()).add(appointmentInviteeContact.getContact());
            }
        }

        for (Appointment appointment : appointmentList)
        {
            List<Contact> contactList = contactAppointmentMap.get(appointment);

            ArrayList<ContactLiteDTO> contactLiteDTOList = new ArrayList<ContactLiteDTO>();
            if (contactList != null)
            {
                for (Contact contact : contactList)
                {
                    ContactLiteDTO contactLiteDTO = new ContactLiteDTO(contact);

                    contactLiteDTO.setAccepted(findByContactLiteDTO(appointmentInviteeContactList, contactLiteDTO).getAccepted());
                    if (contact.getEnterprise().getUuid() != enterprise.getUuid())
                    {
                        contactLiteDTO.setUuid(null);
                        contactLiteDTO.setOrganisationId(null);
                    }

                    contactLiteDTOList.add(contactLiteDTO);
                }
            }
            contactMap.put(appointment, contactLiteDTOList);
        }
    }

    private AppointmentInviteeContact findByContactLiteDTO(List<AppointmentInviteeContact> appointmentInviteeContactList,
                                                           ContactLiteDTO contactLiteDTO)
    {
        for (AppointmentInviteeContact appointmentInviteeContact : appointmentInviteeContactList)
        {
            if (appointmentInviteeContact.getContact().getUuid().equals(contactLiteDTO.getUuid()))
            {
                return appointmentInviteeContact;
            }
        }
        return null;
    }
}
