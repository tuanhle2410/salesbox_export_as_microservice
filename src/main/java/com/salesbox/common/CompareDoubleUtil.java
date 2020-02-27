package com.salesbox.common;

/**
 * Created by hungnh on 14/07/2014.
 */
public class CompareDoubleUtil
{
    public static double e = 0.0000001;

    public static boolean equals(Double d1, Double d2)
    {
        if (d1 == null && d2 == null)
        {
            return true;
        }
        else if ((d1 == null && d2 != null)
                || (d1 != null && d2 == null))
        {
            return false;
        }
        else
        {
            return Math.abs(d1 - d2) < e;
        }
    }
}
