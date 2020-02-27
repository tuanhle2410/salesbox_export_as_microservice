package com.salesbox.entity.view;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by admin on 4/20/2017.
 */

@Entity
@Table(name = "v_product")
public class ProductView
{
    @Id
    @Column(name = "uuid", unique = true, nullable = false, columnDefinition = "uuid")
    @Type(type = "pg-uuid")
    private UUID uuid = UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "line_of_business_id")
    @Type(type = "pg-uuid")
    private UUID lineOfBusinessId;

    @Column(name = "active")
    private Boolean active = Boolean.TRUE;

    @Column(name = "margin")
    private Double margin;

    @Column(name = "measurement_type_id")
    @Type(type = "pg-uuid")
    private UUID measurementTypeId;

    @Column(name = "price", precision = 19, scale = 2)
    private Double price;

    @Column(name = "quantity", precision = 19, scale = 2)
    private Double quantity;

    @Column(name = "measurement_type_name")
    private String measurementTypeName;

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

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public UUID getLineOfBusinessId()
    {
        return lineOfBusinessId;
    }

    public void setLineOfBusinessId(UUID lineOfBusinessId)
    {
        this.lineOfBusinessId = lineOfBusinessId;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Double getMargin()
    {
        return margin;
    }

    public void setMargin(Double margin)
    {
        this.margin = margin;
    }

    public UUID getMeasurementTypeId()
    {
        return measurementTypeId;
    }

    public void setMeasurementTypeId(UUID measurementTypeId)
    {
        this.measurementTypeId = measurementTypeId;
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

    public String getMeasurementTypeName()
    {
        return measurementTypeName;
    }

    public void setMeasurementTypeName(String measurementTypeName)
    {
        this.measurementTypeName = measurementTypeName;
    }
}
