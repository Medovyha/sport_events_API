package com.sport_events.api.presentation.dto;

public record VenueResponse(
        Integer venueId,
        String name,
        String address
) {
}
