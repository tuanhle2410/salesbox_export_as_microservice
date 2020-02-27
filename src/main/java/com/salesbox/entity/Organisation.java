package com.salesbox.entity;

import com.salesbox.entity.enums.MediaType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: luult
 * Date: 5/20/14
 */
@Entity
@Table(name = "organisation")
public class Organisation extends BaseEntity
{
    @Column(name = "number_contact")
    private Long numberContact = 0l;

    @Column(name = "number_active_task")
    private Long numberActiveTask = 0l;

    @Column(name = "next_task_date_and_time")
    private Date nextTaskDateAndTime;

    @Column(name = "number_active_meeting")
    private Long numberActiveMeeting = 0l;

    @Column(name = "number_meeting")
    private Long numberMeeting = 0l;

    @Column(name = "number_prospect")
    private Long numberProspect = 0l;

    @Column(name = "number_active_prospect")
    private Long numberActiveProspect = 0l;

    @Column(name = "number_pick")
    private Long numberPick = 0l;

    @Column(name = "number_call")
    private Long numberCall = 0l;

    @Column(name = "number_actual_meeting")
    private Double numberActualMeeting = 0d;

    @Column(name = "number_goals_meeting")
    private Double numberGoalsMeeting = 0d;

    @Column(name = "order_intake")
    private Double orderIntake = 0d;

    @Column(name = "gross_pipeline")
    private Double grossPipeline = 0d;

    @Column(name = "net_pipeline")
    private Double netPipeline = 0d;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "pipe_profit")
    private Double pipeProfit = 0d;

    @Column(name = "won_profit")
    private Double wonProfit = 0d;

    @Column(name = "relationship")
    private Double relationship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_organisation_id")
    private SharedOrganisation sharedOrganisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private WorkDataOrganisation type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "organisation_user"
            , joinColumns = {
            @JoinColumn(name = "organisation_id")
    }
            , inverseJoinColumns = {
            @JoinColumn(name = "user_id")
    }
    )
    private List<User> participantList;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "name")
    private String name;

    @Column(name = "formal_name")
    private String formalName;

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "name_order")
    private String nameOrder;

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

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = " billing_address_id")
    private Address billingAddress;

    @Column(name = "media_type")
    private MediaType mediaType = MediaType.MANUAL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private WorkDataOrganisation industry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private WorkDataOrganisation size;

    @Column(name = "web")
    private String web;

    @Column(name = "external_key")
    private String externalKey;

    @Column(name = "is_changed")
    private Boolean isChanged = Boolean.FALSE;

    @Column(name = "is_private")
    private Boolean isPrivate = Boolean.FALSE;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "linkedin_profile_id")
    private String linkedinProfileId;

    @Column(name = "linkedin_profile_sales_id")
    private String linkedinProfileSalesId;

    @Column(name = "maestrano_id")
    private String maestranoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_call_id")
    private CommunicationHistory latestCall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_dial_id")
    private CommunicationHistory latestDial;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organisation", cascade = {CascadeType.ALL})
    private List<OrganisationCustomData> organisationCustomDataList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organisation", cascade = {CascadeType.ALL})
    private List<OrganisationVismaCustomer> organisationVismaCustomerList = new ArrayList<>();

    @Column(name = "median_deal_time")
    private Long medianDealTime = 0l; // millis second

    @Column(name = "median_deal_size")
    private Double medianDealSize = 0d;

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

    @Column(name = "phonetic_name")
    private String phoneticName;

    public Organisation(SharedOrganisation sharedOrganisation)
    {
        populateFromSharedOrganisation(sharedOrganisation);
    }

    public void populateFromSharedOrganisation(SharedOrganisation sharedOrganisation)
    {
        this.setSharedOrganisation(sharedOrganisation);
        this.setName(sharedOrganisation.getName());
        this.setPhone(sharedOrganisation.getPhone());
        this.setEmail(sharedOrganisation.getEmail());
        this.setStreet(sharedOrganisation.getStreet());
        this.setZipCode(sharedOrganisation.getZipCode());
        this.setCity(sharedOrganisation.getCity());
        this.setState(sharedOrganisation.getState());
        this.setCountry(sharedOrganisation.getCountry());
        this.setMediaType(sharedOrganisation.getMediaType());
        this.setIndustry(sharedOrganisation.getIndustry());
        this.setSize(sharedOrganisation.getSize());
        this.setWeb(sharedOrganisation.getWeb());
        this.setExternalKey(sharedOrganisation.getExternalKey());
        this.setAvatar(sharedOrganisation.getAvatar());
    }

    public Organisation()
    {
    }

    public String getMaestranoId() {
        return maestranoId;
    }

    public void setMaestranoId(String maestranoId) {
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

    public Long getNumberContact()
    {
        return numberContact;
    }

    public void setNumberContact(Long numberContact)
    {
        this.numberContact = numberContact;
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

    public Double getNumberActualMeeting()
    {
        return numberActualMeeting;
    }

    public void setNumberActualMeeting(Double numberActualMeeting)
    {
        this.numberActualMeeting = numberActualMeeting;
    }

    public Double getNumberGoalsMeeting()
    {
        return numberGoalsMeeting;
    }

    public void setNumberGoalsMeeting(Double numberGoalMeeting)
    {
        this.numberGoalsMeeting = numberGoalMeeting;
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

    public Double getBudget()
    {
        return budget;
    }

    public void setBudget(Double budget)
    {
        this.budget = budget;
    }

    public Double getWonProfit()
    {
        return wonProfit;
    }

    public void setWonProfit(Double wonProfit)
    {
        this.wonProfit = wonProfit;
    }

    public Double getRelationship()
    {
        return relationship;
    }

    public void setRelationship(Double relationship)
    {
        this.relationship = relationship;
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

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public Double getPipeProfit()
    {
        return pipeProfit;
    }

    public void setPipeProfit(Double pipeProfit)
    {
        this.pipeProfit = pipeProfit;
    }

    public SharedOrganisation getSharedOrganisation()
    {
        return sharedOrganisation;
    }

    public void setSharedOrganisation(SharedOrganisation sharedOrganisation)
    {
        this.sharedOrganisation = sharedOrganisation;
    }

    public WorkDataOrganisation getType()
    {
        return type;
    }

    public void setType(WorkDataOrganisation type)
    {
        this.type = type;
    }

    public Date getNextTaskDateAndTime()
    {
        return nextTaskDateAndTime;
    }

    public void setNextTaskDateAndTime(Date nextTaskDateAndTime)
    {
        this.nextTaskDateAndTime = nextTaskDateAndTime;
    }

    public Long getNumberMeeting()
    {
        return numberMeeting;
    }

    public void setNumberMeeting(Long numberMeeting)
    {
        this.numberMeeting = numberMeeting;
    }

    public Long getNumberActiveProspect()
    {
        return numberActiveProspect;
    }

    public void setNumberActiveProspect(Long numberActiveProspect)
    {
        this.numberActiveProspect = numberActiveProspect;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFormalName()
    {
        return formalName;
    }

    public void setFormalName(String formalName)
    {
        this.formalName = formalName;
    }

    public String getVatNumber()
    {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber)
    {
        this.vatNumber = vatNumber;
    }

    public Address getShippingAddress()
    {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress()
    {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress)
    {
        this.billingAddress = billingAddress;
    }

    public String getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
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

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public MediaType getMediaType()
    {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
    }

    public WorkDataOrganisation getIndustry()
    {
        return industry;
    }

    public void setIndustry(WorkDataOrganisation industry)
    {
        this.industry = industry;
    }

    public WorkDataOrganisation getSize()
    {
        return size;
    }

    public void setSize(WorkDataOrganisation size)
    {
        this.size = size;
    }

    public String getWeb()
    {
        return web;
    }

    public void setWeb(String web)
    {
        this.web = web;
    }

    public String getExternalKey()
    {
        return externalKey;
    }

    public void setExternalKey(String externalKey)
    {
        this.externalKey = externalKey;
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

    public CommunicationHistory getLatestCall() {
        return latestCall;
    }

    public void setLatestCall(CommunicationHistory latestCall) {
        this.latestCall = latestCall;
    }

    public CommunicationHistory getLatestDial() {
        return latestDial;
    }

    public void setLatestDial(CommunicationHistory latestDial) {
        this.latestDial = latestDial;
    }

    public List<OrganisationCustomData> getOrganisationCustomDataList()
    {
        return organisationCustomDataList;
    }

    public void setOrganisationCustomDataList(List<OrganisationCustomData> organisationCustomDataList)
    {
        this.organisationCustomDataList = organisationCustomDataList;
    }

    public List<User> getParticipantList()
    {
        return participantList;
    }

    public void setParticipantList(List<User> participantList)
    {
        this.participantList = participantList;
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

    public Boolean getAddFromLead() {
        return addFromLead;
    }

    public void setAddFromLead(Boolean addFromLead) {
        this.addFromLead = addFromLead;
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

    public List<OrganisationVismaCustomer> getOrganisationVismaCustomerList()
    {
        return organisationVismaCustomerList;
    }

    public void setOrganisationVismaCustomerList(List<OrganisationVismaCustomer> organisationVismaCustomerList)
    {
        this.organisationVismaCustomerList = organisationVismaCustomerList;
    }

    public String getPhoneticName()
    {
        return phoneticName;
    }

    public void setPhoneticName(String phoneticName)
    {
        this.phoneticName = phoneticName;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (null == obj)
        {
            return false;
        }
        else if (!(obj instanceof Organisation))
        {
            return false;
        }
        else if (obj == this)
        {
            return true;
        }
        else
        {
            return isEquals((Organisation) obj);
        }
    }

    private boolean isEquals(Organisation organisation)
    {
        if (organisation.getUuid().compareTo(this.getUuid()) == 0)
        {
            return true;
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
        if (state != null)
        {
            if (fullAddress.length() > 0)
            {
                fullAddress = fullAddress + ", ";
            }
            fullAddress = fullAddress + state;
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

    public String getFortNoxId()
    {
        return fortNoxId;
    }

    public void setFortNoxId(String fortNoxId)
    {
        this.fortNoxId = fortNoxId;
    }
}
