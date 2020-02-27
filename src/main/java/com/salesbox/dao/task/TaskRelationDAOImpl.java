package com.salesbox.dao.task;

import com.salesbox.dao.impl.BaseDAOImpl;
import com.salesbox.entity.TaskRelation;
import com.salesbox.entity.task.TaskRelationWithTaskStatusView;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 3/19/2017.
 */
public class TaskRelationDAOImpl extends BaseDAOImpl<TaskRelation> implements TaskRelationDAO {

    private AbstractQuery byTaskUuid(final UUID taskUuid) {

        return new AbstractQuery() {
            @Override
            protected Expression buildExpression(CriteriaBuilder cBuilder, CriteriaQuery query, Root root) {
                return cBuilder.equal(root.get("taskUuid"), taskUuid);
            }
        };
    }

    private AbstractQuery byRelationTypeAndRelationObjectUuidAndFinish(
            final String relationType, final UUID relationObjectUuid, final Boolean isFinish
    ) {

        return new AbstractQuery() {
            @Override
            protected Expression buildExpression(CriteriaBuilder cBuilder, CriteriaQuery query, Root root) {
                return cBuilder.and(
                        cBuilder.equal(root.get("relationType"), relationType),
                        cBuilder.equal(root.get("relationObjectUuid"), relationObjectUuid),
                        cBuilder.equal(root.get("finished"), isFinish),
                        cBuilder.equal(root.get("deleted"), Boolean.FALSE)
                );
            }
        };
    }

    private AbstractQuery byRelationTypeAndTaskId(
            final String relationType, final UUID taskUuid
    ) {

        return new AbstractQuery() {
            @Override
            protected Expression buildExpression(CriteriaBuilder cBuilder, CriteriaQuery query, Root root) {
                return cBuilder.and(
                        cBuilder.equal(root.get("relationType"), relationType),
                        cBuilder.equal(root.get("taskUuid"), taskUuid)
                );
            }
        };
    }

    private AbstractQuery byRelationTypeAndRelationObjectUuid(
            final String relationType, final UUID relationObjectUuid
    ) {

        return new AbstractQuery() {
            @Override
            protected Expression buildExpression(CriteriaBuilder cBuilder, CriteriaQuery query, Root root) {
                return cBuilder.and(
                        cBuilder.equal(root.get("relationType"), relationType),
                        cBuilder.equal(root.get("relationObjectUuid"), relationObjectUuid)
                );
            }
        };
    }

    public Boolean isExistTask(String relationType, UUID relationObjectUuid, Boolean isFinish) {
        return this.byRelationTypeAndRelationObjectUuidAndFinish(relationType, relationObjectUuid, isFinish)
                .isExisted(TaskRelationWithTaskStatusView.class, this.entityManager);
    }

    public Long getCountOfTasks(String relationType, UUID relationObjectUuid, Boolean isFinish) {

        return this.byRelationTypeAndRelationObjectUuidAndFinish(relationType, relationObjectUuid, isFinish)
                .getCount(TaskRelationWithTaskStatusView.class, this.entityManager);
    }

    @Override
    public TaskRelation getTaskRelation(UUID taskUuid) {
        return (TaskRelation) this.byTaskUuid(taskUuid).uniqueResult(TaskRelation.class, this.entityManager);
    }

    @Override
    public List<TaskRelation> getTaskRelationsOn(String relationType, UUID relationObjectUuid) {
        return this.byRelationTypeAndRelationObjectUuid(relationType, relationObjectUuid).getList(TaskRelation.class, this.entityManager);
    }

    @Override
    public List<UUID> getTaskRelationsOnListLead(String relationType, List<UUID> relationObjectUuids)
    {
        String sql = "select tr.taskUuid from TaskRelation tr where tr.relationObjectUuid in (:relationObjectUuids) and tr.relationType = :relationType";
        Query query = entityManager.createQuery(sql);
        query.setParameter("relationObjectUuids",relationObjectUuids);
        query.setParameter("relationType",relationType);
       return query.getResultList();
    }

    @Override
    public List<UUID> getTaskRelationsOnListLead(String relationType, UUID relationObjectUuid)
    {
        String sql = "select tr.taskUuid from TaskRelation tr where tr.relationObjectUuid =:relationObjectUuid and tr.relationType = :relationType";
        Query query = entityManager.createQuery(sql);
        query.setParameter("relationObjectUuid",relationObjectUuid);
        query.setParameter("relationType",relationType);
        return query.getResultList();
    }

    @Override
    public void deleteTaskRelationByListUuidTask(List<UUID> uuidList)
    {
        String sql = "Delete from TaskRelation tr where tr.taskUuid in (:taskUuidList)";
        Query query = entityManager.createQuery(sql);
        query.setParameter("taskUuidList", uuidList);
        query.executeUpdate();
    }

    @Override
    public UUID findLeadIdByTaskId(UUID uuid)
    {String sql = "select tr.relationObjectUuid from TaskRelation tr where tr.taskUuid = :taskUuid";
        Query query = entityManager.createQuery(sql);
        query.setParameter("taskUuid", uuid);
        List<UUID> uuiDlist = query.getResultList();
        return uuiDlist.size() == 0 ? null : uuiDlist.get(0);
    }

//    @Override
//    public Object[] findLastDateForDeadLineLead(UUID leadId)
//    {
//        String sql = "select l,max(t.endDate) from TaskRelation tr,Lead l,Task t where tr.appointmentUuid = t.uuid and tr.relationObjectUuid =:leadId and t.deleted  = false and t.dateAndTime is not null";
//        Query query = entityManager.createQuery(sql);
//        query.setParameter("leadId", leadId);
//        return (Object[]) query.getSingleResult();
//    }

}
