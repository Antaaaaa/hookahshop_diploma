package com.example.hookahshop_diploma.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.SneakyThrows;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

@Component
public class ImageSender {
    @Value("${com.cloudinary.cloud_name}")
    String mCloudName;

    @Value("${com.cloudinary.api_key}")
    String mApiKey;

    @Value("${com.cloudinary.api_secret}")
    String mApiSecret;

    @SneakyThrows
    public String uploadImageAndGetUrl(MultipartFile image){
        Cloudinary c=new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
        File f= Files.createTempFile("temp", image.getOriginalFilename()).toFile();
        image.transferTo(f);
        Map response=c.uploader().upload(f, ObjectUtils.emptyMap());
        JSONObject json=new JSONObject(response);
        String url=json.getString("url");
        return url;
    }
}
