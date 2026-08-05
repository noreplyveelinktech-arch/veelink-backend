package com.veelink.cms.dto.dashboard;

import com.veelink.cms.dto.enquiry.EnquiryResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DashboardStatsResponseDto {
    private long totalCourses;
    private long activeCourses;
    private long totalEnquiries;
    private long emailSentCount;
    private long emailFailedCount;
    private List<EnquiryResponseDto> recentEnquiries;
}