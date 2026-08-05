package com.veelink.cms.dto.about;

import com.veelink.cms.dto.team.TeamMemberResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AboutContentResponseDto {
    private Long id;
    private String pageTitle;
    private String pageSubtitle;
    private String aboutDescription;
    private String mission;
    private String vision;
    private String aboutImageUrl;
    private String valuesTitle;
    private List<TeamMemberResponseDto> teamMembers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}