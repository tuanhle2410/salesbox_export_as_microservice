
package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by quynhtq on 4/27/14.
 */
@Entity
@Table(name = "product")
public class Product extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @Column(name = "name")
    private String name;

    @Column(name = "price", precision = 19, scale = 2)
    private Double price;

    @Column(name = "active")
    private Boolean active = Boolean.TRUE;

    @Column(name = "quantity", precision = 19, scale = 2)
    private Double quantity;

    @Column(name = "margin")
    private Double margin;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "is_in_wizard")
    private Boolean isInWizard = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_of_business_id")
    private LineOfBusiness lineOfBusiness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measurement_type_id")
    private MeasurementType measurementType;

    @Column(name = "maestrano_id")
    private String maestranoId;

    @Column(name = "cost_unit")
    private Double costUnit;

    @Column(name = "description")
    private String description;

    @Column(name = "visma_id")
    private String vismaId;

    @Column(name = "fortnox_id")
    private String fortnoxId;

// --------------------- GETTER / SETTER METHODS ---------------------


    public String getVismaId()
    {
        return vismaId;
    }

    public void setVismaId(String vismaId)
    {
        this.vismaId = vismaId;
    }

    public String getMaestranoId() {
        return maestranoId;
    }

    public void setMaestranoId(String maestranoId) {
        this.maestranoId = maestranoId;
    }

    public Boolean getIsInWizard()
    {
        return isInWizard;
    }

    public void setIsInWizard(Boolean isInWizard)
    {
        this.isInWizard = isInWizard;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public LineOfBusiness getLineOfBusiness()
    {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(LineOfBusiness lineOfBusiness)
    {
        this.lineOfBusiness = lineOfBusiness;
    }

    public MeasurementType getMeasurementType()
    {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType)
    {
        this.measurementType = measurementType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Double quantity)
    {
        this.quantity = quantity;
    }

    public Double getMargin()
    {
        return margin;
    }

    public void setMargin(Double margin)
    {
        this.margin = margin;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public Double getCostUnit()
    {
        return costUnit;
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

    public String getFortnoxId()
    {
        return fortnoxId;
    }

    public void setFortnoxId(String fortnoxId)
    {
        this.fortnoxId = fortnoxId;
    }
}
