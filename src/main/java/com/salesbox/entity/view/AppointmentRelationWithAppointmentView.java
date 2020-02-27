package com.salesbox.entity.view;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Created by admin on 3/21/2017.
 */

@Entity
@Table(name = "v_appointment_relation_with_appointment")
public class AppointmentRelationWithAppointmentView extends BaseEntity
{

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "appointment_uuid")
    private UUID appointmentId;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "relation_object_uuid")
    private UUID relationObjectUuid;

    @Basic
    @Column(name = "relation_type")
    private String relationType;

    @Column(name = "appointment_start_date")
    private Date appointmentStartDate;

    @Column(name = "appointment_end_date")
    private Date appointmentEndDate;


    @Column(name = "is_finished")
    private Boolean finished = Boolean.FALSE;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "ignored")
    private Boolean ignored = Boolean.FALSE;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "appointment_owner_id")
    private UUID appointmentOwnerId;

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getIgnored() {
        return ignored;
    }

    public void setIgnored(Boolean ignored) {
        this.ignored = ignored;
    }

    public Date getAppointmentStartDate()
    {
        return appointmentStartDate;
    }

    public void setAppointmentStartDate(Date appointmentStartDate)
    {
        this.appointmentStartDate = appointmentStartDate;
    }

    public Date getAppointmentEndDate()
    {
        return appointmentEndDate;
    }

    public void setAppointmentEndDate(Date appointmentEndDate)
    {
        this.appointmentEndDate = appointmentEndDate;
    }

    public UUID getAppointmentOwnerId()
    {
        return appointmentOwnerId;
    }

    public void setAppointmentOwnerId(UUID appointmentOwnerId)
    {
        this.appointmentOwnerId = appointmentOwnerId;
    }
}
