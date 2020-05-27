package com.example.hookahshop_diploma.adminwebcontrollers;

import com.example.hookahshop_diploma.model.ShopOrder;
import com.example.hookahshop_diploma.model.ShopOrderStatus;
import com.example.hookahshop_diploma.model.ShopUser;
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

import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {
    @Autowired
    private ShopService service;
    @Autowired
    private UserIdentity userIdentity;
    @Autowired
    private EmailSender emailSender;

    // SEARCHING ORDERS
    @RequestMapping("/order/search")
    public String searchOrder(@RequestParam(value = "id", required = false) Long id, Model model){
        if (id == null) return "redirect:/personal";
        ShopOrder shopOrder = service.findOrderById(id);
        if (shopOrder == null) return "redirect:/personal?error=true";
        User user = userIdentity.getCurrentUser();
        ShopUser shopUser = service.findUserByEmail(user.getUsername());
        model.addAttribute("catalogs", service.findAllCatalog());
        model.addAttribute("orders", Collections.singletonList(shopOrder));
        model.addAttribute("isAdmin", userIdentity.isAdmin());
        model.addAttribute("status", service.findAllStatus());
        model.addAttribute("user", shopUser);
        return "personal";
    }

    // CHANGING ORDER'S STATUS
    @RequestMapping(value = "/order/change-order/{id}", method = RequestMethod.POST)
    public String changeOrderStatus(@PathVariable Long id, @RequestParam("select1") Long select1,
                                    @RequestParam("select2") Boolean select2){
        ShopOrder shopOrder = service.findOrderById(id);
        ShopOrderStatus shopOrderStatus = service.findByStatusById(select1);
        emailSender.send(shopOrder.getUser().getEmail(), "Магазин HookahShop",
                "Статус вашого замовлення змінено на: " + shopOrderStatus.getStatusName());
        shopOrder.setStatus(shopOrderStatus);
        shopOrder.setIsPaid(select2);
        service.addOrder(shopOrder);
        return "redirect:/personal";
    }
}
