package com.sport_events.api.presentation.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddPlayerToEventTeamRequest(
        @NotEmpty List<@NotNull Integer> playerIds
) {
}
