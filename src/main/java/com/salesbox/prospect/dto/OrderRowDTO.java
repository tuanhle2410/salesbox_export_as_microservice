package com.salesbox.prospect.dto;

import com.salesbox.dto.ProductDTO;
import com.salesbox.dto.customField.CustomFieldListDTO;
import com.salesbox.entity.OrderRow;
import com.salesbox.annotation.OrikaMapper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * [File description]
 *
 * @author lent
 * @version 1.0
 * @since 5/1/14
 */
@OrikaMapper(mapClass = OrderRow.class)
public class OrderRowDTO implements Serializable
{
    private UUID uuid;
    private Date deliveryStartDate;
    private Date deliveryEndDate;
    private Double price;
    private Double margin;
    private Double numberOfUnit;
    private ProductDTO productDTO;
    private UUID measurementTypeId;
    private String measurementTypeName;
    private UUID prospectId;
    private String type;
    private String periodType;
    private Integer periodNumber;
    private CustomFieldListDTO customFieldListDTO;
    private Double costUnit;
    private String serialNumber;
    private Boolean won;
    private String lineOfBusinessName;
    private UUID lineOfBusinessId;
    private Double discountedPrice;
    private Double discountPercent;
    private List<ProductDTO> productList;
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

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
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

    public ProductDTO getProductDTO()
    {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO)
    {
        this.productDTO = productDTO;
    }

    public UUID getMeasurementTypeId()
    {
        return measurementTypeId;
    }

    public void setMeasurementTypeId(UUID measurementTypeId)
    {
        this.measurementTypeId = measurementTypeId;
    }

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getNumberOfUnit()
    {
        return numberOfUnit;
    }

    public void setNumberOfUnit(Double numberOfUnit)
    {
        this.numberOfUnit = numberOfUnit;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPeriodType()
    {
        return periodType;
    }

    public void setPeriodType(String periodType)
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

    public CustomFieldListDTO getCustomFieldListDTO()
    {
        return customFieldListDTO;
    }

    public void setCustomFieldListDTO(CustomFieldListDTO customFieldListDTO)
    {
        this.customFieldListDTO = customFieldListDTO;
    }

    public String getMeasurementTypeName()
    {
        return measurementTypeName;
    }

    public void setMeasurementTypeName(String measurementTypeName)
    {
        this.measurementTypeName = measurementTypeName;
    }

    public Double getCostUnit()
    {
        return costUnit;
    }

    public void setCostUnit(Double costUnit)
    {
        this.costUnit = costUnit;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public Boolean getWon()
    {
        return won;
    }

    public void setWon(Boolean won)
    {
        this.won = won;
    }

    public String getLineOfBusinessName()
    {
        return lineOfBusinessName;
    }

    public void setLineOfBusinessName(String lineOfBusinessName)
    {
        this.lineOfBusinessName = lineOfBusinessName;
    }

    public UUID getLineOfBusinessId()
    {
        return lineOfBusinessId;
    }

    public void setLineOfBusinessId(UUID lineOfBusinessId)
    {
        this.lineOfBusinessId = lineOfBusinessId;
    }

    public List<ProductDTO> getProductList()
    {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList)
    {
        this.productList = productList;
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
