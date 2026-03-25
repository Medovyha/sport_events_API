package com.sport_events.api.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.usecase.GetPlayerUseCase;
import com.sport_events.api.presentation.dto.PlayerResponse;
import com.sport_events.api.presentation.mapper.PlayerResponseMapper;
import com.sport_events.api.presentation.util.LanguageUtils;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final GetPlayerUseCase getPlayerUseCase;

    public PlayerController(GetPlayerUseCase getPlayerUseCase) {
        this.getPlayerUseCase = getPlayerUseCase;
    }

    @GetMapping
    public ResponseEntity<List<PlayerResponse>> getAllPlayers(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        List<PlayerResponse> players = getPlayerUseCase.findAll(lang).stream()
                .map(PlayerResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayer(
            @PathVariable Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        return ResponseEntity.ok(PlayerResponseMapper.toResponse(getPlayerUseCase.findById(id, lang)));
    }
}
