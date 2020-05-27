package com.example.hookahshop_diploma.adminwebcontrollers;

import com.example.hookahshop_diploma.model.*;
import com.example.hookahshop_diploma.model.Character;
import com.example.hookahshop_diploma.service.ShopService;
import com.example.hookahshop_diploma.util.ImageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminEditorController {
    @Autowired
    private ShopService service;
    @Autowired
    private ImageSender imageSender;
    @RequestMapping("/editor")
    public String getEditor(@RequestParam(value = "id", defaultValue = "1") Long id, Model model){
        ShopProduct shopProduct = service.findProductById(id);
        model.addAttribute("catalogs", service.findAllCatalog());
        model.addAttribute("subcatalogs", service.findAllSubCatalog());
        model.addAttribute("items", service.findAllProduct()
                .stream()
                .sorted(Comparator.comparing(ShopProduct::getName))
                .collect(Collectors.toList()));
        model.addAttribute("thisItem", shopProduct);
        List<Character> characterList = service.findAllCharacter();
        List<Character> productList = shopProduct.getCharacterValue()
                .stream()
                .map(CharacterValue::getCharacter)
                .collect(Collectors.toList());
        List<Character> newList = new ArrayList<>();
       for (Character character : characterList)
           if (!productList.contains(character)) newList.add(character);
        model.addAttribute("characters", newList);
        return "admin-editor";
    }
    @RequestMapping(value = "/editor/change/{id}", method = RequestMethod.POST)
    public String updateItem(@PathVariable("id") Long id, @RequestParam("name") String name,
                             @RequestParam("price") Double price, @RequestParam("amount") Long amount,
                             @RequestParam("image") MultipartFile image, @RequestParam("type") String type,
                             @RequestParam("description") String description,
                             @RequestParam("character-name") Long characterId,
                             @RequestParam(value = "character-value", required = false) String valueName){
        ShopProduct shopProduct = service.findProductById(id);
        shopProduct.setName(name);
        shopProduct.setPrice(price);
        shopProduct.setAmount(amount);
        List<ShopOrder> orders = service.findShopOrderByStatusIsNotNull();
        Double summary = 0D;
        for (ShopOrder order : orders){
            for (SelectedItems selectedItem : order.getCart().getSelectedItems())
                summary += selectedItem.getNumber() * selectedItem.getShopProduct().getPrice();
            order.setSummary(summary);
        }
        if (valueName != null){
            CharacterValue newCharacterValue = service.findCharacterValueByCharacterAndValue(
                    service.findCharacterById(characterId), service.findValueByName(valueName));
            List<CharacterValue> newList = new ArrayList<>();
            for (CharacterValue characterValue : shopProduct.getCharacterValue()){
                if (characterValue.getCharacter().getId().equals(characterId)) newList.add(newCharacterValue);
                else newList.add(characterValue);
            }
            shopProduct.setCharacterValue(newList);
        }
        shopProduct.setImageUrl(Objects.equals(
                image.getOriginalFilename(), "") ? shopProduct.getImageUrl() : imageSender.uploadImageAndGetUrl(image));
        shopProduct.setType(type.equals("") ? null : type);
        shopProduct.setDescription(description);
        service.addProduct(shopProduct);
        return "redirect:/admin/editor?id="+id;
    }

    @RequestMapping(value = "/editor/{id}/add-character", method = RequestMethod.POST)
    public String addNewCharacter(@PathVariable("id") Long id,
                                  @RequestParam("character-name-new") Long characterId,
                                  @RequestParam("new-character-value") String valueName){
        ShopProduct shopProduct = service.findProductById(id);
        List<CharacterValue> characterValues = shopProduct.getCharacterValue();
        List<CharacterValue> newCharacterValues = new ArrayList<>(characterValues);
        CharacterValue characterValue = service.findCharacterValueByCharacterAndValue(
                service.findCharacterById(characterId), service.findValueByName(valueName));
        newCharacterValues.add(characterValue);
        shopProduct.setCharacterValue(newCharacterValues);
        service.addProduct(shopProduct);
        return "redirect:/admin/editor?id="+id;
    }

    @RequestMapping(value = "/editor/{id}/delete-character", method = RequestMethod.POST)
    public String addNewCharacter(@PathVariable("id") Long id,
                                  @RequestParam("character-name-new") Long characterId){
        ShopProduct shopProduct = service.findProductById(id);
        List<CharacterValue> characterValues = shopProduct.getCharacterValue();
        List<CharacterValue> newCharacterValues = new ArrayList<>();
        for (CharacterValue characterValue : characterValues){
            if (!characterValue.getCharacter().getId().equals(characterId))
                newCharacterValues.add(characterValue);
        }
        shopProduct.setCharacterValue(newCharacterValues);
        service.addProduct(shopProduct);
        return "redirect:/admin/editor?id="+id;
    }


    @RequestMapping(value = "/editor/add-new-item", method = RequestMethod.POST)
    public String updateItem(@RequestParam("subname") String subname, @RequestParam("name") String name,
                             @RequestParam("price") Double price, @RequestParam("amount") Long amount,
                             @RequestParam("image") MultipartFile image, @RequestParam("type") String type,
                             @RequestParam("description") String description){
        ShopProduct shopProduct = new ShopProduct(null, name, price, description, amount, imageSender.uploadImageAndGetUrl(image),
                type.equals("") ? null : type, null, null);
        SubCatalog subCatalog = service.findSubCatalogByName(subname);
        List<ShopProduct> shopProducts = subCatalog.getShopProduct();
        List<ShopProduct> newShopProducts = new ArrayList<>(shopProducts);
        newShopProducts.add(shopProduct);
        subCatalog.setShopProduct(newShopProducts);
        service.addSubCatalog(subCatalog);
        return "redirect:/admin/editor";
    }
}
