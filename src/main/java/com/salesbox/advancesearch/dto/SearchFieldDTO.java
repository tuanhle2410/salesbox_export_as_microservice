package com.salesbox.advancesearch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HUNGLV2 on 2/22/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchFieldDTO
{
    private String prefix;
    private String field;
    private String operator;
    private String valueId;
    private String valueText;
    private Date valueDate;
    private String customFieldId;
    private String productId;

    public SearchFieldDTO()
    {
    }

    public SearchFieldDTO(String field, String operator, String valueId, String valueText, Date valueDate, String customFieldId)
    {
        this.field = field;
        this.operator = operator;
        this.valueId = valueId;
        this.valueText = valueText;
        this.valueDate = valueDate;
        this.customFieldId = customFieldId;
    }

    public Date getValueDate()
    {
        return valueDate;
    }

    public void setValueDate(Date valueDate)
    {
        this.valueDate = valueDate;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getValueId()
    {
        return valueId;
    }

    public void setValueId(String valueId)
    {
        this.valueId = valueId;
    }

    public String getValueText()
    {
        return valueText;
    }

    public void setValueText(String valueText)
    {
        this.valueText = valueText;
    }

    public String getCustomFieldId()
    {
        return customFieldId;
    }

    public void setCustomFieldId(String customFieldId)
    {
        this.customFieldId = customFieldId;
    }

    public boolean checkIfNullValue()
    {
        return (null == valueId) && (null == valueText) && (null == valueDate) && (null == customFieldId);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        List<String> results = new ArrayList<>();
        if(prefix != null) {
            results.add(prefix);
        }
        if(field != null) {
            results.add(field);
        }
        if(operator != null) {
            results.add(operator);
        }
        if(valueId != null) {
            results.add(valueId);
        }
        if(valueText != null) {
            results.add(valueText);
        }
        if(valueDate != null) {
            results.add(valueDate.toString());
        }
        if(customFieldId != null) {
            results.add(customFieldId);
        }
        if(productId != null) {
            results.add(productId);
        }
        return String.join(".", results);
    }
}
