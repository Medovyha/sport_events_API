package com.sport_events.api.presentation.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlayerResponse(
        Integer playerId,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        List<TeamResponse> teams
) {
}
