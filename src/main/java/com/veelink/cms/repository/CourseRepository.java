package com.veelink.cms.repository;

import com.veelink.cms.entity.Course;
import com.veelink.cms.entity.enums.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    long countByStatus(CourseStatus status);
}