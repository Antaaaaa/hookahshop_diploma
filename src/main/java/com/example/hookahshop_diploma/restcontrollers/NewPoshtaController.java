package com.example.hookahshop_diploma.restcontrollers;

import com.example.hookahshop_diploma.dto.newposhta.CityResponce;
import com.example.hookahshop_diploma.dto.newposhta.NewPoshtaData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NewPoshtaController {
    @Value("${newposhta.secret}")
    private String secret;
    private final static String URL = "https://api.novaposhta.ua/v2.0/json/";

    @RequestMapping("/getAllCities/{input}")
    public List<NewPoshtaData> result(@PathVariable String input){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apiKey", secret);
            jsonObject.put("modelName","AddressGeneral");
            jsonObject.put("calledMethod", "getSettlements");

            JSONObject methodProperties = new JSONObject();
            methodProperties.put("FindByString", input);
            jsonObject.put("methodProperties", methodProperties);

            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
            CityResponce cityResponce = restTemplate.postForObject(URL, request, CityResponce.class);

            if (cityResponce.getSuccess()){
                List<String> list = new ArrayList<>();
                return cityResponce.getNewPoshtaData()
                        .stream()
                        .sorted(Comparator.comparing(NewPoshtaData::getSettlementTypeDescription))
                        .collect(Collectors.toList());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/getAllDepartments/{ref}")
    public List<String> getAllDepartments(@PathVariable String ref){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apiKey", secret);
            jsonObject.put("modelName","AddressGeneral");
            jsonObject.put("calledMethod", "getWarehouses");

            JSONObject methodProperties = new JSONObject();
            methodProperties.put("Language", "ua");
            methodProperties.put("SettlementRef", ref);
            jsonObject.put("methodProperties", methodProperties);

            HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
            CityResponce cityResponce = restTemplate.postForObject(URL, request, CityResponce.class);

            if (cityResponce.getSuccess()){
                return cityResponce.getNewPoshtaData()
                        .stream()
                        .sorted(Comparator.comparing(NewPoshtaData::getNumber))
                        .map(NewPoshtaData::getDescription)
                        .collect(Collectors.toList());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
