package com.salesbox.entity;

import com.salesbox.annotation.UserProperty;
import com.salesbox.entity.enums.UserAccountType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/10/14
 * Time: 3:51 PM
 */
@Entity
@Table(name = "user_account")
public class User extends BaseEntity
{
    // ------------------------------ FIELDS ------------------------------
    @Column(name = "hunting_farming_ratio")
    private Integer huntingFarmingRatio;

    @Column(name = "is_manager")
    private Boolean manager;

    @Column(name = "is_active")
    private Boolean active = Boolean.TRUE;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "app_code")
    private String appCode;

    @Column(name = "app_code_expiry_date")
    private Date appCodeExpiryDate;

    @Column(name = "temp_password")
    private String tempPassword;

    @Column(name = "first_login")
    private Boolean firstLogin = Boolean.TRUE;

    @Column(name = "is_super_admin")
    private Boolean superAdmin = Boolean.FALSE;

    @Column(name = "median_deal_time")
    private Long medianDealTime; // millis second

    @Column(name = "median_deal_size")
    private Double medianDealSize;

    @Column(name = "median_lead_time")
    private Long medianLeadTime; // millis second

    @Column(name = "number_picks")
    private Integer numberPicks = 0;

    @Column(name = "total_time_picks")
    private Double totalTimePicks = 0d;

    @Column(name = "minutes_per_pick")
    private Double minutesPerPick;

    @Column(name = "number_calls")
    private Integer numberCalls = 0;

    @Column(name = "total_time_calls")
    private Double totalTimeCalls = 0d;

    @Column(name = "minutes_per_call")
    private Double minutesPerCall;


    @Column(name = "number_meeting")
    private Integer numberMeeting = 0;

    @Column(name = "total_time_meeting")
    private Double totalTimeMeeting = 0d;

    @Column(name = "hours_per_meeting")
    private Double hoursPerMeeting;

    @Column(name = "deactive_date")
    private Date deactiveDate;

    @Column(name = "number_attend_meeting")
    private Integer numberAttendMeeting = 0;

    //unit for calculation: hour
    @Column(name = "travelling_time_per_meeting")
    private Double travellingTimePerMeeting = 0d;
    //unit for calculation: hour
    @Column(name = "total_time_travelling")
    private Double totalTimeTravelling = 0d;

    @Column(name = "number_won_prospect")
    private Integer numberWonProspect = 0;

    @Column(name = "number_done_meeting")
    private Integer numberDoneMeeting = 0;

    @Column(name = "meeting_per_deal")
    private Double meetingPerDeal;


    @Column(name = "temp_total_pick")
    private Integer tempTotalPick = 0;

    @Column(name = "total_pick_per_call")
    private Double totalPickPerCall = 0d;

    @Column(name = "picks_per_call")
    private Double picksPerCall;

    @Column(name = "number_calls_for_meeting")
    private Integer numberCallsForMeeting = 0;

    @Column(name = "calls_per_meeting")
    private Double callsPerMeeting;

    @Column(name = "hours_per_quote")
    private Double hoursPerQuote;

    @Column(name = "hours_per_contract")
    private Double hoursPerContract;

    @Column(name = "number_quote_prospect")
    private Integer numberQuoteProspect = 0;

    @Column(name = "total_time_quote")
    private Double totalTimeQuote = 0d;

    @Column(name = "number_contract_prospect")
    private Integer numberContractProspect = 0;

    @Column(name = "total_time_contract")
    private Double totalTimeContract = 0d;

    @Column(name = "meeting_per_week")
    private Double meetingPerWeek;

    @Column(name = "call_per_week")
    private Double callPerWeek;

    @Column(name = "dial_per_week")
    private Double dialPerWeek;

    @Transient
    private Double tempDealSize;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_contact_id")
    private SharedContact sharedContact;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "permission_version")
    private Long permissionVersion = 0l;

    @Column(name = "annual_commission")
    private Double annualCommission = 0d;

    @Column(name = "number_invited_user")
    public Integer numberInvitedUser = 0;

    @Column(name = "number_recruited_user")
    public Integer numberRecruitedUser = 0;

    @Column(name = "number_ask_for_help")
    private Long numberAskForHelp = 0l;

    @Column(name = "wizard_finished")
    private Boolean wizardFinished = Boolean.FALSE;

    @Column(name = "wizard_finished_date_second_user")
    private Date wizardFinishedDateSecondUser;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private UserAccountType type;

    @Column(name = "email_subscribed")
    private Boolean emailSubscribed = true;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "maestrano_user_id")
    private String maestranoUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscription_info_id")
    private SubscriptionInfo subscriptionInfo;

    public SubscriptionInfo getSubscriptionInfo()
    {
        return subscriptionInfo;
    }

    public void setSubscriptionInfo(SubscriptionInfo subscriptionInfo)
    {
        this.subscriptionInfo = subscriptionInfo;
    }

    public UserAccountType getType()
    {
        return type;
    }

    public void setType(UserAccountType type)
    {
        this.type = type;
    }

    public Boolean getWizardFinished()
    {
        return wizardFinished;
    }

    public void setWizardFinished(Boolean wizardFinished)
    {
        this.wizardFinished = wizardFinished;
    }

    public Long getNumberAskForHelp()
    {
        return numberAskForHelp;
    }

    @UserProperty
    public void setNumberAskForHelp(Long numberAskForHelp)
    {
        this.numberAskForHelp = numberAskForHelp;
    }

    public Integer getHuntingFarmingRatio()
    {
        return huntingFarmingRatio;
    }

    public void setHuntingFarmingRatio(Integer huntingFarmingRatio)
    {
        this.huntingFarmingRatio = huntingFarmingRatio;
    }

    public Boolean getManager()
    {
        return manager;
    }

    public void setManager(Boolean manager)
    {
        this.manager = manager;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAppCode()
    {
        return appCode;
    }

    public void setAppCode(String appCode)
    {
        this.appCode = appCode;
    }

    public Date getAppCodeExpiryDate()
    {
        return appCodeExpiryDate;
    }

    public void setAppCodeExpiryDate(Date appCodeExpiryDate)
    {
        this.appCodeExpiryDate = appCodeExpiryDate;
    }

    public String getTempPassword()
    {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword)
    {
        this.tempPassword = tempPassword;
    }

    public Boolean getFirstLogin()
    {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin)
    {
        this.firstLogin = firstLogin;
    }

    public Boolean getSuperAdmin()
    {
        return superAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin)
    {
        this.superAdmin = superAdmin;
    }

    public long getMedianDealTime()
    {
        return medianDealTime;
    }

    @UserProperty
    public void setMedianDealTime(long medianDealTime)
    {
        this.medianDealTime = medianDealTime;
    }

    public Integer getNumberPicks()
    {
        return numberPicks;
    }

    @UserProperty
    public void setNumberPicks(Integer numberPicks)
    {
        this.numberPicks = numberPicks;
    }

    public Double getTotalTimePicks()
    {
        return totalTimePicks;
    }

    @UserProperty
    public void setTotalTimePicks(Double totalTimePicks)
    {
        this.totalTimePicks = totalTimePicks;
    }

    public Double getMinutesPerPick()
    {
        return minutesPerPick;
    }

    @UserProperty
    public void setMinutesPerPick(Double minutesPerPick)
    {
        this.minutesPerPick = minutesPerPick;
    }

    public Integer getNumberCalls()
    {
        return numberCalls;
    }

    @UserProperty
    public void setNumberCalls(Integer numberCalls)
    {
        this.numberCalls = numberCalls;
    }

    public Double getTotalTimeCalls()
    {
        return totalTimeCalls;
    }

    @UserProperty
    public void setTotalTimeCalls(Double totalTimeCalls)
    {
        this.totalTimeCalls = totalTimeCalls;
    }

    public Double getMinutesPerCall()
    {
        return minutesPerCall;
    }

    @UserProperty
    public void setMinutesPerCall(Double minutesPerCall)
    {
        this.minutesPerCall = minutesPerCall;
    }

    public Integer getNumberMeeting()
    {
        return numberMeeting;
    }

    @UserProperty
    public void setNumberMeeting(Integer numberMeeting)
    {
        this.numberMeeting = numberMeeting;
    }

    public Double getTotalTimeMeeting()
    {
        return totalTimeMeeting;
    }

    @UserProperty
    public void setTotalTimeMeeting(Double totalTimeMeeting)
    {
        this.totalTimeMeeting = totalTimeMeeting;
    }

    public Double getHoursPerMeeting()
    {
        return hoursPerMeeting;
    }

    @UserProperty
    public void setHoursPerMeeting(Double hoursPerMeeting)
    {
        this.hoursPerMeeting = hoursPerMeeting;
    }

    public Integer getNumberWonProspect()
    {
        return numberWonProspect;
    }

    @UserProperty
    public void setNumberWonProspect(Integer numberWonProspect)
    {
        this.numberWonProspect = numberWonProspect;
    }

    public Integer getNumberDoneMeeting()
    {
        return numberDoneMeeting;
    }

    @UserProperty
    public void setNumberDoneMeeting(Integer numberDoneMeeting)
    {
        this.numberDoneMeeting = numberDoneMeeting;
    }

    public Double getMeetingPerDeal()
    {
        return meetingPerDeal;
    }

    @UserProperty
    public void setMeetingPerDeal(Double meetingPerDeal)
    {
        this.meetingPerDeal = meetingPerDeal;
    }

    public Double getPicksPerCall()
    {
        return picksPerCall;
    }

    @UserProperty
    public void setPicksPerCall(Double picksPerCall)
    {
        this.picksPerCall = picksPerCall;
    }

    public Unit getUnit()
    {
        return unit;
    }

    public void setUnit(Unit unit)
    {
        this.unit = unit;
    }

    public Double getTotalPickPerCall()
    {
        return totalPickPerCall;
    }

    @UserProperty
    public void setTotalPickPerCall(Double totalPickPerCall)
    {
        this.totalPickPerCall = totalPickPerCall;
    }

    public double getMedianDealSize()
    {
        return medianDealSize;
    }

    @UserProperty
    public void setMedianDealSize(double medianDealSize)
    {
        this.medianDealSize = medianDealSize;
    }

    public SharedContact getSharedContact()
    {
        return sharedContact;
    }

    public void setSharedContact(SharedContact sharedContact)
    {
        this.sharedContact = sharedContact;
    }

    public Integer getNumberCallsForMeeting()
    {
        return numberCallsForMeeting;
    }

    @UserProperty
    public void setNumberCallsForMeeting(Integer numberCallsForMeeting)
    {
        this.numberCallsForMeeting = numberCallsForMeeting;
    }

    public Double getCallsPerMeeting()
    {
        return callsPerMeeting;
    }

    @UserProperty
    public void setCallsPerMeeting(Double callsPerMeeting)
    {
        this.callsPerMeeting = callsPerMeeting;
    }

    public Integer getTempTotalPick()
    {
        return tempTotalPick;
    }

    @UserProperty
    public void setTempTotalPick(Integer tempTotalPick)
    {
        this.tempTotalPick = tempTotalPick;
    }

    public Double getHoursPerQuote()
    {
        return hoursPerQuote;
    }

    @UserProperty
    public void setHoursPerQuote(Double hoursPerQuote)
    {
        this.hoursPerQuote = hoursPerQuote;
    }

    public Double getHoursPerContract()
    {
        return hoursPerContract;
    }

    @UserProperty
    public void setHoursPerContract(Double hoursPerContract)
    {
        this.hoursPerContract = hoursPerContract;
    }

    public Double getMeetingPerWeek()
    {
        return meetingPerWeek;
    }

    @UserProperty
    public void setMeetingPerWeek(Double meetingPerWeek)
    {
        this.meetingPerWeek = meetingPerWeek;
    }

    public Double getCallPerWeek()
    {
        return callPerWeek;
    }

    @UserProperty
    public void setCallPerWeek(Double callPerWeek)
    {
        this.callPerWeek = callPerWeek;
    }

    public Double getDialPerWeek()
    {
        return dialPerWeek;
    }

    @UserProperty
    public void setDialPerWeek(Double dialPerWeek)
    {
        this.dialPerWeek = dialPerWeek;
    }

    public long getMedianLeadTime()
    {
        return medianLeadTime;
    }

    @UserProperty
    public void setMedianLeadTime(long medianLeadTime)
    {
        this.medianLeadTime = medianLeadTime;
    }

    public Integer getNumberAttendMeeting()
    {
        return numberAttendMeeting;
    }

    @UserProperty
    public void setNumberAttendMeeting(Integer numberAttendMeeting)
    {
        this.numberAttendMeeting = numberAttendMeeting;
    }

    public Double getTravellingTimePerMeeting()
    {
        return travellingTimePerMeeting;
    }

    @UserProperty
    public void setTravellingTimePerMeeting(Double travellingTimePerMeeting)
    {
        this.travellingTimePerMeeting = travellingTimePerMeeting;
    }

    public Double getTotalTimeTravelling()
    {
        return totalTimeTravelling;
    }

    @UserProperty
    public void setTotalTimeTravelling(Double totalTimeTravelling)
    {
        this.totalTimeTravelling = totalTimeTravelling;
    }

    public Double getTotalTimeQuote()
    {
        return totalTimeQuote;
    }

    @UserProperty
    public void setTotalTimeQuote(Double totalTimeQuote)
    {
        this.totalTimeQuote = totalTimeQuote;
    }

    public Double getTotalTimeContract()
    {
        return totalTimeContract;
    }

    @UserProperty
    public void setTotalTimeContract(Double totalTimeContract)
    {
        this.totalTimeContract = totalTimeContract;
    }

    public Integer getNumberContractProspect()
    {
        return numberContractProspect;
    }

    @UserProperty
    public void setNumberContractProspect(Integer numberContractProspect)
    {
        this.numberContractProspect = numberContractProspect;
    }

    public Integer getNumberQuoteProspect()
    {
        return numberQuoteProspect;
    }

    @UserProperty
    public void setNumberQuoteProspect(Integer numberQuoteProspect)
    {
        this.numberQuoteProspect = numberQuoteProspect;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public long getPermissionVersion()
    {
        return permissionVersion;
    }

    public void setPermissionVersion(long permissionVersion)
    {
        this.permissionVersion = permissionVersion;
    }

    public Double getAnnualCommission()
    {
        return annualCommission;
    }

    @UserProperty
    public void setAnnualCommission(Double annualCommission)
    {
        this.annualCommission = annualCommission;
    }

    public Integer getNumberInvitedUser()
    {
        return numberInvitedUser;
    }

    @UserProperty
    public void setNumberInvitedUser(Integer numberInvitedUser)
    {
        this.numberInvitedUser = numberInvitedUser;
    }

    public Integer getNumberRecruitedUser()
    {
        return numberRecruitedUser;
    }

    @UserProperty
    public void setNumberRecruitedUser(Integer numberRecruitedUser)
    {
        this.numberRecruitedUser = numberRecruitedUser;
    }

    public Date getWizardFinishedDateSecondUser()
    {
        return wizardFinishedDateSecondUser;
    }

    public void setWizardFinishedDateSecondUser(Date wizardFinishedDateSecondUser)
    {
        this.wizardFinishedDateSecondUser = wizardFinishedDateSecondUser;
    }

    public boolean isEmailSubscribed()
    {
        return emailSubscribed;
    }

    public void setEmailSubscribed(boolean emailSubscribed)
    {
        this.emailSubscribed = emailSubscribed;
    }

    public Double getTempDealSize()
    {
        return tempDealSize;
    }

    public void setTempDealSize(Double tempDealSize)
    {
        this.tempDealSize = tempDealSize;
    }

    public String getOpenId()
    {
        return openId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

    public String getMaestranoUserId()
    {
        return maestranoUserId;
    }

    public void setMaestranoUserId(String maestranoUserId)
    {
        this.maestranoUserId = maestranoUserId;
    }

    public Date getDeactiveDate()
    {
        return deactiveDate;
    }

    public void setDeactiveDate(Date deactiveDate)
    {
        this.deactiveDate = deactiveDate;
    }
}
