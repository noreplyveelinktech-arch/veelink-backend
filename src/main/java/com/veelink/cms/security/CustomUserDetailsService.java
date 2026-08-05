package com.veelink.cms.security;

import com.veelink.cms.entity.AdminUser;
import com.veelink.cms.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

        if (!Boolean.TRUE.equals(adminUser.getIsActive())) {
            throw new UsernameNotFoundException("User account is inactive");
        }

        return User.builder()
                .username(adminUser.getEmail())
                .password(adminUser.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + adminUser.getRole().name()))
                .disabled(!Boolean.TRUE.equals(adminUser.getIsActive()))
                .build();
    }
}