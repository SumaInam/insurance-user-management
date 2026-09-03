package com.insurance.service;

import com.insurance.entity.PolicyType;

import java.util.List;

public interface PolicyTypeService {

    PolicyType createPolicyType(PolicyType policyType);

    List<PolicyType> getAllPolicyTypes();
}
