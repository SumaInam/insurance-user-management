package com.insurance.controller;

import com.insurance.entity.PolicyType;
import com.insurance.service.PolicyTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy-types")
@RequiredArgsConstructor
public class PolicyTypeController {

    private final PolicyTypeService service;

    @PostMapping
    public PolicyType createPolicyType(
            @RequestBody PolicyType policyType) {

        return service.createPolicyType(policyType);
    }

    @GetMapping
    public List<PolicyType> getAllPolicyTypes() {

        return service.getAllPolicyTypes();
    }
}