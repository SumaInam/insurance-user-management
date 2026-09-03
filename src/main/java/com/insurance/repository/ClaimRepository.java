package com.insurance.repository;

import com.insurance.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findByUserUserId(Long userId);

    List<Claim> findByCustomerPolicyCustomerPolicyId(
            Long customerPolicyId);
}