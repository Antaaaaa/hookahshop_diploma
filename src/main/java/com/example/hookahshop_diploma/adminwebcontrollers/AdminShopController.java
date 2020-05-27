package com.example.hookahshop_diploma.adminwebcontrollers;

import com.example.hookahshop_diploma.model.ShopProduct;
import com.example.hookahshop_diploma.model.ShopProductFeedback;
import com.example.hookahshop_diploma.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminShopController {
    @Autowired
    private ShopService service;

    // DELETING USER'S FEEDBACKS
    @RequestMapping(value = "/shop/{item}/delete-comment/{id}", method = RequestMethod.POST)
    public String deleteComment(@PathVariable Long item, @PathVariable Long id){
        ShopProduct shopProduct = service.findProductById(item);
        List<ShopProductFeedback> shopProductFeedbacks = shopProduct.getShopProductFeedbacks();
        List<ShopProductFeedback> newShopProductFeedbacks = new ArrayList<>();
        for (ShopProductFeedback shopProductFeedback : shopProductFeedbacks){
            if (!shopProductFeedback.getId().equals(id)) newShopProductFeedbacks.add(shopProductFeedback);
        }
        shopProduct.setShopProductFeedbacks(newShopProductFeedbacks);
        service.addProduct(shopProduct);
        service.deleteFeedbackById(id);
        return "redirect:/shop/"+item;
    }
}
