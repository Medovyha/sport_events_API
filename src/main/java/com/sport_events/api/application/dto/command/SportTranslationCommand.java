package com.sport_events.api.application.dto.command;

public record SportTranslationCommand(
        String languageCode,
        String name
) {
}
