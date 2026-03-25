package com.sport_events.api.application.dto.command;

public record UpdateVenueCommand(
        Integer venueId,
        String name,
        String address
) {
}
