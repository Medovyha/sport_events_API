package com.sport_events.api.application.dto.command;

public record UpsertSportTranslationCommand(
        Integer sportId,
        String languageCode,
        String name
) {
}
