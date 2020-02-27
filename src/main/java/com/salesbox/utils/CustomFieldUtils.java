package com.salesbox.utils;

import com.salesbox.entity.enums.ObjectType;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by NEO on 2/8/2017.
 */
public class CustomFieldUtils
{
    private static String cachedSearchQuery;

    public static String querySearchCustomFieldValue() {
        if (StringUtils.isEmpty(cachedSearchQuery)) {
//            cachedSearchQuery = "SELECT " +
//                    "v.uuid AS value_id, v.object_id, v.is_checked, v.value, cf.active, cf.field_type, cf.uuid " +
//                    "FROM " +
//                    "custom_field_value v " +
//                    "INNER JOIN custom_field cf ON cf.uuid = v.custom_field_id " +
//                    "WHERE " +
//                    "CASE " +
//                    "WHEN (cf.field_type = 0 OR cf.field_type = 4) " +
//                    "THEN v.is_checked = TRUE " +
//                    "ELSE " +
//                    "TRUE " +
//                    "END " +
//                    "AND cf.active = TRUE";

            try {
                Resource resource = new ClassPathResource("sql/search_custom_field.sql");
                BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }
                br.close();
                cachedSearchQuery = stringBuilder.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return cachedSearchQuery;
    }

    public interface TableNameInSearch {
        String TASK = "t";
        String APPOINTMENT = "app";
        String LEAD = "l";
        String PROSPECT = "p";
        String ORDER_ROW = "ord";
        String ACCOUNT = "q_search";
        String CONTACT = "q_search";
    }

    public static String getTableNameByObjectType(String objectType, ObjectType customFieldObjectType) {
        switch (ObjectType.valueOf(objectType)) {
            case ACCOUNT:
                return TableNameInSearch.ACCOUNT;
            case CONTACT:
                return TableNameInSearch.CONTACT;
            case TASK:
                return TableNameInSearch.TASK;
            case LEAD:
                return TableNameInSearch.LEAD;
            case OPPORTUNITY:
                return customFieldObjectType == ObjectType.OPPORTUNITY ? TableNameInSearch.PROSPECT : TableNameInSearch.ORDER_ROW;
            case APPOINTMENT:
                return TableNameInSearch.APPOINTMENT;
            case ORDER_ROW:
                return TableNameInSearch.ORDER_ROW;
            default:
                return "";
        }
    }
}
