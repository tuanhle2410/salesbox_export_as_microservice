package com.salesbox.appointment.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hungnh on 15/07/2014.
 */
public class AppointmentListDTO
{
    private List<AppointmentDTO> appointmentDTOList = new ArrayList<AppointmentDTO>();
    private List<UUID> deletedAppointmentList = new ArrayList<>();
    private Date currentTime;
    private long numberMeeting;
    private long numberActiveMeeting;
    private String sessionKey;

    public List<AppointmentDTO> getAppointmentDTOList()
    {
        return appointmentDTOList;
    }

    public void setAppointmentDTOList(List<AppointmentDTO> appointmentDTOList)
    {
        this.appointmentDTOList = appointmentDTOList;
    }

    public List<UUID> getDeletedAppointmentList()
    {
        return deletedAppointmentList;
    }

    public void setDeletedAppointmentList(List<UUID> deletedAppointmentList)
    {
        this.deletedAppointmentList = deletedAppointmentList;
    }

    public Date getCurrentTime()
    {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime)
    {
        this.currentTime = currentTime;
    }

    public long getNumberMeeting()
    {
        return numberMeeting;
    }

    public void setNumberMeeting(long numberMeeting)
    {
        this.numberMeeting = numberMeeting;
    }

    public long getNumberActiveMeeting()
    {
        return numberActiveMeeting;
    }

    public void setNumberActiveMeeting(long numberActiveMeeting)
    {
        this.numberActiveMeeting = numberActiveMeeting;
    }

    public String getSessionKey()
    {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
    }
}
