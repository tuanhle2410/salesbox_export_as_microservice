package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.ProspectBase;
import com.salesbox.entity.Unit;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.SalesMethodActivityType;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/21/14
 * Time: 4:49 PM
 */
public interface ProspectBaseDAO extends BaseDAO<ProspectBase>
{
    ProspectBase findOne(UUID uuid);

    ProspectBase findOne(UUID uuid, Boolean active);


    Map<UUID, Long> getUserAndCountByMarkedAndContractDateBetween(Enterprise enterprise, SalesMethodActivityType type, Date startDate, Date endDate);

    Long countByMarkedByUserContractDateBetween(User user, SalesMethodActivityType type, Date startDate, Date endDate, Boolean isMarked);

    Long countByMarkedByUnitContractDateBetween(Unit unit, SalesMethodActivityType type, Date startDate, Date endDate, Boolean isMarked);

    Long countByMarkedByEnterpriseContractDateBetween(Enterprise enterprise, SalesMethodActivityType type, Date startDate, Date endDate, Boolean isMarked);

    Long getByBestRecordOfUserCompanyAndStartDateBetween(Enterprise enterprise, SalesMethodActivityType type, Date startDate, Date endDate);

    Long getByBestRecordOfUnitCompanyAndStartDateBetween(Enterprise enterprise, SalesMethodActivityType type, Date startDate, Date endDate);

    Map<UUID, Long> getUnitAndCountByMarkedAndContractDateBetween(Enterprise enterprise, SalesMethodActivityType type, Date startDate, Date endDate);

    Long countTotalActiveOppByUserInWithoutMarkedSendQuote(List<User> userList, SalesMethodActivityType salesMethodActivityType, Date startDate, Date endDate);
}
