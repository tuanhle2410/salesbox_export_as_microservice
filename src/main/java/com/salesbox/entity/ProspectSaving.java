package com.salesbox.entity;

import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Date: 09/08/2017
 */
@Entity
@Table(name = "prospect_saving")
public class ProspectSaving extends ProspectBase
{
    public ProspectSaving()
    {
    }
    public ProspectSaving(ProspectActive prospectActive)
    {
        BeanUtils.copyProperties(prospectActive,this);
    }
}
