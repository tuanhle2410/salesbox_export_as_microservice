package com.salesbox.dao;

import com.salesbox.entity.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by hungnh on 16/07/2014.
 */
public interface AppointmentContactDAO extends BaseDAO<AppointmentContact>
{
    List<AppointmentContact> findByAppointment(Appointment appointment);

    List<AppointmentContact> findByAppointmentIn(List<Appointment> appointmentList);

    List<AppointmentContact> findByContactIn(List<Contact> contactList);

    List<AppointmentContact> findByContactAndNotFinishedAndOrderByStartedDateAsc(Contact contact);

    List<AppointmentContact> findByContactInAndNotFinished(Collection<Contact> contactCollection);

    AppointmentContact findByAppointmentAndContactEmail(Appointment appointment, String email);

    List<AppointmentContact> findByContact(Contact contact);

    void removeWhereAppointmentIn(List<Appointment> appointmentList);

    List<Object[]> findContactAppointmentPropertyByAppointment(Appointment appointment);

    List<Object[]> findContactAndAppointment();

    void removeWhereContactIn(List<Contact> contactList);

    List<SharedContact> findSharedContactByAppointment(Appointment appointment);

    void removeWhereAppointment(Appointment appointment);

    List<Contact> findContactByAppointment(Appointment appointment);

    List<AppointmentContact> findByUserAndAppointmentIn(User user, List<Appointment> appointmentList);

    List<AppointmentContact> findByAppointmentInAndContactEnterprise(List<Appointment> appointmentList, Enterprise enterprise);
    List<AppointmentContact> findByAppointmentInOrContactIn(List<Appointment> appointmentList, List<Contact> contactList);
    List<AppointmentContact> findByAppointmentAndContactEnterprise(Appointment appointment, Enterprise enterprise);

    List<AppointmentContact> findByContactInAndNotFinishedAndOrderByStartedDateAsc(List<Contact> contactList);

    Long countActiveByContact(Contact contact);

    Long countByContact(Contact contact);
}
