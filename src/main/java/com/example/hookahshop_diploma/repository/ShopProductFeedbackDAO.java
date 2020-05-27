package com.example.hookahshop_diploma.repository;


import com.example.hookahshop_diploma.model.ShopProductFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopProductFeedbackDAO extends JpaRepository<ShopProductFeedback, Long> { }
