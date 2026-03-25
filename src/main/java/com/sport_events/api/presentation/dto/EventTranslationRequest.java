package com.sport_events.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record EventTranslationRequest(
        @NotBlank String languageCode,
        @NotBlank String name,
        @NotBlank String description
) {
}
