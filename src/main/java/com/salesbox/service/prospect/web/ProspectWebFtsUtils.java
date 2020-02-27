package com.salesbox.service.prospect.web;

import com.salesbox.dao.ProspectActiveDAO;
import com.salesbox.dao.ProspectProgressDAO;
import com.salesbox.dto.LineOfBusinessDTO;
import com.salesbox.dto.ProspectResultSet;
import com.salesbox.dto.SalesMethodDTO;
import com.salesbox.entity.ProspectProgress;
import com.salesbox.entity.enums.DiscProfileType;
import com.salesbox.organisation.dto.OrganisationDTO;
import com.salesbox.prospect.dto.*;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProspectWebFtsUtils
{
    @Autowired
    ProspectProgressDAO prospectProgressDAO;
    @Autowired
    MapperFacade mapper;

    @Autowired
    ProspectActiveDAO prospectActiveDAO;

    String getRoleFilter(ProspectFilterDTO prospectFilterDTO)
    {
        StringBuilder roleFilter = new StringBuilder("");
        if (prospectFilterDTO.getRoleFilterType() != null)
        {
            switch (prospectFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter.append(" AND pu.user_id = '").append(prospectFilterDTO.getRoleFilterValue()).append("' "); // += "AND pu.user_id = '" + prospectFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter.append(" AND u.unit_id = '").append(prospectFilterDTO.getRoleFilterValue()).append("' "); //+= "AND u.unit_id = '" + prospectFilterDTO.getRoleFilterValue() + "' ";
                    break;
            }
        }
        return roleFilter.toString();
    }

    String getRoleFilterRecentAndFavourite(ProspectFilterDTO prospectFilterDTO)
    {
        String roleFilter = "";
        if (prospectFilterDTO.getRoleFilterType() != null)
        {
            switch (prospectFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += "AND pcd.user_id = '" + prospectFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += "AND u.unit_id = '" + prospectFilterDTO.getRoleFilterValue() + "' ";
                    break;
            }
        }
        return roleFilter;
    }

    String getOrderByActive(ProspectFilterDTO prospectFilterDTO)
    {
        String orderByStatement = "";

        switch (prospectFilterDTO.getOrderBy())
        {
            case "owner":
                orderByStatement = "ORDER BY visit_more DESC, prospect_owner_name, p.contract_date desc, p.description";
                break;
            case "contractDate":
                orderByStatement = "ORDER BY visit_more DESC, p.contract_date ASC, p.prospect_progress DESC, o_name ASC";
                break;
            case "fewestActivitiesLeft":
                orderByStatement = "ORDER by visit_more DESC, p.number_activity_left asc, p.description desc";
                break;
            case "fewestAppointmentLeft":
                orderByStatement = "ORDER BY visit_more DESC, p.number_meeting_left asc, p.description desc";
                break;
            case "opportunityProgress":
                orderByStatement = "ORDER BY visit_more DESC, p.prospect_progress desc, p.description";
                break;
            case "grossValue":
                orderByStatement = "ORDER BY visit_more DESC, p.gross_value DESC,  p.description ASC";
                break;
            case "netValue":
                orderByStatement = "ORDER BY visit_more DESC, net_value desc, p.description";
                break;
            case "profit":
                orderByStatement = "ORDER BY visit_more DESC, p.profit desc, p.description";
                break;
            case "accountContactName":
                orderByStatement = "ORDER BY visit_more DESC, o_name asc  NULLS LAST, p.contract_date desc, power_sponsor_name asc, p.description";
                break;
            default:
                orderByStatement = "ORDER BY visit_more DESC, p.contract_date desc, p.description";

        }

        return orderByStatement;
    }

    String getOrderByHistoric(ProspectFilterDTO prospectFilterDTO)
    {
        String orderByStatement = "";

        switch (prospectFilterDTO.getOrderBy())
        {
            case "owner":
                orderByStatement = "ORDER BY visit_more DESC, prospect_owner_name desc, p.won_lost_date desc, p.description asc";
                break;
            case "contractDate":
                orderByStatement = "ORDER BY visit_more DESC, p.won_lost_date DESC, p.description asc";
                break;
            case "grossValue":
                orderByStatement = "ORDER BY visit_more DESC, p.gross_value desc,  p.description asc";
                break;
            case "profit":
                orderByStatement = "ORDER BY visit_more DESC, p.profit desc, p.description asc";
                break;
            case "accountContactName":
                orderByStatement = "ORDER BY visit_more DESC, o_name asc NULLS LAST, p.contract_date DESC, power_sponsor_name asc, p.description asc";
                break;
            case "wonLost":
                orderByStatement = "ORDER BY visit_more DESC, p.won DESC, p.won_lost_date asc, o_name asc";
                break;
            default:
                orderByStatement = "ORDER BY visit_more DESC, p.contract_date DESC, p.description asc";

        }

        return orderByStatement;
    }

    public String getSalesProcessStatement(ProspectFilterDTO prospectFilterDTO)
    {
        StringBuilder salesProcessSQL = new StringBuilder();
        if (!prospectFilterDTO.getSalesProcessIds().isEmpty())
        {
            salesProcessSQL.append(" AND p.sales_method_id IN (");
            int index = 0;
            for (UUID uuid : prospectFilterDTO.getSalesProcessIds())
            {
                salesProcessSQL.append("'").append(uuid.toString()).append("'");
                index++;
                if (index < prospectFilterDTO.getSalesProcessIds().size())
                {
                    salesProcessSQL.append(",");
                }
            }
            salesProcessSQL.append(") ");
        }

        return salesProcessSQL.toString();
    }

    public String getQueryTsAndLikeActive(ProspectFilterDTO prospectFilterDTO)
    {
        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(prospectFilterDTO.getFtsTerms().trim()))
        {
            String[] terms = prospectFilterDTO.getFtsTerms().replaceAll("[()]+", "").split(" ");
            for (String term : terms)
            {
                queryTsAndLike.append(" AND ( " +
                        " (lower(unaccent(c.first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(c.last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (regexp_replace(regexp_replace(c.phone, '[-+..]', ' ','g'), '\\s', '', 'g') like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(regexp_replace(regexp_replace(c.email, '[@]', ' ','g'), '\\s', '', 'g'))) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.owner_first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.owner_last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(o.name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(o.vat_number)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.description)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.serial_number)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.description_first_next_step)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.first_next_step)) like lower(unaccent('%" + term + "%'))) " +
                        " ) ");
            }
        }
        return queryTsAndLike.toString();
    }


    String getQueryTsAndLikeHistoric(ProspectFilterDTO prospectFilterDTO)
    {
        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(prospectFilterDTO.getFtsTerms().trim()))
        {
            String[] terms = prospectFilterDTO.getFtsTerms().replaceAll("[()]+", "").split(" ");
            for (String term : terms)
            {
                queryTsAndLike.append(" AND ( " +
                        " (lower(unaccent(p.contact_first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.contact_last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (regexp_replace(regexp_replace(p.contact_phone, '[-+..]', ' ','g'), '\\s', '', 'g') like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(regexp_replace(regexp_replace(p.contact_email, '[@]', ' ','g'), '\\s', '', 'g'))) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.owner_first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.owner_last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.organisation_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.description)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.serial_number)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.description_first_next_step)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(p.first_next_step)) like lower(unaccent('%" + term + "%'))) " +
                        " ) ");
            }
        }
        return queryTsAndLike.toString();
    }

    boolean isExisted(Object[] objects, ProspectListDTO prospectListDTO)
    {
        for (ProspectDTO prospectDTO : prospectListDTO.getProspectDTOList())
        {
            if (prospectDTO.getUuid().equals(UUID.fromString((String) objects[0])))
            {
                return true;
            }
        }
        return false;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor)
    {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    ProspectListDTO getProspectDTOList(List<ProspectResultSet> listProspect, String orderbyPartition, boolean isActive)
    {
        ProspectListDTO prospectListDTO = new ProspectListDTO();
        List<ProspectDTO> prospectDTOList = new ArrayList<>();
        if (!listProspect.isEmpty())
        {
            prospectDTOList = listProspect.stream().map(this::convertProspectResultToDto).collect(Collectors.toList());
        }

        if (!prospectDTOList.isEmpty())
        {
            prospectDTOList = prospectDTOList.stream().filter(distinctByKey(ProspectDTO::getUuid)).collect(Collectors.toList());
        }

//        switch (orderbyPartition)
//        {
//            case "owner":
//                Collections.sort(prospectDTOList, new ProspectDTOOwnerComparator());
//                break;
//            case "contractDate":
//                if (isActive)
//                {
//                    Collections.sort(prospectDTOList, new ProspectDTOContractDateComparator());
//                }
//                else
//                {
//                    Collections.sort(prospectDTOList, new ProspectDTOWonLostDateComparator());
//                }
//                break;
//            case "fewestActivitiesLeft":
//                Collections.sort(prospectDTOList, new ProspectDTOFewestActivityComparator());
//                break;
//            case "fewestAppointmentLeft":
//                Collections.sort(prospectDTOList, new ProspectDTOFewestAppointmentComparator());
//                break;
//            case "opportunityProgress":
//                Collections.sort(prospectDTOList, new ProspectDTOOpportunityProgressComparator());
//                break;
//            case "grossValue":
//                Collections.sort(prospectDTOList, new ProspectDTOGrossValueComparator());
//                break;
//            case "netValue":
//                Collections.sort(prospectDTOList, new ProspectDTONetValueComparator());
//                break;
//            case "profit":
//                Collections.sort(prospectDTOList, new ProspectDTOProfitComparator());
//                break;
//            case "accountContactName":
//                if (isActive)
//                {
//                    Collections.sort(prospectDTOList, new ProspectDTOAccountContactNameActiveComparator());
//                }
//                else
//                {
//                    Collections.sort(prospectDTOList, new ProspectDTOAccountContactNameHistoricComparator());
//                }
//
//                break;
//            case "wonLost":
//                Collections.sort(prospectDTOList, new ProspectDTOWonComparator());
//                break;
//            default:
//                Collections.sort(prospectDTOList, new ProspectDTOContractDateComparator());
//
//        }
        checkPrioritizedTask(prospectDTOList);
        prospectListDTO.setProspectDTOList(prospectDTOList);
        return prospectListDTO;
    }

    public void checkPrioritizedTask(List<ProspectDTO> prospectDTOList)
    {
        List<UUID> uuids = new ArrayList<>();
        for (ProspectDTO opp : prospectDTOList)
        {
            uuids.add(opp.getUuid());
        }
        if (uuids.size() > 0)
        {
            List<UUID> listProspectUUID = prospectActiveDAO.findProspectHavingPrioritizedTask(uuids);

            for (ProspectDTO opp : prospectDTOList)
            {
                if (listProspectUUID.contains(opp.getUuid()))
                {
                    opp.setHavePrioritiesTask(true);
                }
            }
        }

    }

    String getOrderByPipeLinePartion(ProspectFilterDTO prospectFilterDTO)
    {
        String orderbyPartition = "";
        switch (prospectFilterDTO.getOrderBy())
        {
            case "owner":
                orderbyPartition = "owner_first_name";
                break;
            case "contractDate":
                orderbyPartition = "contract_date";
                break;
            case "fewestActivitiesLeft":
                orderbyPartition = "number_activity_left";
                break;
            case "fewestAppointmentLeft":
                orderbyPartition = "number_meeting_left";
                break;
            case "opportunityProgress":
                orderbyPartition = "prospect_progress";
                break;
            case "grossValue":
                orderbyPartition = "gross_value";
                break;
            case "netValue":
                orderbyPartition = "\t\tCASE\n" +
                        "  \t\tWHEN (p.number_active_task = 0 AND p.number_active_meeting = 0) \n" +
                        "    \tTHEN ((((p.prospect_progress * ((100 - sm.lose_meeting_ratio) / 100.0))) * p.gross_value) / 100)\n" +
                        "  \t\tELSE ((p.prospect_progress * p.gross_value) / 100) END";
                break;
            case "profit":
                orderbyPartition = "profit";
                break;
            default:
                orderbyPartition = "contract_date";

        }
        return orderbyPartition;
    }


    String getOrderByPipeLineActive(ProspectFilterDTO prospectFilterDTO)
    {
        String orderbyPartition = "";
        switch (prospectFilterDTO.getOrderBy())
        {
            case "owner":
                orderbyPartition = "p.visit_more DESC, owner_name desc, p.contract_date desc, p.description";
                break;
            case "contractDate":
                orderbyPartition = "p.visit_more DESC, p.contract_date ASC, p.prospect_progress DESC, o.name asc ";
                break;
            case "fewestActivitiesLeft":
                orderbyPartition = "p.visit_more DESC, p.number_activity_left asc, p.description desc";
                break;
            case "fewestAppointmentLeft":
                orderbyPartition = "p.visit_more DESC, p.number_meeting_left asc, p.description desc";
                break;
            case "opportunityProgress":
                orderbyPartition = "p.visit_more DESC, p.prospect_progress desc, p.description";
                break;
            case "grossValue":
                orderbyPartition = "p.visit_more DESC, p.gross_value DESC,  p.description ASC";
                break;
            case "netValue":
                orderbyPartition = "p.visit_more DESC, net_value desc, p.description";
                break;
            case "profit":
                orderbyPartition = "p.visit_more DESC, p.profit desc, p.description";
                break;
            case "accountContactName":
                orderbyPartition = "p.visit_more DESC, o.name asc  NULLS LAST, p.contract_date desc, power_sponsor_name asc, p.description";
                break;
            default:
                orderbyPartition = "p.visit_more DESC, p.contract_date desc, p.description";

        }
        return orderbyPartition;
    }

    public Map<UUID, List<ProspectProgressDTO>> getProspectIdProgressMap(Set<UUID> uuidList)
    {
        Map<UUID, List<ProspectProgressDTO>> prospectProgressDTOMap = new HashMap<>();
        if (!uuidList.isEmpty())
        {
            //select prospect_id, prospect_progress, activity_name, activity_percentage
            List<Object[]> prospectProgressProperties = prospectProgressDAO.findByProspectIdInOrderByActivityName(uuidList);
            for (Object[] progressObjects : prospectProgressProperties)
            {
                UUID prospectId = (UUID) progressObjects[0];
                ProspectProgress prospectProgress = (ProspectProgress) progressObjects[1];
                String activityName = (String) progressObjects[2];
                Integer activityPercentage = (Integer) progressObjects[3];
                UUID activityId = (UUID) progressObjects[5];
                List<ProspectProgressDTO> prospectProgressDTOList = prospectProgressDTOMap.get(prospectId);
                if (prospectProgressDTOList == null)
                {
                    prospectProgressDTOList = new ArrayList<>();
                }
                ProspectProgressDTO prospectProgressDTO = mapper.map(prospectProgress, ProspectProgressDTO.class);
                prospectProgressDTO.setName(activityName);
                prospectProgressDTO.setProgress(activityPercentage);
                prospectProgressDTO.setActivityId(activityId);
                if (prospectProgress.getDiscProfile() != null)
                {
                    prospectProgressDTO.setDiscProfile(prospectProgress.getDiscProfile().toString());
                }
                prospectProgressDTOList.add(prospectProgressDTO);


                prospectProgressDTOMap.put(prospectId, prospectProgressDTOList);
            }
        }
        return prospectProgressDTOMap;
    }


    ProspectDTO populateProspectActiveDTO(Object[] objects)
    {
        int index = 0;

        ProspectDTO prospectDTO = new ProspectDTO();
        prospectDTO.setDeleted(false);
        prospectDTO.setUuid(UUID.fromString((String) objects[index++]));
        prospectDTO.setOwnerId(UUID.fromString((String) objects[index++]));

        prospectDTO.setSalesMethod(new SalesMethodDTO(UUID.fromString((String) objects[index++]), (String) objects[index++]));
        if (null != objects[index])
        {
            prospectDTO.setLineOfBusiness(new LineOfBusinessDTO(UUID.fromString((String) objects[index++]), (String) objects[index++]));
        }
        else
        {
            index = index + 2;
        }

        prospectDTO.setOrganisation(new OrganisationDTO());
        if (null != objects[index])
        {
            prospectDTO.getOrganisation().setUuid(UUID.fromString((String) objects[index++]));
            prospectDTO.getOrganisation().setName((String) objects[index++]);
        }
        else
        {
            index = index + 2;
        }

        prospectDTO.setDescription((String) objects[index++]);
        prospectDTO.setContractDate((Date) objects[index++]);

        if (null != objects[index])
        {
            prospectDTO.setDaysInPipeline(((BigInteger) objects[index++]).longValue());
        }
        else
        {
            index++;
        }

        prospectDTO.setFirstNextStep((String) objects[index++]);
        prospectDTO.setDescriptionFirstNextStep((String) objects[index++]);

        if (objects[index] != null)
        {
            prospectDTO.setDiscProfileFirstNextStep(DiscProfileType.getDiscProfile((Integer) objects[index++]).toString());
        }
        else
        {
            index++;
        }

        prospectDTO.setSecondNextStep((String) objects[index++]);
        prospectDTO.setDescriptionSecondNextStep((String) objects[index++]);
        if (objects[index] != null)
        {
            prospectDTO.setDiscProfileSecondNextStep(DiscProfileType.getDiscProfile((Integer) objects[index++]).toString());
        }
        else
        {
            index++;
        }

        prospectDTO.setGrossValue((Double) objects[index++]);
        prospectDTO.setProfit((Double) objects[index++]);
        if (objects[index] != null)
        {
            prospectDTO.setProspectProgress(((Integer) objects[index++]));
        }
        else
        {
            index++;
        }
        prospectDTO.setRealProspectProgress(((BigDecimal) objects[index++]).doubleValue());
        prospectDTO.setNetValue((Double) objects[index++]);


        prospectDTO.setNumberActivityLeft((Integer) objects[index++]);
        prospectDTO.setNumberMeetingLeft((Double) objects[index++]);
        prospectDTO.setNumberActiveTask(((BigInteger) objects[index++]).longValue());
        prospectDTO.setNumberActiveMeeting(((BigInteger) objects[index++]).longValue());


        prospectDTO.setFavorite((Boolean) objects[index++]);
        prospectDTO.setCreatedDate((Date) objects[index++]);
        prospectDTO.setNumberParticipant(((BigInteger) objects[index++]).longValue());
        prospectDTO.setOwnerAvatar((String) objects[index++]);
        if (objects[index] != null)
        {
            prospectDTO.setOwnerDiscProfile(DiscProfileType.getDiscProfile((Integer) objects[index++]).toString());
        }
        else
        {
            index++;
        }

        prospectDTO.setLastSyncTime((Date) objects[index++]);
        prospectDTO.setLeadBoxerId((String) objects[index++]);
        prospectDTO.setVisitMore((Boolean) objects[index++]);
        index = index + 2;
        if (objects[index] != null || objects[index + 1] != null)
        {
            prospectDTO.setSponsorList(new ArrayList<SponsorDTO>());
            SponsorDTO sponsorDTO = new SponsorDTO();
            sponsorDTO.setFirstName((String) objects[index]);
            sponsorDTO.setLastName((String) objects[index + 1]);
            if (objects[index + 2] != null)
            {
                sponsorDTO.setUuid(UUID.fromString((String) objects[index + 2]));
            }
            prospectDTO.getSponsorList().add(sponsorDTO);
        }
        return prospectDTO;
    }

    public ProspectDTO convertProspectResultToDto(ProspectResultSet obj)
    {
        ProspectDTO prospectDTO = new ProspectDTO();
        prospectDTO.setDeleted(false);
        prospectDTO.setUuid(obj.getUuid());
        prospectDTO.setOwnerId(obj.getOwner_id());
        prospectDTO.setOwnerName(obj.getProspect_owner_name());
        prospectDTO.setNumberParticipant(obj.getNumber_team_member().longValue());

        prospectDTO.setSalesMethod(new SalesMethodDTO(obj.getSales_method_id(), obj.getSales_method_name()));
        prospectDTO.setLineOfBusiness(new LineOfBusinessDTO(obj.getLine_of_business_id(), obj.getLob_name()));

        if (obj.getOrganisation_id() != null)
        {
            OrganisationDTO organisationDTO = new OrganisationDTO();
            organisationDTO.setUuid(obj.getOrganisation_id());
            organisationDTO.setName(obj.getO_name());
            organisationDTO.setEmail(obj.getOrganisation_email());
            prospectDTO.setOrganisation(organisationDTO);
        }

        prospectDTO.setDescription(obj.getDescription());
        prospectDTO.setContractDate(obj.getContract_date());

        if (obj.getDays_in_pipeline() != null)
        {
            prospectDTO.setDaysInPipeline(Long.valueOf(obj.getDays_in_pipeline().toString()));
        }

        prospectDTO.setFirstNextStep(obj.getFirst_next_step());
        prospectDTO.setDescriptionFirstNextStep(obj.getDescription_first_next_step());

        if (obj.getDisc_profile_first_next_step() != null)
        {
            prospectDTO.setDiscProfileFirstNextStep(DiscProfileType.getDiscProfile(obj.getDisc_profile_first_next_step()).toString());
        }


        prospectDTO.setSecondNextStep(obj.getSecond_next_step());
        prospectDTO.setDescriptionSecondNextStep(obj.getDescription_second_next_step());
        if (obj.getDisc_profile_second_next_step() != null)
        {
            prospectDTO.setDiscProfileSecondNextStep(DiscProfileType.getDiscProfile(obj.getDisc_profile_second_next_step()).toString());
        }


        prospectDTO.setGrossValue(obj.getGross_value());
        prospectDTO.setProfit(obj.getProfit());
        if (obj.getProspect_progress() != null)
        {
            prospectDTO.setProspectProgress(obj.getProspect_progress());
        }


        prospectDTO.setNumberActivityLeft(obj.getNumber_activity_left());
        prospectDTO.setNumberMeetingLeft(obj.getNumber_meeting_left());
        prospectDTO.setNumberActiveTask(obj.getNumber_active_task() == null ? 0L : obj.getNumber_active_task().longValue());
        prospectDTO.setNumberActiveMeeting(obj.getNumber_active_meeting() == null ? 0L : obj.getNumber_active_meeting().longValue());


        prospectDTO.setFavorite(obj.getIs_favorite());
        prospectDTO.setCreatedDate(obj.getCreated_date());
        prospectDTO.setOwnerAvatar(obj.getOwner_avatar());
        if (obj.getOwner_disc_profile() != null)
        {
            prospectDTO.setOwnerDiscProfile(DiscProfileType.getDiscProfile(obj.getOwner_disc_profile()).toString());
        }
        prospectDTO.setLastSyncTime(obj.getLas_sync_time());
        if (obj.getLeadboxer_id() != null)
        {
            prospectDTO.setLeadBoxerId(obj.getLeadboxer_id());
        }
        prospectDTO.setVisitMore(obj.getVisit_more());
        prospectDTO.setLastSyncTime(obj.getLast_sync_time());

        if (obj.getPower_sponsor_id() != null)
        {
            SponsorDTO sponsorDTO = new SponsorDTO();
            sponsorDTO.setFirstName(obj.getFirst_name());
            sponsorDTO.setLastName(obj.getLast_name());
            sponsorDTO.setUuid(obj.getPower_sponsor_id());
            sponsorDTO.setEmail(obj.getContact_email());
            prospectDTO.getSponsorList().add(sponsorDTO);
        }

        prospectDTO.setRealProspectProgress(getRealProgress(obj));
        prospectDTO.setNetValue(getNetValue(obj));
        prospectDTO.setSerialNumber(obj.getSerial_number());
        prospectDTO.setWon(obj.getWon());
        prospectDTO.setWonLostDate(obj.getWon_lost_date());
        prospectDTO.setCurrentStepId(obj.getCurrent_step_id());

        return prospectDTO;
    }


    private Double getRealProgress(ProspectResultSet obj)
    {
        if (obj.getNumber_active_meeting().intValue() == 0 && obj.getNumber_active_task().intValue() == 0)
        {
            return (double) (obj.getProspect_progress() * (100 - obj.getLose_meeting_ratio()) / 100);
        }
        return Double.valueOf(obj.getProspect_progress());
    }

    private Double getNetValue(ProspectResultSet obj)
    {
        if (obj.getNumber_active_meeting().intValue() == 0 && obj.getNumber_active_task().intValue() == 0)
        {
            return obj.getProspect_progress() * ((100 - obj.getLose_meeting_ratio()) / 100) * obj.getGross_value() / 100;
        }
        return obj.getProspect_progress() * obj.getGross_value() / 100;
    }


    public String getOrderByPipeLineHistoric(ProspectFilterDTO prospectFilterDTO)
    {
        String orderByStatement = "";

        switch (prospectFilterDTO.getOrderBy())
        {
            case "owner":
                orderByStatement = " visit_more DESC, owner_name desc, p.won_lost_date desc, p.description asc";
                break;
            case "contractDate":
                orderByStatement = " visit_more DESC, p.won_lost_date desc, p.description asc";
                break;
            case "grossValue":
                orderByStatement = " visit_more DESC, p.gross_value desc,  p.description asc";
                break;
            case "profit":
                orderByStatement = " visit_more DESC, p.profit desc, p.description asc";
                break;
            case "accountContactName":
                orderByStatement = " visit_more DESC, organisation_name asc NULLS LAST, p.contract_date DESC, power_sponsor_name asc, p.description asc";
                break;
            default:
                orderByStatement = " visit_more DESC, p.contract_date asc, p.description asc";

        }
        return orderByStatement;
    }
}
