package com.example.hookahshop_diploma.dto.newposhta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPoshtaData {
    @JsonAlias("Ref")
    private String ref;
    @JsonAlias("SettlementTypeDescription")
    private String settlementTypeDescription;
    @JsonAlias("Description")
    private String description;
    @JsonAlias("AreaDescription")
    private String areaDescription;
    @JsonAlias("SiteKey")
    private String siteKey;
    @JsonAlias("Number")
    private Long number;
}
