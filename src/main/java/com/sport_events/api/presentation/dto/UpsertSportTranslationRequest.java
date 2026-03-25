package com.sport_events.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record UpsertSportTranslationRequest(
        @NotBlank String name
) {
}
