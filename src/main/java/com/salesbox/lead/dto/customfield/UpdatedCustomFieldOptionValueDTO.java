package com.salesbox.lead.dto.customfield;

import com.salesbox.annotation.OrikaMapper;
import com.salesbox.entity.CustomFieldOptionValue;

import java.util.UUID;

/**
 * CustomFieldOptionValueDTO
 * Created by NEO on 1/11/2017.
 */
@OrikaMapper(mapClass = CustomFieldOptionValue.class)
public class UpdatedCustomFieldOptionValueDTO
{
    private UUID uuid;
    private String value;
    private Integer position;
    private Boolean active;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
