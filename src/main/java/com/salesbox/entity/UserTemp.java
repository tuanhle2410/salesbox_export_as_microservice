package com.salesbox.entity;

import com.salesbox.entity.enums.DiscProfileType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 9/4/14
 * Time: 11:24 AM
 */
@Entity
@Table(name = "user_temp")
public class UserTemp extends BaseEntity
{
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "disc_profile")
    @Enumerated(EnumType.ORDINAL)
    private DiscProfileType discProfile;

    @Column(name = "is_manager")
    private Boolean manager;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "hunting_farming_ratio")
    private Integer huntingFarmingRatio;

    @ManyToOne
    @JoinColumn(name = "enterprise_temp_id")
    private EnterpriseTemp enterpriseTemp;

    @Column(name = "confirm_token")
    private String confirmToken;

    @Column(name = "confirm_token_expire")
    private Date confirmTokenExpire;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "country")
    private String country;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "maestrano_user_id")
    private String maestranoUserId;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
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

    public DiscProfileType getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(DiscProfileType discProfile)
    {
        this.discProfile = discProfile;
    }

    public Boolean getManager()
    {
        return manager;
    }

    public void setManager(Boolean manager)
    {
        this.manager = manager;
    }

    public Unit getUnit()
    {
        return unit;
    }

    public void setUnit(Unit unit)
    {
        this.unit = unit;
    }

    public Integer getHuntingFarmingRatio()
    {
        return huntingFarmingRatio;
    }

    public void setHuntingFarmingRatio(Integer huntingFarmingRatio)
    {
        this.huntingFarmingRatio = huntingFarmingRatio;
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

    public void EnterpriseTempsetConfirmTokenExpire(Date confirmTokenExpire)
    {
        this.confirmTokenExpire = confirmTokenExpire;
    }

    public EnterpriseTemp getEnterpriseTemp()
    {
        return enterpriseTemp;
    }

    public void setEnterpriseTemp(EnterpriseTemp enterpriseTemp)
    {
        this.enterpriseTemp = enterpriseTemp;
    }

    public User getCreator()
    {
        return creator;
    }

    public void setCreator(User creator)
    {
        this.creator = creator;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMaestranoUserId() {
        return maestranoUserId;
    }

    public void setMaestranoUserId(String maestranoUserId) {
        this.maestranoUserId = maestranoUserId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
