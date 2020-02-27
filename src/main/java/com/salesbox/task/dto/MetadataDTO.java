package com.salesbox.task.dto;

/**
 * Created by lent on 6/6/2015.
 */
public class MetadataDTO
{
    private String uuid;
    private String value;

    public MetadataDTO() {}

    public MetadataDTO(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
