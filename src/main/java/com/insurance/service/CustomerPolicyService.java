package com.insurance.service;

import com.insurance.dto.PurchasePolicyRequest;
import com.insurance.entity.CustomerPolicy;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerPolicyService {

    CustomerPolicy purchasePolicy(PurchasePolicyRequest request);

    List<CustomerPolicy> getAllPolicies();

    CustomerPolicy getPolicyById(Long customerPolicyId);

    List<CustomerPolicy> getPoliciesByUser(Long userId);

    CustomerPolicy renewPolicy(Long customerPolicyId);

    ResponseEntity<byte[]> downloadPolicy(Long customerPolicyId) throws Exception;
}
