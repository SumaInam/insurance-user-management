package com.insurance.controller;

import com.insurance.dto.PurchasePolicyRequest;
import com.insurance.entity.CustomerPolicy;
import com.insurance.service.CustomerPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class CustomerPolicyController {

    private final CustomerPolicyService customerPolicyService;

    @PostMapping("/purchase")
    public CustomerPolicy purchasePolicy(@RequestBody PurchasePolicyRequest request) {

        return customerPolicyService.purchasePolicy(request);
    }

    @GetMapping("/user/{userId}")
    public List<CustomerPolicy> getPoliciesByUser(@PathVariable Long userId) {

        return customerPolicyService.getPoliciesByUser(userId);
    }

    @GetMapping("/status/{customerPolicyId}")
    public String getPolicyStatus(@PathVariable Long customerPolicyId) {

        CustomerPolicy policy = customerPolicyService.getPolicyById(customerPolicyId);

        return policy.getStatus();
    }

    @PutMapping("/renew/{customerPolicyId}")
    public CustomerPolicy renewPolicy(@PathVariable Long customerPolicyId) {

        return customerPolicyService.renewPolicy(customerPolicyId);
    }

   /* @GetMapping("/download/{customerPolicyId}")
    public String downloadPolicy(
            @PathVariable Long customerPolicyId) {

        CustomerPolicy policy = customerPolicyService.getPolicyById(customerPolicyId);

        return "Policy Downloaded Successfully : " + policy.getPolicyNumber();
    }*/

    @GetMapping("/download/{customerPolicyId}")
    public ResponseEntity<byte[]> downloadPolicy(@PathVariable Long customerPolicyId) throws Exception {

        return customerPolicyService.downloadPolicy(customerPolicyId);
    }

    @GetMapping
    public List<CustomerPolicy> getAllPolicies() {

        return customerPolicyService.getAllPolicies();
    }

    @GetMapping("/{customerPolicyId}")
    public CustomerPolicy getPolicyById(@PathVariable Long customerPolicyId) {

        return customerPolicyService.getPolicyById(customerPolicyId);
    }
}