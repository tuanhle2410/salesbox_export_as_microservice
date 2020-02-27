package com.salesbox.common.calculation;

/**
 * User: luult
 * Date: 7/29/14
 */
public class AdaptiveCalculation
{
    public static Double calculate(Integer newNumberAttendMeeting, Double totalTimeTravelling, Integer maxMeetingExpect, Double previousAvg)
    {
        if (newNumberAttendMeeting > maxMeetingExpect)
        {
            return totalTimeTravelling / newNumberAttendMeeting;
        }
        else
        {
            return totalTimeTravelling * 1.0 / maxMeetingExpect + (1 - 1.0 / maxMeetingExpect * newNumberAttendMeeting) * previousAvg;
        }
    }
}
