package com.example.hookahshop_diploma.dto.newposhta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResponce {
    private Boolean success;
    @JsonAlias("data")
    private List<NewPoshtaData> newPoshtaData;
}
