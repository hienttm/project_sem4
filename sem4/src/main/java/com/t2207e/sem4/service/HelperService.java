package com.t2207e.sem4.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class HelperService {
    public String ConvertFromImageToBase64String(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        String encodedBase64 = Base64.getEncoder().encodeToString(fileBytes);

        String contentType = file.getContentType();
        assert contentType != null;
        String[] parts = contentType.split("/");
        String imageType = parts[1];

        return "data:image/" + imageType + ";base64," + encodedBase64;
    }

    public String ConvertFromVideoToBase64String(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        String encodedBase64 = Base64.getEncoder().encodeToString(fileBytes);

        String contentType = file.getContentType();
        assert contentType != null;
        String[] parts = contentType.split("/");
        String imageType = parts[1];

        return "data:video/" + imageType + ";base64," + encodedBase64;
    }
}
