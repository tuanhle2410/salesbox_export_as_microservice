package com.salesbox.common.calculation;

import com.salesbox.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luult on 10/6/2014.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CompareListUtils
{
    public List<User> findCommonUUIDList(List<User> oldUserList, List<User> newUserList)
    {
        List<User> commonIdList = new ArrayList<>();
        for (User oldUser : oldUserList)
        {
            for (User newUser : newUserList)
            {
                if (oldUser.getUuid().compareTo(newUser.getUuid()) == 0)
                {
                    commonIdList.add(newUser);
                }
            }
        }
        return commonIdList;
    }

    public List<User> findDiff(List<User> userList, List<User> commonUserList)
    {
        List<User> result = new ArrayList<>();
        for (User user : userList)
        {
            boolean found = false;
            for (User commonUser : commonUserList)
            {
                if (user.getUuid().compareTo(commonUser.getUuid()) == 0)
                {
                    found = true;
                }
            }
            if (!found)
            {
                result.add(user);
            }
        }
        return result;
    }

}
