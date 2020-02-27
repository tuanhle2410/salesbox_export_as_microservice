package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by hungnh on 15/07/2014.
 */
@Entity
@Table(name = "appointment_invitee_invitee")
public class AppointmentInviteeInvitee extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communication_id")
    private Communication communication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

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

    public Communication getCommunication()
    {
        return communication;
    }

    public void setCommunication(Communication communication)
    {
        this.communication = communication;
    }

    public Appointment getAppointment()
    {
        return appointment;
    }

    public void setAppointment(Appointment appointment)
    {
        this.appointment = appointment;
    }
}
