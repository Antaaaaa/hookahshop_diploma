package com.example.hookahshop_diploma.restcontrollers;

import com.example.hookahshop_diploma.model.ShopOrder;
import com.example.hookahshop_diploma.model.ShopProduct;
import com.example.hookahshop_diploma.service.ShopService;
import com.example.hookahshop_diploma.util.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/admin/excel")
public class ExcelRestConroller {
    @Autowired
    private ShopService service;
    @Autowired
    private ExcelGenerator excelGenerator;

    @RequestMapping("getExcelOrders")
    public ResponseEntity<InputStreamResource> getExcelOrders(){
        List<ShopOrder> shopOrderList = service.findShopOrderByStatusIsNotNull();

        ByteArrayInputStream in = excelGenerator.getExcelOrdersList(shopOrderList);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=orders.xlsx");
        httpHeaders.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return ResponseEntity.ok().headers(httpHeaders).body(new InputStreamResource(in));
    }

    @RequestMapping("getExcelProducts")
    public ResponseEntity<InputStreamResource> getExcelProduct(){
        List<ShopProduct> shopProductList = service.findAllProduct();

        ByteArrayInputStream in = excelGenerator.getProductExcel(shopProductList);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=product.xlsx");
        httpHeaders.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return ResponseEntity.ok().headers(httpHeaders).body(new InputStreamResource(in));
    }

}
