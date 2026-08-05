package com.veelink.cms.dto.auth;

import com.veelink.cms.dto.user.AdminUserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponseDto {
    private String token;
    private String tokenType;
    private AdminUserResponseDto user;
}