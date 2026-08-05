package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.common.MessageResponse;
import com.veelink.cms.dto.user.AdminUserCreateRequestDto;
import com.veelink.cms.dto.user.AdminUserResponseDto;
import com.veelink.cms.dto.user.AdminUserUpdateRequestDto;
import com.veelink.cms.service.AdminUserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final AdminUserService adminUserService;

    @GetMapping
    public List<AdminUserResponseDto> getUsers() {
        return adminUserService.getUsers();
    }

    @PostMapping
    public ResponseEntity<AdminUserResponseDto> createUser(@Valid @RequestBody AdminUserCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminUserService.create(requestDto));
    }

    @GetMapping("/{id}")
    public AdminUserResponseDto getUser(@PathVariable Long id) {
        return adminUserService.getUser(id);
    }

    @PutMapping("/{id}")
    public AdminUserResponseDto updateUser(@PathVariable Long id, @Valid @RequestBody AdminUserUpdateRequestDto requestDto) {
        return adminUserService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteUser(@PathVariable Long id) {
        adminUserService.disable(id);
        return new MessageResponse("User disabled successfully.");
    }
}