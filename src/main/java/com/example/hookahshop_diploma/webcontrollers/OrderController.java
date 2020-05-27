package com.example.hookahshop_diploma.webcontrollers;

import com.example.hookahshop_diploma.model.*;
import com.example.hookahshop_diploma.service.ShopService;
import com.example.hookahshop_diploma.util.EmailSender;
import com.example.hookahshop_diploma.util.LiqPayUtils;
import com.example.hookahshop_diploma.util.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
public class OrderController {

    //    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_SUCCESS = "sandbox";
    private final static String UNIQUE_ORDER_FORMAT = "hookah-order-%s-%s";

    @Value("${liqpay.public_kay_test}")
    private String publicKey;

    @Value("${liqpay.private_key_test}")
    private String privateKey;

    @Autowired
    private ShopService service;

    @Autowired
    private UserIdentity userIdentity;

    @Autowired
    private LiqPayUtils liqPayUtils;

    @Autowired
    private EmailSender emailSender;

    @RequestMapping("/order")
    public String order(Model model){
        ShopUser user = service.findUserByEmail(userIdentity.getCurrentUser().getUsername());
        ShopOrder order = service.findShopOrderByUserAndStatusIsNull(user);
        List<SelectedItems> selectedItems = order.getCart().getSelectedItems();
        if (selectedItems.size() == 0) return "redirect:/shop";
        double summary = 0;
        for (SelectedItems selectedItem : selectedItems)
            summary += selectedItem.getNumber() * selectedItem.getShopProduct().getPrice();
        order.setSummary(summary);
        service.addOrder(order);
        model.addAttribute("catalogs", service.findAllCatalog());
        model.addAttribute("order", order);
        return "order";
    }
    @RequestMapping(value = "/order/success", method = RequestMethod.POST)
    public String orderedItems(){
        confirmNewOrder(false);
        return "redirect:/personal";
    }
    @RequestMapping("/order/resultLiqPay")
    public String testLiqPay(@RequestParam(required = false) Map<String,String> allParams) throws Exception {
        ShopUser user = service.findUserByEmail(userIdentity.getCurrentUser().getUsername());
        ShopOrder order = service.findShopOrderByUserAndStatusIsNull(user);
        // Checking if noone changed our data
        String data = allParams.get("data");
        String signatureFromServer = allParams.get("signature");
        String mysignature = liqPayUtils.base64Encoder(liqPayUtils.sha1(privateKey + data + privateKey));
        HashMap<String,String> params = new HashMap<>();
        params.put("action", "status");
        params.put("order_id", String.format(UNIQUE_ORDER_FORMAT, order.getId(), liqPayUtils.base64Encoder(order.getDate())));
        params.put("version", "3");
        params.put("public_key", publicKey);
        String status = liqPayUtils.getStatus(params, publicKey, privateKey);
        if (signatureFromServer.equals(mysignature) && status.equals(STATUS_SUCCESS)) {
            confirmNewOrder(true);
            return "redirect:/personal";
        }
        else {
            return "redirect:/order";
            // "redirect:/order?error"
        }
    }

    public void confirmNewOrder(Boolean isPaid){
        ShopUser user = service.findUserByEmail(userIdentity.getCurrentUser().getUsername());
        ShopOrder order = service.findShopOrderByUserAndStatusIsNull(user);
        if (order.getCart().getSelectedItems().size() <= 0)
            throw new NoSuchElementException("You're trying to order 0 items");
        order.setIsPaid(isPaid);

        order.setStatus(service.findByStatusName("Прийнято в замовлення"));
        double summary = 0;
        for (SelectedItems item : order.getCart().getSelectedItems()){
            ShopProduct shopProduct = item.getShopProduct();
            shopProduct.setAmount(shopProduct.getAmount() - item.getNumber());
            summary += item.getNumber() * item.getShopProduct().getPrice();
            service.addProduct(shopProduct);
        }

        order.setSummary(summary);
        order.setEmail(user.getEmail());
        service.addOrder(order);

        Cart newCart = new Cart(null,null);
        ShopOrder newShopOrder = new ShopOrder(null,null,null,null,
                null,null,null,null,null,newCart, user, null,null);

        String message = "Ваше замовлення успішно оброблено! В найближчому часі вам зателефонує наш менеджер!=)";
        emailSender.send(user.getEmail(), "Магазин HookahShop!Успішне замовлення №" + order.getId(), message);

        service.addCart(newCart);

        service.addOrder(newShopOrder);
    }
}
