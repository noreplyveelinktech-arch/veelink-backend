package com.veelink.cms.service;

import com.veelink.cms.dto.user.AdminUserCreateRequestDto;
import com.veelink.cms.dto.user.AdminUserResponseDto;
import com.veelink.cms.dto.user.AdminUserUpdateRequestDto;
import com.veelink.cms.entity.AdminUser;
import com.veelink.cms.exception.BadRequestException;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.repository.AdminUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AdminUserResponseDto> getUsers() {
        return adminUserRepository.findAll().stream().map(this::toDto).toList();
    }

    public AdminUserResponseDto getUser(Long id) {
        return toDto(getEntity(id));
    }

    public AdminUserResponseDto create(AdminUserCreateRequestDto requestDto) {
        if (adminUserRepository.existsByEmailIgnoreCase(requestDto.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setName(requestDto.getName());
        adminUser.setEmail(requestDto.getEmail());
        adminUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        adminUser.setRole(requestDto.getRole());
        adminUser.setIsActive(requestDto.getIsActive());
        return toDto(adminUserRepository.save(adminUser));
    }

    public AdminUserResponseDto update(Long id, AdminUserUpdateRequestDto requestDto) {
        AdminUser adminUser = getEntity(id);
        adminUserRepository.findByEmailIgnoreCase(requestDto.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> { throw new BadRequestException("Email is already in use"); });
        adminUser.setName(requestDto.getName());
        adminUser.setEmail(requestDto.getEmail());
        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
            adminUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }
        adminUser.setRole(requestDto.getRole());
        adminUser.setIsActive(requestDto.getIsActive());
        return toDto(adminUserRepository.save(adminUser));
    }

    public void disable(Long id) {
        AdminUser adminUser = getEntity(id);
        adminUser.setIsActive(Boolean.FALSE);
        adminUserRepository.save(adminUser);
    }

    public AdminUser getByEmail(String email) {
        return adminUserRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin user not found"));
    }

    private AdminUser getEntity(Long id) {
        return adminUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin user not found with id: " + id));
    }

    private AdminUserResponseDto toDto(AdminUser adminUser) {
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