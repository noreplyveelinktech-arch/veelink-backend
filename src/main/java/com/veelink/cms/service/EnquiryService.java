package com.veelink.cms.service;

import com.veelink.cms.dto.enquiry.EnquiryRequestDto;
import com.veelink.cms.dto.enquiry.EnquiryResponseDto;
import com.veelink.cms.entity.Course;
import com.veelink.cms.entity.Enquiry;
import com.veelink.cms.entity.enums.EnquiryStatus;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.repository.EnquiryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnquiryService {

    private final EnquiryRepository enquiryRepository;
    private final CourseService courseService;
    private final EmailService emailService;

    public EnquiryResponseDto createEnquiry(EnquiryRequestDto requestDto) {
        Course course = courseService.getActiveCourseEntity(requestDto.getCourseId());
        Enquiry enquiry = new Enquiry();
        enquiry.setFullName(requestDto.getFullName());
        enquiry.setEmail(requestDto.getEmail());
        enquiry.setMobileNumber(requestDto.getMobileNumber());
        enquiry.setCourse(course);
        enquiry.setInterestedCourse(course.getCourseName());
        enquiry.setMessage(requestDto.getMessage());
        Enquiry savedEnquiry = enquiryRepository.save(enquiry);
        emailService.sendEnquiryEmailsAsync(savedEnquiry.getId());
        return toDto(savedEnquiry);
    }

    public List<EnquiryResponseDto> getEnquiries(EnquiryStatus status, Long courseId) {
        Specification<Enquiry> spec = Specification.where(null);
        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }
        if (courseId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("course").get("id"), courseId));
        }
        return enquiryRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(this::toDto).toList();
    }

    public EnquiryResponseDto getEnquiry(Long id) {
        return toDto(getEntity(id));
    }

    public EnquiryResponseDto updateStatus(Long id, EnquiryStatus status) {
        Enquiry enquiry = getEntity(id);
        enquiry.setStatus(status);
        return toDto(enquiryRepository.save(enquiry));
    }

    public void delete(Long id) {
        enquiryRepository.delete(getEntity(id));
    }

    public EnquiryResponseDto resendCompanyNotification(Long id) {
        return toDto(emailService.resendCompanyNotification(id));
    }

    public long totalEnquiries() {
        return enquiryRepository.count();
    }

    public List<EnquiryResponseDto> recentEnquiries() {
        return enquiryRepository.findTop5ByOrderByCreatedAtDesc().stream().map(this::toDto).toList();
    }

    private Enquiry getEntity(Long id) {
        return enquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enquiry not found with id: " + id));
    }

    private EnquiryResponseDto toDto(Enquiry enquiry) {
        return EnquiryResponseDto.builder()
                .id(enquiry.getId())
                .fullName(enquiry.getFullName())
                .email(enquiry.getEmail())
                .mobileNumber(enquiry.getMobileNumber())
                .courseId(enquiry.getCourse() != null ? enquiry.getCourse().getId() : null)
                .interestedCourse(enquiry.getInterestedCourse())
                .message(enquiry.getMessage())
                .status(enquiry.getStatus())
                .emailStatus(enquiry.getEmailStatus())
                .createdAt(enquiry.getCreatedAt())
                .updatedAt(enquiry.getUpdatedAt())
                .build();
    }
}