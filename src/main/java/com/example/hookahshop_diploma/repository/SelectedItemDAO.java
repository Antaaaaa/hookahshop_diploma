package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.Cart;
import com.example.hookahshop_diploma.model.SelectedItems;
import com.example.hookahshop_diploma.model.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedItemDAO extends JpaRepository<SelectedItems, Long> {
    SelectedItems findSelectedItemsByShopProductAndCart(ShopProduct shopProduct, Cart cart);
}
