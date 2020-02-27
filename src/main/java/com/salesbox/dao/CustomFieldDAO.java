package com.salesbox.dao;


import com.salesbox.entity.CustomField;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.enums.ObjectType;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: neo
 * Date: 11/1/17
 */
public interface CustomFieldDAO extends BaseDAO<CustomField>
{
    CustomField findOne(UUID uuid);

    List<CustomField>  findByIdIn(List<UUID> uuidList);

    List<CustomField> findByObjectTypeOrderByPositionAsc(Enterprise enterprise, ObjectType objectType);

    CustomField findByObjectTypeAndEnterpriseAndName(Enterprise enterprise, ObjectType objectType, String name);

    List<CustomField> findByObjectTypeInOrderByPositionAsc(Enterprise enterprise, List<ObjectType> objectTypeList);

    List<CustomField> findByUuidInOrderByPositionAsc(Collection<UUID> idList);

    List<CustomField> findByEnterpriseAndUpdatedDate(Enterprise enterprise, Date updatedDate, Integer pageIndex, Integer pageSize);

    List<CustomField> findByObjectTypeAndEnterpriseAndNameIn(Enterprise enterprise, ObjectType account, List<String> names);
}
