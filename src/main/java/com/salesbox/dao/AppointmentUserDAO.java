package com.salesbox.dao;

/**
 * User: luult
 * Date: 8/28/14
 */

import com.salesbox.entity.*;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface AppointmentUserDAO extends BaseDAO<AppointmentUser>
{
    AppointmentUser findOne(UUID uuid);

    List<User> findUserByAppointment(Appointment appointment);

    List<Appointment> findAppointmentByUserOrderByCreatedDate(User user);

    Set<Appointment> findAppointmentByUser(User user, Date updatedDate, Pageable pageable);

    Set<Appointment> findAppointmentByUnit(Unit unit, Date updatedDate, Pageable pageable);

    Set<Appointment> findAppointmentByEnterprise(Enterprise enterprise, Date updatedDate, Pageable pageable);

    List<AppointmentUser> findByAppointment(Appointment appointment);

    Set<Appointment> findAllActiveAppointmentByUser(User user, Date now);

    Set<UUID> findByUserUnitEnterpriseAndUpdatedDate(Enterprise enterprise, Date updatedDate);

    List<AppointmentUser> findAppointmentByUserInAndFinishedAndEndDateBetWeen(List<User> userList, Date startDate, Date endDate);

    Map<UUID, Long> findNumberAppointmentByUserInAndFinishedAndEndDateBetWeen(List<User> userList, Date startDate, Date endDate);

    List<AppointmentUser> findAppointmentByUnitInAndFinishedAndEndDateBetWeen(List<Unit> unitList, Date startDate, Date endDate);

    Set<UUID> findByUserAndUpdatedDate(User user, Date updatedDate);

    Set<UUID> findByUserUnitAndUpdatedDate(Unit unit, Date updatedDate);

    void removeWhereAppointmentIn(List<Appointment> appointmentList);

    void removeWhereUserIn(List<User> userList);

    List<AppointmentUser> findByAppointmentIn(List<Appointment> appointmentList);

    void updateDeletedWhereAppointmentAndUserNotIn(Appointment appointment, List<User> userList);

    void updateDeletedWhereAppointment(Appointment appointment);

    void removeWhereAppointmentAndUserIn(Appointment appointment, List<User> userList);

    List<AppointmentUser> findByAppointmentAndUser(Appointment appointment, User user);

    List<AppointmentUser> findByUserAndAppointmentIn(User user, List<Appointment> appointmentList);

    List<AppointmentUser> findByUserInAndDeleted(List<User> userList, boolean deleted);

    List<AppointmentUser> findByUser(User user);

    void moveUser(User oldUser, User newUser);
}
