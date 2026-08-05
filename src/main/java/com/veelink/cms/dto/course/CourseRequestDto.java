package com.veelink.cms.dto.course;

import com.veelink.cms.entity.enums.CourseStatus;
import com.veelink.cms.entity.enums.TrainingMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequestDto {
    @NotBlank
    private String courseName;
    private String courseDepartment;
    private Long courseCategoryId;
    private String courseImage;
    private String courseDescription;
    private String duration;
    @NotNull
    private TrainingMode trainingMode;
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal fee;
    @NotNull
    private CourseStatus status;
    @NotNull
    private Integer displayOrder;
}