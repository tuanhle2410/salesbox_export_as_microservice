package com.salesbox.dao;

import com.salesbox.entity.ProspectCustomData;
import com.salesbox.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 6/6/14
 */
public interface ProspectCustomDataDAO extends BaseDAO<ProspectCustomData>
{
    ProspectCustomData findByProspectIdAndUser(UUID prospectId, User user);

    List<ProspectCustomData> findByUserAndUpdatedDateAfterOrderByUuidAsc(User user, Date updatedDate, Pageable pageable);

    ProspectCustomData findOne(UUID uuid);

    void removeWhereUserIn(List<User> userList);

    void removeWhereProspectIn(List<UUID> prospectIdList);

}
