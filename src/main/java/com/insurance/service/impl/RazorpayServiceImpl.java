package com.insurance.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.insurance.service.RazorpayService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayServiceImpl implements RazorpayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Override
    public Order createOrder(Double amount) throws Exception {

        System.out.println("KEY ID = " + keyId);
        System.out.println("KEY SECRET = " + keySecret);

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();

        options.put("amount", Math.round(amount * 100));

        options.put("currency", "INR");

        options.put("receipt", "receipt_" + System.currentTimeMillis());

        return client.orders.create(options);
    }
}