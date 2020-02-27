package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.Storage;
import com.salesbox.entity.enums.StorageType;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/15/14
 * Time: 2:57 PM
 */
public interface StorageDAO extends BaseDAO<Storage>
{
    Storage findOne(UUID uuid);

    List<Storage> findByEnterprise(Enterprise enterprise);

    Storage findByEnterpriseAndType(Enterprise enterprise, StorageType type);

    void removeWhereEnterprise(Enterprise enterprise);

    List<Storage> findListByEnterpriseAndType(Enterprise enterprise, StorageType type);

    List<Storage> findListByEnterpriseAndTypeIn(Enterprise enterprise, List<StorageType> typeList);
}
