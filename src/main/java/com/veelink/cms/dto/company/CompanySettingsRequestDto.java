package com.veelink.cms.dto.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanySettingsRequestDto {
    @NotBlank
    private String companyName;
    private String logoUrl;
    private String faviconUrl;
    private String tagline;
    private String shortDescription;
    private String longDescription;
    @Email
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
    @Email
    private String primaryEmail;
    @Email
    private String noreplyEmail;
    @Email
    private String enquiryNotificationEmail;
    @Email
    private String enquiryCcEmail;
    @Email
    private String enquiryBccEmail;
    private Boolean studentConfirmationEnabled;
    private String emailSenderName;
}