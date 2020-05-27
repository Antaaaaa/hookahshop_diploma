package com.example.hookahshop_diploma.dto.liqpay;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LiqPay {
    private String data;
    private String signature;
}
