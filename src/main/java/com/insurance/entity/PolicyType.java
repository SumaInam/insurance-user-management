package com.insurance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "policy_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyTypeId;

    private String policyTypeName;

    @JsonIgnore
    @OneToMany(mappedBy = "policyType")
    private List<InsurancePlan> insurancePlans;
}
