package com.example.hookahshop_diploma.restcontrollers;

import com.example.hookahshop_diploma.dto.database.AddressDTO;
import com.example.hookahshop_diploma.dto.database.ShopProductDTO;
import com.example.hookahshop_diploma.dto.database.ValueDTO;
import com.example.hookahshop_diploma.model.CharacterValue;
import com.example.hookahshop_diploma.model.ShopDelivery;
import com.example.hookahshop_diploma.service.ShopService;
import com.example.hookahshop_diploma.util.EmailSender;
import com.example.hookahshop_diploma.util.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/json")
public class WebRestController {
    public final static String SELF_DELIVERY_NAME = "Самовивоз";
    public final static String NOVA_POSHTA_DELIVERY_NAME = "Нова Пошта";

    @Autowired
    private ShopService service;
    @Autowired
    private UserIdentity userIdentity;
    @Autowired
    private EmailSender emailSender;

    @RequestMapping("/shop/{id}")
    public ShopProductDTO getCharacter(@PathVariable Long id){
        return ShopProductDTO.of(service.findProductById(id));
    }

    @RequestMapping("/finduser/{email}")
    public Boolean getUser(@PathVariable String email){
        return service.findUserByEmail(email) != null;
    }

    @RequestMapping("/isuser")
    public Boolean isUser(){
        return userIdentity.getCurrentUser() != null;
    }

    @RequestMapping("/shoplocations")
    public List<AddressDTO> location(){
        ShopDelivery shopDelivery = service.findByDeliveryName(SELF_DELIVERY_NAME);
        return AddressDTO.of(shopDelivery.getAddress());
    }
    @RequestMapping("/shoplocationsnova")
    public List<AddressDTO> locationNewPoshta(){
        ShopDelivery shopDelivery = service.findByDeliveryName(NOVA_POSHTA_DELIVERY_NAME);
        return AddressDTO.of(shopDelivery.getAddress());
    }
//    @RequestMapping("/send")
//    public void sendEmail(){
//        emailSender.send("", "Hello there", "Hi dude");
//    }

    @RequestMapping("/allvalues/{id}")
    public List<ValueDTO> getAllValues(@PathVariable Long id){
        List<CharacterValue> characterValues = service.findAllByCharacter(service.findCharacterById(id));
        List<ValueDTO> valueDTOS = new ArrayList<>();
        for (CharacterValue characterValue : characterValues){
            valueDTOS.add(ValueDTO.of(characterValue.getValue()));
        }
        return valueDTOS;
    }
}
