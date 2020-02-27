package com.salesbox.entity.task;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by admin on 3/19/2017.
 */

@Entity
@Table(name = "v_task_relation_with_task_status")
public class TaskRelationWithTaskStatusView extends BaseEntity
{

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "task_uuid")
    private UUID taskUuid;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "relation_object_uuid")
    private UUID relationObjectUuid;

    @Basic
    @Column(name = "relation_type")
    private String relationType;

    @Column(name = "is_finished")
    private Boolean finished = Boolean.FALSE;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public UUID getTaskUuid() {
        return taskUuid;
    }

    public void setTaskUuid(UUID taskUuid) {
        this.taskUuid = taskUuid;
    }

    public UUID getRelationObjectUuid() {
        return relationObjectUuid;
    }

    public void setRelationObjectUuid(UUID relationObjectUuid) {
        this.relationObjectUuid = relationObjectUuid;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
}
