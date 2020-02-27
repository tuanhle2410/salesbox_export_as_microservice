package com.salesbox.service.prospect.web;

import com.salesbox.advancesearch.service.ConvertSearchFieldService;
import com.salesbox.dao.ProspectActiveDAO;
import com.salesbox.dto.ProspectResultSet;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.entity.enums.ProspectHistoricStatus;
import com.salesbox.prospect.dto.CountProspectDTO;
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
public class ProspectHistoricFtsService
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

    private String cachedHistoricProspectFtsQuery;
    private String cachedCountingHistoricProspectFtsQuery;
    private Boolean cachedAdvanceSearch = false;
    private Boolean cachedAdvanceSearchCount = false;

    private String advance_search_join = " LEFT JOIN organisation o ON p.organisation_id = o.uuid\n" +
            " LEFT JOIN contact c ON p.power_sponsor_id = c.uuid " +
            " LEFT JOIN order_row ord ON ord.prospect_id = p.uuid\n" +
            "  LEFT JOIN measurement_type met ON met.uuid = ord.type_id\n" +
            "  LEFT JOIN product prod ON prod.uuid = ord.product_id ";

    public ProspectListDTO getProspectListDTO(ProspectFilterDTO prospectFilterDTO, Integer pageIndex, Integer pageSize, User user, Enterprise enterprise) throws IOException
    {

        Pageable pageable = new PageRequest(pageIndex, pageSize);

        String roleFilter = prospectWebFtsUtils.getRoleFilter(prospectFilterDTO);
        String orderByStatement = prospectWebFtsUtils.getOrderByHistoric(prospectFilterDTO);
        String queryTsAndLike = prospectWebFtsUtils.getQueryTsAndLikeHistoric(prospectFilterDTO);
        String salesProcessIds = prospectWebFtsUtils.getSalesProcessStatement(prospectFilterDTO);


        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append(", CASE WHEN (p.owner_id='" + user.getUuid().toString() + "') THEN 1 ELSE 0 END AS _owner_id");

        ST st;
        Resource resource;
        if (prospectFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            if (StringUtils.isEmpty(cachedHistoricProspectFtsQuery) || cachedAdvanceSearch == false)
            {
                resource = resourceLoader.getResource("classpath:new/query_list_prospect_detail_historic_advance_search.sql");
                BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                {
                    stringBuilder.append(line).append('\n');
                }
                br.close();
                cachedHistoricProspectFtsQuery = stringBuilder.toString();
            }
            cachedAdvanceSearch = true;
        }
        else
        {
            if (StringUtils.isEmpty(cachedHistoricProspectFtsQuery) || cachedAdvanceSearch)
            {
                resource = resourceLoader.getResource("classpath:new/query_list_prospect_detail_historic_new.sql");
                BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                {
                    stringBuilder.append(line).append('\n');
                }
                br.close();
                cachedHistoricProspectFtsQuery = stringBuilder.toString();
            }
            cachedAdvanceSearch = false;
        }
        String advanceSearch = "", advanceSearchJoin = "";
        if (prospectFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearchJoin = advance_search_join;
            advanceSearch = " AND (" + convertSearchFieldService.convertToQueryProspectHistoric(ObjectType.OPPORTUNITY.toString(), prospectFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }
        //time filter all
        String timeFilter = "";
        if (!prospectFilterDTO.getIsFilterAll())
        {
            timeFilter = " AND p.won_lost_date  BETWEEN '"+new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) +"' AND '"+new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone)+"'";
        }
        StringBuilder wonLossFilter = new StringBuilder("");
        if(prospectFilterDTO.getWonLossFilter() != null && !prospectFilterDTO.getWonLossFilter().equals(ProspectHistoricStatus.ALL))
        {
             wonLossFilter.append(" AND p.won = ");
            if(prospectFilterDTO.getWonLossFilter().equals(ProspectHistoricStatus.WON))
            {
                wonLossFilter.append("TRUE");
            }else if(prospectFilterDTO.getWonLossFilter().equals(ProspectHistoricStatus.LOSS)){
                wonLossFilter.append("FALSE");
            }
        }

        //end filter all
        {
            st = new ST(cachedHistoricProspectFtsQuery);
        }

        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("select_statement", selectStatement);
        st.add("advance_search_join", advanceSearchJoin);
        st.add("advance_search", advanceSearch);
        st.add("role_filter", roleFilter);
        st.add("order_by_statement", orderByStatement);
        st.add("enterpriseId", enterprise.getUuid().toString());
//        st.add("start_date", new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("time_filter",timeFilter);
        st.add("won_loss_filter",wonLossFilter);
        st.add("repeat_query_ts_and_like", queryTsAndLike);
        st.add("sales_method_query", salesProcessIds);
        st.add("limit_and_offset", " LIMIT " + pageable.getPageSize() + " OFFSET " + (pageable.getPageNumber() * pageable.getPageSize()));

        List<ProspectResultSet> listProspect = prospectActiveDAO.findProspectActive(st.render());
        return prospectWebFtsUtils.getProspectDTOList(listProspect, prospectFilterDTO.getOrderBy(), false);
    }


    public CountProspectDTO countHistoricProspect(String token, ProspectFilterDTO prospectFilterDTO, User user, Enterprise enterprise) throws IOException
    {
        CountProspectDTO countProspectDTO = new CountProspectDTO();

        String roleFilter = prospectWebFtsUtils.getRoleFilter(prospectFilterDTO);
        String queryTsAndLike = prospectWebFtsUtils.getQueryTsAndLikeHistoric(prospectFilterDTO);
        String salesProcessIds = prospectWebFtsUtils.getSalesProcessStatement(prospectFilterDTO);

        ST st;
        Resource resource;
        if (prospectFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            if (StringUtils.isEmpty(cachedCountingHistoricProspectFtsQuery) || cachedAdvanceSearchCount == false)
            {
                resource = new ClassPathResource("new/count_list_prospect_detail_historic_advance_search.sql");
                BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                {
                    stringBuilder.append(line).append('\n');
                }
                br.close();
                cachedCountingHistoricProspectFtsQuery = stringBuilder.toString();
            }
            cachedAdvanceSearchCount = true;
        }
        else
        {
            if (StringUtils.isEmpty(cachedCountingHistoricProspectFtsQuery) || cachedAdvanceSearchCount == true)
            {
                resource = new ClassPathResource("new/count_list_prospect_detail_historic.sql");
                BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                {
                    stringBuilder.append(line).append('\n');
                }
                br.close();
                cachedCountingHistoricProspectFtsQuery = stringBuilder.toString();
            }
            cachedAdvanceSearchCount = false;
        }

        String advanceSearch = "", advanceSearchJoin = "";
        if (prospectFilterDTO.getSearchFieldDTOList().size() > 0)
        {
            advanceSearchJoin = advance_search_join;
            advanceSearch = " AND (" + convertSearchFieldService.convertToQueryProspectHistoric(ObjectType.OPPORTUNITY.toString(), prospectFilterDTO.getSearchFieldDTOList(),enterprise) + ")";
        }
        //time filter all
        String timeFilter = "";
        if (!prospectFilterDTO.getIsFilterAll())
        {
            timeFilter = " AND p.won_lost_date  BETWEEN '"+new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone) +"' AND '"+new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone)+"'";
        }

        StringBuilder wonLossFilter = new StringBuilder("");
        if(prospectFilterDTO.getWonLossFilter() != null && !prospectFilterDTO.getWonLossFilter().equals(ProspectHistoricStatus.ALL))
        {
            wonLossFilter.append(" AND p.won = ");
            if(prospectFilterDTO.getWonLossFilter().equals(ProspectHistoricStatus.WON))
            {
                wonLossFilter.append("TRUE");
            }else if(prospectFilterDTO.getWonLossFilter().equals(ProspectHistoricStatus.LOSS)) {
                wonLossFilter.append("FALSE");
            }
        }

        st = new ST(cachedCountingHistoricProspectFtsQuery);

        st.add("searchable_custom_field_value_query", CustomFieldUtils.querySearchCustomFieldValue());
        st.add("role_filter", roleFilter);
        st.add("advance_search_join", advanceSearchJoin);
        st.add("advance_search", advanceSearch);
        st.add("enterpriseId", enterprise.getUuid().toString());
//        st.add("start_date", new DateTime(prospectFilterDTO.getStartDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
//        st.add("end_date", new DateTime(prospectFilterDTO.getEndDate(), DateTimeZone.UTC).toString("yyyy-MM-dd HH:mm:ss" + timeZone));
        st.add("time_filter",timeFilter);
        st.add("won_loss_filter",wonLossFilter);
        st.add("repeat_query_ts_and_like", queryTsAndLike.toString());
        st.add("sales_method_query", salesProcessIds.toString());
        Query query = entityManager.createNativeQuery(st.render());
        List<Object[]> objects = query.getResultList();


        countProspectDTO.setCount(((BigInteger) objects.get(0)[0]).longValue());

        countProspectDTO.setCount(((BigInteger) objects.get(0)[0]).longValue());
        if(objects.get(0)[1] != null)
        {
            countProspectDTO.setTotalGrossValue(Double.parseDouble(objects.get(0)[1].toString()));
        } else {
            countProspectDTO.setTotalGrossValue((double) 0);
        }

        if(objects.get(0)[2] != null)
        {
            countProspectDTO.setTotalProfit(Double.parseDouble(objects.get(0)[2].toString()));
        } else {
            countProspectDTO.setTotalProfit((double) 0);
        }

        return countProspectDTO;
    }
}
