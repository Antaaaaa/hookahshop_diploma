package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.ShopOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderStatusDAO extends JpaRepository<ShopOrderStatus, Long> {
    ShopOrderStatus findByStatusName(String name);
}
