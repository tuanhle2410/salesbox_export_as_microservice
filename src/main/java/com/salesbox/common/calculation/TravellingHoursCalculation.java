package com.salesbox.common.calculation;

import com.salesbox.common.CompareDoubleUtil;
import com.salesbox.common.Constant;
import com.salesbox.constant.UserPropertyConstant;
import com.salesbox.dao.AppointmentUserDAO;
import com.salesbox.dao.UserDAO;
import com.salesbox.dao.UserMetadataDAO;
import com.salesbox.dao.WorkDataWorkDataDAO;
import com.salesbox.entity.Appointment;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.User;
import com.salesbox.entity.UserMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User: luult
 * Date: 8/27/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TravellingHoursCalculation extends AppointmentTemplate
{
    @Autowired
    UserDAO userDAO;
    @Autowired
    AppointmentUserDAO appointmentUserDAO;
    @Autowired
    WorkDataWorkDataDAO workDataWorkDataDAO;
    @Autowired
    UserMetadataDAO userMetadataDAO;

    @Override
    public void addAppointmentWithUser(Appointment appointment, User user)
    {
        Double time = appointment.getTravellingInHours();
        if (time < 0) {
            time = userMetadataDAO.findByUserAndKey(appointment.getOwner(), UserPropertyConstant.TRAVELLING_TIME_PER_MEETING).getValue();
        }
        Map<String, UserMetadata> userMetadataMap = userMetadataDAO.findByUserAndKeyIn(user, Arrays.asList(
                UserPropertyConstant.NUMBER_ATTEND_MEETING,UserPropertyConstant.TRAVELLING_TIME_PER_MEETING,UserPropertyConstant.TOTAL_TIME_TRAVELLING
        ));

        Double newNumberAttendMeeting = userMetadataMap.get(UserPropertyConstant.NUMBER_ATTEND_MEETING).getValue();
        Double travellingTimePerMeeting = userMetadataMap.get(UserPropertyConstant.TRAVELLING_TIME_PER_MEETING).getValue();

        Double totalTimeTravelling = userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_TRAVELLING).getValue() + time;
        userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_TRAVELLING).setValue(totalTimeTravelling);

//        user.setTotalTimeTravelling(totalTimeTravelling);
        userMetadataMap.get(UserPropertyConstant.TRAVELLING_TIME_PER_MEETING).setValue(AdaptiveCalculation.calculate(
                newNumberAttendMeeting.intValue(), totalTimeTravelling,
                Constant.MAX_MEETING_AFFECT, travellingTimePerMeeting));
        userMetadataDAO.save(userMetadataMap.values());
/*
        user.setTravellingTimePerMeeting(
                AdaptiveCalculation.calculate(
                        newNumberAttendMeeting.intValue(), totalTimeTravelling,
                        Constant.MAX_MEETING_AFFECT, travellingTimePerMeeting));
*/
    }

    @Override
    public void deleteAppointmentWithUser(Double oldTravellingTime, User user)
    {
        Map<String, UserMetadata> userMetadataMap = userMetadataDAO.findByUserAndKeyIn(user, Arrays.asList(
                UserPropertyConstant.NUMBER_ATTEND_MEETING,UserPropertyConstant.TRAVELLING_TIME_PER_MEETING,UserPropertyConstant.TOTAL_TIME_TRAVELLING
        ));

        Double newNumberAttendMeeting = userMetadataMap.get(UserPropertyConstant.NUMBER_ATTEND_MEETING).getValue();
        Double travellingTimePerMeeting = userMetadataMap.get(UserPropertyConstant.TRAVELLING_TIME_PER_MEETING).getValue();
        Double currentTotalTimeTravelling = userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_TRAVELLING).getValue();

        if (oldTravellingTime == null)
        {
            oldTravellingTime = travellingTimePerMeeting;
        }
        Double totalTimeTravelling = currentTotalTimeTravelling - oldTravellingTime;
        if (totalTimeTravelling < Constant.e)
        {
            totalTimeTravelling = 0d;
        }
        userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_TRAVELLING).setValue(totalTimeTravelling);
        userMetadataDAO.save(userMetadataMap.values());
//        user.setTotalTimeTravelling(totalTimeTravelling);

        if (newNumberAttendMeeting > Constant.MAX_MEETING_AFFECT)
        {
            userMetadataMap.get(UserPropertyConstant.TRAVELLING_TIME_PER_MEETING).setValue(totalTimeTravelling / newNumberAttendMeeting);
            userMetadataDAO.save(userMetadataMap.values());
//            user.setTravellingTimePerMeeting(totalTimeTravelling / newNumberAttendMeeting);
        }
        else
        {
            calculateCaseLessThanMax(user);
        }
    }

    private void calculateCaseLessThanMax(User user)
    {
        Enterprise enterprise = user.getUnit().getEnterprise();

        List<Appointment> appointmentList = appointmentUserDAO.findAppointmentByUserOrderByCreatedDate(user);
        Double hoursPerMeeting = new Double(workDataWorkDataDAO.findByEnterpriseAndName(enterprise, Constant.TRAVELLING_HOURS_PER_MEETING).getValue());
        int n = 0;
        Double total = 0d;
        for (Appointment appointment : appointmentList)
        {
            n++;
            Double time = appointment.getTravellingInHours();

            if (time == null)
            {
                time = hoursPerMeeting;
            }
            total += time;

            hoursPerMeeting = AdaptiveCalculation.calculate(n, total, Constant.MAX_MEETING_AFFECT, hoursPerMeeting);
        }

        Map<String, UserMetadata> userMetadataMap = userMetadataDAO.findByUserAndKeyIn(user, Arrays.asList(
                UserPropertyConstant.TRAVELLING_TIME_PER_MEETING,UserPropertyConstant.TOTAL_TIME_TRAVELLING
        ));
        userMetadataMap.get(UserPropertyConstant.TRAVELLING_TIME_PER_MEETING).setValue(hoursPerMeeting);
        userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_TRAVELLING).setValue(total);
        userMetadataDAO.save(userMetadataMap.values());
/*
        user.setTravellingTimePerMeeting(hoursPerMeeting);
        user.setTotalTimeTravelling(total);
*/
    }

    @Override
    public void updateCommonAppointment(List<User> commonUserList, Appointment appointment, Double oldTravellingTime)
    {
        Double newTravellingHours = appointment.getHours();

        if (CompareDoubleUtil.equals(oldTravellingTime, newTravellingHours))
        {
            return;
        }
        for (User user : commonUserList)
        {
            Map<String, UserMetadata> userMetadataMap = userMetadataDAO.findByUserAndKeyIn(user, Arrays.asList(
                    UserPropertyConstant.TRAVELLING_TIME_PER_MEETING,UserPropertyConstant.NUMBER_ATTEND_MEETING,UserPropertyConstant.TOTAL_TIME_TRAVELLING
            ));
            Double travellingTimePerMeeting = userMetadataMap.get(UserPropertyConstant.TRAVELLING_TIME_PER_MEETING).getValue();
            Double numberAttendMeeting = userMetadataMap.get(UserPropertyConstant.NUMBER_ATTEND_MEETING).getValue();
            Double totalTimeTravelling = userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_TRAVELLING).getValue();

            Double newHours = newTravellingHours;
            if (newHours == null)
            {
                newHours = travellingTimePerMeeting;
//                newHours = user.getTravellingTimePerMeeting();
            }
            Double oldHours = oldTravellingTime;
            if (oldHours == null)
            {
                oldHours = travellingTimePerMeeting;
//                oldHours = user.getTravellingTimePerMeeting();
            }
            userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_TRAVELLING).setValue(totalTimeTravelling + newHours - oldHours);
//            user.setTotalTimeTravelling(user.getTotalTimeTravelling() + newHours - oldHours);
            if (numberAttendMeeting > Constant.MAX_MEETING_AFFECT)
            {
                userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_TRAVELLING).setValue(totalTimeTravelling / numberAttendMeeting);
//                user.setTravellingTimePerMeeting(user.getTotalTimeTravelling() / user.getNumberAttendMeeting());
            }
            else
            {
                calculateCaseLessThanMax(user);
            }
            userMetadataDAO.save(userMetadataMap.values());
        }
//        userDAO.save(commonUserList);
    }
}
