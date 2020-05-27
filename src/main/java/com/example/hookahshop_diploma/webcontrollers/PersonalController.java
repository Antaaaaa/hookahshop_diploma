package com.example.hookahshop_diploma.webcontrollers;

import com.example.hookahshop_diploma.dto.captcha.CaptchaResponseDTO;
import com.example.hookahshop_diploma.model.*;
import com.example.hookahshop_diploma.service.ShopService;
import com.example.hookahshop_diploma.util.EmailSender;
import com.example.hookahshop_diploma.util.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class PersonalController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Autowired
    private ShopService service;

    @Autowired
    private UserIdentity userIdentity;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailSender emailSender;

    @org.springframework.beans.factory.annotation.Value("${recaptcha.secret}")
    private String secret;

    @RequestMapping("/personal")
    public String personal(@RequestParam(value = "error", required = false) Boolean error, Model model){
        User user = userIdentity.getCurrentUser();
        ShopUser shopUser = service.findUserByEmail(user.getUsername());
        List<ShopOrder> shopOrder = userIdentity.isAdmin() ?
                service.findShopOrderByStatusIsNotNull() : service.findAllByUserAndStatusIsNotNull(shopUser);
        shopOrder.sort(Comparator.comparing(ShopOrder::getId).reversed());
        model.addAttribute("catalogs", service.findAllCatalog());
        model.addAttribute("orders", shopOrder);
        model.addAttribute("isAdmin", userIdentity.isAdmin());
        if (error != null && error) model.addAttribute("error", error);
        model.addAttribute("status", service.findAllStatus());
        model.addAttribute("user", shopUser);
        return "personal";
    }

    @RequestMapping(value = "/personal/delete-liked/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id){
        ShopUser user = service.findUserByEmail(userIdentity.getCurrentUser().getUsername());
        user.getLikedProduct().remove(service.findProductById(id));
        service.addUser(user);
        return "redirect:/personal";
    }
    @RequestMapping(value = "/personal/add/{id}", method = RequestMethod.POST)
    public String add(@PathVariable Long id){
        ShopUser user = service.findUserByEmail(userIdentity.getCurrentUser().getUsername());
        if (!user.getLikedProduct().contains(service.findProductById(id)))
        {
            List<ShopProduct> shopProducts = new ArrayList<>(user.getLikedProduct());
            shopProducts.add(service.findProductById(id));
            user.setLikedProduct(shopProducts);
            service.addUser(user);
        }
        return "redirect:/shop/"+id;
    }
    @RequestMapping(value = "/personal/feedback/{id}", method = RequestMethod.POST)
    public String addFeedback(@PathVariable Long id, @RequestParam String feedback,
                              @RequestParam String rating,
                              @RequestParam(name = "g-recaptcha-response") String responceCaptcha){
        // Captcha Validation
        String url = String.format(CAPTCHA_URL, secret, responceCaptcha);
        CaptchaResponseDTO responce = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);

        if (responce.isSuccess()){
        ShopUser user = service.findUserByEmail(userIdentity.getCurrentUser().getUsername());
        ShopProduct product = service.findProductById(id);
        ShopProductFeedback shopProductFeedBack = new ShopProductFeedback(null, feedback, Integer.valueOf(rating),
                user, product);
        List<ShopProductFeedback> productFeedbacks = new ArrayList<>(product.getShopProductFeedbacks());
        productFeedbacks.add(shopProductFeedBack);
        product.setShopProductFeedbacks(productFeedbacks);
        service.addProduct(product);
        }
        return "redirect:/shop/"+id;
    }
}
