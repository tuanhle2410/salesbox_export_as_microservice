package com.salesbox.dto.customField;

import com.salesbox.entity.CustomField;
import com.salesbox.entity.enums.CustomFieldType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.annotation.OrikaMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Custom field DTO
 * Created by NEO on 1/11/2017.
 */
@OrikaMapper(mapClass = CustomField.class)
public class CustomFieldDTO implements Serializable
{
    private UUID uuid;
    private CustomFieldType fieldType;
    private UUID enterpriseId;
    private ObjectType objectType;
    private String title;
    private Boolean required = false;
    private Boolean active = true;
    private Integer position = 0;
    private CustomFieldOptionDTO customFieldOptionDTO;
    private List<CustomFieldValueDTO> customFieldValueDTOList = new ArrayList<>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public CustomFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(CustomFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public UUID getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public CustomFieldOptionDTO getCustomFieldOptionDTO() {
        return customFieldOptionDTO;
    }

    public void setCustomFieldOptionDTO(CustomFieldOptionDTO customFieldOptionDTO) {
        this.customFieldOptionDTO = customFieldOptionDTO;
    }

    public List<CustomFieldValueDTO> getCustomFieldValueDTOList() {
        return customFieldValueDTOList;
    }

    public void setCustomFieldValueDTOList(List<CustomFieldValueDTO> customFieldValueDTOList) {
        this.customFieldValueDTOList = customFieldValueDTOList;
    }
}
