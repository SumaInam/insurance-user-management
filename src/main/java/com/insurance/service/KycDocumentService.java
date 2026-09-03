package com.insurance.service;

import com.insurance.entity.KycDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface KycDocumentService {

    KycDocument uploadDocument(Long userId, MultipartFile file, String documentType);
    List<KycDocument> getUserKyc(Long userId);
}