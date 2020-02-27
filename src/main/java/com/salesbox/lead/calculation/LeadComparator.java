package com.salesbox.lead.calculation;

import com.salesbox.entity.Lead;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 8/27/14
 * Time: 10:48 AM
 */
public class LeadComparator implements Comparator<Lead>
{
    @Override
    public int compare(Lead o1, Lead o2)
    {
        if (o1.getLeadTime() > o2.getLeadTime())
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}
