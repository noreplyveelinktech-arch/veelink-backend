package com.veelink.cms.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Thin client for the Brevo (Sendinblue) transactional email HTTP API
 * (https://api.brevo.com/v3/smtp/email). Using the HTTPS API instead of raw SMTP avoids the
 * outbound-port blocking/throttling that many free-tier hosts (Render, Railway, etc.) apply to
 * SMTP ports 25/465/587, which is the most common cause of "emails not sending" in production.
 */
@Slf4j
@Component
public class BrevoEmailClient {

    private static final String BREVO_API_URL = "https://api.brevo.com/v3/smtp/email";

    private final RestTemplate restTemplate;

    @Value("${brevo.api-key:}")
    private String apiKey;

    public BrevoEmailClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void send(EmailRequest request) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "BREVO_API_KEY is not configured. Set it as a backend environment variable to enable email sending.");
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("sender", Map.of("email", request.getFromEmail(), "name", request.getFromName()));
        body.put("to", List.of(recipient(request.getTo())));

        if (request.getCc() != null && !request.getCc().isBlank()) {
            body.put("cc", List.of(recipient(request.getCc())));
        }
        if (request.getBcc() != null && !request.getBcc().isBlank()) {
            body.put("bcc", List.of(recipient(request.getBcc())));
        }

        body.put("subject", request.getSubject());
        body.put("textContent", request.getTextContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);
        headers.set("accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(BREVO_API_URL, entity, String.class);
        } catch (RestClientException ex) {
            throw new IllegalStateException("Brevo API request failed: " + ex.getMessage(), ex);
        }
    }

    private static Map<String, String> recipient(String email) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("email", email);
        return map;
    }

    @Getter
    @Builder
    public static class EmailRequest {
        private final String fromEmail;
        private final String fromName;
        private final String to;
        private final String cc;
        private final String bcc;
        private final String subject;
        private final String textContent;
    }
}
