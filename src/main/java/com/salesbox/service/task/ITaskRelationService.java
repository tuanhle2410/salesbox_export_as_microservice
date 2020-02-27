package com.salesbox.service.task;

import com.salesbox.entity.Lead;
import com.salesbox.entity.Task;
import com.salesbox.entity.TaskRelation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 3/20/2017.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public interface ITaskRelationService
{

    public void deleteTasksRelationOn(Lead lead);

    public void deleteTasksRelationOn(UUID taskUuid);

    public void deleteTasksRelationOn(UUID relationObjectUuid, String relationType);

    public Boolean hasActiveTaskOn(Lead lead);

    public Boolean hasActiveTaskOn(UUID relationObjectUuid, String relationType);

    public List<Task> getTasksOn(UUID relationObjectUuid, String relationType, Boolean isFinished);

    public List<Task> getTasksOn(Lead lead, Boolean isFinished);

    public Long getCountOfActivesTasksOn(Lead lead);

    public Long getCountOfActivesTasksOn(UUID relationObjectUuid, String relationType);

    public TaskRelation addNewTaskRelation(UUID taskUuid, String relationType, UUID relationObjectUuid);

    public TaskRelation updateTaskRelation(UUID taskUuid, String relationType, UUID relationObjectUuid);
}
