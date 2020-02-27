package com.salesbox.dao.task;

import com.salesbox.dao.BaseDAO;
import com.salesbox.entity.TaskRelation;

import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 3/19/2017.
 */
public interface TaskRelationDAO extends BaseDAO<TaskRelation>
{

    public Boolean isExistTask(String relationType, UUID relationObjectUuid, Boolean isFinish);

    public Long getCountOfTasks(String relationType, UUID relationObjectUuid, Boolean isFinish);

    public TaskRelation getTaskRelation(UUID taskUuid);

    public List<TaskRelation> getTaskRelationsOn(String relationType, UUID relationObjectUuid);

    public List<UUID> getTaskRelationsOnListLead(String relationType, List<UUID> relationObjectUuids);

    public List<UUID> getTaskRelationsOnListLead(String relationType, UUID relationObjectUuid);

    public void deleteTaskRelationByListUuidTask(List<UUID> uuidList);

    UUID findLeadIdByTaskId(UUID uuid);
//
//    Object[] findLastDateForDeadLineLead(UUID leadId);
}
