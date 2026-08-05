package com.veelink.cms.entity;

import com.veelink.cms.entity.enums.CourseStatus;
import com.veelink.cms.entity.enums.TrainingMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", nullable = false, length = 200)
    private String courseName;

    @Column(name = "course_department", length = 150)
    private String courseDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_category_id")
    private CourseCategory courseCategory;

    @Column(name = "course_image", length = 500)
    private String courseImage;

    @Column(name = "course_description", columnDefinition = "TEXT")
    private String courseDescription;

    @Column(length = 100)
    private String duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_mode", nullable = false, length = 20)
    private TrainingMode trainingMode = TrainingMode.HYBRID;

    @Column(precision = 10, scale = 2)
    private BigDecimal fee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CourseStatus status = CourseStatus.ACTIVE;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;
}