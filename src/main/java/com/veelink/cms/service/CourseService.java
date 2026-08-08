package com.veelink.cms.service;

import com.veelink.cms.dto.course.CourseCategoryResponseDto;
import com.veelink.cms.dto.course.CourseRequestDto;
import com.veelink.cms.dto.course.CourseResponseDto;
import com.veelink.cms.entity.Course;
import com.veelink.cms.entity.CourseCategory;
import com.veelink.cms.entity.Enquiry;
import com.veelink.cms.entity.enums.CourseStatus;
import com.veelink.cms.entity.enums.TrainingMode;
import com.veelink.cms.exception.BadRequestException;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.repository.CourseCategoryRepository;
import com.veelink.cms.repository.CourseRepository;
import com.veelink.cms.repository.EnquiryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final EnquiryRepository enquiryRepository;

    public List<CourseResponseDto> getPublicCourses(Long categoryId, TrainingMode trainingMode, String keyword) {
        Specification<Course> spec = (root, query, cb) -> cb.equal(root.get("status"), CourseStatus.ACTIVE);
        if (categoryId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("courseCategory").get("id"), categoryId));
        }
        if (trainingMode != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("trainingMode"), trainingMode));
        }
        if (keyword != null && !keyword.isBlank()) {
            String likeValue = "%" + keyword.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("courseName")), likeValue),
                    cb.like(cb.lower(root.get("courseDescription")), likeValue),
                    cb.like(cb.lower(root.get("courseDepartment")), likeValue)
            ));
        }
        return courseRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "displayOrder")).stream().map(this::toDto).toList();
    }

    public CourseResponseDto getPublicCourse(Long id) {
        Course course = getEntity(id);
        if (course.getStatus() != CourseStatus.ACTIVE) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        return toDto(course);
    }

    public List<CourseResponseDto> getAdminCourses() {
        return courseRepository.findAll(Sort.by(Sort.Direction.ASC, "displayOrder")).stream().map(this::toDto).toList();
    }

    public CourseResponseDto getAdminCourse(Long id) {
        return toDto(getEntity(id));
    }

    @Transactional
    public CourseResponseDto create(CourseRequestDto requestDto) {
        Course course = new Course();
        applyRequest(course, requestDto);
        return toDto(courseRepository.save(course));
    }

    @Transactional
    public CourseResponseDto update(Long id, CourseRequestDto requestDto) {
        Course course = getEntity(id);
        applyRequest(course, requestDto);
        return toDto(courseRepository.save(course));
    }

    @Transactional
    public CourseResponseDto updateStatus(Long id, CourseStatus status) {
        Course course = getEntity(id);
        course.setStatus(status);
        return toDto(courseRepository.save(course));
    }

    @Transactional
    public void delete(Long id) {
        Course course = getEntity(id);
        List<Enquiry> enquiries = enquiryRepository.findByCourseId(id);
        if (!enquiries.isEmpty()) {
            enquiries.forEach(enquiry -> enquiry.setCourse(null));
            enquiryRepository.saveAll(enquiries);
        }
        courseRepository.delete(course);
    }

    public Course getActiveCourseEntity(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Selected course was not found"));
        if (course.getStatus() != CourseStatus.ACTIVE) {
            throw new BadRequestException("Selected course is inactive");
        }
        return course;
    }

    public long totalCourses() {
        return courseRepository.count();
    }

    public List<CourseCategoryResponseDto> getCategories() {
        return courseCategoryRepository.findByIsActiveTrueOrderByCategoryNameAsc().stream()
                .map(category -> CourseCategoryResponseDto.builder()
                        .id(category.getId())
                        .categoryName(category.getCategoryName())
                        .description(category.getDescription())
                        .isActive(category.getIsActive())
                        .build())
                .toList();
    }

    public long activeCourses() {
        return courseRepository.countByStatus(CourseStatus.ACTIVE);
    }

    private Course getEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    private void applyRequest(Course course, CourseRequestDto requestDto) {
        course.setCourseName(requestDto.getCourseName());
        course.setCourseDepartment(requestDto.getCourseDepartment());
        course.setCourseCategory(resolveCategory(requestDto.getCourseCategoryId()));
        course.setCourseImage(requestDto.getCourseImage());
        course.setCourseDescription(requestDto.getCourseDescription());
        course.setDuration(requestDto.getDuration());
        course.setTrainingMode(requestDto.getTrainingMode());
        course.setFee(requestDto.getFee());
        course.setStatus(requestDto.getStatus());
        course.setDisplayOrder(requestDto.getDisplayOrder());
    }

    private CourseCategory resolveCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return courseCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("Course category not found with id: " + categoryId));
    }

    private CourseResponseDto toDto(Course course) {
        CourseCategory category = course.getCourseCategory();
        return CourseResponseDto.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .courseDepartment(course.getCourseDepartment())
                .courseCategoryId(category != null ? category.getId() : null)
                .courseCategory(category != null ? CourseCategoryResponseDto.builder()
                        .id(category.getId())
                        .categoryName(category.getCategoryName())
                        .description(category.getDescription())
                        .isActive(category.getIsActive())
                        .build() : null)
                .courseImage(course.getCourseImage())
                .courseDescription(course.getCourseDescription())
                .duration(course.getDuration())
                .trainingMode(course.getTrainingMode())
                .fee(course.getFee())
                .status(course.getStatus())
                .displayOrder(course.getDisplayOrder())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}