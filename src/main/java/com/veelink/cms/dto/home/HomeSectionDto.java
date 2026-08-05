package com.veelink.cms.dto.home;

import com.veelink.cms.entity.enums.SectionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeSectionDto {
    private Long id;
    @NotNull
    private SectionType sectionType;
    @NotBlank
    private String title;
    private String description;
    private String iconUrl;
    private String imageUrl;
    @NotNull
    private Integer displayOrder;
    @NotNull
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}