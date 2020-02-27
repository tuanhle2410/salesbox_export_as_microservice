package com.salesbox.service.contact;

import com.salesbox.advancesearch.dto.SearchFieldDTO;
import com.salesbox.advancesearch.dto.fields.ContactField;
import com.salesbox.advancesearch.service.ConvertSearchFieldService;
import com.salesbox.common.BaseService;
import com.salesbox.common.enums.FullTextSearchRule;
import com.salesbox.dao.ContactDAO;
import com.salesbox.dao.WorkDataOrganisationDAO;
import com.salesbox.dto.ContactFilterDTO;
import com.salesbox.dto.ContactResultSet;
import com.salesbox.dto.CountDTO;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.CommunicationType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.exception.ServiceException;
import com.salesbox.utils.CustomFieldUtils;
import com.salesbox.utils.FullTextSearchUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
 * Created by tuantx on 3/28/2016.
 */
@Service
@Transactional
public class ContactSearchService extends BaseService
{
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    ContactDAO contactDAO;
    @Autowired
    ConvertSearchFieldService convertSearchFieldService;
    @Autowired
    WorkDataOrganisationDAO workDataOrganisationDAO;
    @Autowired
    ResourceLoader resourceLoader;

    public String cachedSearchContactLiteQuery;
    public String cachedSearchContactQuery;

    public static final Logger logger = Logger.getLogger(ContactSearchService.class);

    public List<UUID> getUUIDList(String token, ContactFilterDTO contactFilterDTO)
            throws IOException, IllegalAccessException, ServiceException
    {

        List<ContactResultSet> contactList = findContactFts_NoPagination(token, contactFilterDTO);


        if (contactList.size() == 0)
        {
            return new ArrayList<>();
        }
        Set<UUID> uuidSet = new HashSet<>();
        for (ContactResultSet organisationResultSet : contactList)
        {
            uuidSet.add(organisationResultSet.getUuid());
        }

        return new ArrayList<>(uuidSet);
    }

    public List<ContactResultSet> findContactFts_NoPagination(String token, ContactFilterDTO contactFilterDTO)
            throws IOException, IllegalAccessException, ServiceException
    {
        User user = getUserFromToken(token);
        Enterprise enterprise = user.getUnit().getEnterprise();
        Resource resource;
        if (contactFilterDTO.getSearchText().length() == 0 && contactFilterDTO.getSearchFieldDTOList().size() == 0)
        {
            resource = new ClassPathResource("contact-web/query_full_text_search_contact_lite_nopagination.sql");
        }
        else
        {
            resource = new ClassPathResource("contact-web/query_full_text_search_contact_nopagination.sql");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
        {
            stringBuilder.append(line).append('\n');
        }
        br.close();
        String cachedScriptQuery = stringBuilder.toString();

        String[] terms = contactFilterDTO.getSearchText().toLowerCase().replaceAll("\'", "").replaceAll("[()]+", "").split(" ");
        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(contactFilterDTO.getSearchText()))
        {
            StringBuilder likeQuery = new StringBuilder();
            for (int i = 0; i < terms.length - 1; i++)
            {
                likeQuery.append(terms[i] + ":* & ");
            }
            likeQuery.append(terms[terms.length - 1] + ":*");
            //if it is not a lexeme for full text search, ignore it
            Query query = entityManager.createNativeQuery("select (to_tsquery('" + likeQuery.toString() + "')  !=  '')");
            Boolean isValid = (Boolean) query.getSingleResult();
            if (isValid)
            {
                queryTsAndLike.append("and q_search.qq_document @@ to_tsquery('" + likeQuery.toString() + "') ");
            }
        }


        String roleFilter = " AND q_search.enterprise_id = '" + enterprise.getUuid().toString() + "' ";

        if (contactFilterDTO.getRoleFilterType() != null)
        {
            switch (contactFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += "AND ( q_search.user_id = '" + contactFilterDTO.getRoleFilterValue() + "' OR (SELECT COUNT(*) FROM contact_user team_member  WHERE q_search.uuid = team_member.contact_id) = 0 ) ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += "AND ( q_search.unit_id = '" + contactFilterDTO.getRoleFilterValue() + "' OR (SELECT COUNT(*) FROM contact_user team_member  WHERE q_search.uuid = team_member.contact_id) = 0 ) ";
                    break;
            }
        }


        String customFilter = "";
        if (contactFilterDTO.getCustomFilter() != null)
        {
            switch (contactFilterDTO.getCustomFilter())
            {
                case "favorite":
                    customFilter = " AND q_search.is_favorite ";
                    break;
                case "recent":
                    customFilter = " AND q_search.last_viewed IS NOT NULL ";
                    break;
                case "active":
                default:
                    customFilter = "";
            }
        }

        String advanceSearch = "";
        if (contactFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.CONTACT.toString(), contactFilterDTO.getSearchFieldDTOList(), enterprise) + ")";
        }

        ST st = new ST(cachedScriptQuery);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("user_id", user.getUuid().toString());
        st.add("enterprise_id", enterprise.getUuid().toString());
        st.add("role_filter", roleFilter);
        st.add("custom_filter", customFilter);
        st.add("advance_search", advanceSearch);
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());

        org.hibernate.Query newQuery = entityManager.unwrap(org.hibernate.Session.class).createSQLQuery(st.render())
                .setCacheable(true).setResultTransformer(Transformers.aliasToBean(ContactResultSet.class));

        return newQuery.list();
    }

    public CountDTO countRecords(String token, ContactFilterDTO contactFilterDTO, String sessionKey) throws IOException
    {
        User user = getUserFromToken(token);
        Enterprise enterprise = user.getUnit().getEnterprise();

        Resource resource;
        if (contactFilterDTO.getSearchText().length() == 0 && contactFilterDTO.getSearchFieldDTOList().size() == 0)
        {
            resource = new ClassPathResource("contact-web/query_count_full_text_search_contact_lite.sql");
        }
        else
        {
            resource = new ClassPathResource("contact-web/query_count_full_text_search_contact.sql");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
        {
            stringBuilder.append(line).append('\n');
        }
        br.close();
        String cachedCountQuery = stringBuilder.toString();


        //TODO: Narrow filter area's by client request - Support five columns follow by FULL_TEXT_SEARCH_COLUMNS
        /*String[] terms = contactFilterDTO.getSearchText().toLowerCase().replaceAll("\'", "").replaceAll("[()]+", "").split(" ");
        StringBuilder queryTsAndLike = new StringBuilder();
        if (StringUtils.isNotEmpty(contactFilterDTO.getSearchText()))
        {
            StringBuilder likeQuery = new StringBuilder();
            for (int i = 0; i < terms.length - 1; i++)
            {
                likeQuery.append(terms[i] + ":* & ");
            }
            likeQuery.append(terms[terms.length - 1] + ":*");
            //if it is not a lexeme for full text search, ignore it
            Query query = entityManager.createNativeQuery("select (to_tsquery('" + likeQuery.toString() + "')  !=  '')");
            Boolean isValid = (Boolean) query.getSingleResult();
            if (isValid)
            {
                queryTsAndLike.append("and q_search.qq_document @@ to_tsquery('" + likeQuery.toString() + "') ");
            }
        }*/

        String fullTextSearchFilter = fullTextSearchQueryBuilder(contactFilterDTO.getSearchText());

        String roleFilter = " AND q_search.enterprise_id = '" + enterprise.getUuid().toString() + "' ";

        if (contactFilterDTO.getRoleFilterType() != null)
        {
            switch (contactFilterDTO.getRoleFilterType())
            {
                case "Person": // For Person, using UserId
                    roleFilter += "AND ( q_search.user_id = '" + contactFilterDTO.getRoleFilterValue() + "' ) ";
                    break;
                case "Unit": // This case for Unit, using UnitId
                    roleFilter += "AND ( q_search.unit_id = '" + contactFilterDTO.getRoleFilterValue() + "' ) ";
                    break;
            }
        }

        //custom filter parameter
        String customFilter = "";

        if (contactFilterDTO.getCustomFilter() != null)
        {
            switch (contactFilterDTO.getCustomFilter())
            {
                case "favorite":
                    customFilter = " AND q_search.is_favorite ";
                    break;
                case "recent":
                    customFilter = " AND q_search.last_viewed IS NOT NULL ";
                    break;
                case "active":
                default:
                    customFilter = "";
                    // Default is active
            }
        }

        String advanceSearch = "";

        String moreFields = "";
        String moreJoins = "";
        String moreGroupBys = "";
        if (contactFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.CONTACT.toString(), contactFilterDTO.getSearchFieldDTOList(), enterprise) + ")";

            for (SearchFieldDTO[] searchFieldDTOArr : contactFilterDTO.getSearchFieldDTOList())
            {
                for (SearchFieldDTO aSearchFieldDTOArr : searchFieldDTOArr)
                {
                    if (aSearchFieldDTOArr.getField().equals(ContactField.EMAIL_OPEN) || aSearchFieldDTOArr.getField().equals(ContactField.EMAIL_CLICK_ON_URL))
                    {
                        moreFields = " ,ch.receive_date as ch_receive_date, ch.receive_url_date as ch_receive_url_date ";
                        moreJoins = " LEFT JOIN communication_history ch on c.uuid = ch.contact_id ";
                        moreGroupBys = ",ch.receive_date, ch.receive_url_date ";
                        break;
                    }
                }
            }
        }

//        roleFilter +=  "' ) ";

        //fill the params into the template
        ST st = new ST(cachedCountQuery);
        st.add("more_field", moreFields);
        st.add("more_join", moreJoins);
        st.add("more_groupby", moreGroupBys);
        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("user_id", user.getUuid().toString());
        st.add("enterprise_id", enterprise.getUuid().toString());
        st.add("role_filter", roleFilter);
        st.add("custom_filter", customFilter);
        st.add("advance_search", advanceSearch);
//        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());
        st.add("repeat_query_ts_and_like", fullTextSearchFilter);

        Query query = entityManager.createNativeQuery(st.render());

        return new CountDTO(sessionKey, Long.parseLong(query.getSingleResult().toString()));

    }

    private String fullTextSearchQueryBuilder(String searchText)
    {
        String result = "";
        List<CommunicationType> additionalDatas = new ArrayList<>();
        if (StringUtils.isNotEmpty(searchText))
        {
            FullTextSearchRule searchRule = FullTextSearchUtils.detectRule(searchText);
            List<String> searchAbleColumns;
            switch (searchRule)
            {
                case ONLY_EMAIL:
                    searchAbleColumns = FULL_TEXT_SEARCH_EMAIL_COLUMNS;
                    additionalDatas = Arrays.asList(CommunicationType.EMAIL_HOME,
                            CommunicationType.EMAIL_WORK,
                            CommunicationType.EMAIL_ICLOUD,
                            CommunicationType.EMAIL_OTHER);
                    break;
                case ONLY_PHONE_NUMBER:
                    searchAbleColumns = FULL_TEXT_SEARCH_PHONE_COLUMNS;
                    searchText = FullTextSearchUtils.standardizedPhoneSearch(searchText);
                    additionalDatas = Arrays.asList(CommunicationType.PHONE_HOME,
                            CommunicationType.PHONE_WORK,
                            CommunicationType.PHONE_IPHONE,
                            CommunicationType.PHONE_MOBILE,
                            CommunicationType.PHONE_MAIN,
                            CommunicationType.PHONE_HOME_FAX,
                            CommunicationType.PHONE_WORK_FAX,
                            CommunicationType.PHONE_OTHER);
                    break;
                case OTHER:
                default:
                    searchAbleColumns = FULL_TEXT_SEARCH_NAME_COLUMNS;
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
                result = String.format(" AND q_search.com_type IN (%s) AND (%s)", strTypes, filterString);
            }
            else
            {
                result = String.format(" AND (%s)", filterString);
            }
        }
        return result;
    }

    //TODO: Replace first_name and last_name by full_name_lower_case to reduce 2 criteria
    private static final List<String> FULL_TEXT_SEARCH_EMAIL_COLUMNS = Arrays.asList("q_search.com_value");
    private static final List<String> FULL_TEXT_SEARCH_PHONE_COLUMNS = Arrays.asList(String.format(FullTextSearchUtils.PHONE_REGEX_FOR_REMOVE, "q_search.com_value"));
    private static final List<String> FULL_TEXT_SEARCH_NAME_COLUMNS = Arrays.asList("q_search.o_name", "CONCAT(q_search.first_name, ' ', q_search.last_name)");
}