package com.insurance.controller;

import com.insurance.dto.CreateClaimRequest;
import com.insurance.entity.Claim;
import com.insurance.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    public Claim submitClaim(@RequestBody CreateClaimRequest request) {

        return claimService.submitClaim(request);
    }

    @GetMapping
    public List<Claim> getAllClaims() {

        return claimService.getAllClaims();
    }

    @GetMapping("/{claimId}")
    public Claim getClaimById(@PathVariable Long claimId) {

        return claimService.getClaimById(claimId);
    }

    @GetMapping("/status/{claimId}")
    public String getClaimStatus(@PathVariable Long claimId) {

        return claimService.getClaimStatus(claimId);
    }

    @GetMapping("/history/{userId}")
    public List<Claim> getClaimHistory(@PathVariable Long userId) {

        return claimService.getClaimHistoryByUser(userId);
    }
}
