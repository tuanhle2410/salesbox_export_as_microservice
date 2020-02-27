package com.salesbox.entity;

import com.salesbox.entity.enums.DiscProfileType;
import com.salesbox.entity.enums.MediaType;
import com.salesbox.entity.enums.RelationshipType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: luult
 * Date: 5/20/14
 */
@Entity
@Table(name = "contact")
public class Contact extends BaseEntity
{
    @Column(name = "relationship")
    @Enumerated(EnumType.ORDINAL)
    private RelationshipType relationship;

    @Column(name = "number_active_task")
    private Long numberActiveTask = 0l;

    @Column(name = "next_task_date_and_time")
    private Date nextTaskDateAndTime;

    @Column(name = "number_meeting")
    private Long numberMeeting = 0l;

    @Column(name = "number_active_meeting")
    private Long numberActiveMeeting = 0l;

    @Column(name = "start_date_first_meeting")
    private Date startDateFirstMeeting;

    @Column(name = "number_prospect")
    private Long numberProspect = 0l;

    @Column(name = "number_active_prospect")
    private Long numberActiveProspect = 0l;

    @Column(name = "number_pick")
    private Long numberPick = 0l;

    @Column(name = "number_call")
    private Long numberCall = 0l;

    @Column(name = "order_intake")
    private Double orderIntake = 0d;

    @Column(name = "gross_pipeline")
    private Double grossPipeline = 0d;

    @Column(name = "net_pipeline")
    private Double netPipeline = 0d;

    @Column(name = "pipe_profit")
    private Double pipeProfit = 0d;

    @Column(name = "won_profit")
    private Double wonProfit = 0d;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "contact_user"
            , joinColumns = {
            @JoinColumn(name = "contact_id")
    }
            , inverseJoinColumns = {
            @JoinColumn(name = "user_id")
    }
    )
    private List<User> participantList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private WorkDataOrganisation type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_id")
    private WorkDataOrganisation relation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_contact_id")
    private SharedContact sharedContact;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name_lower_case")
    private String fullNameLowerCase;

    @Column(name = "disc_profile")
    @Enumerated(EnumType.ORDINAL)
    private DiscProfileType discProfile = DiscProfileType.NONE;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "street")
    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "country")
    private String country;

    @Column(name = "title")
    private String title;

    @Column(name = "media_type")
    private MediaType mediaType = MediaType.MANUAL;

    @Column(name = "external_key")
    private String externalKey;

    @Column(name = "google_id")
    private String googleId;

    @Column(name = "outlook_id")
    private String outlookId;

    @Column(name = "office365_id")
    private String office365Id;

    @Column(name = "external_url")
    private String externalUrl;

    @Column(name = "client_id")
    private Integer clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private WorkDataOrganisation industry;

    @Column(name = "is_changed")
    private Boolean isChanged = Boolean.FALSE;

    @Column(name = "is_private")
    private Boolean isPrivate = Boolean.FALSE;

    @Column(name = "hash_code")
    private String hashCode;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "linkedin_profile_id")
    private String linkedinProfileId;

    @Column(name = "linkedin_profile_sales_id")
    private String linkedinProfileSalesId;

    @Column(name = "google_etag")
    private String googleEtag;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contact", cascade = {CascadeType.ALL})
    private List<ContactCustomData> contactCustomDataList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_call_id")
    private CommunicationHistory latestCall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_dial_id")
    private CommunicationHistory latestDial;

    @Column(name = "maestrano_id")
    private String maestranoId;

    @Column(name = "median_deal_time")
    private Long medianDealTime = 0l; // millis second

    @Column(name = "median_deal_size")
    private Double medianDealSize = 0d;

    @Column(name = "updated_info_date", nullable = false, columnDefinition = "default now()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedInfoDate = Calendar.getInstance().getTime();

    @Column(name = "updated_device_date", nullable = false, columnDefinition = "default now()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDeviceDate = Calendar.getInstance().getTime();;

    @Column(name = "add_from_lead")
    private Boolean addFromLead = Boolean.FALSE;

    @Column(name = "add_from_call_list")
    private Boolean addFromCallList = Boolean.FALSE;

    @Column(name = "leadboxer_id")
    private String leadBoxerId;

    @Column(name = "visma_id")
    private String vismaId;

    @Column(name = "fortnox_id")
    private String fortNoxId;

    @Column(name = "is_visma_main_contact")
    private Boolean isVismaMainContact;

    @Column(name = "old_contact")
    private Boolean oldContact;

    @Column(name = "phonetic_name")
    private String phoneticName;

    public Contact()
    {
    }

    public Contact(SharedContact sharedContact)
    {
        this.sharedContact = sharedContact;
        this.firstName = sharedContact.getFirstName();
        this.lastName = sharedContact.getLastName();
        this.discProfile = sharedContact.getDiscProfile();
        this.phone = sharedContact.getPhone();
        this.email = sharedContact.getEmail();
        this.street = sharedContact.getStreet();
        this.zipCode = sharedContact.getZipCode();
        this.city = sharedContact.getCity();
        this.region = sharedContact.getRegion();
        this.country = sharedContact.getCountry();
        this.title = sharedContact.getTitle();
        this.mediaType = sharedContact.getMediaType();
        this.externalKey = sharedContact.getExternalKey();
        this.externalUrl = sharedContact.getExternalUrl();
        this.industry = sharedContact.getIndustry();
        this.hashCode = sharedContact.getHashCode();
        this.avatar = sharedContact.getAvatar();
    }

    public String getMaestranoId()
    {
        return maestranoId;
    }

    public void setMaestranoId(String maestranoId)
    {
        this.maestranoId = maestranoId;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public Date getStartDateFirstMeeting()
    {
        return startDateFirstMeeting;
    }

    public void setStartDateFirstMeeting(Date startDateFirstMeeting)
    {
        this.startDateFirstMeeting = startDateFirstMeeting;
    }

    public RelationshipType getRelationship()
    {
        return relationship;
    }

    public void setRelationship(RelationshipType relationship)
    {
        this.relationship = relationship;
    }

    public Long getNumberActiveTask()
    {
        return numberActiveTask;
    }

    public void setNumberActiveTask(Long numberActiveTask)
    {
        if (numberActiveTask < 1)
        {
            this.numberActiveTask = 0L;
        }
        else
        {
            this.numberActiveTask = numberActiveTask;
        }
    }

    public Long getNumberMeeting()
    {
        return numberMeeting;
    }

    public void setNumberMeeting(Long numberMeeting)
    {
        this.numberMeeting = numberMeeting;
    }

    public Long getNumberActiveMeeting()
    {
        return numberActiveMeeting;
    }

    public void setNumberActiveMeeting(Long numberActiveMeeting)
    {
        if (numberActiveMeeting < 1)
        {
            this.numberActiveMeeting = 0L;
        }
        else
        {
            this.numberActiveMeeting = numberActiveMeeting;
        }
    }

    public Long getNumberProspect()
    {
        return numberProspect;
    }

    public void setNumberProspect(Long numberProspect)
    {
        this.numberProspect = numberProspect;
    }

    public Long getNumberPick()
    {
        return numberPick;
    }

    public void setNumberPick(Long numberPick)
    {
        this.numberPick = numberPick;
    }

    public Long getNumberCall()
    {
        return numberCall;
    }

    public void setNumberCall(Long numberCall)
    {
        this.numberCall = numberCall;
    }

    public Double getOrderIntake()
    {
        return orderIntake;
    }

    public void setOrderIntake(Double orderIntake)
    {
        this.orderIntake = orderIntake;
    }

    public Double getGrossPipeline()
    {
        return grossPipeline;
    }

    public void setGrossPipeline(Double grossPipeline)
    {
        this.grossPipeline = grossPipeline;
    }

    public Double getNetPipeline()
    {
        return netPipeline;
    }

    public void setNetPipeline(Double netPipeline)
    {
        this.netPipeline = netPipeline;
    }

    public List<User> getParticipantList()
    {
        return participantList;
    }

    public void setParticipantList(List<User> participantList)
    {
        this.participantList = participantList;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public SharedContact getSharedContact()
    {
        return sharedContact;
    }

    public void setSharedContact(SharedContact sharedContact)
    {
        this.sharedContact = sharedContact;
    }

    public WorkDataOrganisation getType()
    {
        return type;
    }

    public void setType(WorkDataOrganisation type)
    {
        this.type = type;
    }

    public WorkDataOrganisation getRelation()
    {
        return relation;
    }

    public void setRelation(WorkDataOrganisation relation)
    {
        this.relation = relation;
    }

    public Double getPipeProfit()
    {
        return pipeProfit;
    }

    public void setPipeProfit(Double pipeProfit)
    {
        this.pipeProfit = pipeProfit;
    }

    public Double getWonProfit()
    {
        return wonProfit;
    }

    public void setWonProfit(Double wonProfit)
    {
        this.wonProfit = wonProfit;
    }

    public Date getNextTaskDateAndTime()
    {
        return nextTaskDateAndTime;
    }

    public void setNextTaskDateAndTime(Date nextTaskDateAndTime)
    {
        this.nextTaskDateAndTime = nextTaskDateAndTime;
    }

    public Long getNumberActiveProspect()
    {
        return numberActiveProspect;
    }

    public void setNumberActiveProspect(Long numberActiveProspect)
    {
        this.numberActiveProspect = numberActiveProspect;
    }

    public List<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<Task> tasks)
    {
        this.tasks = tasks;
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

    public DiscProfileType getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(DiscProfileType discProfile)
    {
        this.discProfile = discProfile;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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

    public MediaType getMediaType()
    {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
    }

    public String getExternalKey()
    {
        return externalKey;
    }

    public void setExternalKey(String externalKey)
    {
        this.externalKey = externalKey;
    }

    public String getExternalUrl()
    {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl)
    {
        this.externalUrl = externalUrl;
    }

    public Integer getClientId()
    {
        return clientId;
    }

    public void setClientId(Integer clientId)
    {
        this.clientId = clientId;
    }

    public WorkDataOrganisation getIndustry()
    {
        return industry;
    }

    public void setIndustry(WorkDataOrganisation industry)
    {
        this.industry = industry;
    }

    public Boolean getIsChanged()
    {
        return isChanged;
    }

    public void setIsChanged(Boolean isChanged)
    {
        this.isChanged = isChanged;
    }

    public Boolean getIsPrivate()
    {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate)
    {
        this.isPrivate = isPrivate;
    }

    public String getHashCode()
    {
        return hashCode;
    }

    public void setHashCode(String hash)
    {
        this.hashCode = hash;
    }

    public List<ContactCustomData> getContactCustomDataList()
    {
        return contactCustomDataList;
    }

    public void setContactCustomDataList(List<ContactCustomData> contactCustomDataList)
    {
        this.contactCustomDataList = contactCustomDataList;
    }

    public CommunicationHistory getLatestCall()
    {
        return latestCall;
    }

    public void setLatestCall(CommunicationHistory latestCall)
    {
        this.latestCall = latestCall;
    }

    public CommunicationHistory getLatestDial()
    {
        return latestDial;
    }

    public void setLatestDial(CommunicationHistory latestDial)
    {
        this.latestDial = latestDial;
    }

    public String getFullNameLowerCase()
    {
        return fullNameLowerCase;
    }

    public void setFullNameLowerCase(String fullNameLowerCase)
    {
        this.fullNameLowerCase = fullNameLowerCase;
    }

    public String getGoogleId()
    {
        return googleId;
    }

    public void setGoogleId(String googleId)
    {
        this.googleId = googleId;
    }

    public Boolean getChanged()
    {
        return isChanged;
    }

    public void setChanged(Boolean changed)
    {
        isChanged = changed;
    }

    public Boolean getPrivate()
    {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate)
    {
        isPrivate = aPrivate;
    }

    public String getLinkedinProfileId()
    {
        return linkedinProfileId;
    }

    public void setLinkedinProfileId(String linkedinProfileId)
    {
        this.linkedinProfileId = linkedinProfileId;
    }

    public String getLinkedinProfileSalesId()
    {
        return linkedinProfileSalesId;
    }

    public void setLinkedinProfileSalesId(String linkedinProfileSalesId)
    {
        this.linkedinProfileSalesId = linkedinProfileSalesId;
    }

    public Boolean getAddFromCallList()
    {
        return addFromCallList;
    }

    public void setAddFromCallList(Boolean addFromCallList)
    {
        this.addFromCallList = addFromCallList;
    }

    public String getLeadBoxerId() {
        return leadBoxerId;
    }

    public void setLeadBoxerId(String leadBoxerId) {
        this.leadBoxerId = leadBoxerId;
    }

    public String getVismaId()
    {
        return vismaId;
    }

    public void setVismaId(String vismaId)
    {
        this.vismaId = vismaId;
    }

    public Boolean getOldContact() {
        if (oldContact == null) return false;
        return oldContact;
    }

    public void setOldContact(Boolean oldContact) {
        this.oldContact = oldContact;
    }

    public Boolean getVismaMainContact()
    {
        return isVismaMainContact;
    }

    public void setVismaMainContact(Boolean vismaMainContact)
    {
        isVismaMainContact = vismaMainContact;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Contact)
        {
            return ((Contact) obj).getUuid().equals(this.getUuid());
        }
        return false;
    }

    /*Convenience method to get full address combination from street, city, state, country*/
    public String getFullAddress()
    {
        String fullAddress = "";
        if (street != null)
        {
            fullAddress = street;
        }
        if (city != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + city;
        }
        if (region != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + region;
        }

        if (zipCode != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + zipCode;
        }

        if (country != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + country;
        }
        return fullAddress;
    }

    public String getGoogleEtag()
    {
        return googleEtag;
    }

    public void setGoogleEtag(String googleEtag)
    {
        this.googleEtag = googleEtag;
    }

    public Long getMedianDealTime()
    {
        return medianDealTime;
    }

    public void setMedianDealTime(Long medianDealTime)
    {
        this.medianDealTime = medianDealTime;
    }

    public Double getMedianDealSize()
    {
        return medianDealSize;
    }

    public void setMedianDealSize(Double medianDealSize)
    {
        this.medianDealSize = medianDealSize;
    }

    public String getOutlookId()
    {
        return outlookId;
    }

    public void setOutlookId(String outlookId)
    {
        this.outlookId = outlookId;
    }

    public String getOffice365Id()
    {
        return office365Id;
    }

    public void setOffice365Id(String office365Id)
    {
        this.office365Id = office365Id;
    }

    public Date getUpdatedInfoDate()
    {
        return updatedInfoDate;
    }

    public void setUpdatedInfoDate(Date updatedInfoDate)
    {
        this.updatedInfoDate = updatedInfoDate;
    }

    public Date getUpdatedDeviceDate()
    {
        return updatedDeviceDate;
    }

    public void setUpdatedDeviceDate(Date updatedDeviceDate)
    {
        this.updatedDeviceDate = updatedDeviceDate;
    }

    public Boolean getAddFromLead() {
        return addFromLead;
    }

    public void setAddFromLead(Boolean addFromLead) {
        this.addFromLead = addFromLead;
    }

    public String getPhoneticName()
    {
        return phoneticName;
    }

    public void setPhoneticName(String phoneticName)
    {
        this.phoneticName = phoneticName;
    }

    public String buildFullName() {

        return (this.firstName == null || this.firstName.isEmpty() ? "" : (this.firstName + " ")) +
                (this.lastName == null || this.lastName.isEmpty() ? "" : (this.lastName));
    }

    public String getFortNoxId()
    {
        return fortNoxId;
    }

    public void setFortNoxId(String fortNoxId)
    {
        this.fortNoxId = fortNoxId;
    }
}
