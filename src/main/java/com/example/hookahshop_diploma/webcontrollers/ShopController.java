package com.example.hookahshop_diploma.webcontrollers;

import com.example.hookahshop_diploma.model.*;
import com.example.hookahshop_diploma.model.Character;
import com.example.hookahshop_diploma.service.ShopService;
import com.example.hookahshop_diploma.util.ShopFilters;
import com.example.hookahshop_diploma.util.UserIdentity;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ShopController {
    @Autowired
    private ShopService service;
    @Autowired
    private ShopFilters shopFilters;
    @Autowired
    private UserIdentity userIdentity;

    @RequestMapping("/shop/{id}")
    public String item(@PathVariable Long id, Model model){
        model.addAttribute("catalogs", service.findAllCatalog());
        ShopProduct shopProduct = service.findProductById(id);
        SubCatalog subCatalog = service.findSubCatalogByShopProduct(shopProduct);
        model.addAttribute("catalogType", subCatalog.getSubname());
        model.addAttribute("item", shopProduct);
        int avgRating = (int)Math.ceil(shopProduct.getShopProductFeedbacks()
                                             .stream()
                                             .mapToInt(ShopProductFeedback::getRating)
                                             .average()
                                             .orElse(0));
        model.addAttribute("rating", avgRating);
        if (userIdentity.getCurrentUser() != null) {
            ShopUser user = service.findUserByEmail(userIdentity.getCurrentUser().getUsername());
            model.addAttribute("isLiked", user.getLikedProduct().contains(service.findProductById(id)));
        }
        model.addAttribute("isAdmin", userIdentity.isAdmin());
        // 404 page non found if shopProduct == null
        return "one-item";
    }

    @RequestMapping("/shop")
    public String shop(@RequestParam(name = "type", defaultValue = "Кальяни") String type,
                       @RequestParam(required = false) Map<String,String> allParams, Model model){
        List<ShopProduct> shopProductList = allParams.size() > 1 ?
                shopFilters.filterByCriteria(allParams, service.findSubCatalogByName(type).getShopProduct()) :
                service.findSubCatalogByName(type).getShopProduct();
        DoubleSummaryStatistics statistics = shopProductList.stream().collect(Collectors.summarizingDouble(ShopProduct::getPrice));
        Map<Character,List<Value>> map = shopFilters.getFilters(type);
        Pair<Double, Double> minAndMax = Pair.of(Double.isInfinite(statistics.getMin()) ? 0 : statistics.getMin(),
                Double.isInfinite(statistics.getMax()) ? 0 : statistics.getMax());
        model.addAttribute("catalogs", service.findAllCatalog());
        model.addAttribute("catalogType", type);
        model.addAttribute("filters", map);
        model.addAttribute("items", shopProductList);
        model.addAttribute("minAndMax", minAndMax);
        return "shop";
    }

}
