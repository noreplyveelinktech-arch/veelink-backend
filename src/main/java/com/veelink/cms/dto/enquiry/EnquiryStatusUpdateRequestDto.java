package com.veelink.cms.dto.enquiry;

import com.veelink.cms.entity.enums.EnquiryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnquiryStatusUpdateRequestDto {
    @NotNull
    private EnquiryStatus status;
}