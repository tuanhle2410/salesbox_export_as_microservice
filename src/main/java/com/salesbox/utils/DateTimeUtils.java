package com.salesbox.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by huynhlbq on 8/14/2015.
 */
public class DateTimeUtils
{
    public static Date getStartOfDayByTimeZone(Date date, String timezone) {
//        java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone(ZoneId.of(timezone));
        TimeZone timeZone = TimeZone.getTimeZone("GMT" + timezone);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.setTimeZone(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDayByTimeZone(Date date, String timezone) {
//        java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone(ZoneId.of(timezone));
        TimeZone timeZone = TimeZone.getTimeZone("GMT" + timezone);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.setTimeZone(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getEndOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        setTimeToEndOfDay(calendar);
        return calendar.getTime();
    }

    public static Date getStartOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
    }

    public static Date getEndOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int prevQuarter = calendar.get(Calendar.MONTH) / 3 + 1;
        switch (prevQuarter) {
            case 3:
                calendar.set(Calendar.DAY_OF_MONTH, 30);
                calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
                break;
            // return September 30
            case 2:
                calendar.set(Calendar.DAY_OF_MONTH, 30);
                calendar.set(Calendar.MONTH, Calendar.JUNE);
                break;
            // return June 30
            case 1:
                calendar.set(Calendar.DAY_OF_MONTH, 31);
                calendar.set(Calendar.MONTH, Calendar.MARCH);
                break;
            // return March 31
            case 0:
            default:
                calendar.set(Calendar.DAY_OF_MONTH, 31);
                calendar.set(Calendar.MONTH, Calendar.DECEMBER);
                // return December 31
        }
        setTimeToEndOfDay(calendar);
        return calendar.getTime();
    }


    public static Date getStartOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int prevQuarter = calendar.get(Calendar.MONTH) / 3 + 1;
        switch (prevQuarter) {
            case 3:
                calendar.set(Calendar.MONTH, Calendar.JULY);
                break;
            // return July 1
            case 2:
                calendar.set(Calendar.MONTH, Calendar.APRIL);
                break;
            // return April 1
            case 1:
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            // return January 1
            case 0:
            default:
                calendar.set(Calendar.MONTH, Calendar.OCTOBER);
                // return October 1
        }
        setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
    }


    public static Date getStartOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
    }

    public static Date getEndOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndOfDay(calendar);
        return calendar.getTime();
    }

    public static Date getEndOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        setTimeToEndOfDay(calendar);
        return calendar.getTime();
    }

    public static Date getStartOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() - calendar.get(Calendar.DAY_OF_WEEK) + 1);
        setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setTimeToEndOfDay(calendar);
        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
    }

    public static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static void setTimeToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    public static Date getStartOfLast12Moth()
    {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        start.add(Calendar.YEAR, -1);

        return start.getTime();
    }
}
