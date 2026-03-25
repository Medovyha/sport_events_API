package com.sport_events.api.presentation.controller;

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

import com.sport_events.api.application.dto.command.AddPlayerToEventTeamCommand;
import com.sport_events.api.application.dto.command.AddTeamToEventCommand;
import com.sport_events.api.application.dto.command.CreateEventCommand;
import com.sport_events.api.application.dto.command.DeleteEventCommand;
import com.sport_events.api.application.dto.command.EventTranslationCommand;
import com.sport_events.api.application.dto.command.RemovePlayerFromEventTeamCommand;
import com.sport_events.api.application.dto.command.RemoveTeamFromEventCommand;
import com.sport_events.api.application.dto.command.UpdateEventCommand;
import com.sport_events.api.application.dto.command.UpdateEventTeamPlayersCommand;
import com.sport_events.api.application.dto.query.GetAllEventsQuery;
import com.sport_events.api.application.dto.query.GetEventQuery;
import com.sport_events.api.application.dto.query.GetFutureEventsQuery;
import com.sport_events.api.application.dto.query.GetPreviousEventsQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.usecase.AddPlayerToEventTeamUseCase;
import com.sport_events.api.application.usecase.AddTeamToEventUseCase;
import com.sport_events.api.application.usecase.CreateEventUseCase;
import com.sport_events.api.application.usecase.DeleteEventUseCase;
import com.sport_events.api.application.usecase.GetAllEventsUseCase;
import com.sport_events.api.application.usecase.GetEventUseCase;
import com.sport_events.api.application.usecase.GetFutureEventsUseCase;
import com.sport_events.api.application.usecase.GetPreviousEventsUseCase;
import com.sport_events.api.application.usecase.RemovePlayerFromEventTeamUseCase;
import com.sport_events.api.application.usecase.RemoveTeamFromEventUseCase;
import com.sport_events.api.application.usecase.UpdateEventTeamPlayersUseCase;
import com.sport_events.api.application.usecase.UpdateEventUseCase;
import com.sport_events.api.presentation.dto.AddPlayerToEventTeamRequest;
import com.sport_events.api.presentation.dto.AddTeamToEventRequest;
import com.sport_events.api.presentation.dto.CreateEventRequest;
import com.sport_events.api.presentation.dto.EventDetailsResponse;
import com.sport_events.api.presentation.dto.EventTranslationRequest;
import com.sport_events.api.presentation.dto.EventsSummaryResponse;
import com.sport_events.api.presentation.dto.UpdateEventRequest;
import com.sport_events.api.presentation.dto.UpdateEventTeamPlayersRequest;
import com.sport_events.api.presentation.mapper.EventResponseMapper;
import com.sport_events.api.presentation.mapper.EventsListMapper;
import com.sport_events.api.presentation.util.LanguageUtils;

@RestController
@RequestMapping("/events")
public class EventController {

    private final GetEventUseCase getEventUseCase;
    private final GetAllEventsUseCase getAllEventsUseCase;
    private final GetPreviousEventsUseCase getPreviousEventsUseCase;
    private final GetFutureEventsUseCase getFutureEventsUseCase;
    private final CreateEventUseCase createEventUseCase;
    private final UpdateEventUseCase updateEventUseCase;
    private final AddTeamToEventUseCase addTeamToEventUseCase;
    private final AddPlayerToEventTeamUseCase addPlayerToEventTeamUseCase;
    private final UpdateEventTeamPlayersUseCase updateEventTeamPlayersUseCase;
    private final RemovePlayerFromEventTeamUseCase removePlayerFromEventTeamUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final RemoveTeamFromEventUseCase removeTeamFromEventUseCase;

    public EventController(
            GetEventUseCase getEventUseCase,
            GetAllEventsUseCase getAllEventsUseCase,
            GetPreviousEventsUseCase getPreviousEventsUseCase,
            GetFutureEventsUseCase getFutureEventsUseCase,
            CreateEventUseCase createEventUseCase,
            UpdateEventUseCase updateEventUseCase,
            AddTeamToEventUseCase addTeamToEventUseCase,
            AddPlayerToEventTeamUseCase addPlayerToEventTeamUseCase,
            UpdateEventTeamPlayersUseCase updateEventTeamPlayersUseCase,
            RemovePlayerFromEventTeamUseCase removePlayerFromEventTeamUseCase,
            DeleteEventUseCase deleteEventUseCase,
            RemoveTeamFromEventUseCase removeTeamFromEventUseCase) {
        this.getEventUseCase = getEventUseCase;
        this.getAllEventsUseCase = getAllEventsUseCase;
        this.getPreviousEventsUseCase = getPreviousEventsUseCase;
        this.getFutureEventsUseCase = getFutureEventsUseCase;
        this.createEventUseCase = createEventUseCase;
        this.updateEventUseCase = updateEventUseCase;
        this.addTeamToEventUseCase = addTeamToEventUseCase;
        this.addPlayerToEventTeamUseCase = addPlayerToEventTeamUseCase;
        this.updateEventTeamPlayersUseCase = updateEventTeamPlayersUseCase;
        this.removePlayerFromEventTeamUseCase = removePlayerFromEventTeamUseCase;
        this.deleteEventUseCase = deleteEventUseCase;
        this.removeTeamFromEventUseCase = removeTeamFromEventUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetailsResponse> getEvent(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        EventResult result = getEventUseCase.execute(new GetEventQuery(id, LanguageUtils.normalizeLanguage(acceptLanguage)));
        return ResponseEntity.ok(EventResponseMapper.toResponse(result));
    }

    @GetMapping
    public ResponseEntity<java.util.List<EventsSummaryResponse>> getAllEvents(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        var result = getAllEventsUseCase.execute(new GetAllEventsQuery(LanguageUtils.normalizeLanguage(acceptLanguage)));
        return ResponseEntity.ok(EventsListMapper.toResponse(result));
    }

    @GetMapping("/previous")
    public ResponseEntity<java.util.List<EventsSummaryResponse>> getPreviousEvents(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        var result = getPreviousEventsUseCase.execute(new GetPreviousEventsQuery(LanguageUtils.normalizeLanguage(acceptLanguage)));
        return ResponseEntity.ok(EventsListMapper.toResponse(result));
    }

    @GetMapping("/future")
    public ResponseEntity<java.util.List<EventsSummaryResponse>> getFutureEvents(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
        var result = getFutureEventsUseCase.execute(new GetFutureEventsQuery(LanguageUtils.normalizeLanguage(acceptLanguage)));
        return ResponseEntity.ok(EventsListMapper.toResponse(result));
    }

    @PostMapping
    public ResponseEntity<EventDetailsResponse> createEvent(
            @Validated @RequestBody CreateEventRequest request) {
        CreateEventCommand command = new CreateEventCommand(
                request.startsAt(),
                request.venueId(),
                resolveTranslations(request.name(), request.description(), request.translations()));
        EventResult result = createEventUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(EventResponseMapper.toResponse(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDetailsResponse> updateEvent(
            @PathVariable Long id,
            @Validated @RequestBody UpdateEventRequest request) {
        UpdateEventCommand command = new UpdateEventCommand(
                id,
                request.startsAt(),
                request.venueId(),
                resolveTranslations(request.name(), request.description(), request.translations()));
        EventResult result = updateEventUseCase.execute(command);
        return ResponseEntity.ok(EventResponseMapper.toResponse(result));
    }

    @PostMapping("/{id}/teams")
    public ResponseEntity<Void> addTeamToEvent(
            @PathVariable Long id,
            @Validated @RequestBody AddTeamToEventRequest request) {
        addTeamToEventUseCase.execute(new AddTeamToEventCommand(id, request.teamId()));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/teams/{teamId}/players")
    public ResponseEntity<Void> addPlayerToEventTeam(
            @PathVariable Long id,
            @PathVariable Integer teamId,
            @Validated @RequestBody AddPlayerToEventTeamRequest request) {
        addPlayerToEventTeamUseCase.execute(new AddPlayerToEventTeamCommand(id, teamId, request.playerIds()));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/teams/{teamId}/players")
    public ResponseEntity<Void> updateEventTeamPlayers(
            @PathVariable Long id,
            @PathVariable Integer teamId,
            @Validated @RequestBody UpdateEventTeamPlayersRequest request) {
        updateEventTeamPlayersUseCase.execute(new UpdateEventTeamPlayersCommand(id, teamId, request.playerIds()));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/teams/{teamId}/players/{playerId}")
    public ResponseEntity<Void> removePlayerFromEventTeam(
            @PathVariable Long id,
            @PathVariable Integer teamId,
            @PathVariable Integer playerId) {
        removePlayerFromEventTeamUseCase.execute(new RemovePlayerFromEventTeamCommand(id, teamId, playerId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/teams/{teamId}")
    public ResponseEntity<Void> removeTeamFromEvent(
            @PathVariable Long id,
            @PathVariable Integer teamId) {
        removeTeamFromEventUseCase.execute(new RemoveTeamFromEventCommand(id, teamId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id) {
        deleteEventUseCase.execute(new DeleteEventCommand(id));
        return ResponseEntity.noContent().build();
    }

    private java.util.List<EventTranslationCommand> resolveTranslations(
            String englishName,
            String englishDescription,
            java.util.List<EventTranslationRequest> translations) {
        java.util.List<EventTranslationCommand> resolved = new java.util.ArrayList<>();
        resolved.add(new EventTranslationCommand("en", englishName, englishDescription));
        if (translations != null) {
            resolved.addAll(translations.stream()
                    .map(t -> new EventTranslationCommand(
                            LanguageUtils.normalizeLanguage(t.languageCode()),
                            t.name(),
                            t.description()))
                    .toList());
        }
        return resolved;
    }
}
