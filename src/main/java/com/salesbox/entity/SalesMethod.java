package com.salesbox.entity;

import com.salesbox.entity.enums.SalesMethodManualProcess;
import com.salesbox.entity.enums.SalesMethodMode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 4/26/14
 */
@Entity
@Table(name = "sales_method")
public class SalesMethod extends BaseEntity
{
    @Column(name = "name")
    private String name;

    @Column(name = "sales_method_version")
    private int salesMethodVersion;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_using")
    private Boolean using = Boolean.FALSE;

    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "privacy_type")
    private Integer privacyType;

    @Column(name = "number_downloaded")
    private Long numberDownloaded;

    @Column(name = "notification_time")
    private Integer notificationTime;

    @Column(name = "notification_status")
    private Boolean notificationStatus = Boolean.TRUE;

    @Column(name = "lose_meeting_ratio")
    private Integer loseMeetingRatio = 0;

    @Column(name = "hours_per_quote")
    private Double hoursPerQuote;

    @Column(name = "hours_per_contract")
    private Double hoursPerContract;

    @Column(name = "travelling_hours_per_appointment")
    private Double travellingHoursPerAppointment;

    @OneToOne
    @JoinColumn(name = "parent_sales_method_id")
    private SalesMethod parentSalesMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @OneToMany(mappedBy = "salesMethod", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Activity> activityList;

    @Column(name = "editable")
    private Boolean editable = Boolean.TRUE;

    @Column(name = "key_code")
    private String keyCode;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "language_id")
    private UUID languageId;

    @Column(name = "mode")
    @Enumerated(EnumType.ORDINAL)
    private SalesMethodMode mode = SalesMethodMode.SEQUENTIAL;

    @Column(name = "manual_progress")
    @Enumerated(EnumType.STRING)
    private SalesMethodManualProcess manualProgress = SalesMethodManualProcess.OFF;

    public String getName()
    {
        return name;
    }

    public int getSalesMethodVersion()
    {
        return salesMethodVersion;
    }

    public void setSalesMethodVersion(int salesMethodVersion)
    {
        this.salesMethodVersion = salesMethodVersion;
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

    public Boolean getUsing()
    {
        return using;
    }

    public void setUsing(Boolean using)
    {
        this.using = using;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public Integer getPrivacyType()
    {
        return privacyType;
    }

    public void setPrivacyType(Integer privacyType)
    {
        this.privacyType = privacyType;
    }

    public SalesMethod getParentSalesMethod()
    {
        return parentSalesMethod;
    }

    public void setParentSalesMethod(SalesMethod parentSalesMethod)
    {
        this.parentSalesMethod = parentSalesMethod;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public Long getNumberDownloaded()
    {
        return numberDownloaded;
    }

    public void setNumberDownloaded(Long numberDownloaded)
    {
        this.numberDownloaded = numberDownloaded;
    }

    public List<Activity> getActivityList()
    {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList)
    {
        this.activityList = activityList;
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

    public String getKeyCode()
    {
        return keyCode;
    }

    public void setKeyCode(String keyCode)
    {
        this.keyCode = keyCode;
    }

    public UUID getLanguageId()
    {
        return languageId;
    }

    public void setLanguageId(UUID languageId)
    {
        this.languageId = languageId;
    }

    public SalesMethodMode getMode()
    {
        return mode;
    }

    public void setMode(SalesMethodMode mode)
    {
        this.mode = mode;
    }

    public SalesMethodManualProcess getManualProgress()
    {
        return manualProgress;
    }

    public void setManualProgress(SalesMethodManualProcess manualProgress)
    {
        this.manualProgress = manualProgress;
    }
}
