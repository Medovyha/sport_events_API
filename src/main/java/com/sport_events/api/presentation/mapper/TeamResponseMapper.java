package com.sport_events.api.presentation.mapper;

import com.sport_events.api.application.dto.result.TeamResult;
import com.sport_events.api.presentation.dto.TeamResponse;

public class TeamResponseMapper {

    public static TeamResponse toResponse(TeamResult result) {
        return new TeamResponse(
                result.teamId(),
                result.name(),
                result.sportId(),
                result.sportName());
    }
}
