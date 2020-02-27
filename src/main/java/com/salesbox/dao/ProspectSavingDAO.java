package com.salesbox.dao;

import com.salesbox.entity.ProspectSaving;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Linhnn
 * Date: 12/22/17
 * Time: 4:00 PM
 */
public interface ProspectSavingDAO extends BaseDAO<ProspectSaving>
{
    ProspectSaving findOne(UUID uuid);
}
