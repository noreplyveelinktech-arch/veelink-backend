package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.about.AboutContentRequestDto;
import com.veelink.cms.dto.about.AboutContentResponseDto;
import com.veelink.cms.service.AboutContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/about-content")
@RequiredArgsConstructor
public class AboutContentAdminController {

    private final AboutContentService aboutContentService;

    @GetMapping
    public AboutContentResponseDto getAboutContent() {
        return aboutContentService.getAdminAboutContent();
    }

    @PutMapping
    public AboutContentResponseDto updateAboutContent(@Valid @RequestBody AboutContentRequestDto requestDto) {
        return aboutContentService.update(requestDto);
    }
}