package com.veelink.cms.dto.course;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CourseCategoryResponseDto {
    private Long id;
    private String categoryName;
    private String description;
    private Boolean isActive;
}