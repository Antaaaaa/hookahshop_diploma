package com.example.hookahshop_diploma.dto.database;

import com.example.hookahshop_diploma.model.CharacterValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class CharacterValueDTO {
    private Long characterId;
    private Long valueId;
    public static CharacterValueDTO of(CharacterValue characterValue){
        CharacterValueDTO characterValueDTO = new CharacterValueDTO();
        characterValueDTO.setCharacterId(characterValue.getCharacter().getId());
        characterValueDTO.setValueId(characterValue.getValue().getId());
        return characterValueDTO;
    }
    public static List<CharacterValueDTO> of(List<CharacterValue> characterValueList){
        List<CharacterValueDTO> characterValueDTOList = new ArrayList<>();
        for (CharacterValue characterValue : characterValueList){
            characterValueDTOList.add(CharacterValueDTO.of(characterValue));
        }
        return characterValueDTOList;
    }
}
