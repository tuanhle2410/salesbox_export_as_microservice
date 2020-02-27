package com.salesbox.entity;

import com.salesbox.entity.enums.SubscriptionProductType;
import com.salesbox.entity.enums.SubscriptionSourceType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hunglv2
 * Date: 29/1/16
 * Time: 7:52 PM
 */
@Entity
@Table(name = "subscription_info")
public class SubscriptionInfo extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name ="number_paid_license")
    private Integer numberPaidLicense = 0;

    @Column(name = "source")
    @Enumerated(EnumType.ORDINAL)
    private SubscriptionSourceType source = SubscriptionSourceType.STRIPE;

    @Column(name = "product")
    @Enumerated(EnumType.ORDINAL)
    private SubscriptionProductType product = SubscriptionProductType.NORMAL_1999_PER_USER_PER_MONTH;

    @Column(name = "next_billing_date")
    private Date nextBillingDate;

    @Column(name = "vat_percent")
    private Double vatPercent = 0d;

    @Column(name = "start_discount_period")
    private Date startDiscountPeriod;

    @Column(name = "end_discount_period")
    private Date endDiscountPeriod;

    @Column(name = "discount_percent")
    private Double discountPercent = 0d;

    @Column(name = "price_per_unit")
    private Double pricePerUnit = 0d;

    @Column(name = "total")
    private Double total;

    @Column(name = "currency")
    private String currency = "USD";

    @Column(name = "amazon_subscription_id")
    private String amazonSubscriptionId;

    @Column(name = "cancellation_date")
    private Date cancellationDate;

    public Double getTotal()
    {
        return total;
    }

    public void setTotal(Double total)
    {
        this.total = total;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public Double getPricePerUnit()
    {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit)
    {
        this.pricePerUnit = pricePerUnit;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public Integer getNumberPaidLicense()
    {
        return numberPaidLicense;
    }

    public void setNumberPaidLicense(Integer numberPaidLicense)
    {
        this.numberPaidLicense = numberPaidLicense;
    }

    public SubscriptionSourceType getSource()
    {
        return source;
    }

    public void setSource(SubscriptionSourceType source)
    {
        this.source = source;
    }

    public SubscriptionProductType getProduct()
    {
        return product;
    }

    public void setProduct(SubscriptionProductType product)
    {
        this.product = product;
    }

    public Date getNextBillingDate()
    {
        return nextBillingDate;
    }

    public void setNextBillingDate(Date nextBillingDate)
    {
        this.nextBillingDate = nextBillingDate;
    }

    public Double getVatPercent()
    {
        return vatPercent;
    }

    public void setVatPercent(Double vatPercent)
    {
        this.vatPercent = vatPercent;
    }

    public Date getStartDiscountPeriod()
    {
        return startDiscountPeriod;
    }

    public void setStartDiscountPeriod(Date startDiscountPeriod)
    {
        this.startDiscountPeriod = startDiscountPeriod;
    }

    public Date getEndDiscountPeriod()
    {
        return endDiscountPeriod;
    }

    public void setEndDiscountPeriod(Date endDiscountPeriod)
    {
        this.endDiscountPeriod = endDiscountPeriod;
    }

    public Double getDiscountPercent()
    {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent)
    {
        this.discountPercent = discountPercent;
    }

    public String getAmazonSubscriptionId() {
        return amazonSubscriptionId;
    }

    public void setAmazonSubscriptionId(String amazonSubscriptionId) {
        this.amazonSubscriptionId = amazonSubscriptionId;
    }

    public Date getCancellationDate()
    {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate)
    {
        this.cancellationDate = cancellationDate;
    }
}
