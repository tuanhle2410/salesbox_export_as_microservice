package com.salesbox.dao;

import com.salesbox.dto.MigrateAccessItDataDTO;
import com.salesbox.entity.OrderRow;
import com.salesbox.entity.Product;

import java.util.*;

/**
 * User: luult
 * Date: 5/21/14
 */
public interface OrderRowDAO extends BaseDAO<OrderRow>
{
    public OrderRow findOne(UUID uuid);

    public List<OrderRow> findByProspect(UUID prospectId, Integer pageIndex, Integer pageSize);

    public List<OrderRow> findByProspectId(UUID prospectId);

    public List<OrderRow> findByProspectInWithin(Collection<UUID> prospectList, Date startDate, Date endDate);

    public List<OrderRow> findByProduct(Product product);

    Long countByProspectId(UUID prospectId);

    List<Object[]> countByProspectIdIn(Set<UUID> prospectIdList);

    void removeWhereProspectIn(List<UUID> prospectIdList);

    List<OrderRow> findByProspectIdIn(List<UUID> prospectIdList);

    List<Object[]> listByContactInAndFilter(String contactId, Boolean won, Date startDate, Date endDate, Integer pageIndex, Integer pageSize);

    Long countlistByContactInAndFilter(String contactId, Boolean won, Date startDate, Date endDate);

    List<Object[]> listByAccountInAndFilter(String accountId, Boolean won, Date startDate, Date endDate, Integer pageIndex, Integer pageSize);

    Long countByAccountInAndFilter(String accountId, Boolean won, Date startDate, Date endDate);

    List<OrderRow> listByContact(String contactId, String token, Integer pageIndex, Integer pageSize);

    List<OrderRow> findByTaskIdIn(List<UUID> prospectIdList);

    List<MigrateAccessItDataDTO> findMigrateAccessItData();
}
