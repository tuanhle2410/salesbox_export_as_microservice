package com.salesbox.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 4/7/2017.
 */
public class H
{

    public static Boolean isTrue(Object obj)
    {

        if (obj == null)
        {
            return false;
        }

        if (obj instanceof Boolean)
        {
            return ((Boolean) obj);
        }

        if (obj instanceof String)
        {
            return !((String) obj).trim().isEmpty();
        }

        if (obj instanceof Number)
        {
            return !obj.equals(new Integer(0));
        }

        if (obj instanceof Collection)
        {
            return !((Collection) obj).isEmpty();
        }

        return true;
    }

    public static List findAll(List list, Closure closure)
    {

        if (!isTrue(list))
        {
            return null;
        }

        List list_ = new ArrayList();

        for (Object item : list)
        {

            closure.it = item;

            try
            {
                closure.run();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

            if (isTrue(closure.returnResult))
            {
                list_.add(item);
            }
        }

        return list_;
    }

    public static Object find(List list, Closure closure)
    {

        if (!isTrue(list))
        {
            return null;
        }

        for (Object item : list)
        {

            closure.it = item;

            try
            {
                closure.run();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

            if (isTrue(closure.returnResult))
            {
                return item;
            }
        }

        return null;
    }

    public static List collect(List list, Closure closure)
    {

        if (!isTrue(list))
        {
            return null;
        }

        List list_ = new ArrayList();

        for (Object item : list)
        {

            closure.it = item;

            try
            {
                closure.run();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

            list_.add(closure.returnResult);
        }

        return list_;
    }

    public static Object each(List list, Closure closure)
    {

        if (!isTrue(list))
        {
            return null;
        }

        for (Object item : list)
        {

            closure.it = item;

            try
            {
                closure.run();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return closure.returnResult;
    }

    public static List<UUID> convert(List<String> uuids)
    {
        return H.collect(uuids, new Closure()
        {
            @Override
            public void run()
            {
                this.returnResult = UUID.fromString((String) this.it);
            }
        });
    }
}
