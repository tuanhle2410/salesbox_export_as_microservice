package com.salesbox.dao;

import com.salesbox.entity.Notification;
import com.salesbox.entity.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hunglv on 7/29/14.
 */
public interface NotificationDAO extends BaseDAO<Notification>
{
    List<Notification> listByUserAndPageIndexAndPageSize(User user, Integer pageIndex, Integer pageSize);

    List<Notification> listAllUnreadByUser(User user);

    List<Notification> findDuplicateNotificationByUserAndInWeek(User user, String content, Date startDate, Date endDate);

    Notification findOne(UUID uuid);

    Long countNumberNotificationUnReadByUser(User user);

    Long countSentNotifications();

    Long countSentNotificationsCreatedDateBetween(Date startDate, Date endDate);

    void removeWhereUserIn(List<User> userList);
}
