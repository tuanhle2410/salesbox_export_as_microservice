package com.salesbox.dto.customField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomField List DTO
 * Created by NEO on 1/11/2017.
 */
public class CustomFieldListDTO implements Serializable {
    private List<CustomFieldDTO> customFieldDTOList = new ArrayList<>();

    public List<CustomFieldDTO> getCustomFieldDTOList() {
        return customFieldDTOList;
    }

    public void setCustomFieldDTOList(List<CustomFieldDTO> customFieldDTOList) {
        this.customFieldDTOList = customFieldDTOList;
    }
}
