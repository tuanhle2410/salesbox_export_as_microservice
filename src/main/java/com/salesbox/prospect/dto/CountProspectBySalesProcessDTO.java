package com.salesbox.prospect.dto;

/**
 * Created by tuantx on 4/2/2016.
 */
public class CountProspectBySalesProcessDTO
{
    private String salesProcessId;
    private String salesProcessName;
    private long count;
    private Double grossValue;
    private Double netValue;
    private Double profitValue;
    private String mode;
    private String manual;

    public CountProspectBySalesProcessDTO(String salesProcessId, String salesProcessName, long count,
                                          Double grossValue, Double netValue,
                                          String mode, String manual)
    {
        this.salesProcessId = salesProcessId;
        this.salesProcessName = salesProcessName;
        this.count = count;
        this.grossValue = grossValue;
        this.netValue = netValue;
        this.mode = mode;
        this.manual = manual;
    }

    public String getSalesProcessId()
    {
        return salesProcessId;
    }

    public void setSalesProcessId(String salesProcessId)
    {
        this.salesProcessId = salesProcessId;
    }

    public String getSalesProcessName()
    {
        return salesProcessName;
    }

    public void setSalesProcessName(String salesProcessName)
    {
        this.salesProcessName = salesProcessName;
    }

    public long getCount()
    {
        return count;
    }

    public void setCount(long count)
    {
        this.count = count;
    }

    public Double getGrossValue()
    {
        return grossValue;
    }

    public void setGrossValue(Double grossValue)
    {
        this.grossValue = grossValue;
    }

    public Double getNetValue()
    {
        return netValue;
    }

    public void setNetValue(Double netValue)
    {
        this.netValue = netValue;
    }

    public Double getProfitValue()
    {
        return profitValue;
    }

    public void setProfitValue(Double profitValue)
    {
        this.profitValue = profitValue;
    }

    public String getMode()
    {
        return mode;
    }

    public void setMode(String mode)
    {
        this.mode = mode;
    }

    public String getManual()
    {
        return manual;
    }

    public void setManual(String manual)
    {
        this.manual = manual;
    }
}
