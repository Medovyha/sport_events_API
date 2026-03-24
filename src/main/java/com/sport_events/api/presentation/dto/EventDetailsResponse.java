package com.sport_events.api.presentation.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record EventDetailsResponse(
        Long eventId,
        OffsetDateTime startsAt,
        String name,
        String description,
        VenueResponse venue,
        List<EventTeamResponse> teams
) {

    public record VenueResponse(
            Integer venueId,
            String name,
            String address
    ) {}

    public record PlayerResponse(
            Integer playerId,
            String firstName,
            String lastName,
            java.time.LocalDate dateOfBirth
    ) {}

    public record EventTeamResponse(
            Integer eventTeamsId,
            Integer teamId,
            String teamName,
            Integer sportId,
            String sportName,
            List<PlayerResponse> players
    ) {}
}
