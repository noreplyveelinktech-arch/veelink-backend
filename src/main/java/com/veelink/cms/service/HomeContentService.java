package com.veelink.cms.service;

import com.veelink.cms.dto.home.HomeContentRequestDto;
import com.veelink.cms.dto.home.HomeContentResponseDto;
import com.veelink.cms.dto.home.HomeSectionDto;
import com.veelink.cms.entity.HomeContent;
import com.veelink.cms.entity.HomeSection;
import com.veelink.cms.entity.enums.SectionType;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.repository.HomeContentRepository;
import com.veelink.cms.repository.HomeSectionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeContentService {

    private final HomeContentRepository homeContentRepository;
    private final HomeSectionRepository homeSectionRepository;

    public HomeContentResponseDto getPublicHomeContent() {
        return toResponse(getHomeContentEntity(), true);
    }

    public HomeContentResponseDto getAdminHomeContent() {
        return toResponse(getHomeContentEntity(), false);
    }

    public HomeContentResponseDto update(HomeContentRequestDto requestDto) {
        HomeContent content = getHomeContentEntity();
        content.setHeroTitle(requestDto.getHeroTitle());
        content.setHeroSubtitle(requestDto.getHeroSubtitle());
        content.setHeroDescription(requestDto.getHeroDescription());
        content.setHeroImageUrl(requestDto.getHeroImageUrl());
        content.setPrimaryButtonText(requestDto.getPrimaryButtonText());
        content.setPrimaryButtonLink(requestDto.getPrimaryButtonLink());
        content.setSecondaryButtonText(requestDto.getSecondaryButtonText());
        content.setSecondaryButtonLink(requestDto.getSecondaryButtonLink());
        content.setWhyChooseUsTitle(requestDto.getWhyChooseUsTitle());
        content.setHighlightsTitle(requestDto.getHighlightsTitle());
        homeContentRepository.save(content);
        if (requestDto.getSections() != null) {
            homeSectionRepository.deleteAll();
            homeSectionRepository.saveAll(requestDto.getSections().stream().map(this::toEntity).toList());
        }
        return getAdminHomeContent();
    }

    private HomeContent getHomeContentEntity() {
        return homeContentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Home content not found"));
    }

    private HomeContentResponseDto toResponse(HomeContent content, boolean publicMode) {
        List<HomeSection> allSections = publicMode
                ? homeSectionRepository.findAllByOrderBySectionTypeAscDisplayOrderAsc().stream()
                    .filter(section -> Boolean.TRUE.equals(section.getIsActive()))
                    .toList()
                : homeSectionRepository.findAllByOrderBySectionTypeAscDisplayOrderAsc();
        return HomeContentResponseDto.builder()
                .id(content.getId())
                .heroTitle(content.getHeroTitle())
                .heroSubtitle(content.getHeroSubtitle())
                .heroDescription(content.getHeroDescription())
                .heroImageUrl(content.getHeroImageUrl())
                .primaryButtonText(content.getPrimaryButtonText())
                .primaryButtonLink(content.getPrimaryButtonLink())
                .secondaryButtonText(content.getSecondaryButtonText())
                .secondaryButtonLink(content.getSecondaryButtonLink())
                .whyChooseUsTitle(content.getWhyChooseUsTitle())
                .highlightsTitle(content.getHighlightsTitle())
                .whyChooseUs(filterByType(allSections, SectionType.WHY_CHOOSE_US))
                .highlights(filterByType(allSections, SectionType.HIGHLIGHT))
                .successSections(filterByType(allSections, SectionType.SUCCESS))
                .sections(allSections.stream().map(this::toDto).toList())
                .createdAt(content.getCreatedAt())
                .updatedAt(content.getUpdatedAt())
                .build();
    }

    private List<HomeSectionDto> filterByType(List<HomeSection> sections, SectionType sectionType) {
        return sections.stream()
                .filter(section -> section.getSectionType() == sectionType)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private HomeSection toEntity(HomeSectionDto dto) {
        HomeSection section = new HomeSection();
        section.setSectionType(dto.getSectionType());
        section.setTitle(dto.getTitle());
        section.setDescription(dto.getDescription());
        section.setIconUrl(dto.getIconUrl());
        section.setImageUrl(dto.getImageUrl());
        section.setDisplayOrder(dto.getDisplayOrder());
        section.setIsActive(dto.getIsActive());
        return section;
    }

    private HomeSectionDto toDto(HomeSection section) {
        return HomeSectionDto.builder()
                .id(section.getId())
                .sectionType(section.getSectionType())
                .title(section.getTitle())
                .description(section.getDescription())
                .iconUrl(section.getIconUrl())
                .imageUrl(section.getImageUrl())
                .displayOrder(section.getDisplayOrder())
                .isActive(section.getIsActive())
                .createdAt(section.getCreatedAt())
                .updatedAt(section.getUpdatedAt())
                .build();
    }
}