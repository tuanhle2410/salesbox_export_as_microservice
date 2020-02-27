package com.salesbox.dao.impl;

import com.salesbox.dao.BaseDAO;
import com.salesbox.entity.BaseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * User: luult
 * Date: 6/30/14
 */

@Repository
public class BaseDAOImpl<T> implements BaseDAO<T>
{

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public <S extends T> void save(Iterable<S> entities)
    {
        if (entities == null)
        {
            return;
        }

        for (S entity : entities)
        {
            save(entity);
        }
    }

    @Override
    public void save(T t)
    {
        //for case we want to change updated date whenever save method is called
        // if we do not do like this, if t does not change any fields, updated date will not change

        BaseEntity beforeSaved = (BaseEntity) t;
        beforeSaved.setUpdatedDate(new Date());

        entityManager.persist(beforeSaved);
    }

    @Override
    public void delete(T t)
    {
        entityManager.remove(t);
    }

    @Override
    public <S extends T> void delete(Iterable<S> entities)
    {
        Assert.notNull(entities, "The given Iterable of entities not be null!");

        for (T entity : entities)
        {
            delete(entity);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getResultListFromQuery(Query query)
    {
        List<T> tList = new ArrayList<>();

        for (Object o : query.getResultList())
        {
            tList.add((T) o);
        }

        return tList;
    }

    public List getExtraListFromQuery(Query query)
    {
        List tList = new ArrayList<>();

        for (Object o : query.getResultList())
        {
            tList.add(o);
        }

        return tList;
    }

    @SuppressWarnings("unchecked")
    public <TT> List<TT> getResultListFromQuery(Query query, Class<TT> targetType)
    {
        List<TT> tList = new ArrayList<>();

        for (Object o : query.getResultList())
        {
            tList.add((TT) o);
        }

        return tList;
    }

    @SuppressWarnings("unchecked")
    public <T> T getResultFromQuery(Query query)
    {
        try
        {
            return (T) query.getSingleResult();
        }
        catch (NoResultException ex)
        {
            return null;
        }
    }

    @Override
    public void deleteInBatch(List<T> oldList)
    {
        for (T t : oldList)
        {
            entityManager.remove(t);
        }
        entityManager.flush();
    }

    @Override
    public void saveAndFlush(T t)
    {
        BaseEntity beforeSaved = (BaseEntity) t;
        beforeSaved.setUpdatedDate(new Date());

        entityManager.persist(beforeSaved);
        entityManager.flush();
    }

    @Override
    public void updateAndFlush(T t)
    {
        BaseEntity beforeSaved = (BaseEntity) t;
        beforeSaved.setUpdatedDate(new Date());

        entityManager.merge(beforeSaved);
        entityManager.flush();
    }

    @Override
    public void saveAndFlush(List<T> list)
    {
        for (T t : list)
        {
            saveAndFlush(t);
        }
    }

    @Override
    public <S extends T> void updateAndFlush(Iterable<S> entities, Integer batchSize)
    {
        if (entities == null || batchSize == null || batchSize.equals(0))
        {
            return;
        }

        int i = 0;
        for (S entity : entities)
        {
            entityManager.merge(entity);
            if (++i % batchSize == 0)
            {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    @Override
    public <S extends T> void saveAndFlush(Iterable<S> entities, Integer batchSize)
    {
        if (entities == null || batchSize == null || batchSize.equals(0))
        {
            return;
        }

        int i = 0;
        for (S entity : entities)
        {
        	try {
        		BaseEntity beforeSaved = (BaseEntity) entity;
                beforeSaved.setUpdatedDate(new Date());
                entityManager.persist(entity);
                if (++i % batchSize == 0)
                {
                    entityManager.flush();
                    entityManager.clear();
                }	
        	} catch(Exception e) {
        		continue;
        	}
        	
        }
    }

    @Override
    public List<T> getResultListOrderedByUUIDs(Query query, Collection<UUID> uuids)
    {
        List<T> list = query.getResultList();
        Object[] idA = uuids.toArray();

        Map<UUID, Object> ordered = new HashMap<>();
        for (int i = 0; i < uuids.size(); i++)
        {
            ordered.put((UUID) idA[i], i);
        }

        BaseEntity[] result = new BaseEntity[uuids.size()];
        for (T obj : list)
        {
            BaseEntity baseEntity = (BaseEntity) obj;
            int pos = (int) ordered.get(baseEntity.getUuid());
            result[pos] = baseEntity;
        }

        List<T> orderedResult = new ArrayList<>();
        for (int i = 0; i < uuids.size(); i++)
        {
            if (result[i] != null)
            {
                orderedResult.add((T) result[i]);
            }
        }
        return orderedResult;
    }

    @Override
    public T get(Class<T> clazz, UUID id)
    {
        return this.entityManager.find(clazz, id);
    }
}
