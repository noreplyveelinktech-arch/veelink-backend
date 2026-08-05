package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.common.MessageResponse;
import com.veelink.cms.dto.course.CourseRequestDto;
import com.veelink.cms.dto.course.CourseResponseDto;
import com.veelink.cms.dto.course.CourseStatusUpdateRequestDto;
import com.veelink.cms.service.CourseService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/courses")
@RequiredArgsConstructor
public class CourseAdminController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseResponseDto> getCourses() {
        return courseService.getAdminCourses();
    }

    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(requestDto));
    }

    @GetMapping("/{id}")
    public CourseResponseDto getCourse(@PathVariable Long id) {
        return courseService.getAdminCourse(id);
    }

    @PutMapping("/{id}")
    public CourseResponseDto updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequestDto requestDto) {
        return courseService.update(id, requestDto);
    }

    @PatchMapping("/{id}/status")
    public CourseResponseDto updateStatus(@PathVariable Long id, @Valid @RequestBody CourseStatusUpdateRequestDto requestDto) {
        return courseService.updateStatus(id, requestDto.getStatus());
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
        return new MessageResponse("Course deleted successfully.");
    }
}