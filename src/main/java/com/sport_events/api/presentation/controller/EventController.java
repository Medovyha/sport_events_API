package com.sport_events.api.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport_events.api.application.dto.query.GetEventQuery;
import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.application.usecase.GetEventUseCase;
import com.sport_events.api.presentation.dto.EventDetailsResponse;

@RestController
@RequestMapping("/events")
public class EventController {

    private final GetEventUseCase getEventUseCase;

    public EventController(GetEventUseCase getEventUseCase) {
        this.getEventUseCase = getEventUseCase;
    }

    @GetMapping("/{id}")
        public ResponseEntity<EventDetailsResponse> getEvent(
                        @PathVariable Long id,
                        @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {
                String languageCode = acceptLanguage.split(",")[0].split(";")[0].split("-")[0].trim().toLowerCase();
                EventResult result = getEventUseCase.execute(new GetEventQuery(id, languageCode));
        return ResponseEntity.ok(toResponse(result));
    }

    private EventDetailsResponse toResponse(EventResult result) {
        EventDetailsResponse.VenueResponse venueResponse = result.venue() != null
                ? new EventDetailsResponse.VenueResponse(
                        result.venue().venueId(),
                        result.venue().name(),
                        result.venue().address())
                : null;

        var teams = result.teams().stream()
                .map(t -> {
                    var players = t.players().stream()
                            .map(p -> new EventDetailsResponse.PlayerResponse(
                                    p.playerId(), p.firstName(), p.lastName(), p.dateOfBirth()))
                            .toList();
                    return new EventDetailsResponse.EventTeamResponse(
                            t.eventTeamsId(),
                            t.teamId(),
                            t.teamName(),
                            t.sportId(),
                            t.sportName(),
                            players);
                })
                .toList();

        String name = result.translation() != null ? result.translation().name() : null;
        String description = result.translation() != null ? result.translation().description() : null;

        return new EventDetailsResponse(result.eventId(), result.startsAt(), name, description, venueResponse, teams);
    }
}
