package com.salesbox.dto;

import com.salesbox.common.Constant;
import com.salesbox.entity.SubscriptionInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by HUNGLV2 on 5/11/2016.
 */
public class SubscriptionInfoDTO implements Serializable
{
    private String uuid;
    private String amazonSubscriptionId;
    private String amazonSubscriptionName;
    private Integer numberLicense;
    private Integer numberUsedLicense;
    private Date nextBillingDate;
    private Double total;

    public SubscriptionInfoDTO()
    {
    }

    public SubscriptionInfoDTO(SubscriptionInfo subscriptionInfo)
    {
        this.uuid = subscriptionInfo.getUuid().toString();
        this.amazonSubscriptionId = subscriptionInfo.getAmazonSubscriptionId();
        this.amazonSubscriptionName = "Salesbox " + subscriptionInfo.getNumberPaidLicense() + " license";
        this.amazonSubscriptionName += subscriptionInfo.getNumberPaidLicense() > 1 ? "s" : "";
        this.numberLicense = subscriptionInfo.getNumberPaidLicense();
        this.nextBillingDate = subscriptionInfo.getNextBillingDate();
        this.total = this.numberLicense * Constant.PRICE_PER_LICENSE;
    }

    public String getAmazonSubscriptionName()
    {
        return amazonSubscriptionName;
    }

    public void setAmazonSubscriptionName(String amazonSubscriptionName)
    {
        this.amazonSubscriptionName = amazonSubscriptionName;
    }

    public Double getTotal()
    {
        return total;
    }

    public void setTotal(Double total)
    {
        this.total = total;
    }

    public Date getNextBillingDate()
    {
        return nextBillingDate;
    }

    public void setNextBillingDate(Date nextBillingDate)
    {
        this.nextBillingDate = nextBillingDate;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getAmazonSubscriptionId()
    {
        return amazonSubscriptionId;
    }

    public void setAmazonSubscriptionId(String amazonSubscriptionId)
    {
        this.amazonSubscriptionId = amazonSubscriptionId;
    }

    public Integer getNumberLicense()
    {
        return numberLicense;
    }

    public void setNumberLicense(Integer numberLicense)
    {
        this.numberLicense = numberLicense;
    }

    public Integer getNumberUsedLicense()
    {
        return numberUsedLicense;
    }

    public void setNumberUsedLicense(Integer numberUsedLicense)
    {
        this.numberUsedLicense = numberUsedLicense;
    }
}
