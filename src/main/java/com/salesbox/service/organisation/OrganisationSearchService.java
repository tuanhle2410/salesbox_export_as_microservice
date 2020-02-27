package com.salesbox.service.organisation;

import com.salesbox.advancesearch.service.ConvertSearchFieldService;
import com.salesbox.common.BaseService;
import com.salesbox.common.enums.FullTextSearchRule;
import com.salesbox.dto.CountDTO;
import com.salesbox.dto.OrganisationResultSet;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.CommunicationType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.organisation.dto.OrganisationFilterDTO;
import com.salesbox.utils.CustomFieldUtils;
import com.salesbox.utils.FullTextSearchUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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

/**
 * Created by QuynhTQ on 3/27/2016.
 */
@Service
@Transactional
public class OrganisationSearchService extends BaseService
{
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    ConvertSearchFieldService convertSearchFieldService;
    @Autowired
    ResourceLoader resourceLoader;

    public String cachedSearchOrganisationLiteQuery;
    public String cachedSearchOrganisationLiteQueryCompanyLevel;
    public String cachedSearchOrganisationQuery;
    public String cachedSearchOrganisationQueryCompanyLevel;
    public List<UUID> getUUIDList(String token, Integer pageIndex, Integer pageSize, OrganisationFilterDTO organisationFilterDTO) throws IOException
    {
        List<OrganisationResultSet> organisationResultSetList = findOrganisationFts_Improve(token, pageIndex, pageSize, organisationFilterDTO, true);

        if (organisationResultSetList.size() == 0)
        {
            return new ArrayList<>();
        }
        Set<UUID> uuidSet = new HashSet<>();
        for (OrganisationResultSet organisationResultSet : organisationResultSetList)
        {
            uuidSet.add(organisationResultSet.getUuid());
        }

        return new ArrayList<>(uuidSet);

    }

    public CountDTO countOrganisationRecords(String token, String sessionKey, OrganisationFilterDTO organisationFilterDTO) throws IOException
    {
        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);
        String roleFilter = "AND o.enterprise_id = '" + enterprise.getUuid().toString() + "' ";

        if (organisationFilterDTO.getRoleFilterType() != null)
        {
            switch (organisationFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
//                    roleFilter += "AND ( ou.user_id = '" + organisationFilterDTO.getRoleFilterValue() + "' OR (SELECT COUNT(*) FROM organisation_user team_member WHERE o.uuid = team_member.organisation_id) = 0 ) ";
                    roleFilter += "AND ( ou.user_id = '" + organisationFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Unit": // This case for Unit, using UnitId
//                    roleFilter += "AND ( ua.unit_id = '" + organisationFilterDTO.getRoleFilterValue() + "' OR (SELECT COUNT(*) FROM organisation_user team_member WHERE o.uuid = team_member.organisation_id) = 0 ) ";
                    roleFilter += "AND ( ua.unit_id = '" + organisationFilterDTO.getRoleFilterValue() + "' ";
                    break;
                case "Company":
                    roleFilter += "AND ( true  ";
                    break;
            }
        }

        String customFilter = "";
        if (organisationFilterDTO.getCustomFilter() != null)
        {
            switch (organisationFilterDTO.getCustomFilter())
            {
                case "favorite":
                    customFilter += "AND ocd.is_favorite ";
                    break;
                case "recent":
                    customFilter += "AND ocd.last_viewed IS NOT NULL ";
                    break;
                case "alphabetical":
                    break;
            }
        }
        String advanceSearch = "";

        if (organisationFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            roleFilter += " OR (SELECT COUNT(*) FROM organisation_user team_member WHERE o.uuid = team_member.organisation_id) = 0 ";
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.ACCOUNT.toString(), organisationFilterDTO.getSearchFieldDTOList(), enterprise) + ")";
        }

        String[] terms = organisationFilterDTO.getFtsTerms().toLowerCase().replaceAll("\'", "").replaceAll("[()]+", "").split(" ");

        Resource resource;
        if (organisationFilterDTO.getFtsTerms().length() == 0 && organisationFilterDTO.getSearchFieldDTOList().size() == 0)
        {
            resource = new ClassPathResource("query_count_full_text_search_organisation_lite.sql");
        }
        else
        {
            roleFilter += " OR (SELECT COUNT(*) FROM organisation_user team_member WHERE o.uuid = team_member.organisation_id) = 0 ";
            resource = new ClassPathResource("query_count_full_text_search_organisation.sql");
        }

        roleFilter +=  ") ";

        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
        {
            stringBuilder.append(line).append('\n');
        }
        br.close();
        String cachedCountOrganisationQuery = stringBuilder.toString();


        //build like query
//        String queryTsAndLike = getQueryTsAndLike(organisationFilterDTO.getFtsTerms());
        String queryTsAndLike = fullTextSearchQueryBuilder(organisationFilterDTO.getFtsTerms(), false);
        ST st = new ST(cachedCountOrganisationQuery);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("query_ts_and_like", queryTsAndLike);
        st.add("role_filter", roleFilter);
        st.add("custom_filter", customFilter);
        st.add("advance_search", advanceSearch);
        st.add("user_id", user.getUuid().toString());
        Query query = entityManager.createNativeQuery(st.render());
        Long recordCounted = Long.parseLong(query.getSingleResult().toString());

        return new CountDTO(sessionKey, recordCounted);
    }

    private List<OrganisationResultSet> findOrganisationFts_Improve(String token, Integer pageIndex, Integer pageSize,
                                                                    OrganisationFilterDTO organisationFilterDTO, Boolean isGetAll) throws IOException
    {

        Enterprise enterprise = getEnterpriseFromToken(token);
        User user = getUserFromToken(token);

        StringBuilder roleFilter = new StringBuilder("AND o.enterprise_id = '").append(enterprise.getUuid().toString()).append("' ");

        if (organisationFilterDTO.getRoleFilterType() != null)
        {
            switch (organisationFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
//                    roleFilter = roleFilter.append("AND ( ou.user_id = '" + organisationFilterDTO.getRoleFilterValue() + "' OR (SELECT COUNT(*) FROM organisation_user team_member WHERE o.uuid = team_member.organisation_id) = 0 ) ");
                    roleFilter = roleFilter.append("AND ( ou.user_id = '" + organisationFilterDTO.getRoleFilterValue() + "' ");
                    break;
                case "Unit": // This case for Unit, using UnitId
//                    roleFilter = roleFilter.append("AND ( ua.unit_id = '" + organisationFilterDTO.getRoleFilterValue() + "' OR (SELECT COUNT(*) FROM organisation_user team_member WHERE o.uuid = team_member.organisation_id) = 0 ) ");
                    roleFilter = roleFilter.append("AND ( ua.unit_id = '" + organisationFilterDTO.getRoleFilterValue() + "' ");
                    break;
                case "Company":
                    roleFilter = roleFilter.append("AND ( true  ");
                    break;
            }
        }


        boolean ignoreOrderStatement = false;
        boolean isNextMeeting = false;
        StringBuilder customFilter = new StringBuilder("");
        StringBuilder orderByStatement = new StringBuilder("");
        StringBuilder advanceSearch = new StringBuilder("");
        if (organisationFilterDTO.getCustomFilter() != null)
        {
            switch (organisationFilterDTO.getCustomFilter())
            {
                case "active":
                    break;

                case "favorite":
                    ignoreOrderStatement = true;
                    customFilter.append("AND ocd.is_favorite ");
                    orderByStatement.append("ORDER BY q_search.name ASC ");
                    break;
                case "recent":
                    ignoreOrderStatement = true;
                    customFilter.append("AND ocd.last_viewed IS NOT NULL ");
                    orderByStatement.append("ORDER BY ocd.last_viewed DESC NULLS LAST ");
                    break;
                case "alphabetical":
                    ignoreOrderStatement = true;
                    orderByStatement.append("ORDER BY q_search.name ASC ");
                    break;
            }
        }

        if (organisationFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            roleFilter = roleFilter.append(" OR (SELECT COUNT(*) FROM organisation_user team_member WHERE o.uuid = team_member.organisation_id) = 0 ");
            advanceSearch.append(" AND (").append(convertSearchFieldService.convertToQuery(ObjectType.ACCOUNT.toString(), organisationFilterDTO.getSearchFieldDTOList(), enterprise)).append(")");
        }

        if (!ignoreOrderStatement)
        {
            switch (organisationFilterDTO.getOrderBy())
            {
                case "closedSales":
                    orderByStatement.append("ORDER BY q_search.order_intake DESC, q_search.name ");
                    break;
                case "numberActiveProspect":
                    orderByStatement.append("ORDER BY q_search.number_active_prospect DESC, q_search.name ");
                    break;
                case "numberTeamMember":
                    orderByStatement.append("ORDER BY q_search.number_team_member DESC, q_search.name ");
                    break;
                case "profit":
                    orderByStatement.append("ORDER BY q_search.won_profit DESC, q_search.name ");
                    break;
                case "grossPipe":
                    orderByStatement.append("ORDER BY q_search.gross_pipeline DESC, q_search.name ");
                    break;
                case "weightedPipe":
                    orderByStatement.append("ORDER BY q_search.net_pipeline DESC, q_search.name ");
                    break;
                case "nextMeeting":
                    orderByStatement.append("ORDER BY q_search.next_meeting_date ASC, q_search.name ");
                    isNextMeeting = true;
                    break;
                case "nextTask":
                    orderByStatement.append("ORDER BY q_search.next_task_date ASC,q_search.name ");
                    isNextMeeting = true;
                    break;
                case "name":
                    orderByStatement.append("ORDER BY q_search.name ASC");
                    break;
                case "Alphabetical":
                    orderByStatement.append("ORDER BY q_search.name ASC");
                    break;
                default:
                    orderByStatement.append("ORDER BY q_search.order_intake DESC, q_search.name ");
            }
        }
        String[] terms = organisationFilterDTO.getFtsTerms().toLowerCase().replaceAll("\'", "").replaceAll("[()]+", "").split(" ");

        ST st;
        if (organisationFilterDTO.getFtsTerms().length() == 0 && organisationFilterDTO.getSearchFieldDTOList().size() == 0)
        {
            if (StringUtils.isEmpty(cachedSearchOrganisationLiteQuery))
            {
                    Resource resource = resourceLoader.getResource("classpath:query_full_text_search_organisation_lite_new.sql");
                StringBuilder stringBuilder = readQuerySql(resource);
                cachedSearchOrganisationLiteQuery = stringBuilder.toString();
            }

            if (StringUtils.isEmpty(cachedSearchOrganisationLiteQueryCompanyLevel))
            {
                Resource resource = resourceLoader.getResource("classpath:query_full_text_search_organisation_lite_new_company_level.sql");
                StringBuilder stringBuilder = readQuerySql(resource);
                cachedSearchOrganisationLiteQueryCompanyLevel = stringBuilder.toString();
            }

            if (organisationFilterDTO.getRoleFilterType() != null && organisationFilterDTO.getRoleFilterType().equals("Company"))
            {
                st = new ST(cachedSearchOrganisationLiteQueryCompanyLevel);
            }
            else
            {
                st = new ST(cachedSearchOrganisationLiteQuery);
            }
        }
        else
        {
            roleFilter = roleFilter.append(" OR (SELECT COUNT(*) FROM organisation_user team_member WHERE o.uuid = team_member.organisation_id) = 0");
            if (StringUtils.isEmpty(cachedSearchOrganisationQuery))
            {
                Resource resource = resourceLoader.getResource("classpath:query_full_text_search_organisation_new.sql");
                StringBuilder stringBuilder = readQuerySql(resource);
                cachedSearchOrganisationQuery = stringBuilder.toString();
            }

            if (StringUtils.isEmpty(cachedSearchOrganisationQueryCompanyLevel))
            {
                Resource resource = resourceLoader.getResource("classpath:query_full_text_search_organisation_new_company_level.sql");
                StringBuilder stringBuilder = readQuerySql(resource);
                cachedSearchOrganisationQueryCompanyLevel = stringBuilder.toString();
            }


            if (organisationFilterDTO.getRoleFilterType() != null && organisationFilterDTO.getRoleFilterType().equals("Company"))
            {
                st = new ST(cachedSearchOrganisationQueryCompanyLevel);
            }
            else
            {
                st = new ST(cachedSearchOrganisationQuery);
            }
        }

//        if (!organisationFilterDTO.getRoleFilterType().equals("Company"))
//        {

        roleFilter = roleFilter.append(") ");
//        }

        String next_meeting = " ";
        String next_meeting_join = " ";
        if (isNextMeeting)
        {
            next_meeting = ",next_meeting_date,\n" + "next_task_date";
            next_meeting_join = "\t,(SELECT meeting_date.start_date\n" +
                    "     FROM appointment meeting_date WHERE o.uuid = meeting_date.organisation_id AND meeting_date.start_date > now() AND meeting_date.is_finished = false\n" +
                    "      and meeting_date.deleted = false ORDER BY meeting_date.start_date ASC LIMIT 1) as next_meeting_date,\n" +
                    "\n" +
                    "(SELECT task_date.date_and_time\n" +
                    " FROM task task_date WHERE task_date.organisation_id = o.uuid AND task_date.date_and_time > now() AND task_date.is_finished = false AND task_date.deleted = false\n" +
                    " ORDER BY task_date.date_and_time ASC LIMIT 1) as next_task_date";
        }

        //build like query
        // String queryTsAndLike = getQueryTsAndLike(organisationFilterDTO.getFtsTerms());
        String queryTsAndLike = fullTextSearchQueryBuilder(organisationFilterDTO.getFtsTerms(), false);

        //fill parameters to query
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("user_id", user.getUuid().toString());
        st.add("query_ts_and_like", queryTsAndLike);
        st.add("role_filter", roleFilter.toString());
        st.add("custom_filter", customFilter.toString());
        st.add("advance_search", advanceSearch.toString());
        st.add("order_by_statement", orderByStatement.toString());
        st.add("next_meeting", next_meeting);
        st.add("next_meeting_join", next_meeting_join);
        if (!isGetAll)
        {
            st.add("limit_and_offset", " LIMIT " + pageSize + " OFFSET " + pageIndex * pageSize);
        }

        String queryString = st.render();
        org.hibernate.Query newQuery = entityManager.unwrap(org.hibernate.Session.class)
                .createSQLQuery(queryString)
                .setCacheable(true)
                .setResultTransformer(Transformers.aliasToBean(OrganisationResultSet.class));

        return newQuery.list();
    }

    private StringBuilder readQuerySql(Resource resource) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
        {
            stringBuilder.append(line).append('\n');
        }
        br.close();
        return stringBuilder;
    }

    public String fullTextSearchQueryBuilder(String searchText, boolean advanceSearch)
    {
        String result = "";
        if (StringUtils.isNotEmpty(searchText))
        {
            FullTextSearchRule searchRule = FullTextSearchUtils.detectRule(searchText);
            List<String> searchAbleColumns;
            List<CommunicationType> additionalDatas = new ArrayList<>();
            switch (searchRule)
            {
                case ONLY_EMAIL:
                    searchAbleColumns = advanceSearch ? FULL_TEXT_SEARCH_AS_EMAIL_COLUMNS : FULL_TEXT_SEARCH_EMAIL_COLUMNS;
                    additionalDatas = Arrays.asList(CommunicationType.EMAIL_HEAD_QUARTER,
                            CommunicationType.EMAIL_SUBSIDIARY,
                            CommunicationType.EMAIL_DEPARTMENT,
                            CommunicationType.EMAIL_UNIT);
                    break;
                case ONLY_PHONE_NUMBER:
                    searchAbleColumns = advanceSearch ? FULL_TEXT_SEARCH_AS_PHONE_COLUMNS : FULL_TEXT_SEARCH_PHONE_COLUMNS;
                    searchText = FullTextSearchUtils.standardizedPhoneSearch(searchText);
                    additionalDatas = Arrays.asList(CommunicationType.PHONE_HEAD_QUARTER,
                            CommunicationType.PHONE_SUBSIDIARY,
                            CommunicationType.PHONE_DEPARTMENT,
                            CommunicationType.PHONE_UNIT);
                    break;
                case OTHER:
                default:
                    searchAbleColumns = advanceSearch ? FULL_TEXT_SEARCH_AS_NAME_COLUMNS :FULL_TEXT_SEARCH_NAME_COLUMNS;
            }
            List<String> queryParts = new ArrayList<>();
            //Special character with hibernate query builder
            String standardizedText = searchText.replaceAll("\'", "").replace(":", "\\:");
            final String finalStandardizedText = StringUtils.lowerCase(standardizedText).trim();
            queryParts.addAll(searchAbleColumns.stream()
                    .map(column -> String.format("LOWER(%s) LIKE '%%%s%%'", column, finalStandardizedText))
                    .collect(Collectors.toList()));
            String filterString = String.join(" OR ", queryParts);
            if (additionalDatas.size() > 0)
            {
                String strTypes = additionalDatas.stream().map(item -> String.valueOf(item.getExtension())).collect(Collectors.joining(", "));
                result = String.format(" AND co.type IN (%s) AND (%s)", strTypes, filterString);
            }
            else
            {
                result = String.format(" AND (%s)", filterString);
            }
        }
        return result;
    }

    private static final List<String> FULL_TEXT_SEARCH_EMAIL_COLUMNS = Arrays.asList("co.value");
    private static final List<String> FULL_TEXT_SEARCH_PHONE_COLUMNS = Arrays.asList(String.format(FullTextSearchUtils.PHONE_REGEX_FOR_REMOVE, "co.value"));
    private static final List<String> FULL_TEXT_SEARCH_NAME_COLUMNS = Arrays.asList("o.name");

    private static final List<String> FULL_TEXT_SEARCH_AS_EMAIL_COLUMNS = Arrays.asList("q_search.email");
    private static final List<String> FULL_TEXT_SEARCH_AS_PHONE_COLUMNS = Arrays.asList(String.format(FullTextSearchUtils.PHONE_REGEX_FOR_REMOVE, "q_search.phone"));
    private static final List<String> FULL_TEXT_SEARCH_AS_NAME_COLUMNS = Arrays.asList("q_search.name");

}