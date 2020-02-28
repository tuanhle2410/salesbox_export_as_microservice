package com.salesbox.controller;

import com.salesbox.dto.ExportResultDTO;
import com.salesbox.exception.ServiceException;
import com.salesbox.service.organisation.ExportOrganisationService;
import org.apache.commons.codec.EncoderException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@Controller
@RequestMapping(value = "exportAdvancedSearch")
public class AdvanceSearchController
{

    @Autowired
    ExportOrganisationService exportOrganisationService;

    @RequestMapping(value = "/organisation", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    ExportResultDTO exportAdvancedSearch(@RequestParam @Named("token") String token,
                                         @RequestParam @Named("filterDTO") String filterDTO,
                                         HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException, ParseException, EncoderException, IllegalAccessException, InvalidFormatException
    {
        return  exportOrganisationService.exportAdvancedSearch(token, filterDTO);
    }
}
