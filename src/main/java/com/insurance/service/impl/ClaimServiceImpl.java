package com.insurance.service.impl;

import com.insurance.dto.CreateClaimRequest;
import com.insurance.dto.NotificationRequest;
import com.insurance.entity.Claim;
import com.insurance.entity.ClaimStatus;
import com.insurance.entity.CustomerPolicy;
import com.insurance.entity.User;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.ClaimRepository;
import com.insurance.repository.CustomerPolicyRepository;
import com.insurance.repository.UserRepository;
import com.insurance.service.ClaimService;
import com.insurance.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    private final UserRepository userRepository;

    private final CustomerPolicyRepository customerPolicyRepository;

    private final NotificationService notificationService;

    @Override
    public Claim submitClaim(CreateClaimRequest request) {

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        CustomerPolicy customerPolicy = customerPolicyRepository
                        .findById(request.getCustomerPolicyId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Policy not found"));

        Claim claim = new Claim();

        claim.setClaimAmount(request.getClaimAmount());

        claim.setClaimReason(request.getClaimReason());

        claim.setClaimDate(LocalDateTime.now());

        claim.setStatus(ClaimStatus.SUBMITTED);

        claim.setUser(user);

        claim.setCustomerPolicy(customerPolicy);

        /* CHECK EXISTING CLAIM */

        List<Claim> claims =
                claimRepository
                        .findByCustomerPolicyCustomerPolicyId(
                                request.getCustomerPolicyId());

        if(!claims.isEmpty()){

            throw new RuntimeException(
                    "Claim already submitted for this policy");
        }

        /* SAVE CLAIM */

        //return claimRepository.save(claim);

        Claim savedClaim =
                claimRepository.save(claim);

        NotificationRequest notification = new NotificationRequest();

        notification.setUserId(
                user.getUserId());

        notification.setTitle("Claim Submitted");

        notification.setMessage("Your claim has been submitted successfully.");

        notification.setNotificationType("CLAIM");

        notificationService.createNotification(notification);

        return savedClaim;
    }

    @Override
    public List<Claim> getAllClaims() {

        return claimRepository.findAll();
    }

    @Override
    public Claim getClaimById(Long claimId) {

        return claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
    }

    @Override
    public String getClaimStatus(Long claimId) {

        return getClaimById(claimId)
                .getStatus()
                .name();
    }

    @Override
    public List<Claim> getClaimHistoryByUser(Long userId) {

        return claimRepository.findByUserUserId(userId);
    }
}