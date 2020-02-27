package com.salesbox.lead;

import com.salesbox.advancesearch.dto.SearchFieldDTO;
import com.salesbox.advancesearch.dto.fields.LeadField;
import com.salesbox.advancesearch.service.ConvertSearchFieldService;
import com.salesbox.dao.LeadDAO;
import com.salesbox.dao.LeadProductDAO;
import com.salesbox.dao.SessionDAO;
import com.salesbox.dto.CountDTO;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.exception.ServiceException;
import com.salesbox.lead.dto.LeadFilterDTO;
import com.salesbox.service.ProductLoader;
import com.salesbox.service.lead.LeadService;
import com.salesbox.service.task.ITaskRelationService;
import com.salesbox.utils.CustomFieldUtils;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import java.util.List;

/**
 * Created by quynhtq on 6/11/15.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LeadFtsInternal
{

    @Autowired
    SessionDAO sessionDAO;
    @Autowired
    LeadDAO leadDAO;
    @Autowired
    LeadService leadService;
    @Autowired
    ProductLoader productLoader;
    @Autowired
    LeadProductDAO leadProductDAO;
    @Autowired
    ConvertSearchFieldService convertSearchFieldService;
    @Autowired
    ITaskRelationService taskRelationService;
    @Autowired
    MapperFacade mapper;

    private Logger logger = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager entityManager;

    private String cachedScriptQuery;
    private String cachedScriptQueryNoPaging;
    private String cachedCountQuery;

    @Value("${host.timezone}")
    private String timeZone;

    private String buildStatusCondition(String statusSearchValue)
    {
        if (statusSearchValue == null)
        {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(" And l.status ");
        //Consider None value is null in database
        if ("none".equals(statusSearchValue))
        {
            stringBuilder.append(" is null ");
        }
        else
        {
            stringBuilder.append(" = \'" + statusSearchValue + "\' ");
        }
        return stringBuilder.toString();
    }

    public List<Object[]> getLeads(String token, String sessionKey, LeadFilterDTO leadFilterDTO, int pageIndex, int pageSize) throws ServiceException, IOException
    {
        User user = leadService.getUserFromToken(token);
        Enterprise enterprise = user.getUnit().getEnterprise();
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        String roleFilter = "";
        if (leadFilterDTO.isDelegateLead())
        {
            leadFilterDTO.setRoleFilterType("Company");
        }
        if (leadFilterDTO.getRoleFilterType() != null)
        {
            switch (leadFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += "AND l.owner_id = " + (leadFilterDTO.getRoleFilterValue() == null ? null : ("\'" + leadFilterDTO.getRoleFilterValue() + "\'")) + " ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += "AND ou.unit_id = '" + leadFilterDTO.getRoleFilterValue() + "' ";
                    break;
            }
        }

        String statusFilter = this.buildStatusCondition(leadFilterDTO.getStatusSearchValue());

        boolean ignoreOrderStatement = false;
        String customFilter = "", filterCriteria = "", activeNoDeadlineCondition = "";
        if (leadFilterDTO.getCustomFilter() != null)
        {
            switch (leadFilterDTO.getCustomFilter())
            {
                case "history":
                    customFilter += "AND l.is_finished = true ";
                    filterCriteria = " AND ( l.finished_date";
                    activeNoDeadlineCondition = ")";
                    break;
                case "active":
                    customFilter += "AND l.is_finished = false ";
                    filterCriteria = " AND (l.deadline_date";
                    activeNoDeadlineCondition = " OR l.deadline_date IS NULL)";
            }
        }
        customFilter += "AND l.deleted = false ";
        if (leadFilterDTO.isDelegateLead())
        {
            customFilter += "AND l.owner_id IS NULL AND l.type=3 ";
        }
        else
        {
            customFilter += "AND l.type <> 3";
        }

        String advanceSearch = "";
        if (leadFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.LEAD.toString(), leadFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }

        // check is advansearch lead by product type -> specified case
        String leftJoinProduct = " ";
        if (leadFilterDTO.getSearchFieldDTOList() != null && leadFilterDTO.getSearchFieldDTOList() != null)
        {
            List<SearchFieldDTO[]>   searchFielDTOList = leadFilterDTO.getSearchFieldDTOList();
            for (SearchFieldDTO[] searchFieldDTOArras : searchFielDTOList)
            {
                for (int andIndex = 0; andIndex < searchFieldDTOArras.length; andIndex++)
                {
                    String field = searchFieldDTOArras[andIndex].getField();
                    if(LeadField.PRODUCT_TYPE.equalsIgnoreCase(field) || LeadField.PRODUCT.equalsIgnoreCase(field)){
                        leftJoinProduct = "\tLEFT JOIN lead_product lp ON l.uuid = lp.lead_id \n" +
                                "\tLEFT JOIN product p ON lp.product_id = p.uuid";
                    }
                }
            }
        }
        // check is advansearch lead by focus -> specified case

        String leftJoinTaskRelation = " ";
        if (leadFilterDTO.getSearchFieldDTOList() != null && leadFilterDTO.getSearchFieldDTOList() != null)
        {
            List<SearchFieldDTO[]>   searchFielDTOList = leadFilterDTO.getSearchFieldDTOList();
            for (SearchFieldDTO[] searchFieldDTOArras : searchFielDTOList)
            {
                for (int andIndex = 0; andIndex < searchFieldDTOArras.length; andIndex++)
                {
                    if(searchFieldDTOArras[andIndex].getField().equalsIgnoreCase(LeadField.FOCUS_WORKDATA)){
                        leftJoinTaskRelation = "\tLEFT JOIN task_relation tr ON tr.relation_object_uuid = l.uuid \n" +
                                                 "\tLEFT JOIN task t ON t.uuid = tr.task_uuid ";
                    }
                }
            }
        }

        String orderByStatement = "";
        if (!ignoreOrderStatement)
        {
            switch (leadFilterDTO.getOrderBy())
            {
                case "priority":
                    orderByStatement += "ORDER BY visit_more DESC, _priority_order desc, ";
                    if (leadFilterDTO.getCustomFilter().equals("history"))
                    {
                        orderByStatement += "l.finished_date desc";
                    }
                    else
                    {
                        orderByStatement += "l.deadline_date desc nulls last";
                    }
                    break;
                case "source":
                    orderByStatement += "ORDER BY visit_more DESC, l.source, contact_name";
                    break;
                case "lineOfBusiness":
                    orderByStatement += "ORDER BY visit_more DESC, line_of_business_name, contact_name";
                    break;
//                case "product":
//                    orderByStatement += "ORDER BY lsi.product desc";
//                    break;
                case "owner":
                    orderByStatement += "ORDER BY visit_more DESC, _owner_id desc, contact_name";
                    break;
                case "account":
                    orderByStatement += "ORDER BY visit_more DESC, account_name, contact_name";
                    break;
                case "contact":
                    orderByStatement += "ORDER BY visit_more DESC, contact_name";
                    break;
                case "dateTime":
                    if (leadFilterDTO.getCustomFilter().equals("history"))
                    {
                        orderByStatement += "ORDER BY visit_more DESC, l.deadline_date desc nulls last, contact_name, l.created_date ";
                    }
                    else
                    {
                        orderByStatement += "ORDER BY visit_more DESC, l.deadline_date desc nulls last, contact_name, l.created_date";
                    }
                    break;
                case "prospect":
                    orderByStatement += "ORDER BY visit_more DESC, _prospect desc, contact_name";
                    break;
                default:
                    orderByStatement += "ORDER BY visit_more DESC, _priority_order desc, contact_name";
            }
        }
        //case time_filter
        String timeFilter = "";
        if (leadFilterDTO.isFilterAll())
        {
            filterCriteria = "";
            activeNoDeadlineCondition = "";
        }
        else
        {
            timeFilter = " BETWEEN '" + new DateTime(leadFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss") + "' AND '" + new DateTime(leadFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'";
        }

        //create the query template
        if (StringUtils.isEmpty(cachedScriptQuery))
        {
            Resource resource = new ClassPathResource("query_lead_search_index.sql");
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

        StringBuilder queryTsAndLike = new StringBuilder();
        if (leadFilterDTO.getFtsTerms() != null && StringUtils.isNotEmpty(leadFilterDTO.getFtsTerms().trim()))
        {
            String[] terms = leadFilterDTO.getFtsTerms().replaceAll("[()]+", "").split(" ");
            for (String term : terms)
            {
                queryTsAndLike.append(" AND ( " +
                        " (lower(unaccent(c.first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(c.last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (regexp_replace(regexp_replace(c.phone, '[-+..]', ' ','g'), '\\s', '', 'g') like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(regexp_replace(regexp_replace(c.email, '[@]', ' ','g'), '\\s', '', 'g'))) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(csc.first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(csc.last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(osc.first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(osc.last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(o.name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(o.vat_number)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(l.note)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(lob.name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(cfv.value)) like lower(unaccent('%" + term + "%'))) " +
                        " ) ");
            }
        }

        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append(", CASE WHEN (l.owner_id='").append(user.getUuid().toString()).append("') THEN 1 ELSE 0 END AS _owner_id");
        selectStatement.append(", CASE WHEN (l.prospect_id IS NOT NULL) THEN 1 ELSE 0 END AS _prospect");
        selectStatement.append(", CASE WHEN l.priority \\<= 20 THEN 0 WHEN l.priority > 20 AND l.priority \\<= 40 THEN 1 " +
                "WHEN l.priority > 40 AND l.priority \\<= 60 THEN 2 " +
                "WHEN l.priority > 60 AND l.priority \\<= 80 THEN 3 " +
                "WHEN l.priority > 80 THEN 4 " +
                "END AS _priority_order ");

        ST st = new ST(cachedScriptQuery);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("enterprise_id", enterprise.getUuid().toString());
        st.add("select_statement", selectStatement);
        st.add("filter_criteria", filterCriteria);
        st.add("role_filter", roleFilter);
        st.add("status_filter", statusFilter);
        st.add("advance_search", advanceSearch);
        st.add("custom_filter", customFilter);
        st.add("active_no_deadline_condition", activeNoDeadlineCondition);
        st.add("order_by_statement", orderByStatement);
//        st.add("start_date", new DateTime(leadFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(leadFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("time_filter", timeFilter);
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());
        st.add("left_join_product",leftJoinProduct);
        st.add("left_join_task_relation",leftJoinTaskRelation);
        st.add("limit_and_offset", " LIMIT " + pageable.getPageSize() + " OFFSET " + (pageable.getPageNumber() * pageable.getPageSize()));

        Query query = entityManager.createNativeQuery(st.render());
        List<Object[]> properties = query.getResultList();

        return properties;
    }

    public CountDTO countRecords(String token, String sessionKey, LeadFilterDTO leadFilterDTO) throws IOException
    {

        User user = leadService.getUserFromToken(token);
        Enterprise enterprise = user.getUnit().getEnterprise();
        //prepare params
        //role filter parameter
        String roleFilter = "";
        if (leadFilterDTO.isDelegateLead())
        {
            leadFilterDTO.setRoleFilterType("Company");
        }
        if (leadFilterDTO.getRoleFilterType() != null)
        {
            switch (leadFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += "AND l.owner_id = '" + leadFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += "AND ou.unit_id = '" + leadFilterDTO.getRoleFilterValue() + "' ";
                    break;
            }
        }

        String statusFilter = this.buildStatusCondition(leadFilterDTO.getStatusSearchValue());
        //custom filter parameter
        String customFilter = "", filterCriteria = "", activeNoDeadlineCondition = "";
        if (leadFilterDTO.getCustomFilter() != null)
        {
            switch (leadFilterDTO.getCustomFilter())
            {
                case "history":
                    customFilter += "AND l.is_finished = true ";
                    filterCriteria = " AND ( l.finished_date";
                    activeNoDeadlineCondition = ")";
                    break;
                case "active":
                    customFilter += "AND l.is_finished = false ";
                    filterCriteria = " AND (l.deadline_date";
                    activeNoDeadlineCondition = " OR l.deadline_date IS NULL)";
            }
        }
        customFilter += "AND l.deleted = false ";
        if (leadFilterDTO.isDelegateLead())
        {
            customFilter += "AND l.owner_id IS NULL AND l.type=3 ";
        }
        else
        {
            customFilter += "AND l.type <> 3";
        }

        String advanceSearch = "";
        if (leadFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.LEAD.toString(), leadFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }

        // check is filter by produc type -> specified case
        String leftJoinProduct = " ";
        if (leadFilterDTO.getSearchFieldDTOList() != null)
        {
            List<SearchFieldDTO[]>   searchFielDTOList = leadFilterDTO.getSearchFieldDTOList();
            for (SearchFieldDTO[] searchFieldDTOArras : searchFielDTOList)
            {
                for (int andIndex = 0; andIndex < searchFieldDTOArras.length; andIndex++)
                {
                    String field = searchFieldDTOArras[andIndex].getField();
                    if(LeadField.PRODUCT_TYPE.equalsIgnoreCase(field) || LeadField.PRODUCT.equalsIgnoreCase(field)){
                        leftJoinProduct = "\tLEFT JOIN lead_product lp ON l.uuid = lp.lead_id \n" +
                                "\tLEFT JOIN product p ON lp.product_id = p.uuid";
                    }
                }
            }
        }
        // check is advansearch lead by focus -> specified case
        String leftJoinTaskRelation = " ";
        if (leadFilterDTO.getSearchFieldDTOList() != null)
        {
            List<SearchFieldDTO[]>   searchFielDTOList = leadFilterDTO.getSearchFieldDTOList();
            for (SearchFieldDTO[] searchFieldDTOArras : searchFielDTOList)
            {
                for (int andIndex = 0; andIndex < searchFieldDTOArras.length; andIndex++)
                {
                    if(searchFieldDTOArras[andIndex].getField().equalsIgnoreCase(LeadField.FOCUS_WORKDATA)){
                        leftJoinTaskRelation = "\tLEFT JOIN task_relation tr ON tr.relation_object_uuid = l.uuid \n" +
                                "\tLEFT JOIN task t ON t.uuid = tr.task_uuid ";
                    }
                }
            }
        }

        //time filter
        String timeFilter = "";
        if (leadFilterDTO.isFilterAll())
        {
            filterCriteria = "";
            activeNoDeadlineCondition = "";
        }
        else
        {
            timeFilter = " BETWEEN '" + new DateTime(leadFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss") + "' AND '" + new DateTime(leadFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'";
        }

        //search text parameter
        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(leadFilterDTO.getFtsTerms().trim()))
        {
            String[] terms = leadFilterDTO.getFtsTerms().replaceAll("[()]+", "").split(" ");
            for (String term : terms)
            {
                queryTsAndLike.append(" AND ( " +
                        " (lower(unaccent(c.first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(c.last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (regexp_replace(regexp_replace(c.phone, '[-+..]', ' ','g'), '\\s', '', 'g') like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(regexp_replace(regexp_replace(c.email, '[@]', ' ','g'), '\\s', '', 'g'))) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(csc.first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(csc.last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(osc.first_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(osc.last_name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(o.name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(o.vat_number)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(l.note)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(lob.name)) like lower(unaccent('%" + term + "%'))) " +
                        " OR (lower(unaccent(cfv.value)) like lower(unaccent('%" + term + "%'))) " +
                        " ) ");
            }
        }

        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append(", CASE WHEN (l.owner_id='" + user.getUuid().toString() + "') THEN 1 ELSE 0 END AS _owner_id");
        selectStatement.append(", CASE WHEN (l.prospect_id IS NOT NULL) THEN 1 ELSE 0 END AS _prospect");
        //get the template
        if (StringUtils.isEmpty(cachedCountQuery))
        {
            Resource resource = new ClassPathResource("count_lead_query.sql");
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
        //fill the parameters into query
        ST st = new ST(cachedCountQuery);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("enterprise_id", enterprise.getUuid().toString());
        st.add("filter_criteria", filterCriteria);
        st.add("select_statement", selectStatement);
        st.add("role_filter", roleFilter);
        st.add("status_filter", statusFilter);
        st.add("custom_filter", customFilter);
        st.add("active_no_deadline_condition", activeNoDeadlineCondition);
        st.add("advance_search", advanceSearch);
//        st.add("start_date", new DateTime(leadFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(leadFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("time_filter", timeFilter);
        st.add("left_join_product",leftJoinProduct);
        st.add("left_join_task_relation",leftJoinTaskRelation);
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());

        Query query = entityManager.createNativeQuery(st.render());
        return new CountDTO(sessionKey, Long.parseLong(query.getSingleResult().toString()));
    }


}
