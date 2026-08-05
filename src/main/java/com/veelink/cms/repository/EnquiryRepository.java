package com.veelink.cms.repository;

import com.veelink.cms.entity.Enquiry;
import com.veelink.cms.entity.enums.EmailStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long>, JpaSpecificationExecutor<Enquiry> {

    long countByEmailStatus(EmailStatus emailStatus);

    List<Enquiry> findTop5ByOrderByCreatedAtDesc();

    List<Enquiry> findByCourseId(Long courseId);
}