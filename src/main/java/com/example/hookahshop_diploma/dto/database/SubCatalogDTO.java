package com.example.hookahshop_diploma.dto.database;

import com.example.hookahshop_diploma.model.SubCatalog;
import lombok.Data;

import java.util.List;

@Data
public class SubCatalogDTO {
    private Long id;
    private String subname;
    private List<ShopProductDTO> shopProduct;

    public SubCatalogDTO of(SubCatalog subCatalog){
        SubCatalogDTO subCatalogDTO = new SubCatalogDTO();
        subCatalogDTO.setId(subCatalog.getId());
        subCatalogDTO.setSubname(subCatalog.getSubname());
        subCatalogDTO.setShopProduct(ShopProductDTO.of(subCatalog.getShopProduct()));
        return subCatalogDTO;
    }
}
