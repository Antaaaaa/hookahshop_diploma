package com.example.hookahshop_diploma.dto.database;

import com.example.hookahshop_diploma.model.ShopProductFeedback;
import lombok.Data;

@Data
public class ShopProductFeedbackDTO {
    private Long id;
    private String feedback;
    private Integer rating;
    // private ShopUserDTO user;
    private String user;

    public static ShopProductFeedbackDTO of(ShopProductFeedback shopProductFeedback){
        ShopProductFeedbackDTO shopProductFeedbackDTO = new ShopProductFeedbackDTO();
        shopProductFeedbackDTO.setId(shopProductFeedback.getId());
        shopProductFeedbackDTO.setFeedback(shopProductFeedback.getFeedback());
        shopProductFeedbackDTO.setRating(shopProductFeedback.getRating());
        shopProductFeedbackDTO.setUser(shopProductFeedback.getUser().getUsername());
        return shopProductFeedbackDTO;
    }
}
