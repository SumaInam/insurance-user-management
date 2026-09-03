package com.insurance.service;

import com.insurance.dto.CreateClaimRequest;
import com.insurance.entity.Claim;

import java.util.List;

public interface ClaimService {

    Claim submitClaim(CreateClaimRequest request);

    List<Claim> getAllClaims();

    Claim getClaimById(Long claimId);

    String getClaimStatus(Long claimId);

    List<Claim> getClaimHistoryByUser(Long userId);
}