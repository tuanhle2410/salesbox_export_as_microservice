package com.salesbox.dao;


import com.salesbox.entity.CustomField;
import com.salesbox.entity.CustomFieldOptionValue;
import com.salesbox.entity.CustomFieldValue;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.enums.CustomFieldType;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: neo
 * Date: 11/1/18
 */
public interface CustomFieldValueDAO extends BaseDAO<CustomFieldValue>
{
    CustomFieldValue findOne(UUID uuid);

    List<CustomFieldValue> findByCustomFieldAndObjectId(CustomField customField, UUID objectId);

    List<CustomFieldValue> findByCustomFieldAndObjectIdIn(List<UUID> objectIdList);

    List<CustomFieldValue> findByCustomFieldInAndObjectIdIn(List<CustomField> customFieldList, List<UUID> objectIdList);

    List<CustomFieldValue> findByCustomField(CustomField customField);

    List<CustomFieldValue> findByFieldType(List<CustomFieldType> listFieldType, int pageIndex);

    List<CustomFieldValue> findByCustomFieldOptionValue(CustomFieldOptionValue customFieldOptionValue);

    List<CustomFieldValue> findByCustomFieldAndFieldOptionAndObjectId(CustomField customField, CustomFieldOptionValue customFieldOptionValue, UUID objectId);

    List<CustomFieldValue> findByEnterpriseAndUpdatedDate(Enterprise enterprise, Date updatedDate, Integer pageIndex, Integer pageSize);

    void removeWhereObjectIdIn(List<UUID> objectIdList);

    void removeWhereObjectIdInAndCustomFieldIn(List<UUID> objectIdList, List<CustomField> customFieldList);

    void deleteByObjectId(UUID uuid);

    void deleteByCustomFieldIdAndObjectIdAndProductIds(UUID customFieldId, UUID objectId, Set<UUID> productIds);

    void deleteByProductIds(Set<UUID> productIds);

    void deleteByProductId(UUID productId);
}
