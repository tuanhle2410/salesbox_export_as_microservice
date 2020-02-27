package com.salesbox.utils;

import com.salesbox.dao.RecentActivityDAO;
import com.salesbox.entity.BaseEntity;
import com.salesbox.entity.RecentActivity;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.entity.enums.RecentActionType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by tuantx on 3/20/2016.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RecentActivityUtils<T extends BaseEntity>
{
    @Autowired
    RecentActivityDAO recentActivityDAO;

    /*
        Add a new (or update) a recent_activity
     */
    public void addRecentActivity(UUID objectId, User user, ObjectType objectType, RecentActionType recentActionType)
    {
        RecentActivity recentActivity = recentActivityDAO.findByObjectIdAndUser(objectId, user);
        if (recentActivity != null)
        {
            recentActivity.setUpdatedDate(new Date());
            recentActivity.setRecentActionType(recentActionType);
        }
        else
        {
            recentActivity = new RecentActivity(user, objectId, objectType, recentActionType);
        }
        recentActivityDAO.save(recentActivity);
    }

    /*
        Add a list of recent_activity with a list of objectId
     */
    public void addRecentActivity(List<T> objectList, User user, ObjectType objectType, RecentActionType recentActionType)
    {
        List<UUID> objectIdList = getUUIDs(objectList);
        if (objectIdList.size() > 0)
        {

            List<RecentActivity> recentActivityList = recentActivityDAO.findByObjectIdInAndUser(objectIdList, user);
            //update old ones
            List<UUID> addedObjectIdList = new ArrayList<>();
            for (RecentActivity recentActivity : recentActivityList)
            {
                recentActivity.setRecentActionType(recentActionType);
                recentActivity.setUpdatedDate(new Date());
                addedObjectIdList.add(recentActivity.getObjectId());
            }
            //create new ones
            List<RecentActivity> newRecentActivityList = new ArrayList<>();
            Collection<UUID> newObjectIdList = CollectionUtils.subtract(objectIdList, addedObjectIdList);
            for (UUID newObjectId : newObjectIdList)
            {
                newRecentActivityList.add(new RecentActivity(user, newObjectId, objectType, recentActionType));
            }
            recentActivityDAO.save(recentActivityList);
            recentActivityDAO.save(newRecentActivityList);
        }
    }

    /*
        Get all distinct uuid from a list of entity
     */
    private List<UUID> getUUIDs(List<T> baseEntityList)
    {

        Set<UUID> result = new HashSet<>();
        for (BaseEntity baseEntity : baseEntityList)
        {
            result.add(baseEntity.getUuid());
        }
        return new ArrayList<UUID>(result);
    }

}
