package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.WorkDataOrganisation;
import com.salesbox.entity.enums.OrganisationType;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/26/14
 * Time: 7:35 AM
 */
public interface WorkDataOrganisationDAO extends BaseDAO<WorkDataOrganisation>
{

    WorkDataOrganisation findOne(UUID uuid);

    public List<WorkDataOrganisation> findByUuidIn(Collection<UUID> uuidList);

    public List<WorkDataOrganisation> findByEnterpriseOrderByTypeDesc(Enterprise enterprise);

    public List<WorkDataOrganisation> findByEnterpriseAndTypeOrderByTypeDesc(Enterprise enterprise, OrganisationType type);

    public List<WorkDataOrganisation> findByEnterpriseAndNameIn(Enterprise enterprise, List<String> nameList);

    public WorkDataOrganisation findByEnterpriseAndNameToUppercase(Enterprise enterprise, String name);

    public WorkDataOrganisation findByEnterpriseAndKeyCodeAndType(Enterprise enterprise, String keyCode, OrganisationType type);

    public List<WorkDataOrganisation> findByEnterpriseAndType(Enterprise defaultEnterprise, OrganisationType type);

    public List<WorkDataOrganisation> findByEnterpriseIdAndType(UUID enterpriseId, OrganisationType type);

    public List<WorkDataOrganisation> findByType(OrganisationType type);

    public WorkDataOrganisation findByNameUpperCaseAndType(String name, OrganisationType organisationType);

    public List<WorkDataOrganisation> findByNameInUpperCaseAndTypeIn(List<String> name, List<OrganisationType> organisationType);

    void removeWhereEnterprise(Enterprise enterprise);
}
