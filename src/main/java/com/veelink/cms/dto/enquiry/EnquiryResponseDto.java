package com.veelink.cms.dto.enquiry;

import com.veelink.cms.entity.enums.EmailStatus;
import com.veelink.cms.entity.enums.EnquiryStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EnquiryResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String mobileNumber;
    private Long courseId;
    private String interestedCourse;
    private String message;
    private EnquiryStatus status;
    private EmailStatus emailStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}