package com.salesbox.service.lead;

import com.salesbox.constant.UserPropertyConstant;
import com.salesbox.dao.LeadDAO;
import com.salesbox.dao.UserMetadataDAO;
import com.salesbox.entity.User;
import com.salesbox.entity.UserMetadata;
import com.salesbox.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 8/26/14
 * Time: 3:25 PM
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MedianLeadConversionCalculation
{
    @Autowired
    LeadDAO leadDAO;
    @Autowired
    UserMetadataDAO userMetadataDAO;

    public void update(User user)
    {
        Date last12Months = DateTimeUtils.getStartOfLast12Moth();
        Date now = new Date();

        Long numberDone = leadDAO.countDoneStartDateAndEndDateBetween(last12Months, now, Arrays.asList(user));
        Long numberConverted = leadDAO.countDoneAndProspectIdStartDateAndEndDateBetween(last12Months, now, Arrays.asList(user));
        Double result = 0.0;

        if (numberDone != null && numberConverted != null)
        {
            result = 1.0 * numberConverted / numberDone;
        }

        UserMetadata userMetadata = userMetadataDAO.findByUserAndKey(user, UserPropertyConstant.MEDIAN_LEAD_CONVERSION);
        userMetadata.setValue(result);
        userMetadataDAO.save(userMetadata);
    }

}
