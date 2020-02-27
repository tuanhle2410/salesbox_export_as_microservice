package com.salesbox.dao;

import com.salesbox.entity.CommunicationOrganisation;
import com.salesbox.entity.Enterprise;
import com.salesbox.entity.Organisation;
import com.salesbox.entity.enums.CommunicationType;

import java.util.List;
import java.util.UUID;

/**
 * Created by hunglv on 9/4/14.
 */
public interface CommunicationOrganisationDAO extends BaseDAO<CommunicationOrganisation>
{
    List<CommunicationOrganisation> findByValueInAndTypeInAndOrganisation(List<String> valueList, List<CommunicationType> communicationTypeList, Organisation organisation);

    List<CommunicationOrganisation> findByTypeInAndOrganisation(List<CommunicationType> communicationTypeList, Organisation organisation);

    List<CommunicationOrganisation> findByOrganisation(List<Organisation> organisationList);

    List<CommunicationOrganisation> findByOrganisationUUID(UUID organisationID);

    List<CommunicationOrganisation> findByTypeInAndContactNotIsMain(List<CommunicationType> communicationTypeList, Organisation organisation);

    public void removeWhereOrganisation(Organisation organisation);

    public List<UUID> findOrganisationIdByValueInAndTypeInAndOrganisationEnterpriseAndOrganisationDeleted(List<String> valueList, List<CommunicationType> communicationTypeList, Enterprise enterprise, boolean deleted);

    List<CommunicationOrganisation> findByValueInAndTypeInAndOrganisationEnterprise(List<String> valueList, List<CommunicationType> communicationTypeList, Enterprise enterprise);

    public CommunicationOrganisation findPhoneMainByOrganisation(Organisation organisation, List<CommunicationType> communicationTypeList);

    List<Organisation> findOrganisationByValueInAndTypeInAndOrganisationEnterprise(List<String> valueList, List<CommunicationType> communicationTypeList, Enterprise enterprise);

    List<CommunicationOrganisation> findByTypeInAndOrganisationIn(List<CommunicationType> communicationTypeList, List<Organisation> organisationList);

    void removeWhereOrganisationIn(List<Organisation> organisationList);

    void removeWhereOrganisationInAndTypeIn(List<Organisation> organisationList, List<CommunicationType> typeList);

    List<UUID> findOrganisationIdByValueInAndTypeInAndOrganisationEnterpriseAndOrganisationDeletedAndOrganisationIsNot(List<String> valueList, List<CommunicationType> typeList, Enterprise enterprise, boolean deleted, Organisation organisation);

    List<Organisation> findOrganisationByValueInAndTypeInAndOrganisationEnterpriseAndOrganisationDeleted(List<String> valueList, List<CommunicationType> typeList, Enterprise enterprise, boolean deleted);

    List<String> findValueByOrganisation(Organisation organisation);

    List<String> findValueByOrganisationIn(List<Organisation> organisationList);

    Organisation findOrganisationByValueInAndTypeIn(String value, List<CommunicationType> communicationTypeList, Enterprise enterprise);

    void deleteByOrganisationIn(List<Organisation> deletedOrganisations);
}
