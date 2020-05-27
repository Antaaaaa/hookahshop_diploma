package com.example.hookahshop_diploma.dto.database;

import com.example.hookahshop_diploma.model.Value;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ValueDTO {
    String name;
    public static ValueDTO of(Value value){
        ValueDTO valueDTO = new ValueDTO();
        valueDTO.setName(value.getName());
        return valueDTO;
    }
}
