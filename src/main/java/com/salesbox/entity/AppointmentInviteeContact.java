package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by hungnh on 15/07/2014.
 */
@Entity
@Table(name = "appointment_invitee_contact")
public class AppointmentInviteeContact extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(name = "accepted")
    private Boolean accepted;

    public Boolean getAccepted()
    {
        return accepted;
    }

    public void setAccepted(Boolean accepted)
    {
        this.accepted = accepted;
    }

    public Appointment getAppointment()
    {
        return appointment;
    }

    public void setAppointment(Appointment appointment)
    {
        this.appointment = appointment;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }
}
