package com.salesbox.entity;

import com.salesbox.entity.enums.CustomFieldType;
import com.salesbox.entity.enums.ObjectType;

import javax.persistence.*;

/**
 * Custom field entity
 * Custom field will be added by Admin for each enterprise, each Object type (Account, Contact...)
 * Created by NEO on 1/10/2017.
 */
@Entity
@Table(name = "custom_field")
public class CustomField extends BaseEntity
{
    @Column(name = "field_type")
    CustomFieldType fieldType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_field_option_id")
    private CustomFieldOption customFieldOption;

    @Column(name = "object_type")
    @Enumerated(EnumType.ORDINAL)
    private ObjectType objectType;

    @Column(name = "title")
    private String title;

    @Column(name = "required")
    private Boolean required = false;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "position")
    private Integer position = 0;

    public CustomFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(CustomFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
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

    public CustomFieldOption getCustomFieldOption() {
        return customFieldOption;
    }

    public void setCustomFieldOption(CustomFieldOption customFieldOption) {
        this.customFieldOption = customFieldOption;
    }
}
