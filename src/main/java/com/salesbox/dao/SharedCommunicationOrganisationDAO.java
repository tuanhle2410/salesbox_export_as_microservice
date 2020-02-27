package com.salesbox.dao;

import com.salesbox.entity.SharedCommunicationOrganisation;
import com.salesbox.entity.SharedOrganisation;
import com.salesbox.entity.enums.CommunicationType;

import java.util.List;
import java.util.UUID;

/**
 * Created by hunglv on 9/4/14.
 */
public interface SharedCommunicationOrganisationDAO extends BaseDAO<SharedCommunicationOrganisation>
{
    List<SharedCommunicationOrganisation> findByTypeInAndOrganisation(List<CommunicationType> communicationTypeList, SharedOrganisation sharedOrganisation);

    List<SharedCommunicationOrganisation> findByValueInAndTypeIn(List<String> valueList, List<CommunicationType> communicationTypeList);

    List<SharedCommunicationOrganisation> findByTypeInAndSharedOrganisation(List<CommunicationType> typeList, SharedOrganisation sharedOrganisation);

    List<SharedCommunicationOrganisation> findByTypeInAndSharedOrganisationIn(List<CommunicationType> typeList, List<SharedOrganisation> sharedOrganisation);


    List<SharedCommunicationOrganisation> findByTypeInAndSharedOrganisationNotIsMain(List<CommunicationType> communicationTypeList, SharedOrganisation sharedOrganisation);

    public void removeWhereSharedOrganisation(SharedOrganisation sharedOrganisation);

    public void removeWhereSharedOrganisationIn(List<SharedOrganisation> sharedOrganisations);

    public void removeWhereSharedOrganisationAndTypeIn(SharedOrganisation sharedOrganisation, List<CommunicationType> communicationTypeList);

    public List<UUID> findSharedOrganisationIdByValueInAndTypeInAndSharedOrganisationIsNot(List<String> valueList, List<CommunicationType> communicationTypeList, SharedOrganisation sharedOrganisation);

    List<SharedCommunicationOrganisation> findBySharedOrganisation(SharedOrganisation sharedOrganisation);

    SharedOrganisation findSharedOrganisationByValueAndTypeInAndSharedOrganisationIsNotNull(String value, List<CommunicationType> communicationTypeList);

    SharedCommunicationOrganisation findByValueAndTypeInAndSharedOrganisationIsNotNull(String value, List<CommunicationType> communicationTypeList);

    List<SharedCommunicationOrganisation> findByValueInAndTypeInAndSharedContactIsNotNull(List<String> valueList, List<CommunicationType> communicationTypeList);

    SharedCommunicationOrganisation findPhoneMainBySharedOrganisation(SharedOrganisation sharedOrganisation, List<CommunicationType> communicationTypeList);

    SharedCommunicationOrganisation findEmailMainBySharedOrganisation(SharedOrganisation sharedOrganisation, List<CommunicationType> communicationTypeList);

    SharedCommunicationOrganisation findByValueAndTypeInAndSharedOrganisation(String value, List<CommunicationType> communicationTypeList, SharedOrganisation sharedOrganisation);

    List<Object[]> findSharedOrganisationIdAndSharedCommunicationOrganisationBySharedOrganisationIn(List<SharedOrganisation> sharedOrganisationList);

    List<SharedOrganisation> findSharedOrganisationByTypeInAndSharedOrganisationIn(List<CommunicationType> communicationTypeList, List<SharedOrganisation> sharedOrganisationList);

    List<SharedOrganisation> findSharedOrganisationByValueInAndTypeIn(List<String> valueList, List<CommunicationType> communicationTypeList);

    List<String> findValueBySharedOrganisation(SharedOrganisation sharedOrganisation);

    List<String> findValueBySharedOrganisationIn(List<SharedOrganisation> sharedOrganisationList);

    List<SharedCommunicationOrganisation> findBySharedOrganisationIn(List<SharedOrganisation> sharedOrganisationList);
}
