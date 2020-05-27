package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.ShopOrder;
import com.example.hookahshop_diploma.model.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<ShopOrder, Long> {
    List<ShopOrder> findAllByUserAndStatusIsNotNull(ShopUser user);
    ShopOrder findShopOrderByUserAndStatusIsNull(ShopUser user);
    List<ShopOrder> findAllByStatusIsNotNull();
    ShopOrder findShopOrderByIdAndStatusIsNotNull(Long id);
}
