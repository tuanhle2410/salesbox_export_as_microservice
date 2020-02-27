package com.salesbox.dto.customField;

import com.salesbox.entity.CustomFieldOption;
import com.salesbox.annotation.OrikaMapper;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * CustomFieldOptionDTO
 * Created by NEO on 1/11/2017.
 */
@OrikaMapper(mapClass = CustomFieldOption.class)
public class CustomFieldOptionDTO implements Serializable {
    private UUID uuid;
    private Integer maxLength;
    private Integer numberOfIntegers;
    private Integer numberOfDecimals;
    private String urlType;
    private Boolean multiChoice;
    private String searchPrefix;
    private String dateFormat;
    private List<CustomFieldOptionValueDTO> customFieldOptionValueDTOList;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public Boolean getMultiChoice() {
        return multiChoice;
    }

    public void setMultiChoice(Boolean multiChoice) {
        this.multiChoice = multiChoice;
    }

    public String getSearchPrefix() {
        return searchPrefix;
    }

    public void setSearchPrefix(String searchPrefix) {
        this.searchPrefix = searchPrefix;
    }

    public List<CustomFieldOptionValueDTO> getCustomFieldOptionValueDTOList() {
        return customFieldOptionValueDTOList;
    }

    public void setCustomFieldOptionValueDTOList(List<CustomFieldOptionValueDTO> customFieldOptionValueDTOList) {
        this.customFieldOptionValueDTOList = customFieldOptionValueDTOList;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Integer getNumberOfDecimals() {
        return numberOfDecimals;
    }

    public void setNumberOfDecimals(Integer numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;
    }

    public Integer getNumberOfIntegers() {
        return numberOfIntegers;
    }

    public void setNumberOfIntegers(Integer numberOfIntegers) {
        this.numberOfIntegers = numberOfIntegers;
    }
}
