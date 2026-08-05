package com.veelink.cms.repository;

import com.veelink.cms.entity.HomeSection;
import com.veelink.cms.entity.enums.SectionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeSectionRepository extends JpaRepository<HomeSection, Long> {

    List<HomeSection> findBySectionTypeAndIsActiveTrueOrderByDisplayOrderAsc(SectionType sectionType);

    List<HomeSection> findAllByOrderBySectionTypeAscDisplayOrderAsc();
}