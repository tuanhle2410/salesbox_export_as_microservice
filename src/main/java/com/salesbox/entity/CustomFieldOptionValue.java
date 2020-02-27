package com.salesbox.entity;

import javax.persistence.*;

/**
 * Custom field option value entity
 * Created by NEO on 1/10/2017.
 */
@Entity
@Table(name = "custom_field_option_value")
public class CustomFieldOptionValue extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_field_option_id")
    private CustomFieldOption customFieldOption;

    @Column(name = "value")
    private String value;

    @Column(name = "position")
    private Integer position;

    @Column(name = "active")
    private Boolean active = true;

    public CustomFieldOption getCustomFieldOption() {
        return customFieldOption;
    }

    public void setCustomFieldOption(CustomFieldOption customFieldOption) {
        this.customFieldOption = customFieldOption;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
