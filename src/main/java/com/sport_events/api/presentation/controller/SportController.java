package com.sport_events.api.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.usecase.GetSportUseCase;
import com.sport_events.api.application.usecase.GetTeamUseCase;
import com.sport_events.api.presentation.dto.SportResponse;
import com.sport_events.api.presentation.dto.TeamResponse;
import com.sport_events.api.presentation.mapper.SportResponseMapper;
import com.sport_events.api.presentation.mapper.TeamResponseMapper;
import com.sport_events.api.presentation.util.LanguageUtils;

@RestController
@RequestMapping("/sports")
public class SportController {

    private final GetSportUseCase getSportUseCase;
    private final GetTeamUseCase getTeamUseCase;

    public SportController(GetSportUseCase getSportUseCase, GetTeamUseCase getTeamUseCase) {
        this.getSportUseCase = getSportUseCase;
        this.getTeamUseCase = getTeamUseCase;
    }

    @GetMapping
    public ResponseEntity<List<SportResponse>> getAllSports(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        List<SportResponse> sports = getSportUseCase.findAll(lang).stream()
                .map(SportResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(sports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportResponse> getSport(
            @PathVariable Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        return ResponseEntity.ok(SportResponseMapper.toResponse(getSportUseCase.findById(id, lang)));
    }

    @GetMapping("/{id}/teams")
    public ResponseEntity<List<TeamResponse>> getTeamsBySport(
            @PathVariable Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        List<TeamResponse> teams = getTeamUseCase.findBySportId(id, lang).stream()
                .map(TeamResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(teams);
    }

}
