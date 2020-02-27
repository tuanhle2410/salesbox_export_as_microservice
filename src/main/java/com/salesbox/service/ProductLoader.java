package com.salesbox.service;

import com.salesbox.dao.LeadProductDAO;
import com.salesbox.lead.dto.LeadListDTO;
import com.salesbox.dto.ProductDTO;
import com.salesbox.entity.Lead;
import com.salesbox.entity.LeadProduct;
import com.salesbox.entity.Product;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Product: luult
 * Date: 7/16/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductLoader
{
    @Autowired
    LeadProductDAO leadProductDAO;
    @Autowired
    MapperFacade mapper;


    public void load(List<Lead> leadList, LeadListDTO leadListDTO)
    {
        Map<Lead, ArrayList<ProductDTO>> leadProductMap = new HashMap<Lead, ArrayList<ProductDTO>>();

        loadDataToMap(leadList, leadProductMap);
        putDataToDTOList(leadList, leadListDTO, leadProductMap);
    }

    private void putDataToDTOList(List<Lead> leadList, LeadListDTO leadListDTO, Map<Lead, ArrayList<ProductDTO>> leadProductDTOMap)
    {
        int index = 0;
        for (Lead lead : leadList)
        {
            leadListDTO.getLeadDTOList().get(index).setProductList(leadProductDTOMap.get(lead));
            index++;
        }
    }

    private void loadDataToMap(List<Lead> leadList, Map<Lead, ArrayList<ProductDTO>> leadProductDTOMap)
    {
        List<LeadProduct> leadProductList = new ArrayList<LeadProduct>();
        if (leadList.size() > 0)
        {
            leadProductList = leadProductDAO.findByLeadIn(leadList);
        }
        Map<Lead, ArrayList<Product>> leadProductMap = new HashMap<Lead, ArrayList<Product>>();

        for (LeadProduct leadProduct : leadProductList)
        {
            if (leadProductMap.get(leadProduct.getLead()) == null)
            {
                ArrayList<Product> productArrayList = new ArrayList<Product>();

                productArrayList.add(leadProduct.getProduct());
                leadProductMap.put(leadProduct.getLead(), productArrayList);
            }
            else
            {
                leadProductMap.get(leadProduct.getLead()).add(leadProduct.getProduct());
            }
        }

        for (Lead lead : leadList)
        {
            List<Product> productList = leadProductMap.get(lead);

            ArrayList<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
            if (productList != null)
            {
                for (Product product : productList)
                {
                    ProductDTO productDTO = mapper.map(product, ProductDTO.class);
                    productDTO.setLineOfBusinessId(product.getLineOfBusiness().getUuid());
                    productDTO.setMeasurementTypeId(product.getMeasurementType().getUuid());
                    productDTO.setMeasurementTypeName(product.getMeasurementType().getName());
                    productDTOList.add(productDTO);
                }
            }
            leadProductDTOMap.put(lead, productDTOList);
        }
    }
}
