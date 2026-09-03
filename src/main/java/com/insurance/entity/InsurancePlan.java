package com.insurance.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "insurance_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insurancePlanId;

    private String planName;

    private Double coverageAmount;

    private Double premiumAmount;

    private Integer policyTerm;

    private String description;

    private String status;

    @ManyToOne
    @JoinColumn(name = "policy_type_id")
    @JsonIgnore
    private PolicyType policyType;

    @OneToMany(mappedBy = "insurancePlan")
    @JsonIgnore
    private List<CustomerPolicy> customerPolicies;

}