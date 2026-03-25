package com.sport_events.api.presentation.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record PlayerResponse(
            Integer playerId,
            String firstName,
            String lastName,
            java.time.LocalDate dateOfBirth
    ) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record EventTeamResponse(
            Integer eventTeamsId,
            Integer teamId,
            String teamName,
            Integer sportId,
            String sportName,
            List<PlayerResponse> players
    ) {}
}
