package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCharacterDAO extends JpaRepository<Character, Long> {
    Character findCharacterByName(String name);
}
