package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.home.HomeContentRequestDto;
import com.veelink.cms.dto.home.HomeContentResponseDto;
import com.veelink.cms.service.HomeContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/home-content")
@RequiredArgsConstructor
public class HomeContentAdminController {

    private final HomeContentService homeContentService;

    @GetMapping
    public HomeContentResponseDto getHomeContent() {
        return homeContentService.getAdminHomeContent();
    }

    @PutMapping
    public HomeContentResponseDto updateHomeContent(@Valid @RequestBody HomeContentRequestDto requestDto) {
        return homeContentService.update(requestDto);
    }
}