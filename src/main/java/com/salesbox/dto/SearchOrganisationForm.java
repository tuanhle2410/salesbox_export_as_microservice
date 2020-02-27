package com.salesbox.dto;

import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 5/20/2017.
 */
public class SearchOrganisationForm
{
    private List<UUID> organisationIds;
    private List<UUID> sizeIds;
    private List<UUID> industryIds;
    private List<UUID> enterpriseIds;
    private List<String> cities;
    private List<String> keywords;

    public List<UUID> getOrganisationIds()
    {
        return organisationIds;
    }

    public void setOrganisationIds(List<UUID> organisationIds)
    {
        this.organisationIds = organisationIds;
    }

    public List<UUID> getSizeIds()
    {
        return sizeIds;
    }

    public void setSizeIds(List<UUID> sizeIds)
    {
        this.sizeIds = sizeIds;
    }

    public List<UUID> getIndustryIds()
    {
        return industryIds;
    }

    public void setIndustryIds(List<UUID> industryIds)
    {
        this.industryIds = industryIds;
    }

    public List<UUID> getEnterpriseIds()
    {
        return enterpriseIds;
    }

    public void setEnterpriseIds(List<UUID> enterpriseIds)
    {
        this.enterpriseIds = enterpriseIds;
    }

    public List<String> getCities()
    {
        return cities;
    }

    public void setCities(List<String> cities)
    {
        this.cities = cities;
    }

    public List<String> getKeywords()
    {
        return keywords;
    }

    public void setKeywords(List<String> keywords)
    {
        this.keywords = keywords;
    }
}
