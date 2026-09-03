package com.insurance.service.impl;

import com.insurance.entity.*;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.*;
import com.insurance.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final CustomerPolicyRepository policyRepository;

    private final ClaimRepository claimRepository;

    private final KycDocumentRepository kycRepository;

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public List<CustomerPolicy> getAllPolicies() {

        return policyRepository.findAll();
    }

    @Override
    public List<Claim> getAllClaims() {

        return claimRepository.findAll();
    }

    @Override
    public List<KycDocument> getAllKycDocuments() {

        return kycRepository.findAll();
    }

    @Override
    public KycDocument approveKyc(Long kycId) {

        KycDocument kyc = kycRepository.findById(kycId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("KYC not found"));

        kyc.setStatus("APPROVED");

        return kycRepository.save(kyc);
    }

    @Override
    public KycDocument rejectKyc(Long kycId) {

        KycDocument kyc = kycRepository.findById(kycId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("KYC not found"));

        kyc.setStatus("REJECTED");

        return kycRepository.save(kyc);
    }

    @Override
    public Claim approveClaim(Long claimId) {

        Claim claim = claimRepository.findById(claimId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Claim not found"));

        claim.setStatus(ClaimStatus.APPROVED);

        return claimRepository.save(claim);
    }

    @Override
    public Claim rejectClaim(Long claimId, String reason) {

        Claim claim = claimRepository.findById(claimId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Claim not found"));

        claim.setStatus(ClaimStatus.REJECTED);

        claim.setRejectionReason(reason);

        return claimRepository.save(claim);
    }

    @Override
    public Claim settleClaim(Long claimId, Double amount) {

        Claim claim = claimRepository.findById(claimId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Claim not found"));

        claim.setSettlementAmount(amount);

        claim.setStatus(ClaimStatus.SETTLED);

        return claimRepository.save(claim);
    }
}