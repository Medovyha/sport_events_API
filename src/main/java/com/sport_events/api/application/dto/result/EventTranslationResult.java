package com.sport_events.api.application.dto.result;

public record EventTranslationResult(
        Integer eventTranslationId,
        Integer languageId,
        String name,
        String description
) {
}
