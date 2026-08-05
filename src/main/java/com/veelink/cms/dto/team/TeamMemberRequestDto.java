package com.veelink.cms.dto.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamMemberRequestDto {
    @NotBlank
    private String fullName;
    private String designation;
    private String description;
    private String photoUrl;
    private String linkedinUrl;
    @NotNull
    private Integer displayOrder;
    @NotNull
    private Boolean isActive;
}