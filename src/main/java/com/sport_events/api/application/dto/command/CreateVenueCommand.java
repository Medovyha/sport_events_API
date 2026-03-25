package com.sport_events.api.application.dto.command;

public record CreateVenueCommand(
        String name,
        String address
) {
}
