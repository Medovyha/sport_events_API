package com.sport_events.api.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record AddTeamToEventRequest(
        @NotNull Integer teamId
) {
}
