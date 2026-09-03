package com.insurance.dto;

import lombok.Data;

@Data
public class CreateClaimRequest {

    private Long userId;

    private Long customerPolicyId;

    private Double claimAmount;

    private String claimReason;
}