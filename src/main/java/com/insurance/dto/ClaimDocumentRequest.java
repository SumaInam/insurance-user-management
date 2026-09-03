package com.insurance.dto;

import lombok.Data;

@Data
public class ClaimDocumentRequest {

    private Long claimId;

    private String documentName;

    private String documentPath;
}