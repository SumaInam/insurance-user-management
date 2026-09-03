package com.insurance.service;

import com.insurance.dto.ClaimDocumentRequest;
import com.insurance.entity.ClaimDocument;

import java.util.List;

public interface ClaimDocumentService {

    ClaimDocument uploadDocument(ClaimDocumentRequest request);

    List<ClaimDocument> getDocumentsByClaim(Long claimId);
}