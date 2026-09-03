package com.insurance.controller;

import com.insurance.entity.KycDocument;
import com.insurance.service.KycDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor
public class KycDocumentController {

    private final KycDocumentService kycService;

    @PostMapping("/{userId}")
    public KycDocument uploadDocument(@PathVariable Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType){

        return kycService.uploadDocument(
                        userId,
                        file,
                        documentType);
    }

    @GetMapping("/user/{userId}")
    public List<KycDocument> getUserKyc(
            @PathVariable Long userId){

        return kycService.getUserKyc(userId);
    }
}