package com.salesbox.common.calculation;

import com.salesbox.constant.UserPropertyConstant;
import com.salesbox.dao.AppointmentUserDAO;
import com.salesbox.dao.UserDAO;
import com.salesbox.dao.UserMetadataDAO;
import com.salesbox.entity.Appointment;
import com.salesbox.entity.AppointmentUser;
import com.salesbox.entity.User;
import com.salesbox.entity.UserMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * User: luult
 * Date: 9/11/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
abstract class AppointmentTemplate
{
    @Autowired
    AppointmentUserDAO appointmentUserDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CompareListUtils compareListUtils;
    @Autowired
    UserMetadataDAO userMetadataDAO;

    public void updateCaseAdd(Appointment appointment, int hasIncreaseNumberAttendMeeting)
    {
        List<User> userList = appointmentUserDAO.findUserByAppointment(appointment);
        Map<User, UserMetadata> userMetadataHashMap = new HashMap<>();
        if (!userList.isEmpty()) {
            userMetadataHashMap = userMetadataDAO.getByUserInAndKey(userList, UserPropertyConstant.NUMBER_ATTEND_MEETING);
        }
        for (User user : userList)
        {
            UserMetadata userMetadata = userMetadataHashMap.get(user);
            userMetadata.setValue(userMetadata.getValue() + hasIncreaseNumberAttendMeeting);
            userMetadataDAO.save(userMetadata);
            addAppointmentWithUser(appointment, user);
        }
//        userDAO.save(userList);
    }

    public void updateCaseDelete(Appointment appointment, int hasIncreaseNumberAttendMeeting)
    {
        List<User> userList = appointmentUserDAO.findUserByAppointment(appointment);
        Map<User, UserMetadata> userMetadataHashMap = new HashMap<>();
        if (!userList.isEmpty()) {
            userMetadataHashMap = userMetadataDAO.getByUserInAndKey(userList, UserPropertyConstant.NUMBER_ATTEND_MEETING);
        }
        for (User user : userList)
        {
            UserMetadata userMetadata = userMetadataHashMap.get(user);
            userMetadata.setValue(userMetadata.getValue() - hasIncreaseNumberAttendMeeting);
            userMetadataDAO.save(userMetadata);
            deleteAppointmentWithUser(appointment.getHours(), user);
        }
    }
    
    public void updateCaseDeleteInBatch(List<Appointment> appointmentList, int hasIncreaseNumberAttendMeeting)
    {
        List<AppointmentUser> appointmentUserList = appointmentUserDAO.findByAppointmentIn(appointmentList);
        Map<Appointment, List<User>> mapAppointment = appointmentUserList.parallelStream()
        		.collect(Collectors.groupingBy(AppointmentUser::getAppointment,
        				Collectors.collectingAndThen(Collectors.toList(), item -> item.stream().map(elem -> elem.getUser()).collect(Collectors.toList()))));
        
        Map<User, UserMetadata> userMetadataHashMap = new HashMap<>();
        
        for (Entry<Appointment, List<User>> element : mapAppointment.entrySet()) {
			Appointment appointment = element.getKey();
			List<User> userList = element.getValue();
			
			if (!userList.isEmpty()) {
	            userMetadataHashMap = userMetadataDAO.getByUserInAndKey(userList, UserPropertyConstant.NUMBER_ATTEND_MEETING);
	        }
			
			for (User user : userList)
	        {
	            UserMetadata userMetadata = userMetadataHashMap.get(user);
	            userMetadata.setValue(userMetadata.getValue() - hasIncreaseNumberAttendMeeting);
	            List<UserMetadata> userMetadataList = userMetadataHashMap.values().stream().collect(Collectors.toList());
	            userMetadataDAO.save(userMetadataList);
	            
	            deleteAppointmentWithUser(appointment.getHours(), user);
	        }
		}
    }

    public void updateCaseUpdate(Appointment appointment, List<User> oldUserList, Double oldTime, int hasIncreaseNumberAttendMeeting)
    {
        List<User> newUserList = appointmentUserDAO.findUserByAppointment(appointment);

        List<User> commonUserList = compareListUtils.findCommonUUIDList(oldUserList, newUserList);
        removeAppointmentForOldUser(oldTime, compareListUtils.findDiff(oldUserList, commonUserList), hasIncreaseNumberAttendMeeting);
        addAppointmentForNewUser(appointment, compareListUtils.findDiff(newUserList, commonUserList), hasIncreaseNumberAttendMeeting);
        updateCommonAppointment(commonUserList, appointment, oldTime);
    }

    private void addAppointmentForNewUser(Appointment appointment, List<User> userList, int hasIncreaseNumberAttendMeeting)
    {
        Map<User, UserMetadata> userMetadataHashMap = new HashMap<>();
        if (!userList.isEmpty()) {
            userMetadataHashMap = userMetadataDAO.getByUserInAndKey(userList, UserPropertyConstant.NUMBER_ATTEND_MEETING);
        }
        for (User user : userList)
        {
            UserMetadata userMetadata = userMetadataHashMap.get(user);
            userMetadata.setValue(userMetadata.getValue() + hasIncreaseNumberAttendMeeting);
            userMetadataDAO.save(userMetadata);
//            user.setNumberAttendMeeting(user.getNumberAttendMeeting() + hasIncreaseNumberAttendMeeting);
            addAppointmentWithUser(appointment, user);
        }
//        userDAO.save(userList);
    }

    private void removeAppointmentForOldUser(Double oldMeetingTime, List<User> userList, int hasIncreaseNumberAttendMeeting)
    {
        Map<User, UserMetadata> userMetadataHashMap = new HashMap<>();
        if (!userList.isEmpty()) {
            userMetadataHashMap = userMetadataDAO.getByUserInAndKey(userList, UserPropertyConstant.NUMBER_ATTEND_MEETING);
        }
        for (User user : userList)
        {
            UserMetadata userMetadata = userMetadataHashMap.get(user);
            userMetadata.setValue(userMetadata.getValue() - hasIncreaseNumberAttendMeeting);
            userMetadataDAO.save(userMetadata);
            deleteAppointmentWithUser(oldMeetingTime, user);
        }
//        userDAO.save(userList);

    }



    protected abstract void updateCommonAppointment(List<User> commonUserList, Appointment appointment, Double oldTime);

    protected abstract void deleteAppointmentWithUser(Double hours, User user);

    abstract void addAppointmentWithUser(Appointment appointment, User user);

}
