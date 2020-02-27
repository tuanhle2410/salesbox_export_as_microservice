package com.salesbox.utils;

import java.util.Map;

/**
 * Created by huynhlbq on 7/7/2015.
 */
public class QueryBuilderUtils
{

    public static String buildLikeQuery(String term, Map<String, Boolean> fieldLowerMap) {
        if (fieldLowerMap == null || fieldLowerMap.size() == 0) {
            return "";
        }
        String likeQuery = "";
        String operator = " AND (";
        for (String field : fieldLowerMap.keySet()) {
            likeQuery += operator;
            if (fieldLowerMap.get(field)) {
                field = "lower(" + field + ")";
            }
            likeQuery += " (unaccent(" + field + ") LIKE unaccent('%" + term + "%'))";
            operator = " OR ";
        }

        likeQuery += ")";
        return likeQuery;
    }
}
