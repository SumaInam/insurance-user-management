package com.insurance.controller;

import com.insurance.dto.ClaimDocumentRequest;
import com.insurance.entity.ClaimDocument;
import com.insurance.service.ClaimDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claim-documents")
@RequiredArgsConstructor
public class ClaimDocumentController {

    private final ClaimDocumentService service;

    @PostMapping
    public ClaimDocument uploadDocument(@RequestBody ClaimDocumentRequest request) {

        return service.uploadDocument(request);
    }

    @GetMapping("/{claimId}")
    public List<ClaimDocument> getDocumentsByClaim(@PathVariable Long claimId) {

        return service.getDocumentsByClaim(claimId);
    }
}