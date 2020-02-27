package com.salesbox.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 3/31/2017.
 */
public class PaginationBeanBase
{

    public static final String ORDER_DESC = "desc";
    public static final String ORDER_ASC = "asc";

    private Integer pageSize;
    private Integer pageIndex = 0;
    private Long count;
    private List beans;
    private List<OrderBean> orderBeans = new ArrayList<>();

    public List<OrderBean> getOrderBeans() {
        return orderBeans;
    }

    public void setOrderBeans(List<OrderBean> orderBeans) {
        this.orderBeans = orderBeans;
    }

    public void putOrderBeans(OrderBean... orderBean) {
        this.orderBeans.addAll(Arrays.asList(orderBean));
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List getBeans() {
        return beans;
    }

    public void setBeans(List beans) {
        this.beans = beans;
    }
}
