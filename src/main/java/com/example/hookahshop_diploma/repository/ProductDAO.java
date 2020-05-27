package com.example.hookahshop_diploma.repository;


import com.example.hookahshop_diploma.model.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<ShopProduct, Long> {
    List<ShopProduct> findAllByType(String type);

}
