package com.example.hookahshop_diploma.restcontrollers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/cloud")
public class ImageController {
    @Autowired
    @Qualifier("com.cloudinary.cloud_name")
    private String mCloudName;

    @Autowired
    @Qualifier("com.cloudinary.api_key")
    private String mApiKey;

    @Autowired
    @Qualifier("com.cloudinary.api_secret")
    private String mApiSecret;

    @GetMapping(value="/image")
    public ResponseEntity<List<String>> get(
            @RequestParam(value="name", required=false) String aName)
    {
        Cloudinary c=new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
        List<String> retval= new ArrayList<>();
        try
        {
            Map response=c.api().resource("", ObjectUtils.asMap("type", "upload"));
            JSONObject json=new JSONObject(response);
            JSONArray ja=json.getJSONArray("resources");
            for(int i=0; i<ja.length(); i++)
            {
                JSONObject j=ja.getJSONObject(i);
                retval.add(j.getString("url"));
            }

            return new ResponseEntity<>(retval, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
