package com.salesbox.prospect.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by quynhtq on 5/3/14.
 */
public class ProspectListDTO implements Serializable
{
    private List<ProspectDTO> prospectDTOList = new ArrayList<ProspectDTO>();
    private List<UUID> deletedProspectList = new ArrayList<>();
    private Date currentTime;
    private long numberProspect;
    private String sessionKey;

    public List<ProspectDTO> getProspectDTOList()
    {
        return prospectDTOList;
    }

    public void setProspectDTOList(List<ProspectDTO> prospectDTOList)
    {
        this.prospectDTOList = prospectDTOList;
    }

    public List<UUID> getDeletedProspectList()
    {
        return deletedProspectList;
    }

    public void setDeletedProspectList(List<UUID> deletedProspectList)
    {
        this.deletedProspectList = deletedProspectList;
    }

    public Date getCurrentTime()
    {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime)
    {
        this.currentTime = currentTime;
    }

    public void setNumberProspect(long numberProspect) {
        this.numberProspect = numberProspect;
    }

    public long getNumberProspect() {
        return numberProspect;
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
