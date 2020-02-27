package com.salesbox.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quynhtq on 4/27/14.
 */
@Entity
@Table(name = "line_of_business")
public class LineOfBusiness extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_method_id")
    private SalesMethod salesMethod;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "is_in_wizard")
    private Boolean isInWizard = Boolean.FALSE;

    @Column(name = "visma_id")
    private String vismaId;

    @OneToMany(mappedBy = "lineOfBusiness", fetch = FetchType.EAGER)
    private List<Product> productList = new ArrayList<>();

// --------------------------- CONSTRUCTORS ---------------------------

    public LineOfBusiness()
    {
    }

// --------------------- GETTER / SETTER METHODS ---------------------


    public Boolean getIsInWizard()
    {
        return isInWizard;
    }

    public void setIsInWizard(Boolean isInWizard)
    {
        this.isInWizard = isInWizard;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public SalesMethod getSalesMethod()
    {
        return salesMethod;
    }

    public void setSalesMethod(SalesMethod salesMethod)
    {
        this.salesMethod = salesMethod;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Product> getProductList()
    {
        return productList;
    }

    public void setProductList(List<Product> productList)
    {
        this.productList = productList;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public Boolean getInWizard()
    {
        return isInWizard;
    }

    public void setInWizard(Boolean inWizard)
    {
        isInWizard = inWizard;
    }

    public String getVismaId()
    {
        return vismaId;
    }

    public void setVismaId(String vismaId)
    {
        this.vismaId = vismaId;
    }
}
