package com.example.hookahshop_diploma.webcontrollers;

import com.example.hookahshop_diploma.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @Autowired
    private ShopService service;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("catalogs", service.findAllCatalog());
        model.addAttribute("recommended", service.findAllProductByType("recommended"));
        model.addAttribute("newitems", service.findAllProductByType("new"));
        return "index";
    }
}
