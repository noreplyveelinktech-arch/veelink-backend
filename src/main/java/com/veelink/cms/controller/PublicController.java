package com.veelink.cms.controller;

import com.veelink.cms.dto.about.AboutContentResponseDto;
import com.veelink.cms.dto.common.MessageResponse;
import com.veelink.cms.dto.company.CompanySettingsResponseDto;
import com.veelink.cms.dto.company.ContactDetailsResponseDto;
import com.veelink.cms.dto.course.CourseResponseDto;
import com.veelink.cms.dto.enquiry.EnquiryRequestDto;
import com.veelink.cms.dto.home.HomeContentResponseDto;
import com.veelink.cms.dto.team.TeamMemberResponseDto;
import com.veelink.cms.entity.enums.TrainingMode;
import com.veelink.cms.service.AboutContentService;
import com.veelink.cms.service.CompanySettingsService;
import com.veelink.cms.service.CourseService;
import com.veelink.cms.service.EnquiryService;
import com.veelink.cms.service.HomeContentService;
import com.veelink.cms.service.TeamMemberService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final CompanySettingsService companySettingsService;
    private final HomeContentService homeContentService;
    private final AboutContentService aboutContentService;
    private final CourseService courseService;
    private final TeamMemberService teamMemberService;
    private final EnquiryService enquiryService;

    @GetMapping("/company-settings")
    public CompanySettingsResponseDto getCompanySettings() {
        return companySettingsService.getCompanySettings();
    }

    @GetMapping("/home-content")
    public HomeContentResponseDto getHomeContent() {
        return homeContentService.getPublicHomeContent();
    }

    @GetMapping("/about-content")
    public AboutContentResponseDto getAboutContent() {
        return aboutContentService.getPublicAboutContent();
    }

    @GetMapping("/courses")
    public List<CourseResponseDto> getCourses(@RequestParam(required = false) Long categoryId,
                                              @RequestParam(required = false) TrainingMode trainingMode,
                                              @RequestParam(required = false) String keyword) {
        return courseService.getPublicCourses(categoryId, trainingMode, keyword);
    }

    @GetMapping("/courses/{id}")
    public CourseResponseDto getCourse(@PathVariable Long id) {
        return courseService.getPublicCourse(id);
    }

    @GetMapping("/contact-details")
    public ContactDetailsResponseDto getContactDetails() {
        return companySettingsService.getContactDetails();
    }

    @GetMapping("/team-members")
    public List<TeamMemberResponseDto> getTeamMembers() {
        return teamMemberService.getPublicTeamMembers();
    }

    @PostMapping("/enquiries")
    public ResponseEntity<MessageResponse> createEnquiry(@Valid @RequestBody EnquiryRequestDto requestDto) {
        enquiryService.createEnquiry(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Thank you for contacting us. Our team will reach out to you shortly."));
    }
}