package com.sport_events.api.presentation.mapper;

import com.sport_events.api.application.dto.result.EventResult;
import com.sport_events.api.presentation.dto.EventDetailsResponse;

public class EventResponseMapper {

    public static EventDetailsResponse toResponse(EventResult result) {
        EventDetailsResponse.VenueResponse venueResponse = result.venue() != null
                ? new EventDetailsResponse.VenueResponse(
                        result.venue().venueId(),
                        result.venue().name(),
                        result.venue().address())
                : null;

        var teams = result.teams() != null
                ? result.teams().stream()
                        .map(t -> {
                            var players = t.players() != null
                                    ? t.players().stream()
                                            .map(p -> new EventDetailsResponse.PlayerResponse(
                                                    p.playerId(), p.firstName(), p.lastName(), p.dateOfBirth()))
                                            .toList()
                                    : null;
                            return new EventDetailsResponse.EventTeamResponse(
                                    t.eventTeamsId(),
                                    t.teamId(),
                                    t.teamName(),
                                    t.sportId(),
                                    t.sportName(),
                                    players);
                        })
                        .toList()
                : null;

        String name = result.translation() != null ? result.translation().name() : null;
        String description = result.translation() != null ? result.translation().description() : null;

        return new EventDetailsResponse(result.eventId(), result.startsAt(), name, description, venueResponse, teams);
    }
}
