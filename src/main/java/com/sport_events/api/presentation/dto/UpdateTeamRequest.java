package com.sport_events.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTeamRequest(
        @NotBlank String name,
        @NotNull Integer sportId
) {
}
