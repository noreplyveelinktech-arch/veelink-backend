package com.veelink.cms.service;

import com.veelink.cms.entity.CompanySettings;
import com.veelink.cms.entity.Enquiry;
import com.veelink.cms.entity.enums.EmailStatus;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.repository.EnquiryRepository;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    // Initialize RestClient for Brevo HTTP API (Port 443 - Not blocked by Render)
    private final RestClient restClient = RestClient.create("https://api.brevo.com/v3");
    
    private final CompanySettingsService companySettingsService;
    private final EnquiryRepository enquiryRepository;

    @Value("${brevo.api.key:}")
    private String brevoApiKey;

    @Value("${mail.from:noreply@veelinktech.com}")
    private String mailFrom;

    @Value("${mail.from-name:Veelink Technologies}")
    private String defaultFromName;

    @Async("emailTaskExecutor")
    public void sendEnquiryEmailsAsync(Long enquiryId) {
        enquiryRepository.findById(enquiryId).ifPresent(enquiry -> {
            sendCompanyNotificationInternal(enquiry);
            sendStudentConfirmationInternal(enquiry);
        });
    }

    @Transactional
    public Enquiry resendCompanyNotification(Long enquiryId) {
        Enquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() -> new ResourceNotFoundException("Enquiry not found with id: " + enquiryId));
        sendCompanyNotificationInternal(enquiry);
        return enquiryRepository.findById(enquiryId)
                .orElseThrow(() -> new ResourceNotFoundException("Enquiry not found with id: " + enquiryId));
    }

    private void sendCompanyNotificationInternal(Enquiry enquiry) {
        try {
            CompanySettings settings = companySettingsService.getSettingsEntity();
            String recipient = effectivePrimaryEmail(settings);
            
            if (!hasText(recipient)) {
                log.warn("Skipped company notification for enquiry {}: no primary/notification email configured", enquiry.getId());
                enquiry.setEmailStatus(EmailStatus.FAILED);
                enquiryRepository.save(enquiry);
                return;
            }

            Map<String, Object> payload = new HashMap<>();
            payload.put("sender", Map.of("email", effectiveSenderEmail(settings), "name", effectiveSenderName(settings)));
            payload.put("to", List.of(Map.of("email", recipient)));
            
            if (hasText(settings.getEnquiryCcEmail())) {
                payload.put("cc", List.of(Map.of("email", settings.getEnquiryCcEmail())));
            }
            if (hasText(settings.getEnquiryBccEmail())) {
                payload.put("bcc", List.of(Map.of("email", settings.getEnquiryBccEmail())));
            }
            
            payload.put("subject", "New Enquiry Received - " + settings.getCompanyName());
            payload.put("textContent", buildCompanyNotificationBody(enquiry, settings));

            sendViaBrevoApi(payload);

            enquiry.setEmailStatus(EmailStatus.SENT);
            enquiryRepository.save(enquiry);
        } catch (Exception ex) {
            log.error("Failed to send company notification email for enquiry {}", enquiry.getId(), ex);
            enquiry.setEmailStatus(EmailStatus.FAILED);
            enquiryRepository.save(enquiry);
        }
    }

    private void sendStudentConfirmationInternal(Enquiry enquiry) {
        try {
            CompanySettings settings = companySettingsService.getSettingsEntity();
            if (!Boolean.TRUE.equals(settings.getStudentConfirmationEnabled())) {
                return;
            }

            Map<String, Object> payload = new HashMap<>();
            payload.put("sender", Map.of("email", effectiveSenderEmail(settings), "name", effectiveSenderName(settings)));
            payload.put("to", List.of(Map.of("email", enquiry.getEmail(), "name", enquiry.getFullName())));
            payload.put("subject", "Thank you for contacting " + settings.getCompanyName());
            payload.put("textContent", buildStudentConfirmationBody(enquiry, settings));

            sendViaBrevoApi(payload);

        } catch (Exception ex) {
            log.error("Failed to send student confirmation email for enquiry {}", enquiry.getId(), ex);
        }
    }

    private void sendViaBrevoApi(Map<String, Object> payload) {
        if (!hasText(brevoApiKey)) {
            throw new IllegalStateException("Brevo API key is not configured.");
        }

        restClient.post()
                .uri("/smtp/email")
                .header("api-key", brevoApiKey)
                .header("accept", MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .toBodilessEntity();
    }

    /** primaryEmail (admin-configurable) wins; falls back to the legacy enquiryNotificationEmail field. */
    private String effectivePrimaryEmail(CompanySettings settings) {
        return hasText(settings.getPrimaryEmail()) ? settings.getPrimaryEmail() : settings.getEnquiryNotificationEmail();
    }

    /** noreplyEmail (admin-configurable) wins; falls back to the mail.from application property. */
    private String effectiveSenderEmail(CompanySettings settings) {
        return hasText(settings.getNoreplyEmail()) ? settings.getNoreplyEmail() : mailFrom;
    }

    private String effectiveSenderName(CompanySettings settings) {
        return defaultString(settings.getEmailSenderName(), defaultString(settings.getCompanyName(), defaultFromName));
    }

    private String buildCompanyNotificationBody(Enquiry enquiry, CompanySettings settings) {
        return "Hello Team,\n\n"
                + "A new course enquiry has been submitted through the website.\n\n"
                + "Student Details:\n"
                + "Name: " + enquiry.getFullName() + "\n"
                + "Email: " + enquiry.getEmail() + "\n"
                + "Mobile: " + enquiry.getMobileNumber() + "\n"
                + "Interested Course: " + enquiry.getInterestedCourse() + "\n"
                + "Message: " + defaultString(enquiry.getMessage(), "N/A") + "\n"
                + "Submitted At: " + enquiry.getCreatedAt().format(FORMATTER) + "\n\n"
                + "Please contact the student at the earliest.\n\n"
                + "Regards,\n"
                + settings.getCompanyName() + "\n"
                + "Website Enquiry System";
    }

    private String buildStudentConfirmationBody(Enquiry enquiry, CompanySettings settings) {
        return "Dear " + enquiry.getFullName() + ",\n\n"
                + "Thank you for contacting " + settings.getCompanyName() + ". We have received your enquiry and our team will contact you shortly.\n\n"
                + "Submitted Details:\n"
                + "Name: " + enquiry.getFullName() + "\n"
                + "Email: " + enquiry.getEmail() + "\n"
                + "Mobile: " + enquiry.getMobileNumber() + "\n"
                + "Interested Course: " + enquiry.getInterestedCourse() + "\n"
                + "Message: " + defaultString(enquiry.getMessage(), "N/A") + "\n\n"
                + "Regards,\n"
                + settings.getCompanyName();
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private String defaultString(String value, String fallback) {
        return hasText(value) ? value : fallback;
    }
}