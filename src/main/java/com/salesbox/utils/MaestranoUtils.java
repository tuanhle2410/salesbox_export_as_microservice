package com.salesbox.utils;

import com.maestrano.Maestrano;
import com.maestrano.configuration.Preset;
import com.maestrano.net.ConnecClient;
import com.salesbox.entity.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GEM on 12/23/2017.
 */
@Service
@Transactional
public class MaestranoUtils
{

    public Map<String, Object> generateEntityMap(String groupId, Task task)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("group_id", groupId);
        Date now = new Date();

        result.put("created_at", task.getCreatedDate() != null ? task.getCreatedDate() : now);
        result.put("updated_at", now);
        String taskName = task.getFocusWorkData() != null ? task.getFocusWorkData().getName() : task.getFocusActivity().getName();
        result.put("name", taskName);
        if (task.getFinished())
        {
            result.put("completed_date", task.getDateAndTime());
            result.put("status", "COMPLETED");
        }

        //assignee
        if (task.getOwner() != null)
        {
            User user = task.getOwner();
            if (user.getMaestranoUserId() != null)
            {
                Map<String, String> assignee = new HashMap<>();
                assignee.put("role", "SUPERVISOR");
                assignee.put("rights", "ALL");
                assignee.put("status", "ACTIVE");
                assignee.put("user", user.getUuid().toString());
                result.put("assignees", Arrays.asList(assignee));
            }
        }
        return result;
    }

    public final static Logger logger = Logger.getLogger(MaestranoUtils.class);
    public Map<String, Object> generateEntityLeadContactMap(String groupId, Contact contact)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("groupId", groupId);
        result.put("is_lead", true);
        Date now = new Date();
        if (contact.getCreatedDate() != null)
        {
            result.put("created_at", contact.getCreatedDate());
        }
        else
        {
            result.put("created_at", now);
        }
        result.put("updated_at", now);
        result.put("job_title", contact.getTitle());
        //organisation
        if (contact.getOrganisation() != null)
        {
            Organisation organisation = contact.getOrganisation();
            if (organisation.getMaestranoId() != null)
            {
                result.put("organization_id", organisation.getMaestranoId());
            }
        }
        result.put("first_name", contact.getFirstName());
        result.put("last_name", contact.getLastName());
        //phone
        if (contact.getPhone() != null)
        {
            Map<String, String> phoneMap = new HashMap<>();
            phoneMap.put("landline", contact.getPhone());
            result.put("phone_work", phoneMap);
        }
        //email
        if (contact.getEmail() != null)
        {
            Map<String, String> emailMap = new HashMap<>();
            emailMap.put("address", contact.getEmail());
            result.put("email", emailMap);
        }
        //address
        if (contact.getStreet() != null || contact.getCity() != null
                || contact.getZipCode() != null || contact.getCountry() != null)
        {
            Map<String, Object> addressMap = new HashMap<>();
            Map<String, String> billingAddressMap = new HashMap<>();
            billingAddressMap.put("line1", contact.getStreet());
            billingAddressMap.put("city", contact.getCity());
            billingAddressMap.put("postal_code", contact.getZipCode());
            billingAddressMap.put("country", contact.getCountry());
            addressMap.put("billing", billingAddressMap);
            result.put("address_work", addressMap);
        }
        return result;
    }

    public void deleteLeadInMaestrano(String groupId, Lead lead, String maestranoMarketPlace)
    {
        logger.error("Deleting a lead(contact) into Maestrano");
        if (lead.getMaestranoId() != null)
        {
            Map<String, Object> entityMap = lead.getContact() != null
                    ? generateEntityLeadContactMap(groupId, lead.getContact())
                    : new HashMap<String, Object>();
            try
            {
                Preset preset = Maestrano.get(maestranoMarketPlace);
                ConnecClient connecClient = new ConnecClient(preset);
                entityMap.put("status", "INACTIVE ");
                Map<String, Object> result = connecClient.update("people", groupId, lead.getMaestranoId(), entityMap);

                if (result.get("errors") != null)
                {
                    logger.error("Error when delete lead(contact) on Maestrano " + lead.getMaestranoId());
                    JSONObject jsonObject = new JSONObject(result);
                    logger.error(jsonObject);
                }

            }
            catch (Exception e)
            {
                logger.error("Exception when deleting lead(contact) on Maestrano");
                logger.error(ExceptionUtils.getFullStackTrace(e));
            }
        }
        else if (lead.getMaestranoOrgId() != null)
        {
            Map<String, Object> entityMap = lead.getOrganisation() != null
                    ? generateEntityLeadOrganisationMap(groupId, lead.getOrganisation())
                    : new HashMap<String, Object>();
            try
            {
                Preset preset = Maestrano.get(maestranoMarketPlace);
                ConnecClient connecClient = new ConnecClient(preset);
                entityMap.put("status", "INACTIVE ");
                Map<String, Object> result = connecClient.update("organizations", groupId, lead.getMaestranoOrgId(), entityMap);

                if (result.get("errors") != null)
                {
                    logger.error("Error when delete lead(Org) on Maestrano " + lead.getMaestranoOrgId());
                    JSONObject jsonObject = new JSONObject(result);
                    logger.error(jsonObject);
                }

            }
            catch (Exception e)
            {
                logger.error("Exception when deleting lead(Org) on Maestrano");
                logger.error(ExceptionUtils.getFullStackTrace(e));
            }
        }

    }

    private Map<String, Object> generateEntityLeadOrganisationMap(String groupId, Organisation organisation)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("groupId", groupId);
        result.put("is_lead", true);

        Date now = new Date();
        if (organisation.getCreatedDate() != null)
        {
            result.put("created_at", organisation.getCreatedDate());
        }
        else
        {
            result.put("created_at", now);
        }

        result.put("updated_at", now);
        result.put("name", organisation.getName());
        if (organisation.getIndustry() != null)
        {
            result.put("industry", organisation.getIndustry().getName());
        }
        //address
        Map<String, Object> addressMap = new HashMap<>();
        Map<String, String> billingAddress = new HashMap<>();
        if (organisation.getStreet() != null)
        {
            billingAddress.put("line1", organisation.getStreet());
        }
        if (organisation.getCity() != null)
        {
            billingAddress.put("city", organisation.getCity());
        }
        if (organisation.getCountry() != null)
        {
            billingAddress.put("country", organisation.getCountry());
        }
        if (organisation.getZipCode() != null)
        {
            billingAddress.put("postal_code", organisation.getZipCode());
        }
        addressMap.put("billing", billingAddress);
        result.put("address", addressMap);

        //email
        if (organisation.getEmail() != null)
        {
            Map<String, String> emailMap = new HashMap<>();
            emailMap.put("address", organisation.getEmail());
            result.put("email", emailMap);
        }
        //phone
        if (organisation.getPhone() != null)
        {
            Map<String, String> phoneMap = new HashMap<>();
            phoneMap.put("mobile", organisation.getPhone());
            result.put("phone", phoneMap);
        }

        return result;
    }


    public void deleteOpportunityInMaestrano(String groupId, String entityId, String maestranoMarketPlace)
    {
        logger.error("Deleting an opportunity into Maestrano");
        try
        {
            Preset preset = Maestrano.get(maestranoMarketPlace);
            ConnecClient connecClient = new ConnecClient(preset);
            Map<String, Object> entityMap = new HashMap<>();
            entityMap.put("status", "INACTIVE ");
            Map<String, Object> result = connecClient.update("opportunities", groupId, entityId, entityMap);
            if (result.get("errors") != null)
            {
                logger.error("Error when deleting a opportunity on Maestrano " + entityId);
                JSONObject jsonObject = new JSONObject(result);
                logger.error(jsonObject);

            }
            else
            {
                //do nothing
            }

        }
        catch (Exception e)
        {
            logger.error("Exception when deleting opportunity on Maestrano");
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }

    }

    public void deleteSalesOrderInMaestrano(String groupId, String entityId, String maestranoMarketPlace)
    {
        logger.error("Deleting an opportunity into Maestrano");
        try
        {
            Preset preset = Maestrano.get(maestranoMarketPlace);
            ConnecClient connecClient = new ConnecClient(preset);
            Map<String, Object> entityMap = new HashMap<>();
            entityMap.put("status", "INACTIVE ");
            Map<String, Object> result = connecClient.update("sales_orders", groupId, entityId, entityMap);
            if (result.get("errors") != null)
            {
                logger.error("Error when deleting a Sales Order on Maestrano " + entityId);
                JSONObject jsonObject = new JSONObject(result);
                logger.error(jsonObject);

            }

        }
        catch (Exception e)
        {
            logger.error("Exception when deleting opportunity on Maestrano");
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }

    }
}
