package com.sport_events.api.presentation.dto;

import java.time.OffsetDateTime;

public record EventsSummaryResponse(
        Long eventId,
        OffsetDateTime startsAt,
        String name,
        String description,
        VenueResponse venue
) {
    public record VenueResponse(
            Integer venueId,
            String name,
            String address
    ) {
    }
}
