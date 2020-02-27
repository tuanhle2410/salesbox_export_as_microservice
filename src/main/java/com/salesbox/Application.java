package com.salesbox;

import com.salesbox.organisation.dto.OrganisationFilterDTO;
import com.salesbox.service.organisation.ExportOrganisationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application
{
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        ExportOrganisationService exportOrganisationService = context.getBean(ExportOrganisationService.class);

        String token = "88f8b24d-a5f9-4e27-a587-a3a6a254dce1";
        OrganisationFilterDTO organisationFilterDTO = new OrganisationFilterDTO();
        organisationFilterDTO.setRoleFilterType("Person");
        organisationFilterDTO.setRoleFilterValue("15421e5d-9f46-4d46-916a-73f440c09cc7");
        organisationFilterDTO.setCustomFilter("active");
        organisationFilterDTO.setOrderBy("closedSales");
        System.out.println(exportOrganisationService.exportAdvancedSearch(token, organisationFilterDTO, ));
    }
}
