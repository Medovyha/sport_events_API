package com.sport_events.api.presentation.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record UpdateEventTeamPlayersRequest(
        @NotNull List<@NotNull Integer> playerIds
) {
}
