package com.sport_events.api.presentation.mapper;

import java.util.List;

import com.sport_events.api.application.dto.result.PlayerResult;
import com.sport_events.api.presentation.dto.PlayerResponse;
import com.sport_events.api.presentation.dto.TeamResponse;

public class PlayerResponseMapper {

    public static PlayerResponse toResponse(PlayerResult result) {
        List<TeamResponse> teams = result.teams() != null
                ? result.teams().stream().map(TeamResponseMapper::toResponse).toList()
                : null;
        return new PlayerResponse(
                result.playerId(),
                result.firstName(),
                result.lastName(),
                result.dateOfBirth(),
                teams);
    }
}
