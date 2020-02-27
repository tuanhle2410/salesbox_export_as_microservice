package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.ImportExportHistory;
import com.salesbox.entity.enums.ActionType;

import java.util.List;

/**
 * Created by hunglv on 9/29/14.
 */
public interface ImportExportHistoryDAO extends BaseDAO<ImportExportHistory>
{
    List<ImportExportHistory> findByEnterpriseAndActionType(Enterprise enterprise, ActionType actionType);

    void removeWhereEnterprise(Enterprise enterprise);
}
