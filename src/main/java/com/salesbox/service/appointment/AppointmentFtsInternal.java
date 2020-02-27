package com.salesbox.appointment.service;

import com.salesbox.advancesearch.service.ConvertSearchFieldService;
import com.salesbox.appointment.dto.AppointmentFilterDTO;
import com.salesbox.appointment.dto.AppointmentListDTO;
import com.salesbox.common.BaseService;
import com.salesbox.constant.RelationConstant;
import com.salesbox.dao.AppointmentCustomDataDAO;
import com.salesbox.dao.AppointmentDAO;
import com.salesbox.dao.SessionDAO;
import com.salesbox.dto.CountDTO;
import com.salesbox.entity.Appointment;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.exception.ServiceException;
import com.salesbox.service.appointment.ShowAppointmentService;
import com.salesbox.utils.CustomFieldUtils;
import com.salesbox.utils.QueryBuilderUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stringtemplate.v4.ST;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AppointmentFtsInternal extends BaseService
{
    @Autowired
    SessionDAO sessionDAO;
    @Autowired
    AppointmentDAO appointmentDAO;
    @Autowired
    AppointmentCustomDataDAO appointmentCustomDataDAO;
    @Autowired
    ShowAppointmentService showAppointmentService;
    @Autowired
    ConvertSearchFieldService convertSearchFieldService;
    @PersistenceContext
    private EntityManager entityManager;

    private String cachedScriptQuery;
    private String cachedScriptQueryNoPagination;
    private String cachedCountQuery;


    @Value("${host.timezone}")
    private String timeZone;

    private String buildAppointmentRelationLeftJoin(AppointmentFilterDTO appointmentFilterDTO)
    {
        if (appointmentFilterDTO.getLeadId() == null)
        {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder(" LEFT JOIN appointment_relation app_rl on app.uuid = app_rl.appointment_uuid ");
        return stringBuilder.toString();
    }

    private String buildLeadFilter(AppointmentFilterDTO appointmentFilterDTO)
    {
        if (appointmentFilterDTO.getLeadId() == null)
        {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(" AND app_rl.relation_object_uuid");
        stringBuilder.append(" = \'" + appointmentFilterDTO.getLeadId() + "\' AND app_rl.relation_type");
        stringBuilder.append(" = \'" + RelationConstant.APPOINTMENT_LEAD + "\' ");
        return stringBuilder.toString();
    }

    private String buildAppointmentDateRangeFilter(AppointmentFilterDTO appointmentFilterDTO)
    {
        if (appointmentFilterDTO.getStartDate() == null || appointmentFilterDTO.getEndDate() == null)
        {
            return "";
        }
        if (appointmentFilterDTO.getIsFilterAll())
        {
            return  "";
        }

        StringBuilder stringBuilder = new StringBuilder(" AND app.start_date BETWEEN ");
        stringBuilder.append("\'" + new DateTime(appointmentFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "\'");
        stringBuilder.append(" AND " + "\'" + new DateTime(appointmentFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "\'");
        return stringBuilder.toString();
    }

    public AppointmentListDTO findAppointmentFts(String token,
                                                 int pageIndex,
                                                 int pageSize,
                                                 String sessionKey,
                                                 AppointmentFilterDTO appointmentFilterDTO)
            throws IOException, IllegalAccessException, ServiceException
    {
        User user = sessionDAO.findUserByToken(token);
        Enterprise enterprise = user.getUnit().getEnterprise();
        List<Appointment> sortedResults = getAppointments(token, pageIndex, pageSize, sessionKey, appointmentFilterDTO);

        if (sortedResults.isEmpty())
        {
            AppointmentListDTO appointmentListDTO = new AppointmentListDTO();
            appointmentListDTO.setCurrentTime(new Date());
            appointmentListDTO.setSessionKey(sessionKey);
            return appointmentListDTO;
        }

        AppointmentListDTO appointmentListDTO = showAppointmentService.generateAppointmentDTOListFromAppointmentList(sortedResults, enterprise);
        appointmentListDTO.setCurrentTime(new Date());
        appointmentListDTO.setSessionKey(sessionKey);

        return appointmentListDTO;
    }

    public List<Appointment> getAppointments(String token, int pageIndex, int pageSize, String sessionKey, AppointmentFilterDTO appointmentFilterDTO)
            throws IOException, IllegalAccessException, ServiceException{
        User user = sessionDAO.findUserByToken(token);
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        String roleFilter = "";
        if (appointmentFilterDTO.getRoleFilterType() != null)
        {
            switch (appointmentFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += " AND ua.uuid = '" + appointmentFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += " AND ua.unit_id = '" + appointmentFilterDTO.getRoleFilterValue() + "' ";
                    break;
            }
        }
        String customFilter = "";
        String orderByStatement = "";

        if (appointmentFilterDTO.getIsShowHistory())
        {
            customFilter = " AND app.end_date < now()";
        }
        else
        {
            customFilter = " AND app.end_date > now()";
        }

        if (appointmentFilterDTO.getCustomFilter() != null && "noContact".equals(appointmentFilterDTO.getCustomFilter()))
        {
            customFilter += " AND app.first_contact_id IS NULL";
        }

        String advanceSearch = "";
        if (appointmentFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            Enterprise enterprise = getEnterpriseFromToken(token);
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.APPOINTMENT.toString(), appointmentFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }

        switch (appointmentFilterDTO.getOrderBy())
        {
            case "dateAndTime":
                //Different sorting on History and Active. In both sort on closest date to current day first
                if (appointmentFilterDTO.getIsShowHistory())
                {
                    orderByStatement = " order by app.start_date desc";
                }
                else
                {
                    orderByStatement = " order by app.start_date asc";
                }
                break;
            case "focus":
                orderByStatement = " order by focus_name asc";
                break;
            case "appointmentToClosure":
                orderByStatement = " order by app.meeting_order asc, lower(app.title) asc";
                break;
            case "account":
                orderByStatement = " order by lower(o.name) asc";
                break;
            case "contact":
                orderByStatement = " order by first_contact_name asc";
                break;
            case "owner":
                orderByStatement = " order by owner_user_name asc";
                break;
            case "closest":       //travel time - do nothing (we don't support this right now)
                orderByStatement = " order by app.start_date";
                break;
            case "haveOpportunity":
                orderByStatement = " order by have_opportunity desc NULLS LAST, lower(concat(c.first_name, ' ', c.last_name)) asc";
                break;
            default:
                orderByStatement = " order by app.start_date";
        }

        List<Appointment> sortedResults = findAppointmentListByCustomQuery(roleFilter, customFilter, advanceSearch,
                orderByStatement, appointmentFilterDTO, user, pageable);

        return sortedResults;
    }

    public List<Appointment> findAppointmentFts_NoPagination(String token, AppointmentFilterDTO appointmentFilterDTO)
            throws IOException, IllegalAccessException, ServiceException
    {
        User user = sessionDAO.findUserByToken(token);
        String roleFilter = "";
        if (appointmentFilterDTO.getRoleFilterType() != null)
        {
            switch (appointmentFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += " AND ua.uuid = '" + appointmentFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += " AND ua.unit_id = '" + appointmentFilterDTO.getRoleFilterValue() + "' ";
                    break;
            }
        }
        String customFilter = "";

        if (appointmentFilterDTO.getIsShowHistory())
        {
            customFilter = " AND app.end_date < now()";
        }
        else
        {
            customFilter = " AND app.end_date > now()";
        }

        if (appointmentFilterDTO.getCustomFilter() != null && "noContact".equals(appointmentFilterDTO.getCustomFilter()))
        {
            customFilter += " AND app.first_contact_id IS NULL";
        }

        String advanceSearch = "";
        if (appointmentFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            Enterprise enterprise = getEnterpriseFromToken(token);
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.APPOINTMENT.toString(), appointmentFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }

        List<Appointment> sortedResults = findAppointmentListByCustomQuery_NoPagination(roleFilter, customFilter, advanceSearch,
                appointmentFilterDTO, user);

        return sortedResults;
    }

    private List<Appointment> findAppointmentListByCustomQuery(String roleFilter,
                                                               String customFilter,
                                                               String advanceSearch,
                                                               String orderByStatement,
                                                               AppointmentFilterDTO appointmentFilterDTO,
                                                               User user,
                                                               Pageable pageable) throws IOException
    {
        String appointmentRelationLeftJoin = this.buildAppointmentRelationLeftJoin(appointmentFilterDTO);
        String leadFilter = this.buildLeadFilter(appointmentFilterDTO);
        String dateRangeAppointmentFilter = this.buildAppointmentDateRangeFilter(appointmentFilterDTO);

        if (StringUtils.isEmpty(cachedScriptQuery))
        {
            Resource resource = new ClassPathResource("query_appointment_search_index.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
            br.close();
            cachedScriptQuery = stringBuilder.toString();
        }

        String[] terms = appointmentFilterDTO.getSearchText().toLowerCase().replaceAll("[()]+", "").split(" ");

        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(appointmentFilterDTO.getSearchText().trim()))
        {
            Map<String, Boolean> fieldLowerMap = new HashMap<>();
            fieldLowerMap.put("app.title", true);
            fieldLowerMap.put("app.note", true);
            fieldLowerMap.put("app.location", true);
            fieldLowerMap.put("c.first_name", true);
            fieldLowerMap.put("c.last_name", true);
            fieldLowerMap.put("o.name", true);
            fieldLowerMap.put("o.vat_number", true);
            fieldLowerMap.put("fa.name", true);
            fieldLowerMap.put("fwd.name", true);
            fieldLowerMap.put("sc.first_name", true);
            fieldLowerMap.put("sc.last_name", true);
            fieldLowerMap.put("cfv.value", true);
            for (String term : terms)
            {
                queryTsAndLike.append(QueryBuilderUtils.buildLikeQuery(term, fieldLowerMap));
            }
        }

        ST st = new ST(cachedScriptQuery);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("enterprise_id", user.getUnit().getEnterprise().getUuid().toString());
        st.add("role_filter", roleFilter);
        st.add("custom_filter", customFilter);
        st.add("advance_search", advanceSearch);
//        st.add("start_date", new DateTime(appointmentFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(appointmentFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());
        st.add("order_by_statement", orderByStatement);
        st.add("limit_and_offset", " LIMIT " + pageable.getPageSize() + " OFFSET " + (pageable.getPageNumber() * pageable.getPageSize()));

        st.add("appointment_relation_left_joint", appointmentRelationLeftJoin);
        st.add("lead_filter", leadFilter);
        st.add("date_appointment_range_filter", dateRangeAppointmentFilter);

        Query query = entityManager.createNativeQuery(st.render());
        List<Object[]> uuids = query.getResultList();
        if (uuids.size() == 0)
        {
            return new ArrayList<>();
        }
        List<UUID> uuidList = new ArrayList<>();
        for (Object[] uuid : uuids)
        {
            uuidList.add(UUID.fromString(uuid[0].toString()));
        }

        return appointmentDAO.findByIdIn(uuidList);
    }

    private List<Appointment> findAppointmentListByCustomQuery_NoPagination(String roleFilter, String customFilter, String advanceSearch,
                                                                            AppointmentFilterDTO appointmentFilterDTO, User user ) throws IOException
    {
        String appointmentRelationLeftJoin = this.buildAppointmentRelationLeftJoin(appointmentFilterDTO);
        String leadFilter = this.buildLeadFilter(appointmentFilterDTO);
        String dateRangeAppointmentFilter = this.buildAppointmentDateRangeFilter(appointmentFilterDTO);

        if (StringUtils.isEmpty(cachedScriptQueryNoPagination))
        {
            Resource resource = new ClassPathResource("query_appointment_search_index_nopagination.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
            br.close();
            cachedScriptQueryNoPagination = stringBuilder.toString();
        }

        String[] terms = appointmentFilterDTO.getSearchText().toLowerCase().replaceAll("[()]+", "").split(" ");

        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(appointmentFilterDTO.getSearchText().trim()))
        {
            Map<String, Boolean> fieldLowerMap = new HashMap<>();
            fieldLowerMap.put("app.title", true);
            fieldLowerMap.put("app.note", true);
            fieldLowerMap.put("app.location", true);
            fieldLowerMap.put("c.first_name", true);
            fieldLowerMap.put("c.last_name", true);
            fieldLowerMap.put("o.name", true);
            fieldLowerMap.put("o.vat_number", true);
            fieldLowerMap.put("fa.name", true);
            fieldLowerMap.put("fwd.name", true);
            fieldLowerMap.put("sc.first_name", true);
            fieldLowerMap.put("sc.last_name", true);
            fieldLowerMap.put("cfv.value", true);
            for (String term : terms)
            {
                queryTsAndLike.append(QueryBuilderUtils.buildLikeQuery(term, fieldLowerMap));
            }
        }

        ST st = new ST(cachedScriptQueryNoPagination);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("enterprise_id", user.getUnit().getEnterprise().getUuid().toString());
        st.add("role_filter", roleFilter);
        st.add("custom_filter", customFilter);
        st.add("advance_search", advanceSearch);
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());

        st.add("appointment_relation_left_joint", appointmentRelationLeftJoin);
        st.add("lead_filter", leadFilter);
        st.add("date_appointment_range_filter", dateRangeAppointmentFilter);

        Query query = entityManager.createNativeQuery(st.render());
        List<String> uuids = query.getResultList();

        if (uuids.size() == 0)
        {
            return new ArrayList<>();
        }
        List<UUID> uuidList = uuids.parallelStream().map(item -> UUID.fromString(item)).collect(Collectors.toList());

        return appointmentDAO.findByIdIn(uuidList);
    }

    public CountDTO countRecords(String token, String sessionKey, AppointmentFilterDTO appointmentFilterDTO) throws IOException
    {
        User user = sessionDAO.findUserByToken(token);
        //prepare parameters
        //role filter parameter
        String roleFilter = "";
        if (appointmentFilterDTO.getRoleFilterType() != null)
        {
            switch (appointmentFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += " AND ua.uuid = '" + appointmentFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += " AND ua.unit_id = '" + appointmentFilterDTO.getRoleFilterValue() + "' ";
                    break;
            }
        }
        //custom filter paramter
        String customFilter = "";

        if (appointmentFilterDTO.getIsShowHistory())
        {
            customFilter = " AND app.end_date < now()";
        }
        else
        {
            customFilter = " AND app.end_date > now()";
        }

        if (appointmentFilterDTO.getCustomFilter() != null && "noContact".equals(appointmentFilterDTO.getCustomFilter()))
        {
            customFilter += " AND app.first_contact_id IS NULL";
        }

        String advanceSearch = "";
        if (appointmentFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            Enterprise enterprise = getEnterpriseFromToken(token);
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.APPOINTMENT.toString(), appointmentFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }

        //search text paramter
        String[] terms = appointmentFilterDTO.getSearchText().toLowerCase().replaceAll("[()]+", "").split(" ");

        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(appointmentFilterDTO.getSearchText().trim()))
        {
            Map<String, Boolean> fieldLowerMap = new HashMap<>();
            fieldLowerMap.put("app.title", true);
            fieldLowerMap.put("app.note", true);
            fieldLowerMap.put("app.location", true);
            fieldLowerMap.put("c.first_name", true);
            fieldLowerMap.put("c.last_name", true);
            fieldLowerMap.put("o.name", true);
            fieldLowerMap.put("o.vat_number", true);
            fieldLowerMap.put("fa.name", true);
            fieldLowerMap.put("fwd.name", true);
            fieldLowerMap.put("sc.first_name", true);
            fieldLowerMap.put("sc.last_name", true);
            fieldLowerMap.put("cfv.value", true);
            for (String term : terms)
            {
                queryTsAndLike.append(QueryBuilderUtils.buildLikeQuery(term, fieldLowerMap));
            }
        }
        //read the template
        if (StringUtils.isEmpty(cachedCountQuery))
        {
            Resource resource = new ClassPathResource("count_appointment_query.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
            br.close();
            cachedCountQuery = stringBuilder.toString();
        }

        String appointmentRelationLeftJoin = this.buildAppointmentRelationLeftJoin(appointmentFilterDTO);
        String leadFilter = this.buildLeadFilter(appointmentFilterDTO);
        String dateRangeAppointmentFilter = this.buildAppointmentDateRangeFilter(appointmentFilterDTO);

        //fill the params into the template
        ST st = new ST(cachedCountQuery);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("enterprise_id", user.getUnit().getEnterprise().getUuid().toString());
        st.add("role_filter", roleFilter);
        st.add("custom_filter", customFilter);
        st.add("advance_search", advanceSearch);
//        st.add("start_date", new DateTime(appointmentFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(appointmentFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());

        st.add("appointment_relation_left_joint", appointmentRelationLeftJoin);
        st.add("lead_filter", leadFilter);

        st.add("date_appointment_range_filter", dateRangeAppointmentFilter);

        Query query = entityManager.createNativeQuery(st.render());

        List<HashMap> appointmentsList = getAppointmentHistory(user, appointmentFilterDTO);

        return new CountDTO(sessionKey, Long.parseLong(query.getSingleResult().toString()), appointmentsList);
    }

    private List<HashMap> getAppointmentHistory(User user, AppointmentFilterDTO appointmentFilterDTO)
    {

        try
        {
            String appointmentRelationLeftJoin = this.buildAppointmentRelationLeftJoin(appointmentFilterDTO);

            String enterpriseId = user.getUnit().getEnterprise().getUuid().toString();

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT distinct CAST(app.uuid as text),  app.start_date ")
                    .append("FROM appointment app ")
                    .append("LEFT JOIN appointment_custom_data acd ON app.uuid = acd.appointment_id ")
                    .append("LEFT JOIN activity fa ON app.focus_activity_id = fa.uuid ")
                    .append("LEFT JOIN workdata_activity fwd ON app.focus_work_data_id = fwd.uuid ")
                    .append("LEFT JOIN contact c ON app.first_contact_id = c.uuid ")
                    .append("LEFT JOIN appointment_user au ON au.appointment_id = app.uuid ")
                    .append("LEFT JOIN user_account ua ON au.user_id = ua.uuid ")
                    .append("LEFT JOIN unit u ON ua.unit_id = u.uuid ")
                    .append("LEFT JOIN user_account owner_user ON app.owner_id = owner_user.uuid ")
                    .append("LEFT JOIN shared_contact sc ON owner_user.shared_contact_id = sc.uuid ")
                    .append("LEFT JOIN organisation o ON app.organisation_id = o.uuid ")
                    .append(appointmentRelationLeftJoin)
                    .append("WHERE u.enterprise_id = '" + enterpriseId + "' ")
                    .append("AND app.deleted = FALSE ");

            String roleFilter = "";
            if (appointmentFilterDTO.getRoleFilterType() != null)
            {
                switch (appointmentFilterDTO.getRoleFilterType())
                {
                    case "Person": // For Person, using UserId
                        roleFilter += " AND ua.uuid = '" + appointmentFilterDTO.getRoleFilterValue() + "' ";
                        break;
                    case "Unit": // This case for Unit, using UnitId
                        roleFilter += " AND ua.unit_id = '" + appointmentFilterDTO.getRoleFilterValue() + "' ";
                        break;
                    case "Company": // This case for enterprise
//	                roleFilter += " AND app.enterprise_id = '" + enterprise.getUuid().toString() + "' ";
                    default:
                        break;
                }
            }

            queryBuilder.append(roleFilter);

            String leadFilter = this.buildLeadFilter(appointmentFilterDTO);
            queryBuilder.append(leadFilter);

            String customFilter = "";
            if (appointmentFilterDTO.getCustomFilter() != null && "noContact".equals(appointmentFilterDTO.getCustomFilter()))
            {
                customFilter += " AND app.first_contact_id IS NULL";
            }

            queryBuilder.append(customFilter);

            //search text paramter
            String[] terms = appointmentFilterDTO.getSearchText().toLowerCase().replaceAll("[()]+", "").split(" ");

            StringBuilder queryTsAndLike = new StringBuilder();
            if (StringUtils.isNotEmpty(appointmentFilterDTO.getSearchText().trim()))
            {
                Map<String, Boolean> fieldLowerMap = new HashMap<>();
                fieldLowerMap.put("app.title", true);
                fieldLowerMap.put("app.note", true);
                fieldLowerMap.put("app.location", true);
                fieldLowerMap.put("c.first_name", true);
                fieldLowerMap.put("c.last_name", true);
                fieldLowerMap.put("o.name", true);
                fieldLowerMap.put("fa.name", true);
                fieldLowerMap.put("fwd.name", true);
                fieldLowerMap.put("sc.first_name", true);
                fieldLowerMap.put("sc.last_name", true);
                for (String term : terms)
                {
                    queryTsAndLike.append(QueryBuilderUtils.buildLikeQuery(term, fieldLowerMap));
                }
            }

            queryBuilder.append(queryTsAndLike.toString());
            queryBuilder.append(" GROUP BY app.uuid, owner_user.uuid, sc.uuid, c.uuid, au.uuid, ua.uuid, u.uuid, acd.uuid, o.uuid, fwd.uuid, fa.uuid ");

            Query query = entityManager.createNativeQuery(queryBuilder.toString());
            List<Object[]> appointments = query.getResultList();

            List<HashMap> appointmentsList = new ArrayList<>();
            for (Object[] object : appointments)
            {
                String uuid = (String) object[0];
                Date startDate = (Date) object[1];
                Map<String, Object> appointment = new HashMap<>();
                appointment.put("uuid", uuid);
                appointment.put("startDate", startDate.getTime());

                appointmentsList.add((HashMap) appointment);
            }

            return appointmentsList;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    //remove this one in next sprint
    public String correctTimeZoneData()
    {
        String queryLike = "%2b%";
        List<Appointment> appointmentList = appointmentDAO.findByTimezoneLike(queryLike);
        for (Appointment appointment : appointmentList)
        {
            String newTimeZone = appointment.getTimezone().replaceAll("%2b", "+");
            appointment.setTimezone(newTimeZone);
        }
        appointmentDAO.save(appointmentList);
        return "SUCCESS";
    }
}
