package com.veelink.cms.dto.home;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeContentRequestDto {
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
    @Valid
    private List<HomeSectionDto> sections;
}