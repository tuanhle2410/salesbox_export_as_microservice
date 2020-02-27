package com.salesbox.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hungnh on 15/07/2014.
 */
@Entity
@Table(name = "appointment_custom_data")
public class AppointmentCustomData extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "last_viewed")
    private Date lastViewed;

    public Appointment getAppointment()
    {
        return appointment;
    }

    public void setAppointment(Appointment appointment)
    {
        this.appointment = appointment;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Date getLastViewed()
    {
        return lastViewed;
    }

    public void setLastViewed(Date lastViewed)
    {
        this.lastViewed = lastViewed;
    }
}
