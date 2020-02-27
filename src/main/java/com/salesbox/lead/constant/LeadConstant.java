package com.salesbox.lead.constant;

/**
 * Created by admin on 3/18/2017.
 */
public class LeadConstant
{
    /**
     * Lead status is Qualified, Unqualified, or null
     */
    public static final String STATUS_QUALIFIED = "qualified";
    public static final String STATUS_UNQUALIFIED = "unqualified";

    public static final int UUID_INDEX = 0;
    public static final long filterAllDefaultTime = 0;

    public static final Double LOWEST = Double.valueOf(0);
    public static final Double LOW = Double.valueOf(1);
    public static final Double MEDIUM = Double.valueOf(2);
    public static final Double HIGH = Double.valueOf(3);
    public static final Double  HIGHEST= Double.valueOf(4);

    public static String PRE_EXPORT_FILE_NAME = "UnqualifiedDeals_Export_";

}
