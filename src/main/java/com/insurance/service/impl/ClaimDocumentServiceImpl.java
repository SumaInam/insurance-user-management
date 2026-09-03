package com.insurance.service.impl;

import com.insurance.dto.ClaimDocumentRequest;
import com.insurance.entity.Claim;
import com.insurance.entity.ClaimDocument;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.ClaimDocumentRepository;
import com.insurance.repository.ClaimRepository;
import com.insurance.service.ClaimDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimDocumentServiceImpl implements ClaimDocumentService {

    private final ClaimRepository claimRepository;

    private final ClaimDocumentRepository claimDocumentRepository;

    @Override
    public ClaimDocument uploadDocument(ClaimDocumentRequest request) {

        Claim claim = claimRepository.findById(
                request.getClaimId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Claim not found"));

        ClaimDocument document = new ClaimDocument();

        document.setDocumentName(request.getDocumentName());

        document.setDocumentPath(request.getDocumentPath());

        document.setClaim(claim);

        return claimDocumentRepository.save(document);
    }

    @Override
    public List<ClaimDocument> getDocumentsByClaim(Long claimId) {

        return claimDocumentRepository.findByClaimClaimId(claimId);
    }
}