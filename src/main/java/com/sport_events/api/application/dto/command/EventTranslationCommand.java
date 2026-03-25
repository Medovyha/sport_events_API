package com.sport_events.api.application.dto.command;

public record EventTranslationCommand(
        String languageCode,
        String name,
        String description
) {
}
