package com.example.hookahshop_diploma.dto.database;

import com.example.hookahshop_diploma.model.Catalog;
import lombok.Data;

import java.util.List;

@Data
public class CatalogDTO {
    private Long id;
    private String name;
    private String image;
    private List<SubCatalogDTO> subCatalogList;

    public static CatalogDTO of(Catalog catalog){
        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setId(catalog.getId());
        catalogDTO.setName(catalog.getName());
        catalogDTO.setImage(catalog.getImage());
        catalogDTO.setSubCatalogList(catalogDTO.getSubCatalogList());
        return catalogDTO;
    }
}
