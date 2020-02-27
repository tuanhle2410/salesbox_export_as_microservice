package com.salesbox.lead.dto.customfield;

import com.salesbox.annotation.OrikaMapper;
import com.salesbox.entity.CustomFieldValue;

import java.util.Date;
import java.util.UUID;

/**
 * Custom field value DTO
 * Created by NEO on 1/17/2017.
 */
@OrikaMapper(mapClass = CustomFieldValue.class)
public class UpdatedCustomFieldValueDTO
{
    private UUID uuid;
    private UUID objectId;
    private UpdatedCustomFieldDTO customFieldDTO;
    private String value;
    private Boolean isChecked = false;
    private UUID customFieldOptionValueUuid;
    private UUID productId;
    private Date dateValue;

    public UUID getObjectId()
    {
        return objectId;
    }

    public void setObjectId(UUID objectId)
    {
        this.objectId = objectId;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public UpdatedCustomFieldDTO getCustomFieldDTO()
    {
        return customFieldDTO;
    }

    public void setCustomFieldDTO(UpdatedCustomFieldDTO customFieldDTO)
    {
        this.customFieldDTO = customFieldDTO;
    }

    public Boolean getIsChecked()
    {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public UUID getCustomFieldOptionValueUuid()
    {
        return customFieldOptionValueUuid;
    }

    public void setCustomFieldOptionValueUuid(UUID customFieldOptionValueUuid)
    {
        this.customFieldOptionValueUuid = customFieldOptionValueUuid;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }
}
