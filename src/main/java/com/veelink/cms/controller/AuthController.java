package com.veelink.cms.controller;

import com.veelink.cms.dto.auth.AuthResponseDto;
import com.veelink.cms.dto.auth.LoginRequestDto;
import com.veelink.cms.dto.common.MessageResponse;
import com.veelink.cms.dto.user.AdminUserResponseDto;
import com.veelink.cms.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto requestDto) {
        return authService.login(requestDto);
    }

    @GetMapping("/me")
    public AdminUserResponseDto me(Authentication authentication) {
        return authService.me(authentication);
    }

    @PostMapping("/logout")
    public MessageResponse logout() {
        return authService.logout();
    }
}