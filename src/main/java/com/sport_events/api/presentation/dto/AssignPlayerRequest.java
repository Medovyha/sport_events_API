    package com.sport_events.api.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record AssignPlayerRequest(
        @NotNull Integer playerId
) {
}
