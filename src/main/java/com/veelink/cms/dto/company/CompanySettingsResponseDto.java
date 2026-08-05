package com.veelink.cms.dto.company;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanySettingsResponseDto {
    private Long id;
    private String companyName;
    private String logoUrl;
    private String faviconUrl;
    private String tagline;
    private String shortDescription;
    private String longDescription;
    private String email;
    private String phoneNumber;
    private String whatsappNumber;
    private String address;
    private String googleMapsUrl;
    private String workingHours;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String youtubeUrl;
    private String twitterUrl;
    private String primaryEmail;
    private String noreplyEmail;
    private String enquiryNotificationEmail;
    private String enquiryCcEmail;
    private String enquiryBccEmail;
    private Boolean studentConfirmationEnabled;
    private String emailSenderName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}