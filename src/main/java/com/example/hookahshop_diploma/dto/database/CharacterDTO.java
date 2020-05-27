package com.example.hookahshop_diploma.dto.database;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDTO {
    @JsonAlias("name")
    private String name;
    public static CharacterDTO of(Character character){
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setName(characterDTO.getName());
        return characterDTO;
    }
}
