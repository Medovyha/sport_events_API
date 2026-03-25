package com.sport_events.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSportRequest(
        @NotBlank String name
) {
}
