package com.example.hookahshop_diploma.dto.database;

import com.example.hookahshop_diploma.model.Cart;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long id;
    private List<SelectedItemsDTO> selectedItems;

    public static CartDTO of(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setSelectedItems(SelectedItemsDTO.of(cart.getSelectedItems()));
        return cartDTO;
    }
}
