package com.salesbox.utils;

import com.salesbox.entity.ProspectActive;
import com.salesbox.entity.ProspectBase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tuantx on 12/22/2015.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProspectUtils
{

    public Double getRealProspectProgress(ProspectBase prospect)
    {
        Integer progressProspect = prospect.getProspectProgress();
        long numberActiveTask = prospect.getNumberActiveTask();
        long numberActiveMeeting = prospect.getNumberActiveMeeting();

        if (prospect instanceof ProspectActive)
        {
            if (numberActiveTask == 0 && numberActiveMeeting == 0)
            {
                return (double) (progressProspect * (100 - prospect.getSalesMethod().getLoseMeetingRatio()) / 100);
            }
            return Double.valueOf(progressProspect);
        }
        return Double.valueOf(progressProspect);
    }

}
