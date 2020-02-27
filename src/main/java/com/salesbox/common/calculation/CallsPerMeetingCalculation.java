package com.salesbox.common.calculation;

import com.salesbox.common.Constant;
import com.salesbox.constant.UserPropertyConstant;
import com.salesbox.dao.*;
import com.salesbox.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * User: luult
 * Date: 8/7/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CallsPerMeetingCalculation
{
    @Autowired
    UserDAO userDAO;
    @Autowired
    WorkDataWorkDataDAO workDataWorkDataDAO;
    @Autowired
    AppointmentDAO appointmentDAO;
    @Autowired
    WorkDataActivityDAO workDataActivityDAO;
    @Autowired
    UserMetadataDAO userMetadataDAO;

    public void updateCaseAdd(Appointment appointment)
    {
        User owner = appointment.getOwner();
        update(owner, 1, appointment.getNumberCall());
    }

    private void update(User owner, int updatedNumberMeeting, int updatedNumberCall)
    {
        Map<String, UserMetadata> userMetadataMap = userMetadataDAO.findByUserAndKeyIn(owner, Arrays.asList(
                UserPropertyConstant.NUMBER_MEETING, UserPropertyConstant.NUMBER_CALL_FOR_MEETING,UserPropertyConstant.CALL_PER_MEETING
        ));
        Double numberMeeting = userMetadataMap.get(UserPropertyConstant.NUMBER_MEETING).getValue();
        Double numberCallsForMeeting = userMetadataMap.get(UserPropertyConstant.NUMBER_CALL_FOR_MEETING).getValue();

        Double newNumberMeeting = numberMeeting + updatedNumberMeeting;
        Double newNumberCall = numberCallsForMeeting + updatedNumberMeeting * updatedNumberCall;

        userMetadataMap.get(UserPropertyConstant.NUMBER_MEETING).setValue(newNumberMeeting);
        userMetadataMap.get(UserPropertyConstant.NUMBER_CALL_FOR_MEETING).setValue(newNumberCall);
/*
        owner.setNumberMeeting(newNumberMeeting);
        owner.setNumberCallsForMeeting(newNumberCall);
*/

        if (newNumberMeeting > Constant.MAX_MEETING_AFFECT)
        {
            userMetadataMap.get(UserPropertyConstant.CALL_PER_MEETING).setValue(newNumberCall * 1.0 / newNumberMeeting);
//            owner.setCallsPerMeeting(newNumberCall * 1.0 / newNumberMeeting);
        }
        else
        {
            Enterprise enterprise = owner.getUnit().getEnterprise();
            WorkDataWorkData callsPerMeeting = workDataWorkDataDAO.findByEnterpriseAndName(enterprise, Constant.NUMBER_CALLS_PER_MEETING);
            List<Appointment> appointmentList = appointmentDAO.findByOwnerAndDeletedOrderByDateCreated(owner, false);
            Double result = new Double(callsPerMeeting.getValue());
            Double sum = 0d;
            int n = 0;
            for (Appointment appointment1 : appointmentList)
            {
                n++;
                sum += appointment1.getNumberCall();
                result = AdaptiveCalculation.calculate(n, sum, Constant.MAX_MEETING_AFFECT, result);
            }
            userMetadataMap.get(UserPropertyConstant.CALL_PER_MEETING).setValue(result);
//            owner.setCallsPerMeeting(result);
        }
        userMetadataDAO.save(userMetadataMap.values());
//        userDAO.save(owner);
    }
    
    private void updateInBatch(int updatedNumberMeeting, List<Appointment> appointments)
    {
    	Set<User> ownerList = appointments.parallelStream().map(item -> item.getOwner()).collect(Collectors.toSet());
    	Map<User, Integer> mapUpdatedNumberCall = appointments.parallelStream().collect(Collectors.toMap(Appointment::getOwner, Appointment::getNumberCall, (p1, p2) -> p1));
    	
        Map<User, Map<String, UserMetadata>> userMetadataMap = userMetadataDAO.findByUserInAndKeyIn(ownerList, Arrays.asList(
                UserPropertyConstant.NUMBER_MEETING, UserPropertyConstant.NUMBER_CALL_FOR_MEETING,UserPropertyConstant.CALL_PER_MEETING
        ));
        
        List<Enterprise> enterpriseList = ownerList.parallelStream().map(item -> item.getUnit().getEnterprise()).collect(Collectors.toList());
        
        Map<Enterprise, WorkDataWorkData> mapCallsPerMeeting = workDataWorkDataDAO.findByEnterpriseInAndName(enterpriseList, Constant.NUMBER_CALLS_PER_MEETING);
        Map<User, List<Appointment>> mapAppointmentList = appointmentDAO.findByOwnerInAndDeletedOrderByDateCreated(ownerList.stream().collect(Collectors.toList()), false);
        
        for (Entry<User, Map<String, UserMetadata>> element : userMetadataMap.entrySet()) {
			User owner = element.getKey();
			Map<String, UserMetadata> ownerMetadata = element.getValue();

			Integer updatedNumberCall = mapUpdatedNumberCall.get(owner);
			
			Double numberMeeting = ownerMetadata.get(UserPropertyConstant.NUMBER_MEETING).getValue();
			Double numberCallsForMeeting = ownerMetadata.get(UserPropertyConstant.NUMBER_CALL_FOR_MEETING).getValue();

			Double newNumberMeeting = numberMeeting + updatedNumberMeeting;
			Double newNumberCall = numberCallsForMeeting + updatedNumberMeeting * updatedNumberCall;

			ownerMetadata.get(UserPropertyConstant.NUMBER_MEETING).setValue(newNumberMeeting);
			ownerMetadata.get(UserPropertyConstant.NUMBER_CALL_FOR_MEETING).setValue(newNumberCall);
			
			if (newNumberMeeting > Constant.MAX_MEETING_AFFECT)
	        {
				ownerMetadata.get(UserPropertyConstant.CALL_PER_MEETING).setValue(newNumberCall * 1.0 / newNumberMeeting);
	        }
	        else
	        {
	        	Enterprise enterprise = owner.getUnit().getEnterprise();
	        	WorkDataWorkData callsPerMeeting = mapCallsPerMeeting.get(enterprise);
	            List<Appointment> appointmentList = mapAppointmentList.get(owner);
	            Double result = new Double(callsPerMeeting.getValue());
	            Double sum = 0d;
	            int n = 0;
	            for (Appointment appointment1 : appointmentList)
	            {
	                n++;
	                sum += appointment1.getNumberCall();
	                result = AdaptiveCalculation.calculate(n, sum, Constant.MAX_MEETING_AFFECT, result);
	            }
	            ownerMetadata.get(UserPropertyConstant.CALL_PER_MEETING).setValue(result);
	        }
		}
        
        List<UserMetadata> userMetadataList = userMetadataMap.values().parallelStream().map(item -> item.values()).flatMap(Collection::stream).collect(Collectors.toList());
        
        userMetadataDAO.save(userMetadataList);
    }

    public void updateCaseUpdate(Appointment appointment, UUID newOwnerId)
    {
        User newOwner = userDAO.findOne(newOwnerId);
        User oldOwner = appointment.getOwner();

        if (oldOwner.getUuid().compareTo(newOwner.getUuid()) != 0)
        {
            appointment.setOwner(newOwner);
            appointmentDAO.save(appointment);

            updateCaseDelete(appointment);

            appointment.setOwner(newOwner);
            updateCaseAdd(appointment);
            appointment.setNumberCall(0);
        }
    }

    public void updateCaseDelete(Appointment appointment)
    {
        User owner = appointment.getOwner();
        update(owner, -1, appointment.getNumberCall());
    }
    
    public void updateCaseDeleteInBatch(List<Appointment> appointmentList)
    {
        updateInBatch(-1, appointmentList);
    }
}
