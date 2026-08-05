package com.veelink.cms.repository;

import com.veelink.cms.entity.CourseCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Long> {

    Optional<CourseCategory> findByCategoryNameIgnoreCase(String categoryName);

    List<CourseCategory> findByIsActiveTrueOrderByCategoryNameAsc();
}