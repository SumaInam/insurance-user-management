package com.insurance.service.impl;

import com.insurance.dto.PlanFilterRequest;
import com.insurance.entity.InsurancePlan;
import com.insurance.entity.PolicyType;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.InsurancePlanRepository;
import com.insurance.repository.PolicyTypeRepository;
import com.insurance.service.InsurancePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class InsurancePlanServiceImpl implements InsurancePlanService {

    private final InsurancePlanRepository repository;
    private final PolicyTypeRepository policyTypeRepository;

    //    @Override
//    public InsurancePlan createPlan(InsurancePlan plan) {
//
//        return repository.save(plan);
//    }
    @Override
    public InsurancePlan createPlan(InsurancePlan plan) {

        Long policyTypeId = plan.getPolicyType().getPolicyTypeId();

        PolicyType policyType = policyTypeRepository.findById(policyTypeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Policy Type Not Found"));

        plan.setPolicyType(policyType);

        return repository.save(plan);
    }

    @Override
    public List<InsurancePlan> getAllPlans() {

        return repository.findAll();
    }

    @Override
    public InsurancePlan getPlanById(Long planId) {

        return repository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Plan not found"));
    }

    @Override
    public Map<String, Object> comparePlans(Long insurancePlan1Id, Long insurancePlan2Id) {

        InsurancePlan plan1 =
                repository.findById(insurancePlan1Id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Plan 1 not found"));

        InsurancePlan plan2 =
                repository.findById(insurancePlan2Id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Plan 2 not found"));

        Map<String, Object> response = new HashMap<>();

        response.put("plan1", plan1);

        response.put("plan2", plan2);

        return response;
    }

    public List<InsurancePlan> filterPlans(PlanFilterRequest request) {

        return repository.filterPlans(
                request.getPolicyType(),
                request.getMaxPremiumAmount(),
                request.getMaxPolicyTerm());
    }
}