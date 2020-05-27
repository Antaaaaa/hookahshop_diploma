package com.example.hookahshop_diploma.util;

import com.example.hookahshop_diploma.model.CharacterValue;
import com.example.hookahshop_diploma.model.ShopProduct;
import com.example.hookahshop_diploma.model.Value;
import com.example.hookahshop_diploma.model.Character;
import com.example.hookahshop_diploma.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ShopFilters {
    @Autowired
    private ShopService service;

    public Map<Character,List<Value>> getFilters(String type){
        Map<Character, List<Value>> map = new HashMap<>();
        List<CharacterValue> list = service.findSubCatalogByName(type).getShopProduct().stream()
                .flatMap(i -> i.getCharacterValue().stream())
                .collect(Collectors.toList());
        for (CharacterValue value : list){
            if (!map.containsKey(value.getCharacter()))
                map.putIfAbsent(value.getCharacter(), Collections.singletonList(value.getValue()));
            else if (map.get(value.getCharacter()).stream().noneMatch(i -> i.getName().equals(value.getValue().getName()))){
                List<Value> temp = new ArrayList<>();
                temp.addAll(map.get(value.getCharacter()));
                temp.add(value.getValue());
                map.put(value.getCharacter(), temp);
            }
        }
        return map;
    }

    public List<ShopProduct> filterByCriteria(Map<String, String> map, List<ShopProduct> shopProduct){
        List<ShopProduct> shopProductsFiltered = new ArrayList<>(shopProduct);
        for (String character : map.keySet()){
            if (character.equals("type")) continue;
            else if (character.equals("priceFrom")){
                shopProductsFiltered = shopProductsFiltered.stream().filter(i ->
                        i.getPrice() >= Double.parseDouble(map.get(character))).collect(Collectors.toList());
            }
            else if (character.equals("priceTo")){
                shopProductsFiltered = shopProductsFiltered.stream().filter(i ->
                        i.getPrice() <= Double.parseDouble(map.get(character))).collect(Collectors.toList());
            }
            else if (character.equals("available")){
                shopProductsFiltered = shopProductsFiltered.stream().filter(i -> map.get(character).equals("YES") ?
                        i.getAmount() >= 1 : i.getAmount() <= 0).collect(Collectors.toList());
            }
            else
            {
                shopProductsFiltered = shopProductsFiltered.stream().filter(i ->
                        i.getCharacterValue().stream().anyMatch(j ->
                                j.getCharacter().getName().equals(character.replace('_', ' '))
                                        && j.getValue().getName().equals(map.get(character)))).collect(Collectors.toList());
            }

        }
        return shopProductsFiltered;
    }
}
