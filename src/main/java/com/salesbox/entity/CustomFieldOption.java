package com.salesbox.entity;

import com.salesbox.entity.enums.UrlType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom field option entity
 * Created by NEO on 1/10/2017.
 */
@Entity
@Table(name = "custom_field_option")
public class CustomFieldOption extends BaseEntity
{
    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "number_of_integer")
    private Integer numberOfIntegers;

    @Column(name = "number_of_decimal")
    private Integer numberOfDecimals;

    @Column(name = "url_type")
    @Enumerated(EnumType.ORDINAL)
    private UrlType urlType;

    @Column(name = "multi_choice")
    private Boolean multiChoice = false;

    @Column(name = "search_prefix")
    private String searchPrefix;

    @Column(name = "date_format")
    private String dateFormat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customFieldOption")
    private List<CustomFieldOptionValue> customFieldOptionValueList = new ArrayList<>();

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public UrlType getUrlType() {
        return urlType;
    }

    public void setUrlType(UrlType urlType) {
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

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Integer getNumberOfIntegers() {
        return numberOfIntegers;
    }

    public void setNumberOfIntegers(Integer numberOfIntegers) {
        this.numberOfIntegers = numberOfIntegers;
    }

    public Integer getNumberOfDecimals() {
        return numberOfDecimals;
    }

    public void setNumberOfDecimals(Integer numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;
    }

    public List<CustomFieldOptionValue> getCustomFieldOptionValueList()
    {
        return customFieldOptionValueList;
    }

    public void setCustomFieldOptionValueList(List<CustomFieldOptionValue> customFieldOptionValueList)
    {
        this.customFieldOptionValueList = customFieldOptionValueList;
    }
}
