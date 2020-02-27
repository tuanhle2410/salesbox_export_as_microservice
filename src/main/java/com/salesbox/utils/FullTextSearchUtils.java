package com.salesbox.utils;

import com.salesbox.common.enums.FullTextSearchRule;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullTextSearchUtils
{
    public static final String PHONE_REGEX_FOR_REMOVE = "regexp_replace(%s, '[+-.()\\s]', '', 'g')";
    private static final List<String> PHONE_SPECIAL_CHARACTERS = Arrays.asList(new String[]{"+", "-", "(", ")", ".", " "});
    private static final String REGEX_FULL_TEXT_SEARCH_RULE = "^.*(?<email>[@]+).*$|^(?<phoneNumber>[.\\-\\s()+\\d]+$)";
    private static final Pattern pattern = Pattern.compile(REGEX_FULL_TEXT_SEARCH_RULE);
    private static final String PHONE_NUMBER_GROUP = "phoneNumber";
    private static final String EMAIL_GROUP = "email";

    public static FullTextSearchRule detectRule(String searchText) {
        if (StringUtils.isEmpty(searchText)) {
            return FullTextSearchRule.OTHER;
        }
        Matcher matcher = pattern.matcher(searchText);
        if (matcher.matches()) {
            if (matcher.group(EMAIL_GROUP) != null) {
                return FullTextSearchRule.ONLY_EMAIL;
            }

            if (matcher.group(PHONE_NUMBER_GROUP) != null) {
                return FullTextSearchRule.ONLY_PHONE_NUMBER;
            }
        }
        return FullTextSearchRule.OTHER;
    }

    public static String standardizedPhoneSearch(final String searchText) {
        String result = "";
        if (searchText != null && searchText.length() > 0) {
            result = PHONE_SPECIAL_CHARACTERS.stream().reduce(searchText, (context, item) -> context.replace(item, ""));
        }
        return result;
    }
}
