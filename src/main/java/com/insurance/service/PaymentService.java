package com.insurance.service;

import com.insurance.dto.PaymentRequest;
import com.insurance.entity.Payment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {

    Payment createPayment(PaymentRequest request);

    Payment completePayment(Long paymentId, String razorpayPaymentId);

    Payment failedPayment(Long paymentId);

    List<Payment> getPaymentHistory(Long userId);

    Payment getPaymentById(Long paymentId);

    ResponseEntity<byte[]> downloadReceipt(Long paymentId) throws Exception;
}