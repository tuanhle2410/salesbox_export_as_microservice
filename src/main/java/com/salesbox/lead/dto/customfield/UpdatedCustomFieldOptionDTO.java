package com.salesbox.lead.dto.customfield;

import com.salesbox.annotation.OrikaMapper;
import com.salesbox.entity.CustomFieldOption;

import java.util.List;
import java.util.UUID;

/**
 * CustomFieldOptionDTO
 * Created by NEO on 1/11/2017.
 */
@OrikaMapper(mapClass = CustomFieldOption.class)
public class UpdatedCustomFieldOptionDTO
{
    private UUID uuid;
    private Integer maxLength;
    private Integer numberOfIntegers;
    private Integer numberOfDecimals;
    private String urlType;
    private Boolean multiChoice;
    private String searchPrefix;
    private String dateFormat;
    private List<UpdatedCustomFieldOptionValueDTO> customFieldOptionValueDTOList;

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public Integer getMaxLength()
    {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength)
    {
        this.maxLength = maxLength;
    }

    public String getUrlType()
    {
        return urlType;
    }

    public void setUrlType(String urlType)
    {
        this.urlType = urlType;
    }

    public Boolean getMultiChoice()
    {
        return multiChoice;
    }

    public void setMultiChoice(Boolean multiChoice)
    {
        this.multiChoice = multiChoice;
    }

    public String getSearchPrefix()
    {
        return searchPrefix;
    }

    public void setSearchPrefix(String searchPrefix)
    {
        this.searchPrefix = searchPrefix;
    }

    public List<UpdatedCustomFieldOptionValueDTO> getCustomFieldOptionValueDTOList()
    {
        return customFieldOptionValueDTOList;
    }

    public void setCustomFieldOptionValueDTOList(List<UpdatedCustomFieldOptionValueDTO> customFieldOptionValueDTOList)
    {
        this.customFieldOptionValueDTOList = customFieldOptionValueDTOList;
    }

    public String getDateFormat()
    {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    public Integer getNumberOfDecimals()
    {
        return numberOfDecimals;
    }

    public void setNumberOfDecimals(Integer numberOfDecimals)
    {
        this.numberOfDecimals = numberOfDecimals;
    }

    public Integer getNumberOfIntegers()
    {
        return numberOfIntegers;
    }

    public void setNumberOfIntegers(Integer numberOfIntegers)
    {
        this.numberOfIntegers = numberOfIntegers;
    }
}
