package com.salesbox.dao;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/30/14
 */
public interface BaseDAO<T>
{
    void save(T t);

    <S extends T> void save(Iterable<S> entities);

    void delete(T t);

    <S extends T> void delete(Iterable<S> entities);

    void deleteInBatch(List<T> oldList);

    void saveAndFlush(T t);

    void updateAndFlush(T t);

    void saveAndFlush(List<T> list);

    <S extends T> void updateAndFlush(Iterable<S> entities, Integer batchSize);

    <S extends T> void saveAndFlush(Iterable<S> entities, Integer batchSize);

    List<T> getResultListOrderedByUUIDs(Query query, Collection<UUID> uuids);

    T get(Class<T> clazz, UUID id);
}
