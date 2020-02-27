package com.salesbox.entity;

import com.salesbox.annotation.UserProperty;
import com.salesbox.entity.enums.DiscProfileType;
import com.salesbox.entity.enums.ProspectPrioritiseColorType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * User: luult
 * Date: 5/20/14
 */
@MappedSuperclass
public class ProspectBase extends BaseEntity
{
    @Column(name = "number_task")
    private long numberTask;

    @Column(name = "number_active_task")
    private long numberActiveTask;

    @Column(name = "number_activity_left")
    private Integer numberActivityLeft;

    @Column(name = "prospect_progress")
    private Integer prospectProgress;

    @Column(name = "first_next_step")
    private String firstNextStep;

    @Column(name = "second_next_step")
    private String secondNextStep;

    @Column(name = "description_first_next_step")
    private String descriptionFirstNextStep;

    @Column(name = "disc_profile_first_next_step")
    private DiscProfileType discProfileFirstNextStep;

    @Column(name = "description_second_next_step")
    private String descriptionSecondNextStep;

    @Column(name = "disc_profile_second_next_step")
    private DiscProfileType discProfileSecondNextStep;

    @Column(name = "number_meeting")
    private long numberMeeting;

    @Column(name = "number_active_meeting")
    private long numberActiveMeeting;

    @Column(name = "number_meeting_left")
    private Double numberMeetingLeft;

    @Column(name = "number_done_meeting")
    private Integer numberDoneMeeting;

    @Column(name = "done_work_effort")
    private Double doneWorkEffort;

    @Column(name = "total_time_communication")
    private Double totalTimeCommunication = 0d;

    @Column(name = "gross_value")
    private Double grossValue = 0d;

    @Column(name = "profit")
    private Double profit = 0d;

    @Column(name = "description")
    private String description;

    @Column(name = "contract_date")
    private Date contractDate;

    @Column(name = "days_in_pipeline")
    private Long daysInPipeline = 0l;

    @Column(name = "is_quote_sent")
    private Boolean quoteSent;

    @Column(name = "checked_quote_sent")
    private Boolean checkedQuoteSent = Boolean.FALSE;

    @Column(name = "is_contract_sent")
    private Boolean contractSent;

    @Column(name = "checked_contract_sent")
    private Boolean checkedContractSent = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_of_business_id")
    private LineOfBusiness lineOfBusiness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_method_id")
    private SalesMethod salesMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_sponsor_id")
    private Contact powerSponsor;

    @Column(name = "prioritise_color")
    @Enumerated(EnumType.ORDINAL)
    private ProspectPrioritiseColorType prioritiseColor = ProspectPrioritiseColorType.GREEN;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "delivery_start")
    private Date deliveryStartDate;

    @Column(name = "delivery_end")
    private Date deliveryEndDate;

    @Column(name = "hour_per_quote")
    private Double hourPerQuote;

    @Column(name = "hour_per_contract")
    private Double hourPerContract;

    @Column(name = "travelling_hours_per_meeting")
    private Double travellingHoursPerMeeting;

    @Column(name = "maestrano_invoice_id")
    private String maestranoInvoiceId;

    @Column(name = "maestrano_id")
    private String maestranoId;

    @Column(name = "leadboxer_id")
    private String leadBoxerId;

    @Column(name = "last_sync_time")
    private Date lastSyncTime;

    @Column(name = "visit_more")
    private Boolean visitMore = Boolean.FALSE;


    @Column(name = "owner_first_name")
    private String ownerFirstName;

    @Column(name = "owner_last_name")
    private String ownerLastName;

    @Column(name = "owner_avatar")
    private String ownerAvatar;

    @Column(name = "owner_disc_profile")
    @Enumerated(EnumType.ORDINAL)
    private DiscProfileType ownerDiscProfile;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "unit_id")
    private UUID unitId;

    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "visma_id")
    private String vismaId;

    @Column(name = "manual_progress")
    private Integer manualProgress;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "current_step_id")
    private UUID currentStepId;


    @Transient
    private Double tempGrossValue;

    public String getMaestranoInvoiceId() {
        return maestranoInvoiceId;
    }

    public void setMaestranoInvoiceId(String maestranoInvoiceId) {
        this.maestranoInvoiceId = maestranoInvoiceId;
    }

    public Integer getNumberActivityLeft()
    {
        return numberActivityLeft;
    }
    @UserProperty
    public void setNumberActivityLeft(Integer numberActivityLeft)
    {
        this.numberActivityLeft = numberActivityLeft;
    }

    public Integer getProspectProgress()
    {
        return prospectProgress;
    }
    @UserProperty
    public void setProspectProgress(Integer prospectProgress)
    {
        this.prospectProgress = prospectProgress;
    }

    public String getFirstNextStep()
    {
        return firstNextStep;
    }

    public void setFirstNextStep(String firstNextStep)
    {
        this.firstNextStep = firstNextStep;
    }

    public String getSecondNextStep()
    {
        return secondNextStep;
    }

    public void setSecondNextStep(String secondNextStep)
    {
        this.secondNextStep = secondNextStep;
    }

    public String getDescriptionFirstNextStep()
    {
        return descriptionFirstNextStep;
    }

    public void setDescriptionFirstNextStep(String descriptionFirstNextStep)
    {
        this.descriptionFirstNextStep = descriptionFirstNextStep;
    }

    public String getDescriptionSecondNextStep()
    {
        return descriptionSecondNextStep;
    }

    public void setDescriptionSecondNextStep(String descriptionSecondNextStep)
    {
        this.descriptionSecondNextStep = descriptionSecondNextStep;
    }

    public long getNumberTask()
    {
        return numberTask;
    }
    @UserProperty
    public void setNumberTask(long numberTask)
    {
        this.numberTask = numberTask;
    }

    public long getNumberMeeting()
    {
        return numberMeeting;
    }

    @UserProperty
    public void setNumberMeeting(long numberMeeting)
    {
        this.numberMeeting = numberMeeting;
    }

    public Double getGrossValue()
    {
        return grossValue;
    }
    @UserProperty
    public void setGrossValue(Double grossValue)
    {
        this.grossValue = grossValue;
    }

    public Double getProfit()
    {
        return profit;
    }
    @UserProperty
    public void setProfit(Double profit)
    {
        this.profit = profit;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getContractDate()
    {
        return contractDate;
    }

    public void setContractDate(Date contractDate)
    {
        this.contractDate = contractDate;
    }

    public Long getDaysInPipeline()
    {
        return daysInPipeline;
    }

    public void setDaysInPipeline(Long daysInPipeline)
    {
        this.daysInPipeline = daysInPipeline;
    }

    public Boolean getQuoteSent()
    {
        return quoteSent;
    }

    public void setQuoteSent(Boolean quoteSent)
    {
        this.quoteSent = quoteSent;
    }

    public Boolean getContractSent()
    {
        return contractSent;
    }

    public void setContractSent(Boolean contractSent)
    {
        this.contractSent = contractSent;
    }

    public LineOfBusiness getLineOfBusiness()
    {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(LineOfBusiness lineOfBusiness)
    {
        this.lineOfBusiness = lineOfBusiness;
    }

    public SalesMethod getSalesMethod()
    {
        return salesMethod;
    }

    public void setSalesMethod(SalesMethod salesMethod)
    {
        this.salesMethod = salesMethod;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public Contact getPowerSponsor()
    {
        return powerSponsor;
    }

    public void setPowerSponsor(Contact powerSponsor)
    {
        this.powerSponsor = powerSponsor;
    }

//    public List<Activity> getActivityList()
//    {
//        return activityList;
//    }
//
//    public void setActivityList(List<Activity> activityList)
//    {
//        this.activityList = activityList;
//    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public Date getDeliveryStartDate()
    {
        return deliveryStartDate;
    }
    @UserProperty
    public void setDeliveryStartDate(Date deliveryStartDate)
    {
        this.deliveryStartDate = deliveryStartDate;
    }

    public Date getDeliveryEndDate()
    {
        return deliveryEndDate;
    }
    @UserProperty
    public void setDeliveryEndDate(Date deliveryEndDate)
    {
        this.deliveryEndDate = deliveryEndDate;
    }

    public Double getNumberMeetingLeft()
    {
        return numberMeetingLeft;
    }
    @UserProperty
    public void setNumberMeetingLeft(Double numberMeetingLeft)
    {
        this.numberMeetingLeft = numberMeetingLeft;
    }

    public Double getTotalTimeCommunication()
    {
        return totalTimeCommunication;
    }
    @UserProperty
    public void setTotalTimeCommunication(Double totalTimeCommunication)
    {
        this.totalTimeCommunication = totalTimeCommunication;
    }

    public Double getDoneWorkEffort()
    {
        return doneWorkEffort;
    }

    public void setDoneWorkEffort(Double doneWorkEffort)
    {
        this.doneWorkEffort = doneWorkEffort;
    }

    public DiscProfileType getDiscProfileFirstNextStep()
    {
        return discProfileFirstNextStep;
    }

    public void setDiscProfileFirstNextStep(DiscProfileType discProfileFirstNextStep)
    {
        this.discProfileFirstNextStep = discProfileFirstNextStep;
    }

    public DiscProfileType getDiscProfileSecondNextStep()
    {
        return discProfileSecondNextStep;
    }

    public void setDiscProfileSecondNextStep(DiscProfileType discProfileSecondNextStep)
    {
        this.discProfileSecondNextStep = discProfileSecondNextStep;
    }

    public long getNumberActiveTask()
    {
        return numberActiveTask;
    }
    @UserProperty
    public void setNumberActiveTask(long numberActiveTask)
    {
        this.numberActiveTask = numberActiveTask;
    }

    public long getNumberActiveMeeting()
    {
        return numberActiveMeeting;
    }
    @UserProperty
    public void setNumberActiveMeeting(long numberActiveMeeting)
    {
        this.numberActiveMeeting = numberActiveMeeting;
    }

    public Integer getNumberDoneMeeting()
    {
        return numberDoneMeeting;
    }

    public void setNumberDoneMeeting(Integer numberDoneMeeting)
    {
        this.numberDoneMeeting = numberDoneMeeting;
    }
    public Double getLoseNetValue()
    {
        return prospectProgress.doubleValue()/100 * getSalesMethod().getLoseMeetingRatio()/100.0 * grossValue;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public Double getHourPerQuote()
    {
        return hourPerQuote;
    }

    public void setHourPerQuote(Double hourPerQuote)
    {
        this.hourPerQuote = hourPerQuote;
    }

    public Double getHourPerContract()
    {
        return hourPerContract;
    }

    public void setHourPerContract(Double hourPerContract)
    {
        this.hourPerContract = hourPerContract;
    }

    public Boolean getCheckedQuoteSent()
    {
        return checkedQuoteSent;
    }
    @UserProperty
    public void setCheckedQuoteSent(Boolean checkedQuoteSent)
    {
        this.checkedQuoteSent = checkedQuoteSent;
    }

    public Boolean getCheckedContractSent()
    {
        return checkedContractSent;
    }
    @UserProperty
    public void setCheckedContractSent(Boolean checkedContractSent)
    {
        this.checkedContractSent = checkedContractSent;
    }

    public Double getTravellingHoursPerMeeting()
    {
        return travellingHoursPerMeeting;
    }

    public void setTravellingHoursPerMeeting(Double travellingHoursPerMeeting)
    {
        this.travellingHoursPerMeeting = travellingHoursPerMeeting;
    }


    public ProspectPrioritiseColorType getPrioritiseColor()
    {
        return prioritiseColor;
    }
    @UserProperty
    public void setPrioritiseColor(ProspectPrioritiseColorType prioritiseColor)
    {
        this.prioritiseColor = prioritiseColor;
    }

    public Double getTempGrossValue() {
        return tempGrossValue;
    }

    public void setTempGrossValue(Double tempGrossValue) {
        this.tempGrossValue = tempGrossValue;
    }

    public String getMaestranoId()
    {
        return maestranoId;
    }

    public void setMaestranoId(String maestranoId)
    {
        this.maestranoId = maestranoId;
    }

    public String getLeadBoxerId() {
        return leadBoxerId;
    }

    public void setLeadBoxerId(String leadBoxerId) {
        this.leadBoxerId = leadBoxerId;
    }

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Boolean getVisitMore() {
        return visitMore;
    }

    public void setVisitMore(Boolean visitMore) {
        this.visitMore = visitMore;
    }


    public String getOwnerFirstName()
    {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName)
    {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName()
    {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName)
    {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerAvatar()
    {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar)
    {
        this.ownerAvatar = ownerAvatar;
    }

    public DiscProfileType getOwnerDiscProfile()
    {
        return ownerDiscProfile;
    }

    public void setOwnerDiscProfile(DiscProfileType ownerDiscProfile)
    {
        this.ownerDiscProfile = ownerDiscProfile;
    }

    public UUID getUnitId()
    {
        return unitId;
    }

    public void setUnitId(UUID unitId)
    {
        this.unitId = unitId;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }


    public String getVismaId()
    {
        return vismaId;
    }

    public void setVismaId(String vismaId)
    {
        this.vismaId = vismaId;
    }

    public Integer getManualProgress()
    {
        return manualProgress;
    }

    public void setManualProgress(Integer manualProgress)
    {
        this.manualProgress = manualProgress;
    }

    public UUID getCurrentStepId()
    {
        return currentStepId;
    }

    public void setCurrentStepId(UUID currentStepId)
    {
        this.currentStepId = currentStepId;
    }
}
