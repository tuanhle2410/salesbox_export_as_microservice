package com.salesbox.dao;

import com.salesbox.entity.Appointment;
import com.salesbox.entity.AppointmentCustomData;
import com.salesbox.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by hunglv on 7/16/14.
 */
public interface AppointmentCustomDataDAO extends BaseDAO<AppointmentCustomData>
{
    List<AppointmentCustomData> findByUserAndUpdatedDateAfterOrderByUuidAsc(User user,
                                                                            Date updatedDate,
                                                                            Pageable pageable);

    AppointmentCustomData findByAppointmentAndUser(Appointment appointment, User user);

    List<AppointmentCustomData> findByAppointment(Appointment appointment);

    List<AppointmentCustomData> findByUserOrderByLastViewDesc(User userFromToken, Pageable pageable);

    void removeWhereAppointmentIn(List<Appointment> appointmentList);

    void removeWhereUserIn(List<User> userList);

    List<AppointmentCustomData> findByAppointmentIn(List<Appointment> appointmentList);
}
