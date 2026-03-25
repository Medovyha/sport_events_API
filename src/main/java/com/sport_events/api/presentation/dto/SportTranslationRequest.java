package com.sport_events.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record SportTranslationRequest(
        @NotBlank String languageCode,
        @NotBlank String name
) {
}
