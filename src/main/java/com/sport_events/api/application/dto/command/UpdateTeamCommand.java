package com.sport_events.api.application.dto.command;

public record UpdateTeamCommand(
        Integer teamId,
        String name,
        Integer sportId
) {
}
