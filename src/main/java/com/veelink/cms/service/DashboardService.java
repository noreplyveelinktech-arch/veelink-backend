package com.veelink.cms.service;

import com.veelink.cms.dto.dashboard.DashboardStatsResponseDto;
import com.veelink.cms.entity.enums.EmailStatus;
import com.veelink.cms.repository.EnquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CourseService courseService;
    private final EnquiryService enquiryService;
    private final EnquiryRepository enquiryRepository;

    public DashboardStatsResponseDto getStats() {
        return DashboardStatsResponseDto.builder()
                .totalCourses(courseService.totalCourses())
                .activeCourses(courseService.activeCourses())
                .totalEnquiries(enquiryService.totalEnquiries())
                .recentEnquiries(enquiryService.recentEnquiries())
                .emailSentCount(enquiryRepository.countByEmailStatus(EmailStatus.SENT))
                .emailFailedCount(enquiryRepository.countByEmailStatus(EmailStatus.FAILED))
                .build();
    }
}