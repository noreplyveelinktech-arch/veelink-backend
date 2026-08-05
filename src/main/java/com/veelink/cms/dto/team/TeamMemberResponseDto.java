package com.veelink.cms.dto.team;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TeamMemberResponseDto {
    private Long id;
    private String fullName;
    private String designation;
    private String description;
    private String photoUrl;
    private String linkedinUrl;
    private Integer displayOrder;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}