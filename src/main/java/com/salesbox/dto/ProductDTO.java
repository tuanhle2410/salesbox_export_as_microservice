package com.salesbox.dto;

import com.salesbox.annotation.OrikaMapper;
import com.salesbox.entity.Product;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by quynhtq on 4/26/14.
 */
@OrikaMapper(mapClass = Product.class)
public class ProductDTO implements Serializable
{
    private UUID uuid;
    private String name;
    private Double price;
    private Boolean active;
    private Double quantity;
    private Double margin;
    private UUID lineOfBusinessId;
    private UUID measurementTypeId;
    private String measurementTypeName;
    private String lineOfBusinessName;
    private Double costUnit;
    private String description;
    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
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

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Double quantity)
    {
        this.quantity = quantity;
    }

    public UUID getLineOfBusinessId()
    {
        return lineOfBusinessId;
    }

    public void setLineOfBusinessId(UUID lineOfBusinessId)
    {
        this.lineOfBusinessId = lineOfBusinessId;
    }

    public UUID getMeasurementTypeId()
    {
        return measurementTypeId;
    }

    public void setMeasurementTypeId(UUID measurementTypeId)
    {
        this.measurementTypeId = measurementTypeId;
    }

    public Double getMargin()
    {
        return margin;
    }

    public void setMargin(Double margin)
    {
        this.margin = margin;
    }

    public String getMeasurementTypeName()
    {
        return measurementTypeName;
    }

    public void setMeasurementTypeName(String measurementTypeName)
    {
        this.measurementTypeName = measurementTypeName;
    }

    public String getLineOfBusinessName()
    {
        return lineOfBusinessName;
    }

    public void setLineOfBusinessName(String lineOfBusinessName)
    {
        this.lineOfBusinessName = lineOfBusinessName;
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
}
