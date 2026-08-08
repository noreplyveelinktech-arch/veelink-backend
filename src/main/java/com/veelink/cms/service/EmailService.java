package com.veelink.cms.service;

import com.veelink.cms.entity.CompanySettings;
import com.veelink.cms.entity.Enquiry;
import com.veelink.cms.entity.enums.EmailStatus;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.repository.EnquiryRepository;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Sends transactional emails via the Brevo (formerly Sendinblue) HTTP API instead of raw SMTP.
 * SMTP is frequently blocked or rate-limited on free-tier hosting providers (e.g. Render, Railway),
 * which caused enquiry notification emails to silently fail. The Brevo REST API works over
 * standard HTTPS (port 443) and only needs a single API key, making delivery far more reliable.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    private final BrevoEmailClient brevoEmailClient;
    private final CompanySettingsService companySettingsService;
    private final EnquiryRepository enquiryRepository;

    @Value("${mail.from:no-reply@example.com}")
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
            brevoEmailClient.send(BrevoEmailClient.EmailRequest.builder()
                    .fromEmail(effectiveSenderEmail(settings))
                    .fromName(effectiveSenderName(settings))
                    .to(recipient)
                    .cc(hasText(settings.getEnquiryCcEmail()) ? settings.getEnquiryCcEmail() : null)
                    .bcc(hasText(settings.getEnquiryBccEmail()) ? settings.getEnquiryBccEmail() : null)
                    .subject("New Enquiry Received - " + settings.getCompanyName())
                    .textContent(buildCompanyNotificationBody(enquiry, settings))
                    .build());
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
            brevoEmailClient.send(BrevoEmailClient.EmailRequest.builder()
                    .fromEmail(effectiveSenderEmail(settings))
                    .fromName(effectiveSenderName(settings))
                    .to(enquiry.getEmail())
                    .subject("Thank you for contacting " + settings.getCompanyName())
                    .textContent(buildStudentConfirmationBody(enquiry, settings))
                    .build());
        } catch (Exception ex) {
            log.error("Failed to send student confirmation email for enquiry {}", enquiry.getId(), ex);
        }
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