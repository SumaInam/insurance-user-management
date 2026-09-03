package com.insurance.service.impl;

import com.insurance.entity.PolicyType;
import com.insurance.repository.PolicyTypeRepository;
import com.insurance.service.PolicyTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyTypeServiceImpl implements PolicyTypeService {

    private final PolicyTypeRepository repository;

    @Override
    public PolicyType createPolicyType(PolicyType policyType) {

        return repository.save(policyType);
    }

    @Override
    public List<PolicyType> getAllPolicyTypes() {

        return repository.findAll();
    }
}