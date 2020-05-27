package com.example.hookahshop_diploma.util;

import com.example.hookahshop_diploma.dto.liqpay.LiqPay;
import com.example.hookahshop_diploma.dto.liqpay.LiqPayDataStatus;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Component
public class LiqPayUtils {
    private final static String API_VERSION = "3";
    public byte[] sha1(String param) {
        try {
            MessageDigest SHA = MessageDigest.getInstance("SHA-1");
            SHA.reset();
            SHA.update(param.getBytes(StandardCharsets.UTF_8));
            return SHA.digest();
        } catch (Exception e) {
            throw new RuntimeException("Can't calc SHA-1 hash", e);
        }
    }
    public String base64Encoder(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public String base64Encoder(String data) {
        return base64Encoder(data.getBytes());
    }

    public LiqPay cnb_form(Map<String, String> params, String publicKey, String privateKey) {
        String data = base64Encoder(JSONObject.toJSONString(withSandboxParam(withBasicApiParams(params, publicKey))));
        String signature = createSignature(data, privateKey);
        return new LiqPay(data,signature);
    }
    public TreeMap<String, String> withBasicApiParams(Map<String, String> params, String publicKey) {
        TreeMap<String, String> tm = new TreeMap<>(params);
        tm.put("public_key", publicKey);
        tm.put("version", API_VERSION);
        return tm;
    }

    public TreeMap<String, String> withSandboxParam(TreeMap<String, String> params) {
        if (params.get("sandbox") == null) {
            TreeMap<String, String> tm = new TreeMap<>(params);
            tm.put("sandbox", "1");
            return tm;
        }
        return params;
    }
    public String createSignature(String base64EncodedData, String privateKey) {
        return base64Encoder(sha1(privateKey + base64EncodedData + privateKey));
    }
    public String getStatus(Map<String, String> params, String publicKey, String privateKey) throws Exception {
        String data = base64Encoder(JSONObject.toJSONString(withBasicApiParams(params, publicKey)));
        String signature = createSignature(data, privateKey);
        // data : base64Encoder(json{order_id, action, version, public_key})
        // signature : base64Encoder(sha1(privateKey + data + privateKey))
        // 2 parameters data and signature must be entered
        HashMap<String,String> result = new HashMap<>();
        result.put("data", data);
        result.put("signature", signature);
        // from liqpay api sdk
        String urlParameters = "";
        for (Map.Entry<String, String> entry : result.entrySet())
            urlParameters += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(urlParameters, httpHeaders);
        LiqPayDataStatus liqPayDataStatus = restTemplate.postForObject("https://www.liqpay.ua/api/request", request, LiqPayDataStatus.class);
        System.out.println(liqPayDataStatus.getStatus());
        return liqPayDataStatus.getStatus();
    }
}
