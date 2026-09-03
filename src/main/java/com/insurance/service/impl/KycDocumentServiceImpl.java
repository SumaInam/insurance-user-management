package com.insurance.service.impl;

import com.insurance.entity.KycDocument;
import com.insurance.entity.User;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.KycDocumentRepository;
import com.insurance.repository.UserRepository;
import com.insurance.service.KycDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KycDocumentServiceImpl implements KycDocumentService {

    private final KycDocumentRepository kycDocumentRepository;

    private final UserRepository userRepository;

    @Override
    public KycDocument uploadDocument(
            Long userId,
            MultipartFile file,
            String documentType) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        try {

            //String uploadDirectory = "uploads/";

            String uploadDirectory = System.getProperty("user.dir") + "/uploads/";

            File folder = new File(uploadDirectory);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filePath =
                    uploadDirectory
                            + file.getOriginalFilename();

            file.transferTo(new File(filePath));

            KycDocument document = new KycDocument();

            document.setDocumentName(file.getOriginalFilename());

            document.setDocumentType(documentType);

            document.setFilePath(filePath);

            document.setUploadDate(LocalDateTime.now());

            document.setStatus("PENDING");

            document.setUser(user);

            return kycDocumentRepository.save(document);

        }
        catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("KYC Document Upload Failed : " + e.getMessage());
        }
//        catch (Exception e) {
//
//            throw new RuntimeException("KYC Document Upload Failed");
//        }
    }

    @Override
    public List<KycDocument> getUserKyc(Long userId) {

        return kycDocumentRepository.findByUserUserId(userId);
    }
}