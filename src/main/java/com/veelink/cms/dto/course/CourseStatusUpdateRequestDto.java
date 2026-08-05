package com.veelink.cms.dto.course;

import com.veelink.cms.entity.enums.CourseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseStatusUpdateRequestDto {
    @NotNull
    private CourseStatus status;
}