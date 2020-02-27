package com.salesbox.advancesearch.service;

import com.salesbox.advancesearch.dto.FieldType;
import com.salesbox.advancesearch.dto.Operator;
import com.salesbox.advancesearch.dto.SearchFieldDTO;
import com.salesbox.advancesearch.dto.fields.*;
import com.salesbox.dao.CustomFieldDAO;
import com.salesbox.entity.CustomField;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.enums.CampaignStatus;
import com.salesbox.entity.enums.CustomFieldType;
import com.salesbox.entity.enums.ObjectType;
import com.salesbox.entity.enums.SourceType;
import com.salesbox.utils.CustomFieldUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.salesbox.advancesearch.dto.Operator.*;
import static com.salesbox.advancesearch.dto.Operator.NO_VALUE;

/**
 * Created by HUNGLV2 on 2/23/2017.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class
ConvertSearchFieldService
{
    private static final Map<String, String> operatorMap;
    private static final String DEFAULT_DASHBOARD_OPERATOR = "AND";
    @Autowired
    CustomFieldDAO customFieldDAO;

    static
    {
        operatorMap = new HashMap<String, String>();
        operatorMap.put(Operator.CONTAINS.toString(), "LIKE");
        operatorMap.put(Operator.NOT_CONTAINS.toString(), "NOT LIKE");
        operatorMap.put(Operator.EQUALS.toString(), "=");
        operatorMap.put(Operator.NOT_EQUALS.toString(), "!=");
        operatorMap.put(Operator.LESS.toString(), "<");
        operatorMap.put(Operator.LESS_OR_EQUALS.toString(), "<=");
        operatorMap.put(Operator.GREATER.toString(), ">");
        operatorMap.put(Operator.GREATER_OR_EQUALS.toString(), ">=");
        operatorMap.put(Operator.NO_VALUE.toString(), "NO_VALUE");
    }


    public String buildNoValueCustomFieldAccountContact(SearchFieldDTO searchFieldDTO)
    {
        String addedQueries = "";
        addedQueries += " \nNOT EXISTS ( ";
        String cfSearchQuery = CustomFieldUtils.querySearchCustomFieldValue();
        cfSearchQuery += "AND v.object_id = q_search.uuid ";
        cfSearchQuery += "AND cf.uuid = '" + searchFieldDTO.getCustomFieldId() + "' ) ";

        addedQueries += cfSearchQuery;

        return addedQueries;
    }

    public String buildContainCustomFieldAccountContact(SearchFieldDTO searchFieldDTO)
    {
        String addedQueries = "";
        addedQueries += " \nEXISTS ( ";
        String cfSearchQuery = CustomFieldUtils.querySearchCustomFieldValue();
        cfSearchQuery += "AND v.object_id = q_search.uuid ";
        cfSearchQuery += "AND cf.uuid = '" + searchFieldDTO.getCustomFieldId() + "' " +
                "AND (lower(unaccent(v.value)) = lower(unaccent('" + searchFieldDTO.getValueText() + "')) " +
                "OR lower(unaccent(v.value)) LIKE lower(unaccent('%" + searchFieldDTO.getValueText() + "%'))) ) ";

        addedQueries += cfSearchQuery;

        return addedQueries;
    }

    public String buildNotContainCustomFieldAccountContact(SearchFieldDTO searchFieldDTO)
    {
        String addedQueries = "";
        addedQueries += " \nNOT EXISTS ( ";
        String cfSearchQuery = CustomFieldUtils.querySearchCustomFieldValue();
        cfSearchQuery += "AND v.object_id = q_search.uuid ";
        cfSearchQuery += "AND cf.uuid = '" + searchFieldDTO.getCustomFieldId() + "' " +
                "AND (lower(unaccent(v.value)) = lower(unaccent('" + searchFieldDTO.getValueText() + "')) " +
                "OR lower(unaccent(v.value)) LIKE lower(unaccent('%" + searchFieldDTO.getValueText() + "%'))) )  AND " +
                " EXISTS (  SELECT v.uuid AS value_id, v.object_id, v.is_checked, v.value, v.date_value, cf.active, cf.field_type, cf.uuid, v.custom_field_option_value_id\n" +
                "    FROM custom_field_value v INNER JOIN custom_field cf ON cf.uuid = v.custom_field_id\n" +
                "        WHERE CASE WHEN (cf.field_type = 0 OR cf.field_type = 4) THEN v.is_checked = TRUE ELSE TRUE END AND cf.active = TRUE\n" +
                "\t\t\t\tAND v.object_id = q_search.uuid  AND cf.uuid  =  '" + searchFieldDTO.getCustomFieldId() + "' )";

        addedQueries += cfSearchQuery;

        return addedQueries;
    }

    public String buildOtherOperatorCustomFieldQueryAccountContact(SearchFieldDTO searchFieldDTO)
    {

        String addedQueries = "";
        addedQueries += " \nEXISTS ( ";
        String cfSearchQuery = CustomFieldUtils.querySearchCustomFieldValue();
        cfSearchQuery += "AND v.object_id = q_search.uuid ";

        CustomField customField = customFieldDAO.findOne(UUID.fromString(searchFieldDTO.getCustomFieldId()));
        if (customField.getFieldType().equals(CustomFieldType.DATE))
        {
            Date startOfDay = searchFieldDTO.getValueDate();
            Date endOfDay = new Date(searchFieldDTO.getValueDate().getTime() + 24 * 60 * 60 * 1000);

            if (searchFieldDTO.getOperator().equals(Operator.GREATER.toString()))
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        "AND v.date_value IS NOT NULL AND CAST(v.date_value as DATE) >= '" + endOfDay + "') ) ";
            }
            else if (searchFieldDTO.getOperator().equals(Operator.LESS.toString()))
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        "AND v.date_value IS NOT NULL AND CAST(v.date_value as DATE) < '" + startOfDay + "') ) ";
            }
            else
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        "AND v.date_value IS NOT NULL AND CAST(v.date_value as DATE) >= '" + startOfDay + "' AND CAST(v.date_value as DATE) < '"
                        + endOfDay + "') ) ";
            }

        }
        else if (searchFieldDTO.getValueId() == null)
        {
            if (customField.getFieldType().equals(CustomFieldType.NUMBER))
            {   // if when compare number field
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        " AND v.value IS NOT NULL " +
                        " AND v.value != '' " +
                        "AND CAST(v.value as FLOAT) " + operatorMap.get(searchFieldDTO.getOperator()) +
                        " " + searchFieldDTO.getValueText() + ") ) ";
            }
            else
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        "AND lower(unaccent(v.value)) " + operatorMap.get(searchFieldDTO.getOperator()) +
                        " lower(unaccent('" + searchFieldDTO.getValueText() + "'))) ) ";
            }
        }
        else
        {
            if (searchFieldDTO.getOperator().equals(Operator.NOT_EQUALS.toString()))
            {   // fix with operator NOT_EQUALS
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        " AND v.object_id not in (SELECT cfv1.object_id from custom_field_value cfv1 where cfv1.custom_field_option_value_id = '" + searchFieldDTO.getValueId() + "' and cfv1.is_checked = true) ) ) ";
            }
            else
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        " AND v.custom_field_option_value_id " + operatorMap.get(searchFieldDTO.getOperator()) +
                        " '" + searchFieldDTO.getValueId() + "') ) ";
            }
        }

        addedQueries += cfSearchQuery;
        return addedQueries;
    }

    public String convertToQuery(String objectType, List<SearchFieldDTO[]> searchFielDTOList, Enterprise enterprise)
    {
        String subQueries = "";
        for (int orIndex = 0; orIndex < searchFielDTOList.size(); orIndex++)
        {
            SearchFieldDTO[] searchFieldDTOArras = searchFielDTOList.get(orIndex);
            String andConditions = "";

            for (int andIndex = 0; andIndex < searchFieldDTOArras.length; andIndex++)
            {
                String newCondition = convertSearchField(objectType, searchFieldDTOArras[andIndex],enterprise);

                if (newCondition.length() > 0)
                {
                    if (andIndex == 0 || andConditions.length() == 0)
                    {
                        andConditions += newCondition;
                    }
                    else
                    {
                        andConditions += " AND " + newCondition;
                    }
                }
            }

            if (andConditions.length() > 0)
            {
                if (orIndex == 0 || subQueries.length() == 0)
                {
                    subQueries += "(" + andConditions + ")";
                }
                else
                {
                    subQueries += " OR  " + "(" + andConditions + ")";
                }
            }

        }
        return subQueries;
    }

    public String convertToQueryProspectHistoric(String objectType, List<SearchFieldDTO[]> searchFielDTOList, Enterprise enterprise)
    {
        String subQueries = "";
        for (int orIndex = 0; orIndex < searchFielDTOList.size(); orIndex++)
        {
            SearchFieldDTO[] searchFieldDTOArras = searchFielDTOList.get(orIndex);
            String andConditions = "";

            for (int andIndex = 0; andIndex < searchFieldDTOArras.length; andIndex++)
            {
                String newCondition = convertSearchFieldProspectHistoric(objectType, searchFieldDTOArras[andIndex],enterprise);

                if (newCondition.length() > 0)
                {
                    if (andIndex == 0 || andConditions.length() == 0)
                    {
                        andConditions += newCondition;
                    }
                    else
                    {
                        andConditions += " AND " + newCondition;
                    }
                }
            }

            if (andConditions.length() > 0)
            {
                if (orIndex == 0 || subQueries.length() == 0)
                {
                    subQueries += "(" + andConditions + ")";
                }
                else
                {
                    subQueries += " OR  " + "(" + andConditions + ")";
                }
            }

        }
        return subQueries;
    }

    public String getCustomFieldQueryOtherObject(String tableName, SearchFieldDTO searchFieldDTO)
    {
        String addedQueries = "";
        if (searchFieldDTO.getOperator().equals(Operator.CONTAINS.toString()))
        {
            addedQueries += buildContainCustomFieldQueryOtherObject(tableName, searchFieldDTO);
        }
        else if (searchFieldDTO.getOperator().equals(Operator.NOT_CONTAINS.toString()))
        {
            addedQueries += buildNotContainCustomFieldQueryOtherObject(tableName, searchFieldDTO);
        }
        else if (searchFieldDTO.getOperator().equals(Operator.NO_VALUE.toString()))
        {
            addedQueries += buildNoValueCustomFieldQueryOtherObject(tableName, searchFieldDTO);
        }
        else
        {
            addedQueries += buildOtherOperatorCustomFieldQueryOtherObject(tableName, searchFieldDTO);
        }

        return addedQueries;
    }

    public String buildContainCustomFieldQueryOtherObject(String tableName, SearchFieldDTO searchFieldDTO)
    {
        String addedQueries = "";
        addedQueries += " \nEXISTS ( ";
        String cfSearchQuery = " " + CustomFieldUtils.querySearchCustomFieldValue();
        cfSearchQuery += " AND v.object_id = " + tableName + ".uuid ";
        cfSearchQuery += " AND cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                "AND (lower(unaccent(v.value)) = lower(unaccent('" + searchFieldDTO.getValueText() + "')) " +
                "OR lower(unaccent(v.value)) LIKE lower(unaccent('%" + searchFieldDTO.getValueText() + "%'))) ) ";

        addedQueries += cfSearchQuery;

        return addedQueries;
    }

    public String buildNotContainCustomFieldQueryOtherObject(String tableName, SearchFieldDTO searchFieldDTO)
    {
        String addedQueries = "";
        addedQueries += " \nNOT EXISTS ( ";
        String cfSearchQuery = " " + CustomFieldUtils.querySearchCustomFieldValue();
        cfSearchQuery += " AND v.object_id = " + tableName + ".uuid ";
        cfSearchQuery += " AND cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                "AND (lower(unaccent(v.value)) = lower(unaccent('" + searchFieldDTO.getValueText() + "')) " +
                "OR lower(unaccent(v.value)) LIKE lower(unaccent('%" + searchFieldDTO.getValueText() + "%'))) )  AND " +
                " EXISTS (  SELECT v.uuid AS value_id, v.object_id, v.is_checked, v.value, v.date_value, cf.active, cf.field_type, cf.uuid, v.custom_field_option_value_id\n" +
                "    FROM custom_field_value v INNER JOIN custom_field cf ON cf.uuid = v.custom_field_id\n" +
                "        WHERE CASE WHEN (cf.field_type = 0 OR cf.field_type = 4) THEN v.is_checked = TRUE ELSE TRUE END AND cf.active = TRUE\n" +
                "\t\t\t\tAND v.object_id = "+tableName+".uuid  AND cf.uuid  =  '" + searchFieldDTO.getCustomFieldId() + "' )";

        addedQueries += cfSearchQuery;

        return addedQueries;
    }

    public String buildNoValueCustomFieldQueryOtherObject(String tableName, SearchFieldDTO searchFieldDTO)
    {
        String addedQueries = "";
        addedQueries += " \nNOT EXISTS ( ";
        String cfSearchQuery = " " + CustomFieldUtils.querySearchCustomFieldValue();
        cfSearchQuery += " AND v.object_id = " + tableName + ".uuid ";
        cfSearchQuery += " AND v.value  !='' ";
        cfSearchQuery += " AND cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' ) ";

        addedQueries += cfSearchQuery;

        return addedQueries;
    }

    public String buildOtherOperatorCustomFieldQueryOtherObject(String tableName, SearchFieldDTO searchFieldDTO)
    {
        CustomField customField = customFieldDAO.findOne(UUID.fromString(searchFieldDTO.getCustomFieldId()));
        String addedQueries = "";
        addedQueries += " \nEXISTS ( ";
        String cfSearchQuery = " " + CustomFieldUtils.querySearchCustomFieldValue();
        cfSearchQuery += " AND v.object_id = " + tableName + ".uuid ";

        if (customField.getFieldType().equals(CustomFieldType.DATE))
        {

            Date startOfDay = searchFieldDTO.getValueDate();
            Date endOfDay = new Date(searchFieldDTO.getValueDate().getTime() + 24 * 60 * 60 * 1000);

            if (searchFieldDTO.getOperator().equals(Operator.GREATER.toString()))
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        "AND v.date_value IS NOT NULL AND CAST(v.date_value as DATE)  >= '" + endOfDay + "') ) ";
            }
            else if (searchFieldDTO.getOperator().equals(Operator.LESS.toString()))
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        "AND v.date_value IS NOT NULL AND CAST(v.date_value as DATE) < '" + startOfDay + "') ) ";
            }
            else
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        "AND v.date_value IS NOT NULL AND CAST(v.date_value as DATE) >= '" + startOfDay + "' AND CAST(v.date_value as DATE) < '"
                        + endOfDay + "') ) ";
            }
        }
        else if (searchFieldDTO.getValueId() == null)
        {
            if (customField.getFieldType().equals(CustomFieldType.NUMBER))
            { // fix if type is number
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        " AND v.value IS NOT NULL " +
                        " AND v.value != '' " +
                        "AND CAST(v.value as FLOAT)" + operatorMap.get(searchFieldDTO.getOperator()) +
                        " " + searchFieldDTO.getValueText() + ") ) ";
            }
            else
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        "AND lower(unaccent(v.value)) " + operatorMap.get(searchFieldDTO.getOperator()) +
                        " lower(unaccent('" + searchFieldDTO.getValueText() + "'))) ) ";
            }

        }
        else
        {
            if (searchFieldDTO.getOperator().equals(Operator.NOT_EQUALS.toString()))
            {  // fix query not equal
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        " AND v.object_id not in (SELECT cfv1.object_id from custom_field_value cfv1 where cfv1.custom_field_option_value_id = '" + searchFieldDTO.getValueId() + "' and cfv1.is_checked = true) ) ) ";
            }
            else
            {
                cfSearchQuery += " AND (cf.uuid  = '" + searchFieldDTO.getCustomFieldId() + "' " +
                        "AND v.custom_field_option_value_id " + operatorMap.get(searchFieldDTO.getOperator()) +
                        " '" + searchFieldDTO.getValueId() + "') ) ";
            }
        }

        addedQueries += cfSearchQuery;
        return addedQueries;
    }


    public String getCustomFieldQueryAccountContact(SearchFieldDTO searchFieldDTO)
    {
        String addedQueries = "";
        if (searchFieldDTO.getOperator().equals(Operator.CONTAINS.toString()))
        {
//            addedQueries += " (cf_uuid = '" + searchFieldDTO.getCustomFieldId() + "' " +
//                    "AND cf_value = '" + searchFieldDTO.getValueText() + "' " +
//                    "OR cf_value LIKE '%" + searchFieldDTO.getValueText() + "%') ";
            addedQueries += buildContainCustomFieldAccountContact(searchFieldDTO);
        }
        else if (searchFieldDTO.getOperator().equals(Operator.NOT_CONTAINS.toString()))
        {
//            addedQueries += " (cf_uuid = '" + searchFieldDTO.getCustomFieldId() + "' " +
//                    "AND cf_value != '" + searchFieldDTO.getValueText() + "' " +
//                    "AND cf_value NOT LIKE '%" + searchFieldDTO.getValueText() + "%') ";
            addedQueries += buildNotContainCustomFieldAccountContact(searchFieldDTO);
        }
        else if (searchFieldDTO.getOperator().equals(Operator.NO_VALUE.toString()))
        {
            addedQueries += buildNoValueCustomFieldAccountContact(searchFieldDTO);
        }
        else
        {
//            addedQueries += " (cf_uuid = '" + searchFieldDTO.getCustomFieldId() + "' " +
//                    "AND cf_value " + operatorMap.get(searchFieldDTO.getOperator()) +
//                    " '" + searchFieldDTO.getValueText() + "') ";
            addedQueries += buildOtherOperatorCustomFieldQueryAccountContact(searchFieldDTO);
        }

        return addedQueries;
    }

    public String convertSearchFieldProspectHistoric(String objectType, SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        CustomField customField = searchFieldDTO.getCustomFieldId() == null
                ? null
                : customFieldDAO.findOne(UUID.fromString(searchFieldDTO.getCustomFieldId()));

        String tableNameInSearch = CustomFieldUtils.TableNameInSearch.PROSPECT;

        if (customField != null)
        {
            if (customField.getFieldType() == CustomFieldType.PRODUCT_TAG) {
                if (Operator.valueOf(searchFieldDTO.getOperator()) != NO_VALUE && StringUtils.isEmpty(searchFieldDTO.getProductId())) {
                    return "";
                }
                return getCustomFieldProductTagQuery(searchFieldDTO, objectType, customField.getObjectType());
            }
            else if (customField.getObjectType() != ObjectType.OPPORTUNITY)
            {
                tableNameInSearch = CustomFieldUtils.TableNameInSearch.ORDER_ROW;
            }
        }
        return searchFieldDTO.getCustomFieldId() == null ? getOpportunityHistoricQueries(searchFieldDTO,enterprise) : getCustomFieldQueryOtherObject(tableNameInSearch, searchFieldDTO);

    }

    /**
     * For customField type = PRODUCT_TAG
     * @param objectType object type of parent screen
     * @param customFieldObjectType actual object type of custom field, used to detect case when Searching sub-object (OrderRow...) inside parent screen
     */
    private String getCustomFieldProductTagQuery(SearchFieldDTO searchFieldDTO, String objectType, ObjectType customFieldObjectType) {
        StringBuilder sql = new StringBuilder();
        String objectAlias = CustomFieldUtils.getTableNameByObjectType(objectType, customFieldObjectType);
        switch (Operator.valueOf(searchFieldDTO.getOperator())) {
            case EQUALS:
                sql.append(" EXISTS ( ")
                        .append(CustomFieldUtils.querySearchCustomFieldValue())
                        .append(" AND cf.uuid = '").append(searchFieldDTO.getCustomFieldId()).append("' ")
                        .append(" AND ").append(objectAlias).append(".uuid = v.object_id ")
                        .append(" AND v.product_id = '").append(searchFieldDTO.getProductId()).append("' ) ")
                ;
                break;
            case NOT_EQUALS:
                sql.append(" EXISTS ( ")
                        .append(CustomFieldUtils.querySearchCustomFieldValue())
                        .append(" AND cf.uuid = '").append(searchFieldDTO.getCustomFieldId()).append("' ")
                        .append(" AND ").append(objectAlias).append(".uuid = v.object_id ")
                        .append(" AND v.object_id NOT IN ")
                        .append(" (SELECT v1.object_id FROM custom_field_value v1 ")
                        .append(" WHERE v1.custom_field_id = '").append(searchFieldDTO.getCustomFieldId())
                        .append("' AND v1.product_id = '").append(searchFieldDTO.getProductId()).append("' ) ) ")
                ;
                break;
            case NO_VALUE:
                sql.append(" NOT EXISTS ( ")
                        .append(CustomFieldUtils.querySearchCustomFieldValue())
                        .append(" AND cf.uuid = '").append(searchFieldDTO.getCustomFieldId()).append("' ")
                        .append(" AND ").append(objectAlias).append(".uuid = v.object_id ) ")
                ;
                break;
        }
        return sql.toString();
    }

    public String convertSearchField(String objectType, SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        if (StringUtils.isEmpty(searchFieldDTO.getOperator())
                || StringUtils.isEmpty(objectType)
                || (!NO_VALUE.toString().equals(searchFieldDTO.getOperator()) && searchFieldDTO.checkIfNullValue())
        ) {
            return "";
        }

        CustomField customField = StringUtils.isEmpty(searchFieldDTO.getCustomFieldId())
                ? null
                : customFieldDAO.findOne(UUID.fromString(searchFieldDTO.getCustomFieldId()));

        if (customField != null && customField.getFieldType() == CustomFieldType.PRODUCT_TAG) {
            if (Operator.valueOf(searchFieldDTO.getOperator()) != NO_VALUE && StringUtils.isEmpty(searchFieldDTO.getProductId())) {
                return "";
            }
            return getCustomFieldProductTagQuery(searchFieldDTO, objectType, customField.getObjectType());
        }
        else if (objectType.equals(ObjectType.ACCOUNT.toString()))
        {
            return searchFieldDTO.getCustomFieldId() == null ? getAccountQueries(searchFieldDTO, enterprise) : getCustomFieldQueryAccountContact(searchFieldDTO);
        }
        else if (objectType.equals(ObjectType.CONTACT.toString()))
        {
            return searchFieldDTO.getCustomFieldId() == null ? getContactQueries(searchFieldDTO,enterprise) : getCustomFieldQueryAccountContact(searchFieldDTO);
        }
        else if (objectType.equals(ObjectType.OPPORTUNITY.toString()))
        {
            String tableNameInSearch = CustomFieldUtils.TableNameInSearch.PROSPECT;

            if (customField != null)
            {
                if (customField.getObjectType() != ObjectType.OPPORTUNITY)
                {
                    tableNameInSearch = CustomFieldUtils.TableNameInSearch.ORDER_ROW;
                }
            }
            return searchFieldDTO.getCustomFieldId() == null ? getOpportunityQueries(searchFieldDTO, enterprise) : getCustomFieldQueryOtherObject(tableNameInSearch, searchFieldDTO);
        }
        else if (objectType.equals(ObjectType.TASK.toString()))
        {
            return searchFieldDTO.getCustomFieldId() == null ? getTaskQueries(searchFieldDTO,enterprise) : getCustomFieldQueryOtherObject(CustomFieldUtils.TableNameInSearch.TASK, searchFieldDTO);
        }
        else if (objectType.equals(ObjectType.LEAD.toString()))
        {
            return searchFieldDTO.getCustomFieldId() == null ? getLeadQueries(searchFieldDTO,enterprise) : getCustomFieldQueryOtherObject(CustomFieldUtils.TableNameInSearch.LEAD, searchFieldDTO);
        }
        else if (objectType.equals(ObjectType.APPOINTMENT.toString()))
        {
            return searchFieldDTO.getCustomFieldId() == null ? getAppointmentQueries(searchFieldDTO,enterprise) : getCustomFieldQueryOtherObject(CustomFieldUtils.TableNameInSearch.APPOINTMENT, searchFieldDTO);
        }
        else if (objectType.equals(ObjectType.CAMPAIGN.toString()))
        {
            return getCampaignQueries(searchFieldDTO,enterprise);
        }
        else if (objectType.equals(ObjectType.CALL_LIST_CONTACT.toString()) || objectType.equals(ObjectType.CALL_LIST_ACCOUNT.toString()))
        {
            return getCallListQueries(searchFieldDTO,enterprise);
        }
        return "";
    }

    public String convertSearchFieldToQuery(String objectType, List<SearchFieldDTO> searchFieldDTOList, Enterprise enterprise)
    {
        String addedQueries = "";
        for (int i = 0; i < searchFieldDTOList.size(); i++)
        {
            SearchFieldDTO searchFieldDTO = searchFieldDTOList.get(i);
            String newCondition = "";
            if (!searchFieldDTO.checkIfNullValue())
            {
                if (objectType.equals(ObjectType.ACCOUNT.toString()))
                {
                    newCondition = getAccountQueries(searchFieldDTO,enterprise);
                }
                else if (objectType.equals(ObjectType.CONTACT.toString()))
                {
                    newCondition = getContactQueries(searchFieldDTO,enterprise);
                }
                else if (objectType.equals(ObjectType.OPPORTUNITY.toString()))
                {
                    newCondition = getOpportunityQueries(searchFieldDTO, enterprise);
                }
                else if (objectType.equals(ObjectType.TASK.toString()))
                {
                    newCondition = getTaskQueries(searchFieldDTO,enterprise);
                }
                else if (objectType.equals(ObjectType.LEAD.toString()))
                {
                    newCondition = getLeadQueries(searchFieldDTO,enterprise);
                }
                else if (objectType.equals(ObjectType.APPOINTMENT.toString()))
                {
                    newCondition = getAppointmentQueries(searchFieldDTO,enterprise);
                }
            }

            if (newCondition.length() > 0)
            {
                if (i == 0 || addedQueries.length() == 0)
                {
                    addedQueries += " ( ";
                }
                else
                {
                    addedQueries += " " + (searchFieldDTOList.get(i - 1).getPrefix() == null ? DEFAULT_DASHBOARD_OPERATOR : searchFieldDTOList.get(i - 1).getPrefix());
                }
                addedQueries += newCondition;
            }
            if (i == searchFieldDTOList.size() - 1 && addedQueries.length() > 0)
            {
                addedQueries += " ) ";
            }
        }

        return addedQueries.length() == 0 ? "TRUE" : addedQueries;
    }

    public String convertSearchFieldToQueryProspectHistoric(String objectType, List<SearchFieldDTO> searchFieldDTOList, Enterprise enterprise)
    {
        String addedQueries = "";
        for (int i = 0; i < searchFieldDTOList.size(); i++)
        {
            SearchFieldDTO searchFieldDTO = searchFieldDTOList.get(i);
            String newCondition = "";
            if (!searchFieldDTO.checkIfNullValue())
            {
                getOpportunityHistoricQueries(searchFieldDTO, enterprise);
            }

            if (newCondition.length() > 0)
            {
                if (i == 0 || addedQueries.length() == 0)
                {
                    addedQueries += " ( ";
                }
                else
                {
                    addedQueries += " " + (searchFieldDTOList.get(i - 1).getPrefix() == null ? DEFAULT_DASHBOARD_OPERATOR : searchFieldDTOList.get(i - 1).getPrefix());
                }
                addedQueries += newCondition;
            }
            if (i == searchFieldDTOList.size() - 1 && addedQueries.length() > 0)
            {
                addedQueries += " ) ";
            }
        }

        return addedQueries.length() == 0 ? "TRUE" : addedQueries;
    }

    private String getCallListQueries(SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        String result = "";
        Integer timeZone = enterprise.getRegisteredTimeZone();
        String addTimeZone = "+ INTERVAL '"+ (timeZone == null ? '2' : timeZone) + " hour'";
        switch (searchFieldDTO.getField())
        {
            case CallListField.NAME:
                result = " LOWER(temp_.name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CallListField.DEADLINE:
                result = " CAST((temp_.deadline_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(),enterprise);
                break;
            case CallListField.NO_OF_ACCOUNTS:
                result = " temp_.number_account " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CallListField.NO_OF_CONTACTS:
                result = " temp_.number_contact " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CallListField.APPOINTMENTS:
                result = " temp_.number_meeting " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CallListField.DIALS:
                result = " temp_.number_dial " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CallListField.CALLS:
                result = " temp_.number_call " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CallListField.OPPORTUNITIES:
                result = " temp_.number_prospect " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CallListField.OWNER_FIRST_NAME:
                result = " LOWER(temp_.first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CallListField.OWNER_LAST_NAME:
                result = " LOWER(temp_.last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
        }
        return result;
    }

    private String getCampaignQueries(SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        String result = "";
        Integer timeZone = enterprise.getRegisteredTimeZone();
        String addTimeZone = "+ INTERVAL '"+ (timeZone == null ? '2' : timeZone) + " hour'";
        switch (searchFieldDTO.getField())
        {
            case CampaignField.NAME:
                result = " LOWER(c.name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CampaignField.START_DATE:

                result = " CAST((c.start_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(),enterprise);
                break;
            case CampaignField.END_DATE:
                result = " CAST((c.end_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(),enterprise);
                break;
            case CampaignField.PRODUCT_GROUP:
                result = " c.line_of_business_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case CampaignField.PRODUCT:
                result = " cp.product_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case CampaignField.OWNER_FIRST_NAME:
                result = " LOWER(sc.first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CampaignField.OWNER_LAST_NAME:
                result = " LOWER(sc.last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CampaignField.UNIT:
                result = " c.units " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case CampaignField.USER:
                result = " c.users " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case CampaignField.NO_OF_STEPS:
                result = " c.number_step " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CampaignField.CHANNELS:
                result = " LOWER(c.chanel) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CampaignField.STATUS:
                result = populateCampaignStatus(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CampaignField.LEADS:
                result = " c.number_lead " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CampaignField.OPPORTUNITIES:
                result = " c.number_prospect " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CampaignField.ORDERS:
                result = " c.number_won_prospect " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case CampaignField.SALES:
                result = " c.budget " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
        }
        return result;
    }


    private String getAppointmentQueries(SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        String result = "";
        Integer timeZone = enterprise.getRegisteredTimeZone();
        String addTimeZone = "+ INTERVAL '"+ (timeZone == null ? '2' : timeZone) + " hour'";
        switch (searchFieldDTO.getField())
        {
            case AppointmentField.TITLE:
                result = " LOWER(app.title) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_FIRST_NAME:
                result = " LOWER(c.first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_LAST_NAME:
                result = " LOWER(c.last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_PHONE:
                result = " LOWER(c.phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_EMAIL:
                result = " LOWER(c.email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_STREET:
                result = " LOWER(c.street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_ZIP_CODE:
                result = " LOWER(c.zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_CITY:
                result = " LOWER(c.city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_REGION:
                result = " LOWER(c.region) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_COUNTRY:
                result = " LOWER(c.country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.CONTACT_TYPE:
                result = " c.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AppointmentField.CONTACT_INDUSTRY:
                result = " c.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AppointmentField.CONTACT_RELATION:
                result = " c.relation_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AppointmentField.ACCOUNT_NAME:
                result = " LOWER(o.name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.ACCOUNT_VAT:
                result = " LOWER(o.vat_number) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.ACCOUNT_PHONE:
                result = " LOWER(o.phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.ACCOUNT_EMAIL:
                result = " LOWER(o.email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.ACCOUNT_STREET:
                result = " LOWER(o.street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.ACCOUNT_ZIP_CODE:
                result = " LOWER(o.zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.ACCOUNT_CITY:
                result = " LOWER(o.city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.ACCOUNT_STATE:
                result = " LOWER(o.state) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.ACCOUNT_COUNTRY:
                result = " LOWER(o.country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.ACCOUNT_TYPE:
                result = " o.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AppointmentField.ACCOUNT_INDUSTRY:
                result = " o.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AppointmentField.ACCOUNT_SIZE:
                result = " o.size_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AppointmentField.OWNER_FIRST_NAME:
                result = " LOWER(sc.first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.OWNER_LAST_NAME:
                result = " LOWER(sc.last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AppointmentField.FOCUS_WORKDATA:
                result = " app.focus_work_data_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AppointmentField.FOCUS_ACTIVITY:
                result = " app.focus_activity_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AppointmentField.CREATED_TIME:
                result = " CAST((app.created_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(),enterprise);
                break;
            case AppointmentField.START_TIME:
                result = " CAST((app.start_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(),enterprise);
                break;
            case AppointmentField.DONE_TIME:
                result = " CAST((app.end_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(),enterprise);
                break;

            case AppointmentField.NOTE:
                result = " LOWER(app.note) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
        }
        return result;
    }

    private String getLeadQueries(SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        String result = "";
        Integer timeZone = enterprise.getRegisteredTimeZone();
        String addTimeZone = "+ INTERVAL '"+ (timeZone == null ? '2' : timeZone) + " hour'";
        switch (searchFieldDTO.getField())
        {
            case LeadField.CONTACT_FIRST_NAME:
                result = " LOWER(c.first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CONTACT_LAST_NAME:
                result = " LOWER(c.last_name)  " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CONTACT_PHONE:
                result = " LOWER(c.phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CONTACT_EMAIL:
                result = " LOWER(c.email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CONTACT_STREET:
                result = " LOWER(c.street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CONTACT_ZIP_CODE:
                result = " LOWER(c.zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CONTACT_CITY:
                result = " LOWER(c.city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CONTACT_REGION:
                result = " LOWER(c.region) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CONTACT_COUNTRY:
                result = " LOWER(c.country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CONTACT_TYPE:
                result = " c.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case LeadField.CONTACT_INDUSTRY:
                result = " c.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case LeadField.CONTACT_RELATION:
                result = " c.relation_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case LeadField.ACCOUNT_NAME:
                result = " LOWER(o.name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.ACCOUNT_VAT:
                result = " LOWER(o.vat_number) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.ACCOUNT_PHONE:
                result = " LOWER(o.phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.ACCOUNT_EMAIL:
                result = " LOWER(o.email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.ACCOUNT_STREET:
                result = " LOWER(o.street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.ACCOUNT_ZIP_CODE:
                result = " LOWER(o.zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.ACCOUNT_CITY:
                result = " LOWER(o.city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.ACCOUNT_STATE:
                result = " LOWER(o.state) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.ACCOUNT_COUNTRY:
                result = " LOWER(o.country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.ACCOUNT_TYPE:
                result = " o.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case LeadField.ACCOUNT_INDUSTRY:
                result = " o.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.ACCOUNT_SIZE:
                result = " o.size_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case LeadField.OWNER_FIRST_NAME:
                result = " LOWER(osc.first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.OWNER_LAST_NAME:
                result = " LOWER(osc.last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.CREATED_TIME:
                result = " CAST((l.created_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;
            case LeadField.DEADLINE_TIME:
                result = " CAST((l.deadline_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;
            case LeadField.DONE_TIME:
                result = " CAST((l.finished_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;

            case LeadField.PRODUCT_GROUP:
                result = " l.line_of_business_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;

            case LeadField.PRODUCT:
//                result = " o.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                result = " lp.product_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;

            case LeadField.SOURCE:
                result = " l.source " + populateOperatorIntegerNumber(searchFieldDTO.getOperator(), SourceType.valueOf(searchFieldDTO.getValueId()).getExtension() + "");
                break;

            case LeadField.PRIORITY:
                result = populatePriority(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case LeadField.NOTE:
                result = " LOWER(l.note) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
//            case LeadField.STATUS:
//                result = " LOWER(l.note) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
//                break;
            case LeadField.BEHAVIOR_COLOR:
                result = populateBehaviorColorForLead(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;

            case LeadField.PRODUCT_TYPE:
                result = "p.measurement_type_id " + populateLeadProductType(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case LeadField.CREATOR:
                result = "cu.username " + populateByCreator(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case LeadField.FOCUS_WORKDATA:
                result = " t.focus_work_data_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
        }
        return result;
    }

    private String populateByCreator(String operator, String valueText)
    {

        String result = operatorMap.get(operator);
        if (result.equals("NO_VALUE"))
        {
            return " IS NULL";
        }

        if (operator.equals("CONTAINS") || operator.equals("NOT_CONTAINS"))
        {
            return " " + result + " '%" + valueText.toLowerCase().trim() + "%'";
        }
        else
        {
//            return result + " " + value;
            return result + " " + "\'" + valueText + "\'";
        }

    }

    private String populateLeadProductType(String operator, String valueId)
    {
        if (operator.equalsIgnoreCase("EQUALS"))
        {
            return "= " + "'" + valueId + "'";
        }
        else if (operator.equalsIgnoreCase("NOT_EQUALS"))
        {
            return "<> " + "'" + valueId + "'";
        }
        else
        {
            return "is null";
        }
    }

    private String populateBehaviorColorForLead(String operator, String valueText)
    {
        int discProfie = 0;

        if (valueText != null)
        {
            if (valueText.equalsIgnoreCase("RED"))
            {
                discProfie = 1;
            }
            else if (valueText.equalsIgnoreCase("GREEN"))
            {
                discProfie = 2;
            }
            else if (valueText.equalsIgnoreCase("BLUE"))
            {
                discProfie = 3;
            }
            else if (valueText.equalsIgnoreCase("YELLOW"))
            {
                discProfie = 4;
            }
        }
        if (operator.equalsIgnoreCase("EQUALS"))
        {
            return "c.disc_profile = " + discProfie;
        }
        else if (operator.equalsIgnoreCase("NOT_EQUALS"))
        {
            return "c.disc_profile <> " + discProfie;
        }
        else if (operator.equalsIgnoreCase("NO_VALUE"))
        {
            return "c.disc_profile = " + 0;
        }
        return operator;
    }

    private String getTaskQueries(SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        String result = "";
        Integer timeZone = enterprise.getRegisteredTimeZone();
        String addTimeZone = "+ INTERVAL '"+ (timeZone == null ? '2' : timeZone) + " hour'";
        switch (searchFieldDTO.getField())
        {
            case TaskField.CONTACT_FIRST_NAME:
                result = populateOperatorAndStringValue("LOWER(c.first_name)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CONTACT_LAST_NAME:
                result = populateOperatorAndStringValue("LOWER(c.last_name)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CONTACT_PHONE:
                result = populateOperatorAndStringValue("LOWER(c.phone)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CONTACT_EMAIL:
                result = populateOperatorAndStringValue("LOWER(c.email)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CONTACT_STREET:
                result = populateOperatorAndStringValue("LOWER(c.street)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CONTACT_ZIP_CODE:
                result = populateOperatorAndStringValue("LOWER(c.zip_code)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CONTACT_CITY:
                result = populateOperatorAndStringValue("LOWER(c.city)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CONTACT_REGION:
                result = populateOperatorAndStringValue("LOWER(c.region)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CONTACT_COUNTRY:
                result = populateOperatorAndStringValue("LOWER(c.country)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CONTACT_TYPE:
                result = " c.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.CONTACT_INDUSTRY:
                result = " c.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.CONTACT_RELATION:
                result = " c.relation_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.ACCOUNT_NAME:
                result = populateOperatorAndStringValue("LOWER(o.name)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.ACCOUNT_VAT:
                result = populateOperatorAndStringValue("LOWER(o.vat_number)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.ACCOUNT_PHONE:
                result = populateOperatorAndStringValue("LOWER(o.phone)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.ACCOUNT_EMAIL:
                result = populateOperatorAndStringValue("LOWER(o.email)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.ACCOUNT_STREET:
                result = populateOperatorAndStringValue("LOWER(o.street)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.ACCOUNT_ZIP_CODE:
                result = populateOperatorAndStringValue("LOWER(o.zip_code)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.ACCOUNT_CITY:
                result = populateOperatorAndStringValue("LOWER(o.city)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.ACCOUNT_STATE:
                result = populateOperatorAndStringValue("LOWER(o.state)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.ACCOUNT_COUNTRY:
                result = populateOperatorAndStringValue("LOWER(o.country)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.ACCOUNT_TYPE:
                result = " o.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.ACCOUNT_INDUSTRY:
                result = " o.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.ACCOUNT_SIZE:
                result = " o.size_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.OWNER_FIRST_NAME:
                result = populateOperatorAndStringValue("LOWER(sc.first_name)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.OWNER_LAST_NAME:
                result = populateOperatorAndStringValue("LOWER(sc.last_name)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
            case TaskField.CREATED_TIME:
                result = " CAST((t.created_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(),enterprise);
                break;
            case TaskField.DONE_TIME:
                result = " CAST((t.date_and_time "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(),enterprise);
                break;
            case TaskField.TAG:
                result = " t.task_tag_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.FOCUS_WORKDATA:
                result = " t.focus_work_data_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.FOCUS_ACTIVITY:
                result = " t.focus_activity_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.CATEGORY:
                result = " t.category_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case TaskField.NOTE:
                result = populateOperatorAndStringValue("LOWER(t.note)", searchFieldDTO.getOperator(), searchFieldDTO.getValueText(), FieldType.TEXT);
                break;
        }
        return result;
    }

    private String getOpportunityQueries(SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        String result = "";
        Integer timeZone = enterprise.getRegisteredTimeZone();
        String addTimeZone = "+ INTERVAL '"+ (timeZone == null ? '2' : timeZone) + " hour'";
        switch (searchFieldDTO.getField())
        {
            case OpportunityField.CONTACT_FIRST_NAME:
                result = " LOWER(c.first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_LAST_NAME:
                result = " LOWER(c.last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_PHONE:
                result = " LOWER(c.phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_EMAIL:
                result = " LOWER(c.email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_STREET:
                result = " LOWER(c.street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_ZIP_CODE:
                result = " LOWER(c.zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_CITY:
                result = " LOWER(c.city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_REGION:
                result = " LOWER(c.region) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_COUNTRY:
                result = " LOWER(c.country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_TYPE:
                result = " c.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.CONTACT_INDUSTRY:
                result = " c.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.CONTACT_RELATION:
                result = " c.relation_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.ACCOUNT_NAME:
                result = " LOWER(o.name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_VAT:
                result = " LOWER(o.vat_number) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_PHONE:
                result = " LOWER(o.phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_EMAIL:
                result = " LOWER(o.email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_STREET:
                result = " LOWER(o.street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_ZIP_CODE:
                result = " LOWER(o.zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_CITY:
                result = " LOWER(o.city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_STATE:
                result = " LOWER(o.state) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_COUNTRY:
                result = " LOWER(o.country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_TYPE:
                result = " o.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.ACCOUNT_INDUSTRY:
                result = " o.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.ACCOUNT_SIZE:
                result = " o.size_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.OWNER_FIRST_NAME:
                result = " LOWER(p.owner_first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.OWNER_LAST_NAME:
                result = " LOWER(p.owner_first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.NEXT_STEP:
                result = " LOWER(p.first_next_step) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.DESCRIPTION:
                result = " LOWER(p.description) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.SERIAL_NUMBER:
                result = " LOWER(p.serial_number) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.WON:
                result = " p.won " + populateOperatorBoolean(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.LOST:
                result = " p.won " + populateOperatorBoolean(searchFieldDTO.getOperator(), searchFieldDTO.getValueId().equalsIgnoreCase("true") ? "FALSE" : "TRUE");
                break;
            case OpportunityField.SALES_VALUE:
                result = " p.gross_value " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.PROFIT:
                result = " p.profit " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.PROGRESS:
                result = " p.prospect_progress " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CREATED_TIME:
                result = " CAST((p.created_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;
            case OpportunityField.PRODUCT:
                result = " prod.uuid " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.PRODUCT_GROUP:
                result = " lob.uuid " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.PRODUCT_TYPE:
                result = " met.uuid " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.CONTRACT_DATE:
                result = " CAST((p.contract_date  "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;
            case OpportunityField.DELIVERY_START_DATE:
                result = " p.uuid in (select ord1.prospect_id from order_row ord1 where CAST((ord1.delivery_start_date "+addTimeZone+") as DATE) "+populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise)+" ) " ;
                break;
            case OpportunityField.DELIVERY_END_DATE:
                result = " p.uuid in (select ord1.prospect_id from order_row ord1 where CAST((ord1.delivery_end_date "+addTimeZone+") as DATE) "+populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise)+" ) " ;
                break;
            case OpportunityField.DAY_IN_PIPE:
                result = " SELECT round(((EXTRACT(EPOCH FROM now())  - EXTRACT(EPOCH FROM p.created_date)) *1000) /(24*60*60*1000)) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;

            case OpportunityField.SEQUENTIAL_ACTIVITY:
                result = " p.current_step_id  " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
        }
        return result;
    }

    private String getOpportunityHistoricQueries(SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        String result = "";
        Integer timeZone = enterprise.getRegisteredTimeZone();
        String addTimeZone = "+ INTERVAL '"+ (timeZone == null ? '2' : timeZone) + " hour'";
        switch (searchFieldDTO.getField())
        {
            case OpportunityField.CONTACT_FIRST_NAME:
                result = " LOWER(p.contact_first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_LAST_NAME:
                result = " LOWER(p.contact_last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_PHONE:
                result = " LOWER(p.contact_phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_EMAIL:
                result = " LOWER(p.contact_email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_STREET:
                result = " LOWER(c.street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_ZIP_CODE:
                result = " LOWER(c.zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_CITY:
                result = " LOWER(c.city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_REGION:
                result = " LOWER(c.region) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_COUNTRY:
                result = " LOWER(c.country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CONTACT_TYPE:
                result = " c.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.CONTACT_INDUSTRY:
                result = " c.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.CONTACT_RELATION:
                result = " c.relation_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.ACCOUNT_NAME:
                result = " LOWER(p.organisation_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_VAT:
                result = " LOWER(o.vat_number) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_PHONE:
                result = " LOWER(o.phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_EMAIL:
                result = " LOWER(o.email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_STREET:
                result = " LOWER(o.street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_ZIP_CODE:
                result = " LOWER(o.zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_CITY:
                result = " LOWER(o.city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_STATE:
                result = " LOWER(o.state) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_COUNTRY:
                result = " LOWER(o.country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.ACCOUNT_TYPE:
                result = " o.type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.ACCOUNT_INDUSTRY:
                result = " o.industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.ACCOUNT_SIZE:
                result = " o.size_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.OWNER_FIRST_NAME:
                result = " LOWER(p.owner_first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.OWNER_LAST_NAME:
                result = " LOWER(p.owner_last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.NEXT_STEP:
                result = " LOWER(p.first_next_step) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.DESCRIPTION:
                result = " LOWER(p.description) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.SERIAL_NUMBER:
                result = " LOWER(p.serial_number) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.WON:
                result = " p.won " + populateOperatorBoolean(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.LOST:
                result = " p.won " + populateOperatorBoolean(searchFieldDTO.getOperator(), searchFieldDTO.getValueId().equalsIgnoreCase("true") ? "FALSE" : "TRUE");
                break;
            case OpportunityField.SALES_VALUE:
                result = " p.gross_value " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.PROFIT:
                result = " p.profit " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.PROGRESS:
                result = " p.prospect_progress " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case OpportunityField.CREATED_TIME:
                result = " CAST((p.created_date "+addTimeZone+") as DATE)  " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;
            case OpportunityField.PRODUCT:
                result = " prod.uuid " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.PRODUCT_GROUP:
                result = " lob.uuid " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.PRODUCT_TYPE:
                result = " met.uuid " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case OpportunityField.CONTRACT_DATE:
                result = "CAST((p.contract_date "+addTimeZone+") as DATE)  " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;
            case OpportunityField.DELIVERY_START_DATE:
                result = " p.uuid in (select ord1.prospect_id from order_row ord1 where CAST((ord1.delivery_start_date "+addTimeZone+") as DATE) "+populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise)+" ) " ;
                break;
            case OpportunityField.DELIVERY_END_DATE:
                result = " p.uuid in (select ord1.prospect_id from order_row ord1 where CAST((ord1.delivery_end_date "+addTimeZone+") as DATE) "+populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise)+" ) " ;
                break;
            case OpportunityField.DAY_IN_PIPE:
                result = " SELECT round(((EXTRACT(EPOCH FROM p.won_lost_date)  - EXTRACT(EPOCH FROM p.created_date)) *1000) /(24*60*60*1000)) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
        }
        return result;
    }

    private String getContactQueries(SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        String result = "";
        Integer timeZone = enterprise.getRegisteredTimeZone();
        String addTimeZone = "+ INTERVAL '"+ (timeZone == null ? '2' : timeZone) + " hour'";
        switch (searchFieldDTO.getField())
        {
            case ContactField.EMAIL_OPEN:
                result = " CAST((q_search.ch_receive_date "+ addTimeZone+")  as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;

            case ContactField.EMAIL_CLICK_ON_URL:
                result = " CAST((q_search.ch_receive_url_date "+ addTimeZone+")  as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;
                
            case ContactField.CALL_LIST:
                switch (Operator.valueOf(searchFieldDTO.getOperator())) {
                    case EQUALS:
                        result = " q_search.uuid IN " +
                                "(SELECT cc.contact_id FROM call_list_shared_contact cc WHERE cc.call_list_id = '" + searchFieldDTO.getValueId() + "' AND cc.deleted = false) ";
                        break;
                    case NOT_EQUALS:
                        result = " q_search.uuid IN " +
                                " (SELECT DISTINCT(cc.contact_id) FROM call_list_shared_contact cc WHERE cc.contact_id NOT IN " +
                                    " (SELECT cc1.contact_id FROM call_list_shared_contact cc1 WHERE cc1.call_list_id = '" + searchFieldDTO.getValueId() + "' AND cc1.deleted = false) " +
                                " AND cc.deleted = false AND cc.enterprise_id = '" + enterprise.getUuid() + "') ";
                        break;
                    case NO_VALUE:
                        result = " q_search.uuid NOT IN " +
                                "(SELECT DISTINCT(cc.contact_id) FROM call_list_shared_contact cc WHERE cc.deleted = false AND cc.contact_id IS NOT NULL AND cc.enterprise_id = '" + enterprise.getUuid() +"') ";
                        break;
                }
                break;
            case ContactField.COMING_PRODUCT_GROUP:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT contact_id FROM v_coming_product_group_contact WHERE line_of_business_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case ContactField.GOT_PRODUCT_GROUP:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT contact_id FROM v_got_product_group_contact WHERE line_of_business_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case ContactField.COMING_PRODUCT_TYPE:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT contact_id FROM v_coming_product_type_contact WHERE type_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case ContactField.GOT_PRODUCT_TYPE:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT contact_id FROM v_got_product_type_contact WHERE type_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case ContactField.COMING_PRODUCT:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT contact_id FROM v_coming_product_contact WHERE product_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case ContactField.GOT_PRODUCT:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT contact_id FROM v_got_product_contact WHERE product_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case ContactField.FIRST_NAME:
                result = " LOWER(first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.LAST_NAME:
                result = " LOWER(last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.PHONE:
                result = " LOWER(c_phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.EMAIL:
                result = " LOWER(c_email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.STREET:
                result = " LOWER(street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ZIP_CODE:
                result = " LOWER(zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.CITY:
                result = " LOWER(city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.REGION:
                result = " LOWER(region) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.COUNTRY:
                result = " LOWER(country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.TYPE:
                result = " type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case ContactField.INDUSTRY:
                result = " industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case ContactField.RELATION:
                result = " relation_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case ContactField.ACCOUNT_NAME:
                result = " LOWER(o_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ACCOUNT_VAT:
                result = " LOWER(o_vat_number) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ACCOUNT_PHONE:
                result = " LOWER(o_phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ACCOUNT_EMAIL:
                result = " LOWER(o_email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ACCOUNT_STREET:
                result = " LOWER(o_street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ACCOUNT_ZIP_CODE:
                result = " LOWER(o_zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ACCOUNT_CITY:
                result = " LOWER(o_city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ACCOUNT_STATE:
                result = " LOWER(o_state) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ACCOUNT_COUNTRY:
                result = " LOWER(o_country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.ACCOUNT_TYPE:
                result = " o_type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case ContactField.ACCOUNT_INDUSTRY:
                result = " o_industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case ContactField.ACCOUNT_SIZE:
                result = " o_size_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case ContactField.OWNER_FIRST_NAME:
                result = searchFieldDTO.getOperator().equals(Operator.NO_VALUE.toString()) ? " number_team_member = 0 " : " LOWER(owner_first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.OWNER_LAST_NAME:
                result = searchFieldDTO.getOperator().equals(Operator.NO_VALUE.toString()) ? " number_team_member = 0 " : " LOWER(owner_last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.CLOSED_SALES:
                result = " order_intake " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.CLOSED_PROFIT:
                result = " won_profit " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.GROSS_VALUE:
                result = " gross_pipeline " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.NET_VALUE:
                result = " net_pipeline " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.CLOSED_MARGIN:
                result = " (order_intake > 0 AND (won_profit / order_intake)*100 " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText()) + ")";
                break;
            case ContactField.PIPE_MARGIN:
                result = " (gross_pipeline > 0 AND (net_pipeline / gross_pipeline)*100 " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText()) + ")";
                break;
            case ContactField.MEDIAN_DEAL_SIZE:
                result = " median_deal_size " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.MEDIAN_DEAL_TIME:
                result = " median_deal_time " + populateOperatorMedianDealTime(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case ContactField.CREATED_TIME:
                result = " CAST((created_date  "+addTimeZone+")  as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;
            case ContactField.BEHAVIOR_COLOR:
                result = populateBehaviorColor(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
        }
        return result;
    }

    private String populateBehaviorColor(String operator, String valueText)
    {

        int discProfie = 0;

        if (valueText != null)
        {
            if (valueText.equalsIgnoreCase("RED"))
            {
                discProfie = 1;
            }
            else if (valueText.equalsIgnoreCase("GREEN"))
            {
                discProfie = 2;
            }
            else if (valueText.equalsIgnoreCase("BLUE"))
            {
                discProfie = 3;
            }
            else if (valueText.equalsIgnoreCase("YELLOW"))
            {
                discProfie = 4;
            }
        }
        if (operator.equalsIgnoreCase("EQUALS"))
        {
            return "disc_profile = " + discProfie;
        }
        else if (operator.equalsIgnoreCase("NOT_EQUALS"))
        {
            return "disc_profile <> " + discProfie;
        }
        else if (operator.equalsIgnoreCase("NO_VALUE"))
        {
            return "disc_profile = " + 0;
        }
        return operator;
    }


    private String getAccountQueries(SearchFieldDTO searchFieldDTO, Enterprise enterprise)
    {
        String result = "";
        switch (searchFieldDTO.getField())
        {
            case AccountField.CALL_LIST:
                switch (Operator.valueOf(searchFieldDTO.getOperator())) {
                    case EQUALS:
                        result = " q_search.uuid IN " +
                                "(SELECT ca.account_id FROM call_list_account_account ca WHERE ca.call_list_account_id = '" + searchFieldDTO.getValueId() + "' AND ca.deleted = false) ";
                        break;
                    case NOT_EQUALS:
                        result = " q_search.uuid IN " +
                                " (SELECT DISTINCT(ca.account_id) FROM call_list_account_account ca WHERE ca.account_id NOT IN " +
                                    " (SELECT ca1.account_id FROM call_list_account_account ca1 WHERE ca1.call_list_account_id = '" + searchFieldDTO.getValueId() + "' AND ca1.deleted = false) " +
                                " AND ca.deleted = false AND ca.enterprise_id = '" + enterprise.getUuid() + "') ";
                        break;
                    case NO_VALUE:
                        result = " q_search.uuid NOT IN " +
                                "(SELECT DISTINCT(ca.account_id) FROM call_list_account_account ca WHERE ca.deleted = false AND ca.enterprise_id = '" + enterprise.getUuid() +"') ";
                        break;
                }
                break;
            case AccountField.COMING_PRODUCT_GROUP:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT organisation_id FROM v_coming_product_group_organisation WHERE line_of_business_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case AccountField.GOT_PRODUCT_GROUP:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT organisation_id FROM v_got_product_group_organisation WHERE line_of_business_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case AccountField.COMING_PRODUCT_TYPE:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT organisation_id FROM v_coming_product_type_organisation WHERE type_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case AccountField.GOT_PRODUCT_TYPE:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT organisation_id FROM v_got_product_type_organisation WHERE type_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case AccountField.COMING_PRODUCT:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT organisation_id FROM v_coming_product_organisation WHERE product_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case AccountField.GOT_PRODUCT:
                result = " q_search.uuid " + (searchFieldDTO.getOperator().equals(Operator.EQUALS.toString()) ? "IN" : "NOT IN") + " (SELECT organisation_id FROM v_got_product_organisation WHERE product_id = " + "\'" + searchFieldDTO.getValueId() + "\')";
                break;
            case AccountField.NAME:
                result = " LOWER(full_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.PHONE:
                result = " LOWER(phone) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.EMAIL:
                result = " LOWER(email) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.STREET:
                result = " LOWER(street) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.ZIP_CODE:
                result = " LOWER(zip_code) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.CITY:
                result = " LOWER(city) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.STATE:
                result = " LOWER(state) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.COUNTRY:
                result = " LOWER(country) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.VAT:
                result = " LOWER(vat_number) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.TYPE:
                result = " type_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AccountField.INDUSTRY:
                result = " industry_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AccountField.SIZE:
                result = " size_id " + populateOperatorAndObject(searchFieldDTO.getOperator(), searchFieldDTO.getValueId());
                break;
            case AccountField.OWNER_FIRST_NAME:
                result = searchFieldDTO.getOperator().equals(Operator.NO_VALUE.toString()) ? " number_team_member = 0 " : " LOWER(owner_first_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.OWNER_LAST_NAME:
                result = searchFieldDTO.getOperator().equals(Operator.NO_VALUE.toString()) ? " number_team_member = 0 " : " LOWER(owner_last_name) " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.CLOSED_SALES:
                result = " order_intake" + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.CLOSED_PROFIT:
                result = " won_profit" + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.GROSS_VALUE:
                result = " gross_pipeline " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.NET_VALUE:
                result = " net_pipeline " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.CLOSED_MARGIN:
                result = " (order_intake > 0 AND (won_profit / order_intake)*100 " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText()) + ")";
                break;
            case AccountField.PIPE_MARGIN:
                result = " (gross_pipeline > 0 AND (net_pipeline / gross_pipeline)*100 " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText()) + ")";
                break;
            case AccountField.MEDIAN_DEAL_SIZE:
                result = " median_deal_size " + populateOperatorAndValue(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.MEDIAN_DEAL_TIME:
                result = " median_deal_time " + populateOperatorMedianDealTime(searchFieldDTO.getOperator(), searchFieldDTO.getValueText());
                break;
            case AccountField.CREATED_TIME:
                Integer timeZone = enterprise.getRegisteredTimeZone();
                String addTimeZone = "+ INTERVAL '"+ (timeZone == null ? '2' : timeZone) + " hour'";
                result = " CAST((q_search.created_date "+addTimeZone+") as DATE) " + populateOperatorDate(searchFieldDTO.getOperator(), searchFieldDTO.getValueDate(), searchFieldDTO.getValueText(), enterprise);
                break;
        }
        return result;
    }

    private String populatePriority(String operator, String value)
    {
        if (value.equals("LOWEST"))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return " l.priority  <= 20 ";
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return " l.priority  > 20 ";
            }
        }
        else if (value.equals("LOW"))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return " l.priority  <= 40 AND l.priority > 20 ";
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return " l.priority  <= 20 OR l.priority > 40 ";
            }
        }
        else if (value.equals("MEDIUM"))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return " l.priority  <= 60 AND l.priority > 40 ";
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return " l.priority  <= 40 OR l.priority > 60 ";
            }
        }
        else if (value.equals("HIGH"))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return " l.priority  <= 80 AND l.priority > 60 ";
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return " l.priority  <= 60 OR l.priority > 80 ";
            }
        }
        else if (value.equals("HIGHEST"))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return " l.priority  >= 80 ";
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return " l.priority  < 80 ";
            }
        }
        return "";
    }

    private String populateOperatorAndObject(String operator, String value)
    {
        String result = operatorMap.get(operator);
        if (result.equals("NO_VALUE"))
        {
            return " IS NULL";
        }
        return result + "'" + value + "'";
    }

    private String populateOperatorDate(String operator, Date date, String textValue, Enterprise enterprise)
    {
    // phai bien cai date thanh date cua serve truoc khi compare , chi can convert date thanh date hien tai cua clieh la duowc
        if (textValue != null && !textValue.isEmpty())
        {
            String pattern = "dd MMM yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try
            {
                date = simpleDateFormat.parse(textValue);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        String result = operatorMap.get(operator);
        if (result.equals("NO_VALUE"))
        {
            return " IS NULL";
        }
        return result + " '" + date + "'";
    }


    private String populateOperatorMedianDealTime(String operator, String value)
    {
        String result = operatorMap.get(operator);
        return result + " " + Integer.valueOf(value) * 1000 * 3600 * 24;
    }

    private String populateOperatorIntegerNumber(String operator, String value)
    {
        String result = operatorMap.get(operator);
        return result + " " + Integer.valueOf(value);
    }

    private String populateOperatorBoolean(String operator, String value)
    {
        String result = operatorMap.get(operator);
        if (result.equals("NO_VALUE"))
        {
            return " IS NULL";
        }
        return result + " " + Boolean.valueOf(value);
    }

    private String populateOperatorAndStringValue(String prefix, String operator, String value, FieldType fieldType)
    {
        String operatorStr = operatorMap.get(operator);
        String result = "";
        if (operator.equals("NO_VALUE"))
        {
            result = prefix + " IS NULL";
            if (fieldType.equals(FieldType.TEXT))
            {
                result = "(" + result + " OR (" + prefix + " = '')" + ")";
            }
            return result;
        }

        if (operator.equals("CONTAINS") || operator.equals("NOT_CONTAINS"))
        {
            return prefix + " " + operatorStr + " '%" + value.toLowerCase().trim() + "%'";
        }
        else
        {
            return prefix + " " + operatorStr + " " + "\'" + value + "\'";
        }
    }

    private String populateOperatorAndValue(String operator, String value)
    {
        String result = operatorMap.get(operator);
        if (result.equals("NO_VALUE"))
        {
            return " IS NULL";
        }

        if (operator.equals("CONTAINS") || operator.equals("NOT_CONTAINS"))
        {
            return " " + result + " '%" + value.toLowerCase().trim() + "%'";
        }
        else
        {
//            return result + " " + value;
            return result + " " + "\'" + value + "\'";
        }
    }


    private String populateCampaignStatus(String operator, String value)
    {
        String filterCloseGray = " now() < c.start_date ";
        String filterCloseGrayTick = " (( c.end_date < now() and c.number_step = 0 ) " +
                " OR ( c.start_date < now() and c.end_date > now() and  " +
                " (select cs.date from campaign_step cs where cs.campaign_id = c.uuid and cs.status != 0 order by cs.date asc limit 1) is not null) )";
        String filterCloseGreen = " ( c.start_date < now() and c.end_date > now() and  " +
                " (select cs.date from campaign_step cs where cs.campaign_id = c.uuid and cs.status != 0 order by cs.date asc limit 1) < now()) ";
        String filterOpenGrayTick = " c.start_date < now() and c.number_step > 0 ";
        String filterStripeGreen = " c.start_date < now() and c.end_date > now() and " +
                " (select cs.date from campaign_step cs where cs.campaign_id = c.uuid and cs.status != 0 order by cs.date asc limit 1) < now() and" +
                " (select cs.date from campaign_step cs where cs.campaign_id = c.uuid and cs.status != 0 order by cs.date desc limit 1) > now()";
        String filterOpenGreen = " c.start_date < now() and c.end_date > now() and " +
                " (select cs.date from campaign_step cs where cs.campaign_id = c.uuid and cs.status != 0 order by cs.date desc limit 1) < now()";

        if (value.equals(CampaignStatus.PREPARING.toString()))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return filterCloseGray;
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return String.format(" (%s) OR (%s) OR (%s) OR (%s) OR (%s) ", filterCloseGrayTick, filterCloseGreen, filterOpenGrayTick, filterStripeGreen, filterOpenGreen);
            }
        }
        else if (value.equals(CampaignStatus.NOT_READY.toString()))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return filterCloseGrayTick;
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return String.format(" (%s) OR (%s) OR (%s) OR (%s) OR (%s) ", filterCloseGray, filterCloseGreen, filterOpenGrayTick, filterStripeGreen, filterOpenGreen);
            }
        }
        else if (value.equals(CampaignStatus.ACTIVATED.toString()))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return filterCloseGreen;
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return String.format(" (%s) OR (%s) OR (%s) OR (%s) OR (%s) ", filterCloseGray, filterCloseGrayTick, filterOpenGrayTick, filterStripeGreen, filterOpenGreen);
            }
        }
        else if (value.equals(CampaignStatus.READY.toString()))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return filterOpenGrayTick;
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return String.format(" (%s) OR (%s) OR (%s) OR (%s) OR (%s) ", filterCloseGray, filterCloseGrayTick, filterCloseGreen, filterStripeGreen, filterOpenGreen);
            }
        }
        else if (value.equals(CampaignStatus.SENDING.toString()))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return filterStripeGreen;
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return String.format(" (%s) OR (%s) OR (%s) OR (%s) OR (%s) ", filterCloseGray, filterCloseGrayTick, filterCloseGreen, filterOpenGrayTick, filterOpenGreen);
            }
        }
        else if (value.equals(CampaignStatus.SENT_DONE.toString()))
        {
            if (operator.equals(Operator.EQUALS.toString()))
            {
                return filterOpenGreen;
            }
            else if (operator.equals(Operator.NOT_EQUALS.toString()))
            {
                return String.format(" (%s) OR (%s) OR (%s) OR (%s) OR (%s) ", filterCloseGray, filterCloseGrayTick, filterCloseGreen, filterOpenGrayTick, filterStripeGreen);
            }
        }

        return "";
    }
}
