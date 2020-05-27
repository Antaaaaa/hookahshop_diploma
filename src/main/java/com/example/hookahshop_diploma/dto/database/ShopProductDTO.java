package com.example.hookahshop_diploma.dto.database;

import com.example.hookahshop_diploma.model.ShopProduct;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShopProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Long amount;
    private String imageUrl;
    private String type;
    private List<CharacterValueDTO> characterValue;
    private List<ShopProductFeedbackDTO> shopProductFeedbacks;

    public static ShopProductDTO of(ShopProduct shopProduct){
        ShopProductDTO shopProductDTO = new ShopProductDTO();
        shopProductDTO.setId(shopProduct.getId());
        shopProductDTO.setName(shopProduct.getName());
        shopProductDTO.setPrice(shopProduct.getPrice());
        shopProductDTO.setDescription(shopProduct.getDescription());
        shopProductDTO.setAmount(shopProduct.getAmount());
        shopProductDTO.setImageUrl(shopProduct.getImageUrl());
        shopProductDTO.setType(shopProduct.getType());
        shopProductDTO.setCharacterValue(CharacterValueDTO.of(shopProduct.getCharacterValue()));
        return shopProductDTO;
    }
    public static List<ShopProductDTO> of(List<ShopProduct> shopProducts){
        List<ShopProductDTO> shopProductDTOS = new ArrayList<>();
        for (ShopProduct shopProduct : shopProducts){
            shopProductDTOS.add(ShopProductDTO.of(shopProduct));
        }
        return shopProductDTOS;
    }
}

