package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.dashboard.DashboardStatsResponseDto;
import com.veelink.cms.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardAdminController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStatsResponseDto getStats() {
        return dashboardService.getStats();
    }
}