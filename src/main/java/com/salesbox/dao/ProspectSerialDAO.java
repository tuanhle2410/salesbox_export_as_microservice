package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.ProspectSerial;

import java.util.List;

/**
 * User: luult
 * Date: 7/16/14
 */
public interface ProspectSerialDAO extends BaseDAO<ProspectSerial>
{
    ProspectSerial findByEnterpriseAndYear(Enterprise enterprise, Integer year);

    List<ProspectSerial> findAll();

    void deleteAll();
}
