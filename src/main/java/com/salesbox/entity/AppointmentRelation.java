package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by admin on 3/18/2017.
 */
@Entity
@Table(name = "appointment_relation")
public class AppointmentRelation extends BaseEntity
{

    @Type(type = "pg-uuid")
    @Column(name = "appointment_uuid")
    private UUID appointmentUuid;

    @Type(type = "pg-uuid")
    @Column(name = "relation_object_uuid")
    private UUID relationObjectUuid;
    @Column(name = "relation_type")
    private String relationType;

    public UUID getAppointmentUuid() {
        return appointmentUuid;
    }

    public void setAppointmentUuid(UUID appointmentUuid) {
        this.appointmentUuid = appointmentUuid;
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
