package com.salesbox.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 4/25/2017.
 */
public class TaskDateUtil
{
    private static final int DIFF_HOUR_DAY = 7;
    private static final int END_MINUTE_DAY = 30;
    public static final int NUMBER_DAY_LOOP = 30;
    public static final String START_WORKING_TIME_KEY = "startWorkingTime";
    public static final String END_WORKING_TIME_KEY = "endWorkingTime";

    public static Date getDateAfterThirtyMinutes(Date currentDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, 30);
        return calendar.getTime();
    }

    public static long getDiffTimeInMinuteBetweenTwoDate(Date fromDate, Date toDate)
    {
        return (long) (toDate.getTime() - fromDate.getTime()) / (60 * 1000);
    }

    public static Date getTomorrowDate(Date currentDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Map<String, Date> getTomorowWorkingTimeForTask(Date currentDate)
    {
        Map<String, Date> mapWorkingDate = new HashMap<String, Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 1);
        Date startWorkingTime = calendar.getTime();
        mapWorkingDate.put(START_WORKING_TIME_KEY, startWorkingTime);

        calendar.add(Calendar.HOUR_OF_DAY, DIFF_HOUR_DAY);
        calendar.add(Calendar.MINUTE, END_MINUTE_DAY);
        Date endWorkingTime = calendar.getTime();
        mapWorkingDate.put(END_WORKING_TIME_KEY, endWorkingTime);
        return mapWorkingDate;
    }
}
