package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.course.CourseCategoryResponseDto;
import com.veelink.cms.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/course-categories")
@RequiredArgsConstructor
public class CourseCategoryAdminController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseCategoryResponseDto> getCategories() {
        return courseService.getCategories();
    }
}
