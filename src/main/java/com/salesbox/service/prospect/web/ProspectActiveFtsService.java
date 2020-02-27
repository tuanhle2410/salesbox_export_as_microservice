package com.salesbox.service.prospect.web;

import com.salesbox.advancesearch.service.ConvertSearchFieldService;
import com.salesbox.dao.ProspectActiveDAO;
import com.salesbox.dto.ProspectResultSet;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.prospect.dto.CountProspectDTO;
import com.salesbox.prospect.dto.ProspectDTO;
import com.salesbox.prospect.dto.ProspectFilterDTO;
import com.salesbox.prospect.dto.ProspectListDTO;
import com.salesbox.utils.CustomFieldUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
import java.math.BigInteger;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProspectActiveFtsService
{
    @Value("${host.timezone}")
    private String timeZone;
    @Autowired
    ProspectWebFtsUtils prospectWebFtsUtils;
    @Autowired
    ConvertSearchFieldService convertSearchFieldService;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ProspectActiveDAO prospectActiveDAO;


    private String cachedActiveProspectFtsQuery;
    private String cachedCountingActiveProspectFtsQuery;

    private String advance_search_join = " LEFT JOIN order_row ord ON ord.prospect_id = p.uuid\n" +
            "  LEFT JOIN measurement_type met ON met.uuid = ord.type_id\n" +
            "  LEFT JOIN product prod ON prod.uuid = ord.product_id ";

    private String netValueSelectQuery = " ,  CASE\n" +
            "  WHEN (p.number_active_task  = 0 AND p.number_active_meeting = 0)\n" +
            "    THEN ((((p.prospect_progress * ((100 - sm.lose_meeting_ratio) / 100.0))) * p.gross_value) / 100)\n" +
            "  ELSE ((p.prospect_progress * p.gross_value) / 100)\n" +
            "  END AS net_value" +
            "";

    public ProspectListDTO getProspectListDTO(ProspectFilterDTO prospectFilterDTO, Integer pageIndex, Integer pageSize, User user, Enterprise enterprise) throws IOException
    {
        Pageable pageable = new PageRequest(pageIndex, pageSize);

        String roleFilter = prospectWebFtsUtils.getRoleFilter(prospectFilterDTO);
        String orderByStatement = prospectWebFtsUtils.getOrderByActive(prospectFilterDTO);
        String queryTsAndLike = prospectWebFtsUtils.getQueryTsAndLikeActive(prospectFilterDTO);
        String salesProcessIds = prospectWebFtsUtils.getSalesProcessStatement(prospectFilterDTO);

        System.out.println(salesProcessIds);

        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append(", CASE WHEN (p.owner_id='" + user.getUuid().toString() + "') THEN 1 ELSE 0 END AS _owner_id");

        ST st;
        if (StringUtils.isEmpty(cachedActiveProspectFtsQuery))
        {
            Resource resource = resourceLoader.getResource("classpath:new/query_list_prospect_detail_active_new.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
            br.close();
            cachedActiveProspectFtsQuery = stringBuilder.toString();
        }
        String advanceSearch = "", advanceSearchJoin = "";
        if (prospectFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearchJoin = advance_search_join;
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.OPPORTUNITY.toString(), prospectFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }

        //time filter
        String timeFilter = "";
        if (!prospectFilterDTO.getIsFilterAll())
        {
            timeFilter = "AND p.contract_date  BETWEEN" + "'" + new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'" + " AND " + "'" + new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'";
        }

        st = new ST(cachedActiveProspectFtsQuery);


//        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("advance_search_join", advanceSearchJoin);
        st.add("select_more", prospectFilterDTO.getOrderBy().equals("netValue") ?  netValueSelectQuery : "");
        st.add("advance_search", advanceSearch);
        st.add("role_filter", roleFilter);
        st.add("order_by_statement", orderByStatement);
        st.add("enterpriseId", enterprise.getUuid().toString());
//        st.add("start_date", new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("time_filter",timeFilter);
        st.add("repeat_query_ts_and_like", queryTsAndLike);
        st.add("sales_method_query", salesProcessIds);
        st.add("limit_and_offset", " LIMIT " + pageable.getPageSize() + " OFFSET " + (pageable.getPageNumber() * pageable.getPageSize()));


        List<ProspectResultSet> listProspect = prospectActiveDAO.findProspectActive(st.render());
        return prospectWebFtsUtils.getProspectDTOList(listProspect, prospectFilterDTO.getOrderBy(), true);
    }
    
    public List<ProspectResultSet> getProspectListDTO_NoPagination(ProspectFilterDTO prospectFilterDTO, User user, Enterprise enterprise) throws IOException
    {
    	String roleFilter = prospectWebFtsUtils.getRoleFilter(prospectFilterDTO);
    	String orderByStatement = prospectWebFtsUtils.getOrderByActive(prospectFilterDTO);
    	String queryTsAndLike = prospectWebFtsUtils.getQueryTsAndLikeActive(prospectFilterDTO);
        String salesProcessIds = prospectWebFtsUtils.getSalesProcessStatement(prospectFilterDTO);

        StringBuilder selectStatement = new StringBuilder();
    	selectStatement.append(", CASE WHEN (p.owner_id='" + user.getUuid().toString() + "') THEN 1 ELSE 0 END AS _owner_id");
    	
    	ST st;
    	if (StringUtils.isEmpty(cachedActiveProspectFtsQuery))
    	{
    		Resource resource = resourceLoader.getResource("classpath:new/query_list_prospect_detail_active_new_nopagination.sql");
    		BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
    		StringBuilder stringBuilder = new StringBuilder();
    		String line;
    		while ((line = br.readLine()) != null)
    		{
    			stringBuilder.append(line).append('\n');
    		}
    		br.close();
    		cachedActiveProspectFtsQuery = stringBuilder.toString();
    	}
    	String advanceSearch = "", advanceSearchJoin = "";
    	if (prospectFilterDTO.getSearchFieldDTOList().size() > 0)
    	{
    		advanceSearchJoin = advance_search_join;
    		advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.OPPORTUNITY.toString(), prospectFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
    	}
        //time filter
        String timeFilter = "";
        if (!prospectFilterDTO.getIsFilterAll())
        {
            timeFilter = "AND p.contract_date  BETWEEN" + "'" + new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'" + " AND " + "'" + new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'";
        }

    	st = new ST(cachedActiveProspectFtsQuery);
    	
    	
//        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
    	st.add("advance_search_join", advanceSearchJoin);
    	st.add("select_more", prospectFilterDTO.getOrderBy().equals("netValue") ?  netValueSelectQuery : "");
    	st.add("advance_search", advanceSearch);
    	st.add("role_filter", roleFilter);
    	st.add("order_by_statement", orderByStatement);
    	st.add("enterpriseId", enterprise.getUuid().toString());
//    	st.add("start_date", new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//    	st.add("end_date", new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("time_filter",timeFilter);
    	st.add("repeat_query_ts_and_like", queryTsAndLike);
        st.add("sales_method_query", salesProcessIds);
    	
    	List<ProspectResultSet> listProspect = prospectActiveDAO.findProspectActive(st.render());
    	return listProspect;
    }


    //no longer use
    public ProspectListDTO getProspectListDTOOld(ProspectFilterDTO prospectFilterDTO, Integer pageIndex, Integer pageSize, User user, Enterprise enterprise) throws IOException
    {

        List<Object[]> prospectProperties = getProperties(prospectFilterDTO, pageIndex, pageSize, user, enterprise);

        ProspectListDTO prospectListDTO = new ProspectListDTO();
        for (Object[] objects : prospectProperties)
        {
            if (!prospectWebFtsUtils.isExisted(objects, prospectListDTO))
            {
                ProspectDTO prospectDTO = prospectWebFtsUtils.populateProspectActiveDTO(objects);
                prospectListDTO.getProspectDTOList().add(prospectDTO);
            }
        }
        return prospectListDTO;

    }


    //still use for export AS
    public List<Object[]> getProperties(ProspectFilterDTO prospectFilterDTO, Integer pageIndex, Integer pageSize, User user, Enterprise enterprise) throws IOException
    {
        Pageable pageable = new PageRequest(pageIndex, pageSize);

        String roleFilter = prospectWebFtsUtils.getRoleFilter(prospectFilterDTO);
        String orderByStatement = prospectWebFtsUtils.getOrderByActive(prospectFilterDTO);
        String queryTsAndLike = prospectWebFtsUtils.getQueryTsAndLikeActive(prospectFilterDTO);
        String salesProcessIds = prospectWebFtsUtils.getSalesProcessStatement(prospectFilterDTO);

        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append(", CASE WHEN (p.owner_id='" + user.getUuid().toString() + "') THEN 1 ELSE 0 END AS _owner_id");

        ST st;
        if (StringUtils.isEmpty(cachedActiveProspectFtsQuery))
        {
            Resource resource = new ClassPathResource("new/query_list_prospect_detail_active.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
            br.close();
            cachedActiveProspectFtsQuery = stringBuilder.toString();
        }
        String advanceSearch = "", advanceSearchJoin = "";
        if (prospectFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearchJoin = advance_search_join;
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.OPPORTUNITY.toString(), prospectFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }
        st = new ST(cachedActiveProspectFtsQuery);

        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("select_statement", selectStatement);
        st.add("advance_search_join", advanceSearchJoin);
        st.add("advance_search", advanceSearch);
        st.add("role_filter", roleFilter);
        st.add("order_by_statement", orderByStatement);
        st.add("enterpriseId", enterprise.getUuid().toString());
        st.add("start_date", new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("end_date", new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());
        st.add("sales_method_query", salesProcessIds);
        st.add("limit_and_offset", " LIMIT " + pageable.getPageSize() + " OFFSET " + (pageable.getPageNumber() * pageable.getPageSize()));

        Query query = entityManager.createNativeQuery(st.render());
        List<Object[]> prospectProperties = query.getResultList();
        return prospectProperties;
    }

    public CountProspectDTO countActiveProspect(ProspectFilterDTO prospectFilterDTO, User user, Enterprise enterprise) throws IOException
    {
        CountProspectDTO countProspectDTO = new CountProspectDTO();

        String roleFilter = prospectWebFtsUtils.getRoleFilter(prospectFilterDTO);
        String queryTsAndLike = prospectWebFtsUtils.getQueryTsAndLikeActive(prospectFilterDTO);

        String salesProcessIds = prospectWebFtsUtils.getSalesProcessStatement(prospectFilterDTO);
        //time filter
        String timeFilter = "";
        if (!prospectFilterDTO.getIsFilterAll())
        {
            timeFilter = "AND p.contract_date  BETWEEN" + "'" + new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'" + " AND " + "'" + new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) + "'";
        }
        ST st;

        if (StringUtils.isEmpty(cachedCountingActiveProspectFtsQuery))
        {
            Resource resource = new ClassPathResource("new/count_list_prospect_detail_active.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
            br.close();
            cachedCountingActiveProspectFtsQuery = stringBuilder.toString();
        }
        String advanceSearch = "", advanceSearchJoin = "";
        if (prospectFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearchJoin = advance_search_join;
            advanceSearch = " AND (" + convertSearchFieldService.convertToQuery(ObjectType.OPPORTUNITY.toString(), prospectFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }
        st = new ST(cachedCountingActiveProspectFtsQuery);

        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("role_filter", roleFilter);
        st.add("advance_search_join", advanceSearchJoin);
        st.add("advance_search", advanceSearch);
        st.add("enterpriseId", enterprise.getUuid().toString());
//        st.add("start_date", new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("time_filter",timeFilter);
        st.add("sales_method_query", salesProcessIds);
        st.add("repeat_query_ts_and_like", queryTsAndLike);
        Query query = entityManager.createNativeQuery(st.render());
        List<Object[]> objects = query.getResultList();

        countProspectDTO.setCount(((BigInteger) objects.get(0)[0]).longValue());
        if(objects.get(0)[1] != null)
        {
            countProspectDTO.setTotalGrossValue(Double.parseDouble(objects.get(0)[1].toString()));
        } else {
            countProspectDTO.setTotalGrossValue((double) 0);
        }

        if(objects.get(0)[2] != null)
        {
            countProspectDTO.setTotalWeight(Double.parseDouble(objects.get(0)[2].toString()));
        } else {
            countProspectDTO.setTotalWeight((double) 0);
        }

        return countProspectDTO;
    }
}
