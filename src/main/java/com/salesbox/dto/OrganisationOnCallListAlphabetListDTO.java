package com.salesbox.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/31/2017.
 */
public class OrganisationOnCallListAlphabetListDTO
{
    private List<OrganisationOnCallListAlphabetWrapper> alphabetDTOs = new ArrayList<>();

    public List<OrganisationOnCallListAlphabetWrapper> getAlphabetDTOs()
    {
        return alphabetDTOs;
    }

    public void setAlphabetDTOs(List<OrganisationOnCallListAlphabetWrapper> alphabetDTOs)
    {
        this.alphabetDTOs = alphabetDTOs;
    }
}
