package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.company.CompanySettingsRequestDto;
import com.veelink.cms.dto.company.CompanySettingsResponseDto;
import com.veelink.cms.service.CompanySettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/company-settings")
@RequiredArgsConstructor
public class CompanySettingsAdminController {

    private final CompanySettingsService companySettingsService;

    @GetMapping
    public CompanySettingsResponseDto getCompanySettings() {
        return companySettingsService.getCompanySettings();
    }

    @PutMapping
    public CompanySettingsResponseDto updateCompanySettings(@Valid @RequestBody CompanySettingsRequestDto requestDto) {
        return companySettingsService.update(requestDto);
    }
}