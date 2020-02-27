package com.salesbox.dto;

import java.util.HashMap;
import java.util.List;

/**
 * Created by HUNGLV2 on 12/22/2015.
 */
public class CountDTO
{
    private String sessionKey;
    private long count;

    /*Used only in Appointment Count records*/
    private List<HashMap> appointmentsList;

    public CountDTO()
    {
    }

    public CountDTO(String sessionKey, long count)
    {
        this.sessionKey = sessionKey;
        this.count = count;
    }
    
    public CountDTO(String sessionKey, long count, List<HashMap> appointmentsList) {
		super();
		this.sessionKey = sessionKey;
		this.count = count;
		this.appointmentsList = appointmentsList;
	}

	public String getSessionKey()
    {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
    }

    public long getCount()
    {
        return count;
    }

    public void setCount(long count)
    {
        this.count = count;
    }

	public List<HashMap> getAppointmentsList() {
		return appointmentsList;
	}

	public void setAppointmentsList(List<HashMap> appointmentsList) {
		this.appointmentsList = appointmentsList;
	}
    
    
}
