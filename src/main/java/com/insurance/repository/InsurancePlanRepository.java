package com.insurance.repository;

import com.insurance.entity.InsurancePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Long> {
    @Query("""
            SELECT p
            FROM InsurancePlan p
            WHERE p.policyType.policyTypeName = :policyType
            AND p.premiumAmount <= :maxPremium
            AND p.policyTerm <= :maxTerm
            """)
    List<InsurancePlan> filterPlans(
            String policyType,
            Double maxPremium,
            Integer maxTerm);
}
