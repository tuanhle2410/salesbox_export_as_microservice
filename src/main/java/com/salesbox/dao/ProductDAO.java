package com.salesbox.dao;

import com.salesbox.entity.Enterprise;
import com.salesbox.entity.LineOfBusiness;
import com.salesbox.entity.MeasurementType;
import com.salesbox.entity.Product;
import com.salesbox.entity.view.ProductView;

import java.util.List;
import java.util.UUID;

/**
 * Created by quynhtq on 4/27/14.
 */
public interface ProductDAO extends BaseDAO<Product>
{
    Product findOne(UUID uuid);

    public List<Product> findByUuidIn(List<UUID> productList);

    public List<Product> findByLineOfBusinessAndDeletedOrderByNameAsc(LineOfBusiness lineOfBusiness, Boolean deleted);

    List<Product> findByLineOfBusinessAndDeletedOrderByCustomField(LineOfBusiness lineOfBusiness, Boolean deleted, String orderBy);

    List<Product> findByLineOfBusinessAndDeletedOrderByCustomField(LineOfBusiness lineOfBusiness, Boolean deleted, String orderBy, Integer pageIndex, Integer pageSize);

    public List<Product> findByLineOfBusinessInAndDeletedOrderByNameAsc(List<LineOfBusiness> lineOfBusiness, Boolean deleted);

    public List<Product> findByMeasurementTypeAndDeleted(MeasurementType measurementType, Boolean deleted);

    public List<Product> findByLineOfBusinessAndNameAndDeletedCaseInsensitive(LineOfBusiness lineOfBusiness, String name, Boolean deleted);

    Product findByLineOfBusinessAndIsInWizard(LineOfBusiness lineOfBusiness, Boolean isInWizard);

    void removeWhereLineOfBusinessIn(List<LineOfBusiness> lineOfBusinessList);

    List<Product> findByEnterprise(Enterprise enterprise);

    long countByEnterprise(Enterprise enterprise);

    Product findByMaestranoId(String maestranoId);

    List<Product> findByEnterpriseAndNotDeleteOrderByProductName(Enterprise enterprise);

    List<Product> findByEnterpriseAndNotDeleteOrderByProductGroupName(Enterprise enterprise);

    List<Product> findByEnterpriseAndNotDeleteOrderByProductName(Enterprise enterprise, Integer pageIndex, Integer pageSize);

    List<ProductView> findByMultiLineOfBusiness(List<UUID> lobIdList);

    List<Product> findByEnterpriseAndVismaIdNotNull(Enterprise enterprise);

    List<Product> findByEnterpriseAndFortNoxIdNotNull(Enterprise enterprise);

    List<Product> findBymeasurementAndDeletedOrderByNameAsc(MeasurementType measurementType, boolean b);

    List<Product> findByProductGroupAndProductType(List<UUID> productGroupUUIDList, List<UUID> productTypeUUIDList, String orderBy);

    List<Product> findByLineOfBusinessUUIDInAndDeletedOrderByNameAsc(UUID lineOfBusinessId);

    List<Product> findByEnterpriseAndFortNoxID(Enterprise enterprise);

    Product findByEnterpriseAndFortNoxArticleID(Enterprise enterprise, String articleNumber);

    List<Product> findByEnterpriseAndFortnoxIDIn(Enterprise enterprise, List<String> fortnoxArticlesNumbers);

    Product findByEnterpriseAndName(Enterprise enterprise, String name);

    List<Product> findProductTagOptions(UUID customFieldId, String searchText);
}
