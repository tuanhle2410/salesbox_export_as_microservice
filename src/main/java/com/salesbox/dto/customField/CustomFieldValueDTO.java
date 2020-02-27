package com.salesbox.dto.customField;

import com.salesbox.entity.CustomFieldValue;
import com.salesbox.annotation.OrikaMapper;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Custom field value DTO
 * Created by NEO on 1/17/2017.
 */
@OrikaMapper(mapClass = CustomFieldValue.class)
public class CustomFieldValueDTO implements Serializable {
    private UUID uuid;
    private UUID objectId;
    private CustomFieldDTO customFieldDTO;
    private String value;
    private Boolean isChecked = false;
    private UUID customFieldOptionValueUuid;
    private Date dateValue;
    private UUID productId;

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public CustomFieldDTO getCustomFieldDTO() {
        return customFieldDTO;
    }

    public void setCustomFieldDTO(CustomFieldDTO customFieldDTO) {
        this.customFieldDTO = customFieldDTO;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public UUID getCustomFieldOptionValueUuid() {
        return customFieldOptionValueUuid;
    }

    public void setCustomFieldOptionValueUuid(UUID customFieldOptionValueUuid) {
        this.customFieldOptionValueUuid = customFieldOptionValueUuid;
    }

    public Date getDateValue()
    {
        return dateValue;
    }

    public void setDateValue(Date dateValue)
    {
        this.dateValue = dateValue;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
