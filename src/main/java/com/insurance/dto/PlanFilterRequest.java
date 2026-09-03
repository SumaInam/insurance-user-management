package com.insurance.dto;

import lombok.Data;

@Data
public class PlanFilterRequest {

    private String policyType;

    private Double maxPremiumAmount;

    private Integer maxPolicyTerm;
}
