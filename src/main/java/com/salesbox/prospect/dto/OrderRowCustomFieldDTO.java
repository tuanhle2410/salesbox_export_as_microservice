package com.salesbox.prospect.dto;


import com.salesbox.dto.customField.CustomFieldDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GEM on 6/22/2017.
 */
public class OrderRowCustomFieldDTO implements Serializable
{
    private OrderRowDTO orderRowDTO;
    private List<CustomFieldDTO> listCustomFieldDTOs;

    public OrderRowDTO getOrderRowDTO()
    {
        return orderRowDTO;
    }

    public void setOrderRowDTO(OrderRowDTO orderRowDTO)
    {
        this.orderRowDTO = orderRowDTO;
    }

    public List<CustomFieldDTO> getListCustomFieldDTOs()
    {
        return listCustomFieldDTOs;
    }

    public void setListCustomFieldDTOs(List<CustomFieldDTO> listCustomFieldDTOs)
    {
        this.listCustomFieldDTOs = listCustomFieldDTOs;
    }
}
