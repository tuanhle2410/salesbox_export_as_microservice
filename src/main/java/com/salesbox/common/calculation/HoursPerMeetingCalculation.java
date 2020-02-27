package com.salesbox.common.calculation;

import com.salesbox.common.CompareDoubleUtil;
import com.salesbox.common.Constant;
import com.salesbox.constant.UserPropertyConstant;
import com.salesbox.dao.AppointmentDAO;
import com.salesbox.dao.AppointmentUserDAO;
import com.salesbox.dao.UserDAO;
import com.salesbox.dao.WorkDataWorkDataDAO;
import com.salesbox.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: luult
 * Date: 7/30/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HoursPerMeetingCalculation extends AppointmentTemplate
{
    @Autowired
    UserDAO userDAO;
    @Autowired
    AppointmentDAO appointmentDAO;
    @Autowired
    WorkDataWorkDataDAO workDataWorkDataDAO;
    @Autowired
    AppointmentUserDAO appointmentUserDAO;

    @Override
    public void addAppointmentWithUser(Appointment appointment, User user)
    {
        Map<String, UserMetadata> userMetadataMap = userMetadataDAO.findByUserAndKeyIn(user,
                Arrays.asList(UserPropertyConstant.TOTAL_TIME_MEETING, UserPropertyConstant.HOUR_PER_MEETING, UserPropertyConstant.NUMBER_ATTEND_MEETING));

        Double totalTimeMeeting = userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_MEETING).getValue();
        Double hoursPerMeeting = userMetadataMap.get(UserPropertyConstant.HOUR_PER_MEETING).getValue();
        Double numberAttendMeeting = userMetadataMap.get(UserPropertyConstant.NUMBER_ATTEND_MEETING).getValue();

        Double newTotalTimeMeeting = totalTimeMeeting + appointment.getHours();
        userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_MEETING).setValue(newTotalTimeMeeting);
        userMetadataMap.get(UserPropertyConstant.HOUR_PER_MEETING).setValue(AdaptiveCalculation.calculate(
                numberAttendMeeting.intValue(), newTotalTimeMeeting, Constant.MAX_MEETING_AFFECT, hoursPerMeeting));
        userMetadataDAO.save(userMetadataMap.values());
/*
        user.setTotalTimeMeeting(newTotalTimeMeeting);

        user.setHoursPerMeeting(AdaptiveCalculation.calculate(
                user.getNumberAttendMeeting(), newTotalTimeMeeting, Constant.MAX_MEETING_AFFECT, user.getHoursPerMeeting()));
*/
    }

    @Override
    public void updateCommonAppointment(List<User> commonUserList, Appointment appointment, Double oldTime)
    {
        Double newMeetingTime = (appointment.getEndDate().getTime() - appointment.getStartDate().getTime())/1000.0/60/60;

        if (CompareDoubleUtil.equals(oldTime, newMeetingTime))
        {
            return;
        }
        Map<User, UserMetadata> userUserMetadataMap = new HashMap<>();
        if (!commonUserList.isEmpty()) {
            userUserMetadataMap = userMetadataDAO.getByUserInAndKey(commonUserList, UserPropertyConstant.TOTAL_TIME_MEETING);
        }
        for (User user : commonUserList)
        {
            UserMetadata userMetadata = userUserMetadataMap.get(user);
            userMetadata.setValue(userMetadata.getValue() + newMeetingTime - oldTime);
            userMetadataDAO.save(userMetadata);
//            user.setTotalTimeMeeting(userUserMetadataMap.get(user).getValue() + newMeetingTime - oldTime);
            updateWithUser(user);
        }
//        userDAO.save(commonUserList);
    }

    public void updateWithUser(User user)
    {
        Map<String, UserMetadata> userMetadataMap = userMetadataDAO.findByUserAndKeyIn(user,
                Arrays.asList(UserPropertyConstant.NUMBER_ATTEND_MEETING, UserPropertyConstant.TOTAL_TIME_MEETING,UserPropertyConstant.HOUR_PER_MEETING));
        Double numberAttendMeeting = userMetadataMap.get(UserPropertyConstant.NUMBER_ATTEND_MEETING).getValue();
        Double totalTimeMeeting = userMetadataMap.get(UserPropertyConstant.TOTAL_TIME_MEETING).getValue();
        if (numberAttendMeeting > Constant.MAX_MEETING_AFFECT)
        {
            userMetadataMap.get(UserPropertyConstant.HOUR_PER_MEETING).setValue(totalTimeMeeting / numberAttendMeeting);
            userMetadataDAO.save(userMetadataMap.get(UserPropertyConstant.HOUR_PER_MEETING));
//            user.setHoursPerMeeting(totalTimeMeeting / numberAttendMeeting);
        }
        else
        {
            Enterprise enterprise = user.getUnit().getEnterprise();
            List<Appointment> appointmentList = appointmentUserDAO.findAppointmentByUserOrderByCreatedDate(user);
            WorkDataWorkData workDataWorkData = workDataWorkDataDAO.findByEnterpriseAndName(enterprise, Constant.HOURS_PER_MEETING);

            Double hourPerMeeting = new Double(workDataWorkData.getValue());
            int n = 0;
            Double sum = 0d;
            for (Appointment appointment : appointmentList)
            {
                sum += appointment.getHours();
                n++;
                hourPerMeeting = AdaptiveCalculation.calculate(n, sum, Constant.MAX_MEETING_AFFECT, hourPerMeeting);
            }

            userMetadataMap.get(UserPropertyConstant.HOUR_PER_MEETING).setValue(hourPerMeeting);
            userMetadataDAO.save(userMetadataMap.get(UserPropertyConstant.HOUR_PER_MEETING));
//            user.setHoursPerMeeting(hourPerMeeting);
        }

//        userDAO.save(user);
    }

    @Override
    public void deleteAppointmentWithUser(Double oldMeetingTime, User user)
    {
        UserMetadata userMetadata = userMetadataDAO.findByUserAndKey(user, UserPropertyConstant.TOTAL_TIME_MEETING);
        userMetadata.setValue(userMetadata.getValue() - oldMeetingTime);
        userMetadataDAO.save(userMetadata);
//        Double newTotalTimeMeeting = user.getTotalTimeMeeting() - oldMeetingTime;
//        user.setTotalTimeMeeting(newTotalTimeMeeting);

        updateWithUser(user);
    }
}
