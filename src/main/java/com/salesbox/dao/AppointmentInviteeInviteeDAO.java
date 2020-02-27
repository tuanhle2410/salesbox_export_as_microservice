package com.salesbox.dao;

import com.salesbox.entity.Appointment;
import com.salesbox.entity.AppointmentInviteeInvitee;
import com.salesbox.entity.Communication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hungnh on 15/07/2014.
 */
public interface AppointmentInviteeInviteeDAO extends BaseDAO<AppointmentInviteeInvitee>
{
    List<AppointmentInviteeInvitee> findByAppointment(Appointment appointment);

    List<AppointmentInviteeInvitee> findByCommunicationIn(List<Communication> communicationList);

    List<AppointmentInviteeInvitee> findByAppointmentIn(List<Appointment> appointmentList);

    AppointmentInviteeInvitee findByAppointmentAndInviteeEmail(Appointment appointment, String email);

    void removeWhereAppointmentIn(List<Appointment> appointmentList);

    List<Object[]> findInviteeAppointmentPropertyByAppointment(Appointment appointment);

    List<String> findInviteeEmailByAppointment(Appointment appointment);

    void removeWhereAppointmentAndInviteeEmailIn(Appointment appointment, ArrayList<String> emailList);

    void removeWhereAppointmentAndCommunicationIn(Appointment appointment, ArrayList<Communication> communicationList);

    Collection<? extends Communication> findCommunicationByAppointmentAndInviteeEmailIn(Appointment appointment, Collection<String> emailList);

    List<Communication> findCommunicationByAppointment(Appointment appointment);
}
