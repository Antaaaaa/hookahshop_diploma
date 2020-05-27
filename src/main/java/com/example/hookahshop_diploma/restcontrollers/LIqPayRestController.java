package com.example.hookahshop_diploma.restcontrollers;

import com.example.hookahshop_diploma.dto.liqpay.LiqPay;
import com.example.hookahshop_diploma.model.ShopOrder;
import com.example.hookahshop_diploma.model.ShopUser;
import com.example.hookahshop_diploma.service.ShopService;
import com.example.hookahshop_diploma.util.LiqPayUtils;
import com.example.hookahshop_diploma.util.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LIqPayRestController {
    private final static String UNIQUE_ORDER_FORMAT = "hookah-order-%s-%s";
    @Value("${liqpay.public_kay_test}")
    private String publicKey;
    @Value("${liqpay.private_key_test}")
    private String privateKey;
    @Autowired
    private UserIdentity userIdentity;
    @Autowired
    private ShopService service;
    @Autowired
    private LiqPayUtils liqPayUtils;
    @RequestMapping("/order/getLiqPayData")
    public LiqPay getLiqPayData(){
        ShopUser user = service.findUserByEmail(userIdentity.getCurrentUser().getUsername());
        ShopOrder order = service.findShopOrderByUserAndStatusIsNull(user);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("action", "pay");
        params.put("amount", order.getSummary()+"");
        params.put("currency", "UAH");
        params.put("description", "Замовлення №"+order.getId() + " на загальну суму: " + order.getSummary() + " грн");
        // change in production
        params.put("order_id", String.format(UNIQUE_ORDER_FORMAT, order.getId(), liqPayUtils.base64Encoder(order.getDate())));
        params.put("version", "3");
        params.put("result_url","http://hookah-shop.herokuapp.com/order/resultLiqPay");
//        params.put("result_url","http://localhost:8080/order/resultLiqPay");
        return liqPayUtils.cnb_form(params, publicKey, privateKey);
    }
}
