package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.ShopProduct;
import com.example.hookahshop_diploma.model.SubCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCatalogDAO extends JpaRepository<SubCatalog, Long> {
    SubCatalog findSubCatalogByShopProduct(ShopProduct shopProduct);
    SubCatalog findSubCatalogBySubname(String subName);
}
