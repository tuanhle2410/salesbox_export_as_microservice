package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by GEM on 12/7/2017.
 */
@Entity
@Table(name = "prospect_serial")
public class ProspectSerial extends BaseEntity
{
    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "enterprise_id")
    private UUID enterpriseId;

    @Column(name = "year_number")
    private Integer year;

    @Column(name = "current_max_serial")
    private Integer currentMaxSerial;

    public UUID getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getCurrentMaxSerial()
    {
        return currentMaxSerial;
    }

    public void setCurrentMaxSerial(Integer currentMaxSerial)
    {
        this.currentMaxSerial = currentMaxSerial;
    }

    public ProspectSerial(UUID enterpriseId, Integer year, Integer currentMaxSerial)
    {
        this.enterpriseId = enterpriseId;
        this.year = year;
        this.currentMaxSerial = currentMaxSerial;
    }

    public ProspectSerial()
    {
    }
}
