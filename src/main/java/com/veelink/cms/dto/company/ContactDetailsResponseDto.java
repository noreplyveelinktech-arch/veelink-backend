package com.veelink.cms.dto.company;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContactDetailsResponseDto {
    private String companyName;
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
}