package com.insurance.service;

import com.insurance.entity.Claim;
import com.insurance.entity.CustomerPolicy;
import com.insurance.entity.KycDocument;
import com.insurance.entity.User;

import java.util.List;

public interface AdminService {

    List<User> getAllUsers();

    List<CustomerPolicy> getAllPolicies();

    List<Claim> getAllClaims();

    List<KycDocument> getAllKycDocuments();

    KycDocument approveKyc(Long kycId);

    KycDocument rejectKyc(Long kycId);

    Claim approveClaim(Long claimId);

    Claim rejectClaim(Long claimId, String reason);

    Claim settleClaim(Long claimId, Double amount);
}