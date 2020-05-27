package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDAO extends JpaRepository<Address, Long> {
    Address findByAddress(String address);
}
