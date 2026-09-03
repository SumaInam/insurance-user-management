package com.insurance.service.impl;

import com.insurance.dto.NotificationRequest;
import com.insurance.dto.PaymentRequest;
import com.insurance.entity.CustomerPolicy;
import com.insurance.entity.Payment;
import com.insurance.entity.PaymentStatus;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.CustomerPolicyRepository;
import com.insurance.repository.PaymentRepository;
import com.insurance.repository.UserRepository;
import com.insurance.service.NotificationService;
import com.insurance.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final CustomerPolicyRepository policyRepository;

    private final NotificationService  notificationService;

    @Override
    public Payment createPayment(PaymentRequest request) {

        CustomerPolicy policy = policyRepository.findById(request.getCustomerPolicyId())
                        .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        Payment payment = new Payment();

        payment.setRazorpayOrderId(request.getRazorpayOrderId());

        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());

        payment.setTransactionId("TXN" + System.currentTimeMillis());

        payment.setPaymentAmount(request.getPaymentAmount());

        payment.setPaymentMethod(request.getPaymentMethod());

        payment.setPaymentDate(LocalDateTime.now());

        payment.setPaymentStatus(PaymentStatus.PENDING);

        payment.setRazorpayOrderId(request.getRazorpayOrderId());

        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());

        payment.setCustomerPolicy(policy);


//        payment.setTransactionId("TXN" + System.currentTimeMillis());
//
//        payment.setPaymentAmount(request.getPaymentAmount());
//
//        payment.setPaymentMethod(request.getPaymentMethod());
//
//        payment.setPaymentDate(LocalDateTime.now());
//
//        payment.setPaymentStatus(PaymentStatus.PENDING);
//
//        payment.setCustomerPolicy(policy);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment completePayment(Long paymentId, String razorpayPaymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        NotificationRequest notification = new NotificationRequest();

        notification.setUserId(payment.getCustomerPolicy().getUser().getUserId());

        notification.setTitle("Payment Successful");

        notification.setMessage("Your premium payment of Rs." + payment.getPaymentAmount() + " was completed successfully.");

        notification.setNotificationType("PAYMENT");

        notificationService.createNotification(notification);

        payment.setRazorpayPaymentId(razorpayPaymentId);

        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    @Override
    public Payment failedPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        payment.setPaymentStatus(PaymentStatus.FAILED);

        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        NotificationRequest notification = new NotificationRequest();

        notification.setUserId(payment.getCustomerPolicy().getUser().getUserId());

        notification.setTitle("Payment Failed");

        notification.setMessage("Your payment of Rs." + payment.getPaymentAmount() + " has failed. Please try again.");

        notification.setNotificationType("PAYMENT");

        notificationService.createNotification(notification);

        return savedPayment;
    }

    @Override
    public List<Payment> getPaymentHistory(Long userId) {

        return paymentRepository.findByCustomerPolicyUserId(userId);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    @Override
    public ResponseEntity<byte[]> downloadReceipt(Long paymentId) throws Exception {

        Payment payment = getPaymentById(paymentId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, out);

        document.open();

        document.add(new Paragraph("METLIFE INSURANCE"));

        document.add(
                new Paragraph(
                        "PAYMENT RECEIPT"));

        document.add(new Paragraph("----------------------------------"));

        document.add(new Paragraph(
                        "Payment ID : "
                                + payment.getPaymentId()));

        document.add(
                new Paragraph(
                        "Amount : Rs."
                                + payment.getPaymentAmount()));

        document.add(
                new Paragraph(
                        "Method : "
                                + payment.getPaymentMethod()));

        document.add(
                new Paragraph(
                        "Status : "
                                + payment.getPaymentStatus()));

        document.add(
                new Paragraph(
                        "Transaction ID : "
                                + payment.getTransactionId()));

        document.add(
                new Paragraph(
                        "Razorpay Order ID : "
                                + payment.getRazorpayOrderId()));

        document.add(
                new Paragraph(
                        "Razorpay Payment ID : "
                                + payment.getRazorpayPaymentId()));

        document.add(
                new Paragraph(
                        "----------------------------------"));

        document.add(
                new Paragraph(
                        "Thank you for choosing MetLife Insurance"));

        document.close();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=PaymentReceipt.pdf")
                .contentType(
                        MediaType.APPLICATION_PDF)
                .body(out.toByteArray());
    }
}
