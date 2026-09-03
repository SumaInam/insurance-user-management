package com.insurance.repository;

import com.insurance.entity.CustomerPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerPolicyRepository extends JpaRepository<CustomerPolicy,Long> {

    List<CustomerPolicy> findByUserId(Long userId);
}
