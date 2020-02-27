package com.salesbox.dao.task;

import com.salesbox.dto.OrderBean;
import com.salesbox.dto.PaginationBeanBase;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 3/20/2017.
 */
public abstract class AbstractQuery
{

    public static final int MAX_PAGE_SIZE = 100;
    //
    protected CriteriaBuilder cBuilder;
    protected CriteriaQuery query;
    protected Root root;
    protected PaginationBeanBase paginationBeanBase;
    protected Class domainClass;
    protected EntityManager entityManager;

    public AbstractQuery()
    {
    }

    public AbstractQuery(Class domainClass, EntityManager entityManager)
    {
        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }

    public AbstractQuery(PaginationBeanBase paginationBeanBase, Class domainClass, EntityManager entityManager)
    {
        this.paginationBeanBase = paginationBeanBase;
        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }

    protected abstract Expression buildExpression(CriteriaBuilder cBuilder, CriteriaQuery query, Root root);

    public Long getCount(Class domainClass, EntityManager entityManager)
    {
        this.domainClass = domainClass;
        this.entityManager = entityManager;

        return this.getCount();
    }

    public Long getCount()
    {

        this.cBuilder = this.entityManager.getCriteriaBuilder();
        this.query = this.cBuilder.createQuery(Long.class);
        this.root = this.query.from(domainClass);

        this.query.select(
                cBuilder.count(this.root)
        ).where(
                this.buildExpression(this.cBuilder, this.query, this.root)
        );

        return (Long) entityManager.createQuery(this.query).getSingleResult();
    }

    public Boolean isExisted(Class domainClass, EntityManager entityManager)
    {
        this.domainClass = domainClass;
        this.entityManager = entityManager;

        return this.isExisted();
    }

    public Boolean isExisted()
    {

        this.cBuilder = entityManager.getCriteriaBuilder();
        this.query = cBuilder.createQuery(domainClass);
        this.root = this.query.from(domainClass);

        this.query.select(
                this.root
        ).where(
                this.buildExpression(this.cBuilder, this.query, this.root)
        );

        List resultList = entityManager.createQuery(this.query).setMaxResults(1).getResultList();

        return resultList != null && !resultList.isEmpty();
    }

    public List getList(Class domainClass, EntityManager entityManager)
    {
        this.domainClass = domainClass;
        this.entityManager = entityManager;

        return this.getList();
    }

    public PaginationBeanBase getList(Class domainClass, EntityManager entityManager, PaginationBeanBase paginationBeanBase)
    {
        this.domainClass = domainClass;
        this.entityManager = entityManager;
        this.paginationBeanBase = paginationBeanBase;

        paginationBeanBase.setBeans(this.getList());
        paginationBeanBase.setCount(this.getCount());

        return paginationBeanBase;
    }

    public List getList()
    {

        this.cBuilder = entityManager.getCriteriaBuilder();
        this.query = cBuilder.createQuery(Long.class);
        this.root = this.query.from(domainClass);

        this.query.select(
                this.root
        ).where(
                this.buildExpression(this.cBuilder, this.query, this.root)
        );

        this.setOrderArgs();

        return this.setPaginationArgs(entityManager.createQuery(this.query)).getResultList();
    }

    public Object uniqueResult(Class domainClass, EntityManager entityManager)
    {
        this.domainClass = domainClass;
        this.entityManager = entityManager;

        return this.uniqueResult();
    }

    public Object uniqueResult()
    {

        this.cBuilder = entityManager.getCriteriaBuilder();
        this.query = cBuilder.createQuery(Long.class);
        this.root = this.query.from(domainClass);

        this.query.select(
                this.root
        ).where(
                this.buildExpression(this.cBuilder, this.query, this.root)
        );

        this.setOrderArgs();

        List resultList = entityManager.createQuery(this.query).setMaxResults(1).getResultList();

        return resultList != null && !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public Predicate and(Predicate... predicates)
    {
        return this.and_(this.filterNotNull(predicates));
    }

    public Predicate or(Predicate... predicates)
    {
        return this.or_(this.filterNotNull(predicates));
    }

    public Predicate in(String propertyName, List objects)
    {
        return this.cBuilder.in(this.root.get(propertyName)).value(objects);
    }

    private List<Predicate> filterNotNull(Predicate[] predicates)
    {

        List<Predicate> predicates_ = new LinkedList<Predicate>();

        for (Predicate predicate : predicates)
        {
            if (predicate != null)
            {
                predicates_.add(predicate);
            }
        }

        return predicates_;
    }

    private Predicate and_(List<Predicate> predicates)
    {

        switch (predicates.size())
        {

            case 0:
                return null;

            case 1:
                return predicates.get(0);

            case 2:
                return this.cBuilder.and(predicates.get(0), predicates.get(1));

            default:
                return this.cBuilder.and(predicates.remove(0), this.and_(predicates));
        }
    }

    private Predicate or_(List<Predicate> predicates)
    {

        switch (predicates.size())
        {

            case 0:
                return null;

            case 1:
                return predicates.get(0);

            case 2:
                return this.cBuilder.or(predicates.get(0), predicates.get(1));

            default:
                return this.cBuilder.or(predicates.remove(0), this.or_(predicates));
        }
    }

    private TypedQuery setPaginationArgs(TypedQuery typedQuery)
    {

        if (this.paginationBeanBase == null)
        {
            return typedQuery;
        }

        Integer pageSize = paginationBeanBase.getPageSize();
        Integer pageIndex = paginationBeanBase.getPageIndex();

        if (pageSize != null && pageSize > 0)
        {

            if (pageSize > MAX_PAGE_SIZE)
            {
                pageSize = MAX_PAGE_SIZE;
            }

            typedQuery.setMaxResults(pageSize);
            typedQuery.setFirstResult(pageIndex * pageSize);
        }

        return typedQuery;
    }

    private void setOrderArgs()
    {

        if (this.paginationBeanBase == null)
        {
            return;
        }

        List<OrderBean> orderBeans = this.paginationBeanBase.getOrderBeans();

        if (orderBeans == null || orderBeans.isEmpty())
        {
            return;
        }

        List<Order> orders = new ArrayList<>();

        for (OrderBean orderBean : orderBeans)
        {
            String orderBy = orderBean.getOrderBy();
            String order = orderBean.getOrder();

            if (orderBy != null && !orderBy.isEmpty())
            {
                Path orderByPath = root.get(orderBy);
                Boolean isOrderDesc = PaginationBeanBase.ORDER_DESC.equals(order);
                orders.add(isOrderDesc ? this.cBuilder.desc(orderByPath) : this.cBuilder.asc(orderByPath));
            }
        }

        this.query.orderBy(orders);
    }
}
