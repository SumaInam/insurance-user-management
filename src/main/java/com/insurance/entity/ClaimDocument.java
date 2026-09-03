package com.insurance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "claim_document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimDocumentId;

    private String documentName;

    private String documentPath;

    @ManyToOne
    @JoinColumn(name = "claim_id")
    @JsonIgnore
    private Claim claim;
}