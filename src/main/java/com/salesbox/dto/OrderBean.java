package com.salesbox.dto;

/**
 * Created by admin on 4/1/2017.
 */
public class OrderBean
{
    private String orderBy;
    private String order = "asc"; // desc;

    public OrderBean()
    {
    }

    public OrderBean(String orderBy, String order)
    {
        this.orderBy = orderBy;
        this.order = order;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }
}
