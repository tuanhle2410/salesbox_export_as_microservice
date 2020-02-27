package com.salesbox.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by admin on 5/30/2017.
 */
public class CommonUtils
{
    public static String readFile(Resource resource)
    {

        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);

            StringBuilder stringBuilder = new StringBuilder();

            String line;

            while ((line = br.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }

            return stringBuilder.toString();

        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
        finally
        {
            if (H.isTrue(br))
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }
    
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
    @SafeVarargs
	public static <T> Predicate<T> distinctByKeys(Function<? super T, Object>... keyExtractors) {

        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t -> {

            final List<?> keys = Arrays.stream(keyExtractors)
                .map(ke -> ke.apply(t))
                .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;

        };

    }

    public static String toTagFormat(String productName) {
        if (StringUtils.isEmpty(productName)) {
            return "";
        }
        return "#" + productName.replaceAll("\\s", "");
    }
}

