package com.example.hookahshop_diploma.restcontrollers;

import com.example.hookahshop_diploma.dto.database.CartDTO;
import com.example.hookahshop_diploma.dto.database.OrderDTO;
import com.example.hookahshop_diploma.model.*;
import com.example.hookahshop_diploma.service.ShopService;
import com.example.hookahshop_diploma.util.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class OrderRestController {
    @Autowired
    private ShopService service;
    @Autowired
    private UserIdentity userIdentity;

    @RequestMapping(value = "/cart")
    public CartDTO getUsersCart(){
        User user = userIdentity.getCurrentUser();
        if (user == null) return null;
        ShopUser shopUser = service.findUserByEmail(user.getUsername());
        ShopOrder shopOrder = service.findShopOrderByUserAndStatusIsNull(shopUser);
        return CartDTO.of(shopOrder.getCart());
    }
    @RequestMapping(value = "/cart/add/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addItem(@PathVariable Long id){
        User user = userIdentity.getCurrentUser();
        if (user == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        ShopUser shopUser = service.findUserByEmail(user.getUsername());
        Cart cart = service.findShopOrderByUserAndStatusIsNull(shopUser).getCart();
        ShopProduct shopProduct = service.findProductById(id);
        if (shopProduct.getAmount() <= 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        List<SelectedItems> selectedItems = new ArrayList<>(cart.getSelectedItems());
        boolean exists = false;
        for (SelectedItems selectedItem : selectedItems){
            if (selectedItem.getShopProduct().getId().equals(id))
            {
                selectedItem.setNumber(selectedItem.getNumber() + 1);
                if(selectedItem.getShopProduct().getAmount() >= selectedItem.getNumber());
                else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
                exists = true;
            }
        }
        if (!exists) {
            SelectedItems newSelectedItem = new SelectedItems(null, 1L, shopProduct, cart);
            selectedItems.add(newSelectedItem);
        }
        cart.setSelectedItems(selectedItems);
        service.addCart(cart);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/cart/minus/{id}", method = RequestMethod.POST)
    public ResponseEntity minusItem(@PathVariable Long id){
        User user = userIdentity.getCurrentUser();
        if (user == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        ShopUser shopUser = service.findUserByEmail(user.getUsername());
        Cart cart = service.findShopOrderByUserAndStatusIsNull(shopUser).getCart();
        List<SelectedItems> selectedItems = new ArrayList<>(cart.getSelectedItems());
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i).getShopProduct().getId().equals(id)) {
                if (selectedItems.get(i).getNumber() - 1 != 0)
                    selectedItems.get(i).setNumber(selectedItems.get(i).getNumber() - 1);
                else selectedItems.remove(selectedItems.get(i));
            }
        }
        cart.setSelectedItems(selectedItems);
        service.addCart(cart);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/cart/minusall/{id}", method = RequestMethod.POST)
    public ResponseEntity minusAllItems(@PathVariable Long id){
        User user = userIdentity.getCurrentUser();
        if (user == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        ShopUser shopUser = service.findUserByEmail(user.getUsername());
        Cart cart = service.findShopOrderByUserAndStatusIsNull(shopUser).getCart();
        List<SelectedItems> selectedItems = new ArrayList<>(cart.getSelectedItems());
        for (int i = 0; i < selectedItems.size(); i++){
            if (selectedItems.get(i).getShopProduct().getId().equals(id)){
                selectedItems.remove(selectedItems.get(i));
            }
        }
        cart.setSelectedItems(selectedItems);
        service.addCart(cart);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/order/saveorderdata/", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveOrderData(@RequestBody OrderDTO loaded){
        User user = userIdentity.getCurrentUser();
        ShopUser shopUser = service.findUserByEmail(loaded.getEmail());
        ShopOrder order =  service.findShopOrderByUserAndStatusIsNull(shopUser);
        Address addressInDb;
        if ((addressInDb = service.findByAddress(loaded.getAddress())) == null){
            addressInDb = new Address(null, loaded.getAddress());
            service.addAddress(addressInDb);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        order.setDate(dateFormat.format(new Date()));
        order.setAddress(addressInDb);
        order.setName(loaded.getName());
        order.setSurname(loaded.getSurname());
        order.setPhone(loaded.getPhone());
        order.setComment(loaded.getComment());
        service.addOrder(order);
        return new ResponseEntity(HttpStatus.OK);
    }
}
