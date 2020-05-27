package com.example.hookahshop_diploma.repository;

import com.example.hookahshop_diploma.model.Character;
import com.example.hookahshop_diploma.model.CharacterValue;
import com.example.hookahshop_diploma.model.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CharacterValueDAO extends JpaRepository<CharacterValue, Long> {
    List<CharacterValue> findAllByCharacter(Character character);
    CharacterValue findCharacterValueByCharacterAndValue(Character character, Value value);
}
