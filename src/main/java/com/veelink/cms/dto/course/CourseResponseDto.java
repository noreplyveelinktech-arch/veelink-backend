package com.veelink.cms.dto.course;

import com.veelink.cms.entity.enums.CourseStatus;
import com.veelink.cms.entity.enums.TrainingMode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CourseResponseDto {
    private Long id;
    private String courseName;
    private String courseDepartment;
    private Long courseCategoryId;
    private CourseCategoryResponseDto courseCategory;
    private String courseImage;
    private String courseDescription;
    private String duration;
    private TrainingMode trainingMode;
    private BigDecimal fee;
    private CourseStatus status;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}