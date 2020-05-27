package com.example.hookahshop_diploma.dto.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderDTO {
    String name;
    String surname;
    String phone;
    String address;
    String comment;
    String email;
}
