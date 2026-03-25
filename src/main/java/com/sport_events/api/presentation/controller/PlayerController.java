package com.sport_events.api.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.dto.command.CreatePlayerCommand;
import com.sport_events.api.application.usecase.CreatePlayerUseCase;
import com.sport_events.api.application.usecase.GetPlayerUseCase;
import com.sport_events.api.presentation.dto.CreatePlayerRequest;
import com.sport_events.api.presentation.dto.PlayerResponse;
import com.sport_events.api.presentation.mapper.PlayerResponseMapper;
import com.sport_events.api.presentation.util.LanguageUtils;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final GetPlayerUseCase getPlayerUseCase;
    private final CreatePlayerUseCase createPlayerUseCase;

    public PlayerController(GetPlayerUseCase getPlayerUseCase, CreatePlayerUseCase createPlayerUseCase) {
        this.getPlayerUseCase = getPlayerUseCase;
        this.createPlayerUseCase = createPlayerUseCase;
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

    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(
            @Validated @RequestBody CreatePlayerRequest request) {
        CreatePlayerCommand command = new CreatePlayerCommand(
                request.firstName(), request.lastName(), request.dateOfBirth());
        PlayerResponse response = PlayerResponseMapper.toResponse(createPlayerUseCase.execute(command));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
