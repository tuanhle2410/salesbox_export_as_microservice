package com.salesbox.dto;

import com.salesbox.annotation.OrikaMapper;
import com.salesbox.entity.LineOfBusiness;
import com.salesbox.entity.Product;
import com.salesbox.entity.SalesMethod;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by quynhtq on 4/26/14.
 */
@OrikaMapper(mapClass = LineOfBusiness.class)
public class LineOfBusinessDTO implements Serializable
{
    private UUID uuid;
    private String name;
    private SalesMethodDTO salesMethodDTO;
    private Integer numberOfProducts;
    private Integer numberActiveProducts;

    public LineOfBusinessDTO()
    {
    }

    public LineOfBusinessDTO(UUID uuid, String name)
    {
        this.uuid = uuid;
        this.name = name;
    }

    public LineOfBusinessDTO(UUID uuid)
    {
        this.uuid = uuid;
    }

    public LineOfBusinessDTO(LineOfBusiness lineOfBusiness)
    {
        this.uuid = lineOfBusiness.getUuid();
        this.name = lineOfBusiness.getName();
        this.numberOfProducts = 0;
        this.numberActiveProducts = 0;

        for (Product product : lineOfBusiness.getProductList())
        {
            if (!product.getDeleted())
            {
                this.numberOfProducts++;
                if (product.getActive())
                {
                    this.numberActiveProducts++;
                }
            }
        }
        SalesMethod salesMethod = lineOfBusiness.getSalesMethod();
        if (salesMethod != null)
        {
            salesMethodDTO = new SalesMethodDTO();

            salesMethodDTO.setName(salesMethod.getName());
            salesMethodDTO.setUuid(salesMethod.getUuid());
            salesMethodDTO.setPrice(salesMethod.getPrice());
            salesMethodDTO.setPrivacyType(salesMethod.getPrivacyType().toString());
            salesMethodDTO.setRating(salesMethod.getRating());
            salesMethodDTO.setNotificationTime(salesMethod.getNotificationTime());
            salesMethodDTO.setLoseMeetingRatio(salesMethod.getLoseMeetingRatio());
            salesMethodDTO.setUsing(salesMethod.getUsing());
            salesMethodDTO.setKeyCode(salesMethod.getKeyCode());
        }
    }

    public SalesMethodDTO getSalesMethodDTO()
    {
        return salesMethodDTO;
    }

    public void setSalesMethodDTO(SalesMethodDTO salesMethodDTO)
    {
        this.salesMethodDTO = salesMethodDTO;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public Integer getNumberOfProducts()
    {
        return numberOfProducts;
    }

    public void setNumberOfProducts(Integer numberOfProducts)
    {
        this.numberOfProducts = numberOfProducts;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getNumberActiveProducts()
    {
        return numberActiveProducts;
    }

    public void setNumberActiveProducts(Integer numberActiveProducts)
    {
        this.numberActiveProducts = numberActiveProducts;
    }
}
