package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.Unit;
import com.salesbox.entity.enums.UnitType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/15/14
 * Time: 6:36 PM
 */
public interface UnitDAO extends BaseDAO<Unit>
{
    Unit findOne(UUID uuid);

    public List<Unit> findByUuidIn(List<UUID> uuidList);

    public List<Unit> findByEnterpriseAndActiveOrderByNameAsc(Enterprise enterprise, boolean active);

    public List<Unit> findByEnterpriseAndActive(Enterprise enterprise);

    public List<Unit> findByEnterpriseAndType(Enterprise enterprise, UnitType type);

    @Query("select unit from Unit unit where unit.enterprise=:enterprise and upper(unit.name)=upper(:name)")
    public Unit findByEnterpriseAndNameToUppercase(@Param("enterprise") Enterprise enterprise, @Param("name") String name);

    void removeWhereEnterprise(Enterprise enterprise);

    List<Unit> findByEnterprise(Enterprise enterprise);


    Unit findByEnterpriseMaestranoGroupId(String groupId);
}
