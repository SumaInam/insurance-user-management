package com.insurance.service;

import com.razorpay.Order;

public interface RazorpayService {

    Order createOrder(Double amount) throws Exception;
}