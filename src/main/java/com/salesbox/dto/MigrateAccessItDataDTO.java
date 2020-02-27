package com.salesbox.dto;

import java.util.UUID;

public class MigrateAccessItDataDTO
{

    private UUID orderRowId;

    private String description;

    private String customFieldDescription;

    public MigrateAccessItDataDTO(UUID orderRowId, String description, String customFieldDescription) {
        this.orderRowId = orderRowId;
        this.description = description;
        this.customFieldDescription = customFieldDescription;
    }

    public UUID getOrderRowId() {
        return orderRowId;
    }

    public void setOrderRowId(UUID orderRowId) {
        this.orderRowId = orderRowId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomFieldDescription() {
        return customFieldDescription;
    }

    public void setCustomFieldDescription(String customFieldDescription) {
        this.customFieldDescription = customFieldDescription;
    }
}
