package com.veelink.cms.dto.enquiry;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnquiryRequestDto {
    @NotBlank
    private String fullName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^[0-9+\\-\\s]{7,20}$", message = "mobileNumber must contain 7 to 20 valid phone characters")
    private String mobileNumber;
    @Size(max = 2000)
    private String message;
    @NotNull
    private Long courseId;
}