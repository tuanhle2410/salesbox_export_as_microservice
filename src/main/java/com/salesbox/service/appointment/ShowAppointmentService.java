package com.salesbox.service.appointment;

import com.salesbox.appointment.dto.AppointmentDTO;
import com.salesbox.appointment.dto.AppointmentListDTO;
import com.salesbox.common.BaseService;
import com.salesbox.constant.RelationConstant;
import com.salesbox.constant.UserPropertyConstant;
import com.salesbox.dao.*;
import com.salesbox.dto.ActivityDTO;
import com.salesbox.dto.UserDTO;
import com.salesbox.dto.WorkDataActivityDTO;
import com.salesbox.dto.WorkDataOrganisationDTO;
import com.salesbox.entity.*;
import com.salesbox.organisation.dto.OrganisationDTO;
import com.salesbox.prospect.dto.ProspectDTO;
import com.salesbox.utils.ProspectUtils;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by hunglv on 7/16/14.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ShowAppointmentService extends BaseService
{
    @Autowired
    AppointmentRelationDAO appointmentRelationDAO;
    @Autowired
    ContactLoader contactLoader;
    @Autowired
    ContactInviteeLoader contactInviteeLoader;
    @Autowired
    InviteeInviteeLoader inviteeInviteeLoader;
    @Autowired
    MapperFacade mapper;
    @Autowired
    ProspectUserDAO prospectUserDAO;
    @Autowired
    UserMetadataDAO userMetadataDAO;
    @Autowired
    ProspectUtils prospectUtils;

    @Autowired
    LeadDAO leadDAO;

    @Autowired
    ProspectBaseDAO prospectBaseDAO;



    public AppointmentListDTO generateAppointmentDTOListFromAppointmentList(List<Appointment> appointmentList, Enterprise enterprise)
    {
        Date now = new Date();
        AppointmentListDTO appointmentListDTO = new AppointmentListDTO();
        for (Appointment appointment : appointmentList)
        {
            AppointmentDTO appointmentDTO = populateAppointmentDTOFromAppointment(appointment);
            appointmentListDTO.getAppointmentDTOList().add(appointmentDTO);
        }
        contactLoader.load(appointmentList, appointmentListDTO, enterprise);
        contactInviteeLoader.load(appointmentList, appointmentListDTO, enterprise);
        inviteeInviteeLoader.load(appointmentList, appointmentListDTO);

        appointmentListDTO.setCurrentTime(now);
        return appointmentListDTO;
    }

    public AppointmentDTO populateAppointmentDTOFromAppointment(Appointment appointment)
    {
        AppointmentDTO appointmentDTO = mapper.map(appointment, AppointmentDTO.class);

        appointmentDTO.setExternalKeyTempList(createListFromString(appointment.getExternalKeyTempList()));

        if (null != appointment.getFirstContact())
        {
            Contact contact = appointment.getFirstContact();
            appointmentDTO.setFirstContactId(contact.getUuid());
            appointmentDTO.setFirstContactName(contact.getFirstName() + " " + contact.getLastName());
            appointmentDTO.setFirstContactAddress(contact.getFullAddress());
        }

        Organisation organisation = appointment.getOrganisation();

        if (null != organisation)
        {
            OrganisationDTO organisationDTO = mapper.map(organisation, OrganisationDTO.class);
            organisationDTO.setUuid(organisation.getUuid());
            if (null != organisation.getType())
            {
                organisationDTO.setType(new WorkDataOrganisationDTO(organisation.getType()));
            }

            if (null != organisation.getSize())
            {
                organisationDTO.setSize(new WorkDataOrganisationDTO(organisation.getSize()));
            }

            if (null != organisation.getIndustry())
            {
                organisationDTO.setIndustry(new WorkDataOrganisationDTO(organisation.getIndustry()));
            }

            organisationDTO.setFullAddress(organisation.getFullAddress());
            appointmentDTO.setOrganisation(organisationDTO);
        }

        if (null != appointment.getOwner())
        {
            User owner = appointment.getOwner();
            UserDTO ownerDTO = new UserDTO(owner, mapper);
            //meta data
            Map<String, UserMetadata> userMetadataMap = userMetadataDAO.findByUserAndKeyIn(owner, Arrays.asList(
                    UserPropertyConstant.MEDIAN_DEAL_SIZE, UserPropertyConstant.MEDIAN_LEAD_TIME, UserPropertyConstant.MINUTER_PER_PICK, UserPropertyConstant.MINUTER_PER_CALL,
                    UserPropertyConstant.HOUR_PER_MEETING, UserPropertyConstant.CALL_PER_MEETING, UserPropertyConstant.PICK_PER_CALL, UserPropertyConstant.MEETING_PER_DEAL, UserPropertyConstant.TRAVELLING_TIME_PER_MEETING, UserPropertyConstant.HOUR_PER_QUOTE
            ));
            ownerDTO.setMedianDealSize(userMetadataMap.get(UserPropertyConstant.MEDIAN_DEAL_SIZE).getValue());
            ownerDTO.setMedianLeadTime(userMetadataMap.get(UserPropertyConstant.MEDIAN_LEAD_TIME).getValue().longValue());
            ownerDTO.setMinutesPerPick(userMetadataMap.get(UserPropertyConstant.MINUTER_PER_PICK).getValue());
            ownerDTO.setMinutesPerCall(userMetadataMap.get(UserPropertyConstant.MINUTER_PER_CALL).getValue());
            ownerDTO.setHoursPerMeeting(userMetadataMap.get(UserPropertyConstant.HOUR_PER_MEETING).getValue());
            ownerDTO.setCallsPerMeeting(userMetadataMap.get(UserPropertyConstant.CALL_PER_MEETING).getValue());
            ownerDTO.setPicksPerCall(userMetadataMap.get(UserPropertyConstant.PICK_PER_CALL).getValue());
            ownerDTO.setMeetingPerDeal(userMetadataMap.get(UserPropertyConstant.MEETING_PER_DEAL).getValue());
            ownerDTO.setTravellingTimePerMeeting(userMetadataMap.get(UserPropertyConstant.TRAVELLING_TIME_PER_MEETING).getValue());
            ownerDTO.setHoursPerQuote(userMetadataMap.get(UserPropertyConstant.HOUR_PER_QUOTE).getValue());

            SharedContact sharedContact = owner.getSharedContact();
            ownerDTO.setEmail(sharedContact.getEmail());
            ownerDTO.setFirstName(sharedContact.getFirstName());
            ownerDTO.setLastName(sharedContact.getLastName());
            ownerDTO.setPhone(sharedContact.getPhone());
            ownerDTO.setAvatar(owner.getAvatar());
            ownerDTO.setDiscProfile(sharedContact.getDiscProfile().toString());

            appointmentDTO.setOwner(ownerDTO);
            appointmentDTO.setEnterpriseId(owner.getUnit().getEnterprise().getUuid());
        }

        ProspectBase prospect = appointment.getProspectId() == null ? null : prospectBaseDAO.findOne(appointment.getProspectId());

        if (null != prospect)
        {
            appointmentDTO.setProspect(generateProspectDTOFromProspect(prospect));
        }
        else
        {
            AppointmentRelation appointmentRelation = appointmentRelationDAO.findByAppointmentIdAndRelationType(appointmentDTO.getUuid(), RelationConstant.APPOINTMENT_LEAD);
            if (null != appointmentRelation)
            {
                appointmentDTO.setLeadId(appointmentRelation.getRelationObjectUuid());
            }
        }

        if (null != appointment.getFocusActivity())
        {
            appointmentDTO.setFocusActivity(mapper.map(appointment.getFocusActivity(), ActivityDTO.class));
        }

        if (null != appointment.getFocusWorkData())
        {
            appointmentDTO.setFocusWorkData(mapper.map(appointment.getFocusWorkData(), WorkDataActivityDTO.class));
        }

        return appointmentDTO;
    }

    private ProspectDTO generateProspectDTOFromProspect(ProspectBase prospect)
    {
        ProspectDTO prospectDTO = new ProspectDTO();

        prospectDTO.setUuid(prospect.getUuid());
        prospectDTO.setCreatedDate(prospect.getCreatedDate());
        prospectDTO.setUpdatedDate(prospect.getUpdatedDate());
        prospectDTO.setNumberDoneMeeting(prospect.getNumberDoneMeeting());
        prospectDTO.setDoneWorkEffort(prospect.getDoneWorkEffort());
        prospectDTO.setDescription(prospect.getDescription());
        prospectDTO.setContractDate(prospect.getContractDate());
        //metadata
        prospectDTO.setRealProspectProgress(prospectUtils.getRealProspectProgress(prospect));
        prospectDTO.setGrossValue(prospect.getGrossValue());
        prospectDTO.setProfit(prospect.getProfit());
        prospectDTO.setNumberMeetingLeft(prospect.getNumberMeetingLeft());
        prospectDTO.setNumberMeeting(prospect.getNumberMeeting());
        prospectDTO.setNumberTask(prospect.getNumberTask());
        prospectDTO.setNumberActiveMeeting(prospect.getNumberActiveMeeting());
        prospectDTO.setNumberActiveTask(prospect.getNumberActiveTask());
        prospectDTO.setPrioritiseColor(prospect.getPrioritiseColor().toString());
        prospectDTO.setProspectProgress(prospect.getProspectProgress());
        prospectDTO.setNumberActivityLeft(prospect.getNumberActivityLeft());
        if (prospect instanceof ProspectHistoric)
        {
            ProspectHistoric prospectHistoric = (ProspectHistoric) prospect;
            prospectDTO.setWonLostDate(prospectHistoric.getWonLostDate());
            if (prospectHistoric.getWon() != null)
            {
                prospectDTO.setDaysInPipeline(prospect.getDaysInPipeline());
            }
            else
            {
                prospectDTO.setDaysInPipeline(new Date().getTime() - prospect.getCreatedDate().getTime());
            }
        }
        else
        {
            prospectDTO.setDaysInPipeline(new Date().getTime() - prospect.getCreatedDate().getTime());
        }


        if (prospect.getGrossValue().compareTo(0d) > 0)
        {
            prospectDTO.setMargin(prospect.getProfit() / prospect.getGrossValue());
        }
        else
        {
            prospectDTO.setMargin(0d);
        }

        prospectDTO.setNeededWorkEffort(calculateNeededWorkEffort(prospect));

        return prospectDTO;
    }

    private Double calculateNeededWorkEffort(ProspectBase prospect)
    {
        List<User> userList = prospectUserDAO.findUserByProspect(prospect.getUuid());

        Double travellingHoursPerMeeting = 0d;
        Double minutesPerCall = 0d;
        Double minutesPerPick = 0d;
        Double hoursPerMeeting = 0d;
        Double callsPerMeeting = 0d;
        Double picksPerCall = 0d;
        Double hoursPerContract = 0d;
        Double hoursPerQuote = 0d;

        Map<User, Map<String, UserMetadata>> userMapMap = new HashMap<>();
        if (!userList.isEmpty())
        {
            userMapMap = userMetadataDAO.findByUserInAndKeyIn(userList, Arrays.asList(
                    UserPropertyConstant.TRAVELLING_TIME_PER_MEETING, UserPropertyConstant.MINUTER_PER_CALL,
                    UserPropertyConstant.MINUTER_PER_PICK, UserPropertyConstant.HOUR_PER_MEETING, UserPropertyConstant.CALL_PER_MEETING,
                    UserPropertyConstant.PICK_PER_CALL, UserPropertyConstant.HOUR_PER_CONTRACT, UserPropertyConstant.HOUR_PER_QUOTE
            ));
        }
        for (User user : userList)
        {
            Map<String, UserMetadata> userMetadataMap = userMapMap.get(user);

            travellingHoursPerMeeting += userMetadataMap.get(UserPropertyConstant.TRAVELLING_TIME_PER_MEETING).getValue();
            minutesPerCall += userMetadataMap.get(UserPropertyConstant.MINUTER_PER_CALL).getValue();
            minutesPerPick = userMetadataMap.get(UserPropertyConstant.MINUTER_PER_PICK).getValue();
            hoursPerMeeting += userMetadataMap.get(UserPropertyConstant.HOUR_PER_MEETING).getValue();
            callsPerMeeting = userMetadataMap.get(UserPropertyConstant.CALL_PER_MEETING).getValue();
            picksPerCall = userMetadataMap.get(UserPropertyConstant.PICK_PER_CALL).getValue();
            hoursPerContract = userMetadataMap.get(UserPropertyConstant.HOUR_PER_CONTRACT).getValue();
            hoursPerQuote += userMetadataMap.get(UserPropertyConstant.HOUR_PER_QUOTE).getValue();
        }

        int numberParticipants = userList.size();
        travellingHoursPerMeeting = travellingHoursPerMeeting / numberParticipants;
        minutesPerCall = minutesPerCall / numberParticipants;
        minutesPerPick = minutesPerPick / numberParticipants;
        hoursPerMeeting = hoursPerMeeting / numberParticipants;
        callsPerMeeting = callsPerMeeting / numberParticipants;
        picksPerCall = picksPerCall / numberParticipants;
        hoursPerContract = hoursPerContract / numberParticipants;
        hoursPerQuote = hoursPerQuote / numberParticipants;


        Double neededWorkEffort = prospect.getNumberMeetingLeft() * (hoursPerMeeting + travellingHoursPerMeeting + callsPerMeeting * minutesPerCall / 60 + callsPerMeeting * (picksPerCall - 1) * minutesPerPick / 60);
        if (prospect.getContractSent() && !prospect.getCheckedContractSent())
        {
            neededWorkEffort += hoursPerContract;
        }
        if (prospect.getQuoteSent() && !prospect.getCheckedQuoteSent())
        {
            neededWorkEffort += hoursPerQuote;
        }

        return neededWorkEffort;
    }

}
