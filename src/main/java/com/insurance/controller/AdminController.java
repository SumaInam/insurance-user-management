package com.insurance.controller;

import com.insurance.entity.*;
import com.insurance.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<User> getAllUsers() {

        return adminService.getAllUsers();
    }

    @GetMapping("/policies")
    public List<CustomerPolicy> getAllPolicies() {

        return adminService.getAllPolicies();
    }

    @GetMapping("/claims")
    public List<Claim> getAllClaims() {

        return adminService.getAllClaims();
    }

    @GetMapping("/kyc")
    public List<KycDocument> getAllKycDocuments() {

        return adminService.getAllKycDocuments();
    }

    @PutMapping("/kyc/{kycId}/approve")
    public KycDocument approveKyc(@PathVariable Long kycId) {

        return adminService.approveKyc(kycId);
    }

    @PutMapping("/kyc/{kycId}/reject")
    public KycDocument rejectKyc(@PathVariable Long kycId) {

        return adminService.rejectKyc(kycId);
    }

    @PutMapping("/claims/{claimId}/approve")
    public Claim approveClaim(@PathVariable Long claimId) {

        return adminService.approveClaim(claimId);
    }

    @PutMapping("/claims/{claimId}/reject")
    public Claim rejectClaim(@PathVariable Long claimId, @RequestParam String reason) {

        return adminService.rejectClaim(claimId, reason);
    }

    @PutMapping("/claims/{claimId}/settle")
    public Claim settleClaim(@PathVariable Long claimId, @RequestParam Double amount) {

        return adminService.settleClaim(claimId, amount);
    }
}