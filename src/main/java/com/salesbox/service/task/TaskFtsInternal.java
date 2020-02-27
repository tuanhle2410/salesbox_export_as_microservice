package com.salesbox.service.task;

import com.salesbox.advancesearch.service.ConvertSearchFieldService;
import com.salesbox.common.BaseService;
import com.salesbox.dao.TaskDAO;
import com.salesbox.dto.CountDTO;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.Task;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.exception.ServiceException;
import com.salesbox.task.dto.TaskFilterDTO;
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

@Service
@Transactional(rollbackFor = Exception.class)
public class TaskFtsInternal extends BaseService
{
    @Autowired
    TaskDAO taskDAO;
    @PersistenceContext
    private EntityManager entityManager;

    private String cachedScriptQuery;

    private String cachedCountQuery;


    @Autowired
    private ShowTaskServiceInternal showTaskServiceInternal;

    @Autowired
    ConvertSearchFieldService convertSearchFieldService;

    @Value("${host.timezone}")
    private String timeZone;

    public List<Task> listFromDB(String token, String sessionKey, TaskFilterDTO taskFilterDTO, Integer pageIndex, Integer pageSize) throws ServiceException, IOException
    {
        User user = getUserFromToken(token);
        Enterprise enterprise = user.getUnit().getEnterprise();

//        try
//        {
//            googleSyncTaskServiceInternal.startTriggerSyncGoogleTask(token);
//        }
//        catch (ServiceException | com.google.gdata.util.ServiceException e)
//        {
//            e.printStackTrace();
//        }

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        String roleFilter = "";
        if (taskFilterDTO.isDelegateTask())
        {
            taskFilterDTO.setRoleFilterType("Company");
        }
        if (taskFilterDTO.getRoleFilterType() != null)
        {
            switch (taskFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += "AND t.owner_id = '" + taskFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += "AND u.uuid = '" + taskFilterDTO.getRoleFilterValue() + "' ";
                    break;
            }
        }
        boolean ignoreOrderStatement = false;
        String customFilter = "";
        if (taskFilterDTO.getCustomFilter() != null)
        {
            switch (taskFilterDTO.getCustomFilter())
            {
                case "history":
                    customFilter += "AND t.is_finished = true ";
                    break;
                case "active":
                default:
                    customFilter += "AND t.is_finished = false ";
                    // Default is active
            }
        }
        customFilter += "AND t.deleted = false ";
        if (taskFilterDTO.isDelegateTask())
        {
            customFilter += "AND t.owner_id IS NULL AND t.type=3 ";
        }
        else
        {
            customFilter += "AND t.type <> 3";
        }

        String advanceSearch = "";

        if (taskFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.TASK.toString(), taskFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }

        String orderByStatement = "";
        if (!ignoreOrderStatement)
        {
            switch (taskFilterDTO.getOrderBy())
            {
                case "category":
                    orderByStatement += "ORDER BY category_name, focus_name,t.created_date";
                    break;
                case "focus":
                    orderByStatement += "ORDER BY focus_name,t.created_date";
                    break;
                case "owner":
                    orderByStatement += "ORDER BY _owner_id desc, focus_name,t.created_date";
                    break;
                case "account":
                    orderByStatement += "ORDER BY account_name, focus_name,t.created_date";
                    break;
                case "contact":
                    orderByStatement += "ORDER BY c.first_name NULLS LAST, c.last_name NULLS LAST, focus_name,t.created_date";
                    break;
                case "noTimeSet":
                    if (taskFilterDTO.getCustomFilter().equals("history"))
                    {
                        orderByStatement += "ORDER BY t.date_and_time desc NULLS FIRST, focus_name,t.created_date";
                    }
                    else
                    {
                        orderByStatement += "ORDER BY t.date_and_time asc NULLS FIRST, focus_name,t.created_date";
                    }
                    break;
                case "dateAndTime":
                    if (taskFilterDTO.getCustomFilter().equals("history"))
                    {
                        orderByStatement += "ORDER BY t.date_and_time desc NULLS LAST, c.first_name asc, t.created_date";
                    }
                    else
                    {
                        orderByStatement += "ORDER BY t.date_and_time asc NULLS LAST, c.first_name asc, t.created_date";
                    }
                    break;
                default:
                    orderByStatement = "ORDER BY t.date_and_time desc NULLS LAST";
                    break;
            }
        }
        //time filter param
        String timeFilter = "";
        if (!taskFilterDTO.isFilterAll())
        {
            timeFilter = "AND (t.date_and_time BETWEEN " + "'" + new DateTime(taskFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'" + " AND " + "'" + new DateTime(taskFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'" + " OR t.date_and_time IS NULL) ";
        }

        //mark filter parameter
        String markFilter = "";
        if (taskFilterDTO.getSelectedMark() != null)
        {
            markFilter = " AND t.task_tag_id ='" + taskFilterDTO.getSelectedMark() + "' ";
        }

        if (StringUtils.isEmpty(cachedScriptQuery))
        {
            Resource resource = new ClassPathResource("query_task_search_index.sql");
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

        String[] terms = taskFilterDTO.getFtsTerms().toLowerCase().replaceAll("[()]+", "").split(" ");
        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(taskFilterDTO.getFtsTerms().trim()))
        {
            Map<String, Boolean> fieldLowerMap = new HashMap<>();
            fieldLowerMap.put("c.first_name", true);
            fieldLowerMap.put("c.last_name", true);
            fieldLowerMap.put("sc.first_name", true);
            fieldLowerMap.put("sc.last_name", true);
            fieldLowerMap.put("c.phone", true);
            fieldLowerMap.put("c.email", true);
            fieldLowerMap.put("o.name", true);
            fieldLowerMap.put("o.vat_number", true);
            fieldLowerMap.put("fa.name", true);
            fieldLowerMap.put("fwd.name", true);
            fieldLowerMap.put("cwd.name", true);
            fieldLowerMap.put("t.note", true);
//            fieldLowerMap.put("p.description", true);
            fieldLowerMap.put("cfv.value", true);
            for (String term : terms)
            {
                queryTsAndLike.append(QueryBuilderUtils.buildLikeQuery(term, fieldLowerMap));
            }
        }

        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append(", CASE WHEN (t.owner_id='" + user.getUuid().toString() + "') THEN 1 ELSE 0 END AS _owner_id");

        ST st = new ST(cachedScriptQuery);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("enterprise_id", enterprise.getUuid().toString());
        st.add("select_statement", selectStatement);
        st.add("role_filter", roleFilter);
        st.add("custom_filter", customFilter);
        st.add("advance_search", advanceSearch);
        st.add("mark_filter", markFilter);
        st.add("order_by_statement", orderByStatement);
//        st.add("start_date", new DateTime(taskFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(taskFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("time_filter", timeFilter);
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());
        st.add("limit_and_offset", " LIMIT " + pageable.getPageSize() + " OFFSET " + (pageable.getPageNumber() * pageable.getPageSize()));

        Query query = entityManager.createNativeQuery(st.render());
        List<Object[]> uuids = query.getResultList();

        List<UUID> uuidList = new ArrayList<>();
        for (Object[] uuid : uuids)
        {
            uuidList.add(UUID.fromString(uuid[0].toString()));
        }

        List<Task> taskList = new ArrayList<>();
        if (uuidList.size() > 0)
        {
            taskList = taskDAO.findByIds(uuidList);
        }

        return taskList;
    }

    public CountDTO countRecords(String token, String sessionKey, TaskFilterDTO taskFilterDTO) throws IOException
    {

        User user = showTaskServiceInternal.getUserFromToken(token);
        Enterprise enterprise = user.getUnit().getEnterprise();
        //role filter parameter
        String roleFilter = "";
        if (taskFilterDTO.isDelegateTask())
        {
            taskFilterDTO.setRoleFilterType("Company");
        }
        if (taskFilterDTO.getRoleFilterType() != null)
        {
            switch (taskFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += "AND t.owner_id = '" + taskFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += "AND u.uuid = '" + taskFilterDTO.getRoleFilterValue() + "' ";
                    break;
            }
        }
        //custom filter parameter
        String customFilter = "";
        if (taskFilterDTO.getCustomFilter() != null)
        {
            switch (taskFilterDTO.getCustomFilter())
            {
                case "history":
                    customFilter += "AND t.is_finished = true ";
                    break;
                case "active":
                default:
                    customFilter += "AND t.is_finished = false ";
                    // Default is active
            }
        }
        customFilter += "AND t.deleted = false ";
        if (taskFilterDTO.isDelegateTask())
        {
            customFilter += "AND t.owner_id IS NULL AND t.type=3 ";
        }
        else
        {
            customFilter += "AND t.type <> 3";
        }

        String advanceSearch = "";
        if (taskFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery("TASK", taskFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }
        //time filter param
        String timeFilter = "";
        if (!taskFilterDTO.isFilterAll())
        {
            timeFilter = "AND (t.date_and_time BETWEEN " + "'" + new DateTime(taskFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'" + " AND " + "'" + new DateTime(taskFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'" + " OR t.date_and_time IS NULL) ";
        }


        //mark filter parameter
        String markFilter = "";
        if (taskFilterDTO.getSelectedMark() != null)
        {
            markFilter = " AND t.task_tag_id ='" + taskFilterDTO.getSelectedMark() + "' ";
        }

        //read the template
        if (StringUtils.isEmpty(cachedCountQuery))
        {
            Resource resource = new ClassPathResource("count_task_query.sql");
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

        String[] terms = taskFilterDTO.getFtsTerms().toLowerCase().replaceAll("[()]+", "").split(" ");
        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(taskFilterDTO.getFtsTerms().trim()))
        {
            Map<String, Boolean> fieldLowerMap = new HashMap<>();
            fieldLowerMap.put("c.first_name", true);
            fieldLowerMap.put("c.last_name", true);
            fieldLowerMap.put("sc.first_name", true);
            fieldLowerMap.put("sc.last_name", true);
            fieldLowerMap.put("c.phone", true);
            fieldLowerMap.put("c.email", true);
            fieldLowerMap.put("o.name", true);
            fieldLowerMap.put("o.vat_number", true);
            fieldLowerMap.put("fa.name", true);
            fieldLowerMap.put("fwd.name", true);
            fieldLowerMap.put("cwd.name", true);
            fieldLowerMap.put("t.note", true);
//            fieldLowerMap.put("p.description", true);
            fieldLowerMap.put("cfv.value", true);
            for (String term : terms)
            {
                queryTsAndLike.append(QueryBuilderUtils.buildLikeQuery(term, fieldLowerMap));
            }
        }

        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append(", CASE WHEN (t.owner_id='" + user.getUuid().toString() + "') THEN 1 ELSE 0 END AS _owner_id");

        ST st = new ST(cachedCountQuery);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("enterprise_id", enterprise.getUuid().toString());
        st.add("select_statement", selectStatement);
        st.add("role_filter", roleFilter);
        st.add("custom_filter", customFilter);
        st.add("advance_search", advanceSearch);
        st.add("mark_filter", markFilter);
//        st.add("start_date", new DateTime(taskFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(taskFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("time_filter", timeFilter);
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());

        Query query = entityManager.createNativeQuery(st.render());
        return new CountDTO(sessionKey, Long.parseLong(query.getSingleResult().toString()));
    }

}
