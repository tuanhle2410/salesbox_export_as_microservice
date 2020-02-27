package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Custom field value entity
 * Created by NEO on 1/10/2017.
 */
@Entity
@Table(name = "custom_field_value")
public class CustomFieldValue extends BaseEntity
{
    @Column(name = "object_id")
    @Type(type = "pg-uuid")
    private UUID objectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_field_id")
    private CustomField customField;

    @Column(name = "value")
    private String value;

    @Column(name = "date_value")
    private Date dateValue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_field_option_value_id")
    private CustomFieldOptionValue customFieldOptionValue;

    @Column(name = "is_checked")
    private Boolean isChecked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CustomFieldOptionValue getCustomFieldOptionValue() {
        return customFieldOptionValue;
    }

    public void setCustomFieldOptionValue(CustomFieldOptionValue customFieldOptionValue) {
        this.customFieldOptionValue = customFieldOptionValue;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Date getDateValue()
    {
        return dateValue;
    }

    public void setDateValue(Date dateValue)
    {
        this.dateValue = dateValue;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
