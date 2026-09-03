package com.insurance.repository;

import com.insurance.entity.PolicyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyTypeRepository extends JpaRepository<PolicyType, Long> {
}