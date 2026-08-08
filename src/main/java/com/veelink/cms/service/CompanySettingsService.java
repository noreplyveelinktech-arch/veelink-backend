package com.veelink.cms.service;

import com.veelink.cms.dto.company.CompanySettingsRequestDto;
import com.veelink.cms.dto.company.CompanySettingsResponseDto;
import com.veelink.cms.dto.company.ContactDetailsResponseDto;
import com.veelink.cms.entity.CompanySettings;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.repository.CompanySettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanySettingsService {

    private final CompanySettingsRepository companySettingsRepository;

    public CompanySettingsResponseDto getCompanySettings() {
        return toResponse(getSettingsEntity());
    }

    /**
     * Public, unauthenticated variant used by the marketing site. Deliberately omits the internal
     * email-routing fields (primaryEmail, noreplyEmail, enquiryNotificationEmail, CC/BCC) so those
     * addresses are never exposed to anonymous visitors calling /api/public/company-settings.
     */
    public CompanySettingsResponseDto getPublicCompanySettings() {
        CompanySettingsResponseDto response = toResponse(getSettingsEntity());
        response.setPrimaryEmail(null);
        response.setNoreplyEmail(null);
        response.setEnquiryNotificationEmail(null);
        response.setEnquiryCcEmail(null);
        response.setEnquiryBccEmail(null);
        return response;
    }

    public ContactDetailsResponseDto getContactDetails() {
        CompanySettings settings = getSettingsEntity();
        return ContactDetailsResponseDto.builder()
                .companyName(settings.getCompanyName())
                .email(settings.getEmail())
                .phoneNumber(settings.getPhoneNumber())
                .whatsappNumber(settings.getWhatsappNumber())
                .address(settings.getAddress())
                .googleMapsUrl(settings.getGoogleMapsUrl())
                .workingHours(settings.getWorkingHours())
                .facebookUrl(settings.getFacebookUrl())
                .instagramUrl(settings.getInstagramUrl())
                .linkedinUrl(settings.getLinkedinUrl())
                .youtubeUrl(settings.getYoutubeUrl())
                .twitterUrl(settings.getTwitterUrl())
                .build();
    }

    public CompanySettingsResponseDto update(CompanySettingsRequestDto requestDto) {
        CompanySettings settings = getSettingsEntity();
        applyRequest(settings, requestDto);
        return toResponse(companySettingsRepository.save(settings));
    }

    public CompanySettings getSettingsEntity() {
        return companySettingsRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Company settings not found"));
    }

    private void applyRequest(CompanySettings settings, CompanySettingsRequestDto requestDto) {
        settings.setCompanyName(requestDto.getCompanyName());
        settings.setLogoUrl(requestDto.getLogoUrl());
        settings.setFaviconUrl(requestDto.getFaviconUrl());
        settings.setTagline(requestDto.getTagline());
        settings.setShortDescription(requestDto.getShortDescription());
        settings.setLongDescription(requestDto.getLongDescription());
        settings.setEmail(requestDto.getEmail());
        settings.setPhoneNumber(requestDto.getPhoneNumber());
        settings.setWhatsappNumber(requestDto.getWhatsappNumber());
        settings.setAddress(requestDto.getAddress());
        settings.setGoogleMapsUrl(requestDto.getGoogleMapsUrl());
        settings.setWorkingHours(requestDto.getWorkingHours());
        settings.setFacebookUrl(requestDto.getFacebookUrl());
        settings.setInstagramUrl(requestDto.getInstagramUrl());
        settings.setLinkedinUrl(requestDto.getLinkedinUrl());
        settings.setYoutubeUrl(requestDto.getYoutubeUrl());
        settings.setTwitterUrl(requestDto.getTwitterUrl());
        settings.setPrimaryEmail(requestDto.getPrimaryEmail());
        settings.setNoreplyEmail(requestDto.getNoreplyEmail());
        settings.setEnquiryNotificationEmail(requestDto.getEnquiryNotificationEmail());
        settings.setEnquiryCcEmail(requestDto.getEnquiryCcEmail());
        settings.setEnquiryBccEmail(requestDto.getEnquiryBccEmail());
        settings.setStudentConfirmationEnabled(requestDto.getStudentConfirmationEnabled());
        settings.setEmailSenderName(requestDto.getEmailSenderName());
    }

    private CompanySettingsResponseDto toResponse(CompanySettings settings) {
        return CompanySettingsResponseDto.builder()
                .id(settings.getId())
                .companyName(settings.getCompanyName())
                .logoUrl(settings.getLogoUrl())
                .faviconUrl(settings.getFaviconUrl())
                .tagline(settings.getTagline())
                .shortDescription(settings.getShortDescription())
                .longDescription(settings.getLongDescription())
                .email(settings.getEmail())
                .phoneNumber(settings.getPhoneNumber())
                .whatsappNumber(settings.getWhatsappNumber())
                .address(settings.getAddress())
                .googleMapsUrl(settings.getGoogleMapsUrl())
                .workingHours(settings.getWorkingHours())
                .facebookUrl(settings.getFacebookUrl())
                .instagramUrl(settings.getInstagramUrl())
                .linkedinUrl(settings.getLinkedinUrl())
                .youtubeUrl(settings.getYoutubeUrl())
                .twitterUrl(settings.getTwitterUrl())
                .primaryEmail(settings.getPrimaryEmail())
                .noreplyEmail(settings.getNoreplyEmail())
                .enquiryNotificationEmail(settings.getEnquiryNotificationEmail())
                .enquiryCcEmail(settings.getEnquiryCcEmail())
                .enquiryBccEmail(settings.getEnquiryBccEmail())
                .studentConfirmationEnabled(settings.getStudentConfirmationEnabled())
                .emailSenderName(settings.getEmailSenderName())
                .createdAt(settings.getCreatedAt())
                .updatedAt(settings.getUpdatedAt())
                .build();
    }
}