package com.insurance.service;

import com.insurance.dto.PlanFilterRequest;
import com.insurance.entity.InsurancePlan;

import java.util.List;
import java.util.Map;

public interface InsurancePlanService {

    InsurancePlan createPlan(InsurancePlan plan);

    List<InsurancePlan> getAllPlans();

    InsurancePlan getPlanById(Long insurancePlanId);

    List<InsurancePlan> filterPlans(PlanFilterRequest request);

    Map<String, Object> comparePlans(Long insurancePlan1Id, Long insurancePlan2Id);
}
