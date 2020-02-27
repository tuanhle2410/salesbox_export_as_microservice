package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.WorkDataWorkData;
import com.salesbox.entity.enums.WorkDataType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/27/14
 * Time: 9:50 AM
 */
public interface WorkDataWorkDataDAO extends BaseDAO<WorkDataWorkData>
{
    public WorkDataWorkData findOne(UUID uuid);

    public List<WorkDataWorkData> findByEnterpriseOrderByTypeDesc(Enterprise enterprise);

    public List<WorkDataWorkData> findByEnterpriseAndTypeOrderByTypeDesc(Enterprise enterprise, WorkDataType type);

    public WorkDataWorkData findByEnterpriseAndName(Enterprise enterprise, String name);
    
    public Map<Enterprise, WorkDataWorkData> findByEnterpriseInAndName(List<Enterprise> enterpriseList, String name);

    public WorkDataWorkData findByEnterpriseAndTypeAndName(Enterprise enterprise, WorkDataType type, String name);
    
    List<WorkDataWorkData> findByEnterpriseIdsAndTypeAndName(List<UUID> enterpriseIds, WorkDataType type, String name);
    
    List<Object[]> findByEnterpriseIdInAndTypeAndName(List<UUID> enterpriseIdList, WorkDataType workDataType, String name);

    String findValueByEnterpriseAndName(Enterprise enterprise, String name);

    void removeWhereEnterprise(Enterprise enterprise);
}
