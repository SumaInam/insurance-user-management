package com.insurance.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private Long customerPolicyId;

    private Double paymentAmount;

    private String paymentMethod;

    private String razorpayOrderId;

    private String razorpayPaymentId;
}