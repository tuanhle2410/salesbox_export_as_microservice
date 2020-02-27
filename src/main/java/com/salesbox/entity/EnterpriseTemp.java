package com.salesbox.entity;

import com.salesbox.entity.enums.PromotionType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 9/4/14
 * Time: 11:51 AM
 */
@Entity
@Table(name = "enterprise_temp")
public class EnterpriseTemp extends BaseEntity
{
    @Column(name = "name")
    private String name;

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

    @Column(name = "promotion_type")
    private PromotionType promotionType = PromotionType.NORMAL;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "industry_id")
    private WorkDataOrganisation industry;

    @Column(name = "confirm_token")
    String confirmToken;

    @Column(name = "confirm_token_expire")
    private Date confirmTokenExpire;

    @Column(name ="number_license")
    private Integer numberLicense = 0;

    @OneToMany(mappedBy = "enterpriseTemp", cascade = CascadeType.ALL)
    private List<UserTemp> userTempList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "recruit_user")
    @Type(type = "pg-uuid")
    private UUID recruit_user;

    @Column(name ="registered_time_zone")
    private Integer registeredTimeZone;

    @Column(name ="platform")
    private String platform;

    @Column(name = "maestrano_group_id")
    private String maestranoGroupId;

    @Column(name = "tracking_key")
    private String trackingKey;

    public String getName()
    {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public WorkDataOrganisation getIndustry()
    {
        return industry;
    }

    public void setIndustry(WorkDataOrganisation industry)
    {
        this.industry = industry;
    }

    public String getConfirmToken()
    {
        return confirmToken;
    }

    public void setConfirmToken(String confirmToken)
    {
        this.confirmToken = confirmToken;
    }

    public Date getConfirmTokenExpire()
    {
        return confirmTokenExpire;
    }

    public void setConfirmTokenExpire(Date confirmTokenExpire)
    {
        this.confirmTokenExpire = confirmTokenExpire;
    }

    public Integer getNumberLicense()
    {
        return numberLicense;
    }

    public void setNumberLicense(Integer numberLicense)
    {
        this.numberLicense = numberLicense;
    }

    public Language getLanguage()
    {
        return language;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }

    public PromotionType getPromotionType()
    {
        return promotionType;
    }

    public void setPromotionType(PromotionType promotionType)
    {
        this.promotionType = promotionType;
    }

    public UUID getRecruit_user()
    {
        return recruit_user;
    }

    public void setRecruit_user(UUID recruit_user)
    {
        this.recruit_user = recruit_user;
    }

    public Integer getRegisteredTimeZone()
    {
        return registeredTimeZone;
    }

    public void setRegisteredTimeZone(Integer registeredTimeZone)
    {
        this.registeredTimeZone = registeredTimeZone;
    }

    public String getMaestranoGroupId() {
        return maestranoGroupId;
    }

    public void setMaestranoGroupId(String maestranoGroupId) {
        this.maestranoGroupId = maestranoGroupId;
    }

    public String getTrackingKey()
    {
        return trackingKey;
    }

    public void setTrackingKey(String trackingKey)
    {
        this.trackingKey = trackingKey;
    }
}
