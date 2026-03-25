package com.sport_events.api.application.dto.command;

public record CreateSportCommand(
        String name,
        String languageCode
) {
}
