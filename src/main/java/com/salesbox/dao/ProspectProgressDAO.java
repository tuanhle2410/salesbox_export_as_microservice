package com.salesbox.dao;

import com.salesbox.entity.*;
import com.salesbox.entity.enums.SalesMethodActivityType;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/21/14
 * Time: 9:36 AM
 */
public interface ProspectProgressDAO extends BaseDAO<ProspectProgress>
{
    List<ProspectProgress> findByProspectOrderByActivityIndexAsc(UUID prospectId);

    List<ProspectProgress> findByProspectIdIn(Collection<UUID> prospectIdList);

    List<ProspectProgress> findByProspectId(UUID uuid);

    List<ProspectProgress> findByProspectIdOrderByIndex(UUID uuid);

    List<ProspectProgress> findByProspectIdAndFinish(UUID uuid, Boolean isFinish);

    List<ProspectProgress> findByProspectInAndActivity(Collection<UUID> prospectIdList, Activity activity);

    List<ProspectProgress> findByProspectInAndActivityOrderByProspectUpdatedWonDate(Collection<UUID> prospectIdList, Activity activity);

    ProspectProgress findOne(UUID uuid);

    ProspectProgress findByProspectAndActivity(ProspectBase prospect, Activity activity);

    List<ProspectProgress> findByUserAndFinishedOrWonAndActivityTypeAndIsNotOrderByUpdatedDateAsc(User user, SalesMethodActivityType salesMethodActivityType, UUID uuid);

    ProspectProgress findByProspectIdAndActivityType(UUID prospectId, SalesMethodActivityType salesMethodActivityType);

    List<ProspectProgress> findByFinishedAndUserInAndProspectContractDateBetweenAndProspectActiveAndActivityTypeIn(List<User> userList, Date startDate, Date endDate, List<SalesMethodActivityType> salesMethodActivityTypeList);

    List<ProspectProgress> findByUserInAndProspectWonLostDateBetweenAndProspectWonAndActivityTypeIn(List<User> userList, Date startDate, Date endDate, List<SalesMethodActivityType> salesMethodActivityTypeList);

    void removeWhereProspectIn(List<UUID> prospectIdList);

    List<ProspectProgress>  findByActivityInAndProspectId(List<Activity> activityList, UUID prospectId);

    void removeWhereActivityInAndProspectId(List<Activity> activityList, UUID prospectId);

    List<Object[]> findByProspectIdInOrderByActivityName(Set<UUID> prospectIdSet);

    List<ProspectProgress> findByProspectAndIndexBetweenOrderByActivityIndexAsc(UUID prospectId, Integer sourceIndex, Integer targetIndex);

    ProspectProgress findByProspectIdAndLastFinished(ProspectActive prospectActive);

    long countNumberDoneStepByProspect(ProspectBase prospect);
}
