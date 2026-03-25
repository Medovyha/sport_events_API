package com.sport_events.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTeamRequest(
        @NotBlank String name,
        @NotNull Integer sportId
) {
}
