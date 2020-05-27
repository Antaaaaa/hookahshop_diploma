package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopValueDAO extends JpaRepository<Value, Long> {
    Value findValueByName(String name);
}
