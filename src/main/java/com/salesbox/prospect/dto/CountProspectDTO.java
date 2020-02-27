package com.salesbox.prospect.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuantx on 4/2/2016.
 */
public class CountProspectDTO {
    private String sessionKey;
    private long count;
    private Double totalGrossValue;
    private Double totalNetValue;
    private Double totalWeight;
    private Double totalProfit;

    List<CountProspectBySalesProcessDTO> countProspectBySalesProcessDTOs = new ArrayList<>();

    public CountProspectDTO() {
    }

    public CountProspectDTO(String sessionKey, long count, Double totalGrossValue, Double totalNetValue) {
        this.sessionKey = sessionKey;
        this.count = count;
        this.totalGrossValue = totalGrossValue;
        this.totalNetValue = totalNetValue;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Double getTotalGrossValue() {
        return totalGrossValue;
    }

    public void setTotalGrossValue(Double totalGrossValue) {
        this.totalGrossValue = totalGrossValue;
    }

    public Double getTotalNetValue() {
        return totalNetValue;
    }

    public void setTotalNetValue(Double totalNetValue) {
        this.totalNetValue = totalNetValue;
    }

    public Double getTotalProfit()
    {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit)
    {
        this.totalProfit = totalProfit;
    }

    public List<CountProspectBySalesProcessDTO> getCountProspectBySalesProcessDTOs()
    {
        return countProspectBySalesProcessDTOs;
    }

    public void setCountProspectBySalesProcessDTOs(List<CountProspectBySalesProcessDTO> countProspectBySalesProcessDTOs)
    {
        this.countProspectBySalesProcessDTOs = countProspectBySalesProcessDTOs;
    }

    public Double getTotalWeight()
    {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight)
    {
        this.totalWeight = totalWeight;
    }
}
