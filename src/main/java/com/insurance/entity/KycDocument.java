package com.insurance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc_documents")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KycDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private String documentName;

    private String documentType;

    private String filePath;

    private LocalDateTime uploadDate;

    private String status;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;
}