package com.example.hookahshop_diploma.dto.database;

import com.example.hookahshop_diploma.model.SelectedItems;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SelectedItemsDTO {
    private Long id;
    private Long number;
    private ShopProductDTO shopProductDTO;

    public static SelectedItemsDTO of (SelectedItems selectedItems){
        SelectedItemsDTO selectedItemsDTO = new SelectedItemsDTO();
        selectedItemsDTO.setId(selectedItems.getId());
        selectedItemsDTO.setNumber(selectedItems.getNumber());
        selectedItemsDTO.setShopProductDTO(ShopProductDTO.of(selectedItems.getShopProduct()));
        return selectedItemsDTO;
    }

    public static List<SelectedItemsDTO> of (List<SelectedItems> selectedItemsList){
        List<SelectedItemsDTO> selectedItemsDTOS = new ArrayList<>();
        for (SelectedItems selectedItem : selectedItemsList){
            selectedItemsDTOS.add(SelectedItemsDTO.of(selectedItem));
        }
        return selectedItemsDTOS;
    }
}
