package com.salesbox.lead.calculation;

import com.salesbox.common.Constant;
import com.salesbox.constant.UserPropertyConstant;
import com.salesbox.dao.LeadDAO;
import com.salesbox.dao.UserDAO;
import com.salesbox.dao.UserMetadataDAO;
import com.salesbox.dao.WorkDataWorkDataDAO;
import com.salesbox.entity.Lead;
import com.salesbox.entity.User;
import com.salesbox.entity.UserMetadata;
import com.salesbox.entity.WorkDataWorkData;
import com.salesbox.entity.enums.WorkDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 8/26/14
 * Time: 3:25 PM
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MedianLeadTimeCalculation
{
    @Autowired
    UserDAO userDAO;

    @Autowired
    WorkDataWorkDataDAO workDataWorkDataDAO;

    @Autowired
    LeadDAO leadDAO;
    @Autowired
    UserMetadataDAO userMetadataDAO;

    public void update(User user)
    {
        List<Lead> leadList = leadDAO.findByOwnerAndDeletedAndFinished(user,false,true);
        updateForOwner(leadList, user);
    }

    public void updateForOwner(List<Lead> leadList, User owner)
    {
        Long medianLeadTime = 0l;
        if (leadList.size() >= Constant.MAX_PROSPECT_AFFECT)
        {
            medianLeadTime = getMedianLeadTime(leadList);
        }
        else
        {
            List<Lead> orderedLeadList = new ArrayList<>();
            WorkDataWorkData workDataMedianLeadTime = workDataWorkDataDAO.findByEnterpriseAndTypeAndName(owner.getUnit().getEnterprise(), WorkDataType.WORK_LOAD, Constant.MEDIAN_LEAD_TIME);
            medianLeadTime = new Long(workDataMedianLeadTime.getValue()) * 60 * 60 * 1000;
            int n = 1;
            for (Lead finishedLead : leadList)
            {
                orderedLeadList.add(finishedLead);
                long tempMedianDealTime = getMedianLeadTime(orderedLeadList);

                medianLeadTime = Math.round((1 - 1.0 / Constant.MAX_PROSPECT_AFFECT * n) * medianLeadTime
                        + 1.0 / Constant.MAX_PROSPECT_AFFECT * n * tempMedianDealTime);
                n++;
            }
        }
        UserMetadata userMetadata = userMetadataDAO.findByUserAndKey(owner, UserPropertyConstant.MEDIAN_LEAD_TIME);
        userMetadata.setValue(medianLeadTime.doubleValue());
        userMetadataDAO.save(userMetadata);
//        owner.setMedianLeadTime(medianLeadTime);
//        userDAO.save(owner);
    }

    private long getMedianLeadTime(List<Lead> leadList)
    {
        Collections.sort(leadList, new LeadComparator());
        if (leadList.size() % 2 == 1)
        {
            return leadList.get(leadList.size() / 2).getLeadTime();
        }
        else
        {
            int middleIndex = leadList.size() / 2;
            return (leadList.get(middleIndex).getLeadTime() + leadList.get(middleIndex - 1).getLeadTime()) / 2;
        }
    }
}
