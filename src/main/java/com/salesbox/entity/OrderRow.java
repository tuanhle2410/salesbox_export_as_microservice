package com.salesbox.entity;

import com.salesbox.entity.enums.OrderRowsType;
import com.salesbox.entity.enums.PeriodType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * User: luult
 * Date: 5/21/14
 */
@Entity
@Table(name = "order_row")
public class OrderRow extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private MeasurementType measurementType;

    @Column(name = "order_rows_type")
    @Enumerated(EnumType.ORDINAL)
    private OrderRowsType type = OrderRowsType.NORMAL;

    @Column(name = "number_of_unit")
    private Double numberOfUnit;

    @Column(name = "price")
    private Double price;

    @Column(name = "cost_per_unit")
    private Double costUnit;

    @Column(name = "delivery_start_date")
    private Date deliveryStartDate;

    @Column(name = "delivery_end_date")
    private Date deliveryEndDate;

    @Column(name = "margin")
    private Double margin;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;

    @Column(name = "period_type")
    private PeriodType periodType;

    @Column(name = "period_number")
    private Integer periodNumber;

    @Column(name = "maestrano_sales_order_id")
    private String maestranoSalesOrdersId;

    @Column(name = "discounted_price")
    private Double discountedPrice;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "description")
    private String description;

    public Double getDiscountedPrice()
    {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice)
    {
        this.discountedPrice = discountedPrice;
    }

    public Double getDiscountPercent()
    {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent)
    {
        this.discountPercent = discountPercent;
    }

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }

    public PeriodType getPeriodType()
    {
        return periodType;
    }

    public void setPeriodType(PeriodType periodType)
    {
        this.periodType = periodType;
    }

    public Integer getPeriodNumber()
    {
        return periodNumber;
    }

    public void setPeriodNumber(Integer periodNumber)
    {
        this.periodNumber = periodNumber;
    }

    public Double getOrderValue()
    {
        return numberOfUnit * price;
    }

    public Double getCostValue()
    {
        return (numberOfUnit == null ? 0 : numberOfUnit) * (costUnit == null ? 0 : costUnit);
    }

    public Double getProfit()
    {
        return getOrderValue() - getCostValue();
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public MeasurementType getMeasurementType()
    {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType)
    {
        this.measurementType = measurementType;
    }

    public Double getNumberOfUnit()
    {
        return numberOfUnit;
    }

    public void setNumberOfUnit(Double numberOfUnit)
    {
        this.numberOfUnit = numberOfUnit;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Date getDeliveryStartDate()
    {
        return deliveryStartDate;
    }

    public void setDeliveryStartDate(Date deliveryStartDate)
    {
        this.deliveryStartDate = deliveryStartDate;
    }

    public Date getDeliveryEndDate()
    {
        return deliveryEndDate;
    }

    public void setDeliveryEndDate(Date deliveryEndDate)
    {
        this.deliveryEndDate = deliveryEndDate;
    }

    public Double getMargin()
    {
        return margin;
    }

    public void setMargin(Double margin)
    {
        this.margin = margin;
    }

    public OrderRowsType getType()
    {
        return type;
    }

    public void setType(OrderRowsType type)
    {
        this.type = type;
    }

    public String getMaestranoSalesOrdersId()
    {
        return maestranoSalesOrdersId;
    }

    public void setMaestranoSalesOrdersId(String maestranoSalesOrdersId)
    {
        this.maestranoSalesOrdersId = maestranoSalesOrdersId;
    }

    public Double getCostUnit()
    {
        return costUnit == null ? 0 : costUnit;
    }

    public void setCostUnit(Double costUnit)
    {
        this.costUnit = costUnit;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
