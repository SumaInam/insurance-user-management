package com.insurance.service.impl;

import com.insurance.dto.NotificationRequest;
import com.insurance.dto.PurchasePolicyRequest;
import com.insurance.entity.CustomerPolicy;
import com.insurance.entity.InsurancePlan;
import com.insurance.entity.User;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.CustomerPolicyRepository;
import com.insurance.repository.InsurancePlanRepository;
import com.insurance.repository.UserRepository;
import com.insurance.service.CustomerPolicyService;
import com.insurance.service.NotificationService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerPolicyServiceImpl implements CustomerPolicyService {
    private final CustomerPolicyRepository policyRepository;

    private final UserRepository userRepository;

    private final InsurancePlanRepository insurancePlanRepository;

    private final NotificationService  notificationService;

    @Override
    public CustomerPolicy purchasePolicy(PurchasePolicyRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        InsurancePlan plan = insurancePlanRepository.findById(request.getInsurancePlanId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Insurance plan not found"));

        CustomerPolicy policy = new CustomerPolicy();

        policy.setPolicyNumber("POL" + System.currentTimeMillis());

        policy.setPurchaseDate(LocalDate.now());

        policy.setStartDate(LocalDate.now());

        policy.setEndDate(LocalDate.now().plusYears(plan.getPolicyTerm()));

        policy.setPremiumAmount(plan.getPremiumAmount());

        policy.setStatus("ACTIVE");

        policy.setUser(user);

        policy.setInsurancePlan(plan);

        return policyRepository.save(policy);
    }

    @Override
    public List<CustomerPolicy> getAllPolicies() {
        return policyRepository.findAll();
    }

    @Override
    public CustomerPolicy getPolicyById(Long customerPolicyId) {

        return policyRepository.findById(customerPolicyId).orElseThrow(() ->
                        new ResourceNotFoundException("Policy not found"));
    }

    @Override
    public List<CustomerPolicy> getPoliciesByUser(Long userId) {

        return policyRepository.findByUserId(userId);
    }

    @Override
    public CustomerPolicy renewPolicy(Long customerPolicyId) {

        CustomerPolicy policy =
                getPolicyById(customerPolicyId);

        int policyTerm =
                policy.getInsurancePlan()
                        .getPolicyTerm();

        policy.setEndDate(
                policy.getEndDate()
                        .plusYears(policyTerm));

        policy.setStatus("RENEWED");

        CustomerPolicy savedPolicy =
                policyRepository.save(policy);

        NotificationRequest notification =
                new NotificationRequest();

        notification.setUserId(
                policy.getUser().getUserId());

        notification.setTitle(
                "Policy Renewed");

        notification.setMessage(
                "Your policy has been renewed successfully.");

        notification.setNotificationType(
                "RENEWAL");

        notificationService
                .createNotification(notification);

        return savedPolicy;
    }

//    @Override
//    public CustomerPolicy renewPolicy(Long customerPolicyId) {
//
//        CustomerPolicy policy = getPolicyById(customerPolicyId);
//
//        int policyTerm = policy.getInsurancePlan().getPolicyTerm();
//
//        policy.setEndDate(policy.getEndDate().plusYears(policyTerm));
//
//        policy.setStatus("RENEWED");
//
//        return policyRepository.save(policy);
//    }
    @Override
    public ResponseEntity<byte[]> downloadPolicy(Long customerPolicyId) throws Exception {

        CustomerPolicy policy = policyRepository.findById(customerPolicyId)
                        .orElseThrow(() ->
                                new RuntimeException("Policy not found"));

        User user = policy.getUser();

        InsurancePlan plan = policy.getInsurancePlan();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, out);

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);

        Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);

        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        Paragraph company = new Paragraph("METLIFE INSURANCE", titleFont);

        company.setAlignment(Element.ALIGN_CENTER);

        document.add(company);

        document.add(new Paragraph("INSURANCE POLICY CERTIFICATE", headingFont));

        document.add(new Paragraph("------------------------------------------------------------"));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("POLICY INFORMATION", headingFont));

        document.add(new Paragraph("Policy Number : " + policy.getPolicyNumber(), contentFont));

        document.add(new Paragraph("Policy ID : " + policy.getCustomerPolicyId(), contentFont));

        document.add(new Paragraph("Policy Status : " + policy.getStatus(), contentFont));

        document.add(new Paragraph("Purchase Date : " + policy.getPurchaseDate(), contentFont));

        document.add(new Paragraph("Start Date : " + policy.getStartDate(), contentFont));

        document.add(new Paragraph("End Date : " + policy.getEndDate(), contentFont));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("CUSTOMER INFORMATION", headingFont));

        document.add(new Paragraph("Customer Name : " + user.getFirstName() + " " + user.getLastName(), contentFont));

        document.add(new Paragraph("Email : " + user.getEmail(), contentFont));

        document.add(new Paragraph("Mobile Number : " + user.getPhoneNumber(), contentFont));

        document.add(new Paragraph("Gender : " + user.getGender(), contentFont));

        document.add(new Paragraph("Date Of Birth : " + user.getDateOfBirth(), contentFont));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("PLAN INFORMATION", headingFont));

        document.add(new Paragraph("Plan Name : " + plan.getPlanName(), contentFont));

        document.add(new Paragraph("Coverage Amount : Rs." + plan.getCoverageAmount(), contentFont));

        document.add(new Paragraph("Premium Amount : Rs." + policy.getPremiumAmount(), contentFont));

        document.add(new Paragraph("Policy Term : " + plan.getPolicyTerm() + " Year(s)", contentFont));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Insurance Declaration", headingFont));

        document.add(
                new Paragraph(
                        "This document certifies that the above customer "
                                + "has successfully purchased an insurance "
                                + "policy from MetLife Insurance. "
                                + "The customer is eligible for all coverage "
                                + "and benefits as per the selected insurance plan.",
                        contentFont));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(
                new Paragraph(
                        "Customer Signature                    Authorized Signature"));

        document.add(
                new Paragraph(
                        "__________________                    __________________"));

        document.close();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=MetLifePolicy.pdf")
                .contentType(
                        MediaType.APPLICATION_PDF)
                .body(out.toByteArray());
    }
}