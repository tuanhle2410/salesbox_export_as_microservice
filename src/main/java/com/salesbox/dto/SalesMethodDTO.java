package com.salesbox.dto;

import com.salesbox.annotation.OrikaMapper;
import com.salesbox.entity.SalesMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 4/26/14
 */
@OrikaMapper(mapClass = SalesMethod.class)
public class SalesMethodDTO implements Serializable
{
    private UUID uuid;
    private String name;
    private Double rating;
    private String privacyType;
    private Double price;
    private Boolean using;
    private Boolean editable = true;
    private Integer notificationTime;
    private Boolean notificationStatus;
    private Integer loseMeetingRatio;
    private Boolean downloaded = Boolean.FALSE;
    private Integer totalPercent;
    private Double hoursPerQuote;
    private Double hoursPerContract;
    private Double travellingHoursPerAppointment;
    private String keyCode;
    private String mode;
    private String manualProgress;
    private int salesMethodVersion;
    private Boolean deleted;

    private List<ActivityDTO> activityDTOList = new ArrayList<>();

    public SalesMethodDTO()
    {
    }

    public SalesMethodDTO(UUID uuid, String name)
    {
        this.uuid = uuid;
        this.name = name;
    }

    public SalesMethodDTO(UUID uuid)
    {
        this.uuid = uuid;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public String getPrivacyType()
    {
        return privacyType;
    }

    public void setPrivacyType(String privacyType)
    {
        this.privacyType = privacyType;
    }

    public Boolean getUsing()
    {
        return using;
    }

    public void setUsing(Boolean using)
    {
        this.using = using;
    }

    public Boolean getDownloaded()
    {
        return downloaded;
    }

    public void setDownloaded(Boolean downloaded)
    {
        this.downloaded = downloaded;
    }

    public Integer getTotalPercent()
    {
        return totalPercent;
    }

    public void setTotalPercent(Integer totalPercent)
    {
        this.totalPercent = totalPercent;
    }

    public Integer getNotificationTime()
    {
        return notificationTime;
    }

    public void setNotificationTime(Integer notificationTime)
    {
        this.notificationTime = notificationTime;
    }

    public Integer getLoseMeetingRatio()
    {
        return loseMeetingRatio;
    }

    public void setLoseMeetingRatio(Integer loseMeetingRatio)
    {
        this.loseMeetingRatio = loseMeetingRatio;
    }

    public Double getHoursPerQuote()
    {
        return hoursPerQuote;
    }

    public void setHoursPerQuote(Double hoursPerQuote)
    {
        this.hoursPerQuote = hoursPerQuote;
    }

    public Double getHoursPerContract()
    {
        return hoursPerContract;
    }

    public void setHoursPerContract(Double hoursPerContract)
    {
        this.hoursPerContract = hoursPerContract;
    }

    public Double getTravellingHoursPerAppointment()
    {
        return travellingHoursPerAppointment;
    }

    public void setTravellingHoursPerAppointment(Double travellingHoursPerAppointment)
    {
        this.travellingHoursPerAppointment = travellingHoursPerAppointment;
    }

    public Boolean getNotificationStatus()
    {
        return notificationStatus;
    }

    public void setNotificationStatus(Boolean notificationStatus)
    {
        this.notificationStatus = notificationStatus;
    }

    public Boolean getEditable()
    {
        return editable;
    }

    public void setEditable(Boolean editable)
    {
        this.editable = editable;
    }

    public List<ActivityDTO> getActivityDTOList()
    {
        return activityDTOList;
    }

    public void setActivityDTOList(List<ActivityDTO> activityDTOList)
    {
        this.activityDTOList = activityDTOList;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getMode()
    {
        return mode;
    }

    public void setMode(String mode)
    {
        this.mode = mode;
    }

    public String getManualProgress()
    {
        return manualProgress;
    }

    public void setManualProgress(String manualProgress)
    {
        this.manualProgress = manualProgress;
    }

    public int getSalesMethodVersion()
    {
        return salesMethodVersion;
    }

    public void setSalesMethodVersion(int salesMethodVersion)
    {
        this.salesMethodVersion = salesMethodVersion;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
}
