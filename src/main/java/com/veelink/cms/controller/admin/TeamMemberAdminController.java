package com.veelink.cms.controller.admin;

import com.veelink.cms.dto.common.MessageResponse;
import com.veelink.cms.dto.team.TeamMemberRequestDto;
import com.veelink.cms.dto.team.TeamMemberResponseDto;
import com.veelink.cms.service.TeamMemberService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/team-members")
@RequiredArgsConstructor
public class TeamMemberAdminController {

    private final TeamMemberService teamMemberService;

    @GetMapping
    public List<TeamMemberResponseDto> getTeamMembers() {
        return teamMemberService.getAllTeamMembers();
    }

    @PostMapping
    public ResponseEntity<TeamMemberResponseDto> createTeamMember(@Valid @RequestBody TeamMemberRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMemberService.create(requestDto));
    }

    @GetMapping("/{id}")
    public TeamMemberResponseDto getTeamMember(@PathVariable Long id) {
        return teamMemberService.getTeamMember(id);
    }

    @PutMapping("/{id}")
    public TeamMemberResponseDto updateTeamMember(@PathVariable Long id, @Valid @RequestBody TeamMemberRequestDto requestDto) {
        return teamMemberService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteTeamMember(@PathVariable Long id) {
        teamMemberService.delete(id);
        return new MessageResponse("Team member deleted successfully.");
    }
}