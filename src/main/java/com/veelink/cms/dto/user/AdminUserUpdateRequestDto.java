package com.veelink.cms.dto.user;

import com.veelink.cms.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserUpdateRequestDto {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @Size(min = 8)
    private String password;
    @NotNull
    private Role role;
    @NotNull
    private Boolean isActive;
}