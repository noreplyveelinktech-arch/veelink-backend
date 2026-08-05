package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.common.MessageResponse;
import com.veelink.cms.dto.enquiry.EnquiryResponseDto;
import com.veelink.cms.dto.enquiry.EnquiryStatusUpdateRequestDto;
import com.veelink.cms.entity.enums.EnquiryStatus;
import com.veelink.cms.service.EnquiryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/enquiries")
@RequiredArgsConstructor
public class EnquiryAdminController {

    private final EnquiryService enquiryService;

    @GetMapping
    public List<EnquiryResponseDto> getEnquiries(@RequestParam(required = false) EnquiryStatus status,
                                                 @RequestParam(required = false) Long courseId) {
        return enquiryService.getEnquiries(status, courseId);
    }

    @GetMapping("/{id}")
    public EnquiryResponseDto getEnquiry(@PathVariable Long id) {
        return enquiryService.getEnquiry(id);
    }

    @PatchMapping("/{id}/status")
    public EnquiryResponseDto updateStatus(@PathVariable Long id,
                                           @Valid @RequestBody EnquiryStatusUpdateRequestDto requestDto) {
        return enquiryService.updateStatus(id, requestDto.getStatus());
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteEnquiry(@PathVariable Long id) {
        enquiryService.delete(id);
        return new MessageResponse("Enquiry deleted successfully.");
    }

    @PostMapping("/{id}/resend-email")
    public EnquiryResponseDto resendEmail(@PathVariable Long id) {
        return enquiryService.resendCompanyNotification(id);
    }
}