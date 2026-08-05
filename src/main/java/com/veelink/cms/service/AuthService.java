package com.veelink.cms.service;

import com.veelink.cms.dto.auth.AuthResponseDto;
import com.veelink.cms.dto.auth.LoginRequestDto;
import com.veelink.cms.dto.common.MessageResponse;
import com.veelink.cms.dto.user.AdminUserResponseDto;
import com.veelink.cms.entity.AdminUser;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminUserService adminUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDto login(LoginRequestDto requestDto) {
        AdminUser adminUser;
        try {
            adminUser = adminUserService.getByEmail(requestDto.getEmail());
        } catch (ResourceNotFoundException ex) {
            throw new BadCredentialsException("Invalid email or password");
        }
        if (!Boolean.TRUE.equals(adminUser.getIsActive()) || !passwordEncoder.matches(requestDto.getPassword(), adminUser.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return AuthResponseDto.builder()
                .token(jwtUtil.generateToken(adminUser))
                .tokenType("Bearer")
                .user(toResponse(adminUser))
                .build();
    }

    public AdminUserResponseDto me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new BadCredentialsException("Authentication required");
        }
        try {
            return toResponse(adminUserService.getByEmail(authentication.getName()));
        } catch (ResourceNotFoundException ex) {
            throw new BadCredentialsException("Authentication required");
        }
    }

    public MessageResponse logout() {
        return new MessageResponse("Logged out successfully.");
    }

    private AdminUserResponseDto toResponse(AdminUser adminUser) {
        return AdminUserResponseDto.builder()
                .id(adminUser.getId())
                .name(adminUser.getName())
                .email(adminUser.getEmail())
                .role(adminUser.getRole())
                .isActive(adminUser.getIsActive())
                .createdAt(adminUser.getCreatedAt())
                .updatedAt(adminUser.getUpdatedAt())
                .build();
    }
}