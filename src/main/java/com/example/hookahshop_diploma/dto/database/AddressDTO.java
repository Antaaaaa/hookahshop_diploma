package com.example.hookahshop_diploma.dto.database;

import com.example.hookahshop_diploma.model.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddressDTO {
    private String address;

    public static AddressDTO of(Address address){
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddress(address.getAddress());
        return addressDTO;
    }
    public static List<AddressDTO> of(List<Address> addresses){
        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (Address address : addresses){
            addressDTOS.add(AddressDTO.of(address));
        }
        return addressDTOS;
    }
}
