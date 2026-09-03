package com.insurance.dto;

import lombok.Data;

@Data
public class PurchasePolicyRequest {

    private Long userId;

    private Long insurancePlanId;
}
