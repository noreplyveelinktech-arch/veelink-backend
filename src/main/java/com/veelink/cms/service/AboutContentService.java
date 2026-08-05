package com.veelink.cms.service;

import com.veelink.cms.dto.about.AboutContentRequestDto;
import com.veelink.cms.dto.about.AboutContentResponseDto;
import com.veelink.cms.entity.AboutContent;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.repository.AboutContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AboutContentService {

    private final AboutContentRepository aboutContentRepository;
    private final TeamMemberService teamMemberService;

    public AboutContentResponseDto getPublicAboutContent() {
        return toDto(getEntity(), true);
    }

    public AboutContentResponseDto getAdminAboutContent() {
        return toDto(getEntity(), false);
    }

    public AboutContentResponseDto update(AboutContentRequestDto requestDto) {
        AboutContent aboutContent = getEntity();
        aboutContent.setPageTitle(requestDto.getPageTitle());
        aboutContent.setPageSubtitle(requestDto.getPageSubtitle());
        aboutContent.setAboutDescription(requestDto.getAboutDescription());
        aboutContent.setMission(requestDto.getMission());
        aboutContent.setVision(requestDto.getVision());
        aboutContent.setAboutImageUrl(requestDto.getAboutImageUrl());
        aboutContent.setValuesTitle(requestDto.getValuesTitle());
        return toDto(aboutContentRepository.save(aboutContent), false);
    }

    private AboutContent getEntity() {
        return aboutContentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("About content not found"));
    }

    private AboutContentResponseDto toDto(AboutContent aboutContent, boolean publicMode) {
        return AboutContentResponseDto.builder()
                .id(aboutContent.getId())
                .pageTitle(aboutContent.getPageTitle())
                .pageSubtitle(aboutContent.getPageSubtitle())
                .aboutDescription(aboutContent.getAboutDescription())
                .mission(aboutContent.getMission())
                .vision(aboutContent.getVision())
                .aboutImageUrl(aboutContent.getAboutImageUrl())
                .valuesTitle(aboutContent.getValuesTitle())
                .teamMembers(publicMode ? teamMemberService.getPublicTeamMembers() : teamMemberService.getAllTeamMembers())
                .createdAt(aboutContent.getCreatedAt())
                .updatedAt(aboutContent.getUpdatedAt())
                .build();
    }
}