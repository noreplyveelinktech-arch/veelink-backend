package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.common.UploadResponse;
import com.veelink.cms.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/uploads")
@RequiredArgsConstructor
public class UploadAdminController {

    private final ImageUploadService imageUploadService;

    @PostMapping
    public UploadResponse uploadImage(@RequestParam("file") MultipartFile file) {
        return new UploadResponse(imageUploadService.upload(file));
    }
}
