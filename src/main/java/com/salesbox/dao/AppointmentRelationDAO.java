package com.salesbox.dao;

import com.salesbox.entity.AppointmentRelation;
import com.salesbox.entity.view.AppointmentRelationWithAppointmentView;

import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 3/18/2017.
 */
public interface AppointmentRelationDAO extends BaseDAO<AppointmentRelation>
{

    AppointmentRelation findByAppointmentIdAndRelationType(UUID appointmentId, String relationType);

    AppointmentRelation findByAppointmentIdAndLeadId(UUID appointmentId, UUID leadId);

    Long countNumberActiveAppointmentByRelationObjectId(UUID relationObjectUuid, String relationType);

    Boolean isExistActiveAppointmentWithRelationObject(UUID relationObjectUuid, String relationType);

    List<AppointmentRelationWithAppointmentView> getActiveAppointmentView(UUID relationObjectUuid, String relationType);

    List<AppointmentRelationWithAppointmentView> findAllByRelationObjectId(UUID relationObjectUuid, String relationType);

    List<UUID> findAllByRelationObjectIds(List<UUID> relationObjectUuid, String relationType);

    List<UUID> findAppointmentIdByRelationObjectId(UUID relationObjectUuid, String relationType);

    List<AppointmentRelation> findAllAppointmentRelationByByRelationObjectId(UUID relationObjectUuid, String relationType);

    List<AppointmentRelationWithAppointmentView> findByAppointmentId(UUID appointmentId, String relationType);

    List<AppointmentRelationWithAppointmentView> findByRealtionObjectIdAndOwnerAndDeletedAndFinished(UUID relationObjectUuid, UUID ownerId, Boolean deleted, Boolean finished);

    List<AppointmentRelationWithAppointmentView> findAllByRelationObjectIdAndDeletedAndFinishedHistory(UUID relationObjectUuid, String relationType, Boolean deleted, Boolean finished);

    List<AppointmentRelationWithAppointmentView> findActiveByRealtionObjectIdAndOwnerAndRelationType(UUID relationObjectUuid, UUID ownerId, String relationType);

    void findListLeadByListAppointmentID(List<UUID> appointmentUUIDList);

    void deleteByListAppointment(List<UUID> listAppointmentUuids);
}
