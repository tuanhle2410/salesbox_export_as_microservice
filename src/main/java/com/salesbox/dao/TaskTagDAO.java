package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.TaskTag;

import java.util.List;
import java.util.UUID;

/**
 * Created by hungnh on 8/5/15.
 */
public interface TaskTagDAO extends BaseDAO<TaskTag>
{
    List<TaskTag> findDefaultAndManuallyAddedByEnterprise(Enterprise enterprise);

    public TaskTag findByName(String name);

    TaskTag findOne(UUID uuid);
}
