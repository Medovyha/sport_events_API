package com.sport_events.api.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.usecase.GetPlayerUseCase;
import com.sport_events.api.application.usecase.GetTeamUseCase;
import com.sport_events.api.presentation.dto.PlayerResponse;
import com.sport_events.api.presentation.dto.TeamResponse;
import com.sport_events.api.presentation.mapper.PlayerResponseMapper;
import com.sport_events.api.presentation.mapper.TeamResponseMapper;
import com.sport_events.api.presentation.util.LanguageUtils;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final GetTeamUseCase getTeamUseCase;
    private final GetPlayerUseCase getPlayerUseCase;

    public TeamController(GetTeamUseCase getTeamUseCase, GetPlayerUseCase getPlayerUseCase) {
        this.getTeamUseCase = getTeamUseCase;
        this.getPlayerUseCase = getPlayerUseCase;
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getAllTeams(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        List<TeamResponse> teams = getTeamUseCase.findAll(lang).stream()
                .map(TeamResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeam(
            @PathVariable Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        return ResponseEntity.ok(TeamResponseMapper.toResponse(getTeamUseCase.findById(id, lang)));
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<List<PlayerResponse>> getPlayersByTeam(
            @PathVariable Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        List<PlayerResponse> players = getPlayerUseCase.findByTeamId(id, lang).stream()
                .map(PlayerResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(players);
    }

}
