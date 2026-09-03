package com.insurance.controller;

import com.insurance.dto.PlanFilterRequest;
import com.insurance.entity.InsurancePlan;
import com.insurance.service.InsurancePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class InsurancePlanController {

    private final InsurancePlanService insurancePlanService;

    @PostMapping
    public InsurancePlan createPlan(@RequestBody InsurancePlan insurancePlan) {

        return insurancePlanService.createPlan(insurancePlan);
    }

    @GetMapping
    public List<InsurancePlan> getAllPlans() {

        return insurancePlanService.getAllPlans();
    }

    @GetMapping("/{insurancePlanId}")
    public InsurancePlan getinsurancePlanById(@PathVariable Long insurancePlanId){

        return insurancePlanService.getPlanById(insurancePlanId);
    }


    @GetMapping("/compare")
    public Map<String, Object> comparePlans(@RequestParam Long insurancePlan1Id, @RequestParam Long insurancePlan2Id) {

        return insurancePlanService.comparePlans(insurancePlan1Id, insurancePlan2Id);
    }

    @PostMapping("/filter")
    public List<InsurancePlan> filterPlans(@RequestBody PlanFilterRequest request) {

        return insurancePlanService.filterPlans(request);
    }
}