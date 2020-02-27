package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by admin on 3/18/2017.
 */

@Entity
@Table(name = "task_relation")
public class TaskRelation extends BaseEntity
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

}
