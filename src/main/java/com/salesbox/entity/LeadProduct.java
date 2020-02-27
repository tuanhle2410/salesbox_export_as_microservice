package com.salesbox.entity;

import javax.persistence.*;

/**
 * User: luult
 * Date: 7/16/14
 */
@Entity
@Table(name = "lead_product")
public class LeadProduct extends BaseEntity
{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lead_id")
    Lead lead;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    Product product;

    public Lead getLead()
    {
        return lead;
    }

    public void setLead(Lead lead)
    {
        this.lead = lead;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }
}
