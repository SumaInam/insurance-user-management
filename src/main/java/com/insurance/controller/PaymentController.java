package com.insurance.controller;

import com.insurance.dto.PaymentRequest;
import com.insurance.entity.Payment;
import com.insurance.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public Payment createPayment(@RequestBody PaymentRequest request) {

        return paymentService.createPayment(request);
    }

    @PutMapping("/success/{paymentId}")
    public Payment paymentSuccess(@PathVariable Long paymentId, @RequestParam String razorpayPaymentId) {

        return paymentService.completePayment(
                paymentId,
                razorpayPaymentId);
    }

    @PutMapping("/failed/{paymentId}")
    public Payment paymentFailed(@PathVariable Long paymentId) {

        return paymentService.failedPayment(paymentId);
    }

    @GetMapping("/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {

        return paymentService.getPaymentById(paymentId);
    }

    @GetMapping("/history/{userId}")
    public List<Payment> history(@PathVariable Long userId) {

        return paymentService.getPaymentHistory(userId);
    }

    @GetMapping("/receipt/{paymentId}")
    public ResponseEntity<byte[]> downloadReceipt(
            @PathVariable Long paymentId) throws Exception {

        return paymentService.downloadReceipt(paymentId);
    }
}
