package com.example.hookahshop_diploma.webcontrollers;

import com.example.hookahshop_diploma.dto.captcha.CaptchaResponseDTO;
import com.example.hookahshop_diploma.model.Cart;
import com.example.hookahshop_diploma.model.ShopOrder;
import com.example.hookahshop_diploma.model.ShopUser;
import com.example.hookahshop_diploma.role.Role;
import com.example.hookahshop_diploma.service.ShopService;
import com.example.hookahshop_diploma.util.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller
public class AuthController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Autowired
    private ShopService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserIdentity userIdentity;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${recaptcha.secret}")
    private String secret;


    @RequestMapping("/auth")
    public String auth(Model model){
        if (userIdentity.getCurrentUser() == null) return "redirect:/auth/denied";
        else return "redirect:/personal";
    }
    @RequestMapping("/auth/denied")
    public String denied(@RequestParam(name = "error", required = false)String error, Model model){
        if (userIdentity.getCurrentUser() != null) return "redirect:/personal";
        model.addAttribute("error", error);
        model.addAttribute("catalogs", service.findAllCatalog());
        return "authform";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam(name = "email")String email,
                           @RequestParam(name = "username")String username,
                           @RequestParam(name = "password")String password,
                           @RequestParam(name = "g-recaptcha-response") String captchaResponce){
        // Captcha Validation
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponseDTO response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);
        if (service.findUserByEmail(email) == null && response.isSuccess())
        {
            ShopUser user = new ShopUser(username, email, passwordEncoder.encode(password), Role.USER);
            service.addUser(user);
            Cart cart = new Cart(null, null);
            service.addCart(cart);
            service.addOrder(new ShopOrder(null, null, null, null,
                    null,null,null,null,null, cart, user, null ,null));
            return "redirect:/personal";
        }
        return "redirect:/auth/denied";
    }
}
