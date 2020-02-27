package com.salesbox.dto;

import com.salesbox.entity.SharedContact;
import com.salesbox.entity.Unit;
import com.salesbox.entity.User;
import com.salesbox.entity.UserTemp;
import com.salesbox.annotation.OrikaMapper;
import ma.glasnost.orika.MapperFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/23/14
 * Time: 2:44 PM
 */
@OrikaMapper(mapClass = User.class, excludes = {"password"})
public class UserDTO implements Serializable
{
    private UUID uuid;
    private String username;
    private String password;
    private Boolean active;
    private Boolean manager;
    private Boolean delete;
    private Boolean isMainContact = false;
    private UUID pendingId;
    private UUID unitId;
    private String token;
    private Boolean firstLogin;
    private String discProfile;
    private Integer huntingFarmingRatio;
    private String firstName;
    private String lastName;
    private String email;
    private String mainEmailType;
    private String phone;
    private String mainPhoneType;
    private Integer numberActiveTasks;
    private Long medianDealTime;
    private Double medianDealSize;
    private Long medianLeadTime;
    private String openId;
    private String maestranoUserId;

    private Double sharedPercent;

    private String street;
    private String zipCode;
    private String city;
    private String region;
    private String country;
    private String title;

    private WorkDataOrganisationDTO industry;

    private Double minutesPerPick;
    private Double minutesPerCall;
    private Double hoursPerMeeting;
    private Double callsPerMeeting;
    private Double picksPerCall;
    private Double meetingPerDeal;
    private Double travellingTimePerMeeting;
    private Double hoursPerQuote;
    private Double hoursPerContract;


    private List<CommunicationDTO> additionalEmailList = new ArrayList<>();
    private List<CommunicationDTO> additionalPhoneList = new ArrayList<>();

    private UUID sharedContactId;
    private String avatar;
    private long permissionVersion;
    private UUID inviteeId;
    private UUID enterpriseId;
    private SubscriptionInfoDTO subscriptionInfoDTO;

    private RightWebDTO permission;
    private List<UUID> sameUuidInUnit;

    public Double getSharedPercent()
    {
        return sharedPercent;
    }

    public void setSharedPercent(Double sharedPercent)
    {
        this.sharedPercent = sharedPercent;
    }

    public UserDTO(User user, MapperFacade mapper)
    {
        this.uuid = user.getUuid();
        this.username = user.getUsername();
        this.active = user.getActive();
        this.manager = user.getManager();
        this.huntingFarmingRatio = user.getHuntingFarmingRatio();

        SharedContact sharedContact = user.getSharedContact();
        this.setSharedContactId(sharedContact.getUuid());
        this.setEmail(sharedContact.getEmail());
        this.setFirstName(sharedContact.getFirstName());
        this.setLastName(sharedContact.getLastName());
        this.setPhone(sharedContact.getPhone());
        this.setAvatar(user.getAvatar());
        this.setDiscProfile(sharedContact.getDiscProfile().toString());
        this.setStreet(sharedContact.getStreet());
        this.setZipCode(sharedContact.getZipCode());
        this.setCity(sharedContact.getCity());
        this.setRegion(sharedContact.getRegion());
        this.setCountry(sharedContact.getCountry());
        this.setTitle(sharedContact.getTitle());
        this.setOpenId(user.getOpenId());

        this.setMaestranoUserId(user.getMaestranoUserId());
        Unit unit = user.getUnit();
        this.enterpriseId = unit.getEnterprise().getUuid();
        this.setUnitId(unit.getUuid());

        if (user.getSubscriptionInfo() != null)
        {
            this.subscriptionInfoDTO = new SubscriptionInfoDTO(user.getSubscriptionInfo());
        }

    }

    public UserDTO(UserTemp userTemp)
    {
        this.setEmail(userTemp.getEmail().toLowerCase());
        this.setFirstName(userTemp.getFirstName());
        this.setLastName(userTemp.getLastName());
        this.setPhone(userTemp.getPhone());
        this.setDiscProfile(userTemp.getDiscProfile().toString());
        this.setHuntingFarmingRatio(userTemp.getHuntingFarmingRatio());
        this.setCountry(userTemp.getCountry());
        this.openId = userTemp.getOpenId();
        if (userTemp.getUnit() != null)
        {
            this.setUnitId(userTemp.getUnit().getUuid());
        }
        this.pendingId = userTemp.getUuid();

    }

    public UserDTO()
    {

    }

    public SubscriptionInfoDTO getSubscriptionInfoDTO()
    {
        return subscriptionInfoDTO;
    }

    public void setSubscriptionInfoDTO(SubscriptionInfoDTO subscriptionInfoDTO)
    {
        this.subscriptionInfoDTO = subscriptionInfoDTO;
    }

    public UserDTO(UUID uuid)
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

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Boolean getManager()
    {
        return manager;
    }

    public void setManager(Boolean manager)
    {
        this.manager = manager;
    }

    public Integer getHuntingFarmingRatio()
    {
        return huntingFarmingRatio;
    }

    public void setHuntingFarmingRatio(Integer huntingFarmingRatio)
    {
        this.huntingFarmingRatio = huntingFarmingRatio;
    }

    public String getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(String discProfile)
    {
        this.discProfile = discProfile;
    }

    public Boolean getDelete()
    {
        return delete;
    }

    public void setDelete(Boolean delete)
    {
        this.delete = delete;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public UUID getUnitId()
    {
        return unitId;
    }

    public void setUnitId(UUID unitId)
    {
        this.unitId = unitId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Integer getNumberActiveTasks()
    {
        return numberActiveTasks;
    }

    public void setNumberActiveTasks(Integer numberActiveTasks)
    {
        this.numberActiveTasks = numberActiveTasks;
    }

    public Long getMedianDealTime()
    {
        return medianDealTime;
    }

    public void setMedianDealTime(Long medianDealTime)
    {
        this.medianDealTime = medianDealTime;
    }

    public Boolean getFirstLogin()
    {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin)
    {
        this.firstLogin = firstLogin;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<CommunicationDTO> getAdditionalEmailList()
    {
        return additionalEmailList;
    }

    public void setAdditionalEmailList(List<CommunicationDTO> additionalEmailList)
    {
        this.additionalEmailList = additionalEmailList;
    }

    public List<CommunicationDTO> getAdditionalPhoneList()
    {
        return additionalPhoneList;
    }

    public void setAdditionalPhoneList(List<CommunicationDTO> additionalPhoneList)
    {
        this.additionalPhoneList = additionalPhoneList;
    }

    public String getMainEmailType()
    {
        return mainEmailType;
    }

    public void setMainEmailType(String mainEmailType)
    {
        this.mainEmailType = mainEmailType;
    }

    public String getMainPhoneType()
    {
        return mainPhoneType;
    }

    public void setMainPhoneType(String mainPhoneType)
    {
        this.mainPhoneType = mainPhoneType;
    }

    public Double getMinutesPerPick()
    {
        return minutesPerPick;
    }

    public void setMinutesPerPick(Double minutesPerPick)
    {
        this.minutesPerPick = minutesPerPick;
    }

    public Double getMinutesPerCall()
    {
        return minutesPerCall;
    }

    public void setMinutesPerCall(Double minutesPerCall)
    {
        this.minutesPerCall = minutesPerCall;
    }

    public Double getHoursPerMeeting()
    {
        return hoursPerMeeting;
    }

    public void setHoursPerMeeting(Double hoursPerMeeting)
    {
        this.hoursPerMeeting = hoursPerMeeting;
    }

    public Double getCallsPerMeeting()
    {
        return callsPerMeeting;
    }

    public void setCallsPerMeeting(Double callsPerMeeting)
    {
        this.callsPerMeeting = callsPerMeeting;
    }

    public Double getPicksPerCall()
    {
        return picksPerCall;
    }

    public void setPicksPerCall(Double picksPerCall)
    {
        this.picksPerCall = picksPerCall;
    }

    public Double getMeetingPerDeal()
    {
        return meetingPerDeal;
    }

    public void setMeetingPerDeal(Double meetingPerDeal)
    {
        this.meetingPerDeal = meetingPerDeal;
    }

    public UUID getSharedContactId()
    {
        return sharedContactId;
    }

    public void setSharedContactId(UUID sharedContactId)
    {
        this.sharedContactId = sharedContactId;
    }

    public Double getMedianDealSize()
    {
        return medianDealSize;
    }

    public void setMedianDealSize(Double medianDealSize)
    {
        this.medianDealSize = medianDealSize;
    }

    public Long getMedianLeadTime()
    {
        return medianLeadTime;
    }

    public void setMedianLeadTime(Long medianLeadTime)
    {
        this.medianLeadTime = medianLeadTime;
    }

    public UUID getPendingId()
    {
        return pendingId;
    }

    public void setPendingId(UUID pendingId)
    {
        this.pendingId = pendingId;
    }

    public Double getTravellingTimePerMeeting()
    {
        return travellingTimePerMeeting;
    }

    public void setTravellingTimePerMeeting(Double travellingTimePerMeeting)
    {
        this.travellingTimePerMeeting = travellingTimePerMeeting;
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

    public WorkDataOrganisationDTO getIndustry()
    {
        return industry;
    }

    public void setIndustry(WorkDataOrganisationDTO industry)
    {
        this.industry = industry;
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

    public UUID getInviteeId()
    {
        return inviteeId;
    }

    public void setInviteeId(UUID inviteeId)
    {
        this.inviteeId = inviteeId;
    }

    public Boolean getIsMainContact()
    {
        return isMainContact;
    }

    public void setIsMainContact(Boolean isMainContact)
    {
        this.isMainContact = isMainContact;
    }

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
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

    public RightWebDTO getPermission()
    {
        return permission;
    }

    public void setPermission(RightWebDTO permission)
    {
        this.permission = permission;
    }

    public List<UUID> getSameUuidInUnit()
    {
        return sameUuidInUnit;
    }

    public void setSameUuidInUnit(List<UUID> sameUuidInUnit)
    {
        this.sameUuidInUnit = sameUuidInUnit;
    }
}
