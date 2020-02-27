package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.WorkDataActivity;
import com.salesbox.entity.enums.ActivityType;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/26/14
 * Time: 4:22 PM
 */
public interface WorkDataActivityDAO extends BaseDAO<WorkDataActivity>
{
    WorkDataActivity findOne(UUID uuid);

    public List<WorkDataActivity> findByEnterpriseOrderByTypeDesc(Enterprise enterprise);

    public List<WorkDataActivity> findByEnterpriseAndType(Enterprise enterprise, ActivityType type);

    public List<WorkDataActivity> findByEnterpriseAndTypeOrderByTypeDesc(Enterprise enterprise, ActivityType type);

    public WorkDataActivity findByEnterpriseAndNameToUppercase(Enterprise enterprise, String name);

    public List<WorkDataActivity> findByEnterpriseAndNameIn(Enterprise enterprise, List<String> name);

    void removeWhereEnterprise(Enterprise enterprise);

    public WorkDataActivity findByEnterpriseAndKeyCodeAndType(Enterprise enterprise, String keyCode, ActivityType type);
}
