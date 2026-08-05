package com.veelink.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "company_settings")
public class CompanySettings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "favicon_url", length = 500)
    private String faviconUrl;

    @Column(length = 255)
    private String tagline;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "long_description", columnDefinition = "TEXT")
    private String longDescription;

    @Column(length = 150)
    private String email;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(name = "whatsapp_number", length = 30)
    private String whatsappNumber;

    @Column(length = 500)
    private String address;

    @Column(name = "google_maps_url", length = 500)
    private String googleMapsUrl;

    @Column(name = "working_hours", length = 255)
    private String workingHours;

    @Column(name = "facebook_url", length = 300)
    private String facebookUrl;

    @Column(name = "instagram_url", length = 300)
    private String instagramUrl;

    @Column(name = "linkedin_url", length = 300)
    private String linkedinUrl;

    @Column(name = "youtube_url", length = 300)
    private String youtubeUrl;

    @Column(name = "twitter_url", length = 300)
    private String twitterUrl;

    // Primary contact email that receives every enquiry notification. Configurable
    // from the admin panel; falls back to enquiryNotificationEmail if left blank.
    @Column(name = "primary_email", length = 150)
    private String primaryEmail;

    // Optional override for the "From" address used on outgoing enquiry emails.
    // Falls back to the mail.from application property (MAIL_FROM env var) if blank.
    @Column(name = "noreply_email", length = 150)
    private String noreplyEmail;

    @Column(name = "enquiry_notification_email", length = 150)
    private String enquiryNotificationEmail;

    @Column(name = "enquiry_cc_email", length = 150)
    private String enquiryCcEmail;

    @Column(name = "enquiry_bcc_email", length = 150)
    private String enquiryBccEmail;

    @Column(name = "student_confirmation_enabled", nullable = false)
    private Boolean studentConfirmationEnabled = Boolean.TRUE;

    @Column(name = "email_sender_name", length = 150)
    private String emailSenderName;
}