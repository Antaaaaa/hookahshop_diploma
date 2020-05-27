package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDAO extends JpaRepository<Cart, Long> {
}
