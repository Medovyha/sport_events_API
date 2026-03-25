package com.sport_events.api.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.dto.command.CreateSportCommand;
import com.sport_events.api.application.dto.command.SportTranslationCommand;
import com.sport_events.api.application.dto.command.UpdateSportCommand;
import com.sport_events.api.application.dto.command.UpsertSportTranslationCommand;
import com.sport_events.api.application.usecase.CreateSportUseCase;
import com.sport_events.api.application.usecase.GetSportUseCase;
import com.sport_events.api.application.usecase.GetTeamUseCase;
import com.sport_events.api.application.usecase.UpdateSportUseCase;
import com.sport_events.api.application.usecase.UpsertSportTranslationUseCase;
import com.sport_events.api.presentation.dto.CreateSportRequest;
import com.sport_events.api.presentation.dto.SportResponse;
import com.sport_events.api.presentation.dto.SportTranslationRequest;
import com.sport_events.api.presentation.dto.TeamResponse;
import com.sport_events.api.presentation.dto.UpdateSportRequest;
import com.sport_events.api.presentation.dto.UpsertSportTranslationRequest;
import com.sport_events.api.presentation.mapper.SportResponseMapper;
import com.sport_events.api.presentation.mapper.TeamResponseMapper;
import com.sport_events.api.presentation.util.LanguageUtils;

@RestController
@RequestMapping("/sports")
public class SportController {

    private final GetSportUseCase getSportUseCase;
    private final GetTeamUseCase getTeamUseCase;
    private final CreateSportUseCase createSportUseCase;
    private final UpdateSportUseCase updateSportUseCase;
    private final UpsertSportTranslationUseCase upsertSportTranslationUseCase;

    public SportController(
            GetSportUseCase getSportUseCase,
            GetTeamUseCase getTeamUseCase,
            CreateSportUseCase createSportUseCase,
            UpdateSportUseCase updateSportUseCase,
            UpsertSportTranslationUseCase upsertSportTranslationUseCase) {
        this.getSportUseCase = getSportUseCase;
        this.getTeamUseCase = getTeamUseCase;
        this.createSportUseCase = createSportUseCase;
        this.updateSportUseCase = updateSportUseCase;
        this.upsertSportTranslationUseCase = upsertSportTranslationUseCase;
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

    @PostMapping
    public ResponseEntity<SportResponse> createSport(
            @Validated @RequestBody CreateSportRequest request) {
        CreateSportCommand command = new CreateSportCommand(resolveTranslations(request.name(), request.translations()));
        SportResponse response = SportResponseMapper.toResponse(createSportUseCase.execute(command));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportResponse> updateSport(
            @PathVariable Integer id,
            @Validated @RequestBody UpdateSportRequest request) {
        UpdateSportCommand command = new UpdateSportCommand(
                id,
            resolveTranslations(request.name(), request.translations()));
        SportResponse response = SportResponseMapper.toResponse(updateSportUseCase.execute(command));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{sportId}/translations/{languageCode}")
    public ResponseEntity<SportResponse> upsertTranslation(
            @PathVariable Integer sportId,
            @PathVariable String languageCode,
            @Validated @RequestBody UpsertSportTranslationRequest request) {
        String lang = LanguageUtils.normalizeLanguage(languageCode);
        UpsertSportTranslationCommand command = new UpsertSportTranslationCommand(sportId, lang, request.name());
        SportResponse response = SportResponseMapper.toResponse(upsertSportTranslationUseCase.execute(command));
        return ResponseEntity.ok(response);
    }

    private java.util.List<SportTranslationCommand> resolveTranslations(
            String name,
            java.util.List<SportTranslationRequest> translations) {
        java.util.List<SportTranslationCommand> resolved = new java.util.ArrayList<>();
        resolved.add(new SportTranslationCommand("en", name));
        if (translations != null) {
            resolved.addAll(translations.stream()
                .map(t -> new SportTranslationCommand(
                    LanguageUtils.normalizeLanguage(t.languageCode()),
                    t.name()))
                .toList());
        }
        return resolved;
    }
}
