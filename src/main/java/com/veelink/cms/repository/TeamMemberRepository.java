package com.veelink.cms.repository;

import com.veelink.cms.entity.TeamMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByIsActiveTrueOrderByDisplayOrderAsc();

    List<TeamMember> findAllByOrderByDisplayOrderAsc();
}