package com.salesbox.dao;

import com.salesbox.entity.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by hungnh on 15/07/2014.
 */
public interface AppointmentInviteeContactDAO
        extends BaseDAO<AppointmentInviteeContact>
{
    List<AppointmentInviteeContact> findByAppointment(Appointment appointment);

    List<AppointmentInviteeContact> findByAppointmentIn(List<Appointment> appointmentList);

    List<AppointmentInviteeContact> findByContactInAndUpdatedDateAfterOrderByUuidAsc(List<Contact> contactList, Date updatedDate, Integer pageIndex, Integer pageSize);

    List<AppointmentInviteeContact> findByContactAndNotFinishedOrderByStartDateAsc(Contact contact);

    List<AppointmentInviteeContact> findByContactInAndNotFinished(Collection<Contact> contactList);

    AppointmentInviteeContact findByAppointmentAndInviteeEmail(Appointment appointment, String email);

    Long countByAppointmentInAndContact(Collection<Appointment> appointments, Contact contact);

    AppointmentInviteeContact findClosestAppointmentByInviteeInAndNotDeleted(Collection<Contact> contacts);

    List<AppointmentInviteeContact> findByContact(Contact contact);

    List<Appointment> findAppointmentByContactInAndCreatedDateAfter(List<Contact> contactList, Date createdDate);

    void removeWhereAppointmentIn(List<Appointment> appointmentList);

    List<Object[]> findContactAppointmentPropertyByAppointment(Appointment appointment);

    List<Object[]> findContactAndAppointment();

    void removeWhereContactIn(List<Contact> contactList);

    List<SharedContact> findSharedContactByAppointment(Appointment appointment);

    List<Contact> findContactByAppointment(Appointment appointment);

    void removeWhereAppointmentAndContactIn(Appointment appointment, List<Contact> contactList);

    List<AppointmentInviteeContact> findByUserAndAppointmentIn(User user, List<Appointment> appointmentList);

    List<AppointmentInviteeContact> findByAppointmentInAndContactEnterprise(List<Appointment> appointmentList, Enterprise enterprise);

    List<AppointmentInviteeContact> findByAppointmentAndContactEnterprise(Appointment appointment, Enterprise enterprise);

    long countByAppointmentAndContact(Appointment appointment, Contact contact);

    List<AppointmentInviteeContact> findByContactInAndNotFinishedOrderByStartDateAsc(List<Contact> contactList);

    List<AppointmentInviteeContact> findByAppointmentListOrContactIn(List<Appointment> appointmentList, List<Contact> contactList);

    List<AppointmentInviteeContact> findByContactIn(List<Contact> deletedContactList);
}
