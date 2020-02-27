package com.salesbox.service.appointment;

import com.salesbox.appointment.dto.AppointmentListDTO;
import com.salesbox.appointment.dto.ContactLiteDTO;
import com.salesbox.dao.AppointmentContactDAO;
import com.salesbox.entity.Appointment;
import com.salesbox.entity.AppointmentContact;
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
public class ContactLoader
{
    @Autowired
    AppointmentContactDAO appointmentContactDAO;

    @Autowired
    MapperFacade mapper;

    public void load(List<Appointment> appointmentList, AppointmentListDTO appointmentListDTO, Enterprise enterprise)
    {
        Map<Appointment, ArrayList<ContactLiteDTO>> contactMap = new HashMap<Appointment, ArrayList<ContactLiteDTO>>();

        loadDataToMap(appointmentList, contactMap, enterprise);
        putDataToDTOList(appointmentList, appointmentListDTO, contactMap);
    }

    private void putDataToDTOList(List<Appointment> appointmentList, AppointmentListDTO appointmentListDTO, Map<Appointment, ArrayList<ContactLiteDTO>> contactMap)
    {
        int index = 0;
        for (Appointment appointment : appointmentList)
        {
            appointmentListDTO.getAppointmentDTOList().get(index).setContactList(contactMap.get(appointment));
            index++;
        }
    }

    private void loadDataToMap(List<Appointment> appointmentList, Map<Appointment, ArrayList<ContactLiteDTO>> contactMap, Enterprise enterprise)
    {
        List<AppointmentContact> appointmentContactList = new ArrayList<AppointmentContact>();

        if (appointmentList.size() > 0)
        {
            appointmentContactList = appointmentContactDAO.findByAppointmentInAndContactEnterprise(appointmentList, enterprise);
        }

        Map<Appointment, ArrayList<Contact>> contactAppointmentMap = new HashMap<Appointment, ArrayList<Contact>>();

        for (AppointmentContact appointmentContact : appointmentContactList)
        {
            if (contactAppointmentMap.get(appointmentContact.getAppointment()) == null)
            {
                ArrayList<Contact> contactArrayList = new ArrayList<Contact>();

                contactArrayList.add(appointmentContact.getContact());
                contactAppointmentMap.put(appointmentContact.getAppointment(), contactArrayList);
            }
            else
            {
                contactAppointmentMap.get(appointmentContact.getAppointment()).add(appointmentContact.getContact());
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

                    contactLiteDTOList.add(contactLiteDTO);
                }
            }
            contactMap.put(appointment, contactLiteDTOList);
        }
    }
}
