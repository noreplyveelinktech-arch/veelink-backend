package com.veelink.cms.dto.home;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HomeContentResponseDto {
    private Long id;
    private String heroTitle;
    private String heroSubtitle;
    private String heroDescription;
    private String heroImageUrl;
    private String primaryButtonText;
    private String primaryButtonLink;
    private String secondaryButtonText;
    private String secondaryButtonLink;
    private String whyChooseUsTitle;
    private String highlightsTitle;
    private List<HomeSectionDto> whyChooseUs;
    private List<HomeSectionDto> highlights;
    private List<HomeSectionDto> successSections;
    private List<HomeSectionDto> sections;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}