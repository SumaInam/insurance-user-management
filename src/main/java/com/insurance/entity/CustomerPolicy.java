package com.insurance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "customer_policy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerPolicyId;

    private String policyNumber;

    private LocalDate purchaseDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double premiumAmount;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "insurance_plan_id")
    @JsonIgnore
    private InsurancePlan insurancePlan;

}