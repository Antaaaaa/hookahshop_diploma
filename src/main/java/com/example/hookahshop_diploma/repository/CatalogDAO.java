package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogDAO extends JpaRepository<Catalog, Long> { }
