package com.veelink.cms.service;

import com.veelink.cms.dto.team.TeamMemberRequestDto;
import com.veelink.cms.dto.team.TeamMemberResponseDto;
import com.veelink.cms.entity.TeamMember;
import com.veelink.cms.exception.ResourceNotFoundException;
import com.veelink.cms.repository.TeamMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    public List<TeamMemberResponseDto> getPublicTeamMembers() {
        return teamMemberRepository.findByIsActiveTrueOrderByDisplayOrderAsc().stream().map(this::toDto).toList();
    }

    public List<TeamMemberResponseDto> getAllTeamMembers() {
        return teamMemberRepository.findAllByOrderByDisplayOrderAsc().stream().map(this::toDto).toList();
    }

    public TeamMemberResponseDto getTeamMember(Long id) {
        return toDto(getEntity(id));
    }

    public TeamMemberResponseDto create(TeamMemberRequestDto requestDto) {
        TeamMember teamMember = new TeamMember();
        applyRequest(teamMember, requestDto);
        return toDto(teamMemberRepository.save(teamMember));
    }

    public TeamMemberResponseDto update(Long id, TeamMemberRequestDto requestDto) {
        TeamMember teamMember = getEntity(id);
        applyRequest(teamMember, requestDto);
        return toDto(teamMemberRepository.save(teamMember));
    }

    public void delete(Long id) {
        teamMemberRepository.delete(getEntity(id));
    }

    private TeamMember getEntity(Long id) {
        return teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team member not found with id: " + id));
    }

    private void applyRequest(TeamMember teamMember, TeamMemberRequestDto requestDto) {
        teamMember.setFullName(requestDto.getFullName());
        teamMember.setDesignation(requestDto.getDesignation());
        teamMember.setDescription(requestDto.getDescription());
        teamMember.setPhotoUrl(requestDto.getPhotoUrl());
        teamMember.setLinkedinUrl(requestDto.getLinkedinUrl());
        teamMember.setDisplayOrder(requestDto.getDisplayOrder());
        teamMember.setIsActive(requestDto.getIsActive());
    }

    private TeamMemberResponseDto toDto(TeamMember teamMember) {
        return TeamMemberResponseDto.builder()
                .id(teamMember.getId())
                .fullName(teamMember.getFullName())
                .designation(teamMember.getDesignation())
                .description(teamMember.getDescription())
                .photoUrl(teamMember.getPhotoUrl())
                .linkedinUrl(teamMember.getLinkedinUrl())
                .displayOrder(teamMember.getDisplayOrder())
                .isActive(teamMember.getIsActive())
                .createdAt(teamMember.getCreatedAt())
                .updatedAt(teamMember.getUpdatedAt())
                .build();
    }
}