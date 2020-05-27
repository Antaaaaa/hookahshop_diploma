package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.Address;
import com.example.hookahshop_diploma.model.ShopDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDeliveryDAO extends JpaRepository<ShopDelivery, Long> {
    ShopDelivery findByDeliveryName(String name);
    ShopDelivery findByAddress(Address address);
}
