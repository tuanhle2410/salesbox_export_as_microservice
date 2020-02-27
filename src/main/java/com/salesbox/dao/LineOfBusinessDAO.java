package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.LineOfBusiness;

import java.util.List;
import java.util.UUID;

/**
 * Created by quynhtq on 4/27/14.
 */
public interface LineOfBusinessDAO extends BaseDAO<LineOfBusiness>
{
    LineOfBusiness findOne(UUID uuid);
// -------------------------- OTHER METHODS --------------------------

    List<LineOfBusiness> findByEnterpriseAndDeletedOrderByNameAsc(Enterprise enterprise, Boolean deleted);

    LineOfBusiness findFirstByEnterpriseAndDeletedOrderByNameAsc(Enterprise enterprise, Boolean deleted);

    LineOfBusiness findByEnterpriseAndNameAndDeleted(Enterprise enterprise, String name, Boolean deleted);

    LineOfBusiness findByEnterpriseAndNameAndDeletedCaseInsensitive(Enterprise enterprise, String name, Boolean deleted);

    LineOfBusiness findByEnterpriseAndIsInWizard(Enterprise enterprise, Boolean isInWizard);

    List<LineOfBusiness> findByEnterprise(Enterprise enterprise);

    void removeWhereLineOfBusinessIn(List<LineOfBusiness> lineOfBusinessList);

    LineOfBusiness findOneByEnterprise(Enterprise enterprise);
}

