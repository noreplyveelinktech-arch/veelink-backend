package com.veelink.cms.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.veelink.cms.exception.BadRequestException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Uploads admin-supplied images (logo, course images, team photos, etc.) directly to Cloudinary
 * so the Admin Panel can offer a real "upload a file" experience instead of requiring admins to
 * host images elsewhere and paste a URL.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private static final Set<String> ALLOWED_CONTENT_TYPES =
            Set.of("image/jpeg", "image/png", "image/webp", "image/gif", "image/svg+xml");
    private static final long MAX_FILE_SIZE_BYTES = 5L * 1024 * 1024; // 5 MB

    private final Cloudinary cloudinary;

    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("No file was provided.");
        }
        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new BadRequestException("Image is too large. Maximum allowed size is 5 MB.");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new BadRequestException("Unsupported file type. Allowed types: JPG, PNG, WEBP, GIF, SVG.");
        }

        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", "veelink-cms",
                    "resource_type", "image"));
            return String.valueOf(result.get("secure_url"));
        } catch (IOException ex) {
            log.error("Failed to upload image to Cloudinary", ex);
            throw new BadRequestException("Failed to upload image: " + ex.getMessage());
        }
    }
}
