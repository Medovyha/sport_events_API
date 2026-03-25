package com.sport_events.api.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.dto.command.CreatePlayerCommand;
import com.sport_events.api.application.dto.command.UpdatePlayerCommand;
import com.sport_events.api.application.dto.query.GetPlayerQuery;
import com.sport_events.api.application.dto.query.GetPlayersQuery;
import com.sport_events.api.application.usecase.CreatePlayerUseCase;
import com.sport_events.api.application.usecase.GetPlayerUseCase;
import com.sport_events.api.application.usecase.UpdatePlayerUseCase;
import com.sport_events.api.presentation.dto.CreatePlayerRequest;
import com.sport_events.api.presentation.dto.PlayerResponse;
import com.sport_events.api.presentation.dto.UpdatePlayerRequest;
import com.sport_events.api.presentation.mapper.PlayerResponseMapper;
import com.sport_events.api.presentation.util.LanguageUtils;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final GetPlayerUseCase getPlayerUseCase;
    private final CreatePlayerUseCase createPlayerUseCase;
    private final UpdatePlayerUseCase updatePlayerUseCase;

    public PlayerController(
            GetPlayerUseCase getPlayerUseCase,
            CreatePlayerUseCase createPlayerUseCase,
            UpdatePlayerUseCase updatePlayerUseCase) {
        this.getPlayerUseCase = getPlayerUseCase;
        this.createPlayerUseCase = createPlayerUseCase;
        this.updatePlayerUseCase = updatePlayerUseCase;
    }

    @GetMapping
    public ResponseEntity<List<PlayerResponse>> getAllPlayers(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        List<PlayerResponse> players = getPlayerUseCase.execute(new GetPlayersQuery(lang)).stream()
                .map(PlayerResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayer(
            @PathVariable Integer id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        String lang = LanguageUtils.normalizeLanguage(acceptLanguage);
        return ResponseEntity.ok(PlayerResponseMapper.toResponse(getPlayerUseCase.execute(new GetPlayerQuery(id, lang))));
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(
            @Validated @RequestBody CreatePlayerRequest request) {
        CreatePlayerCommand command = new CreatePlayerCommand(
                request.firstName(), request.lastName(), request.dateOfBirth());
        PlayerResponse response = PlayerResponseMapper.toResponse(createPlayerUseCase.execute(command));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponse> updatePlayer(
            @PathVariable Integer id,
            @Validated @RequestBody UpdatePlayerRequest request) {
        UpdatePlayerCommand command = new UpdatePlayerCommand(
                id, request.firstName(), request.lastName(), request.dateOfBirth());
        return ResponseEntity.ok(PlayerResponseMapper.toResponse(updatePlayerUseCase.execute(command)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Integer id) {
        updatePlayerUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
